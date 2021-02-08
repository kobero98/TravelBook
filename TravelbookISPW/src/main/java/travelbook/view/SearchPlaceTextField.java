package main.java.travelbook.view;
import main.java.travelbook.controller.PredictionController;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.travelbook.util.Place;
import main.java.travelbook.util.PlaceAdapter;
import java.util.ArrayList;
import org.json.simple.JSONObject;

import exception.MapboxException;

import java.util.List;
public class SearchPlaceTextField extends AutocompleteTextField<Place> {
	
	public SearchPlaceTextField() {
		this(new TextField());
		setPos(Side.BOTTOM);
	}
	public SearchPlaceTextField(TextField textField) {
		super(textField);
		//At least 3 character are needed... 
		//Per evitare troppe richieste a mapbox.
		super.setCharacterLowerBound(3);
		setPos(Side.BOTTOM);
	}
	@Override
	protected List<Place> getPredictions(String text){
		PredictionController predict=new PredictionController();
		List<Place> places=new ArrayList<>();
		try{
			List<JSONObject> results=predict.getPredictions(text);
			
			for(JSONObject obj: results) {
				places.add(new PlaceAdapter(obj));
			}
			return places;
		}catch(MapboxException e){
			Platform.runLater(()->{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setHeaderText("Map service error");
				alert.setContentText(e.getMessage());
				alert.getDialogPane().getStylesheets().add("main/java/travelbook/css/alert.css");
				alert.getDialogPane().getStylesheets().add("main/java/travelbook/css/project.css");
				Image image = new Image("main/resources/AddViewImages/error.png");
				ImageView imageView = new ImageView(image);
				alert.setGraphic(imageView);
				alert.showAndWait();
			});
		}
		return places;
	}
	
}
