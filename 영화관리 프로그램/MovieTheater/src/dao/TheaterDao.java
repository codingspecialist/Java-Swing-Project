package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Theaters;

public class TheaterDao {
	private TheaterDao(){}
	private static TheaterDao instance = new TheaterDao();
	
	public static TheaterDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public Theaters selectOne(int id) {
		String sql ="SELECT * FROM THEATER WHERE ID = ?";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Theaters theater = new Theaters();
			if(rs.next()) {
				theater.setId(rs.getInt("ID"));
				theater.setName(rs.getString("NAME"));
				theater.setPlaceId(rs.getInt("PLACE_ID"));
				theater.setSeatId(rs.getInt("SEAT_ID"));

				conn.close();
				return theater;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Theaters> selectAll() {
		Vector<Theaters> theaters = new Vector<>();
		String sql ="SELECT T.ID, T.NAME, P.NAME AS PLACE_NAME, S.SEAT_TYPE AS SEAT_TYPE FROM THEATER T INNER JOIN PLACE P ON T.PLACE_ID = P.ID INNER JOIN SEAT S ON T.SEAT_ID = S.ID";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Theaters theater = new Theaters();
				theater.setId(rs.getInt("ID"));
				theater.setName(rs.getString("NAME"));
				theater.setPlaceName(rs.getString("PLACE_NAME"));
				theater.setSeatType(rs.getString("SEAT_TYPE"));
				theaters.add(theater);
			}
			conn.close();
			return theaters;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Theaters> selectKeyword(String keyword) {
		Vector<Theaters> theaters = new Vector<>();
		String sql ="SELECT T.ID, T.NAME, P.NAME AS PLACE_NAME, S.SEAT_TYPE AS SEAT_TYPE FROM THEATER T INNER JOIN PLACE P ON T.PLACE_ID = P.ID INNER JOIN SEAT S ON T.SEAT_ID = S.ID";
		sql += " WHERE T.NAME LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Theaters theater = new Theaters();
				theater.setId(rs.getInt("ID"));
				theater.setName(rs.getString("NAME"));
				theater.setPlaceName(rs.getString("PLACE_NAME"));
				theater.setSeatType(rs.getString("SEAT_TYPE"));
				theaters.add(theater);
			}
			conn.close();
			return theaters;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int insert(int placeId, String name, int seatId) {
		String sql ="INSERT INTO THEATER SELECT THEATER_SEQ.NEXTVAL, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 0 FROM THEATER WHERE NAME = ? AND PLACE_ID = ? AND SEAT_ID = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, placeId);
			pstmt.setInt(3, seatId);
			pstmt.setString(4, name);
			pstmt.setInt(5, placeId);
			pstmt.setInt(6, seatId);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int update(int id, int placeId, String name, int seatId) {
		String sql ="UPDATE THEATER SET NAME = ?, PLACE_ID = ?, SEAT_ID = ? WHERE ID = ? AND NOT EXISTS(SELECT 0 FROM THEATER WHERE NAME = ? AND PLACE_ID = ? AND SEAT_ID = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, placeId);
			pstmt.setInt(3, seatId);
			pstmt.setInt(4, id);
			pstmt.setString(5, name);
			pstmt.setInt(6, placeId);
			pstmt.setInt(7, seatId);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int delete(int id) {
		String sql ="DELETE FROM THEATER WHERE ID = ?";
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