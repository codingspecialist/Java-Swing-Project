package bullet;

import java.util.Vector;

import javax.swing.JFrame;
import character.Isaac;
import lombok.Data;
import monster.Monster;
import objectSize.BulletSize;
import objectSize.Gap;
import objectSize.IsaacSize;
import objectSize.StructureSize;
import objectSize.ViewDirect;
import structure.Rock;

@Data

public class BulletControl {
	private final static String TAG = "BulletControl : ";

	private Vector<Bullet> bullets = new Vector<Bullet>();
	private JFrame app;
	private Vector<Rock> rock;
	private boolean delayBullet = false;
	private boolean isAttacking = false;
	
	private Isaac isaac;
	private Vector<Monster> monster;
	
	public BulletControl(JFrame app, Vector<Rock> rock, Isaac isaac, Vector<Monster> monster) {
		this.app = app;
		this.rock = rock;
		this.isaac = isaac;
		this.monster = monster;
	}
	
	public void addBullet(String gubun, double attackDamage, int direct, int xBullet, int yBullet) {
		if(!delayBullet) {
			if(direct == ViewDirect.DOWN) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 15, yBullet + 18));
			} else if(direct == ViewDirect.LEFT) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet - 13, yBullet + 12));
			} else if(direct == ViewDirect.UP) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 15, yBullet - 14));
			} else {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 26, yBullet + 12));
			}
			drawBullet();
			// 불릿을 쏘고 난뒤 다시 쏠수 있도록 바꾸는 부분
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						delayBullet = true;
						Thread.sleep(300);
						delayBullet = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
				}
			}).start();;
		}
	}
	public void drawBullet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isAttacking == false) {
					isAttacking = true;
					int collisionBulletCount = 0;
					while(isAttacking) {
						if(bullets.size() != 0) {
							for (int i = 0; i < bullets.size(); i++) {
								// 벽 충돌 검사
								if(bullets.get(i).getXBullet() < 110 || bullets.get(i).getXBullet() > 835 || bullets.get(i).getYBullet() < 90 || bullets.get(i).getYBullet() > 500) {
									if(!bullets.get(i).isCollide()) {
										collisionBulletCount++;
									}
									bullets.get(i).setCollide(true);
								}
								// 바위 충돌 검사.
								for(int j = 0; j < rock.size(); j++) {
									if(bullets.get(i).getXBullet() + BulletSize.WIDTH > rock.get(j).getXStructure() && bullets.get(i).getXBullet() < rock.get(j).getXStructure() + StructureSize.WIDTH 
										&& bullets.get(i).getYBullet() + BulletSize.HEIGHT > rock.get(j).getYStructure() && bullets.get(i).getYBullet() < rock.get(j).getYStructure() + StructureSize.HEIGHT) {
										if(!bullets.get(i).isCollide()) {
											collisionBulletCount++;
										}
										bullets.get(i).setCollide(true);
									}
								}
								// 몹 or 플레이어 충돌
								if(bullets.get(i).getGubun() == "isaac"){
									for(int j = 0; j < monster.size(); j++) {
										if(!monster.get(j).isDead()) {
											if(bullets.get(i).getXBullet() + BulletSize.WIDTH > monster.get(j).getXChar() && bullets.get(i).getXBullet() < monster.get(j).getXChar() + monster.get(j).getImgWidth()
													&& bullets.get(i).getYBullet() + BulletSize.HEIGHT > monster.get(j).getYChar() && bullets.get(i).getYBullet() < monster.get(j).getYChar() + monster.get(j).getImgHeight() - 10) {
													if(!bullets.get(i).isCollide()) {
														collisionBulletCount++;
														monster.get(j).setLife(monster.get(j).getLife() - 1); // 생명력 감소
														System.out.println(monster.get(j).getLife());
													}
													bullets.get(i).setCollide(true);
												}
										}
									}
								}else {
									if(bullets.get(i).getXBullet() + BulletSize.WIDTH > isaac.getXChar() && bullets.get(i).getXBullet() < isaac.getXChar() + IsaacSize.HEADWIDTH
											&& bullets.get(i).getYBullet() + BulletSize.HEIGHT > isaac.getYChar() && bullets.get(i).getYBullet() < isaac.getYChar() + IsaacSize.HEADHEIGHT) {
											if(!bullets.get(i).isCollide()) {
												collisionBulletCount++;
												isaac.setLife(isaac.getLife() - 1); // 생명력 감소
												isaac.reDrawLife();
											}
											bullets.get(i).setCollide(true);
										}
								}
								
								// 충돌이 아니면 계속 그림
								drawCheckBullet(bullets.get(i));
							}
							if(collisionBulletCount == bullets.size()) {
								isAttacking = false;
								collisionBulletCount = 0;
								bullets.removeAllElements();
							}
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
				
			}
		}).start();
	}
	
	public void drawCheckBullet(Bullet bullet) {
		if(!bullet.isCollide()) {
			if(bullet.getDirect() == ViewDirect.DOWN) {
				bullet.setYBullet(bullet.getYBullet() + 1);
			} else if(bullet.getDirect() == ViewDirect.LEFT) {
				bullet.setXBullet(bullet.getXBullet() - 1);
			} else if(bullet.getDirect() == ViewDirect.UP) {
				bullet.setYBullet(bullet.getYBullet() - 1);
			} else {	// 보는 방향 오른쪽
				bullet.setXBullet(bullet.getXBullet() + 1);
			}
			app.add(bullet.getSsBullet(), 0);
			bullet.getSsBullet().drawObject(bullet.getXBullet(), bullet.getYBullet());
		} else {
			// 충돌 시 불릿 제거
			if(!bullet.isPop()) {
				removingMotion(bullet);
			}
		}
	}
	
	public void removingMotion(Bullet bullet) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				bullet.setPop(true);
				bullet.getSsBullet().setYPos(21);
				bullet.getSsBullet().setWidth(BulletSize.POPWIDTH);
				bullet.getSsBullet().setHeight(BulletSize.POPHEIGHT);
				int rowCount = 0;
				for(int i = 0; i < 12; i ++) {
					if(i % 4 == 0 && i > 0) {
						rowCount += 1;
					}
					int x = BulletSize.POPWIDTH * (i % 4) + Gap.COLUMNGAP * (i % 4);
					int y = (BulletSize.HEIGHT + Gap.ROWGAP) + (BulletSize.POPHEIGHT * rowCount)  + (Gap.ROWGAP * rowCount);
					
					bullet.getSsBullet().setXPos(x);
					bullet.getSsBullet().setYPos(y);
					bullet.getSsBullet().drawObject(bullet.getXBullet() - 20, bullet.getYBullet() - 20);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				app.remove(bullet.getSsBullet());
				app.repaint();
			}
		}).start();

	}
	// 구현되야 하는 것들
	// 몹 충돌
	// 구조물 충돌
}
