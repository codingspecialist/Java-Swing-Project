package starz502Client.models;

public class Player {
	private String stz_username;
	private int x;
	private int y;
	private double angle;
	private int curHp;
	
	public Player() {
	}

	public Player(String stz_username, int x, int y, double angle, int curHp) {
		this.stz_username = stz_username;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.curHp = curHp;
	}

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getCurHp() {
		return curHp;
	}

	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}
	
}
