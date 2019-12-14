package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Places;

public class PlaceDao {
   private PlaceDao(){}
   private static PlaceDao instance = new PlaceDao();
   
   public static PlaceDao getInstance() {
      return instance;
   }
   
   private static Connection conn;
   private static PreparedStatement pstmt;
   private static ResultSet rs;
   
   public Places selectOne(int id) {
      String sql ="SELECT * FROM PLACE WHERE ID = ?";
      conn = DBConnection.getConnection();
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, id);
         rs = pstmt.executeQuery();
         
         Places place = new Places();
         if(rs.next()) {
            place.setId(rs.getInt("ID"));
            place.setName(rs.getString("NAME"));
            place.setAddr(rs.getString("ADDR"));
            
            conn.close();
            return place;
         } else {
            conn.close();
            return null;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return null;
   }
   
   public Vector<Places> selectAll() {
      Vector<Places> places = new Vector<>();
      String sql ="SELECT * FROM PLACE";
      conn = DBConnection.getConnection();
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         while(rs.next()){
            Places place = new Places();
            place.setId(rs.getInt("ID"));
            place.setName(rs.getString("NAME"));
            place.setAddr(rs.getString("ADDR"));
            places.add(place);
         }
         conn.close();
         return places;
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return null;
   }
   
   public Vector<Places> selectKeyword(String keyword) {
      Vector<Places> places = new Vector<>();
      String sql ="SELECT * FROM PLACE";
      sql += " WHERE NAME LIKE '%" + keyword + "%'";
      conn = DBConnection.getConnection();
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         while(rs.next()){
            Places place = new Places();
            place.setId(rs.getInt("ID"));
            place.setName(rs.getString("NAME"));
            place.setAddr(rs.getString("ADDR"));
            places.add(place);
         }
         conn.close();
         return places;
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return null;
   }
   
   public int insert(String name, String addr) {
      String sql ="INSERT INTO PLACE VALUES (PLACE_SEQ.NEXTVAL, ?, ?)";
      conn = DBConnection.getConnection();
      try {
         int returnCnt = 0;
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, name);
         pstmt.setString(2, addr);
         
         returnCnt = pstmt.executeUpdate();
         
         conn.close();
         return returnCnt;
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return -1;
   }
   
   public int update(int id, String name, String addr) {
      String sql ="UPDATE PLACE SET NAME = ?, ADDR = ? WHERE ID = ? AND NOT EXISTS(SELECT 0 FROM PLACE WHERE NAME = ? AND ADDR = ? )";
      conn = DBConnection.getConnection();
      try {
         int returnCnt = 0;
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, name);
         pstmt.setString(2, addr);
         pstmt.setInt(3, id);
         pstmt.setString(4, name);
         pstmt.setString(5, addr);
         returnCnt = pstmt.executeUpdate();
         
         conn.close();
         return returnCnt;
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return -1;
   }
   
   public int delete(int id) {
      String sql ="DELETE FROM PLACE WHERE ID = ?";
      conn = DBConnection.getConnection();
      try {
         int returnCnt = 0;
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, id);
         returnCnt = pstmt.executeUpdate();
         
         conn.close();
         return returnCnt;
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return -1;
   }
}