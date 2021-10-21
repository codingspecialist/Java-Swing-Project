package frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Boss;
import objects.Enemy1;
import objects.Enemy2;
import objects.Enemy3;
import objects.Enemy4;
import objects.Enemy5;
import objects.Enemy6;
import objects.EnemyUnit;
import objects.PlayerPlane;

public class GamePanel extends JPanel { // 인게임 패널

	private GamePanel gamePanel = this;
	private GameFrame gameFrame;

	private EnemyUnit enemyUnit;
	private Boss boss; // 보스 선언

	private JLabel laLifecount, laLifecount2, laLifecount3; // lifecount 라벨
	private ImageIcon lifeCounticon;

	private ImageIcon stageIcon = new ImageIcon("images/Stage.png"); // 배경 이미지 아이콘
	private Image stageImg = stageIcon.getImage(); // 배경 이미지

	private ImageIcon bossStageIcon = new ImageIcon("images/vsBossStage.png"); // 보스 스테이지 배경 아이콘
	private Image bossStageImg = bossStageIcon.getImage(); // 보스 스테이지 이미지

	private ImageIcon titleIcon = new ImageIcon("images/GameTitle.gif");
	private Image titleImg = titleIcon.getImage();

	int stageY = -(stageImg.getHeight(null) - bossStageImg.getHeight(null)); // 배경 이미지의 Y좌표
	int bossStageBY1 = -(stageImg.getHeight(null)); // 보스 스테이지 이미지 1의 Y좌표
	int bossStageBY2 = -(stageImg.getHeight(null) + bossStageImg.getHeight(null)); // 보스 스테이지 이미지 2의 Y좌표
	int appear = 1; // 적 비행기 출현 위치를 정하기 위해 선언

	Vector<EnemyUnit> enemyUnits = new Vector<>(); // 적 유닛을 모아놓을 배열

	public GamePanel(GameFrame gameFrame) {

		this.gameFrame = gameFrame;
		
		lifeLaInit();
		gamePanel.add(laLifecount);
		gamePanel.add(laLifecount2);
		gamePanel.add(laLifecount3);
		laLifecount.setBounds(0, 0, 50, 50);
		laLifecount2.setBounds(50, 0, 50, 50);
		laLifecount3.setBounds(100, 0, 50, 50);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				gameFrame.isgame = true;
				while (gameFrame.isgame) {
					setLayout(null);
					

					stageY++;
					bossStageBY1++;
					bossStageBY2++;

					if (stageY > bossStageImg.getHeight(null)) {
						stageY = bossStageImg.getHeight(null);
						if (bossStageBY1 > (bossStageImg.getHeight(null) - 1)) {
							bossStageBY1 = -(bossStageImg.getHeight(null) - 1);
						}
						if (bossStageBY2 > (bossStageImg.getHeight(null) - 1)) {
							bossStageBY2 = -(bossStageImg.getHeight(null) - 1);
						}
					}
					try {
						lifeCounting();
						enemyBatch();
						crushBorder();
						appear++;
						repaint();
						Thread.sleep(3);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(stageImg, 0, stageY, null);
		g.drawImage(bossStageImg, 0, bossStageBY1, null);
		g.drawImage(bossStageImg, 0, bossStageBY2, null);

		for (int i = 0; i < enemyUnits.size(); i++) { // null이 아니면 그려라
			if (enemyUnits.get(i) != null) {
				enemyUnits.get(i).enemyUpdate(g);
			}
		}

		if (gameFrame.player != null) {
			gameFrame.player.playerUpdate(g);
		}

		if (boss != null) {
			boss.bossUpdate(g);
		}

		repaint();
	}

	private void lifeLaInit() { // 당장 안 써요. 괜히 만들어달라 했나..
		lifeCounticon = new ImageIcon("images/LifeCount.png");
		laLifecount = new JLabel(lifeCounticon);
		laLifecount2 = new JLabel(lifeCounticon);
		laLifecount3 = new JLabel(lifeCounticon);

	}

	public void lifeCounting() {
		if (gameFrame.player.getLife() == 3) {
			laLifecount.setVisible(true);
			laLifecount2.setVisible(true);
			laLifecount3.setVisible(true);
			repaint();
		} else if (gameFrame.player.getLife() == 2) {
			laLifecount.setVisible(true);
			laLifecount2.setVisible(true);
			laLifecount3.setVisible(false);
			repaint();
		} else if (gameFrame.player.getLife() == 1) {
			laLifecount.setVisible(true);
			laLifecount2.setVisible(false);
			laLifecount3.setVisible(false);
			repaint();
		} else {
			laLifecount.setVisible(false);
			laLifecount2.setVisible(false);
			laLifecount3.setVisible(false);
			repaint();
		}
	}

	public void crushBorder() { // 벽에 충돌하는 조건함수 >> Map 스레드 안에 적용
		if (gameFrame.player.getX() <= 0) {
			gameFrame.player.setX(0);
			repaint();
		} else if (gameFrame.player.getX() >= 550) {
			gameFrame.player.setX(550);
			repaint();
		}
		if (gameFrame.player.getY() <= 0) {
			gameFrame.player.setY(0);
			repaint();
		} else if (gameFrame.player.getY() >= 720) {
			gameFrame.player.setY(720);
			repaint();
		}
	}

	public void enemyBatch() { // 적기 맵에 배치

		if (appear == 5000) {
			enemyUnits.add(new Enemy2(gameFrame.player, -100, 300, 150, 150)); // 而⑦뀓�뒪�듃 �꽆湲곌린
			enemyUnits.add(new Enemy2(gameFrame.player, 500, 300, 150, 150));
		}

		if (appear == 1000 || appear == 3000) {
			enemyUnits.add(new Enemy1(gameFrame.player, 50, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 100, -50, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 150, -100, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 200, -150, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 250, -200, 50, 50));
		}

		if (appear == 2000 || appear == 4000) {
			enemyUnits.add(new Enemy1(gameFrame.player, 500, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 450, -50, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 400, -100, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 350, -150, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 300, -200, 50, 50));
		}

		if (appear == 500 || appear == 1500 || appear == 3500 || appear == 5000 || appear == 6000) {
			enemyUnits.add(new Enemy3(gameFrame.player, 600, -200, 100, 100)); // 컨텍스트 넘기기
			enemyUnits.add(new Enemy4(gameFrame.player, 0, 0, 100, 100));
		}

		if (appear == 6000) {
			enemyUnits.add(new Enemy5(gameFrame.player, 300, -50, 100, 100));
			enemyUnits.add(new Enemy5(gameFrame.player, 500, -50, 100, 100));
		}

		if (appear == 7000) {
			enemyUnits.add(new Enemy6(gameFrame.player, 650, 400, 200, 200));

		}


		if (appear == 8000) {
			enemyUnits.add(new Enemy2(gameFrame.player, 100, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 100, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 200, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 300, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 400, 0, 50, 50));
			enemyUnits.add(new Enemy1(gameFrame.player, 500, 0, 50, 50));
		}

		if (appear == 10000) {
			enemyUnits.removeAllElements();
			boss = new Boss(gameFrame.player, 0, -300);
		}
	}

}
