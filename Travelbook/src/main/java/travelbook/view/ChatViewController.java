package main.java.travelbook.view;



import java.io.IOException;
import javafx.scene.input.KeyCode;

import javafx.util.Callback;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
public class ChatViewController {
	//private MenuBar menu=new MenuBar();
	private Object array1[]=new Object[15];
	private Button button;
	private BorderPane mainPane;
	@FXML
	private Button send;
	@FXML
	private TextArea write;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane chatAnchor;
	@FXML
	private StackPane writeBar;
	@FXML
	private ListView<String> sentList;
	@FXML
	private ListView<MyItem> contactList;
	@FXML
	private ButtonBar menuBar;
	class MyItem {
		private StringProperty specialIndicator;
		private StringProperty name;
        MyItem(String name) {
            this.name = new SimpleStringProperty(name);
            this.specialIndicator = new SimpleStringProperty();
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getSpecialIndicator() {
            return specialIndicator.get();
        }

        public StringProperty specialIndicatorProperty() {
            return specialIndicator;
        }

        public void setSpecialIndicator(String specialIndicator) {
            this.specialIndicator.set(specialIndicator);
        }
    }
	
	public void initialize() {
	
	 ObservableList<String> data = FXCollections.observableArrayList(
	            "!chocolate", "salmonsalmon salmonsalmon salmonsalmonn salmonsalmon salmonsalmon salmonsalmon salmonsalmonn salmonsalmon", "!salmonsalmon salmonsalmon salmonsalmonn salmonsalmon salmonsalmon salmonsalmon salmonsalmonn salmonsalmon","!gold", "coral", "darkorchid",
	            "darkgoldenrod", "lightsalmon", "black", "!rosybrown", "blue",
	            "blueviolet", "brown", "salmon", "gold", "coral", "darkorchid",
	            "darkgoldenrod", "lightsalmon", "!black", "rosybrown", "blue",
	            "blueviolet", "brown");
	 sentList.setItems(data); 
	 sentList.scrollTo(data.size());
	 sentList.setCellFactory(new Callback<ListView<String>, 
	            ListCell<String>>() {
         @Override 
         public ListCell<String> call(ListView<String> list) {
             return new messageCell();
         }
     }
	);
	 ObservableList<MyItem> contacts = FXCollections.observableArrayList(new MyItem("object0"), new MyItem("object1"),new MyItem("object2"),new MyItem("object3"),new MyItem("object4"));
    
	contactList.setItems(contacts);
	contactList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyItem>() {
        public void changed(ObservableValue<? extends MyItem> observable,
                MyItem oldValue, MyItem newValue) { 
              contactList.getItems().get(contactList.getSelectionModel().getSelectedIndex()).setSpecialIndicator("selected");
              contactList.setItems(null);
              contactList.setItems(contacts);
        }
        });
	contactList.setCellFactory(new Callback<ListView<MyItem>,
			ListCell<MyItem>>(){
		@Override
		public ListCell<MyItem> call(ListView<MyItem> list){
			return new contactCell();
		}
	});
	 
	 
	
	}
	
	
	
	 class messageCell extends ListCell<String>{
		@Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
            	HBox hBox = new HBox();
            	if(item.startsWith("!")) {
            		hBox.setAlignment(Pos.BASELINE_LEFT);
            	}
            	else {
            		hBox.setAlignment(Pos.BASELINE_RIGHT);
            	}
                // Create centered Label
                Label label = new Label(item);
                label.setWrapText(true);
                label.setMaxWidth((chatAnchor.getPrefWidth()-(1/5)*chatAnchor.getPrefWidth())/2);
                label.setAlignment(Pos.CENTER);

