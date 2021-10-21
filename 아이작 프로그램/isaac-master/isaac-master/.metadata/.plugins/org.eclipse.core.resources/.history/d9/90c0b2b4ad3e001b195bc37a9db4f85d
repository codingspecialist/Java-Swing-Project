package structure;

import javax.swing.JFrame;

import lombok.Data;

@Data

public class Spike extends Structure {

	private final static String TAG = "Spike : ";
	private double damage;
	
	public Spike(JFrame app, int xStructure, int yStructure) {
		super(app, "structure/spike.png", "spike", xStructure, yStructure);
//		System.out.println(TAG + "만들어짐");
		drawStructure();
		this.damage = 0.5;
	}
	@Override
	public void drawStructure() {
		System.out.println(getXStructure());
		getSsStructure().drawObject(getXStructure(), getYStructure());
		getApp().add(getSsStructure());
	}
}
