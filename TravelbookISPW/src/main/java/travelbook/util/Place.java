package main.java.travelbook.util;


import java.util.List;

public interface Place {
	public List<Double> getCoordinates();
	public String getPlaceName();
	public String getType();
	public String getCountry();
	public String getCity();
	public String toString();
	public String getCategory();
	public String getIcon();
	public String getPostCode();
}
