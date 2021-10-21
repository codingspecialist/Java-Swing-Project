package jumpKing;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class 투명도 extends JPanel {
	ImageIcon bg = new ImageIcon("images/map2.jpg");
	private Image img = bg.getImage();

	
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, 1800, 900, 230, 2024, 1800, 3030, this);
		repaint();
	}
}



public class Stage extends JFrame{
private BgStage bgStage = new BgStage();
	
	
public Stage() {
	setTitle("원본 크기로 원하는 위치에 이미지 그리기");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setContentPane(bgStage);
	setSize(1800,3036);
	setVisible(true);

}

class BgStage extends JLabel{
	ImageIcon bgs = new ImageIcon("images/map2.jpg");
	private Image imgs = bgs.getImage();
	int mapLow = 2000;
	int mapHight = 2500;
// 3030, 2024
	protected void paintComponent(Graphics g) {
//		g.drawImage(imgs, 0, 0, 1800, 900, 230, yy, 1800, y, this);
		g.drawImage(imgs, 0, 0, 1800, 900, 0,mapLow, getWidth(), mapHight, this);
		repaint();
	}
	
	public BgStage() {
		setFocusable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					mapLow = 2500;
					mapHight = 3000;
					repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		setVisible(true);
	
	}
}
	

	
	
	
	
	public static void main(String[] args) {
		new Stage();
	}
}