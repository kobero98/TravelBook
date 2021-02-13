package main.java.travelbook.view;
import javafx.scene.control.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.SetImage;
public class ChatContactView {
	private static final String HBOXSELECTED="h-box-selected";
	private static final String SELECTED="selected";
	private final ObjectProperty<MyItem> selectedItem=new SimpleObjectProperty<>();
	private String changed = "changed";
	private VBox box;
	private ScrollPane scroll;
	public ScrollPane getScroll() {
		return scroll;
	}
	public void setScroll(ScrollPane scroll) {
		this.scroll = scroll;
	}
	public List<MyItem> getItems() {
		return items;
	}
	public VBox getBox() {
		return box;
	}
	public void setBox(VBox box) {
		this.box = box;
	}
	private AnchorPane mainAnchor;
	private List<MyItem> items=new ArrayList<>();
	public ChatContactView() {
		this.box=new VBox();
		this.scroll=new ScrollPane();
	}
	public void setPane(AnchorPane anchor) {
		this.mainAnchor=anchor;
		this.mainAnchor.getChildren().add(this.scroll);
	}
	public ChatContactView(AnchorPane pane) {
		this.box=new VBox();
		this.scroll=new ScrollPane();
		this.scroll.setContent(box);
		this.mainAnchor=pane;
		pane.getChildren().add(scroll);
	}
	public class MyItem {
		private Circle circ;
		private HBox myBox=null;
		private ObjectProperty<String> specialIndicator;
		private UserBean contact;
        MyItem(UserBean name) {
            this.contact = name;
            this.specialIndicator =new SimpleObjectProperty<>();
            this.setSpecialIndicator("");
        }

        public UserBean getUser() {
            return this.contact;
        }

        public void setName(UserBean name) {
            this.contact = name;
        }

        public String getSpecialIndicator() {
            return specialIndicator.getValue();
        }

        public ObjectProperty<String> specialIndicatorProperty() {
            return specialIndicator;
        }

        public void setSpecialIndicator(String specialIndicator) {
            this.specialIndicator.set(specialIndicator);
        }
        public void setHBox(HBox h) {
        	this.myBox=h;
        }
        public HBox getHBox() {
        	return this.myBox;
        }
        public void setCircle(Circle c) {
        	this.circ=c;
        }
        public Circle getCircle() {
        	return this.circ;
        }
    }
	public void setItems(List<MyItem> items) {
		this.box.getChildren().clear();
		this.items=items;
		for(MyItem item: items) {
		item.specialIndicatorProperty().addListener(e->{
			
			if(item.getSpecialIndicator().equalsIgnoreCase("changed")) {
			
				Circle c=new Circle(5, Paint.valueOf("rgb(255, 162, 134)"));
				item.setCircle(c);
				item.getHBox().getChildren().add(c);
			}
			if(item.getSpecialIndicator().equalsIgnoreCase("")){
				
				item.getHBox().getStyleClass().remove(HBOXSELECTED);
			}
			if(item.getSpecialIndicator().equals(SELECTED)) {
			
				item.getHBox().getChildren().remove(item.getCircle());
				item.setCircle(null);
			}
		});
		HBox hBox = new HBox();
		hBox.setOnMouseClicked(e->{
			this.getSelectedItem().set(item);
			e.consume();
		});
		item.setHBox(hBox);
		hBox.setSpacing(mainAnchor.getPrefWidth()*10/1280);
		hBox.getStyleClass().add("h-box");
		hBox.setAlignment(Pos.CENTER);
		
		if(item.getSpecialIndicator().equalsIgnoreCase(SELECTED)) {
			hBox.getStyleClass().add(HBOXSELECTED);
		}
		
		Text contact = new Text(item.getUser().getName()+" "+item.getUser().getSurname());
		contact.getStyleClass().add("text");
		contact.setWrappingWidth(mainAnchor.getPrefWidth()*150/1280);
		Pane contactPic = new Pane();
		new SetImage(contactPic, item.getUser().getPhoto(), false);
		contactPic.setPrefHeight(mainAnchor.getPrefHeight()*70/625);
		contactPic.setPrefWidth(mainAnchor.getPrefWidth()*70/1280);
		mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			contactPic.setPrefWidth(mainAnchor.getPrefWidth()*70/1280);
			hBox.setSpacing(mainAnchor.getPrefWidth()*10/1280);
			contact.setWrappingWidth(mainAnchor.getPrefWidth()*170/1280);
		});
		mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->
			contactPic.setPrefHeight(mainAnchor.getPrefHeight()*70/625)
		);
		
		hBox.getChildren().add(contactPic);
		hBox.getChildren().add(contact);
		if(item.getSpecialIndicator().equalsIgnoreCase(changed)) {
			hBox.getChildren().add(new Circle(5, Paint.valueOf("rgb(255, 162, 134)")));
		}
		setGraphic(hBox);
		}
	}
	private void setGraphic(HBox hBox) {
		this.box.getChildren().add(hBox);
	}
	public ObjectProperty<MyItem> getSelectedItem() {
		return selectedItem;
	}
	public void refresh() {
		for(MyItem item: items) {
			if(item.getSpecialIndicator().equalsIgnoreCase(SELECTED)) {
				item.getHBox().getStyleClass().add(HBOXSELECTED);
			}
			else {
				if(item.getHBox().getStyleClass().contains(HBOXSELECTED))
					item.getHBox().getStyleClass().remove(HBOXSELECTED);
			}
		}
	}

}
