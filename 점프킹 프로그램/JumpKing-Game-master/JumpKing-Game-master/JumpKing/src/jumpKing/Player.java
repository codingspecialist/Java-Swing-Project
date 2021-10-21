package jumpKing;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

// �÷��̾� Ŭ�����Դϴ�.
// ��,���̵� ������ �����Ǿ��ֽ��ϴ�.

@Data
public class Player extends JLabel {
	public Player player = this; // �÷��̾� ���ؽ�Ʈ
	private final static String TAG = "Player"; // �±�
	private ImageIcon icPlayerLS, icPlayerRS, icPlayerLR, icPlayerRR; // ��,�� �̵� �̹���
	private ImageIcon icJumpR1, icJumpR2, icJumpR3, icJumpR4; // �� ���� �̹���
	private ImageIcon icJumpL1, icJumpL2, icJumpL3, icJumpL4; // �� ���� �̹���
	private ImageIcon icNuckDown; // �߶��� �̹���
	private int playerX = 500; // ĳ���� �⺻ ���� X��
	private int playerY = 400; // ĳƽ�� �⺻ ���� Y��
	
	private int stageCount = 1; // �� ������������ ǥ��

	// �¿��̵� Lock
	private boolean moveLock = false; // true�� Lock ���� ������ �������̰��Ѵ�
	private boolean moveLockLeft = false; // true�� Lock �¸� �������̰��Ѵ�
	private boolean moveLockRight = false; // true�� Lock �츦 �������̰��Ѵ�

	// true�϶��� �̵�����
	private boolean isRight = false; // �����̵�
	private boolean isLeft = false; // �����̵�
	private boolean isUp = false; // ���� -�� �̵�
	private boolean isDown = false; // ���� - �Ʒ� �̵�
	private boolean isMoveDown = true; // true�� �÷��̾ �������� false�� �ȶ�������

	private int jumpGauge = 0; // �Ŀ� ���� (����� ��) (���� ������ ���)
	private int jumpGaugeDown = 0; // �Ŀ� ���� (����� ��) (������ �Ʒ��ΰ��� ���)
	private int jumpLeft; // ������ ���󰡴� ���� ���� x�� ����
	private int jumpRight; // ������ ���󰡴� ���� ���� x�� ����

	// ����
	private int jumpUpDirection = 0; // (��,��,���ڸ� ������ �������� ����) -1 �¹���, 0 ���ڸ�, 1 �����
	private boolean jumpUpDirectionStay = true; // (���ڸ� ������ ��,�� �̹�������� ����)

	private Thread thDown; // �߷�
	private int jumpNuckDownCount = 0;
	private int floor = 1; // 535 415, 295, 177 1��,2��,3��,4��

	public boolean Gravity = true; // �߷¿���
	private boolean GravityCount = true;// �˴ٿ� �ѹ��� ǥ��
	private boolean downJpDown = false;// Down�� JumpDown ���ý��� �ȵǰ� ����

	public Player() {
		init();
		thDown.start();
		setIcon(icPlayerRS);
		setSize(70, 70); // ������� ũ��
		setLocation(playerX, playerY); // �⺻ ������ġ
	}

	void init() {
		// ���� �̹���
		icPlayerRS = new ImageIcon("images/icPlayerRS.png"); // �̵�-������
		icPlayerRR = new ImageIcon("images/icPlayerRR.png"); // �̵�-��
		icJumpR1 = new ImageIcon("images/icJumpR1.png"); // ����1 ���ڸ�
		icJumpR2 = new ImageIcon("images/icJumpR2.png");// ����2 ����
		icJumpR3 = new ImageIcon("images/icJumpR3.png");// ����3 ����
		icJumpR4 = new ImageIcon("images/icJumpR4.png");// ����4 ����

		// ���� �̹���
		icPlayerLS = new ImageIcon("images/icPlayerLS.png");// �̵�- ������
		icPlayerLR = new ImageIcon("images/icPlayerLR.png");// �̵�- ��
		icJumpL1 = new ImageIcon("images/icJumpL1.png");// ����1 ���ڸ�
		icJumpL2 = new ImageIcon("images/icJumpL2.png");// ����2 ����
		icJumpL3 = new ImageIcon("images/icJumpL3.png");// ����3 ����
		icJumpL4 = new ImageIcon("images/icJumpL4.png");// ����4 ����

		icNuckDown = new ImageIcon("images/icNuckDown.png");// �߶��� �̹���

		// �߷� ������
		thDown = new Thread(new DownMove());
	};

