package pooyangame;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Wolf extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private final static String TAG = "Wolf : ";

	private PooyanApp pooyanApp;
	private Pooyan pooyan;
	private int floor = 0;

	private ImageIcon iconWolfM4, iconWalkWolfR, iconAttackStayWolf, iconAttackStayWolfR, iconAttackWolf1,
			iconAttackWolf2, iconBallonMint, iconFallingWolf1, iconFallingWolf2, iconDieWolf;
	
	public Wolf wolf = this;
	public int x = 0;
	public int y = -30;

	public boolean isDown = false;
	public boolean isRight = false;
	public boolean isRightGround = false;
	public boolean isUp = false;
	public boolean isAttack = false;
	public boolean isBomb = false;
	public boolean keepBomb = false;

	public boolean wolfStatus = true;

	public Bomb bomb;

	public int rand;
	public int randKeepBomb;

	public Wolf(PooyanApp pooyanApp, Pooyan pooyan) {
		this.pooyanApp = pooyanApp;
		this.pooyan = pooyan;


		iconWolfM4 = new ImageIcon("images/WolfMint4.png");
		new ImageIcon("images/WolfMint5.png");
		iconWalkWolfR = new ImageIcon("images/walkWolfR.gif");
		iconAttackStayWolf = new ImageIcon("images/attackStayWolf.gif");
		iconAttackStayWolfR = new ImageIcon("images/attackStayWolfR.png");
		iconAttackWolf1 = new ImageIcon("images/attackWolf1.png");
		iconAttackWolf2 = new ImageIcon("images/attackWolf2.png");
		iconBallonMint = new ImageIcon("images/ballonMint.png");
		new ImageIcon("images/ballonMintPop1.png");
		new ImageIcon("images/ballonMintPop2.png");
		new ImageIcon("images/ballonMintPop3.png");
		iconFallingWolf1 = new ImageIcon("images/fallingWolf1.png");
		iconFallingWolf2 = new ImageIcon("images/fallingWolf2.png");
		iconDieWolf = new ImageIcon("images/dieWolf.png");
		new JLabel(iconBallonMint);

		setIcon(iconWolfM4);
		setSize(130, 130);
		setLocation(x, y);
		rand = (int) (Math.random() * 300) + 20;
		randKeepBomb = (int) (Math.random()*2)+1; // 1 ~ 3
		if(randKeepBomb != 1) {
			keepBomb = true;
		}
		moveRight();
		
	}

	public void bombAttack() {
		if(keepBomb == true) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					keepBomb = false;
					bomb = new Bomb();
					isBomb = true; 
					bomb.x = x +20;
					bomb.y = y + 40;
					bomb.setLocation(bomb.x, bomb.y);
					pooyanApp.add(bomb);
					while(isBomb) {
						bomb.x++;
						bomb.setLocation(bomb.x, bomb.y);
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(bomb.x >= 490) {
							if(bomb.y>=pooyan.y && bomb.y<=pooyan.y+40 && pooyan.pooyanStatus == true) {
								isBomb = false;
								pooyanApp.remove(bomb);
								pooyan.die();
								break;
							} else {
								if(bomb.x >= 530 ) {
									isBomb =false;
									pooyanApp.remove(bomb);
									repaint();
									break;
								}
							}
						}
						
					}
					
				}
			}).start();
		}
	}
	
	public void attackedFall() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						setIcon(iconFallingWolf1);
						y = y + 3;
						Thread.sleep(10);
						setIcon(iconFallingWolf2);
						y = y + 3;
						Thread.sleep(10);
						setLocation(x, y);
						if (y > 490) {
							setIcon(iconDieWolf);
							Thread.sleep(1000);
							pooyanApp.remove(wolf);
							pooyanApp.repaint();
							pooyanApp.wolves.remove(wolf);
							pooyanApp.count--;
							pooyanApp.remainWolf--;
							System.out.println(TAG + " " + pooyanApp.count);
							pooyanApp.laRemainWolf.setText("" + pooyanApp.remainWolf);
							if (pooyanApp.remainWolf <= 0) {
								pooyanApp.gameEnd();
								break;
							}
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	public void moveFall() {
//		System.out.println(TAG + "moveFall");

		if (isDown == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					isDown = true;
					setIcon(iconWolfM4);
					while (isDown) {
						try {
							if (y > 490) {
								isDown = false;
								isRightGround = true;
								wolf.moveRight();
								setIcon(iconWalkWolfR);
								break;
							}
							if (wolfStatus == false) {
								break;
							}
							y++;
							setLocation(x, y);
							if (pooyan.y == y + 30 && keepBomb == true && pooyan.pooyanStatus == true) {
								bombAttack();
								setIcon(iconWalkWolfR);
								Thread.sleep(200);
								setIcon(iconWolfM4);
							}
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}).start();
		}
	}

	public void moveRight() {
//		System.out.println(TAG + "moveRight");
		new Thread(new Runnable() {
			@Override
			public void run() {
				isRight = true;
				setIcon(iconWalkWolfR);
				while (isRight) {
					if (wolfStatus == false) {
						break;
					}
					if (y == -30) { // 위에서 이동
						if (x >= rand) {
							isRight = false;
							moveFall();
							break;
						}
						x++;
						setLocation(x, y);

						try {
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (y > 490 && wolfStatus == true) { // 밑에서 이동
						if (x > 540) {
							isRight = false;
							if (pooyanApp.floor < 4) {
								isUp = true;
								pooyanApp.floor = pooyanApp.floor + 1;
								floor = pooyanApp.floor;
								wolf.moveUP();
								break;
							} else {
								pooyanApp.remove(wolf);
								pooyanApp.wolves.remove(wolf);
								pooyanApp.repaint();
								pooyanApp.count--;
								System.out.println(TAG + "늑대 " + pooyanApp.wolves.size());
								break;
							}

						}
						x++;
						setLocation(x, y);
						try {
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public void moveUP() {
		System.out.println(TAG + "moveUp");

		new Thread(new Runnable() {
			@Override
			public void run() {
				setIcon(iconAttackStayWolf);
				while (isUp) {
					if (wolfStatus == false) {
						break;
					}
					if (floor == 1 && y < 400 && wolfStatus == true) {
						isUp = false;
						isAttack = true;
						wolf.attack();
						break;
					}
					if (floor == 2 && y < 320 && wolfStatus == true) {
						isUp = false;
						isAttack = true;
						wolf.attack();
						break;
					}
					if (floor == 3 && y < 230 && wolfStatus == true) {
						isUp = false;
						isAttack = true;
						wolf.attack();
						break;
					}
					if (floor == 4 && y < 150 && wolfStatus == true) {
						isUp = false;
						isAttack = true;
						wolf.attack();
						break;
					}
					y--;
					setLocation(x, y);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void attack() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				setIcon(iconAttackStayWolfR);
				while (isAttack) {
					try {
						System.out.println(TAG + "공격");
						Thread.sleep(5000); // 5초마다 공격
						setIcon(iconAttackWolf1);
						x = 500;
						setLocation(x, y);
						Thread.sleep(500); // 공격 모션 딜레이
						setIcon(iconAttackWolf2);
						// 플레이어 좌표가 울프의 근접공격 좌표와 같아지면 플레이어 죽음
						if (x <= pooyan.x + 50 && pooyan.pooyanStatus == true) {
							if (y + 30 >= pooyan.y && y + 30 <= pooyan.y + 50) {
//								pooyanApp.reset();
//								pooyan.life = pooyan.life - 1;
//								if(pooyan.life<0) {
//									pooyanApp.gameEnd();
//								}
								pooyan.die();
								break;
							}
						}
						if (wolfStatus == false) {
							break;
						}
						Thread.sleep(200); // 공격 모션 딜레이
						setIcon(iconAttackStayWolfR);
						x = 541;
						setLocation(x, y);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}
}
