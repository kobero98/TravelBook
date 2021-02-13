package main.java.exception;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TriggerAlert {
	private static final String ALERTCSS="src/main/java/travelbook/css/alert.css";
	private static final String PROJECTCSS="src/main/java/travelbook/css/project.css";
	private static final String WARN_IMG = "src/main/resources/AddViewImages/warning.png";
	private static final String ERR_IMG= "src/main/resources/AddViewImages/error.png";
	private static final String HELP_IMG="src/main/resources/AddViewImages/help.png";
	
	public Alert triggerAlertCreate(String s, String type) {
		Alert alert=null;
		URL url = null;
		try {		
			switch(type) {
			case "help":
				alert =  new Alert(AlertType.CONFIRMATION);
				url = new File(HELP_IMG).toURI().toURL();
				break;
			case "warn":
				alert = new Alert(AlertType.WARNING);
				url = new File(WARN_IMG).toURI().toURL();
				break;
			default:
				alert = new Alert(AlertType.ERROR);
				url = new File(ERR_IMG).toURI().toURL();
			}
		
			
			Image image = new Image(url.toString());
		 	ImageView imageView = new ImageView(image);
		 	alert.setGraphic(imageView);
		 	url = new File(ALERTCSS).toURI().toURL();
		 	alert.getDialogPane().getStylesheets().add(url.toString());
		 	url = new File(PROJECTCSS).toURI().toURL();
		 	alert.getDialogPane().getStylesheets().add(url.toString());
		 	
   		 
   			} catch (MalformedURLException e) {
	   			alert = new Alert(AlertType.NONE);
   			}
		alert.setTitle("Connection lost");
		alert.setHeaderText("Something went wrong, try again");
		alert.setContentText(s);
		return alert;	
	}
}
