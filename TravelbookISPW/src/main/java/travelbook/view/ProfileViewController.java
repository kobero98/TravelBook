package main.java.travelbook.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.java.exception.DBException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.MyProfileController;
import main.java.travelbook.model.bean.Bean;
import main.java.travelbook.model.bean.UserBean;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.travelbook.util.Notification;
import main.java.travelbook.util.Observable;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.SetImage;
public class ProfileViewController implements Observer{
	private BorderPane mainPane;
	private Object[] array1=new Object[15];
	private Button button;
	private Notification dot;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private ButtonBar menuBar;

	private Cell travels;
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
	private Cell show;
	private Cell showTravel;
	private Cell showShared;
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


	UserBean user=MenuBar.getInstance().getLoggedUser();
	MyProfileController myController = new MyProfileController();
	public void initialize() {
		MenuBar.getInstance().setNewThread();
		MenuBar.getInstance().addObserver(this);
		if(MenuBar.getInstance().getNotified())
			this.update(MenuBar.getInstance());
		travels=CellFactory.getInstance().create(CellType.TRAVEL, mainAnchor, mainPane);
		new Thread(()->{
			List<Bean> data;
			try {
				if(user.getTravel()!=null && !user.getTravel().isEmpty()) {
					data = myController.getTravel(user.getTravel());
					
					Platform.runLater(()->{
						List<Object> obj=new ArrayList<>(data);
						travels.setItems(obj); 
						travels.getScroll().setVisible(true);
					});
				}
			} catch (DBException e) {
				new TriggerAlert().triggerAlertCreate("we couldn't reach your travels, try again", "warn").showAndWait();
			}
			
			
		}).start();

		
		new SetImage(profilePhoto, user.getPhoto(), false);
		
		userName.setText(user.getName()+" "+user.getSurname());
		
		myDescr.setText(user.getDescription());
		
		followerButton.setText("Followers: "+user.getNFollower());
		followingButton.setText("Following: "+user.getNFollowing());
		placeVisited.setText("You have visited " + user.getnPlace()+" places");
	}
	
	

