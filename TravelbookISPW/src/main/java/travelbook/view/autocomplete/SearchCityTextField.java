package main.java.travelbook.view.autocomplete;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.TextField;
import main.java.exception.DBException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.ControllerSearch;

import java.util.ArrayList;
import java.util.List;
public class SearchCityTextField extends AutocompleteTextField<String> {
	public SearchCityTextField() {
		super();
		setPos(Side.BOTTOM);
	}
	public SearchCityTextField(TextField text) {
		super(text);
		setPos(Side.BOTTOM);
	}
	@Override
	public List<String> getPredictions(String text){
		try {
			return new ControllerSearch().getCitiesPredictions(text);
		} catch (DBException e) {
			Platform.runLater(()->
			new TriggerAlert().triggerAlertCreate(e.getMessage(),"warn").showAndWait());
			return new ArrayList<>();
		}
		
	}
}
