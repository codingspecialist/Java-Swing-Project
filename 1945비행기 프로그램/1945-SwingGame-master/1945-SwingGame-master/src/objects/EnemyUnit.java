package objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class EnemyUnit extends JLabel { // public으로 선언해야지 다른 패키지에서 접근가능!

	protected int x;
	protected int y;
	protected PlayerPlane player;
	protected int width;
	protected int height;
	protected Image image;
	protected int life;
	protected boolean collision = false;// 플레이어와 적기몸체가 충돌시 체크
	protected boolean crushCheck; // 플레이어 총알이 적기에 맞았는지 체크용
	protected int count; // 총알 발사 속도조절
	protected boolean islife; // 스레드 생명

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isCrushCheck() {
		return crushCheck;
	}

	public void setCrushCheck(boolean crushCheck) {
		this.crushCheck = crushCheck;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void movedown() {
		y++;
		setLocation(x, y);
	}

	public void moveleft() {
		x--;
		setLocation(x, y); // repaint()

	}

	public void moveup() {
		y--;
		setLocation(x, y); // repaint()

	}

	public void moveright() {
		x++;
		setLocation(x, y); // repaint()

	}

	public abstract void move();

	public abstract void enemyUpdate(Graphics g);

	// 적기가 아군 비행기와 충돌시 수행
	public void explosePlayer(PlayerPlane player, EnemyUnit enemyUnit) { // 충돌후 이미지 변경 및 목숨카운트

		try {
			ImageIcon explosionIcon = new ImageIcon("images/explosion.gif");
			player.setIcon(explosionIcon);
			enemyUnit.image = explosionIcon.getImage();
			System.out.println("적기와 아군비행기 충돌");
			player.setInvincible(true); // 무적상태
			Thread.sleep(500);

			
			player.setIcon(player.getPlayerInvincibleIcon());
			player.setLife(player.getLife() - 1);
			System.out.println("남은목숨:" + player.getLife());
			player.setX(200);
			player.setY(520);
			Thread.sleep(1500);

			player.setIcon(player.getPlayerIcon());
			player.setInvincible(false);
			enemyUnit.y = 1000; // 맵 바깥으로 적 던짐
			player.repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void explosePlayer(EnemyUnit enemyUnit) { // 적기가 아군총알에 충돌시 구현, 오버로딩

		try {
			ImageIcon explosionIcon = new ImageIcon("images/explosion.gif");
			enemyUnit.image = explosionIcon.getImage();

			System.out.println("적기와 아군비행기 총알 충돌");
			Thread.sleep(1000);
			enemyUnit.y = 1000; // Thread 강제종료 방법이 마땅히 안 떠오름 대충 이렇게

			enemyUnit.repaint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
