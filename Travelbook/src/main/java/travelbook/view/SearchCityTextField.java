package main.java.travelbook.view;
import javafx.scene.control.TextField;
import main.java.travelbook.controller.SearchTravel;
import java.util.List;
public class SearchCityTextField extends AutocompleteTextField<String> {
	public SearchCityTextField() {
		super();
	}
	public SearchCityTextField(TextField text) {
		super(text);
	}
	@Override
	public List<String> getPredictions(String text){
		List<String> results=SearchTravel.getIstance().getCitiesPredictions(text);
		return results;
	}
}
