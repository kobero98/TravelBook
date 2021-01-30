package main.java.travelbook.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class RootController {
	@FXML
	private BorderPane mainPane;

	public void setMainPane(BorderPane main) {
		this.mainPane=main;
		//then define the resize logic
		this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->{
			DoubleProperty fontSize = new SimpleDoubleProperty(mainPane.getHeight()*20/720); // font size in pt
			main.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
			mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight());
			
			
		});
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->
			mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth())); 
		this.mainPane.heightProperty().addListener((observable,oldValue,newValue)->{
			StackPane title=(StackPane)mainPane.getTop();
			title.setPrefHeight(mainPane.getHeight()*94/720);
	
		});
	}
}
