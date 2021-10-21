package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import frame.GameFrame;

public class Boss {

	private Boss boss = this;
	private static final String TAG = "Boss : ";

	private PlayerPlane player;
	ImageIcon icBoss = new ImageIcon("images/bossSizeup.gif");
	Image imgBoss = icBoss.getImage();

	private int x;
	private int y;
	private int width = imgBoss.getWidth(null);
	private int height = imgBoss.getHeight(null);
	private int life;
	
	private double way;

	private int bCount;
	boolean firePase = false;

	private ArrayList<BossAttack> bossAttackList = new ArrayList<BossAttack>();
	private GameFrame gameFrame;
	private BossAttack bossAttack;

	public Boss(PlayerPlane player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.life=100;
		
		player.bossContextAdd(boss); //플레이어에게 보스 객체를 넘김
		
		this.move();
	}

	
	
	
	
	public Boss getBoss() {
		return boss;
	}





	public void setBoss(Boss boss) {
		this.boss = boss;
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





	public int getLife() {
		return life;
	}





	public void setLife(int life) {
		this.life = life;
	}





	public ArrayList<BossAttack> getBossAttackList() {
		return bossAttackList;
	}





	public void setBossAttackList(ArrayList<BossAttack> bossAttackList) {
		this.bossAttackList = bossAttackList;
	}





	public GameFrame getGameFrame() {
		return gameFrame;
	}





	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}





	public BossAttack getBossAttack() {
		return bossAttack;
	}





	public void setBossAttack(BossAttack bossAttack) {
		this.bossAttack = bossAttack;
	}





	public void move() {
		Thread bossThread = new Thread(new Runnable() {
			@Override
			public void run() {
				bCount = 0;
				while (true) {
					try {
						Thread.sleep(5);
						bossAttackProcess();
						bulletCreate();
						bCount++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		bossThread.setName("Boss");
		
		bossThread.start();
	}

	
	private void bulletCreate() {
		if (bCount % 200 == 0) {
			if(firePase = false) {
				firePase = true;
				for (int j = 1; j <= 5; j++) {
					way = 180 + (30 * j);
					bossAttack = new BossAttack(boss, player, x + 80, y + 600, way, 0.5, 0, 0); // 총알이 생성되는																											// 위치
					bossAttackList.add(bossAttack); // arrayList에 저장한다
				}

				for (int j = 1; j <= 5; j++) {
					way = 180 + (30 * j);
					bossAttack = new BossAttack(boss, player, x + 480, y + 600, way, 0.5, 0, 0); // 총알이 생성되는 위치
					bossAttackList.add(bossAttack); // arrayList에 저장한다
				}
			}
			if(firePase = true) {
				firePase = false;
				for (int j = 1; j <= 7; j++) {
					way = 180 + (25 * j);
					bossAttack = new BossAttack(boss, player, x + 80, y + 600, way, 0.5, 0, 0); // 총알이 생성되는																											// 위치
					bossAttackList.add(bossAttack); // arrayList에 저장한다
				}

				for (int j = 1; j <= 7; j++) {
					way = 180 + (25 * j);
					bossAttack = new BossAttack(boss, player, x + 480, y + 600, way, 0.5, 0, 0); // 총알이 생성되는 위치
					bossAttackList.add(bossAttack); // arrayList에 저장한다
				}
			}
		}
		
		if (bCount % 600 == 0) {
			for (int j = 1; j <= 7; j++) {
				way = 180 + (25 * j);
				bossAttack = new BossAttack(boss, player, x + 280, y + 400, way, 1, 0, 0); // 총알이 생성되는																											// 위치
				bossAttackList.add(bossAttack); // arrayList에 저장한다
			}
		}
	}

	public void bossUpdate(Graphics g) {
		bossDraw(g);
	}

	public void bossAttackProcess() {
		for (int i = 0; i < bossAttackList.size(); i++) {
			bossAttack = bossAttackList.get(i);
			bossAttack.fire();

			// 총알이 벽과 충돌하면 사라짐
			if (bossAttack.getX() < -10 || bossAttack.getX() > gameFrame.SCREEN_WIDTH || bossAttack.getY() < -10 || bossAttack.getY() > gameFrame.SCREEN_HEIGHT) {
				bossAttackList.remove(bossAttack);
			}
		}
	}

	
	public void bossDraw(Graphics g) {
		g.drawImage(imgBoss, x, y, width, height, null);
		for (int i = 0; i < bossAttackList.size(); i++) {
			bossAttack = bossAttackList.get(i);
			g.drawImage(bossAttack.bulletImg1, (int) bossAttack.getX(), (int) bossAttack.getY(), bossAttack.b1Width/2, bossAttack.b1Height/2, null);
			// PlayaerAttack의 자료형을 double로 두고, drawImage를 돌릴 때만 형변환 해준다 (삼각함수 계산을 위해)
		}
	}
}
