package main.java.travelbook.view.cell;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import main.java.exception.DBException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.MyProfileController;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.util.SetImage;
import main.java.travelbook.view.MenuBar;
import main.java.travelbook.view.ProfileOtherController;
import javafx.scene.layout.Region;
public class TravelCell extends Cell {
	private boolean isEditable=true;
	private double width=530;
	private int other=1;
	private static final String CSS = "fav-selected";
	public TravelCell(AnchorPane anchor,BorderPane pane) {
		super(anchor,pane);
		super.getBox().setSpacing(10);
	}
	public TravelCell(AnchorPane anchor,BorderPane pane, boolean edit, double width,int other) {
		this(anchor,pane);
		isEditable=edit;
		this.width=width;
		this.other=other;
	}
	@Override
	public void setItems(List<Object> obj) {
		super.getItems().clear();
		super.getBox().getChildren().clear();
		for(Object item: obj) {
		MiniTravelBean item1 = (MiniTravelBean)item;
    	HBox travel = new HBox();
    	travel.setPrefWidth(super.getAnchor().getPrefWidth()*width/1280);
		travel.setPrefHeight(super.getAnchor().getPrefHeight()*180/625);
    	travel.setMaxWidth(Region.USE_PREF_SIZE);
    	travel.setMinWidth(Region.USE_PREF_SIZE);
    	
    	CornerRadii rad = new CornerRadii(25);
    	Insets in = new Insets(0);
    	BackgroundFill bgcc = new BackgroundFill(Paint.valueOf("rgb(250, 250, 250)"), rad, in);
    	
    	Background mybg = new Background(bgcc);
    	travel.setBackground(mybg);
    	Pane travelPic = new Pane();
    	travelPic.setPrefHeight(super.getAnchor().getPrefHeight()*180/625);
    	travelPic.setPrefWidth(super.getAnchor().getPrefWidth()*265/1280);
    	new SetImage(travelPic, item1.getPathImage(), true);
    	VBox vBox = new VBox();
    	HBox hBox = new HBox();
    	vBox.setPrefWidth(super.getAnchor().getPrefWidth()*265/1280);
    	vBox.setMaxWidth(Region.USE_PREF_SIZE);
    	vBox.setSpacing(super.getAnchor().getPrefHeight()*(180.0/15)/625);
    	Label name = new Label(item1.getNameTravel());
    	Text descr = new Text(item1.getDescriptionTravel());
    	descr.setWrappingWidth(super.getAnchor().getPrefWidth()*265/1280);
    	hBox.setAlignment(Pos.BOTTOM_RIGHT);
    	Button edit;
    	if(other==3)
    		edit=this.makeOther(item1);
    	else
    		edit=this.make(item1,travel,hBox);

    	travel.setOnMouseClicked(e->{
			try {
				MenuBar.getInstance().setIdTravel(item1.getId());
				if(other==3)
					MenuBar.getInstance().moveToView(super.getBorder(), 3);
				else if(other==2)
					MenuBar.getInstance().moveToView(super.getBorder(), 4);
				else
					MenuBar.getInstance().moveToView(super.getBorder(),2);
			} catch (MissingPageException e1) {
				e1.exit();
			}
		});

    	hBox.getChildren().add(edit);
    	vBox.getChildren().add(name);
    	vBox.getChildren().add(descr);
    	vBox.getChildren().add(hBox);
    	
    	travel.getChildren().add(travelPic);
    	travel.getChildren().add(vBox);
    	super.getAnchor().heightProperty().addListener((observable, oldValue, newValue)->{            		
    		travel.setPrefHeight(super.getAnchor().getPrefHeight()*180/625);
    		travelPic.setPrefHeight(super.getAnchor().getPrefHeight()*180/625);
        	edit.setPrefHeight(super.getAnchor().getPrefHeight()*35/625);
    	});
    	super.getAnchor().widthProperty().addListener((observable, oldValue, newValue)->{
    		travel.setPrefWidth(super.getAnchor().getPrefWidth()*width/1280);
    		travelPic.setPrefWidth(super.getAnchor().getPrefWidth()*265/1280);
    		edit.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280);
    		descr.setWrappingWidth(super.getAnchor().getPrefWidth()*265/1280);
    	});
    	super.getBox().getChildren().add(travel);
		}
	}
	private Button makeOther(MiniTravelBean item) {
		Button fav = new Button();
    	fav.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280);
    	fav.setPrefHeight(super.getAnchor().getPrefHeight()*35/625);
    	fav.getStyleClass().clear();
    	fav.getStyleClass().add("favourite");
    	if(MenuBar.getInstance().getLoggedUser().getFav()!=null &&
    			MenuBar.getInstance().getLoggedUser().getFav().contains(item.getId()))
    				fav.getStyleClass().add(CSS);
    	ProfileOtherController c=new ProfileOtherController();
    	fav.setOnMouseClicked(e->c.addToFav(item,fav));
    	return fav;
	}
	private Button make(MiniTravelBean item,HBox h,HBox parent) {
    	Button edit = new Button();
    	edit.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280);
    	edit.setPrefHeight(super.getAnchor().getPrefHeight()*35/625);
    	edit.setVisible(this.isEditable&& !(item.isShared()));
    	edit.getStyleClass().clear();
    	edit.getStyleClass().add("edit");
    	edit.setOnMouseClicked(e->{
    		try {
    			MenuBar.getInstance().setIdTravel(item.getId());
    			MenuBar.getInstance().moveToAddTravel(super.getBorder());
    		}catch(MissingPageException exc) {
    			exc.exit();
    		}
    	});
    	Button removeTravel=new Button();
    	removeTravel.getStyleClass().clear();
    	removeTravel.getStyleClass().add("del");
    	removeTravel.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280);
    	removeTravel.setPrefHeight(super.getAnchor().getPrefHeight()*35/625);
    	super.getAnchor().widthProperty().addListener((observable,oldValue,newValue)->
    		removeTravel.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280)
    	);
    	super.getAnchor().heightProperty().addListener((observable,oldValue,newValue)->
    		removeTravel.setPrefHeight(super.getAnchor().getPrefHeight()*35/625)
    	);
    	removeTravel.setVisible(this.isEditable);
    	removeTravel.setMaxWidth(Region.USE_PREF_SIZE);
    	removeTravel.setMinWidth(Region.USE_PREF_SIZE);
    	removeTravel.setMinHeight(Region.USE_PREF_SIZE);
    	removeTravel.setMaxHeight(Region.USE_PREF_SIZE);
    	removeTravel.setOnMouseClicked(e->{
    		MyProfileController myController=new MyProfileController();
    		try {
    			myController.deleteTravel(item.getId());
    			}catch(DBException ex) {
    				new TriggerAlert().triggerAlertCreate("Unable to remove your travel","err").showAndWait();
    			}
    		MenuBar.getInstance().getLoggedUser().getTravel().remove(item.getId());
    		List<Integer> fav=MenuBar.getInstance().getLoggedUser().getFav();
    		if(fav!=null && fav.contains(item.getId()))
    			fav.remove(item.getId());
    		this.getBox().getChildren().remove(h);
    	});
    	parent.getChildren().add(removeTravel);
    	return edit;
	}
}
