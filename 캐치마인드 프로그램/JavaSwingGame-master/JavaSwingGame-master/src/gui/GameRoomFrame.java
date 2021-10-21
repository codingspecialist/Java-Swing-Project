package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Client.MainClient;
import Paint.MyCanvas;
import server.MainServer;
import utils.Protocol;

public class GameRoomFrame extends JFrame {

	private final static String TAG = "GameroomFrame : ";

	private GameRoomFrame gameroomFrame = this;
	public JTextField tfCard, tfChat;
	public JButton btEnter, btCard, btGstart, btBlack, btRed, btBlue, btGreen, btYellow, btEraser, btAlldel, btBar;
	public JTextArea taChat, taUserList;
	public JLabel LuserList;
	public MainClient mainClient;
	public JPanel Canvas, PDrawing;
	private String username;
	private JLabel laUsername;
	public MyCanvas can;
	private String currentColor = "black";
	private JButton btGreen_1;
	public ImageIcon iconGR;

	public GameRoomFrame(String username) {
		this.username = username;
		
		back();
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);
	}
	
	private void back() {
		iconGR = new ImageIcon("src/images/gameRoomFrame.png");
		PDrawing = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(iconGR.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		
	}
	

	// 객체생성
	public void initObject() {
		mainClient = new MainClient(gameroomFrame);
		btBar = new JButton(new ImageIcon("src/images/grBar.png"));
		btCard = new JButton(new ImageIcon("src/images/grQuiz.png"));
		btGstart = new JButton(new ImageIcon("src/images/grGS.png"));
		tfCard = new JTextField();
//		PDrawing = new JPanel();
		Canvas = new JPanel();
		LuserList = new JLabel(new ImageIcon("src/images/grUList.png"));
		taUserList = new JTextArea();
		tfChat = new JTextField();
		btEnter = new JButton(new ImageIcon("src/images/grEn.png"));
		btBlack = new JButton(new ImageIcon("src/images/black.png"));
		btRed = new JButton(new ImageIcon("src/images/red.png"));
		btBlue = new JButton(new ImageIcon("src/images/blue.png"));
		btGreen = new JButton(new ImageIcon("src/images/green.png"));
		btYellow = new JButton(new ImageIcon("src/images/yellow.png"));
		btEraser = new JButton(new ImageIcon("src/images/grE.png"));
		btAlldel = new JButton(new ImageIcon("src/images/grAE.png"));
	}

	// 데이터초기화
	private void initData() {
		String userMsg = Protocol.CONNECT + ":" + username;
		mainClient.send(userMsg);
		System.out.println(TAG + "send 확인 !!!!! " + userMsg);
	}

	// 디자인
	public void initDesign() {
		// 1. 기본세팅
		setTitle("Game Room");
		setBounds(100, 100, 962, 787);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(211, 211, 211));

		// 2. 패널세팅
		btCard.setBounds(36, 66, 112, 48);
		tfCard.setBounds(160, 66, 401, 47);
		tfCard.setColumns(10);
		tfCard.setEnabled(false);
		tfCard.setDisabledTextColor(Color.BLACK);
		Border borderLine = BorderFactory.createLineBorder(Color.BLACK, 3);
		tfCard.setBorder(borderLine);
		btGstart.setBounds(580, 66, 323, 48);
		LuserList.setBounds(578, 115, 320, 40);
		taUserList.setBounds(580, 177, 323, 92);
		taUserList.setEnabled(false);
		taUserList.setBorder(borderLine);
		taUserList.setDisabledTextColor(Color.BLACK);
		
		JScrollPane chatScroll = new JScrollPane();
		chatScroll.setBounds(584, 275, 316, 363);
		tfChat.setBounds(582, 657, 243, 43);
		tfChat.setColumns(10);
		tfChat.setBorder(borderLine);
		btEnter.setBounds(831, 656, 73, 44);
		btBlack.setBounds(18, 521, 54, 35);
		btBlack.setPreferredSize(new Dimension(40, 25));
		btRed.setBounds(77, 521, 54, 35);
		btRed.setPreferredSize(new Dimension(40, 25));
		btBlue.setBounds(135, 521, 54, 35);
		btBlue.setPreferredSize(new Dimension(40, 25));
		btGreen.setBounds(195, 521, 54, 35);
		btGreen.setPreferredSize(new Dimension(40, 25));
		btYellow.setBounds(253, 521, 54, 35);
		btYellow.setPreferredSize(new Dimension(40, 25));


		btEraser.setBounds(337, 521, 54, 35);
		btAlldel.setBounds(397, 521, 100, 35);
		PDrawing.setBounds(15, 100, 474, 403);
//		PDrawing.setBorder(borderLine);
		Canvas.setLayout(null);
		Canvas.setBounds(40, 126, 521, 574);
		Canvas.setBorder(borderLine);
		Canvas.setBackground(Color.WHITE);
		
		

		// 3. 패널에 컴포넌트 추가
		getContentPane().add(btCard);
		getContentPane().add(tfCard);
		getContentPane().add(Canvas);
		getContentPane().add(btGstart);
		getContentPane().add(LuserList);
		getContentPane().add(taUserList);
		getContentPane().add(chatScroll);
		getContentPane().add(tfChat);
		getContentPane().add(btEnter);
		can = new MyCanvas();
		can.setLocation(8, 5);
		Canvas.add(can);
		can.setSize(489, 506);
		can.setBackground(Color.WHITE);
		Canvas.add(btBlack);
		Canvas.add(btRed);
		Canvas.add(btBlue);
		Canvas.add(btGreen);
		Canvas.add(btYellow);
		Canvas.add(btEraser);
		Canvas.add(btAlldel);
		Canvas.add(PDrawing);

		laUsername = new JLabel();
		laUsername.setBounds(581, 137, 57, 15);
		getContentPane().add(laUsername);
		taChat = new JTextArea();
		taChat.setBounds(580, 272, 323, 369);
		getContentPane().add(taChat);
		taChat.setEditable(false);
		taChat.setTabSize(10);
		taChat.setEnabled(false);
		taChat.setBorder(borderLine);
		taChat.setDisabledTextColor(Color.BLACK);
		btBar.setBounds(-1, 1, 944, 40);
//		btBar.setEnabled(false);
		btBar.setBorder(borderLine);
		getContentPane().add(btBar);
	}

	// 리스너 등록
	private void initListener() {

		btEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				taChat.append(" [ " + username + " ] " + tfChat.getText() + "\n");
				String myMsg = username + ":" + tfChat.getText();
				String msgLine = Protocol.CHAT + ":" + myMsg;
				// Chat:안녕
				mainClient.send(msgLine);
				tfChat.setText("");
			}
		});

		tfChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					taChat.append(" [ " + username + " ] " + tfChat.getText() + "\n");
					String myMsg = username + ":" + tfChat.getText();
					String msgLine = Protocol.CHAT + ":" + myMsg;
					// Chat:안녕
					mainClient.send(msgLine);
					tfChat.setText("");
				}
			}
		});

		btGstart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String msgLine = Protocol.STARTGAME + ":" + "false";
				mainClient.send(msgLine);

				// StartGame
