package main.java.travelbook.view;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
public class TravelButton {
	private Pane pane;
	private StackPane stack;

	private Label title;
	private Label subtitle;

	public Pane getPane() {
		return pane;
	}
	public StackPane getStack() {
		return stack;
	}
	public Label getTitle() {
		return title;
	}
	public void setTitle(Label title) {
		this.title = title;
	}
	public Label getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(Label subtitle) {
		this.subtitle = subtitle;
	}
	public TravelButton(double width, double height, Integer i) {
		// the real constructor take a TravelBean as third parameter
		stack=new StackPane();
		pane=new Pane();
		title=new Label(i.toString());
	    subtitle= new Label(i.toString());
		stack.setPrefWidth(width);
		stack.setPrefHeight(height);
		pane.setPrefWidth(width);
		pane.setPrefHeight(height*130/190);
		pane.setMaxHeight(height*130/190);
		pane.setMinHeight(height*130/190);
		stack.getChildren().addAll(pane,title,subtitle);
		StackPane.setAlignment(pane,Pos.TOP_CENTER);
		StackPane.setAlignment(title,Pos.CENTER);
		StackPane.setAlignment(subtitle,Pos.CENTER);
		StackPane.setMargin(title, new Insets(105,0,0,0));
		StackPane.setMargin(subtitle, new Insets(135,0,0,0));
		stack.prefWidthProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);
		stack.heightProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);
		stack.widthProperty().addListener((observable,oldValue,newValue)->
			this.resize()
		);	}
	private void resize() {
		double width=stack.getWidth();
		double height=stack.getHeight();
		pane.setPrefWidth(width);
		pane.setPrefHeight(height*130/190);
		pane.setMaxHeight(height*130/190);
		pane.setMinHeight(height*130/190);
	}
	
}
