package db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.spi.DirStateFactory.Result;

public class DBUtills {
	
	private final static String TAG = "DBUtills : ";
	
	public static void close (Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(TAG + "DB종료시 오류가 발생 : " + e.getMessage());
		}
	}
	
//	public static void close(Connection conn, PreparedStatement pstmt, Result result) {
//		try {
//			conn.close();
//			pstmt.close();
//			result.close();
//		} catch (Exception e) {
//			System.out.println(TAG + "DB종료시 오류가 발생 : " + e.getMessage());
//		}
//	}

}
