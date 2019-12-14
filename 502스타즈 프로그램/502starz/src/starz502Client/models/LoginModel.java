package starz502Client.models;

import starz502Client.data.DataTypes;

public class LoginModel{
	private Integer datatype = DataTypes.LOGIN;
	private String stz_username;
	private String stz_password;
	
	/*朔 持失切*/
	public LoginModel() {} 

	/*持失切*/
	public LoginModel(Integer datatype, String stz_username, String stz_password) {
		super();
		this.datatype = datatype;
		this.stz_username = stz_username;
		this.stz_password = stz_password;
	}
	
	/*Getter, Setter*/
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

	public String getStz_password() {
		return stz_password;
	}

	public void setStz_password(String stz_password) {
		this.stz_password = stz_password;
	}
	
	
	
}
