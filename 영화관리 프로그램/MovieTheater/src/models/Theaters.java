package models;

public class Theaters {
	private int id;
	private String name;
	private int placeId;
	private String placeName;
	private int seatId;
	private String seatType;
	
	public Theaters() {}

	public Theaters(int id, String name, int placeId, String placeName, int seatId, String seatType) {
		this.id = id;
		this.name = name;
		this.placeId = placeId;
		this.placeName = placeName;
		this.seatId = seatId;
		this.seatType = seatType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	
}
