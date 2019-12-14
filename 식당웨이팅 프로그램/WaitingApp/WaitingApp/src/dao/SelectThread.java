package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import gui.CustomerMainFrame;

public class SelectThread extends Thread {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public void run() {
			int rowcount = 0;
			conn = DBConnectionCuntomer.getConnection();
		while(true) {
		   
		   try {
			   pstmt = conn.prepareStatement("SELECT COUNT(CASE WHEN state='입장 대기' THEN 1 END) count FROM ccs");
			   rs=pstmt.executeQuery();
			   rs.next();
			   rowcount = rs.getInt("count");
			   System.out.println(rowcount);
			   CustomerMainFrame.wait.setText(rowcount+"");
			   
			   try {
				sleep(1000);
			   }
			   
			   catch (Exception e) {}

		   }
		   
		   catch (Exception e) {
			   e.printStackTrace();}
		}
	}
	
}



