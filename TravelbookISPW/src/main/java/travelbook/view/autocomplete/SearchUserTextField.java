package main.java.travelbook.view.autocomplete;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.control.TextField;
import main.java.exception.DBException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.ChatController;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.view.MenuBar;

public class SearchUserTextField extends AutocompleteTextField<UserBean>{

	public SearchUserTextField(TextField text) {
		super(text);
		setPos(Side.TOP);
	}
	@Override
	protected List<UserBean> getPredictions(String text) {
		try {
			return new ChatController().getUserPredictions(text,MenuBar.getInstance().getLoggedUser().getId());
		} catch (DBException e) {
			new TriggerAlert().triggerAlertCreate(e.getMessage(),"warn").showAndWait();
			return new ArrayList<>();
		}
	}
	
}