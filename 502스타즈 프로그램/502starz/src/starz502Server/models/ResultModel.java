package starz502Server.models;

import starz502Server.data.DataTypes;

public class ResultModel {
	private Integer datatype = DataTypes.RESULT;
	private String stz_username;
	private Integer rank; // 한판 한판 순위 (필요없을듯)
	private Integer stz_level;
	private Integer stz_rating;
	private Integer stz_exp;
	private Integer stz_wexp;
	
	public ResultModel() {
		// TODO Auto-generated constructor stub
	}

	public ResultModel(Integer datatype, String stz_username, Integer rank, Integer stz_level, Integer stz_rating,
			Integer stz_exp, Integer stz_wexp) {
		super();
		this.datatype = datatype;
		this.stz_username = stz_username;
		this.rank = rank;
		this.stz_level = stz_level;
		this.stz_rating = stz_rating;
		this.stz_exp = stz_exp;
		this.stz_wexp = stz_wexp;
	}

	public Integer getDatatype() {
		return datatype;
	}

	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getStz_level() {
		return stz_level;
	}

	public void setStz_level(Integer stz_level) {
		this.stz_level = stz_level;
	}

	public Integer getStz_rating() {
		return stz_rating;
	}

	public void setStz_rating(Integer stz_rating) {
		this.stz_rating = stz_rating;
	}

	public Integer getStz_exp() {
		return stz_exp;
	}

	public void setStz_exp(Integer stz_exp) {
		this.stz_exp = stz_exp;
	}

	public Integer getStz_wexp() {
		return stz_wexp;
	}

	public void setStz_wexp(Integer stz_wexp) {
		this.stz_wexp = stz_wexp;
	}	
}
