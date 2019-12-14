package starz502Server.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class LoginModelDAO {
   private  Connection conn; //DB 연결 객체
   private  PreparedStatement pstmt, pstmt2; //Query 작성 객체
   private  ResultSet rs; //Query 결과 커서
   
   public LoginModelDAO() {
      
   }

   

   
   public boolean loginCheck(String username, String password) {
      //1. DB 연결
      conn = DBConnection.getConnection();      
      try {         
         //2. Query 작성
         String query = "select * from stz_user where stz_username=? and stz_password =?";
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1,username);
         pstmt.setString(2,password);
         
         rs = pstmt.executeQuery();
         
         String updateQuery = "update stz_user set stz_logstate='on' where stz_logstate='off'and stz_username=?";
         pstmt2 = conn.prepareStatement(updateQuery);
         pstmt2.setString(1, username);
         if (rs.next()) {
            if(rs.getString("stz_logstate").equals("on")) {
               System.out.println("이미 로그인되어 있습니다.");
               return false; //로그인 실패
            }else {
               pstmt2.executeUpdate();
               return true; //로그인 성공
            }
         }else {
            return false;//아이디, 비밀번호 없음
         }
         
         
      } catch (Exception e) {
         e.printStackTrace();
      } 
      return false; //로그인 실패
   }
   
   public boolean logoutCheck(String username){
	   conn = DBConnection.getConnection();
	   System.out.println("checkmethod: '"+username+"'");
	   try {
		String logoutQuery = "update stz_user set stz_logstate = 'off' where stz_username = ?";
		pstmt = conn.prepareStatement(logoutQuery);
		pstmt.setString(1, username);
		pstmt.executeUpdate();
	} catch (Exception e) {
			System.out.println("3000");
		e.printStackTrace();
	}
	   return false;//로그아웃 실패
   }
   
}