package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Seats;

public class SeatDao {
	private SeatDao(){}
	private static SeatDao instance = new SeatDao();
	
	public static SeatDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public Seats selectOne(int id) {
		String sql ="SELECT * FROM SEAT WHERE ID = ?";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Seats seat = new Seats();
			if(rs.next()) {
				seat.setId(rs.getInt("ID"));
				seat.setType(rs.getString("SEAT_TYPE"));
				seat.setRow(rs.getInt("SEAT_ROW"));
				seat.setCol(rs.getInt("SEAT_COL"));

				conn.close();
				return seat;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Seats> selectAll() {
		Vector<Seats> seats = new Vector<>();
		String sql ="SELECT * FROM SEAT";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Seats seat = new Seats();
				seat.setId(rs.getInt("ID"));
				seat.setType(rs.getString("SEAT_TYPE"));
				seat.setRow(rs.getInt("SEAT_ROW"));
				seat.setCol(rs.getInt("SEAT_COL"));
				seats.add(seat);
			}
			conn.close();
			return seats;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Seats> selectKeyword(String keyword) {
		Vector<Seats> seats = new Vector<>();
		String sql ="SELECT * FROM SEAT";
		sql += " WHERE SEAT_TYPE LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Seats seat = new Seats();
				seat.setId(rs.getInt("ID"));
				seat.setType(rs.getString("SEAT_TYPE"));
				seat.setRow(rs.getInt("SEAT_ROW"));
				seat.setCol(rs.getInt("SEAT_COL"));
				seats.add(seat);
			}
			conn.close();
			return seats;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int insert(String type, int row, int col) {
		String sql ="INSERT INTO SEAT SELECT SEAT_SEQ.NEXTVAL, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 0 FROM SEAT WHERE SEAT_TYPE = ? AND SEAT_ROW = ? AND SEAT_COL = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setInt(2, row);
			pstmt.setInt(3, col);
			pstmt.setString(4, type);
			pstmt.setInt(5, row);
			pstmt.setInt(6, col);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int update(int id, String type, int row, int col) {
		String sql ="UPDATE SEAT SET SEAT_TYPE = ?, SEAT_ROW = ?, SEAT_COL = ? WHERE ID = ? AND NOT EXISTS(SELECT 0 FROM SEAT WHERE SEAT_TYPE = ? AND SEAT_ROW = ? AND SEAT_COL = ?)";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setInt(2, row);
			pstmt.setInt(3, col);
			pstmt.setInt(4, id);
			pstmt.setString(5, type);
			pstmt.setInt(6, row);
			pstmt.setInt(7, col);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int delete(int id) {
		String sql ="DELETE FROM SEAT WHERE ID = ?";
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
	
	public Seats selectSeat(int placeId, int theaterId) {
		String sql ="SELECT SEAT_ROW, SEAT_COL FROM SEAT S";
		sql += " INNER JOIN THEATER T ON S.ID = T.SEAT_ID"; 
		sql += " INNER JOIN PLACE P ON T.PLACE_ID = P.ID"; 
		sql += " WHERE P.ID = ? AND T.ID = ?";
		
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, placeId);
			pstmt.setInt(2, theaterId);
			rs = pstmt.executeQuery();
			
			Seats seat = new Seats();
			if(rs.next()) {
				seat.setRow(rs.getInt("SEAT_ROW"));
				seat.setCol(rs.getInt("SEAT_COL"));

				conn.close();
				return seat;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}