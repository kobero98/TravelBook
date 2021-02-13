package main.java.travelbook.view.cell;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import main.java.travelbook.view.SearchTravelController.MyTypes;
public class TypeCell extends Cell{
	private final ObjectProperty<MyTypes> selectedItem=new SimpleObjectProperty<>();
	private Pane p;
	public TypeCell(AnchorPane anchor,BorderPane pane, Pane p) {
		super(anchor,pane);
		this.p=p;
		this.p.getChildren().add(super.getScroll());
		super.getAnchor().getChildren().remove(super.getScroll());
	}
	public void setItems(List<Object> obj) {
		for(Object o:obj) {
			MyTypes item=(MyTypes) o;
			HBox box= new HBox(2);
	       	Label label= new Label(item.getType());
	       	label.getStyleClass().add("categories-label");
	       	label.setWrapText(true);
	       	label.setMaxWidth(super.getScroll().getHeight()-1);
	       	Circle cerchio =new Circle(10,item.getColor());
	       	box.getChildren().addAll(cerchio,label);
	       	box.setOnMouseClicked(e->
	       		this.selectedItem.set(item)
	       	);
	       	HBox.setMargin(cerchio, new Insets(2));
	       	HBox.setMargin(label, new Insets(2));
	       	box.setAlignment(Pos.CENTER_LEFT);
	       	super.getBox().getChildren().add(box);
		}
	}
	public ObjectProperty<MyTypes> getSelectedItem(){
		return this.selectedItem;
	}
}