	public void moveRight() {
		Thread rightTh = new Thread(new RightMove()); // ������ �̵� ������
		if (isRight == false) { // moveRight() �ѹ��� �����ϴ� ���ǹ� // moveLeft(),JumpUp(),JumpDown() ����
			System.out.println(TAG + "moveRight()");
			rightTh.start();
		}
		setIcon(icPlayerRS);
	}
	public void moveLeft() {
		Thread leftTh = new Thread(new LeftMove()); // �����̵� ������
		if (isLeft == false) {
			System.out.println(TAG + "moveLeft()");
			leftTh.start();
		}
		setIcon(icPlayerLS);
	}
	public void jumpUp() {
		Thread thJumpUp = new Thread(new ThJumpUp());
		if (isUp == false) {
			System.out.println(TAG + "JumpUp()");
			thJumpUp.start();
		}
	}
	public void jumpDown() {
		Thread thJumpDown = new Thread(new ThJumpDown());
		if (isDown == false) {
			System.out.println(TAG + "JumpDown()");
			thJumpDown.start();
		}
	}
	
	class RightMove implements Runnable { // Leftmove()�� ���Ǻ��
		@Override
		public void run() {
			// ��, �� �̵������ �ε巴�� �ϱ����� �ٽ��ѹ� ����
			isLeft = false; // �����̵� false
			isRight = true; // �������̵� true

			while (isRight == true && isMoveLockRight() == false) {
				try {
					playerX = playerX + 10; // ������ 1ȸ�� �̵��� x�� 10������
					setLocation(playerX, playerY); // ���ο� repaint() ����

					// �����ð����� �̹��� ���� ��,��,������,�����ٿ� ��� ����
					Thread.sleep(10);
					setIcon(icPlayerRR);
					Thread.sleep(10);
					setIcon(icPlayerRS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class LeftMove implements Runnable {
		@Override
		public void run() {
			isLeft = true;
			isRight = false;
			while (isLeft == true && isMoveLockLeft() == false) {
				try {
					playerX = playerX - 10;
					setLocation(playerX, playerY); // ���ο� repaint() ����
					Thread.sleep(10);
					setIcon(icPlayerLR);
					Thread.sleep(10);
					setIcon(icPlayerLS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class DownMove implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					if (downJpDown == false) {// 
						if (Gravity == false) {
							isDown = false; // �ٿ� ����
							isUp = false; // �� ����

							if (GravityCount == false) { // �̹��� ������� 1��������
								if (jumpNuckDownCount > 300) {
									setIcon(icNuckDown);
								} else if (jumpUpDirection == 1) { // �� ���� ���� �̹���
									setIcon(icJumpR4);
								} else if (jumpUpDirection == -1) { // �� ���� ���� �̹���
									setIcon(icJumpL4);
								} else if (jumpUpDirectionStay == true) { // �� ���ڸ� ���� ���� �̹���
									setIcon(icPlayerRS);
								} else if (jumpUpDirectionStay == false) {// �� ���ڸ� ���� ���� �̹���
									setIcon(icPlayerLS);
								}
								jumpNuckDownCount = 0;
							}
							GravityCount = true;
						} else {
							Gravity = true; // ���� ���̸� true

						}

						if (Gravity == true) {// ���� ���϶� if��
							isRight = false; // �� �̵��Ұ���
							isLeft = false;// �� �̵� �Ұ���
							GravityCount = false;
							playerY = playerY + 4; // ������ 1ȸ�� 4��ŭ �ٿ�
							jumpNuckDownCount += 4;
							if (jumpUpDirectionStay == true) { // ���������� ���ڸ������� ������ icJumpR2�����ܻ��
								setIcon(icJumpR3);
							} else if (jumpUpDirectionStay == false) { // ���������� ���ڸ������� ������ icJumpR2�����ܻ��
								setIcon(icJumpL3);
							}
						}
					}
					setLocation(playerX, playerY); // ���ο� repaint() ����
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ThJumpUp implements Runnable {
		@Override
		public void run() {
			moveLock = true; // ������ ��,���̵� �Ұ���
			isUp = true; // ���� ����
			isRight = false; // �������̵� �Ұ���
			isLeft = false; // ���� �̵� �Ұ���
			downJpDown = true; // ���� ������ true �ƴҽ� false
			jumpGauge = 0;
			jumpLeft = 3;
			jumpRight = 3;
			while (isUp == true) {
				setIcon(icJumpR1);
				try {
					if (jumpGauge < 101) { // ��(�Ŀ�)�� 100�̻��̸� ���̻� �����
						jumpGauge = jumpGauge + 1; // �ѹ��� 5�� ����������
					}
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			while (true) {
				try {
					isRight = false; // �� �̵��Ұ���
					isLeft = false;// �� �̵� �Ұ���
					if (jumpUpDirection == 1) { // ���� ������ �������ΰ��� ���ǹ�
						if (moveLockRight == true) {
							jumpUpDirection = -1;
							continue;
						}
						playerX = playerX + jumpRight;
						setIcon(icJumpR2);
					} else if (jumpUpDirection == -1) { // ���� ������ �������ΰ��� ���ǹ�
						if (moveLockLeft == true) {
							jumpUpDirection = 1;
							continue;
						}
						playerX = playerX - jumpLeft;
						setIcon(icJumpL2);
					} else if (jumpUpDirectionStay == true) { // ���������� ���ڸ������� ������ icJumpR2�����ܻ��
						setIcon(icJumpR2);
					} else if (jumpUpDirectionStay == false) { // ���������� ���ڸ������� ������ icJumpR2�����ܻ��
						setIcon(icJumpL2);
					}

					setLocation(playerX, playerY); // ���ο� repaint() ����
					// ���������� �̵��ϴ� ���ǹ�
					if (jumpGauge > 50) {// ���� �б�1 ���� �� �κ�
						playerY = playerY - 5;
						jumpGauge -= 1;

					} else if (jumpGauge <= 50 && jumpGauge > 25) { // ���� �б�2 �߾� �κ�
						playerY = playerY - 3;
						jumpGauge -= 1;

					} else if (jumpGauge <= 25) { // ���� �б�3 ���� �� �κ�
						playerY = playerY - 1;
						jumpGauge -= 1;

					}

					if (jumpGauge <= 0) {// �ִ� ���� ���޽� �Լ�����
						System.out.println("�ٿ�޼��� ȣ��");
						jumpGauge = 100; // ���������� 0���� �ʱ�ȭ
						break;
					}
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			jumpDown(); // �ٿ� �޼��� ȣ��
		}
	}
	
	class ThJumpDown implements Runnable {
		@Override
		public void run() {
			jumpGaugeDown = 100;
			isDown = true; // �ٿ� ���
			jumpNuckDownCount = 0; // �����Ÿ� �̻� �������� �˴ٿ� �����ܹߵ�
			while (isDown) {
				setLocation(playerX, playerY); // ���ο� repaint() ����

				try {
					if (Gravity == true) {

						isRight = false;
						isLeft = false;
						if (jumpUpDirection == 1) { // �����ٿ�� x������
							if (moveLockRight == true) {
								jumpUpDirection = -1;
								continue;
							}
							setIcon(icJumpR3);
							playerX = playerX + jumpRight;
						} else if (jumpUpDirection == -1) { // �������ٿ�� x�� ����
							if (moveLockLeft == true) {
								jumpUpDirection = 1;
								continue;
							}
							setIcon(icJumpL3);
							playerX = playerX - jumpLeft;
						} else if (jumpUpDirectionStay == true) { // �������� ���ڸ��ٱ�� �̹���
							setIcon(icJumpR3);
						} else if (jumpUpDirectionStay == false) {// �������� ���ڸ��ٱ�� �̹���
							setIcon(icJumpL3);
						}
						if (jumpGaugeDown > 75) {// ���� �б�1 ���� �� �κ�
							playerY = playerY + 1;
							jumpNuckDownCount += +1;
							jumpGaugeDown -= 1;
						} else if (jumpGaugeDown <= 75 && jumpGaugeDown > 50) { // ���� �б�2 �߾� �κ�
							playerY = playerY + 3;
							jumpNuckDownCount += +3;
							jumpGaugeDown -= 1;
						} else if (jumpGaugeDown <= 50) { // ���� �б�3 ���� �� �κ�
							playerY = playerY + 5;
							jumpNuckDownCount += +5;
							jumpGaugeDown -= 1;
						}
					} else if (Gravity == false) {

						isDown = false; // �ٿ� ����
						isUp = false; // �� ����
						if (jumpNuckDownCount > 200) {
							setIcon(icNuckDown);
						} else if (jumpUpDirection == 1) { // �� ���� ���� �̹���
							setIcon(icJumpR4);
						} else if (jumpUpDirection == -1) { // �� ���� ���� �̹���
							setIcon(icJumpL4);
						} else if (jumpUpDirectionStay == true) { // �� ���ڸ� ���� ���� �̹���
							setIcon(icJumpR4);
						} else if (jumpUpDirectionStay == false) {// �� ���ڸ� ���� ���� �̹���
							setIcon(icJumpL4);
						}
						jumpUpDirection = 0;// �������� �ʱ�ȭ (������ ������ �׳� ���������� �����ذ��)
						moveLock = false; // ������ ������ �ٽ� �̵��Ҽ��ְ� Lock����
						downJpDown = false; // ������ ������ �ٽ� �߷½���
						break;
					}
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
