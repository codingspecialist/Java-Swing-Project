package jumpKing;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

// 플레이어 클래스입니다.
// 좌,우이동 점프가 구현되어있습니다.

@Data
public class Player extends JLabel {
	public Player player = this; // 플레이어 콘텍스트
	private final static String TAG = "Player"; // 태그
	private ImageIcon icPlayerLS, icPlayerRS, icPlayerLR, icPlayerRR; // 좌,우 이동 이미지
	private ImageIcon icJumpR1, icJumpR2, icJumpR3, icJumpR4; // 우 점프 이미지
	private ImageIcon icJumpL1, icJumpL2, icJumpL3, icJumpL4; // 좌 점프 이미지
	private ImageIcon icNuckDown; // 추락시 이미지
	private int playerX = 500; // 캐릭터 기본 시작 X축
	private int playerY = 400; // 캐틱터 기본 시작 Y축
	
	private int stageCount = 1; // 몇 스테이지인지 표시

	// 좌우이동 Lock
	private boolean moveLock = false; // true시 Lock 으로 모돈조작 못움직이게한다
	private boolean moveLockLeft = false; // true시 Lock 좌를 못움직이게한다
	private boolean moveLockRight = false; // true시 Lock 우를 못움직이게한다

	// true일때만 이동가능
	private boolean isRight = false; // 우측이동
	private boolean isLeft = false; // 좌측이동
	private boolean isUp = false; // 점프 -위 이동
	private boolean isDown = false; // 점프 - 아래 이동
	private boolean isMoveDown = true; // true시 플레이어가 떨어지고 false시 안떨어진다

	private int jumpGauge = 0; // 파워 변수 (기모우는 것) (위로 점프시 사용)
	private int jumpGaugeDown = 0; // 파워 변수 (기모우는 것) (점프후 아래로갈때 사용)
	private int jumpLeft; // 점프시 날라가는 좌측 방향 x값 변경
	private int jumpRight; // 점프시 날라가는 우측 방향 x값 변경

	// 점프
	private int jumpUpDirection = 0; // (좌,우,제자리 점프시 방향지정 변수) -1 좌방향, 0 제자리, 1 우방향
	private boolean jumpUpDirectionStay = true; // (제자리 점프시 좌,우 이미지저장용 변수)

	private Thread thDown; // 중력
	private int jumpNuckDownCount = 0;
	private int floor = 1; // 535 415, 295, 177 1층,2층,3층,4층

	public boolean Gravity = true; // 중력역할
	private boolean GravityCount = true;// 넉다운 한번만 표현
	private boolean downJpDown = false;// Down과 JumpDown 동시실행 안되게 제어

	public Player() {
		init();
		thDown.start();
		setIcon(icPlayerRS);
		setSize(70, 70); // 버블버블 크기
		setLocation(playerX, playerY); // 기본 시작위치
	}

	void init() {
		// 우측 이미지
		icPlayerRS = new ImageIcon("images/icPlayerRS.png"); // 이동-가만히
		icPlayerRR = new ImageIcon("images/icPlayerRR.png"); // 이동-뜀
		icJumpR1 = new ImageIcon("images/icJumpR1.png"); // 점프1 제자리
		icJumpR2 = new ImageIcon("images/icJumpR2.png");// 점프2 점프
		icJumpR3 = new ImageIcon("images/icJumpR3.png");// 점프3 낙하
		icJumpR4 = new ImageIcon("images/icJumpR4.png");// 점프4 착지

		// 좌측 이미지
		icPlayerLS = new ImageIcon("images/icPlayerLS.png");// 이동- 가만히
		icPlayerLR = new ImageIcon("images/icPlayerLR.png");// 이동- 뜀
		icJumpL1 = new ImageIcon("images/icJumpL1.png");// 점프1 제자리
		icJumpL2 = new ImageIcon("images/icJumpL2.png");// 점프2 점프
		icJumpL3 = new ImageIcon("images/icJumpL3.png");// 점프3 낙하
		icJumpL4 = new ImageIcon("images/icJumpL4.png");// 점프4 착지

		icNuckDown = new ImageIcon("images/icNuckDown.png");// 추락시 이미지

		// 중력 쓰레드
		thDown = new Thread(new DownMove());
	};

