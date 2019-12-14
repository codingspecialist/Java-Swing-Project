package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Sales;

public class SalesDao {
	private SalesDao() {}

	private static SalesDao instance = new SalesDao();

	public static SalesDao getInstance() {
		return instance;
	}

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private String sql;

	public Vector<Sales> selectMovie(String keyword) {
		Vector<Sales> sales = new Vector<>();
		sql = "SELECT M.TITLE 영화명, SUM(R.PRICE) 총액 FROM RESERVE R";
		sql += " INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " WHERE M.TITLE LIKE '%" + keyword + "%' GROUP BY M.TITLE";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Sales sale = new Sales();
				sale.setTitle(rs.getString("영화명"));
				sale.setPrice(rs.getInt("총액"));
				sales.add(sale);
			}
		
			conn.close();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Sales> selectMovieAll() {
		Vector<Sales> sales = new Vector<>();
		sql = "SELECT M.TITLE 영화명, SUM(R.PRICE) 총액 FROM RESERVE R";
		sql += " INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID GROUP BY M.TITLE";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Sales sale = new Sales();
				sale.setTitle(rs.getString("영화명"));
				sale.setPrice(rs.getInt("총액"));
				sales.add(sale);
			}
			
			conn.close();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Vector<Sales> selectPlace(String keyword) {
      Vector<Sales> sales = new Vector<>();
      sql = "SELECT P.NAME 영화관명, SUM(R.PRICE) 총액 FROM RESERVE R";
      sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
      sql += " WHERE P.NAME LIKE '%" + keyword + "%' GROUP BY P.NAME";
      conn = DBConnection.getConnection();

      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            Sales sale = new Sales();
            sale.setName(rs.getString("영화관명"));
            sale.setPrice(rs.getInt("총액"));
            sales.add(sale);
         }
         
         conn.close();
         return sales;
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }

   public Vector<Sales> selectPlaceAll() {
      Vector<Sales> sales = new Vector<>();
      sql = "SELECT P.NAME 영화관명, SUM(R.PRICE) 총액 FROM RESERVE R";
      sql += " INNER JOIN PLACE P ON R.ID = P.ID GROUP BY P.NAME";
      conn = DBConnection.getConnection();
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            Sales sale = new Sales();
            sale.setName(rs.getString("영화관명"));
            sale.setPrice(rs.getInt("총액"));
            sales.add(sale);
         }
    
         conn.close();
         return sales;
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }
}
