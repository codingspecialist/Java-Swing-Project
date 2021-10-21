package pooyangame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Meat extends JLabel {
	private static final String TAG = "Meat : ";

	private ImageIcon icMeat;
	//
	
	//public boolean isIn = false;
	public int x = 0;
	public int y = 0;
	
	public PooyanApp pooyanApp;
	public Pooyan pooyan;
	public Wolf wolf;

	public boolean isKill = false;
	public int stack=0;
	
	public Meat(PooyanApp pooyanApp, Wolf wolf, Pooyan pooyan) {
		this.pooyanApp = pooyanApp;
		this.wolf = wolf;
		this.pooyan = pooyan;
		icMeat = new ImageIcon("images/meat.png");
		setIcon(icMeat);
		setSize(160, 90);
		setLocation(496, 70);
	}

	public void kill() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isKill) {
					try {
						System.out.println(TAG + "킬 쓰레드 진행중");
						for (int i = 0; i < pooyanApp.wolves.size(); i++) {
							if (pooyan.meatX >= pooyanApp.wolves.get(i).x - 40
									&& pooyan.meatX  <= pooyanApp.wolves.get(i).x + 50) {
								if (pooyan.meatY >= pooyanApp.wolves.get(i).y
										&& pooyan.meatY <= pooyanApp.wolves.get(i).y + 70) {
									if (pooyanApp.wolves.get(i).wolfStatus == true) {
										pooyanApp.wolves.get(i).wolfStatus = false;
										stack++;
										if(stack>=3) {
											pooyan.score = pooyan.score +2000;
											pooyanApp.laScore.setText(""+pooyan.score);
										} else {
											pooyan.score = pooyan.score + 400*stack;
											pooyanApp.laScore.setText(""+pooyan.score);
										}
										pooyanApp.wolves.get(i).attackedFall();
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