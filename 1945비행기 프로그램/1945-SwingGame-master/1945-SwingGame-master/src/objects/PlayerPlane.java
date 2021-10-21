package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import frame.GameFrame;
import frame.GameTitle;

public class PlayerPlane extends JLabel {

	public PlayerPlane player = this;
	public final static String TAG = "Player: ";

	private GameFrame gameFrame;
	private EnemyUnit enemyUnit;
	private Boss boss;
	ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>(); // 총알피격시 객체를 담을 벡터

	private ImageIcon playerIcon; //기본 아이콘
	private ImageIcon playerInvincibleIcon; //무적상태시 아이콘

	private int width = 70;
	private int height = 65;
	private int x = (GameFrame.SCREEN_WIDTH / 2) - (width / 2);
	private int y = (GameFrame.SCREEN_HEIGHT - (height * 2));
	private int life = 3;
	private int pCount; // 총알 발사 속도
	private boolean invincible; // 무적상태
	
	private int wepponLevel = 0;
	public int select;// player 선택
	
	private boolean islife=true; //스레드 생명
	public boolean isRight = false;
	public boolean isLeft = false;
	public boolean isUp = false;
	public boolean isDown = false; // is 붙여라
	public boolean isAttack = false;
	public boolean isWepponLevelUp = false;

	public ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private PlayerAttack playerAttack;

	ArrayList<Integer> check = new ArrayList<>(); // 필요없는 총알인덱스 체크용

	public PlayerPlane(GameFrame gameFrame,String PLANE) {
		
		this.gameFrame = gameFrame;
		playerIcon = new ImageIcon("images/Player" + PLANE + ".png");
		playerInvincibleIcon = new ImageIcon("images/" + PLANE + "무적.png");

		this.invincible = false;

		System.out.println("images/Player" + PLANE + ".png");
		if (PLANE.equals("PLANE1")) {
			select = 1;
		}
		if (PLANE.equals("PLANE2")) {
			select = 2;
		}
		if (PLANE.equals("PLANE3")) {
			select = 3;
		}
		
		setIcon(playerIcon);
		move();
	}

	public boolean getIslife() {
		return islife;
	}

	public void setIslife(boolean islife) {
		this.islife = islife;
	}

