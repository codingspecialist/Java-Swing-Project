package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jelly {
	private Image image; // 젤리 이미지
	
	// 젤리들의 좌표와 크기
	private int x;
	private int y;
	private int width;
	private int height;
	
	// 젤리의 투명도 0투명 255불투명
	private int alpha;
	
	// 젤리의 점수
	private int score;
}

