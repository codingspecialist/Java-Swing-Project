package starz502Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import starz502Server.calculator.GameCalculator;
import starz502Server.dao.JoinModelDAO;
import starz502Server.dao.LobbyModelDAO;
import starz502Server.dao.LoginModelDAO;
import starz502Server.dao.ResultModelDAO;
import starz502Server.data.DataTypeParser;
import starz502Server.data.DataTypes;
import starz502Server.models.Bullet;
import starz502Server.models.GameModel;
import starz502Server.models.GameModelList;
import starz502Server.models.JoinModel;
import starz502Server.models.LobbyModel;
import starz502Server.models.LoginModel;
import starz502Server.models.Player;
import starz502Server.models.ResultModel;

public class GameServer {
	private ServerSocket serverSocket;
	private Vector<Client> clientList;
	private GameCalculator gameCalculator;
	private DataExportToAllClient dataExportToAllClient;

	private ScheduledExecutorService service;

	public GameServer() {
		try {
			gameCalculator = new GameCalculator();
			dataExportToAllClient = new DataExportToAllClient();
			serverSocket = new ServerSocket(5000);
			clientList = new Vector<Client>();

			while (true) {
				System.out.println("Ŭ���̾�Ʈ �����...");
				Socket socket = serverSocket.accept();
				System.out.println("Ŭ���̾�Ʈ ����");

				// �α��� ��û ó���ϴ� �޼���

				Client client = new Client(socket, dataExportToAllClient);
				clientList.add(client);
				client.start();
			}

		} catch (Exception e) {
			System.out.println("Ŭ���̾�Ʈ ���� ����");
			e.printStackTrace();
		}
	}

	class Client extends Thread {
		private String username;
		private BufferedReader reader;
		private StringBuilder jSonData;
		private PrintWriter writer;
		private Socket socket;
		private String line;
		private LoginModelDAO loginModelDao;
		private JoinModelDAO joinModelDao;
		private LobbyModelDAO lobbyModelDao;
		private ResultModelDAO resultModelDao;
		private DataExportToAllClient dataExportToAllClient;

		public Client(Socket socket, DataExportToAllClient dataExportToAllClient) {
			loginModelDao = new LoginModelDAO();
			joinModelDao = new JoinModelDAO();
			lobbyModelDao = new LobbyModelDAO();
			resultModelDao = new ResultModelDAO();
			this.socket = socket;
			this.dataExportToAllClient = dataExportToAllClient;
		}

		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());
				jSonData = new StringBuilder();
				Gson gson = new Gson();

				// Ŭ���̾�Ʈ�κ��� ��û�Ǵ� ���� ���� (���ѷ���) -> �ش� ������ ������ �����ϸ� while�� �����鼭 thread ���� �ʿ�
				while ((line = reader.readLine()) != null) {
					jSonData.append(line);
					Integer dataType = DataTypeParser.getDataType(jSonData.toString()); // datatype �Ľ�
					switch (dataType) { // dataType���� ó����� �б�
					case DataTypes.LOGIN:
						LoginModel loginModel = gson.fromJson(jSonData.toString(), LoginModel.class); // ������ �������
						username = loginModel.getStz_username();
						boolean loginResult = loginModelDao.loginCheck(loginModel.getStz_username(),
								loginModel.getStz_password()); // �α��� üũ�Լ����� ���̵�, ��� Ȯ�� �� true or false ����
						jSonData.setLength(0);
						loginResultToClient(loginResult); // ��� �ش� �������� ����
						break;
					case DataTypes.JOIN:
						JoinModel joinModel = gson.fromJson(jSonData.toString(), JoinModel.class);
						boolean joinResult = joinModelDao.saveNewUser(joinModel.getStz_username(),
								joinModel.getStz_password());
						jSonData.setLength(0);
						joinResultToClient(joinResult);
						break;
					case DataTypes.LOBBY:
						LobbyModel lobbyModelResult = gson.fromJson(jSonData.toString(), LobbyModel.class);
						lobbyModelResult
								.setLobbyModelUser(lobbyModelDao.getLobbyData(lobbyModelResult, dataExportToAllClient));
						jSonData.setLength(0);
						lobbyResultToClient(lobbyModelResult);

						if (gameCalculator.getResultModelList().size() > 0) {
							int removeIndex = 0;
							for (ResultModel rm : gameCalculator.getResultModelList()) {
								if (rm.getStz_username().equals(username)) {
									break;
								}
								removeIndex++;
							}
							gameCalculator.getResultModelList().remove(removeIndex);
						}
						break;
					case DataTypes.GAME:
						/*
						 * �� Ŭ���̾�Ʈ �����忡�� ������ ���ܿ�
						 */
						GameModel gameModel = gson.fromJson(jSonData.toString(), GameModel.class);

						for (int i = 0; i < gameCalculator.getGameModelForCalculator().size(); i++) {
							if (gameCalculator.getGameModelForCalculator().get(i).getPlayer().getStz_username()
									.equals(username)) {
								gameCalculator.getGameModelForCalculator().get(i).getPlayer()
										.setAngle(gameModel.getPlayer().getAngle());
								gameCalculator.getGameModelForCalculator().get(i).getPlayer()
										.setStz_username(gameModel.getPlayer().getStz_username());
								gameCalculator.getGameModelForCalculator().get(i).getPlayer()
										.setX(gameModel.getPlayer().getX());
								gameCalculator.getGameModelForCalculator().get(i).getPlayer()
										.setY(gameModel.getPlayer().getY());
								gameCalculator.getGameModelForCalculator().get(i)
										.setBulletList(gameModel.getBulletList());
							}
						}
						jSonData.setLength(0);
						break;
					case DataTypes.RESULT:
						ResultModel resultModel = gson.fromJson(jSonData.toString(), ResultModel.class);
						for (int i = 0; i < gameCalculator.getResultModelList().size(); i++) {
							if (resultModel.getStz_username()
									.equals(gameCalculator.getResultModelList().get(i).getStz_username())) {
								resultResultToClient(gameCalculator.getResultModelList().get(i));
								break;
							}
						}
						jSonData.setLength(0);
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				loginModelDao.logoutCheck(username);
				lobbyModelDao.readyCheck(username);
				System.out.println(username + "Ŭ�� ���� ����");
			}
		}

