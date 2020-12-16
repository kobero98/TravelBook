package travelbook.util;

//import com.esri.arcgisruntime.tasks.geocode.SuggestResult;
public class PlacePrediction {
	//private AutocompletePrediction pred;
	private String pred;
	//private SuggestResult res;
	/*public PlacePrediction(AutocompletePrediction pred) {
		this.pred=pred;
	}*/
	public PlacePrediction(String pred) {
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
}
