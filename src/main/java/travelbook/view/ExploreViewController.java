package main.java.travelbook.view;
import main.java.travelbook.view.animation.SlideImageAnimationHR;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.travelbook.view.animation.SlideImageAnimationHL;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.Vector;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Line;
public class ExploreViewController {
	//private Vector<TravelBean> travelSelection;
	//private Vector<TravelBean> topTenTravel;
	private ViewTravelController controller;
	private AnchorPane internalPane;
	private StackPane stack;
	private Object array1[]=new Object[15];
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
	private Vector<TravelButton> topTenGroup;
	private Vector<TravelButton> selectionGroup;
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
	private void initialize() {
		//Ask to controller to obtain information about selection travel and top ten travel
		//And load it into the button 
		//The controller must return a Collection of TravelBean compilated and these travelBean must be passed in constructButton
		//In this example use Empty image as Pane and Some strings a cazzo di cane.
		int i=0;
		TravelButton istance;
		topTenGroup=new Vector(10);
		selectionGroup=new Vector(15);
		//Now create some buttons 10 for tt and 15 for travelSelection.
		while(i<10) {
			istance=new TravelButton(136,190.4,i);
			istance.getStack().getStyleClass().add("tile");
			istance.getPane().getStyleClass().add("pane");
			istance.getTitle().getStyleClass().add("my-text");
			istance.getSubtitle().getStyleClass().addAll("my-text", "subtitle");
			topTenGroup.add(istance);
			topTenBar.getButtons().add(istance.getStack());
			istance.getStack().setOnMouseClicked(e->travelExampleHandler());
			i++;
		}
		i=0;
		while(i<15) {
			istance=new TravelButton(136,190.4,i);
			istance.getStack().getStyleClass().add("tile");
			istance.getPane().getStyleClass().add("pane");
			istance.getTitle().getStyleClass().add("my-text");
			istance.getSubtitle().getStyleClass().addAll("my-text", "subtitle");
			selectionGroup.add(istance);
			selectionBar.getButtons().add(istance.getStack());
			istance.getStack().setOnMouseClicked(e->travelExampleHandler());
			i++;
		}
	}
	private void travelExampleHandler() {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ProfileViewController.class.getResource("ViewTravel.fxml"));
		try {
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			controller=loader.getController();
			controller.setMainPane(mainPane,1);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void setMainPane(BorderPane main) {
		this.mainPane=main;
		//then define the resize logic
		
		
		
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			System.out.println("Altezza anchorPane: " + this.mainAnchor.getHeight());
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
			/*for(int i=0;i<15;i++) {
				stack=(StackPane)array1[i];
				stack.setPrefHeight(mainAnchor.getHeight()*190/625);
			}*/
			topTenStack.setPrefHeight(mainAnchor.getHeight()*190/625);
			topTenScroll.setPrefHeight(mainAnchor.getHeight()*190/625);
			topTenBar.setPrefHeight(mainAnchor.getHeight()*190/625);
			array1=topTenBar.getButtons().toArray();
			/*for(int i=0;i<10;i++) {
				stack=(StackPane)array1[i];
				stack.setPrefHeight(mainAnchor.getHeight()*190/625);
			}*/
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
			//ttLine.setEndX(mainAnchor.getWidth()*173.66665649414062/625);
			//selectionLine.setEndX(mainAnchor.getWidth()*173.66665649414062/625);
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
	@FXML
	private void topTenScrollRightHandler() {
		Double upgrade= new Double((double)(3.0/7.0));
    	SlideImageAnimationHR anim=new SlideImageAnimationHR();
    	anim.setScrollAndMax(topTenScroll, topTenScroll.getHvalue()+0.42);
    	anim.setSpeed(2);
    	anim.start();
	}
	@FXML
	private void topTenScrollLeftHandler() {
		Double upgrade= new Double((double)(3.0/7.0));
    	SlideImageAnimationHL anim=new SlideImageAnimationHL();
    	anim.setScrollAndMin(topTenScroll, topTenScroll.getHvalue()-0.42);
    	anim.setSpeed(2);
    	anim.start();
	}
	@FXML
	private void selectionScrollRightHandler() {
		Double upgrade= new Double((double)(1.0/4.0));
    	SlideImageAnimationHR anim=new SlideImageAnimationHR();
    	anim.setScrollAndMax(selectionScroll, selectionScroll.getHvalue()+upgrade);
    	anim.setSpeed(1);
    	anim.start();
	}
	@FXML
	private void selectionScrollLeftHandler() {
		Double upgrade= new Double((double)(1.0/4.0));
    	SlideImageAnimationHL anim=new SlideImageAnimationHL();
    	anim.setScrollAndMin(selectionScroll, selectionScroll.getHvalue()-upgrade);
    	anim.setSpeed(1);
    	anim.start();
	}
	@FXML
    private void profileHandler(){
    	try {
    	MenuBar.getInstance().moveToProfile(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    private void addHandler() {
    	try {
    		MenuBar.getInstance().moveToAdd(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    private void chatHandler() {
    	try {
    		MenuBar.getInstance().moveToChat(mainPane);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
	@FXML
	private void advanceSearchHandler() {
		//Redirect to search view but now simply go to stdout
		System.out.println("Hello i'm trying to go to search");
	}
	@FXML
	private void selectionOnMouseEnter() {
		System.out.println("Sono entrato in selection");
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
		System.out.println("Sono uscito dalla selection");
		mainAnchor.requestFocus();
		selectionScroll.setOnKeyPressed((KeyEvent e)->{
			e.consume();
		});
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
		});
	}
	@FXML
	public void moveToSearch()throws IOException {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MenuBar.class.getResource("SerchPage.fxml"));
		AnchorPane internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		SearchTravelController controller=loader.getController();
		controller.setMainPane(mainPane);
	}
	
	
}