                hBox.getChildren().add(label);
                setGraphic(hBox);
            }
        }
	}
	class contactCell extends ListCell<MyItem>{
		@Override
		public void updateItem(MyItem item, boolean empty) {
			super.updateItem(item, empty);
			if(!empty) {
				HBox hBox = new HBox();
				hBox.setSpacing(contactList.getPrefWidth()/5);
				hBox.getStyleClass().add("h-box");
				hBox.setAlignment(Pos.CENTER);
				if("selected".equalsIgnoreCase(item.getSpecialIndicator())) {
					hBox.getStyleClass().add("h-box-selected");
				}
				Text contact = new Text(item.getName());
				contact.getStyleClass().add("text");
				Image photo = new Image("main/resources/ProfilePageImages/cupola1.jpg");
				ImageView contactPic = new ImageView(photo);
				contactPic.setPreserveRatio(true);
				contactPic.setFitHeight(50);
				contactPic.setFitWidth(100);
				hBox.getChildren().add(contactPic);
				hBox.getChildren().add(contact);
				item.setSpecialIndicator("");
				setGraphic(hBox);
			}
		}
	}
	public void setMainPane(BorderPane main) {
		this.mainPane=main;
		//then define the resize logic
		this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->{
			mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight());
		});
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->{
			mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth());
			
			
		}); 
		this.mainPane.heightProperty().addListener((observable,oldValue,newValue)->{
			StackPane title=(StackPane)mainPane.getTop();
			title.setPrefHeight(mainPane.getHeight()*94/720);
			mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
			System.out.println("Altezza predefinita: "+ this.mainAnchor.getPrefHeight());
		});
		this.mainPane.widthProperty().addListener((observable,oldValue,newValue)->{
			mainAnchor.setPrefWidth(mainPane.getWidth());
		});
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			System.out.println("Altezza anchorPane: " + this.mainAnchor.getHeight());
			chatAnchor.setPrefHeight(mainAnchor.getPrefHeight()*588/625);
			chatAnchor.setLayoutY(mainAnchor.getPrefHeight()*25/625);
			sentList.setPrefHeight(mainAnchor.getPrefHeight()*498/625);
			
			writeBar.setPrefHeight(mainAnchor.getPrefHeight()*90/625);
			writeBar.setLayoutY(mainAnchor.getPrefHeight()*498/625);
			write.setPrefHeight(mainAnchor.getPrefHeight()*70/625);
			send.setPrefHeight(mainAnchor.getHeight()*30/625);
			contactList.setPrefHeight(mainAnchor.getHeight()*500/625);
			contactList.setLayoutY(mainAnchor.getHeight()*107/625);
			menuBar.setPrefHeight(mainAnchor.getHeight()*85/625);
			menuBar.setLayoutY(0);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefHeight(mainAnchor.getHeight()*56/625);
			}
		});	
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			chatAnchor.setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			chatAnchor.setLayoutX(mainAnchor.getPrefWidth()*350/1280);
			sentList.setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			
			writeBar.setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			write.setPrefWidth(mainAnchor.getPrefWidth()*750/1280);
			send.setPrefWidth(mainAnchor.getWidth()*40/1280);
			contactList.setPrefWidth(mainAnchor.getWidth()*300/1280);
			contactList.setLayoutX(mainAnchor.getWidth()*33/1280);
			menuBar.setPrefWidth(mainAnchor.getWidth()*592/1280);
			menuBar.setLayoutX(0);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefWidth(mainAnchor.getWidth()*147/1280);
			}
		});	
		this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
		this.mainAnchor.setPrefWidth(mainPane.getWidth());
		write.setOnKeyTyped(e -> keyTyped(e));
	}
	private void keyTyped(KeyEvent evt) {
		KeyCode ch = evt.getCode();
		if(ch.equals(KeyCode.ENTER)|| evt.getCharacter().getBytes()[0] == '\n' || evt.getCharacter().getBytes()[0] == '\r') {
			sendHandler();
		}
	}
	private void clickButton(ActionEvent e) {
		System.out.println("ButtonPressed");
		Button temp = (Button) e.getSource();
		temp.getStyleClass().add("button-selected");
		
		
	}
	@FXML
    private void profileHandler(){
    	try {
    	MenuBar.getInstance().moveToProfile(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    private void addHandler() {
    	try {
    		MenuBar.getInstance().moveToAdd(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    private void exploreHandler() {
    	try {
    		MenuBar.getInstance().moveToExplore(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    private void sendHandler() {
    	sentList.getItems().add(write.getText());
    	write.clear();
    	sentList.scrollTo(sentList.getItems().size());
    }
    //questo codice non funzionaaaa, è da togliere
    @FXML
    private void scrollAppear() {
    	try {
    	for (Node node : sentList.lookupAll(".scroll-bar")) {
    	    if (node instanceof ScrollBar) {
    	        ScrollBar scrollBar = (ScrollBar) node;
    	        scrollBar.setVisible(true);
    	    }
    	}
    	}catch(NullPointerException e) {
    	e.printStackTrace();
    	}
    }
    @FXML
    private void scrollDisappear() {
    	try {
    	for (Node node : sentList.lookupAll(".scroll-bar")) {
    	    if (node instanceof ScrollBar) {
    	        ScrollBar scrollBar = (ScrollBar) node;
    	        scrollBar.setVisible(false);
    	    }
    	}
    	}catch(NullPointerException e) {
    		e.printStackTrace();
    	}
    }


}
