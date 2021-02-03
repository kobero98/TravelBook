package main.java.travelbook.view;
import javafx.geometry.Side;
import javafx.scene.control.TextField;
import main.java.travelbook.controller.ControllerSearch;

import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import exception.TriggerAlert;
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
			return ControllerSearch.getInstance().getCitiesPredictions(text);
		} catch (DBException e) {
			new TriggerAlert().triggerAlertCreate(e.getMessage(),"warn").showAndWait();
			return new ArrayList<>();
		}
		
	}
}
