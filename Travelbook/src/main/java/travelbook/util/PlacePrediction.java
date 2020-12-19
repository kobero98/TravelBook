package main.java.travelbook.util;

import main.java.travelbook.controller.PredictionController;
import java.util.List;
//import com.esri.arcgisruntime.tasks.geocode.SuggestResult;
public class PlacePrediction {
	//private AutocompletePrediction pred;
	private String pred;
	//private SuggestResult res;
	/*public PlacePrediction(AutocompletePrediction pred) {
		this.pred=pred;
	}*/
	public PlacePrediction(String pred) {
		//Questo costruttore serve per passare da string ad altro
		PredictionController controller=new PredictionController();
		//now call controller method
		//this.pred=controller.getPlace(pred);
		
		this.pred=pred;
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
