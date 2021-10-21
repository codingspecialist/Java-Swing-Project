package monster;

import java.util.Vector;

import javax.swing.JFrame;

import SpriteSheet.SpriteSheet;
import character.Isaac;
import objectSize.Gap;
import objectSize.ViewDirect;
import objectSize.WormSize;
import structure.Structure;

public class Worm extends Monster {
	private final static String GUBUN = "Worm : ";

	public Worm(JFrame app, Isaac isaac, Vector<Structure> Structures, String url, int imgWidth, int imgHeight) {
		super(app, isaac, Structures, url, imgWidth, imgHeight);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!isDead()) {
					if(getLife() <= 0) {
						setDead(true);
						break;
					}
					moveDirectCheck();
					collisionRock();
					moveUp();
					moveDown();
					moveRight();
					moveLeft();
					moveMotion();
					getSsMonster().drawObject(getXChar(), getYChar());
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(isAttacking() == false) {
						attack();
					}
				}
				if(isDead()) {
					dead();
				}
			}
		}).start();
	}
	
	public void init() {
		setSsMonster(new SpriteSheet(getUrl(), "monster", 0, 0, getImgWidth(), getImgHeight())); 
	}
	public void setting() {
		setViewDirect(ViewDirect.RIGHT);
		setXChar(180);
		setYChar(130);
		setXCenter(getXChar() + WormSize.WIDTH / 2); 
		setYCenter(getYChar() + WormSize.HEIGHT / 2); 
		setLife(20);
		setAttackDamge(1);
	}
	public void batch() {
		getSsMonster().drawObject(getXChar(), getYChar());
		getApp().add(getSsMonster(), 0);
	}
	
	public void attckCheck(int direct, int range) {
		setAttacking(true);
		int xDistance = getIsaac().getXCenter() - getXCenter();
		int yDistance = getIsaac().getYCenter() - getYCenter();
		double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
		if(distance < range) {
			attackMotion(direct - 1);
		}
	}
	@Override
	public void attack() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				switch (getViewDirect()) {
					case ViewDirect.DOWN:
						attckCheck(ViewDirect.DOWN, 53);
						break;	
					case ViewDirect.LEFT:
						attckCheck(ViewDirect.LEFT, 35);
						break;
					case ViewDirect.UP:
						attckCheck(ViewDirect.UP, 30);
						break;
					case ViewDirect.RIGHT:
						attckCheck(ViewDirect.RIGHT, 35);
						break;
				}
				setAttacking(false);
			} 
			
		}).start();
	}
	public void attackMotion(int direct) {
		getSsMonster().setXPos((WormSize.WIDTH * direct) + (Gap.COLUMNGAP * direct));
		getSsMonster().setYPos(WormSize.HEIGHT * 4 + Gap.ROWGAP * 4);
		getSsMonster().drawObject(getXChar(), getYChar());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSsMonster().setXPos(0);
		getSsMonster().setYPos(WormSize.HEIGHT * direct + Gap.ROWGAP * direct);
		getSsMonster().drawObject(getXChar(), getYChar());
		getIsaac().setLife(getIsaac().getLife() - 1);	// 플레이어 생명력 1감소
		getIsaac().reDrawLife();
		getIsaac().dead();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
