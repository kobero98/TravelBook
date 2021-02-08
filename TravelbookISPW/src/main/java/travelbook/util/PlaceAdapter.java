package main.java.travelbook.util;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PlaceAdapter implements Place{

	private String pred;
	private String type;
	private String country;
	private String city="unkown";
	private double lat;
	private double lon;
	private String postCode;
	private String category;
	private String icon;
	@Override
	public String getIcon() {
		return icon;
	}
	public PlaceAdapter(JSONObject place) {
		this.converti(place);
	}
	private void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String getCategory() {
		return category;
	}
	private void setCategory(String category) {
		this.category = category;
	}
	private void setPostCode(String code) {
		this.postCode=code;
	}
	@Override
	public String getPostCode() {
		return this.postCode;
	}
	private void setPlaceName(String place) {
		this.pred=place;
	}
	private void setPlaceType(String type) {
		this.type=type;
	}
	private void setCountry(String country) {
		this.country=country;
	}
	private void setCity(String city) {
		this.city=city;
	}
	private void setCoordinates(double lat,double lon) {
		this.lat=lat;
		this.lon=lon;
	}
	@Override
	public List<Double> getCoordinates() {
		List<Double> array=new ArrayList<>();
		array.add(this.lat);
		array.add(this.lon);
		return array;
	}
	@Override
	public String getPlaceName() {
		return this.pred;
	}
	@Override
	public String getType() {
		return this.type;
	}
	@Override
	public String getCountry() {
		return this.country;
	}
	@Override
	public String getCity() {
		return this.city;
	}
	@Override
	public String toString() {
		return this.pred;
	}
	private void converti(JSONObject place) {
        	this.setPlaceName(place.get("place_name").toString());
        	JSONArray types=(JSONArray)place.get("place_type");
        	String tipo=(String)types.get(0);
        	this.setPlaceType(tipo);
        	JSONArray coordinates=(JSONArray)place.get("center");
        	//mapbox return a long lat array but i need lat long (see map)
        	this.setCoordinates((double)coordinates.get(1), (double)coordinates.get(0));
        	JSONArray context=(JSONArray)place.get("context");
        	analyzeContext(context,tipo);
        	if(tipo.compareTo("poi")==0) {
        		
        		JSONObject categoria=(JSONObject)place.get("properties");
        		if(categoria.get("maki")!=null) {
        		this.setIcon(categoria.get("maki").toString());
        		}
        		if(categoria.get("category")!=null)
        			this.setCategory(categoria.get("category").toString());
        	}
        	
        }
	private void analyzeContext(JSONArray context,String tipo) {
		if(context!=null) {
        	for(int j=0;j<context.size();j++) {
        		JSONObject first=(JSONObject)context.get(j);
        		String id=first.get("id").toString();
        		if(tipo.compareTo("poi")==0 && id.startsWith("postcode")) {
        			
        				this.setPostCode(first.get("text").toString());
        				
        			
        			
        		}
        		if(id.startsWith("region")) {
        			this.setCity(first.get("text").toString());
        		}
        		else if(id.startsWith("country")) {
        			this.setCountry(first.get("text").toString());
        		}
        	}
		}
	}
}
