package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy2 extends EnemyUnit {

	private Enemy2 enemy2 = this;
	private static final String TAG = "Enemy2 : ";

	ArrayList<EnemyAttack> enemyAttackkList = new ArrayList<EnemyAttack>();
	private EnemyAttack enemyAttack;

	public Enemy2(PlayerPlane player, int x, int y, int w, int h) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = new ImageIcon("images/enemy2.png").getImage();
		this.life = 10;
		this.crushCheck = false;
		this.islife = true;

		this.player.contextAdd(enemy2);

		if (x < 100) {
			this.move();
			this.crush();

		} else {
			this.move2();
			this.crush();
		}

	}

	public void move() {
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				while (islife) {
					try {
						Thread.sleep(5);

						if (y > 100) {
							y--; // 속도는 여기서 조절하면 됨
							moveup();
							moveright();
						}
						bulletCreate();
						count++;

						if (y > 900) {
							System.out.println("enemy2 쓰레드 종료");
							islife = false;
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t2.setName("enemy2Move");
		t2.start();
	}

	public void move2() {
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				while (islife) {
					try {
						Thread.sleep(5);

						y--;

						if (y > 100) {
							moveup();
							moveleft();
						}

						if (y < -300) {
							System.out.println("enemy2 쓰레드 종료");
							islife =false; //break 말고 이게 더 보기 좋은 거 같음.
						}

						bulletCreate();
						//enemyAttack();
						count++;

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t2.setName("enemy2Move");
		t2.start();
	}

	public void crush() { // 적비행기-Player 충돌

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (!player.getInvincible() && y < 900 && y > -300) {

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
							explosePlayer(player, enemy2); // 충돌 폭발 메서드
						}
						if (crushCheck) {
							explosePlayer(enemy2); // 플레이어 총알이 적기 충돌시
						}
						Thread.sleep(10);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	private void bulletCreate() {
		if (count % 100 == 0) {
			enemyAttack = new EnemyAttack(enemy2, player, x + 20, y + 40, 300, 2, 30, 30);
			enemyAttackkList.add(enemyAttack);

			enemyAttack = new EnemyAttack(enemy2, player, x + 80, y + 40, 1300, 2, 30, 30);
			enemyAttackkList.add(enemyAttack);

		}
	}

	public void enemyUpdate(Graphics g) {
		enemyDraw(g);
	}

//	private void enemyAttack() { //이제 안쓰는 메서드
//		for (int i = 0; i < enemyAttackkList.size(); i++) {
//			enemyAttack = enemyAttackkList.get(i);
////			if (enemy2.life > 0)  //발사는 여기서 구현하면 곤란
////				enemyAttack.fire();
//		}
//	}

	public void enemyDraw(Graphics g) { // 그림그리기
		g.drawImage(image, x, y, width, height, null);
		for (int i = 0; i < enemyAttackkList.size(); i++) {
			enemyAttack = enemyAttackkList.get(i);
			g.drawImage(enemyAttack.bulletImg2, enemyAttack.getX(), enemyAttack.getY(), enemyAttack.getWidth(),
					enemyAttack.getHeight(), null);

		}
	}

}
