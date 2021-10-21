package Paint;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class MyCanvas extends Canvas {

	// 검은색 점 안찍히게 하기 위해서 x, y 값을 -로 지정해준다.
	public int x;
	public int y;
//	public int W = 7;
//	public int H = 7;
	
	public Color color = Color.BLACK;

	@Override
	public void paint(Graphics graphics) {
		System.out.println("paint`````````````````````````````````");
		graphics.setColor(color);
		graphics.fillOval(x -5, y -5, 7, 7); // 70, 70 크기의 원 그리기 
	}

	@Override
	public void update(Graphics graphics) {
		System.out.println("update`````````````````````````````````");
		paint(graphics);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}