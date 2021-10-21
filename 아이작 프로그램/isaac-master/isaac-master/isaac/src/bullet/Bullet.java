package bullet;

import SpriteSheet.SpriteSheet;
import lombok.Data;
import objectSize.BulletSize;

@Data

public class Bullet {
	private final static String TAG = "Bullet : ";
	private SpriteSheet ssBullet;
	private String gubun; 	// 아이작의 불릿인지 적의 불릿인지 구분하기 위함
	private double attackDamage;	// Bullet Damage
	private int direct; // 쏘는 방향(캐릭터의 바라보는 방향) 1 : 아래, 2: 왼쪽, 3: 위쪽, 4 : 오른쪽
	private int xBullet, yBullet;	// 불릿 x, y 좌표
	private boolean isCollide, isPop;
	
	public Bullet(String gubun, double attackDamage, int direct, int xBullet, int yBullet) {
//		System.out.println(TAG+ gubun + " bullet 생성");
		this.gubun = gubun;
		String url = null;
		if(gubun == "isaac") {
			url = "bullet/isaacBullet.png";
		} else {
			url = "bullet/enemyBullet.png";
		}
		ssBullet = new SpriteSheet(url, gubun, 0, 0, BulletSize.WIDTH, BulletSize.HEIGHT);
		this.attackDamage = attackDamage;
		this.direct = direct;
		this.xBullet = xBullet;
		this.yBullet = yBullet;
		this.isCollide = false;
		this.isPop = false;
	}
	
}
