package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cookie {
	
	private Image image; // 쿠키 이미지
	
	// 쿠키의 좌표와 넓이 높이
	private int x = 160;
	private int y = 0;
	private int width = 80;
	private int height = 120;
	
	// 쿠키의 투명도 (0이 투명, 255가 완전 불투명)
	private int alpha = 255;
	
	// 쿠키의 체력
	private int health = 1000;
	
	// 쿠키의 상태 (거대화와 가속화 미구현)
	private int big = 0; // 거대화 남은 시간
	private int fast = 0; // 가속화 남은 시간
	private int countJump = 0; // 점프 횟수
	private boolean invincible = false; // 무적 여부
	private boolean fall = false; // 낙하 여부
	private boolean jump = false; // 점프 여부
	
	public Cookie(Image image){ // 이미지만 넣어주자
		this.image = image;
	}
}

