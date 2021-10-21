package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tacle {
	
	private Image image; // 장애물 이미지
	
	// 장애물의 좌표와 넓이 높이
	private int x;
	private int y;
	private int width;
	private int height;
	
	// 장애물 상태
	private int state;
}

