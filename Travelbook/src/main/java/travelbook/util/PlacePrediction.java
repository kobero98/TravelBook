package main.java.travelbook.util;


import java.util.ArrayList;
import java.util.List;
//import com.esri.arcgisruntime.tasks.geocode.SuggestResult;
public class PlacePrediction {
	//private AutocompletePrediction pred;
	private String pred;
	private String type;
	private String country;
	private String city;
	private double lat;
	private double lon;
	private String postCode;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public PlacePrediction() {
		
	}
	public void setPostCode(String code) {
		this.postCode=code;
	}
	public String getPostCode() {
		return this.postCode;
	}
	public void setPlaceName(String place) {
		this.pred=place;
	}
	public void setPlaceType(String type) {
		this.type=type;
	}
	public void setCountry(String country) {
		this.country=country;
	}
	public void setCity(String city) {
		this.city=city;
	}
	public void setCoordinates(double lat,double lon) {
		this.lat=lat;
		this.lon=lon;
	}
	public List<Double> getCoordinates() {
		List<Double> array=new ArrayList<Double>();
		array.add(this.lat);
		array.add(this.lon);
		return array;
	}
	public String getPlaceName() {
		return this.pred;
	}
	public String getType() {
		return this.type;
	}
	public String getCountry() {
		return this.country;
	}
	public String getCity() {
		return this.city;
	}
	
	public String toString() {
		return this.pred;
	}
	
}
