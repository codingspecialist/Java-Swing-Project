package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy1 extends EnemyUnit {
	// 단순히 밑으로만 이동하는 유닛
	private Enemy1 enemy1 = this;
	private static final String TAG = "Enemy1 : ";

	static ArrayList<EnemyAttack> enemyAttackkList = new ArrayList<EnemyAttack>();
	private EnemyAttack enemyAttack;

	public Enemy1(PlayerPlane player, int x, int y, int w, int h) {

		this.player = player;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = new ImageIcon("images/enemy1.png").getImage();
		this.life = 1;
		this.crushCheck = false;
		this.islife = true;

		this.player.contextAdd(enemy1); // 동적으로 생성때마다 컨텍스트 플레이어에게 넘기기

		this.move();
		this.crush(); // 충돌탐지용
	}

	public void move() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (islife) {
					try {
						Thread.sleep(2);

						movedown();

						if (y > 900) {
							System.out.println("enemy1MoveThread 종료");
							islife = false;
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t1.setName("enemy1Move");
		t1.start();

	}

	public void crush() { // 적비행기-Player 충돌

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				while (!player.getInvincible() && y < 900) {

					if (Math.abs((player.getX() + player.getWidth() / 2) - (x + player.getWidth() / 2)) < (width / 2
							+ player.getWidth() / 2)
							&& Math.abs((player.getY() + player.getHeight() / 2) - (y + height / 2)) < (height / 2
									+ player.getHeight() / 2)) {
						collision = true;
					} else {
						collision = false;
					}

					try {
						if (collision) {
							explosePlayer(player, enemy1); // 플레이어와 적기 충돌시
						}

						if (crushCheck) {
							explosePlayer(enemy1); // 플레이어 총알이 적기 충돌시
						}
						Thread.sleep(10);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		});

		t1.setName("enemy1Crash");
		t1.start();

	}

	public void enemyUpdate(Graphics g) {
		enemyDraw(g);
	}

	public void enemyDraw(Graphics g) { // 그림그리기
		g.drawImage(image, x, y, width, height, null);

	}

}
