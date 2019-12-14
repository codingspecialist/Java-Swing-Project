package bubbleBobble;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Z extends JLabel implements Runnable {
	int x = 480;
	int y = 178;

	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	int direction = 1;
	int floor = 4;

	long past;
	long current;

	ImageIcon zhenR;
	ImageIcon zhenL;
	boolean isBubbled;
	ImageIcon bubbled;

	public void init() {
		zhenR = new ImageIcon("image/zhenR.png");
		zhenL = new ImageIcon("image/zhenL.png");
		bubbled = new ImageIcon("image/bubbled.png");
		isBubbled = false;
	}

	public Z() {
		init();
		setIcon(zhenL);
		this.setSize(50, 50);
		this.setLocation(480, 178);
	}

	public void held() {
		new Thread(() -> {
			this.isBubbled = true;
			this.left = false;
			this.right = false;
			this.up = false;
			this.down = false;
			this.setIcon(bubbled);
			moveZhen();
		}).start();
	}
	private void moveZhen() {
		try {
			for (int i = 0; i < this.getY() + 50; i++) {
				this.y -= 1;
				this.setLocation(this.x, this.y);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void rightMove() {
		if (this.right == false) {
			this.setIcon(zhenR);
			this.right = true;
			this.direction = 2;

//			new Thread(() -> {
			System.out.println("우측이동 스레드가 생성되었습니다.");
			while (this.right && !this.isBubbled) {
				if (this.right && !this.down && !this.up) { // 평상시 이동속도
					try {
						this.setLocation(this.x + 1, this.y);
						this.x++;
						if (this.x >= 885) {
							this.x = 885;
							this.right = false;
							this.leftMove();
						}
						if (this.getX() >= 110 && this.getX() <= 170 && this.getY() <= 420 && this.up == false) {
							this.downMove();
						} else if (this.getX() >= 780 && this.getX() <= 830 && this.getY() <= 420 && this.up == false) {
							this.downMove();
						}
						if (floor < A.floor && (x <= 100 || (x >= 180 && x <= 770) || x >= 840)) {
							upMove();
						}

						Thread.sleep(5);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
//			}).start();
		}
	}

	public void leftMove() {
		if (this.left == false) {
			this.setIcon(zhenL);
			this.left = true;
			this.direction = 1;
//			new Thread(() -> {
			System.out.println("좌측이동 스레드가 생성되었습니다.");
			while (this.left && !this.isBubbled) {
				if (this.left && !this.down && !this.up) { // 평상시 이동속도
					try {
						this.setLocation(this.x - 1, this.y);
						this.x--;
						if (this.x <= 55) {
							this.x = 55;
							this.left = false;
							this.rightMove();

						}
						if (this.getX() >= 110 && this.getX() <= 170 && this.getY() <= 420 && this.up == false) {
							this.downMove();
						} else if (this.getX() >= 780 && this.getX() <= 830 && this.getY() <= 420 && this.up == false) {
							this.downMove();
							Thread.sleep(5);
						}
						if (floor < A.floor && (x <= 100 || (x >= 180 && x <= 770) || x >= 840)) {
							upMove();
						}
						Thread.sleep(5);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
//			}).start();
		}

	}

	public void upMove() {
		if (up == false) {
			this.up = true;
//			this.direction = 1;
//			new Thread(() -> {
			System.out.println("상승 스레드가 생성되었습니다.");
			while (this.up && !this.isBubbled) {
				for (int i = 0; i < 130; i++) {
					try {
						this.setLocation(this.x, this.y);
						this.y--;
						Thread.sleep(5);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} // 상승 끝
				this.up = false;
				this.downMove();
			}
//			}).start();
		}
	}

	public void downMove() {
		if (this.down == false && this.y < 536) {
			this.down = true;
//			new Thread(() -> {
			System.out.println("하강 스레드가 생성되었습니다.");
			while (this.down && !this.isBubbled) {
				try {
					this.setLocation(this.x, this.y + 1);
					this.y++;
					if (this.y >= 537) {
						this.y = 536;
						this.down = false;
						this.floor = 1;
						if (this.direction == 1) {
							this.left = false;
							this.leftMove();
						} else {
							this.right = false;
							this.rightMove();
						}
					}
					if (this.getY() == 415) {
						this.down = false;
						this.floor = 2;
						if (this.direction == 1) {
							this.left = false;
							this.leftMove();
						} else {
							this.right = false;
							this.rightMove();
						}
					}
					// 3층
					if (this.getY() == 295) {
						this.down = false;
						this.floor = 3;
						if (this.direction == 1) {
							this.left = false;
							this.leftMove();
						} else {
							this.right = false;
							this.rightMove();
						}
					}
					// 4층
					if (this.getY() == 177) {
						this.down = false;
						this.floor = 4;
						if (this.direction == 1) {
							this.left = false;
							this.leftMove();
						} else {
							this.right = false;
							this.rightMove();
						}
					}
					Thread.sleep(10);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
//			}).start();
		}
	}

	@Override
	public void run() {
//		while (true) {
		try {
			// 벽 좌표 계산 시작
			this.leftMove();

//				if (this.x <= 55) {
//					this.x = 55;
//					this.left = false;
//					this.rightMove();
//				}
//				if (this.x >= 885) {
//					this.x = 885;
//					this.right = false;
//					this.leftMove();
//				}
//				if (this.y >= 537) {
//					this.down = false;
//					this.y = 536;
//					this.floor = 1;
//					System.out.println("하강 끝");
//					System.out.println(this.direction);
//					if(this.direction == 1) {
//						this.left = false;
//						this.leftMove();
//					} else {
//						this.right = false;
//						this.rightMove();
//					}
//				}
//				// 벽 좌표 계산 끝
//				// 구멍 계산 시작
//				if (this.getX() >= 110 && this.getX() <= 170 && this.getY() <= 420 && this.up == false) {
//					this.downMove();
//				} else if (this.getX() >= 780 && this.getX() <= 830 && this.getY() <= 420 && this.up == false) {
//					this.downMove();
//					// 구멍 계산 끝
//				} else {
//					// 2층
//					if (this.getY() == 415) {
//						this.down = false;
//						this.floor = 2;
//					}
//					// 3층
//					if (this.getY() == 295) {
//						this.down = false;
//						this.floor = 3;
//					}
//					// 4층
//					if (this.getY() == 177) {
//						this.down = false;
//						this.floor = 4;
//					}
//				}
			Thread.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	}

}

class B extends JLabel {
	int x;
	int y;
	ImageIcon bubbled;
	ImageIcon bubble;
	A a;
	Boolean status;

	public void init() {
		bubbled = new ImageIcon("image/bubbled.png");
		bubble = new ImageIcon("image/bubble.png");
		status = true;
	}

	public B(int x, int y) {
		init();
		this.x = x;
		this.y = y;
		setIcon(bubble);
		this.setSize(50, 50);
		this.setLocation(x, y);

	}

//	public void hold(Z z) {
//		new Thread(() -> {
//			this.x = -50;
//			this.y = -50;
//			z.isBubbled = true;
//			z.setIcon(bubbled);
//			moveZhen(z);
//		}).start();
//
//	}

	private void moveZhen(Z z) {
		try {
			for (int i = 0; i < z.getY() + 50; i++) {
				z.y -= 1;
				z.setLocation(z.x, z.y);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void leftB(A a) {
		new Thread(() -> {
			try {
				for (int i = 0; i < 300; i++) {
					this.setLocation(this.x, this.y);
					this.x -= 1;
					Thread.sleep(5);
				}
				status = false;
				for (int i = 0; i < 250; i++) {
					this.setLocation(this.x, this.y);
					this.y -= 3;
					Thread.sleep(16);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}).start();
	}

	public void rightB(A a) {
		new Thread(() -> {
			try {
				for (int i = 0; i < 300; i++) {
					this.setLocation(this.x, this.y);
					this.x += 1;
					Thread.sleep(5);
				}
				status = false;
				for (int i = 0; i < 250; i++) {
					this.setLocation(this.x, this.y);
					this.y -= 3;
					Thread.sleep(16);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}).start();
	}

}

class Cal extends Thread {
	A a;
	Test test;
	ArrayList<Z> zList;
	long past = 0;
	long current = 0;

	public Cal(A a, Test test, ArrayList<Z> zList) {
		this.a = a;
		this.test = test;
		this.zList = zList;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (zList.size() == 0) {

				}
				// 벽 좌표 계산 시작
				if (a.x <= 55) {
					a.x = 55;
				}
				if (a.x >= 885) {
					a.x = 885;
				}
				if (a.y >= 536) {
					a.down = false;
					a.y = 536;
					a.floor = 1;
				}
				// 벽 좌표 계산 끝
				// 구멍 계산 시작
				if (a.getX() >= 110 && a.getX() <= 170 && a.getY() <= 420 && a.up == false) {
					a.downMove();
				} else if (a.getX() >= 780 && a.getX() <= 830 && a.getY() <= 420 && a.up == false) {
					a.downMove();
					// 구멍 계산 끝
				} else {
					// 2층
					if (a.getY() == 415) {
						a.down = false;
						a.floor = 2;
					}
					// 3층
					if (a.getY() == 295) {
						a.down = false;
						a.floor = 3;
					}
					// 4층
					if (a.getY() == 177) {
						a.down = false;
						a.floor = 4;
					}
				}

				// 젠과 플레이어 층 맞추기

				// 버블과 젠 충돌 계산
				try {
					for (int i = 0; i < test.bList.size(); i++) {
						for (int j = 0; j < zList.size(); j++) {
							if (test.bList.get(i).status) {
								if (test.bList.get(i).getX() >= zList.get(j).getX() - 50
										&& test.bList.get(i).getX() <= zList.get(j).getX() + 50
										&& test.bList.get(i).getY() > zList.get(j).getY() - 50
										&& test.bList.get(i).getY() < zList.get(j).getY() + 50
										&& zList.get(j).isBubbled == false) {
									zList.get(j).held();
									test.remove(test.bList.get(i));
									test.repaint();
									test.bList.remove(i);
								}
							} else if (test.bList.get(i).getY() <= 0) {
								test.remove(test.bList.get(i));
								test.repaint();
								test.bList.remove(i);

							}
						}

					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				// 젠과 플레이어 충돌 계산
				try {
					for (int i = 0; i < zList.size(); i++) {
						if (zList.get(i) != null) {
							if (a.getX() >= zList.get(i).getX() - 50 && a.getX() <= zList.get(i).getX() + 50
									&& a.getY() > zList.get(i).getY() - 50 && a.getY() < zList.get(i).getY() + 50) {
								a.attack(zList.get(i));
								zList.remove(i);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class A extends JLabel {
	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	int x = 55;
	int y = 535;
	ImageIcon playerR = new ImageIcon("image/playerR.png");
	ImageIcon playerL = new ImageIcon("image/playerL.png");
	ImageIcon bomb = new ImageIcon("image/bomb.png");
	Test test;
	int direction = 2;
	static int floor = 1;

	Thread rightTh;

	public A(Test test) {
		setIcon(playerR);
		this.setSize(50, 50);
		this.setLocation(x, y);
		this.test = test;
	}

	public void attack(Z z) {
		if (z.isBubbled) {
			new Thread(() -> {
				z.setIcon(bomb);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				test.remove(z);
				test.repaint();
				z.x = -50;
				z.y = -50;
			}).start();

		}

	}

	public void rightMove() {
		if (right == false) {
			this.setIcon(playerR);
			this.right = true;
			this.direction = 2;

			new Thread(() -> {
				System.out.println("우측이동 스레드가 생성되었습니다.");
				while (this.right) {
					if (this.up || this.down) { // 하강 혹은 상승중일 때는 이동속도가 떨어진다.
						try {
							this.setLocation(this.x + 1, this.y);
							this.x++;
							Thread.sleep(10);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					} else { // 평상시 이동속도
						try {
							this.setLocation(this.x + 1, this.y);
							this.x++;
							Thread.sleep(5);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	public void leftMove() {
		if (left == false) {
			this.setIcon(playerL);
			this.left = true;
			this.direction = 1;
			new Thread(() -> {
				while (this.left) {
					try {
							this.setLocation(this.x - 1, this.y);
							this.x--;
							if (this.up || this.down) { // 하강 혹은 상승중일 때는 이동속도가 떨어진다.
							Thread.sleep(10);
							} else { // 평상시 이동속도
							Thread.sleep(5);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}).start();
		}
	}

	public void upMove() {
		if (up == false) {
			this.up = true;
			new Thread(() -> {
				System.out.println("상승 스레드가 생성되었습니다.");
				while (this.up) {
					for (int i = 0; i < 130; i++) {
						try {
							this.setLocation(this.x, this.y);
							this.y--;
							Thread.sleep(5);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					} 
					this.up = false;
					this.downMove();
				}
			}).start();
		}
	}

	public void downMove() {
		if (this.down == false && this.y < 536) {
			this.down = true;
			new Thread(() -> {
				while (this.down) {
					try {
						this.setLocation(this.x, this.y + 1);
						this.y++;
						Thread.sleep(10);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}).start();
		}
	}

}

public class Test extends JFrame {

	A a;
	Cal cal;
//	Bubble[] bubble;
	ArrayList<Z> zList;
	JLabel jl;
	ArrayList<B> bList;
	int zCount;
	Z z;

	public void init() {
		zCount = 3;
		jl = new JLabel(new ImageIcon("image/bg.png"));
		a = new A(this);
		zList = new ArrayList<Z>();
		for (int i = 0; i < zCount; i++) {
			z = new Z();
			zList.add(z);
		}
		bList = new ArrayList<B>();
		cal = new Cal(a, this, zList);

	}

	public Test() {
		setSize(1000, 639);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("0");
		init();
		setContentPane(jl);

		add(a);
		int temp = 500; // 테스트용
		for (Z z : zList) {
			z.x = temp;
			z.setLocation(temp, z.y);
			add(z);
			temp -= 100;
			Thread zTh = new Thread(z);
			zTh.start();
		}
		cal.start();

		setVisible(true);

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					new Clear();
				case KeyEvent.VK_RIGHT:
					a.rightMove();
					break;
				case KeyEvent.VK_LEFT:
					a.leftMove();
					break;
				case KeyEvent.VK_UP:
					if (a.down == false) {
						a.upMove();
					}
					break;
				case KeyEvent.VK_SPACE:
					if (a.direction == 1) {
						B bubble = new B(a.getX() - 50, a.getY());
						bubble.leftB(a);
						add(bubble);
						bList.add(bubble);

					} else {
						B bubble = new B(a.getX() + 50, a.getY());
						bubble.rightB(a);
						add(bubble);
						bList.add(bubble);

					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					a.right = false;
					break;
				case KeyEvent.VK_LEFT:
					a.left = false;
					break;
				}
			}
		});
	}

	public static void main(String[] args) {
		new Test();
	}

}

class Clear extends JDialog {

	JLabel jlb = new JLabel("");
	JButton jb = new JButton("종료");

	public Clear() {
		getContentPane().add(jlb);
		setLocation(400, 300);
		setLayout(new BorderLayout());
		jlb.setText("클리어 하였습니다.");
		this.setSize(100, 100);
//            this.setModal(true);
		this.setVisible(true);
		jb.setSize(30, 30);
		add(jb, BorderLayout.SOUTH);
		add(jlb, BorderLayout.CENTER);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

}