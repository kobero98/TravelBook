package main.java.travelbook.view;
import main.java.travelbook.view.animation.SlideImageAnimationHR;
import main.java.travelbook.model.bean.TravelBean;
import javafx.scene.input.KeyCode;
import java.util.List;

import exception.MissingPageException;

import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import main.java.travelbook.view.animation.SlideImageAnimationHL;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.Notification;
import main.java.travelbook.util.Observable;
public class ExploreViewController implements Observer{
	private Object[] array1=new Object[15];
	private BorderPane mainPane;
	private Button button;
	@FXML
	private ButtonBar selectionBar;
	@FXML
	private ButtonBar topTenBar;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane worldPane;
	@FXML
	private ImageView cartina;
	@FXML
	private Text advanceSearch;
	@FXML
	private Label advance;
	@FXML
	private Label suggests;
	@FXML
	private Label tt;
	@FXML
	private Label worldTitle;
	@FXML
	private Button goSearchButton;
	@FXML
	private StackPane selectionStack;
	@FXML
	private StackPane topTenStack;
	
	
	@FXML
	private ScrollPane selectionScroll;
	@FXML
	private ScrollPane topTenScroll;
	@FXML
	private Button ttLeft;
	@FXML
	private Button ttRight;
	@FXML
	private Button selLeft;
	@FXML
	private Button selRight;
	@FXML
	private ButtonBar menuBar;
	@FXML
	private Line selectionLine;
	@FXML
	private Line ttLine;
	@FXML
	private void initialize()  {
		//Ask to controller to obtain information about selection travel and top ten travel
		//And load it into the button 
		//The controller must return a Collection of TravelBean compilated and these travelBean must be passed in constructButton
		//In this example use Empty image as Pane and Some strings a cazzo di cane.
		MenuBar.getInstance().addObserver(this);
		MenuBar.getInstance().setNewThread();
		if(MenuBar.getInstance().getNotified())
			this.update(MenuBar.getInstance());
		String myText = "my-text";
		int i=0;
		List<TravelButton> selectionGroup;
		List<TravelButton> topTenGroup;
		TravelButton istance;
		topTenGroup=new ArrayList<>(10);
		selectionGroup=new ArrayList<>(15);
		//Now create some buttons 10 for tt and 15 for travelSelection.
		while(i<10) {
			TravelBean travel=new TravelBean();
			istance=new TravelButton(136,190.4,i,travel);
			istance.getStack().getStyleClass().add("tile");
			istance.getPane().getStyleClass().add("pane");
			istance.getTitle().getStyleClass().add(myText);
			istance.getSubtitle().getStyleClass().addAll(myText, "subtitle");
			topTenGroup.add(istance);
			topTenBar.getButtons().add(istance.getStack());
			
			i++;
		}
		i=0;
		while(i<15) {
			TravelBean travel=new TravelBean();
			istance=new TravelButton(136,190.4,i,travel);
			istance.getStack().getStyleClass().add("tile");
			istance.getPane().getStyleClass().add("pane");
			istance.getTitle().getStyleClass().add(myText);
			istance.getSubtitle().getStyleClass().addAll(myText, "subtitle");
			selectionGroup.add(istance);
			selectionBar.getButtons().add(istance.getStack());

			i++;
		}
	}

