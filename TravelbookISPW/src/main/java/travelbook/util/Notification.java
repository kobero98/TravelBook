package main.java.travelbook.util;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Notification {
	
	public Notification(AnchorPane mainAnchor,int height) {
		Circle dot = new Circle(6);
		dot.setFill(Color.DARKSALMON);
		mainAnchor.getChildren().add(dot);
		dot.setLayoutX(510);
		dot.setLayoutY(height);
		mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->
			dot.setLayoutY(mainAnchor.getHeight()*30/625));
		mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->
			dot.setLayoutX(mainAnchor.getWidth()*510/1280));
		
	}

}
