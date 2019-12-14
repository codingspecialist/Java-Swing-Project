package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Movies;

public class ScreenDao {
	private ScreenDao(){}
	private static ScreenDao instance = new ScreenDao();
	
	public static ScreenDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public Movies selectOne(int id) {
		String sql = "SELECT S.ID AS SCREENID, S.MOVIE_ID AS ID, M.TITLE, S.PLACE_ID AS PLACEID, P.NAME AS PLACENAME, S.THEATER_ID AS THEATERID, T.NAME AS THEATERNAME,"; 
		sql += " S.START_DATE AS STARTDATE, S.END_DATE AS ENDDATE, S.START_TIME AS STARTTIME";
		sql += " FROM SCREEN S";
		sql += " INNER JOIN MOVIE M ON M.ID = S.MOVIE_ID";
		sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID";
		sql += " WHERE S.ID = ?";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			Movies movie = new Movies();
			if (rs.next()) {
				movie.setId(rs.getInt("ID"));
				movie.setScreenId(rs.getInt("SCREENID"));
				movie.setPlaceId(rs.getInt("PLACEID"));
				movie.setPlaceName(rs.getString("PLACENAME"));
				movie.setTheaterId(rs.getInt("THEATERID"));
				movie.setTheaterName(rs.getString("THEATERNAME"));
				movie.setStartDate(rs.getString("STARTDATE"));
				movie.setEndDate(rs.getString("ENDDATE"));
				movie.setStartTime(rs.getString("STARTTIME"));
				conn.close();
				return movie;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Movies> selectAll() {
		Vector<Movies> movies = new Vector<>();
		String sql = "SELECT S.ID AS SCREENID, S.MOVIE_ID AS ID, M.TITLE, S.PLACE_ID AS PLACEID, P.NAME AS PLACENAME, S.THEATER_ID AS THEATERID, T.NAME AS THEATERNAME,";
		sql += " S.START_DATE AS STARTDATE, S.END_DATE AS ENDDATE, S.START_TIME AS STARTTIME";
		sql += " FROM SCREEN S";
		sql += " INNER JOIN MOVIE M ON M.ID = S.MOVIE_ID";
		sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setScreenId(rs.getInt("SCREENID"));
				movie.setPlaceId(rs.getInt("PLACEID"));
				movie.setPlaceName(rs.getString("PLACENAME"));
				movie.setTheaterId(rs.getInt("THEATERID"));
				movie.setTheaterName(rs.getString("THEATERNAME"));
				movie.setStartDate(rs.getString("STARTDATE"));
				movie.setEndDate(rs.getString("ENDDATE"));
				movie.setStartTime(rs.getString("STARTTIME"));
				movies.add(movie);
			}

			conn.close();

			return movies;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Movies> selectKeyword(String keyword) {
		Vector<Movies> movies = new Vector<>();
		String sql = "SELECT M.ID AS ID, M.TITLE AS TITLE, M.PRICE AS PRICE, M.AGE AS AGE, M.RUNNING_TIME AS RUNNING_TIME, S.PLACE_ID AS PLACEID, S.THEATER_ID AS THEATERID, S.START_DATE AS STARTDATE, S.END_DATE AS ENDDATE, S.START_TIME AS STARTTIME";
		sql += " FROM MOVIE M";
		sql += " INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID";
		sql += " WHERE M.TITLE LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setPrice(rs.getInt("PRICE"));
				movie.setAge(rs.getInt("AGE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				movie.setPlaceId(rs.getInt("PLACEID"));
				movie.setTheaterId(rs.getInt("THEATERID"));
				movie.setStartDate(rs.getString("STARTDATE"));
				movie.setEndDate(rs.getString("ENDDATE"));
				movie.setStartTime(rs.getString("STARTTIME"));
				movies.add(movie);
			}
			conn.close();
			return movies;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int insert(int movieId, int placeId, int theaterId, String startDate, String endDate, String startTime) {
		String sql = "INSERT INTO SCREEN SELECT SCREEN_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ? FROM DUAL";
		sql += " WHERE NOT EXISTS (SELECT 0 FROM SCREEN WHERE MOVIE_ID = ? AND PLACE_ID = ? AND THEATER_ID = ? AND START_DATE = ? AND END_DATE = ? AND START_TIME = ?)";
		conn = DBConnection.getConnection();

		int returnCnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieId);
			pstmt.setInt(2, placeId);
			pstmt.setInt(3, theaterId);
			pstmt.setString(4, startDate);
			pstmt.setString(5, endDate);
			pstmt.setString(6, startTime);
			pstmt.setInt(7, movieId);
			pstmt.setInt(8, placeId);
			pstmt.setInt(9, theaterId);
			pstmt.setString(10, startDate);
			pstmt.setString(11, endDate);
			pstmt.setString(12, startTime);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();

			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int update(int screenId, int movieId, int placeId, int theaterId, String startDate, String endDate, String startTime) {
		String sql = "UPDATE SCREEN SET MOVIE_ID = ?, PLACE_ID = ?, THEATER_ID = ?, START_DATE = ?, END_DATE = ?, START_TIME = ? WHERE  ID = ?";
		sql += " AND NOT EXISTS (SELECT 0 FROM SCREEN WHERE MOVIE_ID = ? AND PLACE_ID = ? AND THEATER_ID = ? AND START_DATE = ? AND END_DATE = ? AND START_TIME = ?)";
		conn = DBConnection.getConnection();

		int returnCnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieId);
			pstmt.setInt(2, placeId);
			pstmt.setInt(3, theaterId);
			pstmt.setString(4, startDate);
			pstmt.setString(5, endDate);
			pstmt.setString(6, startTime);
			pstmt.setInt(7, screenId);
			pstmt.setInt(8, movieId);
			pstmt.setInt(9, placeId);
			pstmt.setInt(10, theaterId);
			pstmt.setString(11, startDate);
			pstmt.setString(12, endDate);
			pstmt.setString(13, startTime);
			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int delete(int screenId) {
		String sql = "DELETE FROM SCREEN WHERE ID = ?";
		conn = DBConnection.getConnection();

		int returnCnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, screenId);
			returnCnt = pstmt.executeUpdate();
			
			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
	
}