package main.java.travelbook.model.bean;

import java.util.List;

public class SearchTrip {

	private List<String> type=null;
	private String city=null;
	private Integer costoMin=null;
	private Integer costoMax=null;
	private Integer durationMin=null;
	private Integer durationMax=null;
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getDurationMin() {
		return durationMin;
	}
	public void setDurationMin(Integer durationMin) {
		this.durationMin = durationMin;
	}
	public Integer getDurationMax() {
		return durationMax;
	}
	public void setDurationMax(Integer durationMax) {
		this.durationMax = durationMax;
	}
	public Integer getCostoMax() {
		return costoMax;
	}
	public void setCostoMax(Integer costoMax) {
		this.costoMax = costoMax;
	}
	public Integer getCostoMin() {
		return costoMin;
	}
	public void setCostoMin(Integer costoMin) {
		this.costoMin = costoMin;
	}

	
}
