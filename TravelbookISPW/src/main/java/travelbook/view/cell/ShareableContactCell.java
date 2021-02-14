package main.java.travelbook.view.cell;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.SetImage;

public class ShareableContactCell extends Cell {
	private List<UserBean> toShare=new ArrayList<>();
	private AnchorPane main;
	public ShareableContactCell(AnchorPane anchor,BorderPane pane) {
		super(anchor,pane);
		this.getScroll().getStyleClass().add("list-view");
	}
	@Override
	public void setItems(List<Object> obj) {
		super.getBox().getChildren().clear();
		for(Object o:obj) {
		UserBean item=(UserBean)o;
		HBox hBox = new HBox();
		hBox.setSpacing(this.main.getPrefWidth()*5/1280);
		hBox.setAlignment(Pos.CENTER);
		hBox.getStyleClass().add("h-box");
		Text contact = new Text(item.getName()+" "+item.getSurname());
		contact.getStyleClass().add("text");
		contact.setWrappingWidth(this.main.getPrefWidth()*100/1280);
		Pane contactPic = new Pane();
		new SetImage(contactPic, item.getPhoto(), false);
		contactPic.setPrefHeight(this.main.getPrefHeight()*50/625);
		contactPic.setPrefWidth(this.main.getPrefWidth()*50/1280);
		CheckBox check = new CheckBox();
		check.selectedProperty().addListener((observable, oldValue, newValue)->{
			if(check.isSelected()) 
				toShare.add(item);
			else
				if(toShare.contains(item)) 
					toShare.remove(item);
		});
		this.main.widthProperty().addListener((observable,oldValue,newValue)->{
			contactPic.setPrefWidth(this.main.getPrefWidth()*50/1280);
			hBox.setSpacing(this.main.getPrefWidth()*5/1280);
			contact.setWrappingWidth(this.main.getPrefWidth()*100/1280);
		});
		this.main.heightProperty().addListener((observable,oldValue,newValue)->
			contactPic.setPrefHeight(this.main.getPrefHeight()*50/625)
		);
		hBox.getChildren().add(contactPic);
		hBox.getChildren().add(contact);
		hBox.getChildren().add(check);
		super.getBox().getChildren().add(hBox);
		}
	}
	public void setSecond(AnchorPane a) {
		this.main=a;
	}
	public void setToShare(List<UserBean> l) {
		this.toShare=l;
	}
	public List<UserBean> getToShare(){
		return this.toShare;
	}
}
