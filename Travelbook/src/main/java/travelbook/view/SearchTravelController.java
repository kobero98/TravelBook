package main.java.travelbook.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.java.travelbook.controller.ControllerSearch;
import main.java.travelbook.model.bean.SearchTrip;

public class SearchTravelController {
	private BorderPane mainPane;
	@FXML
	private AnchorPane sfondo;
	@FXML
	private Button turnBack;
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
	private Label foundLabel;
	@FXML
	private Line lineaListView;
	@FXML
	private ListView <String> lista;
	@FXML
	private Button advanced;
	@FXML
	private Pane advancedSearch;
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
	private ScrollPane consigliati;
	@FXML
	private Pane ricerca;
	@FXML
	private ListView <MyTypes> type;
	@FXML
	private VBox tipiSelezionati; 
	@FXML
	private AnchorPane consigliatiAnchor;
	@FXML
	private VBox consigliatiVbox;
	@FXML
	private Line ricercaLine;
	@FXML 
	private Button searchButton;
	@FXML
	private TextField ricercaTextField;
	@FXML
	private Line lineaVerticaleGrande;
	@FXML
	private ScrollPane scrollSelezionati;
	private ToggleGroup group;
	private List <MyTypes> typeChoose=new ArrayList<>();
	class MyTypes{
		private String tipo;
		private Color colore;
		public MyTypes(String type,Color color) {
			this.tipo=type;
			this.colore=color;
		}
		public void setType(String types)
		{
			this.tipo=types;
		}
		public void setColor(Color color)
		{
			this.colore=color;
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
	            	label.getStyleClass().add("categories-label");
	            	label.setWrapText(true);
	            	label.setMaxWidth(type.getHeight()-1);
	            	Circle cerchio =new Circle(10,item.getColor());
	            	box.getChildren().addAll(cerchio,label);
	            	HBox.setMargin(cerchio, new Insets(2));
	            	HBox.setMargin(label, new Insets(2));
	            	box.setAlignment(Pos.CENTER_LEFT);
	            	setGraphic(box);
	            }
	        }
		}
	@FXML
	private void initialize() {
		SearchCityTextField newText=new SearchCityTextField(this.ricercaTextField);
		ObservableList<MyTypes> information = FXCollections.observableArrayList(new MyTypes("Romantic Trip",Color.DARKMAGENTA),new MyTypes("Family Holiday",Color.DARKTURQUOISE),
				new MyTypes("On The Road",Color.LIMEGREEN),new MyTypes("Children Friendly",Color.CRIMSON),new MyTypes("Travel with Friend",Color.NAVY),
				new MyTypes("Cultural Travel",Color.ORANGE),new MyTypes("Relaxing Holiday",Color.VIOLET));
		MultipleSelectionModel<MyTypes> selectionModel = type.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
		this.type.setItems(information);
		this.type.setCellFactory(list-> new TipoListaGraphic());
		selectionModel.selectedItemProperty().addListener((observable,oldValue,  newValue)-> 
			{
				MyTypes addType = selectionModel.getSelectedItem();
				boolean bool = addType!=null && !typeChoose.contains(addType);
		        	if(bool) {
		        		typeChoose.add(addType);
		        		HBox box= new HBox(2);
		            	Label label= new Label(addType.getType());
		            	label.setTextFill(Color.WHITE);
		            	label.getStyleClass().add("categories-label");
		            	label.setWrapText(true);
		                Button delet =new Button();
		                
		                // when button is pressed 
		                delet.setOnMouseClicked(this::delButton);
		            	delet.setPrefSize(15, 15);
		            	delet.setMaxWidth(Region.USE_PREF_SIZE);
		            	delet.setMaxHeight(Region.USE_PREF_SIZE);
		            	delet.setMinWidth(Region.USE_PREF_SIZE);
		            	delet.setMinHeight(Region.USE_PREF_SIZE);
		            	box.setPrefWidth(tipiSelezionati.getPrefWidth()*0.75);
		            	box.setMaxWidth(Region.USE_PREF_SIZE);
		                box.getChildren().addAll(delet,label);
		                HBox.setMargin(delet, new Insets(7));
		                HBox.setMargin(label, new Insets(2));
		            	box.setAlignment(Pos.CENTER_LEFT);
		            	box.setBackground(new Background(new BackgroundFill(addType.getColor(),new CornerRadii(15), null)));
		            	tipiSelezionati.getChildren().add(box);
		            	tipiSelezionati.setPrefHeight(typeChoose.size()*40.0);
		            	tipiSelezionati.setAlignment(Pos.CENTER_LEFT);
		            	tipiSelezionati.setPadding(new Insets(0, 10, 0, 10));
		            	tipiSelezionati.setSpacing(sfondo.getHeight()*10/625);
		        		tipiSelezionati.getParent().getParent().getParent().setVisible(true);
		        		
		           }
			
			}
	          );
		
	}
	private void delButton(MouseEvent e){ 
    	Button b= (Button) e.getSource();
    	HBox box1=(HBox) b.getParent();
    	
    	Label l =(Label) box1.getChildren().get(1);
        MyTypes i=new MyTypes(l.getText(),(Color) box1.getBackground().getFills().get(0).getFill());
        int j=0;
        while(j<typeChoose.size())
        	{	if(typeChoose.get(j).getType().equals(i.getType())) {
        			typeChoose.remove(j);	
        			j--;
        			}
        		j++;
        	}
        tipiSelezionati.getChildren().remove(box1);
    	tipiSelezionati.setPrefHeight(typeChoose.size()*40.0);
    	if(typeChoose.isEmpty()) {
    		tipiSelezionati.getParent().getParent().getParent().setVisible(false);

    	}
    	type.getSelectionModel().clearSelection();
}
	public void setMainPane(BorderPane main)
	{
		this.mainPane=main;
		
		this.group = new ToggleGroup();
	    budjet1.setToggleGroup(group);
	    budjet2.setToggleGroup(group);
	    budjet3.setToggleGroup(group);
	    budjet4.setToggleGroup(group);
	    this.minCost.setOnMouseClicked(e-> { 
		      TextField s=(TextField) e.getSource();
		       try{
		    	   Double.parseDouble((s.getText()));
		       }catch(NumberFormatException ex)
		       {
		    	   s.clear();
		       }
		   } );
	    this.maxCost.setOnMouseClicked( e->{ 
		      TextField s=(TextField) e.getSource();
		       try{
		    	   Double.parseDouble((s.getText()));
		       }catch(NumberFormatException ex)
		       {
		    	   s.clear();
		       }
		   } );
	    this.minCost.textProperty().addListener((observable,oldValue,newValue)-> {
	    		if (!newValue.matches("\\d*")) {
	 
	                minCost.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        
	    });
	    
	    this.maxCost.textProperty().addListener((observable,oldValue,newValue)-> {
	            if (!newValue.matches("\\d*")) {
	                maxCost.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	    });
	    //then define the resize logic
		this.sfondo.heightProperty().addListener((observable,oldValue,newValue)->{
			consigliati.setPrefHeight(sfondo.getHeight()*465/625);
			consigliati.setLayoutY(sfondo.getHeight()*160/625);
			
			consigliatiAnchor.setPrefHeight(sfondo.getHeight()*890/625);
			
			consigliatiVbox.setPrefHeight(sfondo.getHeight()*800/625);
			consigliatiVbox.setLayoutY(sfondo.getHeight()*6/625);
			
			turnBack.setPrefHeight(sfondo.getHeight()*40/625);
			turnBack.setLayoutY(sfondo.getPrefHeight()*30/625);
			
			double t;
			double y;
			double z;
			double w;
			if(soprasotto==0) {
				t=0.6944;
				y=0.2912;
				z=0.2144;
				w=0.2672;
			}
			
			else {
				t=0.3744;
				y=0.6112;
				z=0.5344;
				w=0.5872;
			}
			
			foundLabel.setLayoutY(sfondo.getHeight()*z);
			
			lineaListView.setLayoutY(sfondo.getHeight()*w);
			
			ricerca.setPrefHeight(sfondo.getHeight()*45/625);
			ricerca.setLayoutY(sfondo.getHeight()*50/625);
			
			ricercaLine.setLayoutY(sfondo.getHeight()*8/625);
			ricercaLine.setStartY(sfondo.getHeight()*30/625);
			
			searchButton.setPrefHeight(sfondo.getHeight()*40/625);
			searchButton.setLayoutY(sfondo.getHeight()*3/625);
			
			ricercaTextField.setPrefHeight(sfondo.getHeight()*45/625);
			
			lineaVerticaleGrande.setLayoutY(sfondo.getHeight()*131/625);
			lineaVerticaleGrande.setStartY(sfondo.getHeight()*3/625);
			lineaVerticaleGrande.setEndY(sfondo.getHeight()*484/625);
			
			scritta2.setLayoutY(sfondo.getHeight()*120/625);
			
			advanced.setPrefHeight(sfondo.getHeight()*13/625);
			advanced.setLayoutY(sfondo.getHeight()*114/625);
			
			scritta1.setLayoutY(sfondo.getHeight()*109/625);
			
			lista.setPrefHeight(sfondo.getHeight()*t);
			lista.setLayoutY(sfondo.getHeight()*y);
			
			advancedSearch.setPrefHeight(sfondo.getHeight()*200/625);
			advancedSearch.setLayoutY(sfondo.getHeight()*133/625);
			
			scritta3.setLayoutX(sfondo.getHeight()*7/625);
			budjet1.setLayoutY(sfondo.getHeight()*55/625);
			budjet1.setPrefHeight(sfondo.getHeight()*26.8/625);
			budjet2.setLayoutY(sfondo.getHeight()*87/625);
			budjet2.setPrefHeight(sfondo.getHeight()*26.8/625);
			budjet3.setLayoutY(sfondo.getHeight()*119/625);
			budjet3.setPrefHeight(sfondo.getHeight()*26.8/625);
			budjet4.setLayoutY(sfondo.getHeight()*151/625);
			budjet4.setPrefHeight(sfondo.getHeight()*26.8/625);
			
			scritta5.setLayoutY(sfondo.getHeight()*7/625);
			minCost.setLayoutY(sfondo.getHeight()*78/625);
			minCost.setPrefHeight(sfondo.getHeight()*44/625);
			maxCost.setLayoutY(sfondo.getHeight()*78/625);
			maxCost.setPrefHeight(sfondo.getHeight()*44/625);
			
			scritta4.setLayoutY(sfondo.getHeight()*7/625);
			
			type.setPrefHeight(sfondo.getHeight()*137/625);
			type.setLayoutY(sfondo.getHeight()*54/625);
			
			tipiSelezionati.setPrefHeight(sfondo.getHeight()*tipiSelezionati.getPrefHeight()/625);
			scrollSelezionati.setLayoutY(sfondo.getHeight()*55/625);
			scrollSelezionati.setPrefHeight(sfondo.getHeight()*137/625);
			
		});
		this.sfondo.widthProperty().addListener((observable,oldValue,newValue)->{
			
			consigliati.setPrefWidth(sfondo.getWidth()*316/1280);
			consigliati.setLayoutX(sfondo.getWidth()*964/1280);
			
			consigliatiAnchor.setPrefWidth(sfondo.getWidth()*380/1280);
			
			consigliatiVbox.setPrefWidth(sfondo.getWidth()*190/1280);
			consigliatiVbox.setLayoutX(sfondo.getWidth()*43/1280);
			
			turnBack.setPrefWidth(sfondo.getWidth()*40/1280);
			turnBack.setLayoutX(sfondo.getWidth()*34/1280);
			
			foundLabel.setLayoutX(sfondo.getWidth()*54/1280);
			
			lineaListView.setLayoutX(sfondo.getWidth()*62/1280);
			lineaListView.setEndX(sfondo.getWidth()*800/1280);
			
			ricerca.setPrefWidth(sfondo.getWidth()*650/1280);
			ricerca.setLayoutX(sfondo.getWidth()*315/1280);
			
			ricercaLine.setLayoutX(sfondo.getWidth()*60/1280);
			
			
			searchButton.setPrefWidth(sfondo.getWidth()*40/1280);
			searchButton.setLayoutX(sfondo.getWidth()*14/1280);
			
			ricercaTextField.setPrefWidth(sfondo.getWidth()*575/1280);
			ricercaTextField.setLayoutX(sfondo.getWidth()*75/1280);
			
			lineaVerticaleGrande.setLayoutX(sfondo.getWidth()*953/1280);
			
			scritta2.setLayoutX(sfondo.getWidth()*972/1280);
			
			advanced.setPrefWidth(sfondo.getWidth()*15/1280);
			advanced.setLayoutX(sfondo.getWidth()*27/1280);
			
			scritta1.setLayoutX(sfondo.getWidth()*48/1280);
			
			lista.setPrefWidth(sfondo.getWidth()*885/1280);
			lista.setLayoutX(sfondo.getWidth()*61/1280);
			
			advancedSearch.setPrefWidth(sfondo.getWidth()*910/1280);
			advancedSearch.setLayoutX(sfondo.getWidth()*34/1280);
			
			scritta3.setLayoutX(sfondo.getWidth()*6/1280);
			budjet1.setLayoutX(sfondo.getWidth()*6/1280);
			budjet2.setLayoutX(sfondo.getWidth()*6/1280);
			budjet3.setLayoutX(sfondo.getWidth()*6/1280);
			
			budjet4.setLayoutX(sfondo.getWidth()*6/1280);
			
			scritta5.setLayoutX(sfondo.getWidth()*211/1280);
			minCost.setLayoutX(sfondo.getWidth()*220/1280);
			minCost.setPrefWidth(sfondo.getWidth()*96/1280);
			maxCost.setLayoutX(sfondo.getWidth()*347/1280);
			maxCost.setPrefWidth(sfondo.getWidth()*96/1280);
			
			scritta4.setLayoutX(sfondo.getWidth()*479/1280);
			
			type.setPrefWidth(sfondo.getWidth()*217/1280);
			type.setLayoutX(sfondo.getWidth()*469/1280);
			
			tipiSelezionati.setPrefWidth(sfondo.getWidth()*215/1280);
			scrollSelezionati.setLayoutX(sfondo.getWidth()*697/1280);
			scrollSelezionati.setPrefWidth(sfondo.getWidth()*217/1280);
			
			
		});
		sfondo.setPrefHeight(this.mainPane.getHeight()*625/720);
		sfondo.setPrefWidth(this.mainPane.getWidth());
	
	}
	
	private int soprasotto=0;
	@FXML
	private void showSearchAdvanced() {
		if(this.soprasotto==0) {
			this.soprasotto=1;
			double move=this.advancedSearch.getHeight();
			this.foundLabel.setLayoutY(this.foundLabel.getLayoutY()+move);
			this.lineaListView.setLayoutY(this.lineaListView.getLayoutY()+move);
			this.lista.setPrefHeight(this.lista.getHeight()-move);
			this.lista.setLayoutY(this.lista.getLayoutY()+move);
			this.advancedSearch.setVisible(true);
		
		}
		else {
			this.soprasotto=0;
			double move=this.advancedSearch.getHeight();
			this.foundLabel.setLayoutY(this.foundLabel.getLayoutY()-move);
			this.lineaListView.setLayoutY(this.lineaListView.getLayoutY()-move);
			this.lista.setPrefHeight(this.lista.getPrefHeight()+move);
			this.lista.setLayoutY(this.lista.getLayoutY()-move);
			this.advancedSearch.setVisible(false);
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

	@FXML
	public void handlerReserch() {
		String r=ricercaTextField.getText();
		if(!r.isEmpty()) return;
		SearchTrip trip=new SearchTrip();
		if(!minCost.getText().isEmpty() && !minCost.getText().equals("min"))
		{
			int i=Integer.parseInt(minCost.getText());
			trip.setDurationMin(i);
		}
		else trip.setDurationMin(0);
		if(!maxCost.getText().isEmpty() && !maxCost.getText().equals("max"))
		{
			int i=Integer.parseInt(minCost.getText());
			trip.setDurationMax(i);
		}
		else trip.setDurationMax(0);
		if(trip.getDurationMin()>trip.getDurationMax()) return;
		trip.setCostoMax(0);
		trip.setCostoMin(0);
		if(budjet1.isSelected()) trip.setCostoMax(300);
		if(budjet2.isSelected()) {
				trip.setCostoMax(1000);
				trip.setCostoMin(300);
		}
		if(budjet1.isSelected()) {
				trip.setCostoMax(2000);
				trip.setCostoMin(1000);
		}
		if(budjet1.isSelected()) {
				trip.setCostoMin(2000);
		}
		List <String> s=new ArrayList<>();
		for(int i=0;i<typeChoose.size();i++) s.add(typeChoose.get(i).getType());
		if(s.isEmpty()) s=null;
		trip.setType(s);
		trip.setCity(r);
		ControllerSearch.getInstance().search(trip);
	}
}
