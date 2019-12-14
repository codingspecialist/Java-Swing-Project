package models;

public class Reserves {
	private int id;
	private String userId;
	private int movieId;
	private int placeId;
	private int theaterId;
	private String reserveDate;
	private String reserveTime;
	private int reserveCnt;
	private String seat;
	private int price;
	private int discountId;
	private String cardNo;
	private String insDt;
	private String movieTitle;
	private String placeName;
	private String theaterName;
	private String discountName;
	private String delFg;
	private String delDt;
	
	public Reserves() {}

	public Reserves(int id, String userId, int movieId, int placeId, int theaterId, String reserveDate,
			String reserveTime, int reserveCnt, String seat, int price, int discountId, String cardNo, String insDt,
			String movieTitle, String placeName, String theaterName, String discountName, String delFg, String delDt) {
		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
		this.placeId = placeId;
		this.theaterId = theaterId;
		this.reserveDate = reserveDate;
		this.reserveTime = reserveTime;
		this.reserveCnt = reserveCnt;
		this.seat = seat;
		this.price = price;
		this.discountId = discountId;
		this.cardNo = cardNo;
		this.insDt = insDt;
		this.movieTitle = movieTitle;
		this.placeName = placeName;
		this.theaterName = theaterName;
		this.discountName = discountName;
		this.delFg = delFg;
		this.delDt = delDt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}

	public String getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}

	public int getReserveCnt() {
		return reserveCnt;
	}

	public void setReserveCnt(int reserveCnt) {
		this.reserveCnt = reserveCnt;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getInsDt() {
		return insDt;
	}

	public void setInsDt(String insDt) {
		this.insDt = insDt;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getTheaterName() {
		return theaterName;
	}

	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getDelFg() {
		return delFg;
	}

	public void setDelFg(String delFg) {
		this.delFg = delFg;
	}

	public String getDelDt() {
		return delDt;
	}

	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}
	
}