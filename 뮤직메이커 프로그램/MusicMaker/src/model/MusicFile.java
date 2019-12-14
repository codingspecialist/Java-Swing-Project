package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicFile {
	private String userfilename;
	private String username;
	private String filename;
	private int track;
	private int madi;
	private int bpm;
	private String note;
	private String drumnote;
}
