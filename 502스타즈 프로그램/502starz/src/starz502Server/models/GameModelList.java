package starz502Server.models;

import java.util.Vector;

public class GameModelList {
	private Vector<GameModel> gameModelList;
	
	public GameModelList() {
		// TODO Auto-generated constructor stub
	}

	public GameModelList(Vector<GameModel> gameModelList) {
		super();
		this.gameModelList = gameModelList;
	}

	public Vector<GameModel> getGameModelList() {
		return gameModelList;
	}

	public void setGameModelList(Vector<GameModel> gameModelList) {
		this.gameModelList = gameModelList;
	}
	
	
}
