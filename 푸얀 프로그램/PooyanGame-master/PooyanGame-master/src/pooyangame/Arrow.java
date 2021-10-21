package pooyangame;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;





public class Arrow extends JLabel{
	private Arrow arrow = this;
	private static final String TAG = "Arrow : ";
	
	private ImageIcon icBow;
	private ImageIcon icBowFall;
	private PooyanApp pooyanApp;
	private Wolf wolf;
	private Pooyan pooyan;
	
	
	public boolean isIn = false;
	public boolean isRemove = false;
	public int x = 0;
	public int y = 0;
	
	
	
	public boolean isKill = true;
	public boolean isFall = false;
	
	public Arrow(PooyanApp pooyanApp, Wolf wolf, Pooyan pooyan) {
		this.pooyanApp = pooyanApp;
		this.wolf = wolf;
		this.pooyan = pooyan;
		icBow = new ImageIcon("images/bow.png");
		icBowFall = new ImageIcon("images/bowFall.png");
		setIcon(icBow);
		setSize(60, 5);
		setLocation(0,0);
		kill();
	}
	
	public void kill() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(isKill) {
					try {
						System.out.println(TAG+"킬 쓰레드 진행중");
						for (int i = 0; i < pooyanApp.wolves.size(); i++) {
							if(x>pooyanApp.wolves.get(i).x&&x<=pooyanApp.wolves.get(i).x+40) {
								if(y>=pooyanApp.wolves.get(i).y+10 && y<=pooyanApp.wolves.get(i).y+50) {
									System.out.println(TAG+"킬");
									isKill = false;
									pooyanApp.wolves.get(i).wolfStatus = false;
									pooyan.remove(arrow);
									pooyan.score = pooyan.score+200; // 점수 200점
									pooyanApp.laScore.setText(""+pooyan.score);
									pooyanApp.wolves.get(i).attackedFall();
									break;
								}  else if(y>pooyanApp.wolves.get(i).y+60 && y<=pooyanApp.wolves.get(i).y+100) {
									while(true) {
										isFall=true;
										System.out.println(TAG+"화살 추락");
										setSize(5,60);
										setIcon(icBowFall);
										y++;
										setLocation(x, y);
										Thread.sleep(1);
										if(y>490) {
											pooyan.remove(arrow);
											pooyanApp.repaint();
											isFall=false;
											isKill=false;
											break;
										}
									}
								}
								
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
