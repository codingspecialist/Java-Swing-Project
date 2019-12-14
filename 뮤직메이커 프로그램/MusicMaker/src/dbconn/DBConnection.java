package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@192.168.0.44:1521:xe";
		String id = "Music";
		String pw = "502";
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url , id , pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
