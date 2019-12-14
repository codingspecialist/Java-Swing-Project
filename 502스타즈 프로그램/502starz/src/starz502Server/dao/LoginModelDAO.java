package starz502Server.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class LoginModelDAO {
   private  Connection conn; //DB ���� ��ü
   private  PreparedStatement pstmt, pstmt2; //Query �ۼ� ��ü
   private  ResultSet rs; //Query ��� Ŀ��
   
   public LoginModelDAO() {
      
   }

   

   
   public boolean loginCheck(String username, String password) {
      //1. DB ����
      conn = DBConnection.getConnection();      
      try {         
         //2. Query �ۼ�
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
               System.out.println("�̹� �α��εǾ� �ֽ��ϴ�.");
               return false; //�α��� ����
            }else {
               pstmt2.executeUpdate();
               return true; //�α��� ����
            }
         }else {
            return false;//���̵�, ��й�ȣ ����
         }
         
         
      } catch (Exception e) {
         e.printStackTrace();
      } 
      return false; //�α��� ����
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
	   return false;//�α׾ƿ� ����
   }
   
}