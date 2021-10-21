package ingame;

import javax.swing.ImageIcon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapObjectImg {
	
	// 배경 이미지
	private ImageIcon backIc; // 제일 뒷 배경
	private ImageIcon secondBackIc; // 2번째 배경

	// 젤리 이미지 아이콘들
	private ImageIcon jelly1Ic;
	private ImageIcon jelly2Ic;
	private ImageIcon jelly3Ic;
	private ImageIcon jellyHPIc;

	private ImageIcon jellyEffectIc;

	// 발판 이미지 아이콘들
	private ImageIcon field1Ic; // 발판
	private ImageIcon field2Ic; // 공중발판

	// 장애물 이미지 아이콘들
	private ImageIcon tacle10Ic; // 1칸 장애물
	private ImageIcon tacle20Ic; // 2칸 장애물
	private ImageIcon tacle30Ic; // 3칸 장애물
	private ImageIcon tacle40Ic; // 4칸 장애물
	
}
