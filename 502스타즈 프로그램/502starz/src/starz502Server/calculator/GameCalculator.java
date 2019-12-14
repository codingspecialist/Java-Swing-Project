package starz502Server.calculator;


import java.util.Vector;
import starz502Server.dao.ResultModelDAO;
import starz502Server.models.GameModel;
import starz502Server.models.ResultModel;

public class GameCalculator { // ���� ���� ����� �ϳ��� �����ؾ��ϱ⶧���� �̱������� �ۼ�
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

			for (int i = 0; i < gameModelForCalculator.size(); i++) {// �Ѿ� ��� ����
				for (int j = 0; j < gameModelForCalculator.get(i).getBulletList().size(); j++) {// �Ѿ� ����
					for (int k = 0; k < gameModelForCalculator.size(); k++) {// �Ѿ� �´� ����
						if (i != k) {// �� �Ѿ� �ȸ°�
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
			// ����� ResultModel ����
			for (int m = 0; m < 4; m++) {
				if (gameModelForCalculator.get(m).getPlayer().getCurHp() > 0) { // rank�� 1�� �ƴµ� ����ü���� 0 �̻��̸� �����
					gameModelForCalculator.get(m).getPlayer().setCurHp(0);
					ResultModelDAO resultModelDaoWinner = new ResultModelDAO();
					ResultModel resultModelWinner = new ResultModel();
					resultModelWinner = resultModelDaoWinner.resultUserData(gameModelForCalculator.get(m).getPlayer().getStz_username(), rank);
					resultModelList.add(resultModelWinner);
				}
			}
		} else {
			rank = 4; // ���� �������� �ʰ� 4���� ��� �κ�� ���ͼ� �ٽ� ������ ��츦 ���� rank ���� ����
						// �ʱ�ȭ
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
