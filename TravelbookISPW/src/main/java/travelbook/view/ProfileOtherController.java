package main.java.travelbook.view;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.java.exception.DBException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.ControllerProfileOther;
import main.java.travelbook.controller.TravelController;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.SetImage;
import main.java.travelbook.view.cell.Cell;
import main.java.travelbook.view.cell.CellFactory;
import main.java.travelbook.view.cell.CellType;
import	main.java.travelbook.model.bean.Bean;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
public class ProfileOtherController {
	private BorderPane mainPane;
	private int goBack;
	private int travelId;
	private UserBean user;
	@FXML
	private AnchorPane mainAnchor;
	private Cell travels;
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
	@FXML
	private Button follow;
	@FXML
	private Label errorMsg;
	private ControllerProfileOther myController = new ControllerProfileOther();
	private static final String CSS = "fav-selected";
	
	public void initialize() {
		travels=CellFactory.getInstance().create(CellType.OTHERTRAVEL, this.mainAnchor, this.mainPane);
		try {
			
			this.user = myController.getUser(MenuBar.getInstance().getUserId());
		} catch (DBException e1) {
			new TriggerAlert().triggerAlertCreate(e1.getMessage(), "err").showAndWait();
		}
		new Thread(()->{
			List<Bean> data;
			try {
				List<Bean>l= myController.getTravel(user.getTravel());
				if(l!=null)
					{
					int i=0;
					while(i<l.size())
					{
						MiniTravelBean t=(MiniTravelBean) l.get(i);
						if(!t.isShared()) {l.remove(i);}
						else{i++;}
					}
					}
				data = l;
				List<Object> obj=new ArrayList<>(data);
				Platform.runLater(()->{
					travels.setItems(obj);
					travels.getScroll().setVisible(true);
				});
			} catch (DBException e) {
				new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
			}
			
		}).start();
		if(MenuBar.getInstance().getLoggedUser().getFollowing()!=null &&
    			MenuBar.getInstance().getLoggedUser().getFollowing().contains(user.getId()))
    				follow.getStyleClass().add(CSS);
		new SetImage(profilePhoto, user.getPhoto(), false);
		userName.setText(user.getName()+ " "+ user.getSurname());
		myDescr.setText(user.getDescription());
		followerButton.setText("Followers: "+user.getNFollower());
		followingButton.setText("Following: "+user.getNFollowing());
		placeVisited.setText(user.getName() + " has visited " + user.getnPlace() +" places");
		favText.setText(user.getName()+"'s favourite travels");
	}

	
	public void addToFav(MiniTravelBean item, Button fav){
		try {
			List<Integer> f= MenuBar.getInstance().getLoggedUser().getFav();
			UserBean user1= new UserBean(MenuBar.getInstance().getLoggedUser().getId());
			List<Integer> s=new ArrayList<>();
			s.add(item.getId());
			user1.setFav(s);
			if(fav.getStyleClass().contains(CSS)) {
			fav.getStyleClass().remove(CSS);
			new TravelController().updateFav(user1);
			f.remove(item.getId());
			}
			else {
				fav.getStyleClass().add(CSS);
				if(f==null)  f=new ArrayList<>();
				f.add(item.getId());
				MenuBar.getInstance().getLoggedUser().setFav(f);
				myController.updateFav(MenuBar.getInstance().getLoggedUser());
			}
		} catch (DBException exc) {
			
			new TriggerAlert().triggerAlertCreate(exc.getMessage(), "warn").showAndWait();
		}
	}
	public void setMainPane(BorderPane main, int provenience, int travelId) {
		this.mainPane=main;
		this.goBack=provenience;
		this.travels.setBorder(main);
		this.travelId = travelId;
this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->					
			this.mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight()));
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->
			this.mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth())); 
		
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followerButton.setLayoutY(mainAnchor.getHeight()*410/625);
			
			favButton.setPrefHeight(mainAnchor.getHeight()*50/625);
			favButton.setLayoutY(mainAnchor.getHeight()*513/625);
			
			map.setFitHeight(mainAnchor.getHeight()*160/625);
			map.setLayoutY(mainAnchor.getHeight()*434/625);
			followingButton.setPrefHeight(mainAnchor.getHeight()*57/625);
			followingButton.setLayoutY(mainAnchor.getHeight()*410/625);
			placeVisited.setPrefHeight(mainAnchor.getHeight()*160/625);
			placeVisited.setLayoutY(mainAnchor.getHeight()*419/625);
			profilePhoto.setPrefHeight(mainAnchor.getHeight()*200/625);
			profilePhoto.setLayoutY(mainAnchor.getHeight()*90/625);
			favIcon.setFitHeight(mainAnchor.getHeight()*27.5/625);
			favText.setLayoutY(mainAnchor.getHeight()*534/625);
			userName.setLayoutY(mainAnchor.getHeight()*80/625);
			myDescr.setLayoutY(mainAnchor.getHeight()*200/625);
			show.setPrefHeight(mainAnchor.getHeight()*575/625);
			show.setLayoutY(mainAnchor.getHeight()*50/625);
			listTitle.setPrefHeight(mainAnchor.getHeight()*50/625);
			backButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			backButton.setLayoutY(mainAnchor.getHeight()*20/625);
			showBackButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			listText.setPrefHeight(mainAnchor.getHeight()*30/625);
			travels.getScroll().setPrefHeight(mainAnchor.getHeight()*591/625);
			travels.getScroll().setLayoutY(mainAnchor.getHeight()*14/625);
			follow.setPrefHeight(mainAnchor.getHeight()*30/625);
		});	
		
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			followerButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followerButton.setLayoutX(mainAnchor.getWidth()*29/1280);
			favButton.setPrefWidth(mainAnchor.getWidth()*50/1280);
			favButton.setLayoutX(mainAnchor.getWidth()*41/1280);
			
			map.setFitWidth(mainAnchor.getWidth()*285/1280);
			map.setLayoutX(mainAnchor.getWidth()*307/1280);
			followingButton.setPrefWidth(mainAnchor.getWidth()*123/1280);
			followingButton.setLayoutX(mainAnchor.getWidth()*158/1280);
			placeVisited.setPrefWidth(mainAnchor.getWidth()*270/1280);
			placeVisited.setLayoutX(mainAnchor.getWidth()*317/1280);
			profilePhoto.setPrefWidth(mainAnchor.getWidth()*200/1280);
			profilePhoto.setLayoutX(mainAnchor.getWidth()*65/1280);
			userName.setLayoutX(mainAnchor.getWidth()*275/1280);
			userName.setWrappingWidth(mainAnchor.getWidth()*326/1280);
			favIcon.setFitWidth(mainAnchor.getWidth()*30/1280);
			favText.setLayoutX(mainAnchor.getWidth()*95/1280);
			favText.setWrappingWidth(mainAnchor.getWidth()*152/1280);
			myDescr.setLayoutX(mainAnchor.getWidth()*269/1280);
			myDescr.setWrappingWidth(mainAnchor.getWidth()*326/1280);
			show.setPrefWidth(mainAnchor.getWidth()*297/1280);
			listTitle.setPrefWidth(mainAnchor.getWidth()*297/1280);
			backButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			backButton.setLayoutX(mainAnchor.getWidth()*20/1280);
			showBackButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			listText.setPrefWidth(mainAnchor.getWidth()*200/1280);
			travels.getScroll().setPrefWidth(mainAnchor.getWidth()*606/1280);
			travels.getScroll().setLayoutX(mainAnchor.getWidth()*631/1280);
			follow.setPrefWidth(mainAnchor.getWidth()*30/1280);
		});	
	this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
	this.mainAnchor.setPrefWidth(mainPane.getWidth());
	}

	@FXML
	private void favouriteList(){
		show.setItems(null);
		show.setVisible(true);
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText(user.getName()+"'s favourite travels");
		if(user.getFav()!=null && !user.getFav().isEmpty()) {
			ObservableList<String> fav;
			try {
				fav = FXCollections.observableList(myController.getFavS(user.getFav()));
				show.setItems(fav);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
		}
	}
	@FXML
	private void followerList() {
		show.setItems(null);
		show.setVisible(true);
		
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText(user.getName()+"'s followers");
		if(user.getFollower()!= null && !user.getFollower().isEmpty()) {
			ObservableList<String> fav;
			try {
				fav = FXCollections.observableList(myController.getFollowS(user.getFollower()));
				show.setItems(fav);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	@FXML
	private void followingList() {
		show.setItems(null);
		show.setVisible(true);
		
		errorMsg.setVisible(false);
		listTitle.setVisible(true);
		listText.setText(user.getName() +"'s interesting people");
		if(user.getFollowing()!=null && !user.getFollowing().isEmpty()) {
			ObservableList<String> fav;
			try {
				fav = FXCollections.observableList(myController.getFollowS(user.getFollowing()));
				show.setItems(fav);
			} catch (DBException e) {
				errorMsg.setVisible(true);
			}
			
		}
	}
	@FXML
	private void follow() {
		UserBean me= MenuBar.getInstance().getLoggedUser();
		if(follow.getStyleClass().contains(CSS)) {
			follow.getStyleClass().remove(CSS);
			
			user.getFollower().remove((Integer)me.getId());
			me.getFollowing().remove((Integer)user.getId());
		}
		else {
			follow.getStyleClass().add(CSS);
			if(me.getFollowing()==null) me.setFollowing(new ArrayList<>());
			me.getFollowing().add(user.getId());
			if(user.getFollower()==null) user.setFollower(new ArrayList<>());
			user.getFollower().add(me.getId());
		}
		try {
			myController.updateFollow(me.getId(),user.getId());
		} catch (DBException e) {
			
			new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
		}
		
	}
	@FXML
	private void showBack() {
		show.setVisible(false);
		listTitle.setVisible(false);
		errorMsg.setVisible(false);
	}
	@FXML
	private void back() {
		MenuBar.getInstance().setIdTravel(travelId);
		switch (goBack){
		case 11:	
			try {
				MenuBar.getInstance().moveToView(mainPane, 1);
			} catch (MissingPageException e) {
				e.exit();
			}
			break;
		case 14:
			try {
				MenuBar.getInstance().moveToView(mainPane, 4);
			} catch (MissingPageException e) {
				e.exit();
			}
			break;
		case 2:
			try {
				MenuBar.getInstance().moveToChat(mainPane);
			}catch(MissingPageException e) {
	    		e.exit();
	    	}
			break;
		case 3:
			try {
				MenuBar.getInstance().moveToProfile(mainPane);
			}catch(MissingPageException e) {
	    		e.exit();
	    	}
			break;
		default:
			try {
				MenuBar.getInstance().moveToExplore(mainPane);
			}catch(MissingPageException e) {
	    		e.exit();
	    	}
		}
	}

}
