package main.java.travelbook.view;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;


import java.io.ByteArrayOutputStream;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.Place;

import java.time.LocalDate;
import javafx.scene.shape.Line;
import javafx.scene.control.Hyperlink;
import java.util.Collections;
import main.java.travelbook.util.NumberInDayComparator;
import main.java.travelbook.util.Observable;
import javafx.scene.control.ScrollPane;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.application.Platform;
import main.java.travelbook.model.bean.TravelBean;
import javafx.scene.input.MouseEvent;
import main.java.exception.AddTravelException;
import main.java.exception.DBException;
import main.java.exception.MapboxException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.AddTravel;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.util.DateUtil;
import main.java.travelbook.util.Notification;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import javafx.scene.layout.RowConstraints;
import java.util.List;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;
import main.java.travelbook.view.animation.OpacityAnimation;
import main.java.travelbook.view.autocomplete.SearchPlaceTextField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
public class AddViewController implements Observer{
	private Integer travelId;
	private File travelFileFoto;
	private Notification dot;
	@FXML
	private ButtonBar menuBar;
	@FXML
	private Line stepsBarLine;
	@FXML
	private Line titleLine;
	@FXML
	private Label selectDates;
	@FXML
	private Label addDescription;
	@FXML
	private Label costLabel;
	@FXML
	private Label uploadFoto;
	@FXML
	private Label whatType;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label selectStops;
	@FXML
	private Label givePractical;
	@FXML
	private Label stepImageLabel;
	@FXML
	private Button removeImage;
	@FXML
	private Button allDoneButton;
	@FXML
	private Label giveUsAName;
	@FXML
	private Label hiSoGlad;
	@FXML
	private AnchorPane travelInfoPane;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private ScrollPane travelPane;
	private BorderPane mainPane;
	@FXML
	private Button profile;
	@FXML
	private Button chat;
	@FXML
	private Button explore;
	@FXML
	private Button choosePresentation;
	@FXML
	private ImageView viewPresentation;
	@FXML
	private AnchorPane internalPane;
	@FXML
	private Pane progressPane;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private DatePicker startDate;
	@FXML
	private ImageView arrowImage;
	@FXML
	private DatePicker endDate;
	@FXML
	private TextField travelName;
	@FXML
	private TextArea travelDescription;
	@FXML
	private Button saveDraft;
	@FXML
	private AnchorPane filterPane;
	@FXML
	private ChoiceBox<String> dayBox;
	@FXML
	private ButtonBar stepsBar;
	@FXML
	private Button newStop;
	@FXML
	private TextArea stopDescription;
	@FXML
	private TextArea practicalInformation;
	@FXML
	private Button chooseStepImages;
	@FXML
	private ProgressIndicator progressIndicator;
	private ImageGridPane imageGridPane;
	@FXML
	private AnchorPane stepInfoPane;
	@FXML
	private StackPane viewImagePane;
	@FXML
	private ImageView viewImage;
	@FXML
	private Button closeImage;
	@FXML
	private Button closeProgressBar;
	@FXML
	private Button removeStep;
	@FXML
	private Label errorDayPanel;
	@FXML
	private Text errorDateField;
	@FXML
	private ScrollPane stepsScroll;
	@FXML
	private Label nowGiveUs;
	@FXML
	private Label youAreEditing;
	@FXML
	private Hyperlink viewOnMap;
	@FXML
	private TextField costField;
	private Integer dayNumber;
	private boolean saved=true;
	private List<List<StepBean>> stepByDay=new ArrayList<>();
	private TravelBean travel;
	private List<List<ImageGridPane>> dayImagePane=new ArrayList<>();
	private int stepNumber;
	private int nextRow;
	private int nextCol;
	private double standardImageHeight=89.34;
	private double standardImageWidth=89.34;
	private long numOfDays;
	private SearchPlaceTextField searchText;
	private ImageView actualImage;
	private int stepLimit=10;
	private double stepInfoPaneHeight;
	private AddTravel myController = new AddTravel();
	@FXML
	private void initialize() {
		MenuBar.getInstance().setNewThread();
		MenuBar.getInstance().addObserver(this);
		if(MenuBar.getInstance().getNotified())
			this.update(MenuBar.getInstance());
		//set travel and the first day and the first step by default.
		LocalDate dataFinale=endDate.getValue();
		LocalDate dataIniziale=startDate.getValue();
		this.stepInfoPaneHeight=620;
		imageGridPane=new ImageGridPane();
		dayNumber=0;
		stepNumber=0;
		startDate.valueProperty().addListener((observable,oldValue,newValue)->
				startDateListener(dataFinale, dataIniziale));
		this.viewPresentation.setOnMouseClicked((MouseEvent e)->{
			if(this.viewPresentation.getImage()!=null) {
			//Show the image 
			this.actualImage=viewPresentation;
			this.viewImage.setImage(viewPresentation.getImage());
			viewImagePane.setVisible(true);
			OpacityAnimation anim=new OpacityAnimation();
			anim.setBackTop(internalPane, viewImagePane);
			anim.setLimits(0.1, 0.9);
			anim.start();
			}
			//else do nothing
		});
		endDate.valueProperty().addListener((observable,oldValue,newValue)->
				endDateListener());
		dayBox.valueProperty().addListener((observable,oldValue,newValue)->
			dayBoxListener());
		this.practicalInformation.textProperty().addListener(e->
			infListener());
		TextField text=new TextField();
		text.setPrefHeight(26);
		text.setPrefWidth(378);
		text.setLayoutX(181);
		text.setLayoutY(25);
		text.setPromptText("search location");
		text.applyCss();
		searchText=new SearchPlaceTextField(text);
		stepInfoPane.getChildren().add(searchText.getTextField());
		searchText.getLastSelectedItem().addListener((observable,oldValue,newValue)->{
			saved=false;
			//Add this value to Place field for the selected StepBean
			if(searchText.getLastSelectedItem().get()!=null) {
			Place place=searchText.getLastSelectedItem().getValue();
			stepByDay.get(dayNumber).get(stepNumber).setPlace(place.toString());
			stepByDay.get(dayNumber).get(stepNumber).setFullPlace(place);
			}
			
		});
		this.stopDescription.textProperty().addListener((observable,oldValue,newValue)->
			descrListener());
		this.travelName.textProperty().addListener((observable,oldValue,newValue)->{
			if(travelName.getText().length()>20) {
				this.travelName.setText(this.travelName.getText().substring(0,20));
			}
			else {
			travelName.setStyle("");
			saved=false;
			}
		});
		this.travelDescription.textProperty().addListener((observable,oldValue,newValue)->{
			if(this.travelDescription.getText().length()>100) {
				this.travelDescription.setText(this.travelDescription.getText().substring(0,100));
			}
			else {
			saved=false;
			travelDescription.setStyle("");
			}
		});
		this.viewPresentation.imageProperty().addListener((observable,oldValue,newValue)->{
			saved=false;
			viewPresentation.setStyle("");
		});
		CheckBox element;
		for(int i=0;i<this.filterPane.getChildren().size();i++) {
			element=(CheckBox)this.filterPane.getChildren().get(i);
			element.selectedProperty().addListener((observable,oldValue,newValue)->{
			this.filterPane.setStyle("");
			saved=false;
			});
		}
		this.costField.textProperty().addListener((observable,oldValue,newValue)->{
			saved=false;
			this.costField.setStyle("");
		});
	}
	private void descrListener() {
		if(this.stopDescription.getText()!=null) {
			if(this.stopDescription.getText().length()>300) {
				this.stopDescription.setText(this.stopDescription.getText().substring(0,300));
			}
			else {
			saved=false;
			this.stepByDay.get(dayNumber).get(stepNumber).setDescriptionStep(stopDescription.getText());
			}
		}
	}
	private void dayBoxListener() {
		String valore=dayBox.getValue();
		dayNumber=Integer.parseInt(valore);
		dayNumber--;
		//Set la buttonBar a contenere i bottoni dei suoi step e fai il fire sul primo
		stepsBar.getButtons().removeAll(stepsBar.getButtons());
		List<StepBean> steps=stepByDay.get(dayNumber);
		Button button;
		for(int i=0;i<steps.size();i++) {
			button=makeButton();
			stepsBar.getButtons().add(button);
		}
		button=(Button)stepsBar.getButtons().get(0);
		button.fire();
	}
	private void infListener() {
		if(this.practicalInformation.getText()!=null) {
		if(this.practicalInformation.getText().length()>500) {
			this.practicalInformation.setText(this.practicalInformation.getText().substring(0,500));
		}
		else {
		this.saved=false;
		this.stepByDay.get(dayNumber).get(stepNumber).setPrecisionInformation(this.practicalInformation.getText());
		}
		}
	}
	private void endDateListener() {
		saved=false;
		endDate.setStyle("");
		DateUtil util=new DateUtil();
		if(!util.isAfter(endDate.getValue(),startDate.getValue())) {
				endDate.setValue(null);
				//then show an error message
			}
		else {
			if(startDate.getValue()!=null && endDate.getValue()!=null) {
			//riesamina il numero di giorni del viaggio
			numOfDays=util.numOfDaysBetween(startDate.getValue(),endDate.getValue())+1;
			changeListOfDays();
			}
		}
	}
	private void startDateListener(LocalDate dataFinale, LocalDate dataIniziale) {
		saved=false;
		startDate.setStyle("");
		DateUtil util=new DateUtil();
		if(!util.isAfter(dataFinale,dataIniziale)) {
			startDate.setValue(null);
		}
		else {
			
			//riesamina il numero di giorni del viaggio.
			numOfDays=util.numOfDaysBetween(startDate.getValue(), endDate.getValue());
			changeListOfDays();
		}
	}
	@Override
	public void update(Observable bar, Object notify) {
		boolean value=(Boolean)notify;
		if(value) {
			Platform.runLater(()->
				dot=new Notification(mainAnchor, 30));
			}
		else {
			if(dot!=null) dot.remove();
		}
	}
	@Override
	public void update(Observable bar) {
		this.update(bar,true);
	}
	public class ImageGridPane{
		//GridPane with a matrix that show if an entry (row,col) is empty or not. 
		//If is empty positions[row][col]==1
		private List<List<Integer>> positions=new ArrayList<>();
		private List<List<File>> files=new ArrayList<>();
		
