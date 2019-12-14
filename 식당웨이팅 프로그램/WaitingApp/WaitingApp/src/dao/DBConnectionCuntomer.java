package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionCuntomer {
   
   //url, id, pw를 알아야 데이터베이스 접근가능
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
