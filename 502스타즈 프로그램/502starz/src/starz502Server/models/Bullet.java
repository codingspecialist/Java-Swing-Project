package starz502Server.models;

public class Bullet {
	private double x;
	private double y;
	private double angle;
	private String stz_username;
	private int dmg;
	
	public Bullet() {
		// TODO Auto-generated constructor stub
	}

	public Bullet(double x, double y, double angle, String stz_username, int dmg) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.stz_username = stz_username;
		this.dmg = dmg;
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

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	
	
}
