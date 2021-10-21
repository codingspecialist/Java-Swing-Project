package structure;

import javax.swing.JFrame;

import SpriteSheet.SpriteSheet;
import lombok.Data;
import objectSize.StructureSize;

@Data

public class Structure {

	private final static String TAG = "Structure : ";
	
	private JFrame app;
	private SpriteSheet ssStructure;
	private int xStructure, yStructure;
	private boolean isBroken = false;
	
	public Structure(JFrame app,String url, String gubun, int xStructure, int yStructure) {
//		System.out.println(TAG + "만들어짐");
		this.app = app;
		this.ssStructure = new SpriteSheet(url, gubun, 0, 0, StructureSize.WIDTH, StructureSize.HEIGHT);
		this.xStructure = xStructure;
		this.yStructure = yStructure;
	}
	public void drawStructure() {}
}
