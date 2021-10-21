package objects;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EnemyAttack implements Runnable {
	// 클래스 이름을 크게 Bullet이라 짓고 둘이 상속받는게 좋았을 듯, 일단 시간없으니 진행

	private EnemyAttack enemyAttack = this;

	private frame.GameFrame gameFrame;

	private EnemyUnit enemyunit;
	private PlayerPlane player;
	private boolean collision;

	Image bulletImg1 = new ImageIcon("images/bullet1.png").getImage();
	Image bulletImg2 = new ImageIcon("images/bullet2.png").getImage();
	Image bulletImg3 = new ImageIcon("images/bullet3.png").getImage();
	Image bulletImg4 = new ImageIcon("images/bullet4.png").getImage();
	Image bulletImg5 = new ImageIcon("images/missle.png").getImage();

	private int x;
	private int y;
	private double angel = 270; // 총알각도
	private double speed = 2; // 총알속도
	private int width;
	private int height;
	private boolean islife;

	public EnemyAttack(EnemyUnit enemyunit, PlayerPlane player, int x, int y, double angel, double speed, int width,
			int height) {

		this.enemyunit = enemyunit;
		this.player = player;
		this.x = x;
		this.y = y;
		this.angel = angel;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.islife = true;

		this.collision = false;

		Thread bulletthread = new Thread(this); // 총알 충돌 thread 생성, 실행
		bulletthread.setName("EnemyBullet");
		bulletthread.start();
	}

	public frame.GameFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(frame.GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public EnemyUnit getEnemyunit() {
		return enemyunit;
	}

	public void setEnemyunit(EnemyUnit enemyunit) {
		this.enemyunit = enemyunit;
	}

	public PlayerPlane getPlayer() {
		return player;
	}

	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getAngel() {
		return angel;
	}

	public void setAngel(double angel) {
		this.angel = angel;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void fire() { // 총알 발사
		x -= Math.cos(Math.toRadians(angel)) * speed;
		y -= Math.sin(Math.toRadians(angel)) * speed;
	}

	@Override
	public void run() {

		while (islife) {

			fire();

			if (x > 1000 || x < -500 || y < -500 || y > 1000) {
				// System.out.println("bullet thread terminate");
				islife = false; // Thread 종료구문
			}

			if (!player.getInvincible()) { // 무적상태가 아니면

				crash();

				try {
					if (collision) {
						explosePlayer(player); // 충돌 폭발 메서드
					}
					Thread.sleep(10);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void crash() { // 적 총알이 아군 비행기에 부딪쳤을 시 충돌연산
		if (Math.abs(
				((player.getX() - 11) + player.getWidth() / 3) - (x + width / 3)) < (width / 3 + player.getWidth() / 3)
				&& Math.abs(((player.getY() - 5) + player.getHeight() / 3) - (y + height / 3)) < (height / 3
						+ player.getHeight() / 3)) {
			collision = true;
		} else {
			collision = false;
		}
	}

	public void explosePlayer(PlayerPlane player) { // 충돌후 이미지 변경 및 목숨카운트

		try {
			ImageIcon explosionIcon = new ImageIcon("images/explosion.gif");
			player.setIcon(explosionIcon);
			// enemyAttack= null; //부딪칠시 적 총알 사라짐 안되네... 자바에서는 객체를 직접 제거하는 게 안되고
			y = 1000; // 가비지 컬렉션으로만 가능, 강제로 제거하려면 finallize 함수?

			islife = false;// 이게아닌가벼
			player.setInvincible(true); // 무적상태
			Thread.sleep(1000);

			player.setIcon(player.getPlayerInvincibleIcon());
			player.setLife(player.getLife() - 1);
			System.out.println("남은목숨:" + player.getLife());
			player.setX(200);
			player.setY(520);

			Thread.sleep(1500);

			player.setIcon(player.getPlayerIcon());
			player.setInvincible(false);

			player.repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
