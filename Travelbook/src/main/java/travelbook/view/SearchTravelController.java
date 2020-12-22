package main.java.travelbook.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

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
	
	public void setMainPane(BorderPane main)
	{
		this.mainPane=main;
		
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
