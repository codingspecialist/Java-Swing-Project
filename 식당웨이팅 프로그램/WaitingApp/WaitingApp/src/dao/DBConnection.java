package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
   
   //url, id, pw�� �˾ƾ� �����ͺ��̽� ���ٰ���
   public static Connection getConnection() {
      
      Connection conn = null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         conn = DriverManager.getConnection(
               "jdbc:oracle:thin:@192.168.0.35:1521:XE",
               "ccs",
               "1234"
               );
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      
      return conn;
   }
}
