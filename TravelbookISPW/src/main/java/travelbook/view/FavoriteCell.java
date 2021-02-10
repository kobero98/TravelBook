package main.java.travelbook.view;

import java.util.List;

import exception.MissingPageException;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.util.SetImage;

public class FavoriteCell extends Cell {
	private  static final String TEXT_CSS="text1";
	public FavoriteCell(AnchorPane anchor, BorderPane pane) {
		super(anchor,pane);
	}
	@Override
	public void setItems(List<Object> obj) {
		for(Object item: obj) {
     	MiniTravelBean myItem = (MiniTravelBean)item;
    	HBox hBox = new HBox();
    	Pane contactPic = new Pane();
		new SetImage(contactPic, myItem.getPathImage(), false);
			contactPic.setPrefHeight(super.getAnchor().getPrefHeight()*50/625);
			contactPic.setPrefWidth(this.getAnchor().getPrefWidth()*50/1280);
			contactPic.getStyleClass().add("profile-pic");
			Label name = new Label(myItem.getNameTravel());
			name.getStyleClass().add(TEXT_CSS);
			hBox.getChildren().add(contactPic);
			hBox.getChildren().add(name);
			hBox.setOnMouseClicked(e1->{
			try {
				MenuBar.getInstance().setIdTravel(myItem.getId());
				MenuBar.getInstance().moveToView(super.getBorder(),2);
			} catch (MissingPageException e) {
				e.exit();
			}
			});
			super.getBox().getChildren().add(hBox);
		}
	}
}
