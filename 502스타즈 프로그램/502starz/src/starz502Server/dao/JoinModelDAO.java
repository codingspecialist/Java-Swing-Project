package starz502Server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JoinModelDAO {
   public JoinModelDAO() {}


   private static Connection conn; // DB ���� ��ü
   private static PreparedStatement pstmt,pstmt2; // Query �ۼ� ��ü   
   private static ResultSet rs;

   
   public boolean saveNewUser(String username, String password) {
      System.out.println("savenewuser"+username+password);
      // 1. DB ����
      conn = DBConnection.getConnection();
      try {
         // 2. Query �ۼ�
         String checkQuery = "select * from stz_user where stz_username=?";
         pstmt= conn.prepareStatement(checkQuery);
         pstmt.setString(1, username);
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            System.out.println("�̹� �����ϴ� ���̵��Դϴ�.");
            return false;
         } else {
            System.out.println("��밡���� ���̵��Դϴ�.");
            String query = "insert into stz_user values (stz_id_seq.nextval,?,?,1,0,1000,0,0,0,'off','0') ";
            pstmt2 = conn.prepareStatement(query);
            pstmt2.setString(1, username);
            pstmt2.setString(2, password);
            pstmt2.executeUpdate();
            return true;
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      }      
      return false;
   }
}