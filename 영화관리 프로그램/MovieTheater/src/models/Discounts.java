package models;

public class Discounts {

	private int id;
	private String name;
	private int val;
	private String unit;
	
	public Discounts() {}
	
	public Discounts(int id, String name, int val, String unit) {
		this.id = id;
		this.name = name;
		this.val = val;
		this.unit = unit;
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

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
