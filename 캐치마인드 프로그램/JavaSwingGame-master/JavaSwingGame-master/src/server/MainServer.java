package server;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import Paint.MyCanvas;
import gui.CountDown;
import gui.GameRoomFrame;
import utils.Protocol;

public class MainServer {

	public ArrayList<String> userList = new ArrayList<>();
	private final static String TAG = "MainServer : ";

	ServerSocket serverSocket;
	public static Vector<SocketThread> vc;
	String turnWord = null;
	int turn = 0;
	public boolean 정답;
	private GameRoomFrame gameRoomFrame;
	private int allTurn = 2;
	private int currentTurn = 0;

	public MainServer() throws Exception {
		vc = new Vector<>();
		serverSocket = new ServerSocket(8892);
		System.out.println(TAG + "서버접속완료");

		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println(TAG + "접속 요청이 들어왔습니다.");
			SocketThread st = new SocketThread(socket);
			Thread newWorker = new Thread(st);
			newWorker.start();
			vc.add(st);
		}
	}

	// 새로운 스레드에게 버퍼를 연결할 수 있게 socket을 전달
	class SocketThread implements Runnable {
		
		SocketThread socketThread = this;
		Socket socket;
		BufferedReader br;
		BufferedWriter bw;
		String username;

		public BufferedImage bi;
		public MyCanvas myCanvas;
		public int x;
		public int y;

		public SocketThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

				String msg = "";
				while ((msg = br.readLine()) != null) {
					System.out.println(TAG + "클라이언트 : " + msg);
					router(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void router(String msgLine) {

			String[] msg = msgLine.split(":");
			String protocol = msg[0];

			if (protocol.equals(Protocol.CHAT)) {
				String username = msg[1];
				String chatMsg = msg[2];
				System.out.println(TAG + "현재 제시어 : " + turnWord);
				System.out.println(TAG + "chatMsg : " + chatMsg);
				if (chatMsg.equals(turnWord)) {
					System.out.println(TAG + "정답 : " + chatMsg + "turnWord : " + turnWord);
					chattingMsg(username + ":" + chatMsg); // ta뿌리기, ta에 정답입니다 뿌리기
					nextTurn();
				} else {
					System.out.println(TAG + "메시지 : " + chatMsg + "turnWord : " + turnWord);
					chattingMsg(username + ":" + chatMsg); // ta뿌리기
				}
			} else if (protocol.equals(Protocol.STARTGAME)) {
				startGame();
			} else if (protocol.equals(Protocol.DRAW)) {

				// System.out.println(TAG + msg[1] + "DRAW 프로토콜 확인");

				// client에게 샌드해줘
				try {
					for (SocketThread socketThread : vc) {
						if (socketThread != this) {
							socketThread.bw.write(Protocol.DRAW + ":" + msg[1] + "\n");
							socketThread.bw.flush();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (protocol.equals(Protocol.CONNECT)) {
				// username - msg[1]
				
				userList.add(msg[1]);

				System.out.println(TAG + "userList 확인 : " + userList);
				
				// userList 는 배열이라서 바로 보내는 것이 불가능함!
				// getuserListParse 를 만들어서 배열을 string 으로 풀어서 보냄
				System.out.println(TAG + "vc.size() 확인!!!!!!!!!" + vc.size());
				try {
					for (SocketThread socketThread : vc) {
						if (true) {
							socketThread.bw.write(Protocol.CONNECT + ":" + getUserListParse() + "\n");
							System.out.println(TAG + "check userList 확인 : " + getUserListParse());
							socketThread.bw.flush();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (protocol.equals(Protocol.ALLERASER)) {
				allEraser();
			}

		}// router 닫힘

		public void allEraser() {
			
			
			try {
				for (SocketThread socketThread : vc) {
					
					if (socketThread != this) {
						socketThread.bw.write(Protocol.ALLERASER + ":" + "false" + "\n");
						socketThread.bw.flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public String getUserListParse() {
			
			String sendUsernames = "";
			
			for (int i = 0; i < userList.size(); i++) {
				// 파싱할 , 와 함께 sendusernames 에 더해짐
				sendUsernames += userList.get(i) + ",";
			}
			return sendUsernames;
		}

		
		public void chattingMsg(String chatMsg) {
			try {
				for (SocketThread socketThread : vc) {
					
					if (socketThread != this) {
						socketThread.bw.write(Protocol.CHAT + ":" + chatMsg + "\n");
						socketThread.bw.flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//chattingMsg() 닫힘 

		// 제시어를 턴의 주인에게 뿌리기
		public void startGame() {
			System.out.println(TAG + "표시 1 : 성공");
			turnWord = new Word().getStr();
			try {
				if (1 < vc.size()) {
//						new CountDown();
					if (currentTurn <= allTurn) {
						for (int i = 0; i < vc.size(); i++) {

							if (i == turn) {
								// StartGame
								System.out.println(TAG + "표시 2 : 성공");
								System.out.println(TAG + "표시 2 : 메시지 프로토콜 : " + Protocol.STARTGAME + ":" + turnWord);
								vc.get(i).bw.write(Protocol.STARTGAME + ":" + turnWord + "\n");
								vc.get(i).bw.flush();
							} else {
								System.out.println(TAG + "표시 3 : 성공");
								System.out.println(TAG + "표시 3 : 메시지 프로토콜 : " + Protocol.STARTGAME + ":" + "false");
								vc.get(i).bw.write(Protocol.STARTGAME + ":" + "false" + "\n");
								vc.get(i).bw.flush();
							}
						}
					} else {
						for (int i = 0; i < allTurn; i++) {
							vc.get(i).bw.write(Protocol.ENDGAME + ":" + "false" + "\n");
							vc.get(i).bw.flush();
						}
					}
					currentTurn++;
					turn++;
					if (turn == vc.size()) { // 3
						turn = 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// startGame() 닫힘 

		// 제시어를 맞추면 다음턴으로 넘어가기
		public void nextTurn() {
			startGame();
		}
	}// Thread 닫힘

	public static void main(String[] args) {
		try {
			new MainServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
