package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Combo;

public class ComboDao {
	private ComboDao(){}
	private static ComboDao instance = new ComboDao();
	
	public static ComboDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public Vector<Combo> setCombo(String comboContent) {
		Vector<Combo> combos = new Vector<>();
		String sql;
		
		if(comboContent.equals("place")) {
			sql = "SELECT ID AS KEY, NAME AS VALUE FROM PLACE";
		} else if(comboContent.equals("seat")) {
			sql = "SELECT ID AS KEY, SEAT_TYPE AS VALUE FROM SEAT";
		} else if(comboContent.equals("discount")) {
			sql = "SELECT ID AS KEY, NAME||' ('||VAL||UNIT||')' AS VALUE FROM DISCOUNT";
		} else if(comboContent.equals("movie")) {
			sql = "SELECT ID AS KEY, TITLE AS VALUE FROM MOVIE";
		} else {
			sql = "";
		}
		
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Combo combo = new Combo();
				combo.setKey(rs.getInt("KEY"));
				combo.setValue(rs.getString("VALUE"));
				combos.add(combo);
			}
			
			if(rs.getRow() == 0) {
				conn.close();
				return null;
			} else {
				conn.close();
				return combos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Combo> setCombo(String comboContent, int id) {
		Vector<Combo> combos = new Vector<>();
		String sql;
		
		sql = "SELECT ID AS KEY, NAME AS VALUE FROM THEATER WHERE PLACE_ID = ?";
		
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(comboContent.equals("theater")) {
				pstmt.setInt(1, id);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Combo combo = new Combo();
				combo.setKey(rs.getInt("KEY"));
				combo.setValue(rs.getString("VALUE"));
				combos.add(combo);
			}
			
			if(rs.getRow() == 0) {
				conn.close();
				return null;
			} else {
				conn.close();
				return combos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
