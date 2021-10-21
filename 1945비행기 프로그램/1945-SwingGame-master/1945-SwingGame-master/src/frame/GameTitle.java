package frame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameTitle extends JPanel implements screenSize {
	
	private ImageIcon titleIcon = new ImageIcon("images/GameTitle.gif");
	private Image titleImg = titleIcon.getImage();
	
	
	private GameFrame win;

	public GameTitle(GameFrame win) {
		setLayout(null);
		this.win = win;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImg, 0, 0, SCREEN_WIDTH - 15, SCREEN_HEIGHT, 0, 0, 338, 594, this);
	}



}
