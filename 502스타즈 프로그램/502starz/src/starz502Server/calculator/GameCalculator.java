package starz502Server.calculator;


import java.util.Vector;
import starz502Server.dao.ResultModelDAO;
import starz502Server.models.GameModel;
import starz502Server.models.ResultModel;

public class GameCalculator { // 게임 연산 모듈은 하나만 존재해야하기때문에 싱글톤으로 작성
	private Vector<GameModel> gameModelForCalculator;
	private Vector<ResultModel> resultModelList;
	private int rank;

	public GameCalculator() {
		gameModelForCalculator = new Vector<GameModel>();
		resultModelList = new Vector<ResultModel>();
		rank = 4;
	}

	public void calPlayerHitByBullet() {
		if (rank >= 2) {

			for (int i = 0; i < gameModelForCalculator.size(); i++) {// 총알 쏘는 유저
				for (int j = 0; j < gameModelForCalculator.get(i).getBulletList().size(); j++) {// 총알 갯수
					for (int k = 0; k < gameModelForCalculator.size(); k++) {// 총알 맞는 유저
						if (i != k) {// 내 총알 안맞게
							int userX = gameModelForCalculator.get(k).getPlayer().getX();
							int userY = gameModelForCalculator.get(k).getPlayer().getY();
							double bulletX = gameModelForCalculator.get(i).getBulletList().get(j).getX();
							double bulletY = gameModelForCalculator.get(i).getBulletList().get(j).getY();
							int bulletDmg = gameModelForCalculator.get(i).getBulletList().get(j).getDmg();
							int userCurHp = gameModelForCalculator.get(k).getPlayer().getCurHp();
							if (userX + 15 >= bulletX) {
								if (userX - 15 <= bulletX) {
									if (userY + 15 >= bulletY) {
										if (userY - 15 <= bulletY) {

											gameModelForCalculator.get(i).getBulletList().get(j).setDmg(0);

											if (userCurHp > 0) {
												gameModelForCalculator.get(k).getPlayer().setCurHp(userCurHp - bulletDmg);

												if (gameModelForCalculator.get(k).getPlayer().getCurHp() <= 0) {
													if (resultModelList.size() < 4) {
														ResultModelDAO resultModelDao = new ResultModelDAO();
														ResultModel resultModel = new ResultModel();
														resultModel = resultModelDao.resultUserData(gameModelForCalculator.get(k).getPlayer().getStz_username(), rank);

														resultModelList.add(resultModel);
													}
												}

											}

											break;

										}
									}
								}
							}
						}
					}
				}
			}
		} else if (rank == 1) {
			// 우승자 ResultModel 세팅
			for (int m = 0; m < 4; m++) {
				if (gameModelForCalculator.get(m).getPlayer().getCurHp() > 0) { // rank가 1이 됐는데 현재체력이 0 이상이면 우승자
					gameModelForCalculator.get(m).getPlayer().setCurHp(0);
					ResultModelDAO resultModelDaoWinner = new ResultModelDAO();
					ResultModel resultModelWinner = new ResultModel();
					resultModelWinner = resultModelDaoWinner.resultUserData(gameModelForCalculator.get(m).getPlayer().getStz_username(), rank);
					resultModelList.add(resultModelWinner);
				}
			}
		} else {
			rank = 4; // 서버 종료하지 않고 4명이 모두 로비로 나와서 다시 게임할 경우를 위해 rank 판정 숫자
						// 초기화
		}

	}

	public Vector<GameModel> getGameModelForCalculator() {
		return gameModelForCalculator;
	}

	public void setGameModelForCalculator(Vector<GameModel> gameModelForCalculator) {
		this.gameModelForCalculator = gameModelForCalculator;
	}

	public Vector<ResultModel> getResultModelList() {
		return resultModelList;
	}

	public void setResultModelList(Vector<ResultModel> resultModelList) {
		this.resultModelList = resultModelList;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
