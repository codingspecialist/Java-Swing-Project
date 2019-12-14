package dbconn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDB {
	private UserDB(){}
	private static UserDB instance = new UserDB();
	public static UserDB getInstance() {
		return instance;
	}
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//회원가입 완료 버튼(INSERT)
	public int save(User user) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("insert into member values(member_seq.nextval,?,?)");
			
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
				return -1;
		}
		
	}
	//로그인 버튼 (SELECT)
	public int findByUsernameAndPassword(String username , String password) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("select id from member where username = ? and password = ?");
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
}
