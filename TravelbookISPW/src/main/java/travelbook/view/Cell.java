package main.java.travelbook.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public abstract class Cell {
	private VBox box;
	protected ScrollPane scroll;
	private AnchorPane mainAnchor;
	private BorderPane mainPane;
	private List<Object> items=new ArrayList<>();
	protected Cell(AnchorPane anchor,BorderPane pane) {
		this.box=new VBox();
		this.scroll=new ScrollPane();
		this.scroll.setContent(box);
		this.scroll.setVisible(false);
		this.mainAnchor=anchor;
		this.mainAnchor.getChildren().add(scroll);
		this.mainPane=pane;
	}
	public abstract void setItems(List<Object> obj);
	public List<Object> getItems(){
		return this.items;
	}
	public ScrollPane getScroll() {
		return this.scroll;
	}
	protected VBox getBox() {
		return this.box;
	}
	protected void setList(List<Object> obj) {
		this.items=obj;
	}
	protected AnchorPane getAnchor() {
		return this.mainAnchor;
	}
	protected BorderPane getBorder() {
		return this.mainPane;
	}
	public void setBorder(BorderPane pane) {
		this.mainPane=pane;
	}
	protected void setScroll(ScrollPane scroll) {
		this.scroll=scroll;
	}
	protected void setBox(VBox box) {
		this.box=box;
	}
}
