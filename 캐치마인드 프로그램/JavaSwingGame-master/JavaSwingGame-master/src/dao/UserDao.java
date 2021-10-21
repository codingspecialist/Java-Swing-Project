package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;
import db.DBUtills;
import models.User;

// 자바 디비 거점 
public class UserDao {

	private final static String TAG = "UserDao : ";

	public UserDao() {

	}

	private static UserDao instance = new UserDao();

	public static UserDao getInstance() {
		return instance;
	}

	public int 가입(User user) {

		final String SQL = "INSERT INTO users VALUES(users_seq.nextval, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(TAG + "UserName :" + user.getUserName());
		System.out.println(TAG + "Password :" + user.getPassword());
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);
//			 3. 물음표 완성
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			// 4. 쿼리전송(flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "추가오류 : " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtills.close(conn, pstmt);
		}
		return -1;
	}

	public int 확인(String userName) {
		final String SQL = "SELECT userName FROM users WHERE username = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userName);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return 0;
			} else {
				return 1;
			}

		} catch (Exception e) {
			System.out.println(TAG + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}

	public int 로그인(String userName, String password) {
		final String SQL = "SELECT userName FROM users WHERE username = ? AND password = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return 1;
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(TAG + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}

}
