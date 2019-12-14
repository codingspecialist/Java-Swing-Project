package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Discounts;

public class DiscountDao {
	private DiscountDao(){}
	private static DiscountDao instance = new DiscountDao();
	
	public static DiscountDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public Discounts selectOne(int id) {
		String sql ="SELECT * FROM DISCOUNT WHERE ID = ?";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Discounts discount = new Discounts();
			if(rs.next()) {
				discount.setId(rs.getInt("ID"));
				discount.setName(rs.getString("NAME"));
				discount.setVal(rs.getInt("VAL"));
				discount.setUnit(rs.getString("UNIT"));

				conn.close();
				return discount;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Discounts> selectAll() {
		Vector<Discounts> discounts = new Vector<>();
		String sql ="SELECT * FROM DISCOUNT";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Discounts discount = new Discounts();
				discount.setId(rs.getInt("ID"));
				discount.setName(rs.getString("NAME"));
				discount.setVal(rs.getInt("VAL"));
				discount.setUnit(rs.getString("UNIT"));
				discounts.add(discount);
			}
			conn.close();
			return discounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Discounts> selectKeyword(String keyword) {
		Vector<Discounts> discounts = new Vector<>();
		String sql ="SELECT * FROM DISCOUNT";
		sql += " WHERE NAME LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Discounts discount = new Discounts();
				discount.setId(rs.getInt("ID"));
				discount.setName(rs.getString("NAME"));
				discount.setVal(rs.getInt("VAL"));
				discount.setUnit(rs.getString("UNIT"));
				discounts.add(discount);
			}
			conn.close();
			return discounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int insert(String name, int val, String unit) {
		String sql ="INSERT INTO DISCOUNT SELECT DISCOUNT_SEQ.NEXTVAL, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 0 FROM DISCOUNT WHERE NAME = ? AND VAL = ? AND UNIT = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, val);
			pstmt.setString(3, unit);
			pstmt.setString(4, name);
			pstmt.setInt(5, val);
			pstmt.setString(6, unit);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int update(int id, String name, int val, String unit) {
		String sql ="UPDATE DISCOUNT SET NAME = ?, VAL = ?, UNIT = ? WHERE ID = ? AND NOT EXISTS(SELECT 0 FROM DISCOUNT WHERE NAME = ? AND VAL = ? AND UNIT = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, val);
			pstmt.setString(3, unit);
			pstmt.setInt(4, id);
			pstmt.setString(5, name);
			pstmt.setInt(6, val);
			pstmt.setString(7, unit);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int delete(int id) {
		String sql ="DELETE FROM DISCOUNT WHERE ID = ?";
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
