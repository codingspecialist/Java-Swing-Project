package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Reserves;

public class ReserveDao {
	private ReserveDao() {
	}

	private static ReserveDao instance = new ReserveDao();

	public static ReserveDao getInstance() {
		return instance;
	}

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private String sql;

	public Vector<Reserves> selectMovie(String keyword) {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " WHERE M.TITLE LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Reserves> selectMovieAll() {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " ORDER BY M.TITLE";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Reserves> selectPlace(String keyword) {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " WHERE P.NAME LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Reserves> selectPlaceAll() {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " ORDER BY P.NAME";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Reserves> selectId(String keyword) {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " WHERE U.USER_ID LIKE '%" + keyword + "%'";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Reserves selectOne(int id) {
		Reserves reserve = new Reserves();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.ID 지점ID, T.ID 상영관ID, D.ID 할인ID , M.ID 영화ID,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " WHERE R.ID = ?";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieId(rs.getInt("영화ID"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceId(rs.getInt("지점ID"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterId(rs.getInt("상영관ID"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setSeat(rs.getString("좌석"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDiscountName(rs.getString("할인구분"));
				reserve.setCardNo(rs.getString("카드번호"));
				reserve.setInsDt(rs.getString("예약일"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserve.setDelDt(rs.getString("취소일시"));
			}

			conn.close();
			return reserve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Reserves> selectIdAll() {
		Vector<Reserves> reserves = new Vector<>();
		sql = "SELECT R.ID 아이디, R.USER_ID 사용자아이디, M.TITLE 영화명,";
		sql += " P.ID 지점ID, T.ID 상영관ID, D.ID 할인ID , M.ID 영화ID,";
		sql += " P.NAME 지점명, T.NAME 상영관명, R.RESERVE_DATE 예매날짜, R.RESERVE_TIME 예매시간, R.RESERVE_CNT 예매인원,";
		sql += " R.SEAT 좌석, R.PRICE 결제금액, D.NAME 할인구분, R.CARD_NO 카드번호, R.INS_DT 예약일, R.DELETE_FG 취소여부, R.DEL_DT 취소일시";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID";
		sql += " INNER JOIN USERS U ON R.USER_ID = U.USER_ID";
		sql += " INNER JOIN PLACE P ON R.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON R.THEATER_ID = T.ID";
		sql += " INNER JOIN DISCOUNT D ON R.DISCOUNT_ID = D.ID";
		sql += " ORDER BY U.USER_ID";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reserves reserve = new Reserves();
				reserve.setId(rs.getInt("아이디"));
				reserve.setUserId(rs.getString("사용자아이디"));
				reserve.setMovieTitle(rs.getString("영화명"));
				reserve.setPlaceName(rs.getString("지점명"));
				reserve.setTheaterName(rs.getString("상영관명"));
				reserve.setReserveTime(rs.getString("예매시간"));
				reserve.setReserveDate(rs.getString("예매날짜"));
				reserve.setReserveCnt(rs.getInt("예매인원"));
				reserve.setPrice(rs.getInt("결제금액"));
				reserve.setDelFg(rs.getString("취소여부"));
				reserves.add(reserve);
			}

			conn.close();
			return reserves;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Reserves selectedSeats(int movieId, int placeId, int theaterId, String reserveDate, String reserveTime) {
		sql = "SELECT LISTAGG(SEAT, ',') WITHIN GROUP (ORDER BY SEAT) AS SEAT FROM (";
		sql += " SELECT R.SEAT FROM MOVIE M";
		sql += " INNER JOIN SCREEN SC ON M.ID = SC.MOVIE_ID";
		sql += " INNER JOIN PLACE P ON SC.PLACE_ID = P.ID";
		sql += " INNER JOIN THEATER T ON SC.THEATER_ID = T.ID";
		sql += " INNER JOIN SEAT SE ON T.SEAT_ID = SE.ID";
		sql += " INNER JOIN RESERVE R ON M.ID = R.MOVIE_ID AND SC.PLACE_ID = R.PLACE_ID AND SC.THEATER_ID = R.THEATER_ID";
		sql += " WHERE R.MOVIE_ID = ? AND R.PLACE_ID = ? AND R.THEATER_ID = ?";
		sql += " AND R.RESERVE_TIME = ? AND R.RESERVE_DATE = ?)";

		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieId);
			pstmt.setInt(2, placeId);
			pstmt.setInt(3, theaterId);
			pstmt.setString(4, reserveTime);
			pstmt.setString(5, reserveDate);
			rs = pstmt.executeQuery();

			Reserves reserve = new Reserves();
			if (rs.next()) {
				reserve.setSeat(rs.getString("SEAT"));

				conn.close();
				return reserve;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Reserves selectRecent(String userId) {
		sql = "SELECT * FROM";
		sql += " (SELECT M.TITLE, R.RESERVE_DATE, R.RESERVE_CNT, R.SEAT, R.PRICE, TO_CHAR(R.INS_DT, 'YYYY-MM-DD HH24:MI') AS INS_DT";
		sql += " FROM RESERVE R INNER JOIN MOVIE M ON R.MOVIE_ID = M.ID";
		sql += " WHERE R.USER_ID = ?";
		sql += " ORDER BY INS_DT DESC)";
		sql += " WHERE ROWNUM = 1";

		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			Reserves reserve = new Reserves();
			if (rs.next()) {
				reserve.setMovieTitle(rs.getString("TITLE"));
				reserve.setReserveDate(rs.getString("RESERVE_DATE"));
				reserve.setReserveCnt(rs.getInt("RESERVE_CNT"));
				reserve.setSeat(rs.getString("SEAT"));
				reserve.setPrice(rs.getInt("PRICE"));
				reserve.setInsDt(rs.getString("INS_DT"));

				conn.close();
				return reserve;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int insert(String userId, int movieId, int placeId, int theaterId, String reserveDate, String reserveTime,
			int reserveCnt, String seat, int price, int discountId, String cardNo) {
		sql = "INSERT INTO RESERVE(ID, USER_ID, MOVIE_ID, PLACE_ID, THEATER_ID, RESERVE_DATE, RESERVE_TIME, RESERVE_CNT, SEAT, PRICE, DISCOUNT_ID, CARD_NO, INS_DT, DELETE_FG)";
		sql += " VALUES(RESERVE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, 'N')";

		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, movieId);
			pstmt.setInt(3, placeId);
			pstmt.setInt(4, theaterId);
			pstmt.setString(5, reserveDate);
			pstmt.setString(6, reserveTime);
			pstmt.setInt(7, reserveCnt);
			pstmt.setString(8, seat);
			pstmt.setInt(9, price);
			pstmt.setInt(10, discountId);
			pstmt.setString(11, cardNo);
			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int update(String userId, int movieId, int placeId, int theaterId, String reserveDate, String reserveTime,
			int reserveCnt, String seat, int price, int discountId, String cardNo, int id) {
		sql = "UPDATE RESERVE SET USER_ID = ?, MOVIE_ID = ?, PLACE_ID = ?, THEATER_ID = ?, RESERVE_DATE = ?, ";
		sql += "RESERVE_TIME = ?, RESERVE_CNT = ?, SEAT = ?, PRICE = ?, DISCOUNT_ID = ?, CARD_NO = ? ";
		sql += "WHERE ID = ? AND NOT EXISTS (SELECT 0 FROM RESERVE WHERE USER_ID = ? AND MOVIE_ID = ? AND PLACE_ID = ? AND THEATER_ID = ? AND RESERVE_DATE = ? AND ";
		sql += "RESERVE_TIME = ? AND RESERVE_CNT = ? AND SEAT = ? AND PRICE = ? AND DISCOUNT_ID = ? AND CARD_NO = ?)";

		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, movieId);
			pstmt.setInt(3, placeId);
			pstmt.setInt(4, theaterId);
			pstmt.setString(5, reserveDate);
			pstmt.setString(6, reserveTime);
			pstmt.setInt(7, reserveCnt);
			pstmt.setString(8, seat);
			pstmt.setInt(9, price);
			pstmt.setInt(10, discountId);
			pstmt.setString(11, cardNo);
			pstmt.setInt(12, id);
			pstmt.setString(13, userId);
			pstmt.setInt(14, movieId);
			pstmt.setInt(15, placeId);
			pstmt.setInt(16, theaterId);
			pstmt.setString(17, reserveDate);
			pstmt.setString(18, reserveTime);
			pstmt.setInt(19, reserveCnt);
			pstmt.setString(20, seat);
			pstmt.setInt(21, price);
			pstmt.setInt(22, discountId);
			pstmt.setString(23, cardNo);

			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int delete(int id) {
		String sql = "UPDATE RESERVE SET DELETE_FG = 'Y', DEL_DT = SYSDATE WHERE ID = ?";
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