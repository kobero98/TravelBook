package main.java.travelbook.view;
import main.java.travelbook.controller.PredictionController;
import main.java.travelbook.util.PlacePrediction;
import java.util.List;
public class SearchPlaceTextField extends AutocompleteTextField<PlacePrediction> {
	
	public SearchPlaceTextField() {
		//Call the constructor of autocomplete.
		super();
		//At least 3 character are needed... 
		//Per evitare troppe richieste a mapbox.
		super.setCharacterLowerBound(3);
	}
	@Override
	protected List<PlacePrediction> getPredictions(String text){
		PredictionController predict=new PredictionController();
		return predict.getPredictions(text);
	}
}
