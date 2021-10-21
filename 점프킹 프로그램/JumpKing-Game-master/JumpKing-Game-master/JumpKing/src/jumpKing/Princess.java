package jumpKing;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

// 공주 클래스입니다.
// 쓰레드가 돌면서 이미지가 변환됩니다.
@Data
public class Princess extends JLabel {
	public Princess princess = this; // 플레이어 콘텍스트
	private final static String TAG = "princess"; 
	private Thread princessActivity;
	//공주 이미지
	private ImageIcon icPrincess1,icPrincess2,icPrincess3,icPrincess4,icPrincess5,icPrincess6,icPrincess7,icPrincess8;
//	private  int princessX = 180; // 캐릭터 기본 시작 X축
//	private  int princessY = 95; // 캐틱터 기본 시작 Y축
	
	private  int princessX = 600; // 캐릭터 기본 시작 X축
    private  int princessY = 300; // 캐틱터 기본 시작 Y축 
	
	public Princess() {
		init();
		setIcon(icPrincess1);
		setSize(70, 70); // 버블버블 크기
		setLocation(princessX, princessY); // 기본 시작위치
	}

	void init() {
		// 공주이미지
		icPrincess1 = new ImageIcon("images/icPrincess1.png");
		icPrincess2 = new ImageIcon("images/icPrincess2.png");
		icPrincess3 = new ImageIcon("images/icPrincess3.png");
		icPrincess4 = new ImageIcon("images/icPrincess4.png");
		icPrincess5 = new ImageIcon("images/icPrincess5.png");
		icPrincess6 = new ImageIcon("images/icPrincess6.png");
		icPrincess7 = new ImageIcon("images/icPrincess7.png");
		icPrincess8 = new ImageIcon("images/icPrincess8.png");
		
		princessActivity = new Thread(new PrincessActivity());
		princessActivity.start();
	};
		
	class PrincessActivity implements Runnable{

		@Override
		public void run() {
			while(true) {
				try {
					setIcon(icPrincess1);
					Thread.sleep(300);
					setIcon(icPrincess2);
					Thread.sleep(300);
					setIcon(icPrincess3);
					Thread.sleep(300);
					setIcon(icPrincess4);
					Thread.sleep(300);
					setIcon(icPrincess5);
					Thread.sleep(300);
					setIcon(icPrincess6);
					Thread.sleep(300);
					setIcon(icPrincess7);
					Thread.sleep(300);
					setIcon(icPrincess8);
					Thread.sleep(300);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		
	}
	
}
