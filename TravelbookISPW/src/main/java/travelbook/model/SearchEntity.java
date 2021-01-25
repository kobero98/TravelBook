package main.java.travelbook.model;

public class SearchEntity implements Entity {

	private CityEntity city=null;
	private String type=null;
	private Integer minDay=null;
	private Integer maxDay=null;
	private Integer minCost=null;
	private Integer maxCost=null;
	public Integer getMaxCost() {
		return maxCost;
	}
	public void setMaxCost(Integer maxCost) {
		this.maxCost = maxCost;
	}
	public Integer getMinCost() {
		return minCost;
	}
	public void setMinCost(Integer minCost) {
		this.minCost = minCost;
	}
	public Integer getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(Integer maxDay) {
		this.maxDay = maxDay;
	}
	public Integer getMinDay() {
		return minDay;
	}
	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CityEntity getCity() {
		return city;
	}
	public void setCity(CityEntity city) {
		this.city = city;
	}
	
	
	
}