//				int vectorSize = MainServer.vc.size();

//				System.out.println(TAG + "vectorSize 확인 : " + vectorSize);
//				if (vectorSize < 2) {
//					taChat.append("유저가 2인 이상이어야 시작할 수 있습니다.");
//				} else {
			}
		});
		
		btAlldel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String deleteLine = Protocol.ALLERASER + ":" + "false";
				mainClient.send(deleteLine);
			}
		});

		MyHandler myHandler = new MyHandler();
		can.addMouseMotionListener(myHandler);
		btBlack.addActionListener(myHandler);
		btRed.addActionListener(myHandler);
		btBlue.addActionListener(myHandler);
		btGreen.addActionListener(myHandler);
		btYellow.addActionListener(myHandler);
		btEraser.addActionListener(myHandler);
		btAlldel.addActionListener(myHandler);
	}

	public void setColor(String myColor) {
		if (myColor.equals("black")) {
			can.color = Color.BLACK;
		} else if (myColor.equals("red")) {
			can.color = Color.RED;
		} else if (myColor.equals("blue")) {
			can.color = Color.BLUE;
		} else if (myColor.equals("Green")) {
			can.color = Color.GREEN;
		} else if (myColor.equals("Yellow")) {
			can.color = Color.YELLOW;
		} else if (myColor.equals("eraser")) {
			can.color = can.getBackground();
		} 
	}

	class MyHandler implements MouseMotionListener, ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();

			if (object == btBlack) {
				can.color = Color.BLACK;
				currentColor = "black";
			} else if (object == btRed) {
				can.color = Color.RED;
				currentColor = "red";
			} else if (object == btBlue) {
				can.color = Color.BLUE;
				currentColor = "blue";
			} else if (object == btGreen) {
				can.color = Color.GREEN;
				currentColor = "Green";
			} else if (object == btYellow) {
				can.color = Color.YELLOW;
				currentColor = "Yellow";
			} else if (object == btEraser) {
				can.color = can.getBackground();
				currentColor = "eraser";
			} else if (object == btAlldel) {
				Graphics graphics = can.getGraphics();
				graphics.clearRect(0, 0, getWidth(), getHeight());
				currentColor = "allEraser";

			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// 마우스를 드래그한 지점의 x좌표, y좌표를 얻어와서 can의 x,y 좌표값에 전달
			int XX = e.getX();
			int YY = e.getY();
			can.x = XX;
			can.y = YY;

			can.repaint();

			// 프로토콜 draw:색깔,x값,y값
			String msgLine = Protocol.DRAW + ":" + currentColor + "," + XX + "," + YY;
			System.out.println(TAG + "x, y 좌표" + XX + "," + YY);

			mainClient.send(msgLine);
			System.out.println(TAG + "drawLine" + msgLine);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
}