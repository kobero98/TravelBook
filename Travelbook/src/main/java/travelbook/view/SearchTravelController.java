package main.java.travelbook.view;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import main.java.travelbook.view.ChatViewController.MyItem;
import main.java.travelbook.view.ChatViewController.contactCell;

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
	private ListView <MyTypes> type;
	@FXML
	private ListView <String> typeChoose;
	class MyTypes{
		private String tipo;
		private Color colore;
		public MyTypes(String type,Color C) {
			this.tipo=type;
			this.colore=C;
		}
		public void setType(String types)
		{
			this.tipo=types;
		}
		public void setColor(Color C)
		{
			this.colore=C;
		}
		public String getType()
		{
			return this.tipo;
		}
		public Color getColor(){
			return this.colore;
		}
	}
	class TipoListaGraphic extends ListCell<MyTypes>{
			@Override
	        public void updateItem(MyTypes item, boolean empty) {
	            super.updateItem(item, empty);
	            if (item != null) {
	            	HBox box= new HBox(2);
	            	Label label= new Label(item.getType());
	            	label.setStyle("-fx-font-size: 0.75em");
	            	label.setWrapText(true);
	            	label.setMaxWidth(type.getHeight()-1);
	            	Circle cerchio =new Circle(10,item.getColor());
	            	box.getChildren().addAll(cerchio,label);
	            	box.setMargin(cerchio, new Insets(2));
	            	box.setMargin(label, new Insets(2));
	            	box.setAlignment(Pos.CENTER_LEFT);
	            	setGraphic(box);
	            }
	        }
		}
	
	@FXML
	private void initialize() {
		ObservableList<MyTypes> information = FXCollections.observableArrayList(new MyTypes("Romantic Trip",Color.RED),new MyTypes("Family Holiday",Color.GREEN),
				new MyTypes("On The Road",Color.PURPLE),new MyTypes("Children Friendly",Color.BLUE),new MyTypes("Travel with Friend",Color.ORANGE),
				new MyTypes("Cultural Travel",Color.BLACK),new MyTypes("Relaxing Holiday",Color.YELLOW));
		this.type.setItems(information);

	    type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyTypes>() {
	          public void changed(ObservableValue<? extends MyTypes> observable,
	        		  MyTypes oldValue, MyTypes newValue) {
	            System.out.println("selection changed"); 
	            if(typeChoose.getItems().isEmpty())
	            	typeChoose.setVisible(true);
	            ObservableList<String> s=typeChoose.getItems();
	            s.add(type.getItems().get(type.getSelectionModel().getSelectedIndex()).getType());
	            typeChoose.setItems(s);
	          }
	        });
		this.type.setCellFactory(new Callback<ListView<MyTypes>,
				ListCell<MyTypes>>(){
			@Override
			public ListCell<MyTypes> call(ListView<MyTypes> list){
				return new TipoListaGraphic();
			}
		});
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
