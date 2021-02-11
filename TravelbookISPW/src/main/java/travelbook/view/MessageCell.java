package main.java.travelbook.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.travelbook.model.bean.MessageBean;

public class MessageCell extends Cell{
	private AnchorPane localAnchor;
	private ObservableList<MessageBean> observableItems;

	public ObservableList<MessageBean> getObservableItems() {
		return observableItems;
	}
	public void setObservableItems(ObservableList<MessageBean> observableItems) {
		this.observableItems = observableItems;
	}
	public MessageCell(AnchorPane anchor,AnchorPane main,BorderPane pane) {
		super(anchor,pane);
		this.localAnchor=main;
		super.getScroll().setVisible(true);
		super.getBox().setSpacing(10);
	}
	public void setItems(ObservableList<MessageBean> messages) {
		super.getAnchor().getChildren().remove(super.getScroll());
		this.observableItems=messages;
		ScrollPane s=new ScrollPane();
		s.setMinWidth(Region.USE_PREF_SIZE);
		s.setMaxWidth(Region.USE_PREF_SIZE);
		s.setMaxHeight(Region.USE_PREF_SIZE);
		s.setMinHeight(Region.USE_PREF_SIZE);
		s.setPrefHeight(localAnchor.getPrefHeight()*433/625);
		s.setPrefWidth(localAnchor.getPrefWidth()*904/1280);
		s.setMinHeight(432);
		super.setScroll(s);
		VBox b=new VBox();
		b.setSpacing(10*localAnchor.getPrefHeight()/625);
		VBox.setVgrow(b, Priority.ALWAYS);
		super.setBox(b);
		super.getScroll().setContent(b);
		super.getAnchor().getChildren().add(super.getScroll());
		messages.addListener((ListChangeListener<MessageBean>)e->
				this.setItems(messages)
		);
		for(MessageBean item: messages) {
		this.setItem(item);
	}
		
	}
	private void setItem(MessageBean item) {
		super.getScroll().setVvalue(1);
		HBox hBox = new HBox();
		hBox.setMaxWidth(Region.USE_PREF_SIZE);
		hBox.setMinWidth(Region.USE_PREF_SIZE);
		hBox.setPrefWidth(850*localAnchor.getPrefWidth()/1280);
    	if(item.getIdMittente()!=MenuBar.getInstance().getLoggedUser().getId()) {
    		hBox.setAlignment(Pos.BASELINE_LEFT);
    	}
    	else {
    		hBox.setAlignment(Pos.BASELINE_RIGHT);
    	}
        // Create centered Label
        VBox label = new VBox();
        Text msg=new Text(item.getText());
        label.setMinHeight(Region.USE_PREF_SIZE);
        label.setMaxHeight(Region.USE_PREF_SIZE);
        label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.getStyleClass().add("label");
        label.setPrefWidth((super.getAnchor().getPrefWidth()-(1.0/5)*super.getAnchor().getPrefWidth())/2);
        msg.setWrappingWidth((super.getAnchor().getPrefWidth()-(1.0/4)*super.getAnchor().getPrefWidth())/2);
        label.setAlignment(Pos.CENTER);
        label.getChildren().add(msg);
        hBox.getChildren().add(label);
        localAnchor.widthProperty().addListener((observable,newValue,oldValue)->{
            label.setPrefWidth((super.getAnchor().getPrefWidth()-(1.0/5)*super.getAnchor().getPrefWidth())/2);
            msg.setWrappingWidth((super.getAnchor().getPrefWidth()-(1.0/4)*super.getAnchor().getPrefWidth())/2);
            hBox.setPrefWidth(this.localAnchor.getPrefWidth()*850/1280);
        });
        
        super.getBox().getChildren().add(hBox);
        
	}
	@Override
	public void setItems(List<Object> obj) {
		List<MessageBean> m=new ArrayList<>();
		for(Object o: obj) {
			m.add((MessageBean)o);
		}
		ObservableList<MessageBean> mex=FXCollections.observableArrayList(m);
		this.setItems(mex);
		
	}
	public void resize() {
		if(this.observableItems!=null)
			this.setItems(this.observableItems);
	}
}
