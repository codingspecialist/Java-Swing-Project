package starz502Client.models;

public class Bullet {
	private String stz_username;
	private double x;
	private double y;
	private double angle;
	private int dmg;
	
	public Bullet() {
	}

	public Bullet(String stz_username, double x, double y, double angle, int dmg) {
		super();
		this.stz_username = stz_username;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.dmg = dmg;
	}

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
}
