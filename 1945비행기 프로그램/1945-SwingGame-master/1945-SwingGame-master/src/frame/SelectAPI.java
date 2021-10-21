package frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.PlayerPlane;

public class SelectAPI extends JPanel implements screenSize{

	private GameFrame gameFrame;
	private SelectAPI selectAPI = this;
	private ImageIcon player1Icon, player2Icon, player3Icon; // 플레이어 기체 이미지
	private ImageIcon planeDetailIcon1, planeDetailIcon2, planeDetailIcon3; // 기체 상세설명 이미지
	private ImageIcon bigPlayer1icon, bigPlayer2icon, bigPlayer3icon; // 버튼 누를시 커지는 이미지
	private ImageIcon selectPlaneIcon = new ImageIcon("images/SelectPlane1.png");
	private Image selectPlaneImg = selectPlaneIcon.getImage();
	

	public SelectAPI(GameFrame gameFrame) {
		
		setLayout(null);
		
		this.gameFrame = gameFrame;

		player1Icon = new ImageIcon("images/PlayerPlane1.png");
		player2Icon = new ImageIcon("images/PlayerPlane2.png");
		player3Icon = new ImageIcon("images/PlayerPlane3.png");

		bigPlayer1icon = new ImageIcon("images/BigPlane1.png");
		bigPlayer2icon = new ImageIcon("images/BigPlane2.png");
		bigPlayer3icon = new ImageIcon("images/BigPlane3.png");

		planeDetailIcon1 = new ImageIcon("images/PlaneDetailImg1.png");
		planeDetailIcon2 = new ImageIcon("images/PlaneDetailImg2.png");
		planeDetailIcon3 = new ImageIcon("images/PlaneDetailImg3.png");

		JButton btn1 = new JButton("", player1Icon);
		JButton btn2 = new JButton("", player2Icon);
		JButton btn3 = new JButton("", player3Icon);
		JLabel planeImg = new JLabel("");

		// 버튼 테두리 없음
		btn1.setBorderPainted(false);
		btn2.setBorderPainted(false);
		btn3.setBorderPainted(false);

		// 버튼 채우기 없음
		btn1.setContentAreaFilled(false);
		btn2.setContentAreaFilled(false);
		btn3.setContentAreaFilled(false);

		// 버튼 투명
		btn1.setOpaque(false);
		btn2.setOpaque(false);
		btn3.setOpaque(false);

		// 버튼 액션
		btn1.addMouseListener(new MouseAdapter() { // 버튼 클릭 리스너
			@Override
			public void mousePressed(MouseEvent e) {
				gameFrame.change("gameMap");
				batch("playerPlane");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				planeImg.setIcon(planeDetailIcon1);
				btn1.setSize(100, 89);
				btn1.setIcon(bigPlayer1icon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				planeImg.setIcon(null);
				btn1.setSize(70, 59);
				btn1.setIcon(player1Icon);
			}
		});
		btn2.addMouseListener(new MouseAdapter() { // 버튼 클릭 리스너
			@Override
			public void mousePressed(MouseEvent e) {
				gameFrame.change("gameMap");
				batch("playerPlane2");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				planeImg.setIcon(planeDetailIcon2);
				btn2.setSize(100, 89);
				btn2.setIcon(bigPlayer2icon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				planeImg.setIcon(null);
				btn2.setSize(70, 59);
				btn2.setIcon(player2Icon);
			}
		});

		btn3.addMouseListener(new MouseAdapter() { // 버튼 클릭 리스너
			@Override
			public void mousePressed(MouseEvent e) {
				gameFrame.change("gameMap");
				batch("playerPlane3");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				planeImg.setIcon(planeDetailIcon3);
				btn3.setSize(100, 89);
				btn3.setIcon(bigPlayer3icon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				planeImg.setIcon(null);
				btn3.setSize(70, 59);
				btn3.setIcon(player3Icon);
			}
		});

		btn1.setBounds(100, 640, 70, 59);
		btn2.setBounds(250, 640, 70, 59);
		btn3.setBounds(400, 640, 70, 59);
		planeImg.setBounds(180, 250, 223, 318);

		this.add(planeImg);
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(selectPlaneImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0, 196, 182, this);
		repaint();
	}
	
	
	public void batch(String playerPlane) { // 비행기 선택 후 비행기 new add
		if (playerPlane == "playerPlane") {
			gameFrame.player = new PlayerPlane(gameFrame,"PLANE1");
			gameFrame.gamePanel.add(gameFrame.player);
		} else if (playerPlane == "playerPlane2") {
			gameFrame.player = new PlayerPlane(gameFrame,"PLANE2");
			gameFrame.gamePanel.add(gameFrame.player);
		} else if (playerPlane == "playerPlane3") {
			gameFrame.player = new PlayerPlane(gameFrame, "PLANE3");
			gameFrame.gamePanel.add(gameFrame.player);
		}
	}
	


}
