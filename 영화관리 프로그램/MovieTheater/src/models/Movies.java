package models;

public class Movies {
	private int id;
	private String title;
	private int placeId;
	private int theaterId;
	private int price;
	private int age;
	private int runningTime;
	private String startDate;
	private String endDate;
	private String startTime;
	private String placeName;
	private String theaterName;
	private String seat;
	private int screenId;
	
	public Movies() {}

	public Movies(int id, String title, int placeId, int theaterId, int price, int age, int runningTime,
			String startDate, String endDate, String startTime, String placeName, String theaterName, String seat,
			int screenId) {
		this.id = id;
		this.title = title;
		this.placeId = placeId;
		this.theaterId = theaterId;
		this.price = price;
		this.age = age;
		this.runningTime = runningTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.placeName = placeName;
		this.theaterName = theaterName;
		this.seat = seat;
		this.screenId = screenId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

}
