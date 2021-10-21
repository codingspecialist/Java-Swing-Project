package jumpKing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// 픽셀체크 클레스입니다.
// 플레이어의 위치에따라 색을 판단해줍니다
public class PixelCheck implements Runnable {
	private int playerX, playerY, playerY2, playerX3, playerY3, playerX4, playerY4, playerX5,playerY5;
	private int red1, green1, blue1, alpha1;// RGB,투명도 변수 //투명도는 안씀 참고로 넣어놓는거
	private int red2, green2, blue2 ;// RGB 변수
	private int red3, green3, blue3 ;// RGB 변수
	private int red4, green4, blue4 ;// RGB 변수
	private int red5, green5, blue5 ;// RGB 변수
	

	private BufferedImage image; // 이미지
	private Player player; // 플레이어 콘텍스트 담기
	private JumpKingApp jumpKingApp; // 버블맵 콘텍스트 담기

	public PixelCheck(Player player, JumpKingApp jumpKingApp) {
		this.player = player; //플레이어 콘텍스트 담기
		this.jumpKingApp = jumpKingApp; // 버블맵 콘텍스트 담기
		try {
			this.image = ImageIO.read(new File("images/1StageBW.png")); // 백그라운드 이미지
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {

			try {
				// 파일경로
//				int x = image.getWidth(null); // 이미지 가로 전체 
//				int y = image.getHeight(null); // 이미지 세로 전체

				playerX = player.getX() + 25; // 땅바닥 인식좌표
				playerY = player.getY() + 60;//

				playerY2 = player.getY() + 55; // 땅바닥 인식좌표2

				playerX3 = player.getX(); // 좌측 인식좌표
				playerY3 = player.getY() + 40; 

				playerX4 = player.getX() + 55; // 우측 인식좌표
				playerY4 = player.getY() + 40;
				
				playerX5 = player.getX() + 25; // 머리 인식좌표
				playerY5 = player.getY();
				

				Color color1 = new Color(image.getRGB(playerX, playerY));// 해당좌표 컬러담기
				Color color2 = new Color(image.getRGB(playerX, playerY2)); // 벽뚫 방지 컬러좌표 //검은색 판별
				Color color3 = new Color(image.getRGB(playerX3, playerY3));// 벽뚫 방지 좌측 // 파란색 판별
				Color color4 = new Color(image.getRGB(playerX4, playerY4));// 벽뚫 방지 우측 // 파란색 판별
				Color color5 = new Color(image.getRGB(playerX5, playerY4));// 벽뚫 방지 위쪽 // 파란색 판별
				

				// alpha = color.getAlpha(); // 투명도 저장

				red1 = color1.getRed(); // 빨강색 저장
				green1 = color1.getGreen();// 초록색 저장
				blue1 = color1.getBlue(); // 파랑색 저장

				red2 = color2.getRed(); // 빨강색 저장
				green2 = color2.getGreen();// 초록색 저장
				blue2 = color2.getBlue(); // 파랑색 저장

				red3 = color3.getRed(); // 빨강색 저장
				green3 = color3.getGreen();// 초록색 저장
				blue3 = color3.getBlue(); // 파랑색 저장

				red4 = color4.getRed(); // 빨강색 저장
				green4 = color4.getGreen();// 초록색 저장
				blue4 = color4.getBlue(); // 파랑색 저장
				
				red5 = color5.getRed(); // 빨강색 저장
				green5 = color5.getGreen();// 초록색 저장
				blue5 = color5.getBlue(); // 파랑색 저장


//				System.out.println(" X 좌표 : " +playerX + " Y 좌표 : " + playerY); //현재 플레이어의 위치 출력
//				System.out.println("빨강 : "+red+" 연두 : "+ green + "파랑 : " + blue); // 색깔출력
				
				
				if (red1 == 255 && green1 == 255 && blue1 == 255) { //땅바닥이 아니면 [흰색] 아래로 떨어짐
					player.setMoveLockLeft(false);
					player.setMoveLockRight(false);
					player.Gravity = true;
//					System.out.println("흰색");

				} else if (red1 == 0 && green1 == 0 && blue1 == 0) { // 땅바닥 IF문
					if (red2 == 0 && green2 == 0 && blue2 == 0) {// 땅속이면 땅바닥으로 올려주는 IF문
						player.setPlayerY(player.getY() - 1);
//						System.out.println("깜장이당");	
					} else {//땅바닥이면 떨어지지 않고, 좌,우 이동가능하게 Lock 해제
						player.Gravity = false;
						player.setMoveLockLeft(false);
						player.setMoveLockRight(false);
						
//						System.out.println("검은색");	
					}
				} 
				
				if((red3 == 0 && green3 == 0 && blue3 == 255)&&(red4 == 0 && green4 == 0 && blue4 == 255)){
					// 파랑벽에끼면 위로 올려줌
					player.setPlayerY(player.getPlayerY()+1);
				}else if (red3 == 0 && green3 == 0 && blue3 == 255) { //왼쪽이 파란색이면 더이상 못감
//					System.out.println("퍼랭이다 왼쪽");
					player.setMoveLockLeft(true); //왼쪽이동 잠금
					player.setJumpRight(2); //점프우측계수 2낮춤
					player.setJumpGauge(player.getJumpGauge()-30); //점프게이지 30낮춤
					player.setJumpGaugeDown(player.getJumpGaugeDown()-30); // 점프게이지다운 30낮춤
				}else	if (red4 == 0 && green4 == 0 && blue4 == 255) {//오른쪽이 파란색이면 더이상 못감
//					System.out.println("퍼랭이다 오른쪽");
					player.setMoveLockRight(true);
					player.setJumpLeft(2);
					player.setJumpGauge(player.getJumpGauge()-30);
					player.setJumpGaugeDown(player.getJumpGaugeDown()-30);
				} 
								
				 if (red5 == 0 && green5 == 0 && blue5 == 255) {// 머리부분이 파란색이면 하강하도록 만듬 
//					System.out.println("머리@당");
					player.setJumpGauge(0);
				}
			
				if (player.getY() < 0) { //천장을 뚫으면 다음스테이지로 넘어감
					if (player.getStageCount() == 1) { // 스테이지카운트가 1일때 발생
						player.setPlayerY(500); // Y값 500으로 변경
						player.setStageCount(2); // 스테이지 카운트2 로변경
						jumpKingApp.setImgCount(2); // 배경을 스테이지 2로 변경
						jumpKingApp.setSize(1080, 607); // 배경 사이즈 변경
						
						this.image = ImageIO.read(new File("images/2StageBW.png")); // 연산배경 2스테이지로 변경
					} else if (player.getStageCount() == 2) { // 스테이지 카운트가 2일때 발생 위와 동일
						player.setPlayerY(500);
						player.setStageCount(3);
						jumpKingApp.setImgCount(3);
						jumpKingApp.creatPrincess(); //공주 생성
						jumpKingApp.setSize(1080, 607);
						this.image = ImageIO.read(new File("images/3StageBW.png"));
					}
				} else if (player.getY() > 535) {// 아래로 하락하면 스테이지 이전 스테이지 전환
					if (player.getStageCount() == 2) { // 스테이지카운터 2일때 발생
						player.setPlayerY(0); // Y값 10으로 변경
						player.setStageCount(1); // 스테이지 카운터1
						jumpKingApp.setImgCount(1);// 배경을 스테이지 1로 바꿈
						jumpKingApp.setSize(1080, 607); // 배경 사이즈 변경
						
						this.image = ImageIO.read(new File("images/1StageBW.png"));// 연산배경 스테이지1로 변경
					} else if (player.getStageCount() == 3) {// 스테이지 카운터 3일때발생 위와 동일
						player.setPlayerY(0);
						player.setStageCount(2);
						jumpKingApp.setImgCount(2);
						jumpKingApp.removePrincess();// 공주 제거
						jumpKingApp.setSize(1080, 607);
						this.image = ImageIO.read(new File("images/2StageBW.png"));
					}
				}
				Thread.sleep(1);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
