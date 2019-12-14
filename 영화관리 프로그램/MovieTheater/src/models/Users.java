package models;

public class Users {

   private int id;
   private String userId;
   private String password;
   private int birthDate;
   private String phone;
   private String privacyFg;
   private String adminFg;
   private String insDt;
   private String delFg;
   private String delDt;
   
   public Users() {}

	public Users(int id, String userId, String password, int birthDate, String phone, String privacyFg, String adminFg,
			String insDt, String delFg, String delDt) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.birthDate = birthDate;
		this.phone = phone;
		this.privacyFg = privacyFg;
		this.adminFg = adminFg;
		this.insDt = insDt;
		this.delFg = delFg;
		this.delDt = delDt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrivacyFg() {
		return privacyFg;
	}

	public void setPrivacyFg(String privacyFg) {
		this.privacyFg = privacyFg;
	}

	public String getAdminFg() {
		return adminFg;
	}

	public void setAdminFg(String adminFg) {
		this.adminFg = adminFg;
	}

	public String getInsDt() {
		return insDt;
	}

	public void setInsDt(String insDt) {
		this.insDt = insDt;
	}

	public String getDelFg() {
		return delFg;
	}

	public void setDelFg(String delFg) {
		this.delFg = delFg;
	}

	public String getDelDt() {
		return delDt;
	}

	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}
}