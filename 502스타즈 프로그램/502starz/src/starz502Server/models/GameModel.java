package starz502Server.models;

import java.util.Vector;

import starz502Server.data.DataTypes;

public class GameModel {
	private Integer datatype = DataTypes.GAME;
	private Player player;
	private Vector<Bullet> bulletList;
	
	public GameModel() {
		// TODO Auto-generated constructor stub
	}

	public GameModel(Integer datatype, Player player, Vector<Bullet> bulletList) {
		super();
		this.datatype = datatype;
		this.player = player;
		this.bulletList = bulletList;
	}

	public Integer getDatatype() {
		return datatype;
	}

	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Vector<Bullet> getBulletList() {
		return bulletList;
	}

	public void setBulletList(Vector<Bullet> bulletList) {
		this.bulletList = bulletList;
	}

	
}
