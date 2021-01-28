package main.java.travelbook.view;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import exception.DBException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.java.travelbook.controller.MyProfileController;
import main.java.travelbook.model.bean.Bean;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.ShareBean;
import main.java.travelbook.model.bean.UserBean;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.travelbook.util.Observable;
import main.java.travelbook.util.Observer;
public class ProfileViewController implements Observer{
	private BorderPane mainPane;
	private Object[] array1=new Object[15];
	private Button button;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private ButtonBar menuBar;
	@FXML
	private ListView<MiniTravelBean> travels;
	@FXML
	private AnchorPane profileAnchor;
	@FXML
	private Pane profilePhoto;
	@FXML
	private Button choosePhoto;
	@FXML
	private Text userName;
	@FXML
	private Text myDescr;
	@FXML
	private Button myDescrEdit;
	@FXML
	private TextArea descrWrite; 
	@FXML
	private ListView<Bean> show;
	@FXML
	private Button favButton;
	@FXML
	private ImageView favIcon;
	@FXML
	private Text favText;
	@FXML
	private Button shButton;
	@FXML
	private ImageView shIcon;
	@FXML
	private Text shText;
	@FXML
	private Button followerButton;
	@FXML
	private StackPane listTitle;
	@FXML
	private Button backButton;
	@FXML
	private Label listText;
	@FXML
	private Button followingButton;
	@FXML
	private ImageView map;
	@FXML
	private Label placeVisited;
	@FXML
	private Button logOutButton;
	@FXML
	private Label errorMsg;
	private static final String ALERTCSS="src/main/java/travelbook/css/alert.css";
	private static final String PROJECTCSS="src/main/java/travelbook/css/project.css";
	private static final String HEADER_MSG ="Something went wrong!";
	private static final String WARN_IMG = "src/main/resources/AddViewImages/warning.png";
	UserBean user=MenuBar.getInstance().getLoggedUser();
	MyProfileController myController = new MyProfileController();
	public void initialize() {
		MenuBar.getInstance().setNewThread();
		MenuBar.getInstance().addObserver(this);
		new Thread(()->{
			ObservableList<MiniTravelBean> data;
			try {
				if(user.getTravel()!=null && !user.getTravel().isEmpty()) {
					data = FXCollections.observableList(myController.getTravel(user.getTravel()));
					travels.setItems(data); 
				}
			} catch (DBException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Travels unreacheable");
	    		alert.setHeaderText(HEADER_MSG);
	    		alert.setContentText("we couldn't reach your travels, try again");
	    		URL url = null;
		   		 try {
		   		 url = new File(ALERTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 url = new File(PROJECTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 
		   		 
		   			url = new File(WARN_IMG).toURI().toURL();
		   		} catch (MalformedURLException e1) {
		   			// TODO Auto-generated catch block
		   			e1.printStackTrace();
		   		}
	   		 	Image image = new Image(url.toString());
	   		 	ImageView imageView = new ImageView(image);
	   		 	alert.setGraphic(imageView);
	   		 	alert.showAndWait();
			}
			
			travels.setCellFactory(list->new TravelCell());
		}).start();

		
		if(user.getPhoto() !=null) {
			BackgroundImage bgPhoto = new BackgroundImage(user.getPhoto(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
			Background newBg = new Background(bgPhoto);
			profilePhoto.setBackground(newBg);
		}
		else {
			try {
				URL url = new File("src/main/resources/ProfilePageImages/travelers.png").toURI().toURL();
				Image myPhoto = new Image(url.toString());
				BackgroundImage bgPhoto = new BackgroundImage(myPhoto, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
				Background newBg = new Background(bgPhoto);
				profilePhoto.setBackground(newBg);
			}catch(IllegalArgumentException | MalformedURLException e) {
        		BackgroundFill bgcc1 = new BackgroundFill(Paint.valueOf("rgb(255, 162, 134)"), null, null);
            	
            	Background mybg1 = new Background(bgcc1);
            	profilePhoto.setBackground(mybg1);
			}
		}
		
		userName.setText(user.getName()+" "+user.getSurname());
		
		myDescr.setText(user.getDescription());
		
		followerButton.setText("Followers: "+user.getNFollower());
		followingButton.setText("Following: "+user.getNFollowing());
		placeVisited.setText("You have visited " + user.getnPlace()+" places");
	}
	class TravelCell extends ListCell<MiniTravelBean>{
		@Override
        public void updateItem(MiniTravelBean item, boolean empty) {
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
            		Image myPhoto = item.getPathImage();
            		BackgroundImage bgPhoto = new BackgroundImage(myPhoto, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
            		Background mybg1 = new Background(bgPhoto);
            		travelPic.setBackground(mybg1);
            	}catch(IllegalArgumentException | NullPointerException e) {
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
            	Label name = new Label(item.getNameTravel());
            	Text descr = new Text(item.getDescriptionTravel());
            	descr.setWrappingWidth(mainAnchor.getPrefWidth()*265/1280);
            	hBox.setAlignment(Pos.BOTTOM_RIGHT);
 
            	Button edit = new Button();
            	edit.setPrefWidth(mainAnchor.getPrefWidth()*35/1280);
            	edit.setPrefHeight(mainAnchor.getPrefHeight()*35/625);
            	edit.getStyleClass().add("edit");
            	travel.setOnMouseClicked(e->moveToView(item.getId()));
            	edit.setOnMouseClicked(e->{
            		try {
            			MenuBar.getInstance().setIdTravel(item.getId());
            			MenuBar.getInstance().moveToAddTravel(mainPane); //aggiungere id viaggio dopo aver sistemato add
            		}catch(IOException exc) {
            			exc.printStackTrace();
            		}
            	});
            	hBox.getChildren().add(edit);
            	vBox.getChildren().add(name);
            	vBox.getChildren().add(descr);
            	vBox.getChildren().add(hBox);
            	
            	travel.getChildren().add(travelPic);
            	travel.getChildren().add(vBox);
            	mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->{            		
            		travel.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
            		travelPic.setPrefHeight(mainAnchor.getPrefHeight()*180/625);
                	edit.setPrefHeight(mainAnchor.getPrefHeight()*35/625);
            	});
            	mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->{
            		travel.setPrefWidth(mainAnchor.getPrefWidth()*530/1280);
            		travelPic.setPrefWidth(mainAnchor.getPrefWidth()*265/1280);
            		edit.setPrefWidth(mainAnchor.getPrefWidth()*35/1280);
            		descr.setWrappingWidth(mainAnchor.getPrefWidth()*265/1280);
            	});
            	setGraphic(travel);
            	
            	
            }
		}
	}
	
	private void moveToView(int id) {
   		FXMLLoader loader=new FXMLLoader();
		
		ViewTravelController controller;
		AnchorPane internalPane;
		try {
			URL url = new File("src/main/java/travelbook/view/ViewTravel.fxml").toURI().toURL();
			MenuBar.getInstance().setIdTravel(id);
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			controller=loader.getController();
			controller.setMainPane(mainPane,2);
		}catch(IOException exc) {
			exc.printStackTrace();
		}
	}
	public void setMainPane(BorderPane main) {

		this.mainPane=main;
		
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
			favButton.setLayoutY(mainAnchor.getHeight()*499/625);
			favIcon.setFitHeight(mainAnchor.getHeight()*27.5/625);
			favText.setLayoutY(mainAnchor.getHeight()*519/625);
			shButton.setPrefHeight(mainAnchor.getHeight()*50/625);
			shButton.setLayoutY(mainAnchor.getHeight()*564/625);
			shIcon.setFitHeight(mainAnchor.getHeight()*27.5/625);
			shText.setLayoutY(mainAnchor.getHeight()*583/625);
			map.setFitHeight(mainAnchor.getHeight()*160/625);
			map.setLayoutY(mainAnchor.getHeight()*434/625);
			placeVisited.setPrefHeight(mainAnchor.getHeight()*160/625);
			placeVisited.setLayoutY(mainAnchor.getHeight()*419/625);
			profileAnchor.setPrefHeight(mainAnchor.getHeight()*300/625);
			profilePhoto.setPrefHeight(mainAnchor.getHeight()*200/625);
			profilePhoto.setLayoutY(mainAnchor.getHeight()*45/625);
			userName.setLayoutY(mainAnchor.getHeight()*137/625);
			myDescr.setLayoutY(mainAnchor.getHeight()*150/625);
			logOutButton.setPrefHeight(mainAnchor.getHeight()*35/625);
			logOutButton.setLayoutY(mainAnchor.getHeight()*14/625);
			choosePhoto.setPrefHeight(mainAnchor.getHeight()*40/625);
			choosePhoto.setLayoutY(mainAnchor.getHeight()*80/625);
			myDescrEdit.setPrefHeight(mainAnchor.getHeight()*35/625);
			myDescrEdit.setLayoutY(mainAnchor.getHeight()*155/625);
			descrWrite.setPrefHeight(mainAnchor.getHeight()*100/625);
			descrWrite.setLayoutY(mainAnchor.getHeight()*150/625);
			menuBar.setPrefHeight(mainAnchor.getHeight()*85/625);
			menuBar.setLayoutY(mainAnchor.getHeight()*300/625);
			show.setPrefHeight(mainAnchor.getHeight()*575/625);
			show.setLayoutY(mainAnchor.getHeight()*50/625);
			listTitle.setPrefHeight(mainAnchor.getHeight()*50/625);
			backButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			listText.setPrefHeight(mainAnchor.getHeight()*30/625);
			errorMsg.setPrefHeight(mainAnchor.getHeight()*70/625);
			errorMsg.setLayoutY(mainAnchor.getHeight()*300/625);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefHeight(mainAnchor.getHeight()*56/625);
			}
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
			shButton.setPrefWidth(mainAnchor.getWidth()*50/1280);
			shButton.setLayoutX(mainAnchor.getWidth()*41/1280);
			shIcon.setFitWidth(mainAnchor.getWidth()*30/1280);
			shText.setLayoutX(mainAnchor.getWidth()*95/1280);
			map.setFitWidth(mainAnchor.getWidth()*285/1280);
			map.setLayoutX(mainAnchor.getWidth()*307/1280);
			placeVisited.setPrefWidth(mainAnchor.getWidth()*270/1280);
			placeVisited.setLayoutX(mainAnchor.getWidth()*307/1280);
			profileAnchor.setPrefWidth(mainAnchor.getWidth()*592/1280);
			profilePhoto.setPrefWidth(mainAnchor.getWidth()*200/1280);
			profilePhoto.setLayoutX(mainAnchor.getWidth()*55/1280);
			userName.setLayoutX(mainAnchor.getWidth()*238/1280);
			myDescr.setLayoutX(mainAnchor.getWidth()*280/1280);
			myDescr.setWrappingWidth(mainAnchor.getWidth()*270/1280);
			logOutButton.setPrefWidth(mainAnchor.getWidth()*35/1280);
			logOutButton.setLayoutX(mainAnchor.getWidth()*14/1280);
			choosePhoto.setPrefWidth(mainAnchor.getWidth()*50/1280);
			choosePhoto.setLayoutX(mainAnchor.getWidth()*75/1280);
			myDescrEdit.setPrefWidth(mainAnchor.getWidth()*35/1280);
			myDescrEdit.setLayoutX(mainAnchor.getWidth()*515/1280);
			descrWrite.setPrefWidth(mainAnchor.getWidth()*270/1280);
			descrWrite.setLayoutX(mainAnchor.getWidth()*280/1280);
			menuBar.setPrefWidth(mainAnchor.getWidth()*592/1280);
			show.setPrefWidth(mainAnchor.getWidth()*297/1280);
			listTitle.setPrefWidth(mainAnchor.getWidth()*297/1280);
			backButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			listText.setPrefWidth(mainAnchor.getWidth()*200/1280);
			errorMsg.setPrefWidth(mainAnchor.getWidth()*260/1280);
			errorMsg.setLayoutX(mainAnchor.getWidth()*10/1280);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefWidth(mainAnchor.getWidth()*147/1280);
			}
			travels.setPrefWidth(mainAnchor.getWidth()*606/1280);
			travels.setLayoutX(mainAnchor.getWidth()*631/1280);
		});	
	this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
	this.mainAnchor.setPrefWidth(mainPane.getWidth());
	}
	@Override
	public void update(Observable bar, Object notify) {
		boolean value=(Boolean)notify;
		if(value) {
			Platform.runLater(()->{
				Circle dot = new Circle(6);
				dot.setFill(Color.DARKSALMON);
				mainAnchor.getChildren().add(dot);
				dot.setLayoutX(510);
				dot.setLayoutY(530);
				mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->
					dot.setLayoutY(mainAnchor.getHeight()*30/625));
				mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->
					dot.setLayoutX(mainAnchor.getWidth()*510/1280));
			});
			
		}
	}
	@Override
	public void update(Observable bar) {
		this.update(bar,true);
	}
	@FXML
	private void photoHandler(){
		FileChooser dialog=new FileChooser();
		dialog.setTitle("Choose a profile photo");
		dialog.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png","*.jpg"));
		File selectedFile=dialog.showOpenDialog(mainPane.getScene().getWindow());
		if(selectedFile!=null) {
			Image myPhoto=new Image(selectedFile.toURI().toString());
			BackgroundImage bgPhoto = new BackgroundImage(myPhoto, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
			Background newBg = new Background(bgPhoto);
			profilePhoto.setBackground(newBg);
			user.setPhoto(myPhoto);
			try {
				myController.updatePhoto(user.getId(),selectedFile);
				
			} catch (DBException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Update failed");
	    		alert.setHeaderText(HEADER_MSG);
	    		alert.setContentText("we couldn't update your information, try again");
	    		URL url = null;
		   		 try {
		   		 url = new File(ALERTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 url = new File(PROJECTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 
		   		 
		   			url = new File(WARN_IMG).toURI().toURL();
		   		} catch (MalformedURLException e1) {
		   			// TODO Auto-generated catch block
		   			e1.printStackTrace();
		   		}
	   		 	Image image = new Image(url.toString());
	   		 	ImageView imageView = new ImageView(image);
	   		 	alert.setGraphic(imageView);
	   		 	alert.showAndWait();
			}
		}
	}
	@FXML
	private void descriptionHandler() {
		descrWrite.setVisible(true);
		descrWrite.setOnKeyTyped(this::updateDescription);
	}
	private void updateDescription(KeyEvent evt) {
		KeyCode ch = evt.getCode();
		if(ch.equals(KeyCode.ENTER)|| evt.getCharacter().getBytes()[0] == '\n' || evt.getCharacter().getBytes()[0] == '\r') {
			String newDescr = descrWrite.getText();
			myDescr.setText(newDescr);
			user.setDescription(newDescr);
			descrWrite.clear();
			descrWrite.setVisible(false);
			try {
				myController.updateDescr(user.getId(),newDescr);
			} catch (DBException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Update failed");
	    		alert.setHeaderText(HEADER_MSG);
	    		alert.setContentText("we couldn't update your information, try again");
	    		URL url = null;
		   		 try {
		   		 url = new File(ALERTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 url = new File(PROJECTCSS).toURI().toURL();
		   		 alert.getDialogPane().getStylesheets().add(url.toString());
		   		 
		   		 
		   			url = new File(WARN_IMG).toURI().toURL();
		   		} catch (MalformedURLException e1) {
		   			// TODO Auto-generated catch block
		   			e1.printStackTrace();
		   		}
	   		 	Image image = new Image(url.toString());
	   		 	ImageView imageView = new ImageView(image);
	   		 	alert.setGraphic(imageView);
	   		 	alert.showAndWait();
			}
			
		}
	}
	@FXML
	private void choosePhoto(){
		choosePhoto.setVisible(true);
	}
	@FXML
	private void choosePhotoDisappear() {
		choosePhoto.setVisible(false);
	}
	@FXML
	private void myDescrEdit() {
		myDescrEdit.setVisible(true);
	}
	@FXML
	private void myDescrEditDisappear() {
		myDescrEdit.setVisible(false);
	}
	@FXML
	private void sharedList(){
		show.setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Check out this travels");
		show.getItems().clear();
		try {
			List<Bean> sh = myController.getShared(user.getId());
			if(sh!=null && !sh.isEmpty()) {
				ObservableList<Bean> fav = FXCollections.observableList(sh);
				show.setItems(fav);
				show.setCellFactory(list->new shCell());
			}
		} catch (DBException e) {
			errorMsg.setVisible(true);
		}
	}
	class shCell extends ListCell<Bean>{
		@Override
        public void updateItem(Bean item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
            	ShareBean myItem = (ShareBean)item;
            	MiniTravelBean myTravel=null;
            	UserBean myUser=null;
				try {
					myTravel = myController.getTravel(myItem.getTravelShared());
					myUser = myController.getUser(myItem.getCreator());
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HBox hBox = new HBox();
            	VBox vBox = new VBox();
            	Label title = new Label(myTravel.getNameTravel());
            	title.getStyleClass().add("text1");
            	Label creator = new Label(myUser.getName()+" "+myUser.getSurname());
            	creator.getStyleClass().add("text2");
            	Pane contactPic = new Pane();
				Background bg;
				try {
					Image photo = myTravel.getPathImage();
					
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
				}catch(NullPointerException | IllegalArgumentException e) {
					URL url=null;
					try {
						url = new File("src/main/resources/ProfilePageImages/travelers.png").toURI().toURL();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Image photo = new Image(url.toString());
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
				}
					contactPic.setBackground(bg);
					contactPic.setPrefHeight(mainAnchor.getPrefHeight()*50/625);
					contactPic.setPrefWidth(mainAnchor.getPrefWidth()*50/1280);
					contactPic.getStyleClass().add("profile-pic");
            	vBox.setOnMouseClicked(e->{
            		moveToView(myItem.getTravelShared());
            	});
            	vBox.getChildren().add(title);
            	vBox.getChildren().add(creator);
            	hBox.getChildren().add(contactPic);
            	hBox.getChildren().add(vBox);
            	setGraphic(hBox);
            }
            else
            	setGraphic(null);
		}
	}
	@FXML
	private void favouriteList(){
		show.setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your favourite travels");
		show.getItems().clear();
		if(user.getFav()!=null && !user.getFav().isEmpty()) {
			ObservableList<Bean> fav;
			try {
				List<Bean> l =myController.getFav(user.getFav());
				fav = FXCollections.observableList(l);
				show.setItems(fav);
				show.setCellFactory(list-> new favCell());
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
		}
	}
	@FXML
	private void followerList() {
		show.setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your followers");
		show.getItems().clear();
		if(user.getFollower()!= null && !user.getFollower().isEmpty()) {
			ObservableList<Bean> fav;
			try {
				fav = FXCollections.observableList(myController.getFollow(user.getFollower()));
				show.setCellFactory(list-> new followCell());
				show.setItems(fav);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	@FXML
	private void followingList() {
		show.setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your interesting people");
		show.getItems().clear();
		if(user.getFollowing()!=null && !user.getFollowing().isEmpty()) {
			ObservableList<Bean> fav;
			try {
				fav = FXCollections.observableList(myController.getFollow(user.getFollowing()));
				show.setCellFactory(list-> new followCell());
				show.setItems(fav);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	class followCell extends ListCell<Bean>{
		@Override
		public void updateItem(Bean item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
            	UserBean myItem = (UserBean)item;
            	HBox hBox = new HBox();
            	Pane contactPic = new Pane();
				Background bg;
				try {
					Image photo = myItem.getPhoto();
					
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
				}catch(NullPointerException | IllegalArgumentException e) {
					URL url=null;
					try {
						url = new File("src/main/resources/ProfilePageImages/travelers.png").toURI().toURL();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Image photo = new Image(url.toString());
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
				}
					contactPic.setBackground(bg);
					contactPic.setPrefHeight(mainAnchor.getPrefHeight()*50/625);
					contactPic.setPrefWidth(mainAnchor.getPrefWidth()*50/1280);
					contactPic.getStyleClass().add("profile-pic");
					Label name = new Label(myItem.getName()+" "+myItem.getSurname());
					name.getStyleClass().add("text1");
					hBox.getChildren().add(contactPic);
					hBox.getChildren().add(name);
					hBox.setOnMouseClicked(e1->{
						MenuBar.getInstance().setIdUser(myItem.getId());
						FXMLLoader loader =new FXMLLoader();
						try {
							URL url1 = new File("src/main/java/travelbook/view/ProfileUserViewOther.fxml").toURI().toURL();
						loader.setLocation(url1);
						AnchorPane internalPane=(AnchorPane)loader.load();
						mainPane.setCenter(internalPane);
						ProfileOtherController controller=loader.getController();
						controller.setMainPane(mainPane, 4, 0);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					});
					setGraphic(hBox);
				
            }
		}
	}
	
	class favCell extends ListCell<Bean>{
		@Override
		public void updateItem(Bean item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
            	MiniTravelBean myItem = (MiniTravelBean)item;
            	HBox hBox = new HBox();
            	Pane contactPic = new Pane();
				Background bg;
				try {
					Image photo = myItem.getPathImage();
					
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
					}catch(NullPointerException | IllegalArgumentException e) {
						BackgroundFill bgfill = new BackgroundFill(Paint.valueOf("rgb(255, 162, 134)"), null, null);
						bg = new Background(bgfill);
					}
					
					contactPic.setBackground(bg);
					contactPic.setPrefHeight(mainAnchor.getPrefHeight()*50/625);
					contactPic.setPrefWidth(mainAnchor.getPrefWidth()*50/1280);
					contactPic.getStyleClass().add("profile-pic");
					Label name = new Label(myItem.getNameTravel());
					name.getStyleClass().add("text1");
					hBox.getChildren().add(contactPic);
					hBox.getChildren().add(name);
					hBox.setOnMouseClicked(e1->
						moveToView(myItem.getId()));
					setGraphic(hBox);
				
            }
		}
	}
	@FXML
	private void back() {
		show.setVisible(false);
		listTitle.setVisible(false);
		errorMsg.setVisible(false);
	}
	@FXML
	private void logOut() {
		MenuBar.getInstance().initialize();
		FXMLLoader loader=new FXMLLoader();
		try {
			URL url = new File("src/main/java/travelbook/view/LoginView.fxml").toURI().toURL();
			loader.setLocation(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LoginViewController controller = new LoginViewController();
		try {
			AnchorPane loginPane=(AnchorPane)loader.load();
			mainPane.setCenter(loginPane);
			controller=loader.getController();
			controller.setMain(mainPane);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
    private void chatHandler(){
    	try {
    	MenuBar.getInstance().moveToChat(mainPane);
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
}