	public void setMainPane(BorderPane main) {
		this.mainPane=main;
		//then define the resize logic
		
		
		
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			worldPane.setPrefHeight(mainAnchor.getPrefHeight()*591/625);
			worldPane.setLayoutY(mainAnchor.getPrefHeight()*10/625);
			cartina.setFitHeight(mainAnchor.getPrefHeight()*269/625);
			advance.setPrefHeight(mainAnchor.getPrefHeight()*23.2/625);
			worldTitle.setPrefHeight(mainAnchor.getPrefHeight()*36/625);
			worldTitle.setLayoutY(mainAnchor.getPrefHeight()*10/625);
			cartina.setLayoutY(mainAnchor.getPrefHeight()*100/625);
			advance.setLayoutY(mainAnchor.getPrefHeight()*440/625);
			advanceSearch.setLayoutY(mainAnchor.getHeight()*480/625);
			goSearchButton.setLayoutY(mainAnchor.getHeight()*480/625);
			goSearchButton.setPrefHeight(mainAnchor.getHeight()*52/625);
			menuBar.setPrefHeight(mainAnchor.getHeight()*85/625);
			menuBar.setLayoutY(0);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefHeight(mainAnchor.getHeight()*56/625);
			}
			suggests.setPrefHeight(mainAnchor.getHeight()*36/625);
			suggests.setLayoutY(mainAnchor.getHeight()*100/625);
			selectionLine.setLayoutY(mainAnchor.getHeight()*135/625);
			ttLine.setLayoutY(mainAnchor.getHeight()*395/625);
			tt.setPrefHeight(mainAnchor.getHeight()*36/625);
			tt.setLayoutY(mainAnchor.getHeight()*360/625);
			ttRight.setPrefHeight(mainAnchor.getHeight()*15.74/625);
			ttLeft.setPrefHeight(mainAnchor.getHeight()*15.74/625);
			selectionStack.setPrefHeight(mainAnchor.getHeight()*190/625);
			selectionBar.setPrefHeight(mainAnchor.getHeight()*190/625);
			selectionScroll.setPrefHeight(mainAnchor.getHeight()*190/625);
			selectionStack.setLayoutY(mainAnchor.getPrefHeight()*154/625);
			selLeft.setPrefHeight(mainAnchor.getPrefHeight()*15.74/625);
			selRight.setPrefHeight(mainAnchor.getPrefHeight()*15.74/625);
			array1=selectionBar.getButtons().toArray();

			topTenStack.setPrefHeight(mainAnchor.getHeight()*190/625);
			topTenScroll.setPrefHeight(mainAnchor.getHeight()*190/625);
			topTenBar.setPrefHeight(mainAnchor.getHeight()*190/625);
			array1=topTenBar.getButtons().toArray();

