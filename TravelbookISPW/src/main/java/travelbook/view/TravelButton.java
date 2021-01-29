package main.java.travelbook.view;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.Observable;
public class TravelButton implements Observer {
	private Pane pane;
	private StackPane stack;
	
	private Label title;
	private Label subtitle;
	private BorderPane mainPane;
	public Pane getPane() {
		return pane;
	}
	public StackPane getStack() {
		return stack;
	}
	public Label getTitle() {
		return title;
	}
	public void setTitle(Label title) {
		this.title = title;
	}
	public Label getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(Label subtitle) {
		this.subtitle = subtitle;
	}
	public TravelButton(double width, double height, Integer i, TravelBean travel) {
		// the real constructor take a TravelBean as third parameter
		travel.addObserver(this);
		stack=new StackPane();
		pane=new Pane();
		title=new Label(i.toString());
	    subtitle= new Label(i.toString());
		stack.setPrefWidth(width);
		stack.setPrefHeight(height);
		pane.setPrefWidth(width);
		pane.setPrefHeight(height*130/190);
		pane.setMaxHeight(height*130/190);
		pane.setMinHeight(height*130/190);
		stack.getChildren().addAll(pane,title,subtitle);
		StackPane.setAlignment(pane,Pos.TOP_CENTER);
		StackPane.setAlignment(title,Pos.CENTER);
		StackPane.setAlignment(subtitle,Pos.CENTER);
		StackPane.setMargin(title, new Insets(105,0,0,0));
		StackPane.setMargin(subtitle, new Insets(135,0,0,0));
		stack.prefWidthProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);
		stack.heightProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);
		stack.widthProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);	}
	private void resize() {
		double width=stack.getWidth();
		double height=stack.getHeight();
		pane.setPrefWidth(width);
		pane.setPrefHeight(height*130/190);
		pane.setMaxHeight(height*130/190);
		pane.setMinHeight(height*130/190);
	}
	@Override
	public void update(Observable o) {
		TravelBean myTravel;
		myTravel=(TravelBean) o;
		this.stack.setOnMouseClicked(e->{
			FXMLLoader loader=new FXMLLoader();
			URL url;
			try {
				url = new File("src/main/java/travelbook/view/ViewTravel.fxml").toURI().toURL();
				loader.setLocation(url);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				ViewTravelController controller;
				AnchorPane internalPane;
				internalPane=(AnchorPane)loader.load();
				mainPane.setCenter(internalPane);
				controller=loader.getController();
				MenuBar.getInstance().setIdTravel(myTravel.getId());
				controller.setMainPane(mainPane, 1);
				
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		});
		//Carica la foto nel pane
		BackgroundImage bgPhoto = new BackgroundImage(myTravel.getPathImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
		Background newBg = new Background(bgPhoto);
		this.pane.setBackground(newBg);
	}
	@Override
	public void update(Observable o,Object arg) {
		this.update(o);
	}
	
}
