package db;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

// 연결
public class DBConnection {
	
	private final static String TAG = "DBConnection : ";
	
	Connection conn;
	Statement stmt;
	
	public static Connection getConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
					DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "chat1_1", "bitc5600");
			return conn;
		} catch (Exception e) {
			System.out.println(TAG + "DB 연결 실패" + e.getMessage());
		}
		return null;
	}
	
}
