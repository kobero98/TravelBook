package main.java.travelbook.view;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import main.java.travelbook.view.animation.SlideImageAnimationHL;
import main.java.travelbook.view.animation.SlideImageAnimationHR;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewTravelController {
	private Object[] array1=new Object[10];
	private Object[] array2=new Object[10];
	private Button button;
	private Text text;
	private BorderPane mainPane;
	private int goBack; 
	@FXML
	private Button backButton;
	@FXML 
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane view;
	@FXML
	private HBox travel;
	@FXML
	private Pane descrbg;
	@FXML
	private Text descr;
	@FXML
	private ButtonBar bb;
	@FXML
	private Button profileButton;
	@FXML
	private Button chatButton;
	@FXML
	private Button favButton;
	@FXML
	private Button shareButton;
	@FXML
	private TabPane days;
	@FXML
	private ScrollPane step;
	@FXML
	private AnchorPane stepAnchor;
	@FXML
	private Label stepName;
	@FXML
	private StackPane photoStack;
	@FXML
	private ScrollPane stepPhoto;
	@FXML
	private ButtonBar photoBox;
	@FXML
	private Text stepDescr;
	@FXML
	private Text stepInf;
	@FXML
	private Button rightScroll;
	@FXML
	private Button leftScroll;
	private String path = "main/java/travelbook/cupola1.jpg";
	private Button selected = null;
	@FXML
	private void initialize() {
    	
    	CornerRadii rad = new CornerRadii(0);
    	Insets in = new Insets(0);
    	Pane travelPic = new Pane();
    	travelPic.setPrefHeight(mainAnchor.getPrefHeight()*176/625);
    	travelPic.setPrefWidth(mainAnchor.getPrefWidth()*278.5/1280);
    	try {
    		Image myPhoto = new Image(path); 
    		BackgroundImage bgPhoto = new BackgroundImage(myPhoto, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
    		Background mybg1 = new Background(bgPhoto);
    		travelPic.setBackground(mybg1);
    	}catch(IllegalArgumentException e) {
    		BackgroundFill bgcc1 = new BackgroundFill(Paint.valueOf("rgb(255, 162, 134)"), rad, in);
        	
        	Background mybg1 = new Background(bgcc1);
        	travelPic.setBackground(mybg1);
    	}
    	
    	travelPic.setStyle("-fx-shape: \"M 350 900 L 350 795 C 350 780 360 770 375 770 L 438 770 C 453 770 463 780 463 795 L 463 900 Z\"");
    	VBox vBox = new VBox();
    	HBox hBox = new HBox();
    	vBox.setPrefWidth(mainAnchor.getPrefWidth()*278.5/1280);
    	vBox.setSpacing(mainAnchor.getPrefHeight()*(176.0/15)/625);
    	Label name = new Label("Travelname");
    	Text date = new Text("dd/mm/yyyy - dd/mm/yyyy");
    	date.setWrappingWidth(mainAnchor.getPrefWidth()*278.5/1280);
    	hBox.setAlignment(Pos.BOTTOM_RIGHT);
    	vBox.getChildren().add(name);
    	vBox.getChildren().add(date);
    	vBox.getChildren().add(hBox);
    	
    	travel.getChildren().add(travelPic);
    	travel.getChildren().add(vBox);
    	
    	
    	descr.setText("This is our travel description");
    	//dummy
    	days.getTabs().removeAll(days.getTabs());
    	days.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		Tab day = days.getTabs().get(days.getSelectionModel().getSelectedIndex());
    		
    		VBox stepBox = new VBox();
    		stepBox.setFillWidth(false);
    		stepBox.setAlignment(Pos.CENTER);
    		HBox steps = new HBox();
    		HBox names = new HBox();
    		steps.setAlignment(Pos.BOTTOM_LEFT);
    		names.setAlignment(Pos.TOP_LEFT);
    		steps.setPrefWidth(mainAnchor.getPrefWidth()*500/1280);
    		names.setPrefWidth(mainAnchor.getPrefWidth()*500/1280);
    		steps.setSpacing((steps.getPrefWidth()-(10*mainAnchor.getPrefWidth()*40/1280))/9);//hbox-width-10*buttonwidth/9
    		steps.getStyleClass().add("itinerary");
    		Line buttonLine = new Line();
    		buttonLine.getStyleClass().add("itinerary-line");
    		for(int j=0; j<10; j++) {
    			Button b = new Button();
    			Text t = new Text("this step");
    			b.setPrefWidth(mainAnchor.getPrefWidth()*40/1280);
    			b.setPrefHeight(mainAnchor.getPrefHeight()*40/625);
    			t.setWrappingWidth(mainAnchor.getPrefWidth()*50/1280);
    			b.setOnAction((ActionEvent e)->{
    				setStep();
    				String css = "button-focused";
    				Button actual = (Button)e.getSource();
    				actual.getStyleClass().add(css);
    				if(selected!=null) selected.getStyleClass().remove(css);
    				selected = actual;
    				});
    			t.getStyleClass().add("text");
    			steps.getChildren().add(b);
    			names.getChildren().add(t);
    		}
    		buttonLine.setStartX(0.0);
    		buttonLine.setStartY(mainAnchor.getPrefHeight()*50/625);
    		buttonLine.setEndX(mainAnchor.getPrefWidth()*500/1280);
    		buttonLine.setEndY(mainAnchor.getPrefHeight()*50/625);
    		stepBox.getChildren().add(steps);
    		stepBox.getChildren().add(buttonLine);
    		stepBox.getChildren().add(names);
    		day.setContent(stepBox);
    		mainAnchor.heightProperty().addListener((observable1, oldValue1, newValue1)->{
    			buttonLine.setStartY(mainAnchor.getHeight()*50/625);
    			buttonLine.setEndY(mainAnchor.getPrefHeight()*50/625);
    			array1 = steps.getChildren().toArray();
    			for(int k=0;k<array1.length;k++) {
    				button=(Button)array1[k];
    				button.setPrefHeight(mainAnchor.getHeight()*40/625);
    			}
    			
    		});
    		mainAnchor.widthProperty().addListener((observable2, oldValue2, newValue2)->{
    			
    			steps.setSpacing(mainAnchor.getPrefWidth()*steps.getSpacing()/1280);//hbox-width-10*buttonwidth/9
    			steps.setPrefWidth(mainAnchor.getPrefWidth()*500/1280);
        		names.setPrefWidth(mainAnchor.getPrefWidth()*500/1280);
        		buttonLine.setEndX(mainAnchor.getPrefWidth()*500/1280);
        		array1 = steps.getChildren().toArray();
        		array2 = names.getChildren().toArray();
    			for(int k=0;k<array1.length;k++) {
    				button=(Button)array1[k];
    				button.setPrefWidth(mainAnchor.getWidth()*40/1280);
    				text=(Text)array2[k];
    				text.setWrappingWidth(mainAnchor.getWidth()*50/1280);
    			}
    			
    		});
            
      });
    	for(int i=1; i<=12;i++) {
    		Tab day = new Tab();
    		day.setText("day" + i);
    		days.getTabs().add(day);
    	}
    	mainAnchor.heightProperty().addListener((observable, oldValue, newValue)->{            		
    		travelPic.setPrefHeight(mainAnchor.getPrefHeight()*176/625);
    		vBox.setSpacing(mainAnchor.getPrefHeight()*(176.0/15)/625);
    	});
    	mainAnchor.widthProperty().addListener((observable, oldValue, newValue)->{
    		travelPic.setPrefWidth(mainAnchor.getPrefWidth()*278.5/1280);
    		date.setWrappingWidth(mainAnchor.getPrefWidth()*278.5/1280);
    		vBox.setPrefWidth(mainAnchor.getPrefWidth()*278.5/1280);
    	});
	}
	
	public void setMainPane(BorderPane main, int provenience) {
		this.mainPane=main;
		this.goBack=provenience;
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			backButton.setPrefHeight(mainAnchor.getHeight()*34/625);
			backButton.setLayoutY(mainAnchor.getHeight()*10/625);
			view.setPrefHeight(mainAnchor.getHeight()*609/625);
			view.setLayoutY(mainAnchor.getHeight()*7/625);
			bb.setPrefHeight(mainAnchor.getHeight()*63/625);
			bb.setLayoutY(mainAnchor.getHeight()*201/625);
			days.setPrefHeight(mainAnchor.getHeight()*245/625);
			days.setLayoutY(mainAnchor.getHeight()*343/625);
			descrbg.setPrefHeight(mainAnchor.getHeight()*134/625);
			descrbg.setLayoutY(mainAnchor.getHeight()*201/625);
			travel.setPrefHeight(mainAnchor.getHeight()*176/625);
			travel.setLayoutY(mainAnchor.getHeight()*25/625);
			array1=bb.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefHeight(mainAnchor.getHeight()*35/625);
			}
			step.setPrefHeight(mainAnchor.getHeight()*597/625);
			step.setLayoutY(mainAnchor.getHeight()*10/625);
			stepAnchor.setPrefHeight(mainAnchor.getHeight()*stepPhoto.getPrefHeight()/625);
			stepDescr.setLayoutY(mainAnchor.getHeight()*290/625);
			stepInf.setLayoutY(mainAnchor.getHeight()*stepInf.getLayoutY()/625);
			stepName.setPrefHeight(mainAnchor.getHeight()*40/625);
			stepName.setLayoutY(mainAnchor.getHeight()*24/625);
			photoStack.setPrefHeight(mainAnchor.getHeight()*169/625);
			photoStack.setLayoutY(mainAnchor.getHeight()*80/625);
			stepPhoto.setPrefHeight(mainAnchor.getHeight()*169/625);
			photoBox.setPrefHeight(mainAnchor.getHeight()*169/625);
			leftScroll.setPrefHeight(mainAnchor.getHeight()*20/625);
			rightScroll.setPrefHeight(mainAnchor.getHeight()*20/625);
		});	
		
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			backButton.setPrefWidth(mainAnchor.getWidth()*45/1280);
			view.setPrefWidth(mainAnchor.getWidth()*629/1280);
			view.setLayoutX(mainAnchor.getWidth()*46/1280);
			bb.setPrefWidth(mainAnchor.getWidth()*278.5/1280);
			bb.setLayoutX(mainAnchor.getWidth()*323.5/1280);
			days.setPrefWidth(mainAnchor.getWidth()*555/1280);
			days.setLayoutX(mainAnchor.getWidth()*46/1280);
			descrbg.setPrefWidth(mainAnchor.getWidth()*278.5/1280);
			descrbg.setLayoutX(mainAnchor.getWidth()*45/1280);
			descr.setWrappingWidth(mainAnchor.getWidth()*262/1280);
			travel.setPrefWidth(mainAnchor.getWidth()*557/1280);
			travel.setLayoutX(mainAnchor.getWidth()*45/1280);
			array1=bb.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefWidth(mainAnchor.getWidth()*35/1280);
			}
			step.setPrefWidth(mainAnchor.getWidth()*586/1280);
			step.setLayoutX(mainAnchor.getWidth()*684/1280);
			stepAnchor.setPrefWidth(mainAnchor.getWidth()*565/1280);
			stepDescr.setWrappingWidth(mainAnchor.getWidth()*543/1280);
			stepDescr.setLayoutX(mainAnchor.getWidth()*11/1280);
			stepInf.setWrappingWidth(mainAnchor.getWidth()*543/1280);
			stepInf.setLayoutX(mainAnchor.getWidth()*11/1280);
			stepName.setPrefWidth(mainAnchor.getWidth()*543/1280);
			stepName.setLayoutX(mainAnchor.getWidth()*11/1280);
			photoStack.setPrefWidth(mainAnchor.getWidth()*560/1280);
			photoStack.setLayoutX(mainAnchor.getWidth()*5/1280);
			stepPhoto.setPrefWidth(mainAnchor.getWidth()*520/1280);
			photoBox.setPrefWidth(mainAnchor.getWidth()*photoBox.getPrefWidth()/1280);
			leftScroll.setPrefWidth(mainAnchor.getWidth()*15/1280);
			rightScroll.setPrefWidth(mainAnchor.getWidth()*15/1280);
		});
		this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
		this.mainAnchor.setPrefWidth(mainPane.getWidth());
	}
	
	private void setStep() {
		//dummy example
		photoBox.getButtons().removeAll(photoBox.getButtons());
		photoBox.setPrefWidth(0);
		stepName.setText("Florence's Duomo");
		ObservableList<Image> photo = FXCollections.observableArrayList( new Image(path) , new Image(path),new Image(path) , new Image(path), new Image(path),new Image(path) , new Image(path));
		for(int i = 0; i < photo.size(); i++) {
			ImageView displayPhoto = new ImageView(photo.get(i));
			displayPhoto.setFitHeight(stepPhoto.getPrefHeight()*3/4);
			displayPhoto.setFitWidth(stepPhoto.getPrefHeight());
			photoBox.setPrefWidth(photoBox.getPrefWidth()+displayPhoto.getFitWidth()+mainAnchor.getPrefWidth()*15/1280);
			photoBox.getButtons().add(displayPhoto);
			stepDescr.setText("Florence’s duomo is the city most iconic landmark. How explained by our guide, it is capped by Filippo Brunelleschi’s red-tiled cupola, built between 1420 and 1436. Climbing the 463 steps inside the dome, you can view the whole city, with its red roofs and Arno river flowing through, ununfold at your feet.\n"
					+ "For the dome, Filippo Brunelleschi took inspiration from the Pantheon in Rome and designed a distinctive octagonal form of inner and outer concentric domes that rest on the drum of the cathedral rather than the roof itself.");
			stepInf.setText("Entrance to the Duomo is free, unless you want to skip the line, that usually take at least an hour.\n"
					+ "To climb the dome you have to pay a small fee, 10€  (5€ for a reduced ticket).");
			Double newHeight = stepDescr.maxHeight(stepDescr.getWrappingWidth()) + stepInf.maxHeight(Double.MAX_VALUE) + 
					photoBox.getPrefHeight() + stepName.getPrefHeight() + mainAnchor.getPrefHeight()*101/625;
			if(newHeight > step.getPrefHeight()) {
				stepAnchor.setPrefHeight(newHeight);
			}
			stepInf.setLayoutY(stepDescr.getLayoutY()+stepDescr.maxHeight(stepDescr.getWrappingWidth())+mainAnchor.getPrefHeight()*10/1280);
			mainAnchor.heightProperty().addListener((observable,oldValue, newValue)->{
				
				displayPhoto.setFitHeight(mainAnchor.getPrefHeight()*displayPhoto.getFitHeight()/625);
				Double newChangeHeight = stepDescr.maxHeight(stepDescr.getWrappingWidth()) + stepInf.maxHeight(Double.MAX_VALUE) + 
					photoBox.getPrefHeight() + stepName.getPrefHeight() + mainAnchor.getPrefHeight()*101/625;
				if(newChangeHeight > step.getPrefHeight()) {
					stepAnchor.setPrefHeight(newChangeHeight);
				}
				stepInf.setLayoutY(stepDescr.getLayoutY()+stepDescr.maxHeight(stepDescr.getWrappingWidth())+mainAnchor.getPrefHeight()*10/1280);
			});
			mainAnchor.widthProperty().addListener((observable, oldVAlue, newValue)->
				displayPhoto.setFitWidth(mainAnchor.getPrefWidth()*displayPhoto.getFitWidth()/1280));
		}
	}
	
	
	@FXML
	private void topTenScrollRightHandler() {
    	SlideImageAnimationHR anim=new SlideImageAnimationHR();
    	anim.setScrollAndMax(stepPhoto, stepPhoto.getHvalue()+4.0/photoBox.getButtons().size());
    	anim.setSpeed(2);
    	anim.start();
	}
	@FXML
	private void topTenScrollLeftHandler() {
    	SlideImageAnimationHL anim=new SlideImageAnimationHL();
    	anim.setScrollAndMin(stepPhoto, stepPhoto.getHvalue()-1);
    	anim.setSpeed(2);
    	anim.start();
	}
	
	@FXML
	private void backHandler() {
		switch(goBack) {
		case 1:
			try {
				MenuBar.getInstance().moveToExplore(mainPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
    		MenuBar.getInstance().moveToProfile(mainPane);
			}catch(IOException e) {
    		e.printStackTrace();
			}
			break;
		case 3:
			try {
				profileButtonHandler();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				MenuBar.getInstance().moveToExplore(mainPane);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	private void profileButtonHandler()throws IOException {
			FXMLLoader loader =new FXMLLoader();
			loader.setLocation(ViewTravelController.class.getResource("ProfileUserViewOther.fxml"));
			AnchorPane internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ProfileOtherController controller=loader.getController();
			controller.setMainPane(mainPane, Integer.parseInt("1"+goBack));
	}
	@FXML
	private void chatButtonHandler() {
		try {
			MenuBar.getInstance().moveToChat(mainPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void favButtonHandler() {
		String css = "fav-selected";
		//dummy method
		if(favButton.getStyleClass().contains(css)) {
			favButton.getStyleClass().remove(css);
		}
		else {
			favButton.getStyleClass().add(css);
		}
	}
	@FXML
	private void shareButtonHandler() {
		//dummy method
		System.out.println("shared!");
	}
}