	public PlayerPlane getPlayer() {
		return player;
	}

	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}


	public EnemyUnit getEnemyUnit() {
		return enemyUnit;
	}

	public boolean getInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void setEnemyUnit(EnemyUnit enemyUnit) {
		this.enemyUnit = enemyUnit;
	}

	public ArrayList<EnemyUnit> getEnemyUnitList() {
		return enemyUnitList;
	}

	public void setEnemyUnitList(ArrayList<EnemyUnit> enemyUnitList) {
		this.enemyUnitList = enemyUnitList;
	}

	public ImageIcon getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(ImageIcon playerIcon) {
		this.playerIcon = playerIcon;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getpCount() {
		return pCount;
	}

	public void setpCount(int pCount) {
		this.pCount = pCount;
	}

	public int getWepponLevel() {
		return wepponLevel;
	}

	public void setWepponLevel(int wepponLevel) {
		this.wepponLevel = wepponLevel;
	}

	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public boolean isAttack() {
		return isAttack;
	}

	public void setAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}

	public ArrayList<PlayerAttack> getPlayerAttackList() {
		return playerAttackList;
	}

	public void setPlayerAttackList(ArrayList<PlayerAttack> playerAttackList) {
		this.playerAttackList = playerAttackList;
	}

	public PlayerAttack getPlayerAttack() {
		return playerAttack;
	}

	public void setPlayerAttack(PlayerAttack playerAttack) {
		this.playerAttack = playerAttack;
	}

	public ArrayList<Integer> getCheck() {
		return check;
	}

	public void setCheck(ArrayList<Integer> check) {
		this.check = check;
	}

	public boolean isWepponLevelUp() {
		return isWepponLevelUp;
	}

	public ImageIcon getPlayerInvincibleIcon() {
		return playerInvincibleIcon;
	}

	public void setPlayerInvincibleIcon(ImageIcon playerInvincibleIcon) {
		this.playerInvincibleIcon = playerInvincibleIcon;
	}

	public void move() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				pCount = 0; // count를 0으로 설정
				while (islife) {
					try {
						Thread.sleep(5);
						gameOver();
						keyProcess();
						playerAttackProcess();
						PlayerBullet();
						setLocation(x, y); // repaint()
						setSize(width, height);
						pCount++; // 1씩 늘어난다
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t1.setName("Player");
		t1.start();
	}

	private void keyProcess() {
		if (!invincible && isAttack && pCount % 30 == 0) { // 총알의 발사 속도를 조절
			if (wepponLevel == 0) { // 총알 한줄만 발사
				// 총알이 생성되는 위치, 각도, 발사속도 조절
				playerAttack = new PlayerAttack(boss, x + 20, y - 40, 90, 2);
				playerAttackList.add(playerAttack); // arrayList에 저장한다
			}
			if (wepponLevel == 1) { // 총알 2줄 발사
				playerAttack = new PlayerAttack(boss, x + 10, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 30, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
			}
			if (wepponLevel == 2) { // 총알 3줄 발사
				playerAttack = new PlayerAttack(boss, x + 0, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 20, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 40, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
			}
			if (wepponLevel == 3) { // 총알 4줄 발사
				playerAttack = new PlayerAttack(boss, x - 10, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 10, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 30, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 50, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
			}
			if (wepponLevel == 4) { // 양 옆 대각선으로 나가는 총알 2줄 추가
				playerAttack = new PlayerAttack(boss, x - 15, y - 40, 80, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x - 10, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 10, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 30, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 50, y - 40, 90, 2);
				playerAttackList.add(playerAttack);
				playerAttack = new PlayerAttack(boss, x + 55, y - 40, 100, 2);
				playerAttackList.add(playerAttack);
			}
		}

		if (isUp) {
			y--;
		}
		if (isDown) {
			y++;
		}
		if (isLeft) {
			x--;
		}
		if (isRight) {
			x++;
		}
	}

	public void playerUpdate(Graphics g) { // 플레이어에 관한 모든 그림은 여기서 정리한다
		playerDraw(g);
	}

	public void playerDraw(Graphics g) {
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			if (select == 1) {
				g.drawImage(playerAttack.playerBulletImg1, (int) playerAttack.getX(), (int) playerAttack.getY(), null);
			}
			if (select == 2) {
				g.drawImage(playerAttack.playerBulletImg2, (int) playerAttack.getX(), (int) playerAttack.getY(), 20, 20,
						null);
			}
			if (select == 3) {
				g.drawImage(playerAttack.playerBulletImg3, (int) playerAttack.getX(), (int) playerAttack.getY(), 20, 20,
						null);
			}
			// PlayaerAttack의 자료형을 double로 두고, drawImage를 돌릴 때만 형변환 해준다 (삼각함수 계산을 위해)
		}
	}

	private void playerAttackProcess() {
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			playerAttack.Fire();
		}
	}

	public void setWepponLevelUp(boolean isWepponLevelUp) {
		this.isWepponLevelUp = isWepponLevelUp;
		if (isWepponLevelUp == true && wepponLevel < 6) {
			wepponLevel = wepponLevel + 1;
			System.out.println("무기 레벨 : " + wepponLevel);
			if (wepponLevel == 4) {
				System.out.println("무기 레벨이 최대입니다");
			} else if (wepponLevel == 5) {
				wepponLevel = 0;
			}
		}
	}

	public void contextAdd(EnemyUnit enemyUnit) { // 동적으로 생성된 적의 컨텍스트를 받아온다.

		this.enemyUnit = enemyUnit;
		if (enemyUnit != null)
			this.enemyUnitList.add(enemyUnit);
	}

	public void bossContextAdd(Boss boss) {
		if (boss != null) {
			this.boss = boss;
		}
	}

	// 충돌판정 쓰레드 종류
	// 적 총알이 내 비행기에 부딪쳤을 때 처리하는 쓰레드 1 , 내 비행기가 적 비행기랑 부딪쳤을 때 스레드2(에너미마다. 234567) .
	// 내 총알이 적 비행기에 부딪쳤을 때 3, 보스 총알이 내 비행기에 충돌시 스레드 4,

	// 맵 쓰레드 1개
	// 1. 그리는 기능, 2.플레이어 이동제한

	// 무브 쓰레드 종류
	// 1.enemy1,2,3,4,5,6 , Boss,
	//

	// 플레이어가 쏜 총알에 대한 처리.
	public void PlayerBullet() {
//		// 맵에서 벗어난 적의 객체를 탐지해 list에서 제거
//		for (int i = 0; i < this.enemyUnitList.size(); i++) {
//			if (enemyUnitList.get(i).y > 1000 && enemyUnitList.size() != 0) {
//				enemyUnitList.remove(i);
//			}
//		}
//		// 맵에서 벗어난 플레이어총알을 탐지해 list에서 제거
//		for (int i = 0; i < this.playerAttackList.size(); i++) {
//			if (playerAttackList.get(i).getY() < -100) {
//				playerAttackList.remove(i);
//			}
//		}

		// 플레이어가 쏜 총알에 적이 맞았을 때에 대한 처리.
		for (int i = 0; i < this.playerAttackList.size(); i++) {

			PlayerAttack bullet = this.playerAttackList.get(i);

			// 플레이어 총알이 일반 적 비행기에 맞을때 처리.
			for (int j = 0; j < this.enemyUnitList.size(); j++) {

				if (Crash((int) bullet.getX(), (int) bullet.getY(), enemyUnitList.get(j).x, enemyUnitList.get(j).y,
						bullet.getWidth(), bullet.getHeight(), enemyUnitList.get(j).width,
						enemyUnitList.get(j).height)) {

					System.out.println("하기 전: " + enemyUnitList.get(j).crushCheck);

					playerAttackList.remove(i); // 충돌판정이 맞으면, 총알 사라지고 적의 체력이 1 깍임
					i = i - 1; // 인덱스 에러해결하기 위해
					enemyUnitList.get(j).setLife(enemyUnitList.get(j).getLife() - 1);

					if (enemyUnitList.get(j).life == 0) { // 적의 체력이 다 깍이면 리스트에서 제거 후 폭파 연산
						// enemyUnitList.remove(j);
						System.out.println("적 hp 0 됨");
						check.add(j);
						enemyUnitList.get(j).crushCheck = true;

					}
				}

			}

			for (Integer j : check) { // 인덱스 에러가 안 뜨게
				enemyUnitList.remove(j);
			}

		}
	}

	// 플레이어 총알이 적의 비행기에 닿았는지 탐지하는 연산
	static boolean Crash(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2) {
		// x,y : 위치값 , w,h : 이미지의 높이와 길이.
		boolean result = false;
		if (Math.abs((x1 + w1 / 2) - (x2 + w2 / 2)) < (w2 / 2 + w1 / 2)
				&& Math.abs((y1 + h1 / 2) - (y2 + h2 / 2)) < (h2 / 2 + h1 / 2))
			result = true;
		else
			result = false;

		return result;
	}

	
	private void gameOver() {
		if(life<=0) {
			islife =false; //dispose 해도 안의 쓰레드는 살아있다...  이 명령 추가.. 그냥 완전 다 삭제해주는 함수는 없나...
			gameFrame.isgame = false;
			gameFrame.dispose(); 
			new GameFrame();
		}
		
	}
}
