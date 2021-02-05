package main.java.travelbook.util;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Notification {
	
	Circle dot;
	AnchorPane mainAnchor;
	public Notification(AnchorPane mainAnchor,int height) {
		this.dot = new Circle(6);
		this.mainAnchor=mainAnchor;
		dot.setFill(Color.DARKSALMON);
		mainAnchor.getChildren().add(dot);
		dot.setLayoutX(510);
		dot.setLayoutY(height);
		mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->
			dot.setLayoutY(mainAnchor.getHeight()*30/625));
		mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->
			dot.setLayoutX(mainAnchor.getWidth()*510/1280));
		
	}
	public void remove() {
		mainAnchor.getChildren().remove(dot);
	}

}
