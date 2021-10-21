package jumpKing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

// 점프킹앱 클래스입니다.
// 플레이어를 생성하고 조작하는역할을합니다.
// 픽셀체크와, 공주를 생성합니다.
// 공주와 플레이어와 충돌시 패널을 출력합니다.

public class JumpKingApp extends JFrame implements Initable {

	// 컨텍스트 저장
	private JumpKingApp jumpKingApp = this; // 버블 컨텍스트 남기기
	// 태그
	private static final String TAG = "JumpKing : ";

	public Player player; // 플레이어
	public Princess princess; // 플레이어
	private Thread thPixel; // 픽셀검사
	private BgJumpKing bgPanel; // 백그라운드

	private ImageIcon[] icon = { null, new ImageIcon("images/1StageC.png"), new ImageIcon("images/2StageC.png"),
			new ImageIcon("images/3StageC.png") };
	private int imgCount = 1;
	private Image img = icon[imgCount].getImage(); // 이미지 객체

	private Thread laThread;
	
	//프레임 관련
	private DefalutPanel defalutPanel;
	private JLabel laPrincess;
	private JLabel la1Stage, la2Stage, la3Stage;
	private boolean princessAdd = false;

	public JumpKingApp() {
		init(); // 생성 객체모음
		
		setting(); // 셋팅 모음
		batch(); // 배치 모음
		listener(); // 리스너 모음
		
		
		setVisible(true);

	}

	class BgJumpKing extends JLabel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);

		}

		public BgJumpKing() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						img = icon[imgCount].getImage();
						repaint();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	}

	@Override
	public void init() {

		player = new Player();
		princess = new Princess();
		bgPanel = new BgJumpKing();
		defalutPanel = new DefalutPanel(jumpKingApp);
		
		laPrincess = new JLabel("공주");
		la1Stage = new JLabel("----------1Stage----------");
		la2Stage = new JLabel("----------2Stage----------");
		la3Stage = new JLabel("----------3Stage----------");
		thPixel = new Thread(new PixelCheck(player, jumpKingApp)); // 색깔 연산 스레드
		thPixel.start();
		laThread = new Thread(new StageChange());
		laThread.start();

	}

	@Override
	public void setting() {
		setTitle("점프킹");
		setSize(1080, 607);
		
		defalutPanel.setBounds(190, 300, 700, 200);
		defalutPanel.getLaName().setText("공주");
		defalutPanel.getJtaContent().setText("구해주셔서감사합니다");
		
		la1Stage.setBounds(450, 80, 300, 400);
		la1Stage.setForeground(Color.white);
		la1Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la2Stage.setBounds(450, 80, 300, 400);
		la2Stage.setForeground(Color.white);
		la2Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la3Stage.setBounds(450, 80, 300, 400);
		la3Stage.setForeground(Color.white);
		la3Stage.setFont(new Font("Serif", Font.BOLD, 22));
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setContentPane(bgPanel); // 기본 컨텐트페인 = 라벨 백그라운드
	}

	@Override
	public void batch() {
		add(player);
		add(defalutPanel);
		defalutPanel.setVisible(false);
	

	}

	@Override
	public void listener() {
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (player.isMoveLock() == true) { // Move락이 걸려있으면 메서드실행안됨
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // 오른쪽 이동
					player.setJumpUpDirectionStay(true); // 제자리 점프시 우측 방향 설정 (우측 이미지 때문에 사용)
					player.moveRight();
//					System.out.println(player.getPlayerX() +" a "+ player.getPlayerY());
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽이동
					player.setJumpUpDirectionStay(false); // 제자리 점프시 좌측 방향 설정 (좌측 이미지 때문에 사용)
					player.moveLeft();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.isLeft() == true) { // 좌측 위 점프
					player.setJumpUpDirection(-1);// 좌측 방향값 설정
					player.jumpUp();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.isRight() == true) { // 우측 위 점프
					player.setJumpUpDirection(1); // 우측 방향값 설정
					player.jumpUp();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {// 제자리 점프
					player.jumpUp();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { // 버튼땔때 멈추는 함수
				if (e.getKeyCode() == KeyEvent.VK_SPACE) // UP버튼 연속입력 방지
					player.setUp(false);
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) // 좌,우이동 매끄러운 변환 조건문
					player.setRight(false);
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					player.setLeft(false);
			}
		});
	}

	class StageChange implements Runnable {
		int clear = 0; //공주 한번만 실행

		@Override
		public void run() {
			boolean lacount = true;
			while (true) {
				try {
					if (imgCount == 1 && lacount == true) {
						System.out.println("스테이지1");
						lacount = false;
						add(la1Stage);
						Thread.sleep(2000);
					} else if (imgCount == 2 && lacount == false) {
						System.out.println("스테이지22");
						lacount = true;
						add(la2Stage);
						Thread.sleep(2000);
					} else if (imgCount == 3 && lacount == true) {
						System.out.println("스테이지3");
						lacount = false;
						add(la3Stage);
						Thread.sleep(3000);
					}
				} catch (Exception e) {
				}
				if (player.getX() < (princess.getX()+25) && (princess.getX()+25) < (player.getX() + 55)
						&& (player.getY()-20) < (princess.getY()+50) && (princess.getY()+50) < (player.getY() + 70)&&
						clear == 0 && imgCount==3) {
					System.out.println("공주사마!!");
					defalutPanel.setVisible(true);
					clear = 1;
				}				
				remove(la1Stage);
				remove(la2Stage);
				remove(la3Stage);
			}
		}
	}

	public void creatPrincess() { // 공주만들기
		add(princess);
	}

	public void removePrincess() { // 공주제거
		remove(princess);
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}
	
	

	public static void main(String[] args) {
		new JumpKingApp();
	}
}
