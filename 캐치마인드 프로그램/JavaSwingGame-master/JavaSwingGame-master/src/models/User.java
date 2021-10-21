package models;

import lombok.Data;

@Data
public class User {
	
	private int id;
	private String userName;
	private String password;

	public User(int id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public User (String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return id + ":" + userName + ":" + password;
	}
	

}
