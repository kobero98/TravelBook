package main.java.travelbook.util;

import main.java.travelbook.controller.PredictionController;
import java.util.List;
//import com.esri.arcgisruntime.tasks.geocode.SuggestResult;
public class PlacePrediction {
	//private AutocompletePrediction pred;
	private String pred;
	private String type;
	private String country;
	private String city;
	private double coordinates[];
	private double postCode;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	//private SuggestResult res;
	/*public PlacePrediction(AutocompletePrediction pred) {
		this.pred=pred;
	}*/
	public PlacePrediction() {
		
	}
	public void setPostCode(double code) {
		this.postCode=code;
	}
	public double getPostCode(double code) {
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
	public void setCoordinates(double coord[]) {
		this.coordinates=coord;
	}
	public double[] getCoordinates() {
		return this.coordinates;
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
	//Decomment this for ArcGIS
	/*public PlacePrediction(SuggestResult res) {
		this.res=res;
	}*/
	public String toString() {
		return this.pred;
	}
	//Use this for ArcGIS
	/* public String toString(){
	 * return this.res.getLabel();
	 * }
	 */
	public List<String> getCityAndCountry() {
		//Don't try to use it without an API KEY
		//If you have an API KEY so set it in the variable API_KEY of PredictionController.java file
		PredictionController controller=new PredictionController();
		List<String> cityAndCountry=null;
		//cityAndCountry=controller.getCityAndCountry(this.pred);
		//verifica se il dato ricevuto è corretto altrimenti risetta a null
		return cityAndCountry;
	}
}