		public void loginResultToClient(boolean loginResult) { // �α��� ���� ���� ���� true, false�� �ش� �������� ����
			writer.println(loginResult);
			writer.flush();
		}

		public void joinResultToClient(boolean joinResult) { // ȸ������ ���� ���� ���� �ش� �������� true, false�� ����
			writer.println(joinResult);
			writer.flush();
		}

		public void lobbyResultToClient(LobbyModel lobbyModelResult) {
			Gson gson = new Gson();
			String jSonData = gson.toJson(lobbyModelResult);
			dataExportToAllClient.lobbyDataExportToAllClient(jSonData);
		}

		public void resultResultToClient(ResultModel resultResult) {
			Gson gson = new Gson();
			String jSonData = gson.toJson(resultResult);
			gameCalculator.setRank(resultResult.getRank() - 1);
			if (gameCalculator.getRank() == 0) {
				service.shutdown();
				gameCalculator.setRank(4);
			}

			writer.println(jSonData);
			writer.flush();
		}

	}// Ŭ���̾�Ʈ Ŭ���� ����

	public class DataExportToAllClient extends Thread { 
		private String username;
		private Gson gson = new Gson();

		public DataExportToAllClient() {

		}

		public DataExportToAllClient(String username) {
			this.username = username;
		}

		public void startDataToReadyClient(Vector<String> readyClientList) {
			gameCalculator.getGameModelForCalculator().setSize(0);
			for (int i = 0; i < readyClientList.size(); i++) {
				GameModel readyClient = new GameModel();
				readyClient.setPlayer(new Player());
				readyClient.getPlayer().setStz_username(readyClientList.get(i));
				readyClient.getPlayer().setCurHp(100);


				Vector<Bullet> bulletList = new Vector<Bullet>();
				readyClient.setBulletList(bulletList);

				gameCalculator.getGameModelForCalculator().add(readyClient);

			}

			for (String readyClientUserName : readyClientList) {
				for (Client client : clientList) {
					if (readyClientUserName.equals(client.username)) {
						client.writer.println("{\"datatype\":6"); 
						client.writer.flush();
					}
				}
			}

			// run() 0�� �Ŀ� 34ms���� ����
			service = Executors.newSingleThreadScheduledExecutor();
			service.scheduleAtFixedRate(this, 0, 34, TimeUnit.MILLISECONDS);

		}

		public void lobbyDataExportToAllClient(String jSonData) {
			for (Client client : clientList) {				
				client.writer.println(jSonData);
				client.writer.flush();
			}
		}

		// ��� Ŭ���̾�Ʈ���� (�׸� �׸��µ� �ʿ��� ���)������ �����ϴ� ������ , period : 34ms

		@Override
		public void run() {

			gameCalculator.calPlayerHitByBullet(); // �ǰ� ���� �Լ�
			GameModelList gameModelList = new GameModelList(gameCalculator.getGameModelForCalculator());

			for (Client client : clientList) {
				client.writer.println(gson.toJson(gameModelList));
				client.writer.flush();
			}

			for (int i = 0; i < gameModelList.getGameModelList().size(); i++) {
				if (gameModelList.getGameModelList().get(i).getPlayer().getCurHp() <= 0) {
					for (int j = 0; j < clientList.size(); j++) {
						if (gameModelList.getGameModelList().get(i).getPlayer().getStz_username()
								.equals(clientList.get(j).username)) {
							clientList.remove(j);
						}
					}
				}
			}
		}

	}

	public static void main(String[] args) { // ���� ��ŸƮ
		new GameServer();
	}

}