	public void setMainPane(BorderPane main) {

		this.mainPane=main;
		this.travels.setBorder(main);
		this.show=CellFactory.getInstance().create(CellType.FOLLOWER, this.mainAnchor, this.mainPane);
		this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->					
			this.mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight()));
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->
			this.mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth())); 
		this.showShared=CellFactory.getInstance().create(CellType.SHARE, this.mainAnchor, this.mainPane);
		this.showTravel=CellFactory.getInstance().create(CellType.FAVORITE, this.mainAnchor, this.mainPane);
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followerButton.setLayoutY(mainAnchor.getHeight()*410/625);
			showTravel.getScroll().setPrefHeight(mainAnchor.getHeight()*575/625);
			showTravel.getScroll().setLayoutY(mainAnchor.getHeight()*50/625);
			followingButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followingButton.setLayoutY(mainAnchor.getHeight()*410/625);
			favButton.setPrefHeight(mainAnchor.getHeight()*50/625);
			favButton.setLayoutY(mainAnchor.getHeight()*499/625);
			showShared.getScroll().setPrefHeight(mainAnchor.getHeight()*575/625);
			showShared.getScroll().setLayoutY(mainAnchor.getHeight()*50/625);
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
			userName.setLayoutY(mainAnchor.getHeight()*75/625);
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
			show.getScroll().setPrefHeight(mainAnchor.getHeight()*575/625);
			show.getScroll().setLayoutY(mainAnchor.getHeight()*50/625);
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
			travels.getScroll().setPrefHeight(mainAnchor.getHeight()*591/625);
			travels.getScroll().setLayoutY(mainAnchor.getHeight()*14/625);
		});	
		
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followerButton.setLayoutX(mainAnchor.getWidth()*29/1280);
			showShared.getScroll().setPrefWidth(mainAnchor.getWidth()*297/1280);
			followingButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followingButton.setLayoutX(mainAnchor.getWidth()*158/1280);
			favButton.setPrefWidth(mainAnchor.getWidth()*50/1280);
			favButton.setLayoutX(mainAnchor.getWidth()*41/1280);
			showTravel.getScroll().setPrefWidth(mainAnchor.getWidth()*297/1280);
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
			userName.setLayoutX(mainAnchor.getWidth()*240/1280);
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
			show.getScroll().setPrefWidth(mainAnchor.getWidth()*297/1280);
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
			travels.getScroll().setPrefWidth(mainAnchor.getWidth()*606/1280);
			travels.getScroll().setLayoutX(mainAnchor.getWidth()*631/1280);
		});	
	this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
	this.mainAnchor.setPrefWidth(mainPane.getWidth());
	}
	@Override
	public void update(Observable bar) {
		this.update(bar,true);
	}
	@Override
	public void update(Observable bar, Object notify) {
		boolean value=(Boolean)notify;
		if(value) {
			Platform.runLater(()->
				dot=new Notification(mainAnchor,330));
		}
		else {
			if(dot!=null) dot.remove();
		}
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
				InputStream inputPhoto = new FileInputStream(selectedFile);
				myController.updatePhoto(user.getId(),inputPhoto);
				
			} catch (DBException e) {
				new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
			} catch (FileNotFoundException e) {
				new TriggerAlert().triggerAlertCreate("seems we couldn't find yout photo...", "warn");
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
				new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
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
		showShared.getScroll().setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Check out this travels");
		showShared.getItems().clear();
		try {
			List<Bean> sh = myController.getShared(user.getId());
			if(sh!=null && !sh.isEmpty()) {
				List<Object> fav = new ArrayList<>(sh);
				showShared.setItems(fav);
				
			}
		} catch (DBException e) {
			errorMsg.setVisible(true);
		}
	}

	@FXML
	private void favouriteList(){
		showTravel.getScroll().setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your favourite travels");
		showTravel.getItems().clear();
		if(user.getFav()!=null && !user.getFav().isEmpty()) {
			try {
				List<Bean> l =myController.getTravel(user.getFav());
				List<Object> obj=new ArrayList<>(l);
				showTravel.setItems(obj);
				
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
		}
	}
	@FXML
	private void followerList() {
		show.getScroll().setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your followers");
		show.setItems(null);
		
		if(user.getFollower()!= null && !user.getFollower().isEmpty()) {
			try {
				List<Bean> users=myController.getFollow(user.getFollower());
				List<Object> fol=new ArrayList<>();
				for(Bean bean:users )
					fol.add(bean);
				show.setItems(fol);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	@FXML
	private void followingList() {
		
		show.getScroll().setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText("Your interesting people");
		show.setItems(null);
		if(user.getFollowing()!=null && !user.getFollowing().isEmpty()) {
			try {
				List<Bean> users=myController.getFollow(user.getFollowing());
				List<Object> fol=new ArrayList<>();
				for(Bean bean:users )
					fol.add(bean);
				show.setItems(fol);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	public void removeATrip(int id) {
		try {
		this.myController.deleteTravel(id);
		}catch(DBException e) {
			new TriggerAlert().triggerAlertCreate("Unable to remove your travel","err").showAndWait();
		}
	}
	@FXML
	private void back() {
		show.getScroll().setVisible(false);
		listTitle.setVisible(false);
		errorMsg.setVisible(false);
		showTravel.getScroll().setVisible(false);
		showShared.getScroll().setVisible(false);
	}
	@FXML
	private void logOut() {
		MenuBar.getInstance().initialize();
		try {
			MenuBar.getInstance().moveToLogin(mainPane);
		} catch (MissingPageException e) {
			e.exit();
		}
	}
	@FXML
    private void chatHandler(){
    	try {
    	MenuBar.getInstance().moveToChat(mainPane);
    	} catch (MissingPageException e) {
			e.exit();
		}
    }
    @FXML
    private void addHandler() {
    	try {
    		MenuBar.getInstance().moveToAdd(mainPane);
    	} catch (MissingPageException e) {
			e.exit();
		}
    }
    @FXML
    private void exploreHandler() {
    	try {
    		MenuBar.getInstance().moveToExplore(mainPane);
    	} catch (MissingPageException e) {
			e.exit();
		}
    }
}
