package main.java.travelbook.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class SearchTravelController {
	
	private BorderPane mainPane;
	
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
			soprasotto=1;
			double  move=this.AdvancedSearch.getHeight();
			double  y=this.Found.getLayoutY();
			this.Found.setLayoutY(y+move+30);
			double  y1=this.Found.getLayoutY();
			this.Found.relocate(this.Found.getLayoutX(), y1);
			/*double y1=this.LineaListView.getLayoutY();
			this.LineaListView.setLayoutY(y1+y+move+30);
			double y2=this.lista.getLayoutY();
			this.lista.setPrefHeight(lista.getHeight()-(y1+y+move+30));
			this.lista.setLayoutY(y2);*/
			System.out.print("misura: "+move+ " situato "+y+" ora sto a: "+y1);
			this.AdvancedSearch.setVisible(true);
		}
		else {
			soprasotto=0;
			
		}
	}
	
		
}
