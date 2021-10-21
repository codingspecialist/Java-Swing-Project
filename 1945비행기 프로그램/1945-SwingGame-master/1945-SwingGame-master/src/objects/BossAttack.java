package objects;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BossAttack implements Runnable {
	// 이것도 시간이 없으니 일단 진행.. 불만이 많은 코드
	private frame.GameFrame gameFrame;
	private Boss boss;
	private PlayerPlane player;
	private boolean collision;

	Image bulletImg1 = new ImageIcon("images/bullet1.png").getImage();
	Image bulletImg2 = new ImageIcon("images/bullet2.png").getImage();
	Image bulletImg3 = new ImageIcon("images/bullet3.png").getImage();
	Image bulletImg4 = new ImageIcon("images/bullet4.png").getImage();
	Image bulletImg5 = new ImageIcon("images/missle.png").getImage();

	public int b1Width = bulletImg1.getWidth(null);
	public int b1Height = bulletImg1.getHeight(null);
	
	private double x;
	private double y;
	private double angel = 270; // 총알각도
	private double speed = 2; // 총알속도
	private int width;
	private int height;
	private boolean islife; //Thread를 삭제시키기 위한 구문

	public BossAttack(Boss boss, PlayerPlane player, double x, double y, double angel, double speed, int width,
			int height) {

		this.boss = boss;
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
		bulletthread.setName("Boss Bullet");
		bulletthread.start();
	}



	public frame.GameFrame getGameFrame() {
		return gameFrame;
	}



	public void setGameFrame(frame.GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}





	public Boss getBoss() {
		return boss;
	}



	public void setBoss(Boss boss) {
		this.boss = boss;
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



	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
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

		while (!player.getInvincible() && islife) { // 생명이 0보다 크면

			crash();

			try {
				if (collision) {
					explosePlayer(player); // 충돌 폭발 메서드
				}
				Thread.sleep(10);

				if (x > 1000 || x < -500 || y < -500 || y > 1000) {
					islife = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
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
			y=1000; //플레이어와 부딪히면 보스총알 사라짐
			islife = false;
			player.setInvincible(true); //무적상태
			
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
