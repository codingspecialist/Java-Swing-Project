package frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import objects.PlayerPlane;

public class GameFrame extends JFrame implements screenSize {

	private GameFrame gameFrame = this;
	public boolean isgame; // 게임실행 여부
	public GamePanel gamePanel; // 인게임 패널 이거 잘 봐야된다. 오류 !!
	public GameTitle gameTitle; // 타이틀 인트로 패널
	public SelectAPI selectAPI; // 선택 패널
	public PlayerPlane player; // 플레이어 선언

	public GameFrame() {
		init();
		setting();
		listener();

		setVisible(true);
	}

	public void init() {
		change("gameTitle"); // 초기 타이틀 화면
		isgame = false; // 게임 중 이지 않은 상태
	}

	public void setting() {
		setTitle("Strikers 1945");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// 패널 바꾸기 함수
	public void change(String panelName) {
		if (panelName.equals("gameTitle")) {
			gameTitle = new GameTitle(gameFrame);
			getContentPane().removeAll();
			getContentPane().add(gameTitle);
			revalidate();
			repaint();
		} else if (panelName.equals("selectAPL")) {
			selectAPI = new SelectAPI(gameFrame);
			getContentPane().removeAll();
			getContentPane().add(selectAPI);
			revalidate();
			repaint();
		} else if (panelName.equals("gameMap")) {
			gamePanel = new GamePanel(gameFrame);
			getContentPane().removeAll();
			getContentPane().add(gamePanel);
			revalidate();
			repaint();
		} else {
			gameTitle = null;
			selectAPI = null;
			gamePanel = null;
			isgame = false;
			getContentPane().removeAll();
			revalidate();
			repaint();
		}
	}

	public void listener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					player.setWepponLevelUp(true);
					break;
				case KeyEvent.VK_ENTER:
					change("selectAPL");
					break;
				case KeyEvent.VK_SPACE:
					player.setAttack(true);
					break;
				case KeyEvent.VK_UP:
					player.setUp(true);
					break;
				case KeyEvent.VK_DOWN:
					player.setDown(true);
					break;
				case KeyEvent.VK_LEFT:
					player.setLeft(true);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(true);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					player.setWepponLevelUp(false);
					break;
				case KeyEvent.VK_SPACE:
					player.setAttack(false);
					break;
				case KeyEvent.VK_UP:
					player.setUp(false);
					break;
				case KeyEvent.VK_DOWN:
					player.setDown(false);
					break;
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
		});
	}

}
