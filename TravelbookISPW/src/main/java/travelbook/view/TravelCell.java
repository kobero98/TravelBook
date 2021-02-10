package main.java.travelbook.view;

import java.util.List;

import exception.MissingPageException;
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
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.util.SetImage;
import javafx.scene.layout.Region;
public class TravelCell extends Cell {
	private boolean isEditable=true;
	private double width=530;
	private boolean other=false;
	private static final String CSS = "fav-selected";
	public TravelCell(AnchorPane anchor,BorderPane pane) {
		super(anchor,pane);
		super.getBox().setSpacing(10);
	}
	public TravelCell(AnchorPane anchor,BorderPane pane, boolean edit, double width,boolean other) {
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
    	if(other)
    		edit=this.makeOther(item1);
    	else
    		edit=this.make();

    	travel.setOnMouseClicked(e->{
			try {
				MenuBar.getInstance().setIdTravel(item1.getId());
				MenuBar.getInstance().moveToView(super.getBorder(),2);
			} catch (MissingPageException e1) {
				e1.exit();
			}
		});
    	edit.setOnMouseClicked(e->{
    		try {
    			MenuBar.getInstance().setIdTravel(item1.getId());
    			MenuBar.getInstance().moveToAddTravel(super.getBorder()); //aggiungere id viaggio dopo aver sistemato add
    		}catch(MissingPageException exc) {
    			exc.exit();
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
    	fav.getStyleClass().add("favourite");
    	if(MenuBar.getInstance().getLoggedUser().getFav()!=null &&
    			MenuBar.getInstance().getLoggedUser().getFav().contains(item.getId()))
    				fav.getStyleClass().add(CSS);
    	ProfileOtherController c=new ProfileOtherController();
    	fav.setOnMouseClicked(e->c.addToFav(item,fav));
    	return fav;
	}
	private Button make() {
    	Button edit = new Button();
    	edit.setPrefWidth(super.getAnchor().getPrefWidth()*35/1280);
    	edit.setPrefHeight(super.getAnchor().getPrefHeight()*35/625);
    	edit.setVisible(this.isEditable);
    	edit.getStyleClass().clear();
    	edit.getStyleClass().add("edit");
    	return edit;
	}
}
