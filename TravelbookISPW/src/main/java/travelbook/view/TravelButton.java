package main.java.travelbook.view;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import exception.MissingPageException;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.Observable;
public class TravelButton implements Observer {
	private Pane pane;
	private StackPane stack;
	
	private Label title;
	private Label subtitle;
	private BorderPane mainPane;
	public void setMainPane(BorderPane p) {
		this.mainPane=p;
	}
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
	public TravelButton(double width, double height, Integer i, MiniTravelBean travel) {
		travel.addObserver(this);
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
		pane.getStyleClass().add("pane");
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
	@Override
	public void update(Observable o) {
		MiniTravelBean myTravel;
		myTravel=(MiniTravelBean) o;
		this.stack.setOnMouseClicked(e->{
			try {
				
				MenuBar.getInstance().setIdTravel(myTravel.getId());
				MenuBar.getInstance().moveToView(mainPane, 1);
			} catch (MissingPageException e1) {
				e1.exit();
			}
		});
		this.title.setText(myTravel.getNameTravel());
		this.subtitle.setText(myTravel.getDescriptionTravel());
		//Carica la foto nel pane
		Image img=myTravel.getPathImage();
		if(img!=null) {
			BackgroundImage bgPhoto = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
			Background newBg = new Background(bgPhoto);
			this.pane.getStyleClass().clear();
			this.pane.setBackground(newBg);
			this.pane.getStyleClass().add("pane");
		}
	}
	@Override
	public void update(Observable o,Object arg) {
		this.update(o);
	}
	
}
