package Client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import gui.GameRoomFrame;
import utils.Protocol;

public class MainClient {

	private static final String TAG = "MainClient : ";

	private GameRoomFrame gameroomFrame;
	Socket socket;
	BufferedWriter bw;
	BufferedReader keyboardln;
	BufferedImage bi;

	public MainClient(GameRoomFrame gameroomFrame) {
		this.gameroomFrame = gameroomFrame;
		try {
			socket = new Socket("localhost", 8892);
			ReadThread rt = new ReadThread();
			Thread newWorker = new Thread(rt);
			newWorker.start();

			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

			keyboardln = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 스타트 버튼 클릭 : StartGame, Chat:안녕
	public void send(String outputMsg) {
		try {
			bw.write(outputMsg + "\n");
			System.out.println(TAG + "send 확인!!!!!!!!!!!!" + outputMsg);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ReadThread implements Runnable {

		public int x;
		public int y;

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String inputMsg = "";
				while ((inputMsg = br.readLine()) != null) {
					router(inputMsg);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void router(String msgLine) {
			// 만약에 Chat

			String[] msg = msgLine.split(":");
			String protocol = msg[0];
			if (protocol.equals(Protocol.CHAT)) {
				String username = msg[1];
				String chatMsg = msg[2];
				gameroomFrame.taChat.append(" [ " + username + " ] " + chatMsg + "\n");
				gameroomFrame.taChat.setCaretPosition(gameroomFrame.taChat.getDocument().getLength());

			} else if (protocol.equals(Protocol.STARTGAME)) {
				// 만약에 제시어:false -> 그림판 비활성화, 채팅창 활성화, 제시어부분 클리어
				// 만약에 제시어:다른게 -> 그림판 활성화, 채팅창 비활성화, 제시어부분 넣어주기
				String chatMsg = msg[1];
				if (chatMsg.equals("false")) {
					gameroomFrame.can.setEnabled(false);
					gameroomFrame.tfChat.setEnabled(true);
					gameroomFrame.tfCard.setText("");
					gameroomFrame.can.getGraphics().clearRect(0, 0, 900, 900);
					gameroomFrame.btGstart.setEnabled(false);
				} else { // 제시어 턴의 주인
					gameroomFrame.can.setEnabled(true);
					gameroomFrame.tfChat.setEnabled(false);
					gameroomFrame.tfCard.setText(chatMsg);
					gameroomFrame.can.getGraphics().clearRect(0, 0, 900, 900);
					gameroomFrame.btGstart.setEnabled(false);
				}
			} else if (protocol.equals(Protocol.DRAW)) {
				String[] drawMsg = msg[1].split(",");
				x = Integer.parseInt(drawMsg[1]);
				y = Integer.parseInt(drawMsg[2]);
				System.out.println(TAG + "drawMsg : x : " + x + ",  y : " + y);

				gameroomFrame.setColor(drawMsg[0]);
				gameroomFrame.can.setX(x);
				gameroomFrame.can.setY(y);
				gameroomFrame.can.repaint();
			} else if (protocol.equals(Protocol.ENDGAME)) {
				gameroomFrame.taChat.setText("게임이 종료되었습니다.");
				gameroomFrame.btGstart.setEnabled(true);
			} else if (protocol.equals(Protocol.CONNECT)) {
				// msg[0] : 프로토콜, msg[1] : getUserListParser() - userList + ","
				String[] connectMsg = msg[1].split(",");
				gameroomFrame.taUserList.setText("");
				for (int i = 0; i < connectMsg.length; i++) {
					gameroomFrame.taUserList.append(connectMsg[i] + "\n");
					System.out.println(TAG + "connect 확인 : " + connectMsg[i]);
				}

			} else if (protocol.equals(Protocol.ALLERASER)) {
				gameroomFrame.can.getGraphics().clearRect(0, 0, 900, 900);

			}
		}
	}

}
