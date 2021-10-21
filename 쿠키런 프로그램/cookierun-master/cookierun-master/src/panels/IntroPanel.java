package panels;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {
	
	ImageIcon introIc = new ImageIcon("img/intro/intro.png"); // 인트로 이미지
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 화면을 비운다
		
		// 인트로 화면을 그린다
		g.drawImage(introIc.getImage(), -60, 0, /* this.getWidth(), this.getHeight(), */ null);

	}

}
