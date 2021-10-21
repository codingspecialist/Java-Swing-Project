package pooyangame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pooyan extends JPanel {
	public Pooyan pooyan = this;
//	public ArrayList<Arrow> listArrow;
	private Meat meat;
	private final static String TAG = "Pooyan : ";
	private Color transparency;

	private ImageIcon icElevator, icAttackBow, icAttackPy, icAttackMeatPy, icFallingPy, icDiePy;
	private JLabel laElevator, laAttackBow, laAttackPy, laAttackMeatPy, laFallingPy, laDiePy;
	private PooyanApp pooyanApp;
	private Wolf wolf;
	
	public JPanel jpPlayer ,jpDie;

	public boolean isUp = false;
	public boolean isDown = false;
	public boolean isShoot = false;
	public boolean isArrow = false;
	public boolean isItem = false;
	public boolean isMeat = false;
	//public boolean isDie = false;
	public boolean pooyanStatus = true;
	
	public int x = 486;
	public int y = 130;

	public int life = 2;

	public int arrowX = 486;
	public int arrowY = 130;
	public int meatX = 0;
	public int meatY = 0;
	public int dieX = 468;
	public int dieY = 130;

	public int g = 1; // 중력가속도
	public int meatVx = -15; // meat x축 초기 속도
	public int meatVy = 0; // meat y축 초기 속도
	public int score = 0;

	

	private void init() {

		icElevator = new ImageIcon("images/elevator.png");
		laElevator = new JLabel();

		icAttackBow = new ImageIcon("images/attackBowPy.png");
		laAttackBow = new JLabel();

		icAttackPy = new ImageIcon("images/attackPy.png");
		laAttackPy = new JLabel();

		icAttackMeatPy = new ImageIcon("images/attackMeatPy.png");
		laAttackMeatPy = new JLabel();

		icFallingPy = new ImageIcon("images/fallingPy.png");
		laFallingPy = new JLabel();

		icDiePy = new ImageIcon("images/diePy.png");
		laDiePy = new JLabel();
		
		transparency = new Color(255, 0, 0, 0);
		jpPlayer = new JPanel();

		jpDie = new JPanel();
		
//		listArrow = new ArrayList<Arrow>();
		meat = new Meat(pooyanApp, wolf, pooyan);

	}

	private void setting() {

		setSize(620, 630);
		setLayout(null);
		setOpaque(false);
		setBackground(transparency);

		laElevator.setSize(50, 80);
		laElevator.setIcon(icElevator);
		laElevator.setBounds(10, 0, 50, 80);

		laAttackBow.setIcon(icAttackBow);
		laAttackBow.setBounds(0, 20, 50, 50);

		laAttackPy.setIcon(icAttackPy);
		laAttackPy.setBounds(0, 20, 50, 50);
		laAttackPy.setVisible(false);

		laAttackMeatPy.setIcon(icAttackMeatPy);
		laAttackMeatPy.setBounds(0, 20, 50, 50);
		laAttackMeatPy.setVisible(false);

		laFallingPy.setIcon(icFallingPy);
		laFallingPy.setBounds(0, 20, 50, 50);
		laFallingPy.setVisible(false);

		laDiePy.setIcon(icDiePy);
		laDiePy.setBounds(0, 20, 50, 50);
		laDiePy.setVisible(false);
		
		jpPlayer.setLayout(null);
		jpPlayer.setSize(80, 80);
		jpPlayer.setOpaque(false);
		jpPlayer.setBackground(transparency);
		jpPlayer.setLocation(x, y);

		jpDie.setLayout(null);
		jpDie.setSize(80, 80);
		jpDie.setOpaque(false);
		jpDie.setBackground(transparency);
		jpDie.setLocation(x, y);
		
		meat.setLocation(496, 70);

		if (isItem == false) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (pooyanApp.gameStatus) {
						System.out.println(TAG + "meat 장착 쓰레드");
						if (isItem == false) {
							if (jpPlayer.getLocation().x == meat.getLocation().x - 10
									&& jpPlayer.getLocation().y == meat.getLocation().y + 30) {
								isItem = true;
								score = score + 200;
								meat.stack = 0;
								pooyanApp.laScore.setText("" + pooyan.score);
								meat.x = jpPlayer.getLocation().x;
								meat.y = jpPlayer.getLocation().y;
								meat.setLocation(meat.x, meat.y);

//							System.out.println("meat.getLocation.x" + meat.getLocation().x);
								meat.setVisible(false);
								laAttackBow.setVisible(false);
								laAttackMeatPy.setVisible(true);

							}
						}
						try {
							if (isItem == false) {
								meat.setVisible(false);
								Thread.sleep(800);
								meat.setVisible(true);
								Thread.sleep(1000);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}).start();

		}

	}

	private void batch() {
		jpPlayer.add(laElevator);
		jpPlayer.add(laAttackBow);
		jpPlayer.add(laAttackPy);
		jpPlayer.add(laAttackMeatPy);
		add(jpPlayer);
		add(meat);
		add(jpDie);
		jpDie.add(laDiePy);
		jpDie.add(laFallingPy);
	}

	public Pooyan(PooyanApp pooyanApp, Wolf wolf) {
		this.pooyanApp = pooyanApp;
		this.wolf = wolf;
		init();
		setting();
		batch();

	}

	public void die() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(pooyanStatus == true) {
					pooyanStatus = false;
					laAttackBow.setVisible(false);
					laAttackMeatPy.setVisible(false);
					laAttackPy.setVisible(false);
					laFallingPy.setVisible(true);
					dieX = jpPlayer.getLocation().x;
					dieY = jpPlayer.getLocation().y;
					while(life>-1) {
						dieX--;
						dieY++;
						jpDie.setLocation(dieX, dieY);
						try {
							Thread.sleep(5);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(dieY > 510) {
							laFallingPy.setVisible(false);
							laDiePy.setVisible(true);
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							laDiePy.setVisible(false);
							laAttackBow.setVisible(true);
							life = life - 1;
							pooyanApp.reset();
							pooyanStatus = true;
							if(life==-1) {
								pooyanApp.gameEnd();
								break;
							}
							break;
						}
					}
				}
			}
		}).start();
	}
	
	public void moveUp() {
		if (isUp == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					isUp = true;
					while (isUp) {
//						System.out.println(y);
						y--;
						if (y < 100) {
							y++;
							isUp = false;
						}
						jpPlayer.setLocation(x, y);
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}).start();
		}

	}

	public void moveDown() {
		if (isDown == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					isDown = true;
					while (isDown) {
//						System.out.println(y);
						y++;
						if (y > 413) {
							y--;
							isDown = false;

						}
						jpPlayer.setLocation(x, y);
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	public void shoot() {
		if (isShoot == true) {
			if (isItem == true) {

				if (isMeat == false) {
					isMeat = true;
					meat.x = jpPlayer.getLocation().x;
					meat.y = jpPlayer.getLocation().y;

					meatX = meat.x;
					meatY = meat.y;

					meat.setLocation(meatX, meatY);
					meat.setVisible(true);
					laAttackMeatPy.setVisible(false);

					new Thread(new Runnable() {

						@Override
						public void run() {

							while (true) {
								if (meatX > 400) {
									meatX = meatX + meatVx;
								} else {
									meatVy = meatVy + g; // 중력가속도에 의해 meatVy 점점 증가
									meatX = meatX + meatVx;
									meatY = meatY + meatVy;
								}
								meat.isKill = true;
								meat.kill();
								meat.setLocation(meatX, meatY);
								if (meatY > 490 || meatX < 0) {
									// meat.y++;
									meatVy = 0;
									meatX = 496;
									meatY = 70;
									meat.setLocation(meatX, meatY);
									isItem = false;
									isMeat = false;
									meat.isKill = false;
									break;
									// repaint();

								}
								try {
									Thread.sleep(30);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

						}
					}).start();
				}

			} else if (isArrow == false) {
//				listArrow.add(new Arrow(pooyanApp, wolf, pooyan));
				Arrow arrow = new Arrow(pooyanApp, wolf, pooyan);
				if (isItem == true) {
					isArrow = false;

				} else {

					isArrow = true;
				}
				if (isArrow == true) {
//					for (int i = 0; i < listArrow.size(); i++) {
//						if (listArrow.get(i).isIn == false) {
//							listArrow.get(i).x = jpPlayer.getLocation().x - 60;
//							listArrow.get(i).y = jpPlayer.getLocation().y + 50;
//
//							arrowX = listArrow.get(i).x;
//							arrowY = listArrow.get(i).y;
//
//							listArrow.get(i).setLocation(arrowX, arrowY);
//
//							add(listArrow.get(i));
//
//						}
//						listArrow.get(i).isIn = true;
//
//					}
					if (arrow.isIn == false) {
						arrow.x = jpPlayer.getLocation().x - 60;
						arrow.y = jpPlayer.getLocation().y + 50;

						arrowX = arrow.x;
						arrowY = arrow.y;

						arrow.setLocation(arrowX, arrowY);

						add(arrow);

					}
					arrow.isIn = true;
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							while (true) {
//								for (int i = 0; i < listArrow.size(); i++) {
//									listArrow.get(i).x--;
//									listArrow.get(i).setLocation(listArrow.get(i).x, listArrow.get(i).y);
//									if (listArrow.get(i).x < -2) {
//										remove(listArrow.get(i));
//										listArrow.remove(i);
//										System.out.println(listArrow.size());
//									}
//									try {
//										Thread.sleep(1);
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//								}
//							}
//
//						}
//					}).start();
					new Thread(new Runnable() {
						@Override
						public void run() {
							while (true) {

								arrow.x--;
								arrow.setLocation(arrow.x, arrow.y);

								if (arrow.isFall == true) {
									break;
								}
								if (arrow.x < -2) {
									remove(arrow);
									arrow.isKill = false;
									break;
								}

								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}

					}).start();

					laAttackBow.setVisible(false);
					laAttackPy.setVisible(true);
					repaint();
				}

			} else {

				laAttackBow.setVisible(true);
				laAttackPy.setVisible(false);
				repaint();

			}

		} else {

			laAttackBow.setVisible(true);
			laAttackPy.setVisible(false);
			isArrow = false;
			repaint();

		}

	}

	
	// 줄
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 216, 225));
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(jpPlayer.getLocation().x + 35, 100, jpPlayer.getLocation().x + 35, jpPlayer.getLocation().y);
	}
}
