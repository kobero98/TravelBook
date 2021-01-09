package main.java.travelbook.view;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class ProfileOtherController {
	private BorderPane mainPane;
	private int goBack;
	private ViewTravelController controller;
	private AnchorPane internalPane;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private ListView<String> travels;
	@FXML
	private Pane profilePhoto;
	@FXML
	private Text userName;
	@FXML
	private Text myDescr;
	@FXML
	private ListView<String> show;
	@FXML
	private Button favButton;
	@FXML
	private ImageView favIcon;
	@FXML
	private Text favText;
	@FXML
	private Button followerButton;
	@FXML
	private StackPane listTitle;
	@FXML
	private Button backButton;
	@FXML
	private Button showBackButton;
	@FXML
	private Label listText;
	@FXML
	private Button followingButton;
	@FXML
	private ImageView map;
	@FXML
	private Label placeVisited;
	private String viewTravel = "ViewTravel.fxml";
	public void initialize() {
		
		 ObservableList<String> data = FXCollections.observableArrayList("travel1lungo", "travel2", "travel3", "travel4");
		 travels.setItems(data); 
		 travels.setCellFactory(list -> new TravelCell());
		followerButton.setText("Followers: "+"5");
		followingButton.setText("Following: "+"7");
		placeVisited.setText(userName.getText() + " has visited " + "4" +" places");
	}
	class TravelCell extends ListCell<String>{
		@Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
            	HBox travel = new HBox();
            	travel.setPrefWidth(mainAnchor.getPrefWidth()*530/1280);
        		travel.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
            	travel.setMaxWidth(USE_PREF_SIZE);
            	travel.setMinWidth(USE_PREF_SIZE);
            	
            	CornerRadii rad = new CornerRadii(25);
            	Insets in = new Insets(0);
            	BackgroundFill bgcc = new BackgroundFill(Paint.valueOf("rgb(250, 250, 250)"), rad, in);
            	
            	Background mybg = new Background(bgcc);
            	travel.setBackground(mybg);
            	Pane travelPic = new Pane();
            	travelPic.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
            	travelPic.setPrefWidth(mainAnchor.getPrefWidth()*265/1280);
            	try {
            		Image myPhoto = new Image("main/java/travelbook/cupola1.jpg");
            		BackgroundImage bgPhoto = new BackgroundImage(myPhoto, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
            		Background mybg1 = new Background(bgPhoto);
            		travelPic.setBackground(mybg1);
            	}catch(IllegalArgumentException e) {
            		e.printStackTrace();
            		BackgroundFill bgcc1 = new BackgroundFill(Paint.valueOf("rgb(255, 162, 134)"), rad, in);
                	
                	Background mybg1 = new Background(bgcc1);
                	travelPic.setBackground(mybg1);
            	}
            	
            	travelPic.setStyle("-fx-shape: \"M 350 900 L 350 795 C 350 780 360 770 375 770 L 438 770 C 453 770 463 780 463 795 L 463 900 Z\"");
            	VBox vBox = new VBox();
            	HBox hBox = new HBox();
            	vBox.setPrefWidth(mainAnchor.getPrefWidth()*265/1280);
            	vBox.setMaxWidth(USE_PREF_SIZE);
            	vBox.setSpacing(mainAnchor.getPrefHeight()*(180.0/15)/625);
            	Label name = new Label(item);
            	Text descr = new Text("this is a description mooolto lunga che non è una descrizione");
            	descr.setWrappingWidth(mainAnchor.getPrefWidth()*265/1280);
            	hBox.setAlignment(Pos.BOTTOM_RIGHT);
 
            	Button fav = new Button();
            	fav.setPrefWidth(mainAnchor.getPrefWidth()*35/1280);
            	fav.setPrefHeight(mainAnchor.getPrefHeight()*35/625);
            	travel.setOnMouseClicked(e->{
            		FXMLLoader loader=new FXMLLoader();
            		loader.setLocation(ProfileViewController.class.getResource(viewTravel));
            		try {
            			internalPane=(AnchorPane)loader.load();
            			mainPane.setCenter(internalPane);
            			controller=loader.getController();
            			controller.setMainPane(mainPane,3);
            		}catch(IOException exc) {
            			exc.printStackTrace();
            		}
            	});
            	fav.setOnMouseClicked(e->{
            		//dummy method
            		String css = "fav-selected";
            		if(favButton.getStyleClass().contains(css)) {
            			favButton.getStyleClass().remove(css);
            		}
            		else {
            			favButton.getStyleClass().add(css);
            		}
            	});
            	hBox.getChildren().add(fav);
            	vBox.getChildren().add(name);
            	vBox.getChildren().add(descr);
            	vBox.getChildren().add(hBox);
            	
            	travel.getChildren().add(travelPic);
            	travel.getChildren().add(vBox);
            	mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->{            		
            		travel.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
            		travelPic.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
                	fav.setPrefHeight(mainAnchor.getPrefHeight()*35/625);
            	});
            	mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->{
            		travel.setPrefWidth(mainAnchor.getPrefWidth()*530/1280);
            		travelPic.setPrefWidth(mainAnchor.getPrefWidth()*265/1280);
            		fav.setPrefWidth(mainAnchor.getPrefWidth()*35/1280);
            		descr.setWrappingWidth(mainAnchor.getPrefWidth()*265/1280);
            	});
            	setGraphic(travel);
            	
            	
            }
		}
	}
	public void setMainPane(BorderPane main, int provenience) {
		this.mainPane=main;
		this.goBack=provenience;
this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->					
			this.mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight()));
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->
			this.mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth())); 
		
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followerButton.setLayoutY(mainAnchor.getHeight()*410/625);
			followingButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followingButton.setLayoutY(mainAnchor.getHeight()*410/625);
			favButton.setPrefHeight(mainAnchor.getHeight()*50/625);
			favButton.setLayoutY(mainAnchor.getHeight()*513/625);
			favIcon.setFitHeight(mainAnchor.getHeight()*27.5/625);
			favText.setLayoutY(mainAnchor.getHeight()*534/625);
			map.setFitHeight(mainAnchor.getHeight()*160/625);
			map.setLayoutY(mainAnchor.getHeight()*434/625);
			placeVisited.setPrefHeight(mainAnchor.getHeight()*160/625);
			placeVisited.setLayoutY(mainAnchor.getHeight()*419/625);
			profilePhoto.setPrefHeight(mainAnchor.getHeight()*200/625);
			profilePhoto.setLayoutY(mainAnchor.getHeight()*90/625);
			userName.setLayoutY(mainAnchor.getHeight()*150/625);
			myDescr.setLayoutY(mainAnchor.getHeight()*200/625);
			show.setPrefHeight(mainAnchor.getHeight()*575/625);
			show.setLayoutY(mainAnchor.getHeight()*50/625);
			listTitle.setPrefHeight(mainAnchor.getHeight()*50/625);
			backButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			backButton.setLayoutY(mainAnchor.getHeight()*20/625);
			showBackButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			listText.setPrefHeight(mainAnchor.getHeight()*30/625);
			travels.setPrefHeight(mainAnchor.getHeight()*591/625);
			travels.setLayoutY(mainAnchor.getHeight()*14/625);
		});	
		
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followerButton.setLayoutX(mainAnchor.getWidth()*29/1280);
			followingButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followingButton.setLayoutX(mainAnchor.getWidth()*158/1280);
			favButton.setPrefWidth(mainAnchor.getWidth()*50/1280);
			favButton.setLayoutX(mainAnchor.getWidth()*41/1280);
			favIcon.setFitWidth(mainAnchor.getWidth()*30/1280);
			favText.setLayoutX(mainAnchor.getWidth()*95/1280);
			favText.setWrappingWidth(mainAnchor.getWidth()*152/1280);
			map.setFitWidth(mainAnchor.getWidth()*285/1280);
			map.setLayoutX(mainAnchor.getWidth()*307/1280);
			placeVisited.setPrefWidth(mainAnchor.getWidth()*270/1280);
			placeVisited.setLayoutX(mainAnchor.getWidth()*317/1280);
			profilePhoto.setPrefWidth(mainAnchor.getWidth()*200/1280);
			profilePhoto.setLayoutX(mainAnchor.getWidth()*65/1280);
			userName.setLayoutX(mainAnchor.getWidth()*269/1280);
			userName.setWrappingWidth(mainAnchor.getWidth()*326/1280);
			myDescr.setLayoutX(mainAnchor.getWidth()*269/1280);
			myDescr.setWrappingWidth(mainAnchor.getWidth()*326/1280);
			show.setPrefWidth(mainAnchor.getWidth()*297/1280);
			listTitle.setPrefWidth(mainAnchor.getWidth()*297/1280);
			backButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			backButton.setLayoutX(mainAnchor.getWidth()*20/1280);
			showBackButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			listText.setPrefWidth(mainAnchor.getWidth()*200/1280);
			travels.setPrefWidth(mainAnchor.getWidth()*606/1280);
			travels.setLayoutX(mainAnchor.getWidth()*631/1280);
		});	
	this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
	this.mainAnchor.setPrefWidth(mainPane.getWidth());
	}

	@FXML
	private void favouriteList(){
		show.setVisible(true);
		listTitle.setVisible(true);
		listText.setText(userName.getText() + " favourite travels");
		ObservableList<String> fav = FXCollections.observableArrayList("travel1", "travel2", "travel3");
		show.setItems(fav);
	}
	@FXML
	private void followerList() {
		show.setVisible(true);
		listTitle.setVisible(true);
		listText.setText(userName.getText() + " followers");
		ObservableList<String> fav = FXCollections.observableArrayList("follower1", "follower2", "follower3");
		show.setItems(fav);
	}
	@FXML
	private void followingList() {
		show.setVisible(true);
		listTitle.setVisible(true);
		listText.setText(userName.getText() + " interesting people");
		ObservableList<String> fav = FXCollections.observableArrayList("following1", "following2", "following3");
		show.setItems(fav);
	}
	@FXML
	private void showBack() {
		show.setVisible(false);
		listTitle.setVisible(false);
	}
	@FXML
	private void back() {
		FXMLLoader loader;
		switch (goBack){
		case 11:
			loader=new FXMLLoader();
			loader.setLocation(ProfileViewController.class.getResource(viewTravel));
			try {
				internalPane=(AnchorPane)loader.load();
				mainPane.setCenter(internalPane);
				controller=loader.getController();
				controller.setMainPane(mainPane,1); //da risolvere
			}catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case 12:
			loader=new FXMLLoader();
			loader.setLocation(ProfileViewController.class.getResource(viewTravel));
			try {
				internalPane=(AnchorPane)loader.load();
				mainPane.setCenter(internalPane);
				controller=loader.getController();
				controller.setMainPane(mainPane,2); //da risolvere
			}catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case 13:
			loader=new FXMLLoader();
			loader.setLocation(ProfileViewController.class.getResource(viewTravel));
			try {
				internalPane=(AnchorPane)loader.load();
				mainPane.setCenter(internalPane);
				controller=loader.getController();
				controller.setMainPane(mainPane,3); //da risolvere
			}catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				MenuBar.getInstance().moveToChat(mainPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				MenuBar.getInstance().moveToExplore(mainPane);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
