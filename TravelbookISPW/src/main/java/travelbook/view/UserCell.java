package main.java.travelbook.view;


import java.util.List;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.java.exception.MissingPageException;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.SetImage;

public class UserCell extends Cell{
	
	private  static final String TEXT_CSS="text1";
	public UserCell(AnchorPane anchor,BorderPane pane) {
		super(anchor,pane);
		super.scroll.getStyleClass().add("lists");
	}
	@Override
	public void setItems(List<Object> obj) {
		super.getBox().getChildren().clear();
		if(obj==null)
			return;
		super.setList(obj);
		for(Object item: obj) {
		UserBean myItem = (UserBean)item;
    	HBox hBox = new HBox();
    	Pane contactPic = new Pane();
		new SetImage(contactPic, myItem.getPhoto(),false);
			contactPic.setPrefHeight(super.getAnchor().getPrefHeight()*50/625);
			contactPic.setPrefWidth(super.getAnchor().getPrefWidth()*50/1280);
			Label name = new Label(myItem.getName()+" "+myItem.getSurname());
			name.getStyleClass().add(TEXT_CSS);
			hBox.getChildren().add(contactPic);
			hBox.getChildren().add(name);
			hBox.setOnMouseClicked(e1->{
				MenuBar.getInstance().setIdUser(myItem.getId());
				try {
					MenuBar.getInstance().moveToProfileOther(super.getBorder(), 3, 0);
				} catch (MissingPageException e) {
					e.exit();
				}
			});
			super.getBox().getChildren().add(hBox);
		}
	}

}
