package starz502Server.calculator;

import starz502Server.models.ResultModel;

public class ResultCalculator {
	
	public ResultCalculator() {

	}
	
	public ResultModel resultCalculator(ResultModel resultModel, int rank) {
		switch (rank) {
		case 1:
			resultModel.setStz_wexp(resultModel.getStz_wexp()+10);
			resultModel.setStz_exp(resultModel.getStz_wexp()%100);
			resultModel.setStz_level(resultModel.getStz_wexp()/100);
			resultModel.setStz_rating(resultModel.getStz_rating()+30);
			break;
		case 2:
			resultModel.setStz_wexp(resultModel.getStz_wexp()+10);
			resultModel.setStz_exp(resultModel.getStz_wexp()%100);
			resultModel.setStz_level(resultModel.getStz_wexp()/100);
			resultModel.setStz_rating(resultModel.getStz_rating()+20);
			break;
		case 3:
			resultModel.setStz_wexp(resultModel.getStz_wexp()+10);
			resultModel.setStz_exp(resultModel.getStz_wexp()%100);
			resultModel.setStz_level(resultModel.getStz_wexp()/100);
			resultModel.setStz_rating(resultModel.getStz_rating());
			break;
		case 4:
			resultModel.setStz_wexp(resultModel.getStz_wexp()+10);
			resultModel.setStz_exp(resultModel.getStz_wexp()%100);
			resultModel.setStz_level(resultModel.getStz_wexp()/100);
			resultModel.setStz_rating(resultModel.getStz_rating()-10);
			break;
		}
		return resultModel;
	}
	
}
