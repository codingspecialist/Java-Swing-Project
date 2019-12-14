package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
	  private int trackNum; //트랙번호
	  private String inst; // 악기명
	  private String note;// 노트
}
