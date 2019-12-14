package starz502Client.models;

public class LobbyModelUser {
	private String stz_username;
	private Integer stz_rating;
	private String stz_logstate;
	private Integer stz_level;
	private String stz_ready;
	
	public LobbyModelUser() {
	}

	public LobbyModelUser(String stz_username, Integer stz_rating, String stz_logstate, Integer stz_level,	String stz_ready) {
		this.stz_username = stz_username;
		this.stz_rating = stz_rating;
		this.stz_logstate = stz_logstate;
		this.stz_level = stz_level;
		this.stz_ready = stz_ready;
	}

	public String getStz_username() {
		return stz_username;
	}

	public void setStz_username(String stz_username) {
		this.stz_username = stz_username;
	}

	public Integer getStz_rating() {
		return stz_rating;
	}

	public void setStz_rating(Integer stz_rating) {
		this.stz_rating = stz_rating;
	}

	public String getStz_logstate() {
		return stz_logstate;
	}

	public void setStz_logstate(String stz_logstate) {
		this.stz_logstate = stz_logstate;
	}

	public Integer getStz_level() {
		return stz_level;
	}

	public void setStz_level(Integer stz_level) {
		this.stz_level = stz_level;
	}

	public String getStz_ready() {
		return stz_ready;
	}

	public void setStz_ready(String stz_ready) {
		this.stz_ready = stz_ready;
	}

}
