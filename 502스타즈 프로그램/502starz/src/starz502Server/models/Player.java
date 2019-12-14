package starz502Server.models;



public class Player {
	private int x;
	private int y;
	private String stz_username;
	private double angle;
	private int curHp;
	
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(int x, int y, String stz_username, double angle, int maxHp, int curHp) {
		super();
		this.x = x;
		this.y = y;
		this.stz_username = stz_username;
		this.angle = angle;
		this.curHp = curHp;
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

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
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