			topTenStack.setLayoutY(mainAnchor.getPrefHeight()*404/625);
		});	
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			worldPane.setPrefWidth(mainAnchor.getWidth()*611/1280);
			worldPane.setLayoutX(mainAnchor.getWidth()*650/1280);
			cartina.setFitWidth(mainAnchor.getWidth()*580/1280);
			cartina.setLayoutX(mainAnchor.getWidth()*15/1280);
			advance.setPrefWidth(mainAnchor.getWidth()*250/1280);
			advanceSearch.setWrappingWidth(mainAnchor.getWidth()*270/1280);
			menuBar.setLayoutX(0);
			worldTitle.setPrefWidth(mainAnchor.getWidth()*375/1280);
			worldTitle.setLayoutX(mainAnchor.getWidth()*10/1280);
			advanceSearch.setLayoutX(mainAnchor.getWidth()*60/1280);
			advance.setLayoutX(mainAnchor.getWidth()*60/1280);
			goSearchButton.setPrefWidth(mainAnchor.getWidth()*54/1280);
			goSearchButton.setLayoutX(mainAnchor.getWidth()*400/1280);
			menuBar.setPrefWidth(mainAnchor.getWidth()*592/1280);
			menuBar.setButtonMinWidth(mainAnchor.getWidth()*136/1280);
			suggests.setPrefWidth(mainAnchor.getWidth()*252/1280);
			tt.setPrefWidth(mainAnchor.getWidth()*298.4/1280);
			ttRight.setPrefWidth(mainAnchor.getWidth()*9.72/1280);
			ttLeft.setPrefWidth(mainAnchor.getWidth()*9.72/1280);
			selectionStack.setPrefWidth(mainAnchor.getWidth()*500/1280);
			selectionScroll.setPrefWidth(mainAnchor.getWidth()*450/1280);
			selectionBar.setPrefWidth(mainAnchor.getWidth()*2250/1280);
			selectionBar.setButtonMinWidth(mainAnchor.getWidth()*136/1280);
			selRight.setPrefWidth(mainAnchor.getWidth()*9.72/1280);
			selLeft.setPrefWidth(mainAnchor.getWidth()*9.72/1280);
			topTenBar.setPrefWidth(mainAnchor.getWidth()*1500/1280);
			topTenBar.setButtonMinWidth(mainAnchor.getWidth()*136/1280);
			topTenStack.setPrefWidth(mainAnchor.getWidth()*500/1280);
			topTenScroll.setPrefWidth(mainAnchor.getWidth()*450/1280);
		});
	this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
	this.mainAnchor.setPrefWidth(mainPane.getWidth());
		}
	@Override
	public void update(Observable bar, Object notify) {
		boolean value=(Boolean)notify;
		if(value) {
			Platform.runLater(()->
				new Notification(mainAnchor,30));
			
		}
	}
	
	@FXML
	private void topTenScrollRightHandler() {

    	SlideImageAnimationHR anim=new SlideImageAnimationHR();
    	anim.setScrollAndMax(topTenScroll, topTenScroll.getHvalue()+0.42);
    	anim.setSpeed(2);
    	anim.start();
	}
	@Override
	public void update(Observable bar) {
		this.update(bar,true);
	}
	@FXML
	private void topTenScrollLeftHandler() {

    	SlideImageAnimationHL anim=new SlideImageAnimationHL();
    	anim.setScrollAndMin(topTenScroll, topTenScroll.getHvalue()-0.42);
    	anim.setSpeed(2);
    	anim.start();
	}
	@FXML
	private void selectionScrollRightHandler() {
		Double upgrade= (1.0/4.0);
    	SlideImageAnimationHR anim=new SlideImageAnimationHR();
    	anim.setScrollAndMax(selectionScroll, selectionScroll.getHvalue()+upgrade);
    	anim.setSpeed(1);
    	anim.start();
	}
	@FXML
	private void selectionScrollLeftHandler() {
		Double upgrade= (1.0/4.0);
    	SlideImageAnimationHL anim=new SlideImageAnimationHL();
    	anim.setScrollAndMin(selectionScroll, selectionScroll.getHvalue()-upgrade);
    	anim.setSpeed(1);
    	anim.start();
	}

	   @FXML
	    private void chatHandler() {
	    	try {
	    		MenuBar.getInstance().moveToChat(mainPane);
	    	}catch(MissingPageException e) {
	    		e.exit();
	    	}
	   }
		@FXML
	    private void profileHandler(){
	    	try {
	    	MenuBar.getInstance().moveToProfile(mainPane);
	    	}catch(MissingPageException e) {
	    		e.exit();
	    	}
	    }
    @FXML
    private void addHandler() {
    	try {
    		MenuBar.getInstance().moveToAdd(mainPane);
    	}catch(MissingPageException e) {
    		e.exit();
    	}
    }
 
	@FXML
	private void advanceSearchHandler() {//credo non venga mai usato e sia da togliere
		//Redirect to search view but now simply go to stdout
	}
	@FXML
	private void selectionOnMouseEnter() {
		selectionScroll.requestFocus();
		selectionScroll.setOnKeyPressed((KeyEvent e)->{
			if(e.getCode()==KeyCode.RIGHT) {
				selectionScrollRightHandler();
			}
			if(e.getCode()==KeyCode.LEFT) {
				selectionScrollLeftHandler();
			}
			e.consume();
		});
	}
	@FXML
	private void selectionOnMouseExit() {
		mainAnchor.requestFocus();
		selectionScroll.setOnKeyPressed((KeyEvent e)->{
			e.consume();
			mainAnchor.requestFocus();
		}
	);
	}
	@FXML
	private void ttOnMouseEnter() {
		topTenScroll.requestFocus();
		topTenScroll.setOnKeyPressed((KeyEvent e)->{
			if(e.getCode()==KeyCode.RIGHT) {
				topTenScrollRightHandler();
			}
			if(e.getCode()==KeyCode.LEFT) {
				topTenScrollLeftHandler();
			}
			e.consume();
		});
	}
	@FXML
	private void ttOnMouseExit() {
		mainAnchor.requestFocus();
		topTenScroll.setOnKeyPressed((KeyEvent e)->{
			e.consume();
			mainAnchor.requestFocus();
		}
		);
	}
	@FXML
	public void moveToSearch() {
		try {
			MenuBar.getInstance().moveToSearch(mainPane);
		} catch (MissingPageException e) {
			e.exit();
		}
	}
	
	
}

