package structure;

import javax.swing.JFrame;

import lombok.Data;

@Data

public class Spike extends Structure {

	private final static String TAG = "Spike : ";
	
	public Spike(JFrame app, int xStructure, int yStructure) {
		super(app, "structure/spike.png", "spike", xStructure, yStructure);
//		System.out.println(TAG + "만들어짐");
		drawStructure();
	}
	@Override
	public void drawStructure() {
		System.out.println(getXStructure());
		getSsStructure().drawObject(getXStructure(), getYStructure());
		getApp().add(getSsStructure());
	}
}
