package main.java.travelbook.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	private RadioButton budjet4;
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
	private Label scritta1;
	@FXML
	private Label scritta2;
	@FXML
	private Label scritta3;
	@FXML
	private Label scritta4;
	@FXML
	private Label scritta5;
	@FXML
	private Label scritta6;
	@FXML
	private ScrollPane Consigliati;
	@FXML
	private Pane ricerca;
	@FXML
	private ListView <MyTypes> type;
	@FXML
	private VBox tipiSelezionati; 
	
	
	private List <MyTypes> typeChoose=null;
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
		this.type.setCellFactory(new Callback<ListView<MyTypes>,
				ListCell<MyTypes>>(){
			@Override
			public ListCell<MyTypes> call(ListView<MyTypes> list){
				return new TipoListaGraphic();
			}
		});
		type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyTypes>() {
	          public void changed(ObservableValue<? extends MyTypes> observable,
	        		MyTypes oldValue, MyTypes newValue) {
	        	MyTypes addType=type.getItems().get(type.getSelectionModel().getSelectedIndex());
	        	if(typeChoose==null) {
	        		typeChoose= new ArrayList<>();
	        		typeChoose.add(addType);
	        	}
	        	else {
	        			typeChoose.add(addType);
	        	}
	        		HBox box= new HBox(2);
	            	Label label= new Label(addType.getType());
	            	label.setTextFill(Color.WHITE);
	            	label.setStyle("-fx-font-size: 0.75em");
	            	label.setWrapText(true);
	                Button delet =new Button();
	                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
	                    public void handle(ActionEvent e) 
	                    { 
	                    	Button b= (Button) e.getSource();
	                    	HBox box=(HBox) b.getParent();
	                    	
	                    	Label l =(Label) box.getChildren().get(1);
	                        MyTypes i=new MyTypes(l.getText(),(Color) box.getBackground().getFills().get(0).getFill());
	                        for(int j=0;j<typeChoose.size();j++)
	                        	if(typeChoose.get(j).getType().equals(i.getType())) typeChoose.remove(j);	
	                        tipiSelezionati.getChildren().remove(box);
	    	            	tipiSelezionati.setPrefHeight(typeChoose.size()*45);
	    	            	if(typeChoose.size()==0) {
	    		        		tipiSelezionati.getParent().getParent().getParent().setVisible(false);

	    	            	}
	                    } 
	                }; 
	          
	                // when button is pressed 
	                delet.setOnAction(event); 
	                delet.setStyle("-fx-shape:\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\" ");
	            	delet.setPrefSize(13, 13);
	                box.getChildren().addAll(delet,label);
	            	box.setMargin(delet, new Insets(2));
	            	box.setMargin(label, new Insets(2));
	            	box.setAlignment(Pos.CENTER_LEFT);
	            	box.setBackground(new Background(new BackgroundFill(addType.getColor(),new CornerRadii(4),null)));
	            	tipiSelezionati.getChildren().add(box);
	            	tipiSelezionati.setPrefHeight(typeChoose.size()*45);
	        		tipiSelezionati.getParent().getParent().getParent().setVisible(true);
	          }
	          
	          });
		


	}
	private EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
		      TextField s=(TextField) e.getSource();
		       try{
		    	   Double.parseDouble((s.getText()));
		       }catch(NumberFormatException ex)
		       {
		    	   s.clear();
		       }
		   } 
		};   
	public void setMainPane(BorderPane main)
	{
		this.mainPane=main;
		
		ToggleGroup group = new ToggleGroup();
	    budjet1.setToggleGroup(group);
	    budjet2.setToggleGroup(group);
	    budjet3.setToggleGroup(group);
	    budjet4.setToggleGroup(group);
	    this.minCost.setOnMouseClicked(eventHandler);
	    this.maxCost.setOnMouseClicked(eventHandler);
	    this.minCost.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	 
	                minCost.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	    
	    this.maxCost.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	                maxCost.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	    //then define the resize logic
		this.sfondo.heightProperty().addListener((observable,oldValue,newValue)->{
			
			
			scritta1.setLayoutY(sfondo.getPrefHeight()*109/625);
			scritta2.setLayoutY(sfondo.getPrefHeight()*120/625);
			if(soprasotto==0) {
				Found.setLayoutY(sfondo.getPrefHeight()*134/625);
				lista.setLayoutY(sfondo.getPrefHeight()*182/625);
				lista.setPrefHeight(sfondo.getPrefHeight()*434/625);
			}
			else {
				Found.setLayoutY(sfondo.getPrefHeight()*(134+this.AdvancedSearch.getHeight())/625);
				lista.setLayoutY(sfondo.getPrefHeight()*(182+this.AdvancedSearch.getHeight())/625);
				lista.setPrefHeight(sfondo.getPrefHeight()*(434-this.AdvancedSearch.getHeight())/625);
			}
			ricerca.setPrefHeight(sfondo.getPrefHeight()*45/625);
			ricerca.setLayoutY(sfondo.getPrefHeight()*50/625);
			
			Consigliati.setPrefHeight(sfondo.getPrefHeight()*465/625);
			Consigliati.setLayoutY(sfondo.getPrefHeight()*160/625);
			
			AdvancedSearch.setPrefHeight(sfondo.getPrefHeight()*200/625);
			AdvancedSearch.setLayoutY(sfondo.getPrefHeight()*133/625);
			
			scritta3.setLayoutY(sfondo.getPrefHeight()*(7+AdvancedSearch.getLayoutY())/625);
			
			budjet1.setLayoutY(sfondo.getPrefHeight()*(AdvancedSearch.getLayoutY()+55)/625);
			budjet2.setLayoutY(sfondo.getPrefHeight()*(87+AdvancedSearch.getLayoutY())/625);
			budjet3.setLayoutY(sfondo.getPrefHeight()*(119+AdvancedSearch.getLayoutY())/625);
			budjet4.setLayoutY(sfondo.getPrefHeight()*(151+AdvancedSearch.getLayoutY())/625);
			
			type.setPrefHeight(sfondo.getPrefHeight()*137/625);
			type.setLayoutY(sfondo.getPrefHeight()*54/625);
			
			TurnBack.setPrefHeight(sfondo.getPrefHeight()*30/625);
			TurnBack.setLayoutY(sfondo.getPrefHeight()*50/625);
			System.out.println(lista.getLayoutY()+" "+lista.getPrefHeight());
			
		
			
			
			Advanced.setPrefHeight(sfondo.getPrefHeight()*13/625);
			Advanced.setLayoutY(sfondo.getPrefHeight()*114/625);
		});
		this.sfondo.widthProperty().addListener((observable,oldValue,newValue)->{
			
			Found.setLayoutX(sfondo.getPrefWidth()*54/1280);
			scritta1.setLayoutX(sfondo.getPrefWidth()*48/1280);
			scritta2.setLayoutX(sfondo.getPrefWidth()*972/1280);
			
			AdvancedSearch.setPrefWidth(sfondo.getPrefWidth()*910/1280);
			AdvancedSearch.setLayoutX(sfondo.getPrefWidth()*34/1280);
			
			ricerca.setPrefWidth(sfondo.getPrefWidth()*650/1280);
			ricerca.setLayoutX(sfondo.getPrefWidth()*315/1280);
						
			Consigliati.setPrefWidth(sfondo.getPrefWidth()*316/1280);
			Consigliati.setLayoutX(sfondo.getPrefWidth()*964/1280);
			
			budjet1.setLayoutX(sfondo.getPrefWidth()*6/1280);
			budjet2.setLayoutX(sfondo.getPrefWidth()*6/1280);
			budjet3.setLayoutX(sfondo.getPrefWidth()*6/1280);
			budjet4.setLayoutX(sfondo.getPrefWidth()*6/1280);
			
			type.setPrefWidth(sfondo.getPrefWidth()*217/1280);
			type.setLayoutX(sfondo.getPrefWidth()*469/1280);
			
			TurnBack.setPrefWidth(sfondo.getPrefWidth()*30/1280);
			TurnBack.setLayoutX(sfondo.getPrefWidth()*26/1280);
			
			lista.setPrefWidth(sfondo.getPrefWidth()*885/1280);
			lista.setLayoutX(sfondo.getPrefWidth()*61/1280);
			
			Advanced.setPrefWidth(sfondo.getPrefWidth()*15/1280);
			Advanced.setLayoutX(sfondo.getPrefWidth()*27/1280);
			
			
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

	public void HandlerReserch() {
		
	}
}
