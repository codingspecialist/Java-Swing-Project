package models;

public class Sales {

	private String title;
	private int price;
	private String name;
	private int movieId;
	private int placeId;
	private int reserveId;

	public Sales() {}

	public Sales(String title, int price, String name, int movieId, int placeId, int reserveId) {
		this.title = title;
		this.price = price;
		this.name = name;
		this.movieId = movieId;
		this.placeId = placeId;
		this.reserveId = reserveId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getReserveId() {
		return reserveId;
	}

	public void setReserveId(int reserveId) {
		this.reserveId = reserveId;
	}

}