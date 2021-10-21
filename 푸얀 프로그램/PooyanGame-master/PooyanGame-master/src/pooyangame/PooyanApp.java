package pooyangame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PooyanApp extends JFrame implements Initable {

	private PooyanApp pooyanApp = this;

	private static final String TAG = "PooyanApp : ";
	

	private JLabel laBackground;
	private Wolf wolf;
	private Pooyan pooyan;
	
	public ArrayList<Wolf> wolves;
	public int floor = 0;
	public int count = 0;
	public JLabel laRemainWolf;
	public JLabel laLife;
	
	public int remainWolf = 32;

	
	public JLabel laScore;
	public int score = 0;

	public int randTime; // 늑대 생성 간격 랜덤 시간
	public int randWolf; // 늑대 생성 수 랜덤

	public boolean gameStatus = true;

	public PooyanApp() {
		init();
		setting();
		batch();
		listener();
		


		setVisible(true);
	}

//		public static void main(String[] args) {
//			new PooyanApp();
//		}

	@Override
	public void init() {
		laBackground = new JLabel(new ImageIcon("images/background.png"));
		wolves = new ArrayList<Wolf>();
		pooyan = new Pooyan(pooyanApp, wolf);
		laRemainWolf = new JLabel();
		laScore = new JLabel();
		
		laLife = new JLabel();

	}

	@Override
	public void setting() {
		setTitle("Pooyan");
		setSize(620, 630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setContentPane(laBackground);

		laRemainWolf.setText("" + remainWolf);
		laRemainWolf.setSize(30, 30);
		laRemainWolf.setLocation(10, 10);
		laRemainWolf.setFont(new Font("Serif", Font.BOLD, 30));
		laRemainWolf.setForeground(Color.WHITE);

		laScore.setText("" + pooyan.score);
		laScore.setSize(100, 30);
		laScore.setLocation(500, 0);
		laScore.setFont(new Font("Serif", Font.BOLD, 30));
		laScore.setForeground(Color.WHITE);
		
		laLife.setText("❤ ❤");
		laLife.setFont(new Font("Serif", Font.BOLD, 30));
		laLife.setForeground(Color.WHITE);
		//laLife.setLocation(400,0);
		laLife.setBounds(530, 20, 70, 30);
	}

	@Override
	public void batch() {
		add(pooyan);
		wolfAdd(); // 늑대 생성
		getContentPane().add(laRemainWolf);
		getContentPane().add(laScore);
		add(laLife);
	}

	@Override
	public void listener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (pooyan.pooyanStatus == true) {
						pooyan.moveUp();
					}
					
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (pooyan.pooyanStatus == true) {
						pooyan.moveDown();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (pooyan.pooyanStatus == true) {
						pooyan.isShoot = true;
						pooyan.shoot();
					}	

				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					pooyan.isUp = false;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					pooyan.isDown = false;
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					System.out.println("keyReleased");
					pooyan.isShoot = false;
//						pooyan.isItem = false;
					if(pooyan.pooyanStatus == true) {
						pooyan.shoot();

					}
				}
			}

		});

	}

	public void wolfAdd() {
		new Thread(new Runnable() {
			public void run() {
				while (remainWolf > 0 && gameStatus==true) {
					try {
						randWolf = (int) (Math.random() * 3) + 2;
						for (int i = 0; i < randWolf; i++) {
							if(gameStatus==false) {
								reset();
								break;
							}
							wolves.add(new Wolf(pooyanApp, pooyan));
							getContentPane().add(wolves.get(count));
							count = wolves.size();
							System.out.println(TAG + " 늑대 " + count);
							Thread.sleep(1000);
						}
						randTime = (int) (Math.random() * (3000 - 1000 + 1)) + 1000;
						Thread.sleep(randTime);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}

	// 플레이어가 죽었을때 리셋
	public void reset() {
		for (int i = 0; i < wolves.size(); i++) {

			remove(wolves.get(i));
			wolves.get(i).wolfStatus = false;
		}
		System.out.println(TAG+"pooyan.life"+pooyan.life);
		wolves.clear();
		if (pooyan.life == 1) {
			laLife.setText("❤");
			
			System.out.println(TAG+"pooyan.life"+pooyan.life);

		} else if (pooyan.life == 0) {
			remove(pooyan.jpPlayer);
			laLife.setText("");
			System.out.println(TAG+"pooyan.life"+pooyan.life);
			repaint();

		}
		repaint();
		count = 0;
		floor = 0;
		pooyan.x = 486;
		pooyan.y = 130;
		pooyan.jpPlayer.setLocation(pooyan.x, pooyan.y);
		
		
	}
	
	// 플레이어가 목숨을 다 잃었거나 목표치의 wolf를 죽였을때 게임엔드
	public void gameEnd() {
		gameStatus = false;
		reset();
		score = pooyan.score;
		System.out.println(TAG+gameStatus);
		new ScoreFrame(pooyanApp);
	}
}
