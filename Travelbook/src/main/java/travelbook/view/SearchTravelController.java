package main.java.travelbook.view;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SearchTravelController {
	
	private BorderPane mainPane;
	@FXML
	private AnchorPane sfondo;
	@FXML
	private Button TurnBack;
	@FXML
	private TextField minCost;
	@FXML
	private TextField maxCost;
	@FXML
	private RadioButton budjet1;
	@FXML 
	private RadioButton budjet2;
	@FXML
	private RadioButton budjet3;
	@FXML
	private RadioButton Budjet4;
	@FXML
	private Label Found;
	@FXML
	private Line LineaListView;
	@FXML
	private ListView lista;
	@FXML
	private Button Advanced;
	@FXML
	private Pane AdvancedSearch;
	
	@FXML
	private ListView <HBox> type;
	@FXML
	private ListView <String> typeChoose;
	
	@FXML
	private void initialize() {
			
		/*	new String("Romantic Trip"),
		     new String("Family Holliday"),
		     new String("On The Road"),
		     new String("Children Friendly"),
		     new String("Travel with Friend"),
		     new String("Cultural Travel"),
		     new String("Relaxing Holliday")
		     */
		
		type.getItems().addAll(
		     new HBox(2),
		     new HBox(2),
		     new HBox(2),
		     new HBox(2),
		     new HBox(2),
		     new HBox(2),
		     new HBox(2));
		type.getItems().get(0).getChildren().addAll(new Circle(5, Color.RED) ,new Label("Romantic Trip"));
		type.getItems().get(1).getChildren().addAll(new Circle(5, Color.GREEN) ,new Label("Family Holliday"));
		type.getItems().get(2).getChildren().addAll(new Circle(5, Color.PURPLE) ,new Label("On The Road"));
		type.getItems().get(3).getChildren().addAll(new Circle(5, Color.BLUE) ,new Label("Children Friendly"));
		type.getItems().get(4).getChildren().addAll(new Circle(5, Color.ORANGE) ,new Label("Travel with Friend"));
		type.getItems().get(5).getChildren().addAll(new Circle(5, Color.BLACK) ,new Label("Cultural Travel"));
		type.getItems().get(6).getChildren().addAll(new Circle(5, Color.YELLOW) ,new Label("Relaxing Holliday"));
		for(int i=0;i<6;i++) type.getItems().get(i).setAlignment(Pos.CENTER);
	}
	public void setMainPane(BorderPane main)
	{
		this.mainPane=main;
		//then define the resize logic

		this.sfondo.heightProperty().addListener((observable,oldValue,newValue)->{
			System.out.println("Altezza Sfondo: " + this.sfondo.getHeight());
			AdvancedSearch.setPrefHeight(sfondo.getPrefHeight()*200/625);
			AdvancedSearch.setLayoutY(sfondo.getPrefHeight()*133/625);
			
			TurnBack.setPrefHeight(sfondo.getHeight()*30/625);
			TurnBack.setLayoutY(sfondo.getHeight()*50/625);
			
			lista.setPrefHeight(sfondo.getHeight()*437/625);
			lista.setLayoutY(sfondo.getHeight()*133/625);
			
			Advanced.setPrefHeight(sfondo.getHeight()*15/625);
			Advanced.setLayoutY(sfondo.getHeight()*113/625);
		
		});
		sfondo.setPrefHeight(this.mainPane.getHeight()*625/720);
		sfondo.setPrefWidth(this.mainPane.getWidth());
	}
	
	private int soprasotto=0;
	@FXML
	private void showSearchAdvanced() {
		if(this.soprasotto==0) {
			this.soprasotto=1;
			double move=this.AdvancedSearch.getHeight();
			this.Found.setLayoutY(this.Found.getLayoutY()+move);
			this.LineaListView.setLayoutY(this.LineaListView.getLayoutY()+move);
			this.lista.setPrefHeight(this.lista.getHeight()-move);
			this.lista.setLayoutY(this.lista.getLayoutY()+move);
			this.AdvancedSearch.setVisible(true);
		
		}
		else {
			this.soprasotto=0;
			double move=this.AdvancedSearch.getHeight();
			this.Found.setLayoutY(this.Found.getLayoutY()-move);
			this.LineaListView.setLayoutY(this.LineaListView.getLayoutY()-move);
			this.lista.setPrefHeight(this.lista.getPrefHeight()+move);
			this.lista.setLayoutY(this.lista.getLayoutY()-move);
			this.AdvancedSearch.setVisible(false);
		}
	}
	@FXML
	public void moveToExplore()throws IOException {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MenuBar.class.getResource("ExplorePage.fxml"));
		AnchorPane internalPane=(AnchorPane)loader.load();
		this.mainPane.setCenter(internalPane);
		ExploreViewController controller=loader.getController();
		controller.setMainPane(this.mainPane);
	} 
}