	public void moveRight() {
		Thread rightTh = new Thread(new RightMove()); // 오른쪽 이동 스레드
		if (isRight == false) { // moveRight() 한번만 실행하는 조건문 // moveLeft(),JumpUp(),JumpDown() 동일
			System.out.println(TAG + "moveRight()");
			rightTh.start();
		}
		setIcon(icPlayerRS);
	}
	public void moveLeft() {
		Thread leftTh = new Thread(new LeftMove()); // 왼쪽이동 스레드
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
	
	class RightMove implements Runnable { // Leftmove()랑 거의비슷
		@Override
		public void run() {
			// 좌, 우 이동변경시 부드럽게 하기위해 다시한번 설정
			isLeft = false; // 왼쪽이동 false
			isRight = true; // 오른쪽이동 true

			while (isRight == true && isMoveLockRight() == false) {
				try {
					playerX = playerX + 10; // 쓰레드 1회당 이동시 x값 10씩증가
					setLocation(playerX, playerY); // 내부에 repaint() 존재

					// 일정시간마다 이미지 변경 좌,우,점프업,점프다운 모두 동일
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
					setLocation(playerX, playerY); // 내부에 repaint() 존재
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
							isDown = false; // 다운 금지
							isUp = false; // 업 금지

							if (GravityCount == false) { // 이미지 착지모션 1번만실행
								if (jumpNuckDownCount > 300) {
									setIcon(icNuckDown);
								} else if (jumpUpDirection == 1) { // 우 점프 착지 이미지
									setIcon(icJumpR4);
								} else if (jumpUpDirection == -1) { // 좌 점프 착지 이미지
									setIcon(icJumpL4);
								} else if (jumpUpDirectionStay == true) { // 우 제자리 점프 착지 이미지
									setIcon(icPlayerRS);
								} else if (jumpUpDirectionStay == false) {// 좌 제자리 점프 착지 이미지
									setIcon(icPlayerLS);
								}
								jumpNuckDownCount = 0;
							}
							GravityCount = true;
						} else {
							Gravity = true; // 벽돌 밖이면 true

						}

						if (Gravity == true) {// 벽돌 밖일때 if문
							isRight = false; // 우 이동불가능
							isLeft = false;// 좌 이동 불가능
							GravityCount = false;
							playerY = playerY + 4; // 쓰레드 1회당 4만큼 다운
							jumpNuckDownCount += 4;
							if (jumpUpDirectionStay == true) { // 우측을보며 제자리점프시 아이콘 icJumpR2아이콘사용
								setIcon(icJumpR3);
							} else if (jumpUpDirectionStay == false) { // 좌측을보며 제자리점프시 아이콘 icJumpR2아이콘사용
								setIcon(icJumpL3);
							}
						}
					}
					setLocation(playerX, playerY); // 내부에 repaint() 존재
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
			moveLock = true; // 점프시 좌,우이동 불가능
			isUp = true; // 업만 가능
			isRight = false; // 오른쪽이동 불가능
			isLeft = false; // 왼쪽 이동 불가능
			downJpDown = true; // 점프 실행중 true 아닐시 false
			jumpGauge = 0;
			jumpLeft = 3;
			jumpRight = 3;
			while (isUp == true) {
				setIcon(icJumpR1);
				try {
					if (jumpGauge < 101) { // 기(파워)가 100이상이면 더이상 못모움
						jumpGauge = jumpGauge + 1; // 한번에 5씩 모을수있음
					}
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			while (true) {
				try {
					isRight = false; // 우 이동불가능
					isLeft = false;// 좌 이동 불가능
					if (jumpUpDirection == 1) { // 우측 점프시 우측으로가는 조건문
						if (moveLockRight == true) {
							jumpUpDirection = -1;
							continue;
						}
						playerX = playerX + jumpRight;
						setIcon(icJumpR2);
					} else if (jumpUpDirection == -1) { // 좌측 점프시 좌측으로가는 조건문
						if (moveLockLeft == true) {
							jumpUpDirection = 1;
							continue;
						}
						playerX = playerX - jumpLeft;
						setIcon(icJumpL2);
					} else if (jumpUpDirectionStay == true) { // 우측을보며 제자리점프시 아이콘 icJumpR2아이콘사용
						setIcon(icJumpR2);
					} else if (jumpUpDirectionStay == false) { // 좌측을보며 제자리점프시 아이콘 icJumpR2아이콘사용
						setIcon(icJumpL2);
					}

					setLocation(playerX, playerY); // 내부에 repaint() 존재
					// 포물선으로 이동하는 조건문
					if (jumpGauge > 50) {// 점프 분기1 제일 밑 부분
						playerY = playerY - 5;
						jumpGauge -= 1;

					} else if (jumpGauge <= 50 && jumpGauge > 25) { // 점프 분기2 중앙 부분
						playerY = playerY - 3;
						jumpGauge -= 1;

					} else if (jumpGauge <= 25) { // 점프 분기3 제일 윗 부분
						playerY = playerY - 1;
						jumpGauge -= 1;

					}

					if (jumpGauge <= 0) {// 최대 높이 도달시 함수종료
						System.out.println("다운메서드 호출");
						jumpGauge = 100; // 점프게이지 0으로 초기화
						break;
					}
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			jumpDown(); // 다운 메서드 호출
		}
	}
	
	class ThJumpDown implements Runnable {
		@Override
		public void run() {
			jumpGaugeDown = 100;
			isDown = true; // 다운 사용
			jumpNuckDownCount = 0; // 일정거리 이상 떨어지면 넉다운 아이콘발동
			while (isDown) {
				setLocation(playerX, playerY); // 내부에 repaint() 존재

				try {
					if (Gravity == true) {

						isRight = false;
						isLeft = false;
						if (jumpUpDirection == 1) { // 우측다운시 x값증가
							if (moveLockRight == true) {
								jumpUpDirection = -1;
								continue;
							}
							setIcon(icJumpR3);
							playerX = playerX + jumpRight;
						} else if (jumpUpDirection == -1) { // 좌측측다운시 x값 감소
							if (moveLockLeft == true) {
								jumpUpDirection = 1;
								continue;
							}
							setIcon(icJumpL3);
							playerX = playerX - jumpLeft;
						} else if (jumpUpDirectionStay == true) { // 우측보며 제자리뛰기시 이미지
							setIcon(icJumpR3);
						} else if (jumpUpDirectionStay == false) {// 좌측보며 제자리뛰기시 이미지
							setIcon(icJumpL3);
						}
						if (jumpGaugeDown > 75) {// 점프 분기1 제일 윗 부분
							playerY = playerY + 1;
							jumpNuckDownCount += +1;
							jumpGaugeDown -= 1;
						} else if (jumpGaugeDown <= 75 && jumpGaugeDown > 50) { // 점프 분기2 중앙 부분
							playerY = playerY + 3;
							jumpNuckDownCount += +3;
							jumpGaugeDown -= 1;
						} else if (jumpGaugeDown <= 50) { // 점프 분기3 제일 밑 부분
							playerY = playerY + 5;
							jumpNuckDownCount += +5;
							jumpGaugeDown -= 1;
						}
					} else if (Gravity == false) {

						isDown = false; // 다운 금지
						isUp = false; // 업 금지
						if (jumpNuckDownCount > 200) {
							setIcon(icNuckDown);
						} else if (jumpUpDirection == 1) { // 우 점프 착지 이미지
							setIcon(icJumpR4);
						} else if (jumpUpDirection == -1) { // 좌 점프 착지 이미지
							setIcon(icJumpL4);
						} else if (jumpUpDirectionStay == true) { // 우 제자리 점프 착지 이미지
							setIcon(icJumpR4);
						} else if (jumpUpDirectionStay == false) {// 좌 제자리 점프 착지 이미지
							setIcon(icJumpL4);
						}
						jumpUpDirection = 0;// 점프방향 초기화 (점프가 끝나고 그냥 위로점프시 버그해결용)
						moveLock = false; // 점프가 끝나면 다시 이동할수있게 Lock해제
						downJpDown = false; // 점프가 끝나고 다시 중력실행
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
