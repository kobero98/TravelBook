package main.java.travelbook.view;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.exception.DBException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.MyProfileController;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.ShareBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.SetImage;

public class SharedCell extends Cell {
	private  static final String TEXT_CSS="text1";
	private MyProfileController myController=new MyProfileController();
	public SharedCell(AnchorPane anchor, BorderPane pane) {
		super(anchor,pane);
		super.scroll.getStyleClass().add("lists");
	}
	public void setItems(List<Object> obj) {
		for(Object item: obj) {
		ShareBean myItem = (ShareBean)item;
    	MiniTravelBean myTravel=null;
    	UserBean myUser=null;
		try {
			myTravel = myController.getTravel(myItem.getTravelShared());
			myUser = myController.getUser(myItem.getWhoShare());
		
		HBox hBox = new HBox();
    	VBox vBox = new VBox();
    	Label title=null;
    	title = new Label(myTravel.getNameTravel());
    	title.getStyleClass().add(TEXT_CSS);
    	Label creator =null;
    	if(myUser!=null)  {
    		creator = new Label(myUser.getName()+" "+myUser.getSurname());
    		creator.getStyleClass().add("text2");
    		}
    	Pane contactPic = new Pane();
		new SetImage(contactPic, myTravel.getPathImage(), false);
		contactPic.setPrefHeight(super.getAnchor().getPrefHeight()*50/625);
		contactPic.setPrefWidth(super.getAnchor().getPrefWidth()*50/1280);
		
    	hBox.setOnMouseClicked(e->{
    	try {
    		MenuBar.getInstance().setIdTravel(myItem.getTravelShared());
			MenuBar.getInstance().moveToView(super.getBorder(),2);
		} catch (MissingPageException e1) {
			e1.exit();
		}});
    	vBox.getChildren().add(title);
    	vBox.getChildren().add(creator);
    	hBox.getChildren().add(contactPic);
    	hBox.getChildren().add(vBox);
		super.getBox().getChildren().add(hBox);
		} catch (DBException e) {
			new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
		}catch(NullPointerException e1) {
			new TriggerAlert().triggerAlertCreate("Error while loading", "warn");
		}
		}
	}
}