		private GridPane gridPane;
		public ImageGridPane() {
			gridPane=new GridPane();
			//setup to a gridPane with 5 col and 1 row
			nextCol=0;
    		nextRow=0;
    		this.gridPane.setPrefHeight(standardImageHeight);
    		this.gridPane.setPrefWidth(standardImageWidth*5);
    		ColumnConstraints column1=new ColumnConstraints();
    		ColumnConstraints column2=new ColumnConstraints();
    		ColumnConstraints column3=new ColumnConstraints();
    		ColumnConstraints column4=new ColumnConstraints();
    		ColumnConstraints column5=new ColumnConstraints();
    		column1.setPercentWidth(20);
    		column2.setPercentWidth(20);
    		column3.setPercentWidth(20);
    		column4.setPercentWidth(20);
    		column5.setPercentWidth(20);
    		this.gridPane.getColumnConstraints().addAll(column1,column2,column3,column4,column5);
    		this.moveX(35*mainAnchor.getPrefWidth()/1280);
    		this.moveY(520*mainAnchor.getPrefHeight()/625);
    		RowConstraints row1=new RowConstraints();
    		row1.setPrefHeight(standardImageHeight);
    		this.gridPane.getRowConstraints().add(row1);
			positions.add(new ArrayList<>());
			files.add(new ArrayList<>());
			for(int i=0;i<5;i++) {
				positions.get(0).add(1);
			}
			
		}
		public void moveX(double newX) {
			this.gridPane.setLayoutX(newX);
		}
		public void moveY(double newY) {
			this.gridPane.setLayoutY(newY);
		}
		public void add(Node node,int col,int row) {
			this.gridPane.add(node, col,row);
			positions.get(row).set(col, 0);
		}
		public void updateRow() {
			positions.add(new ArrayList<>());
			for(int i=0;i<5;i++) {
				positions.get(positions.size()-1).add(1);
			}
		}
		public void remove(int row,int col) {
			//set valid the entry
			int c=0;
			int count=0;
			int i=0;
			int j=0;
			for(i=0;i<row+1;i++) {
				for(j=0;j<this.positions.get(i).size();j++) {
					if(this.positions.get(i).get(j)==1)
						count++;
				}
			}
			j=0;
			i=0;
			c=0;
			count=row*c+col+count;
			while(c<count) {
				j++;
				if(j==5) {
					i++;
					j=0;
				}
				c++;
			}
			this.positions.get(i).set(j, 1);
		}
		public boolean isValid(int row,int col) {
			//return ture if the position is valid
			return this.positions.get(row).get(col)==1;
			
		}
		public void resizeRow() {
			for(int i=0;i<this.gridPane.getRowConstraints().size();i++) {
				RowConstraints row=this.gridPane.getRowConstraints().get(i);
				row.setPrefHeight(this.gridPane.getPrefHeight()/this.gridPane.getRowConstraints().size());
			}
		}
		public GridPane getGridPane() {
			return this.gridPane;
		}
		public List<List<File>> getFiles(){
			return files;
		}
	}
	public void setMain(BorderPane main) {
		this.mainPane=main;
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			double height=this.mainAnchor.getPrefHeight();
			this.internalPane.setPrefHeight(height*625/625);
			this.internalPane.setLayoutY(0);
			this.menuBar.setPrefHeight(85*height/625);
			this.menuBar.setLayoutY(0);
			for(int i=0;i<menuBar.getButtons().size();i++) {
				Button button=(Button)menuBar.getButtons().get(i);
				button.setPrefHeight(56.8*height/625);
			}
			this.travelPane.setPrefHeight(526*height/625);
			this.travelPane.setLayoutY(95*height/625);
			this.travelInfoPane.setPrefHeight(730*height/625);
			this.travelInfoPane.setLayoutY(0);
			this.hiSoGlad.setPrefHeight(38*height/625);
			this.hiSoGlad.setLayoutY(0);
			this.giveUsAName.setPrefHeight(23.2*height/625);
			this.giveUsAName.setLayoutY(70*height/625);
			this.travelName.setPrefHeight(33.6*height/625);
			this.travelName.setLayoutY(67*height/625);
			this.startDate.setPrefHeight(33.6*height/625);
			this.startDate.setLayoutY(115*height/625);
			this.endDate.setPrefHeight(33.6*height/625);
			this.endDate.setLayoutY(115*height/625);
			this.travelDescription.setPrefHeight(100*height/625);
			this.travelDescription.setLayoutY(163*height/625);
			this.choosePresentation.setPrefHeight(38.4*height/625);
			this.choosePresentation.setLayoutY(320*height/625);
			this.viewPresentation.setFitHeight(177.04*height/625);
			this.viewPresentation.setLayoutY(358*height/625);
			this.filterPane.setPrefHeight(106.4*height/625);
			this.filterPane.setLayoutY(610*height/625);
			CheckBox element;
			for(int i=0;i<filterPane.getChildren().size();i++) {
				element=(CheckBox)filterPane.getChildren().get(i);
				element.setPrefHeight(height*20.8/625);
			}
			saveDraft.setPrefHeight(70*height/625);
			saveDraft.setLayoutY(580*height/625);
			allDoneButton.setPrefHeight(70*height/625);
			allDoneButton.setLayoutY(660*height/625);
			arrowImage.setFitHeight(30*height/625);
			this.arrowImage.setLayoutY(295*height/625);
			progressPane.setPrefHeight(400*height/625);
			progressPane.setLayoutY(150*height/625);
			progressBar.setPrefHeight(40*height/625);
			progressBar.setLayoutY(175*height/625);
			closeProgressBar.setPrefHeight(38*height/625);
			closeProgressBar.setLayoutY(20*height/625);
			viewImagePane.setPrefHeight(444*height/625);
			viewImagePane.setLayoutY(106*height/625);
			viewImage.setFitHeight(400*height/625);
			closeImage.setPrefHeight(36*height/625);
			removeImage.setPrefHeight(36*height/625);
			this.nowGiveUs.setPrefHeight(32*height/625);
			this.nowGiveUs.setLayoutY(27*height/625);
			this.youAreEditing.setPrefHeight(39*height/625);
			this.youAreEditing.setLayoutY(59*height/625);
			this.dayBox.setPrefHeight(31.2*height/625);
			this.dayBox.setLayoutY(63*height/625);
			this.stepsScroll.setPrefHeight(520*height/625);
			this.stepsScroll.setLayoutY(100*height/625);
			this.stepInfoPane.setPrefHeight(this.stepInfoPaneHeight*height/625);
			this.stepsBar.setPrefHeight(50*height/625);
			this.stepsBar.setLayoutY(70*height/625);
			this.stopDescription.setPrefHeight(122*height/625);
			this.stopDescription.setLayoutY(160*height/625);
			this.practicalInformation.setPrefHeight(98*height/625);
			this.practicalInformation.setLayoutY(350*height/625);
			this.chooseStepImages.setPrefHeight(38.4*height/625);
			this.chooseStepImages.setLayoutY(470*height/625);
			this.progressIndicator.setPrefHeight(42*height/625);
			this.progressIndicator.setLayoutY(470*height/625);
			this.resizeImagesHeight(height);
		
			
			this.standardImageHeight=89.34*height/625;
			this.removeStep.setPrefHeight(5*height/625);
			this.removeStep.setLayoutY(98*height/625);
			this.newStop.setPrefHeight(40*height/625);
			this.newStop.setLayoutY(80*height/625);
			this.addDescription.setPrefHeight(height*23.2/625);
			this.addDescription.setLayoutY(160*height/625);
			this.selectDates.setPrefHeight(23.2*height/625);
			this.selectDates.setLayoutY(120*height/625);
			this.costLabel.setPrefHeight(23.2*height/625);
			this.costLabel.setLayoutY(280*height/625);
			this.costField.setPrefHeight(34*height/625);
			this.costField.setLayoutY(275*height/625);
			this.uploadFoto.setPrefHeight(23.2*height/625);
			this.uploadFoto.setLayoutY(330*height/625);
			this.whatType.setLayoutY(583*height/625);
			this.whatType.setPrefHeight(23.2/625*height);
			this.selectStops.setPrefHeight(39*height/625);
			this.selectStops.setLayoutY(20*height/625);
			this.searchText.getTextField().setPrefHeight(26*height/625);
			this.searchText.getTextField().setLayoutY(25*height/625);
			this.descriptionLabel.setPrefHeight(29.2*height/625);
			this.descriptionLabel.setLayoutY(150*height/625);
			this.givePractical.setPrefHeight(39.2*height/625);
			this.givePractical.setLayoutY(300*height/625);
			this.stepImageLabel.setPrefHeight(39.2*height/625);
			this.stepImageLabel.setLayoutY(470*height/625);
			this.stepsBarLine.setLayoutY(124*height/625);
			this.titleLine.setLayoutY(53*height/625);
		});
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			double width=mainAnchor.getPrefWidth();
			this.internalPane.setPrefWidth(1280*width/1280);
			this.travelPane.setPrefWidth(640*width/1280);
			this.travelPane.setLayoutX(10*width/1280);
			this.travelInfoPane.setLayoutX(640*width/1280);
			this.hiSoGlad.setPrefWidth(557*width/1280);
			this.hiSoGlad.setLayoutX(10*width/1280);
			this.giveUsAName.setPrefWidth(280*width/1280);
			this.giveUsAName.setLayoutX(10*width/1280);
			this.travelName.setPrefWidth(236*width/1280);
			this.travelName.setLayoutX(296*width/1280);
			this.selectDates.setPrefWidth(160.8*width/1280);
			this.selectDates.setLayoutX(10*width/1280);
			this.startDate.setPrefWidth(164*width/1280);
			this.startDate.setLayoutX(181*width/1280);
			this.endDate.setPrefWidth(173*width/1280);
			this.endDate.setLayoutX(width/1280*350);
			this.addDescription.setPrefWidth(160.8*width/1280);
			this.addDescription.setLayoutX(10*width/1280);
			this.travelDescription.setPrefWidth(400*width/1280);
			this.travelDescription.setLayoutX(179*width/1280);
			this.costLabel.setPrefWidth(189.6*width/1280);
			this.costLabel.setLayoutX(10*width/1280);
			this.costField.setPrefWidth(239*width/1280);
			this.costField.setLayoutX(223*width/1280);
			this.uploadFoto.setPrefWidth(284.8*width/1280);
			this.uploadFoto.setLayoutX(10*width/1280);
			this.choosePresentation.setPrefWidth(120*width/1280);
			this.choosePresentation.setLayoutX(334*width/1280);
			this.viewPresentation.setFitWidth(530*width/1280);
			this.viewPresentation.setLayoutX(69*width/1280);
			this.whatType.setPrefWidth(156.8*width/1280);
			this.whatType.setLayoutX(10*width/1280);
			this.filterPane.setPrefWidth(307*width/1280);
			this.filterPane.setLayoutX(10*width/1280);
			for(int i=0;i<filterPane.getChildren().size();i++) {
				CheckBox box=(CheckBox)this.filterPane.getChildren().get(i);
				box.setPrefWidth(150*width/1280);
				if(i<4) {
					box.setLayoutX(0);
				}
				else {
					box.setLayoutX(160*width/1280);
				}
			}
			this.allDoneButton.setPrefWidth(270*width/1280);
			this.allDoneButton.setLayoutX(350*width/1280);
			this.saveDraft.setLayoutX(350*width/1280);
			this.saveDraft.setPrefWidth(270*width/1280);
			this.menuBar.setPrefWidth(592*width/1280);
			this.menuBar.setButtonMinWidth(136*width/1280);
			this.nowGiveUs.setPrefWidth(501*width/1280);
			this.nowGiveUs.setLayoutX(700*width/1280);
			this.youAreEditing.setPrefWidth(184*width/1280);
			this.youAreEditing.setLayoutX(700*width/1280);
			this.dayBox.setPrefWidth(70*width/1280);
			this.dayBox.setLayoutX(889*width/1280);
			this.stepsScroll.setPrefWidth(580*width/1280);
			this.stepsScroll.setLayoutX(677*width/1280);
			this.stepInfoPane.setPrefWidth(580*width/1280);
			this.selectStops.setPrefWidth(184*width/1280);
			this.selectStops.setLayoutX(14*width/1280);
			this.searchText.getTextField().setPrefWidth(378*width/1280);
			this.searchText.getTextField().setLayoutX(181*width/1280);
			this.stepsBar.setPrefWidth(420*width/1280);
			this.stepsBar.setButtonMinWidth(28*width/1280);
			this.stepsBar.setLayoutX(20*width/1280);
			this.stepsBarLine.setLayoutX(171*width/1280);
			this.descriptionLabel.setPrefWidth(170.4*width/1280);
			this.descriptionLabel.setLayoutX(31*width/1280);
			this.stopDescription.setPrefWidth(348*width/1280);
			this.stopDescription.setLayoutX(200*width/1280);
			this.givePractical.setPrefWidth(444*width/1280);
			this.givePractical.setLayoutX(33*width/1280);
			this.practicalInformation.setPrefWidth(435*width/1280);
			this.practicalInformation.setLayoutX(34*width/1280);
			this.stepImageLabel.setPrefWidth(197.6*width/1280);
			this.stepImageLabel.setLayoutX(36*width/1280);
			this.chooseStepImages.setPrefWidth(120*width/1280);
			this.chooseStepImages.setLayoutX(307*width/1280);
			this.progressIndicator.setPrefWidth(45*width/1280);
			this.progressIndicator.setLayoutX(420*width/1280);
			this.resizeImagesWidth(width);
			standardImageWidth=89.34*width/1280;
			this.newStop.setPrefWidth(40*width/1280);
			this.newStop.setLayoutX(504*width/1280);
			this.removeStep.setPrefWidth(40*width/1280);
			this.removeStep.setLayoutX(449*width/1280);
			this.arrowImage.setFitWidth(30*width/1280);
			this.arrowImage.setLayoutX(650*width/1280);
			this.progressPane.setPrefWidth(600*width/1280);
			this.progressPane.setLayoutX(300*width/1280);
			this.progressBar.setPrefWidth(200*width/1280);
			this.progressBar.setLayoutX(200*width/1280);
			this.closeProgressBar.setPrefWidth(80*width/1280);
			this.closeProgressBar.setLayoutX(500*width/1280);
			this.viewImagePane.setPrefWidth(400*width/1280);
			this.viewImagePane.setLayoutX(400*width/1280);
			this.viewImage.setFitWidth(400*width/1280);
			this.closeImage.setPrefWidth(36*width/1280);
			this.removeImage.setPrefWidth(36*width/1280);
		});
		this.mainAnchor.setPrefWidth(this.mainPane.getWidth());
		this.mainAnchor.setPrefHeight(625*this.mainPane.getHeight()/720);
		
	}
	private void resizeImagesHeight(double height) {
		for(int i=0;i<dayImagePane.size();i++) {
			for(ImageGridPane imageGrid: dayImagePane.get(i)) {
				GridPane gridPane=imageGrid.getGridPane();
				gridPane.setPrefHeight(gridPane.getRowConstraints().size()*89.34*height/625);
				imageGrid.moveY(520*height/625);
				imageGrid.resizeRow();
				for(int j=0;j<gridPane.getChildren().size();j++) {
					ImageView view=(ImageView)gridPane.getChildren().get(j);
					view.setFitHeight(89.34*height/625);
				}
			}
	}
	}
	private void resizeImagesWidth(double width) {
		for(int i=0;i<dayImagePane.size();i++) {
			for(ImageGridPane imageGrid: dayImagePane.get(i)) {
				GridPane gridPane=imageGrid.getGridPane();
				gridPane.setPrefWidth(5*89.34*width/1280);
				imageGrid.moveX(35*width/1280);
				for(int j=0;j<gridPane.getChildren().size();j++) {
					ImageView view=(ImageView)gridPane.getChildren().get(j);
					view.setFitWidth(89.34*width/1280);
				}
			}
		}
	}
	private boolean alertSave() {
		 Alert saveAlert=new TriggerAlert().triggerAlertCreate("There are some unsaved information that will be lost. What do you want to do?","help" );
		 saveAlert.setTitle("Unsaved information");
		 saveAlert.setHeaderText("There are some unsaved information");
		 ButtonType saveExit=new ButtonType("Save And Exit");
		 ButtonType notSave=new ButtonType("Don't save and exit");
		 ButtonType cancel=new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
		 saveAlert.getButtonTypes().clear();
		 saveAlert.getButtonTypes().addAll(saveExit,notSave,cancel);
		 saveAlert.initOwner(this.mainPane.getScene().getWindow());
		 Optional<ButtonType> result=saveAlert.showAndWait();
		 if(result.isPresent()) {
		 if(result.get()==saveExit) {
			 this.saveDraft.fire();
		 }
		 else if(result.get()==cancel) {
			 return false;
		 }
		 }
		 return true;
	}
	 @FXML
	    private void profileHandler(){
		 boolean exit=true;
		 if(!saved) {
			 //ask if the user want to save 
			 exit=alertSave();
			 }
			 if(exit) {
	    	try {
	    		MenuBar.getInstance().deleteObserver(this);
	    	MenuBar.getInstance().moveToProfile(mainPane);
	    	}catch(MissingPageException e) {
	    		e.exit();
	    	}
			 }
	    }
	    @FXML
	    private void exploreHandler() {
	    	boolean exit=true;
	    	if(!saved) {
	    		exit=alertSave();
	    	}
	    	if(exit) {
	    	try {
	    		MenuBar.getInstance().deleteObserver(this);
	    		MenuBar.getInstance().moveToExplore(mainPane);
	    	}catch(MissingPageException e) {
	    		e.exit();
	    	}}
	    }
	    @FXML
	    private void chatHandler() {
	    	boolean exit=true;
	    	if(!saved) {
	    		exit=alertSave();
	    	}
	    	if(exit) {
	    	try {
	    		MenuBar.getInstance().deleteObserver(this);
	    		MenuBar.getInstance().moveToChat(mainPane);
	    	}catch(MissingPageException e) {
	    		e.exit();
	    	}}
	    }
	    @FXML
	    private void choosePresentationHandler() {
	    	FileChooser dialog=new FileChooser();
	    	dialog.setTitle("Choose a presentation photo");
	    	dialog.getExtensionFilters().add(new ExtensionFilter("Image","*.png","*.jpg"));
	    	File selectedFile=dialog.showOpenDialog(mainPane.getScene().getWindow());
	    	Image presentationImage;
	    	if(selectedFile!=null) {
	    		this.travelFileFoto=selectedFile;
	    		presentationImage=new Image(selectedFile.toURI().toString());
	    		viewPresentation.setImage(presentationImage);
	    	}
	    }
	    private void verificaInfo(TravelBean travel,List<Object> listOfErrors) {
	    	if(!travelName.getText().isEmpty() && travelName.getText()!=null) {
	    		travel.setNameTravel(travelName.getText());
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(travelName);
	    	}
	    	
	    	if(!travelDescription.getText().isEmpty() && travelDescription.getText()!=null) {
	    		travel.setDescriptionTravel(travelDescription.getText());
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(travelDescription);
	    	}
	    }
	    @FXML
	    private void allDoneHanlder() {
	    	
	    	travel=new TravelBean();
	    	travel.setShare(true);
	    	List<Object> listOfErrors=new ArrayList<>();
	    	travel.setListStep(new ArrayList<>());
	    	progressPane.setVisible(true);
	    	progressPane.setOpacity(0.9);
	    	internalPane.setOpacity(0.1);
	    	this.closeProgressBar.setVisible(false);
	    	// sostituire questo codice con invio al controller applicativo dei dati.
	    	this.verificaInfo(travel, listOfErrors);
	    	if(viewPresentation.getImage()!=null) {
	    		try {
	    		travel.setPathBackground(viewPresentation.getImage());
	    		travel.setPathFile(this.travelFileFoto);
	    		BufferedImage bImage = SwingFXUtils.fromFXImage(viewPresentation.getImage(), null);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
			
				ImageIO.write(bImage, "png", s);
				this.travel.setArray(s.toByteArray());
	    		}catch(Exception e) {
	    			new TriggerAlert().triggerAlertCreate("we couldn't load your photo", "warn");
	    		}
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(viewPresentation);
	    		viewPresentation.setStyle("-fx-border-color: #FF0000");
	    	}
	    	this.addFiltersAndDate(listOfErrors, travel);
	    	if(this.costField.getText()!=null && !this.costField.getText().isEmpty()) {
	    		try{
	    			travel.setCostTravel(Double.parseDouble(this.costField.getText()));
	    		}catch(NumberFormatException e) {
	    			listOfErrors.add(this.costField);
	    		}
	    	}
	    	else {
	    		listOfErrors.add(costField);
	    	}
	    	//then take every steps for each day
	    	List<StepBean> incompleteSteps=new ArrayList<>();
	    	travel.setListStep(new ArrayList<>());
	    	this.setTravelSteps(travel, incompleteSteps,true);
	    	if(!incompleteSteps.isEmpty()) {
	    		//Vorrei gestire questa situazione in maniera diversa mostrando dove sono
	    		//gli errori all'utente ma non so bene come
	    		//Per ora lo avverto che ci sono
	    		progressPane.setOpacity(0);
    			internalPane.setOpacity(1);
    			progressPane.setVisible(false);
	    		new TriggerAlert().triggerAlertCreate("There are some incomplete steps, complete them and then retry", "err").showAndWait();
	    		}
	    		if(!listOfErrors.isEmpty()) {
	    			this.modifyColor(listOfErrors);
	    			progressPane.setOpacity(0);
	    			internalPane.setOpacity(1);
	    			progressPane.setVisible(false);
	    		}
	    		progressBar(listOfErrors, incompleteSteps);
	    			
	    }
	    private void progressBar(List<Object> listOfErrors, List<StepBean> incompleteSteps) {
	    	
	    new Thread(()->{
	    	if(listOfErrors.isEmpty()&&incompleteSteps.isEmpty()) {
			Platform.runLater(()->{
				double indeterminate=ProgressIndicator.INDETERMINATE_PROGRESS;
				progressBar.setProgress(indeterminate);
				});
			//Call the controller applicativo
			try {
				activateProgressBar();
			}catch(AddTravelException ex) {
				Platform.runLater(()->{
						new TriggerAlert().triggerAlertCreate(ex.getMessage(), "err").showAndWait();
						this.progressBar.setVisible(false);
						this.progressBar.setOpacity(0);
						this.internalPane.setOpacity(1);
				});
			}catch(Exception e) {
				Platform.runLater(()->{
					new TriggerAlert().triggerAlertCreate("Sometimes go bad "+e.getMessage(), "err").showAndWait();
					this.progressBarDoneHandler();
				});
				
			}
	    }
	    }).start();
	    }
	    private void modifyColor(List<Object> listOfErrors) {
	    	for(int i=0;i<listOfErrors.size();i++) {
				Node node=(Node) listOfErrors.get(i);
				node.setStyle("-fx-border-color: #FF0000");
			}
	    }
	    private void activateProgressBar() throws DBException, MapboxException {
	    	if(travelId!=null) {
				myController.saveAndDelete(travel, travelId,MenuBar.getInstance().getLoggedUser().getId());
			}
			else {
		myController.saveTravel(travel,MenuBar.getInstance().getLoggedUser().getId());
			}
		saved=true;
		Platform.runLater(()->{
			progressBar.setProgress(1);
			//when done activate the close button
	    	closeProgressBar.setVisible(true);
		});
	    }
	    private void addFiltersAndDate(List<Object> listOfErrors,TravelBean travel) {
	    	DateUtil util=new DateUtil();
	    	CheckBox element;
	    	List<String> filtri=new ArrayList<>();
	    	for(int i=0;i<filterPane.getChildren().size();i++) {
	    		element=(CheckBox)filterPane.getChildren().get(i);
	    		if(element.isSelected()) {
	    			filtri.add(element.getText());
	    		}
	    	}
	    	if(!filtri.isEmpty()) {
	    		travel.setType(filtri);
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(filterPane);
	    	}
	    	if(this.startDate.getValue()!=null) {
	    		travel.setStartTravelDate(util.toString(startDate.getValue()));
	    	}
	    	else {
	    		listOfErrors.add(startDate);
	    	}
	    	if(this.endDate.getValue()!=null && !util.isFuture(this.endDate.getValue())) {
	    		travel.setEndTravelDate(util.toString(this.endDate.getValue()));
	    	}
	    	else {
	    		listOfErrors.add(endDate);
	    	}
	    }
	    private void setTravelSteps(TravelBean travel, List<StepBean> incompleteSteps,boolean total) {
	    	try {	    	
	    		for(int day=0;day<this.stepByDay.size();day++) {
	    			List<StepBean> steps=stepByDay.get(day);
	    			for(int stepN=0;stepN<steps.size();stepN++) {
	    				//for each step
	    				StepBean step=steps.get(stepN);
	    				if(step.getPlace()!=null &&!step.getPlace().isEmpty() && (!total ||  (step.getDescriptionStep()!=null && !step.getDescriptionStep().isEmpty() && step.getPrecisionInformation()!=null && !step.getPrecisionInformation().isEmpty() )) ) {
	    					step.setListPhoto(new ArrayList<>());
	    					
	    					for(int im=0;im<dayImagePane.get(day).get(stepN).getGridPane().getChildren().size();im++) {
	    						ImageView view=(ImageView)dayImagePane.get(day).get(stepN).getGridPane().getChildren().get(im);
	    						BufferedImage bImage = SwingFXUtils.fromFXImage(view.getImage(), null);
	    						ByteArrayOutputStream s = new ByteArrayOutputStream();
	    					
	    						ImageIO.write(bImage, "png", s);

	    						step.getBytes().add(s);
	    					}
	    					travel.getListStep().add(step);
	    				}
	    				else {
	    					incompleteSteps.add(step);
	    				}
	    		
	    			}
	    		
	    		}	    					
	    	}catch(IOException e) {
	    		new TriggerAlert().triggerAlertCreate("Problem while loading step", "warn");
	    	}
	    }
	    @FXML
	    private void saveAsDraftHandler() {
	    	DateUtil util=new DateUtil();
	    	travel=new TravelBean();
	    	travel.setNameTravel(travelName.getText());
	    	travel.setDescriptionTravel(this.travelDescription.getText());
	    	travel.setShare(false);
	    	String startDateString=util.toString(this.startDate.getValue());
	    	String endDateString=util.toString(this.endDate.getValue());
	    	
	    	try {
	    		travel.setCostTravel(Double.parseDouble(this.costField.getText()));
	    		String travelCost=this.costField.getText();
	    		if(travelCost.contains("f") || travelCost.contains("F") || travelCost.contains("d") || travelCost.contains("D")) {
	    			throw new NumberFormatException();
	    		}
	    	}catch(NullPointerException e) {
	    		travel.setCostTravel(null);
	    	}catch(NumberFormatException e) {
	    		if(this.costField.getText()!=null || !this.costField.getText().isEmpty())
	    			new TriggerAlert().triggerAlertCreate("travel cost must be a number! This information will not be stored", "err").showAndWait();
	    		
	    	}
	    	travel.setStartTravelDate(startDateString);
	    	travel.setEndTravelDate(endDateString);
	    	try {
                BufferedImage bImage = SwingFXUtils.fromFXImage(viewPresentation.getImage(), null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", s);
                this.travel.setArray(s.toByteArray());
                }catch(Exception e) {
                    new TriggerAlert().triggerAlertCreate("we couldn't load your photo", "warn");
                }
	    	travel.setShare(false);
	    	List<String> filtri=new ArrayList<>();
	    	CheckBox element;
	    	for(int i=0;i<filterPane.getChildren().size();i++) {
	    		element=(CheckBox)filterPane.getChildren().get(i);
	    		if(element.isSelected()) {
	    			filtri.add(element.getText());
	    		}
	    	}
	    	travel.setType(filtri);
	    	travel.setListStep(new ArrayList<>());
	    	this.setTravelSteps(travel, new ArrayList<>(),false);
	    	//then call the controller and send data
	    	progressPane.setVisible(true);
	    	progressPane.setOpacity(1);
	    	internalPane.setOpacity(0);
	    	new Thread(()->{
    			
    			Platform.runLater(()->{
    				double indet=ProgressIndicator.INDETERMINATE_PROGRESS;
    				progressBar.setProgress(indet);
    				});
    			//Call the controller applicativo
    			try {
    				activateProgressBar();
    			}catch(Exception e) {
    				Platform.runLater(()->{ 
    					progressBar.setVisible(false);
    					progressBar.setOpacity(0);
    					internalPane.setOpacity(1);
    					new TriggerAlert().triggerAlertCreate("An error has occured while save your travel please try again", "err").showAndWait();
    					progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    					});
    				
    				
    			}
    			
    		}).start();
	    	
	    }
	    private void incrementProgress() {
	    	this.progressBar.setProgress(this.progressBar.getProgress()+ 0.001);
	    }
	    @FXML
	    private void progressBarDoneHandler() {
	    	OpacityAnimation anim=new OpacityAnimation();
	    	anim.setBackTop(progressPane, internalPane);
	    	anim.setLimits(0, 1);
	    	anim.start();
	    	progressPane.setVisible(false);
	    	//redirect to the view of the travel
	    }
	    @FXML
	    private void multipleChoosHandler() {
	    	if(dayNumber>=0) {
	    	FileChooser dialog=new FileChooser();
	    	//Visto che place dovrebbe essere una stringa lunga forse meglio solo la citt 
	    	dialog.setTitle("Choose some photos for this step" + stepByDay.get(dayNumber).get(stepNumber).getPlace());
	    	dialog.getExtensionFilters().add(new ExtensionFilter("Image","*.png","*.jpg"));
	    	List<File> files = dialog.showOpenMultipleDialog(mainPane.getScene().getWindow());
	    	if(files!=null) {

	    	Image im;
	    	ImageView view;
	    	double percentuale=(double)1/(double)files.size();
	    	
	    	for(int i=0;i<files.size();i++) {
	    		saved=false;
	    		im=new Image(files.get(i).toURI().toString(),false);
	    		view=new ImageView();
	    		view.setFitHeight(standardImageHeight);
	    		view.setFitWidth(standardImageWidth);
	    		view.setImage(im);
	    		view.setOnMouseClicked(this::openImage);
	    		while(!imageGridPane.isValid(nextRow,nextCol)) {
	    			nextCol++;
	    			if(nextCol==5) {
	    				nextCol=0;
	    				nextRow++;
	    			}
	    		}
	    		//Aggiungi il file nella stessa posizione in cui trovi il file Image 
	    		imageGridPane.add(view, nextCol, nextRow);
	    		updateGridIndex();
	    		progressIndicator.setProgress(progressIndicator.getProgress()+percentuale);
	    		
	    	}
	    	}
	    	}
	    }
	    private void updateGridIndex() {
	    	nextCol++;
	    	if (nextCol==5) {
	    		nextCol=0;
	    		int numRow=imageGridPane.getGridPane().getRowConstraints().size();
	    		imageGridPane.getGridPane().getRowConstraints().add(new RowConstraints(imageGridPane.getGridPane().getRowConstraints().get(0).getPrefHeight()));
	    		imageGridPane.getGridPane().setPrefHeight(imageGridPane.getGridPane().getHeight()/numRow + imageGridPane.getGridPane().getHeight());
	    		nextRow++;
	    		imageGridPane.updateRow();
	    		stepInfoPaneHeight=620+89.34*(imageGridPane.getGridPane().getRowConstraints().size()-1);
	    		this.stepInfoPane.setPrefHeight(this.stepInfoPane.getHeight()+standardImageHeight);
	    	}
	    }
	    @FXML
	    private void addStepButton() {
	    	if(stepsBar.getButtons().size()<this.stepLimit) {
	    	saved=false;
	    	if(dayNumber>=0) {
	    	dayImagePane.get(dayNumber).add(new ImageGridPane());
	    	stepNumber++;
	    	StepBean step=new StepBean();
	    	
	    	step.setGroupDay(dayNumber);
	    	stepByDay.get(dayNumber).add(step);
	    	step.setNumberInDay(stepNumber);
	    	
	    	Button button=makeButton();
	    	stepsBar.getButtons().add(button);
	    	button=(Button)stepsBar.getButtons().get(0);
	    	button.fire();
	    	}
	    	}
	    	else {
	    		new TriggerAlert().triggerAlertCreate("You have reached the maximum number of steps per day, the maximum number is "+this.stepLimit, "err").showAndWait();
	    	}
	    }
	    private Button makeButton() {
	    	Button button=new Button();
	    	button.applyCss();
            button.setOnAction((ActionEvent e)->{	
            for(int i=0;i<stepsBar.getButtons().size();i++) {
            	if(this.stepNumber==i) {
            		//Remove the style for the pressed button
            		Button prec=(Button)stepsBar.getButtons().get(stepsBar.getButtons().size()-i-1);
            		prec.setStyle("");
            	}
            }
            for(int i=0;i<stepsBar.getButtons().size();i++) {
            	if(stepsBar.getButtons().get(i)==e.getTarget()) {
            		this.stepNumber=stepsBar.getButtons().size()-i-1;
            		Button actual=(Button)stepsBar.getButtons().get(stepsBar.getButtons().size()-stepNumber-1);
            		actual.setStyle("-fx-border-color: -fx-light-grey;-fx-background-color: -fx-dcolor;-fx-background-insets: -16 -8 0 -8;-fx-border-insets: -16 -8 0 -8;");
            		break;
            	}
            }
            StepBean step=this.stepByDay.get(dayNumber).get(stepNumber);
            this.searchText.getTextField().setText(null);
            this.searchText.getLastSelectedItem().set(null);
            
            if(step.getPlace()!=null) {
            	this.searchText.setBlocked(true);
            this.searchText.getTextField().setText(step.getPlace());
            this.searchText.setBlocked(false);
            }
            this.searchText.setBlocked(true);
            this.searchText.getLastSelectedItem().set(step.getFullPlace());
            this.searchText.setBlocked(false);
    		this.stopDescription.setText(step.getDescriptionStep());
    		this.practicalInformation.setText(step.getPrecisionInformation());
    		
    		stepInfoPane.getChildren().remove(imageGridPane.getGridPane());
    		
    		imageGridPane=dayImagePane.get(step.getGroupDay()).get(this.stepNumber);
    		stepInfoPane.getChildren().add(imageGridPane.getGridPane());
    		nextRow=imageGridPane.getGridPane().getRowConstraints().size()-1;
    		nextCol=imageGridPane.getGridPane().getChildren().size() - nextRow*5;
    		});
    		return button;
	    }
	    
	    private void changeListOfDays() {
	    	if(numOfDays>0) {
	    		this.stepsScroll.setVisible(true);
	    		this.arrowImage.setVisible(true);
	    		this.nowGiveUs.setVisible(true);
	    		this.youAreEditing.setVisible(true);
	    		this.dayBox.setVisible(true);
	    
	    	if(numOfDays>dayImagePane.size()) {
	    		Integer x=dayImagePane.size();
	    		for(Integer i=x;i<numOfDays;i++) {
	    			Integer num=i+1;
		    		dayBox.getItems().add(num.toString());
	    			dayImagePane.add(new ArrayList<>());
	    			dayImagePane.get(i).add(new ImageGridPane());
	    			//Add one entry for each day added
		    		stepByDay.add(new ArrayList<>());
		    		StepBean step=new StepBean();
		    		step.setGroupDay(stepByDay.size()-1);
		    		
		    		stepByDay.get(stepByDay.size()-1).add(step);
	    		}
	    	}
	    	else {
	    	if(numOfDays<dayImagePane.size()) {
	    		Integer x=dayImagePane.size();
	    		int y=(int)numOfDays;
	    		int count=x;
	    		while(count>y) {
	    			dayBox.getItems().remove(y);
	    			dayImagePane.remove(y);
	    			stepByDay.remove(y);
	    			count--;
	    		}
	    	}
	    	}
	    	dayBox.setValue("1");
	    	}
	    }
	    @FXML
	    private void closeImage() {
	    	OpacityAnimation anim= new OpacityAnimation();
	    	anim.setBackTop(viewImagePane, internalPane);
	    	anim.setLimits(0, 1);
	    	anim.start();
	    	viewImagePane.setVisible(false);
	    	
	    }
	    @FXML
	    private void removeImage() {
	    	saved=false;
	    	if(this.actualImage!=this.viewPresentation) {
	    	//Remove the selected image from gridPane and step.
	    	int col=imageGridPane.getGridPane().getColumnConstraints().size();
	    	int row=imageGridPane.getGridPane().getRowConstraints().size();
	    	boolean finito=false;
	    	for(int i=0;i<row;i++) {
	    		for(int j=0;j<col;j++) {
	    			if(imageGridPane.getGridPane().getChildren().get(i*col +j)==actualImage) {
	    				actualRemove(i,j,col);
	    				finito=true;
	    				break;
	    			}
	    			
	    		}
	    		if(finito)
	    			break;
	    	}
	    	}
	    	else {
	    		viewPresentation.setImage(null);
	    	}
	    	//then close the image panel
	    	this.closeImage();
	    }
	    private void actualRemove(int i, int j, int col) {
	    	imageGridPane.getGridPane().getChildren().remove(i*col+j);
			imageGridPane.remove(i, j);
			nextCol=j;
			nextRow=i;
	    }
	    @FXML
	    private void removeStepHandler() {
	    	saved=false;
	    	//then remove the selected step from the list and the button bar.
	    	
	    	Alert confirmAlert=new TriggerAlert().triggerAlertCreate("if you remove this step then all the information are deleted", "help");
	    	confirmAlert.setTitle("Delete step confirmation");
	    	confirmAlert.setHeaderText("Are you sure to remove this step?");
	    	confirmAlert.showAndWait();
	    	ButtonType result=confirmAlert.getResult();
	    	if(result.getButtonData()==ButtonData.OK_DONE) {
	    		//then remove the step
	    			//Remove the step from the list and delete the gridPane associated and then also from the buttonbar
	    			stepByDay.get(dayNumber).remove(stepNumber);
	    			dayImagePane.get(dayNumber).remove(stepNumber);
	    			stepsBar.getButtons().remove(stepsBar.getButtons().size()-stepNumber-1);
	    			//if stepsBar has some buttons then fire on the last else remove all the information in the field.
	    			if(!stepsBar.getButtons().isEmpty()) {
	    				Button button=(Button)stepsBar.getButtons().get(0);
	    				button.fire();
	    			}
	    			else {
	    				//We can't have a day without steps so create a new step and fire on it
	    				addStepButton();
	    			}
	    		
	    		
	    	}
	    	}
	    	
	    
	    public void modfiyTravelMode(Integer travelId) {
	    	
	    	try {
	    		
	    	this.travel=myController.getTravelById(travelId);
	    	this.travelId=travelId;
	    	if(travel.getCostTravel()!=null)
	    		this.costField.setText(travel.getCostTravel().toString());
	    	if(travel.getNameTravel()!=null) {
	    	travelName.setText(travel.getNameTravel());
	    	}
	    	if(travel.getDescriptionTravel()!=null) {
	    	this.travelDescription.setText(travel.getNameTravel());
	    	}
	    	DateUtil util=new DateUtil();
	    	if(travel.getStartDate()!=null) {
	    		this.startDate.setValue(util.toLocalDate(travel.getStartDate()));
	    	}
	    	if(travel.getEndDate()!=null) {
	    		this.endDate.setValue(util.toLocalDate(travel.getEndDate()));
	    	}
	    	Image img = travel.getPathImage();
	    	if(img!=null) {
	    		this.viewPresentation.setImage(img);
	    	}
	    	
	    	List<StepBean> stepOfTravel=travel.getListStep();
	    	List<List<StepBean>> stepInDay=new ArrayList<>();
	    	
	    	int numOfDaysInt=this.dayBox.getItems().size();
	    	this.setFiltersFromTravel(travel);
	    	if(stepOfTravel!=null) {
	    		for(int i=0;i<numOfDaysInt;i++) {
	    			stepInDay.add(new ArrayList<>());
	    		}
	    		for(int i=0;i<stepOfTravel.size();i++) {
	    			StepBean step=stepOfTravel.get(i);
	    			stepInDay.get(step.getGroupDay()).add(step);
	    		}
	    		for(int i=0;i<numOfDaysInt;i++) {
	    			Collections.sort(stepInDay.get(i),new NumberInDayComparator());
	    		}
	    		this.stepByDay=stepInDay;
	    		this.setImageForSteps();
	    		this.dayBox.valueProperty().set("1");
	    		this.dayBoxListener();
	    	}
	    	}catch(Exception e) {
	    		new TriggerAlert().triggerAlertCreate("error while loading travel", "err");
	    	}
	    	
	    }
		private void setFiltersFromTravel(TravelBean travel) {
			List<String> filtri=travel.getTypeTravel();
			
	    	if(filtri!=null) {
	    		for(String filter: filtri) {
	    			
	    			//Select all filters
	    			for(int i=0;i<filterPane.getChildren().size();i++) {
	    				CheckBox elem=(CheckBox)filterPane.getChildren().get(i);
	    				if(elem.getText().equals(filter)) {
	    					elem.setSelected(true);
	    					break;
	    				}
	    			}
	    		}
	    	}
		}
	    private void setImageForSteps() {
	    	this.dayImagePane.clear();
	    	for(int i=0;i<stepByDay.size();i++) {
	    		this.dayImagePane.add(new ArrayList<>());
    			for(int step=0;step<stepByDay.get(i).size();step++) {
    				this.dayImagePane.get(i).add(new ImageGridPane());
    				
    				nextCol=0;
    				nextRow=0;
    				//GridPane created before by changeDayListener
    				//Add elements to this pane
    				List<Image> l = stepByDay.get(i).get(step).getListPhoto();
    				if(l!=null) {
    					this.imageGridPane=dayImagePane.get(i).get(step);
    				for(int c=0;c<l.size();c++) {
    					Image image=l.get(c);
    					
    					ImageView view;
    					view=new ImageView();
    		    		view.setFitHeight(standardImageHeight);
    		    		view.setFitWidth(standardImageWidth);
    		    		view.setImage(image);
    		    		view.setOnMouseClicked(this::openImage);
    		    		
    		    		this.dayImagePane.get(i).get(step).add(view, nextCol, nextRow);
    		    		updateGridIndex();
    				}
    				}
    			}
    		}
	    	
	    }
	    @FXML
	    private void viewOnMapHandler() {
	    	List<StepBean> stepNow=new ArrayList<>();
	    	for(List<StepBean> stepInDay: this.stepByDay) {
	    		for(StepBean step: stepInDay) {
	    			if(step.getPlace()!=null && !step.getPlace().isEmpty()) {
	    				//Aggiunge solo gli step di cui   noto almeno il posto
	    				stepNow.add(step);
	    			}
	    		}
	    	}
	    	if(!stepNow.isEmpty()) {
	    	ViewOnMapController controller=new ViewOnMapController();
	    	controller.load(stepNow);
	    	}
	    	}
	    private void openImage(MouseEvent e) {
	    	ImageView io=(ImageView)e.getTarget();
			actualImage=io;
			viewImage.setImage(io.getImage());
			viewImagePane.setVisible(true);
			OpacityAnimation anim=new OpacityAnimation();
			anim.setBackTop(internalPane, viewImagePane);
			anim.setLimits(0.1, 0.9);
			anim.start();
	    }
}
