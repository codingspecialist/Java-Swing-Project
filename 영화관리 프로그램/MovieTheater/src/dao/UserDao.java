package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Users;

public class UserDao {
	private UserDao() {
	}

	private static UserDao instance = new UserDao();

	public static UserDao getInstance() {
		return instance;
	}

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public Users selectOne(String userId) {
		String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			Users user = new Users();
			if (rs.next()) {
				user.setId(rs.getInt("ID"));
				user.setUserId(rs.getString("USER_ID"));
				user.setBirthDate(rs.getInt("BIRTH_DATE"));
				user.setPhone(rs.getString("PHONE"));
				user.setPrivacyFg(rs.getString("PRIVACY_FG"));
				user.setAdminFg(rs.getString("ADMIN_FG"));
				user.setInsDt(rs.getString("INS_DT"));
				user.setDelFg(rs.getString("DEL_FG"));
				user.setDelDt(rs.getString("DEL_DT"));

				conn.close();
				return user;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Users> selectAll(String keyword) {
		Vector<Users> users = new Vector<>();
		String sql = "SELECT * FROM USERS WHERE USER_ID LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("ID"));
				user.setUserId(rs.getString("USER_ID"));
				user.setBirthDate(rs.getInt("BIRTH_DATE"));
				user.setPhone(rs.getString("PHONE"));
				user.setPrivacyFg(rs.getString("PRIVACY_FG"));
				user.setInsDt(rs.getString("INS_DT"));
				user.setDelFg(rs.getString("DEL_FG"));
				user.setDelDt(rs.getString("DEL_DT"));
				users.add(user);
			}
			conn.close();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int updatePassword(String userId) {
		String sql = "UPDATE USERS SET PASSWORD = PHONE WHERE USER_ID = ?";

		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int updateDel(String userId) {
		String sql = "UPDATE USERS SET DEL_FG = 'Y', DEL_DT = SYSDATE WHERE USER_ID = ?";

		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Users selectBirth(String userId) {
		String sql = "SELECT BIRTH_DATE FROM USERS WHERE USER_ID = ?";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			Users user = new Users();
			if (rs.next()) {
				user.setBirthDate(rs.getInt("BIRTH_DATE"));

				conn.close();
				return user;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
