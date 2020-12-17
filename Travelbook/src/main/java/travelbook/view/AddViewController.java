package main.java.travelbook.view;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.CityBean;
import javafx.scene.input.MouseEvent;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.util.DateUtil;
import main.java.travelbook.util.PlacePrediction;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;
import main.java.travelbook.view.animation.OpacityAnimation;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
public class AddViewController {
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
	@FXML
	private GridPane imageGridPane;
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
	private Integer dayNumber;
	private List<List<StepBean>> stepByDay=new ArrayList<List<StepBean>>();
	private TravelBean travel;
	private List<List<GridPane>> dayImagePane=new ArrayList<List<GridPane>>();
	private int stepNumber,nextRow,nextCol;
	private double standardImageHeight=89.34,standardImageWidth=89.34;
	private Image presentationImage;
	private long numOfDays;
	private AutocompleteTextField searchText;
	private ImageView actualImage;
	@FXML
	private void initialize() {
		//set travel and the first day and the first step by default.
		travel=new TravelBean();
		dayNumber=0;
		stepNumber=0;
		startDate.valueProperty().addListener((observable,oldValue,newValue)->{
			DateUtil util=new DateUtil();
			if(!util.isAfter(endDate.getValue(), startDate.getValue())) {
				if(endDate.getValue()!=null) {
					startDate.setValue(null);
					//then show an error message
			}
			}
			else {
				//riesamina il numero di giorni del viaggio.
				numOfDays=util.numOfDaysBetween(startDate.getValue(), endDate.getValue());
				System.out.println("numero di giorni: "+numOfDays);
				changeListOfDays();
			}
		});
		endDate.valueProperty().addListener((observable,oldValue,newValue)->{
			DateUtil util=new DateUtil();
			if(!util.isAfter(endDate.getValue(),startDate.getValue())) {
				if(startDate.getValue()!=null) {
					endDate.setValue(null);
					//then show an error message
				}
			}
			else {
				//riesamina il numero di giorni del viaggio
				numOfDays=util.numOfDaysBetween(startDate.getValue(),endDate.getValue());
				System.out.println("numero di giorni: "+numOfDays);
				changeListOfDays();
			}
		});
		dayBox.valueProperty().addListener((observable,oldValue,newValue)->{
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
			
		});
		searchText=new AutocompleteTextField();
		searchText.setPrefHeight(26);
		searchText.setPrefWidth(378);
		searchText.setLayoutX(181);
		searchText.setLayoutY(25);
		searchText.setPromptText("search location");
		searchText.applyCss();
		stepInfoPane.getChildren().add(searchText);
		searchText.getLastSelectedItem().addListener((observable,oldValue,newValue)->{
			//Add this value to Place field for the selected StepBean
			if(searchText.getLastSelectedItem().get()!=null) {
			PlacePrediction place=searchText.getLastSelectedItem().getValue();
			stepByDay.get(dayNumber).get(stepNumber).setPlace(place.toString());
			//Forse può essere interessante conservare da qualche parte il dato PlacePrediction per query future 
			//senza doverlo ricercare.
			}
			
		});
		searchText.block();
		this.stopDescription.textProperty().addListener((observable,oldValue,newValue)->{
			//Update description for the selected step in "real time"
			this.stepByDay.get(dayNumber).get(stepNumber).setDescriptionStep(stopDescription.getText());
		});
	}
	public void setMain(BorderPane main) {
		this.mainPane=main;
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
	    private void exploreHandler() {
	    	try {
	    		MenuBar.getInstance().moveToExplore(mainPane);
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
	    private void choosePresentationHandler() {
	    	FileChooser dialog=new FileChooser();
	    	dialog.setTitle("Choose a presentation photo");
	    	dialog.getExtensionFilters().add(new ExtensionFilter("Image","*.png","*.jpg"));
	    	File selectedFile=dialog.showOpenDialog(mainPane.getScene().getWindow());
	    	if(selectedFile!=null) {
	    		presentationImage=new Image(selectedFile.toURI().toString(),viewPresentation.getFitWidth(),viewPresentation.getFitHeight(),false,false);
	    		viewPresentation.setImage(presentationImage);
	    	}
	    }
	    @FXML
	    private void allDoneHanlder() {
	    	//TODO complete with the save of information logic... now i define the progress bar animation
	    	OpacityAnimation anim=new OpacityAnimation();
	    	progressPane.setVisible(true);
	    	anim.setBackTop(internalPane, progressPane);
	    	anim.setLimits(0.1, 0.9);
	    	anim.start();
	    	// sostituire questo codice con invio al controller applicativo dei dati.
	    	double i=0;
	    	while(i<1) {
	    	progressBar.setProgress(progressBar.getProgress()+0.01);
	    	i=i+0.01;
	    	}
	    	//when done activate the close button
	    	closeProgressBar.setVisible(true);
	    }
	    @FXML
	    private void progressBarDoneHandler() {
	    	System.out.println("fatto");
	    	OpacityAnimation anim=new OpacityAnimation();
	    	anim.setBackTop(progressPane, internalPane);
	    	anim.setLimits(0, 1);
	    	anim.start();
	    	progressPane.setVisible(false);
	    	System.out.println("Finito");
	    	//redirect to ...
	    }
	    @FXML
	    private void multipleChoosHandler() {
	    	if(dayNumber>=0) {
	    	FileChooser dialog=new FileChooser();
	    	//Visto che place dovrebbe essere una stringa lunga forse meglio solo la città
	    	dialog.setTitle("Choose some photos for this step" + stepByDay.get(dayNumber).get(stepNumber).getPlace());
	    	dialog.getExtensionFilters().add(new ExtensionFilter("Image","*.png","*.jpg"));
	    	List<File> files = dialog.showOpenMultipleDialog(mainPane.getScene().getWindow());
	    	Image im;
	    	ImageView view;
	    	for(int i=0;i<files.size();i++) {
	    		im=new Image(files.get(i).toURI().toString(),false);
	    		view=new ImageView();
	    		view.setFitHeight(standardImageHeight);
	    		view.setFitWidth(standardImageWidth);
	    		//view.setViewport(new Rectangle2D(standardImageHeight,standardImageWidth,standardImageHeight,standardImageWidth));
	    		view.setImage(im);
	    		view.setOnMouseClicked((MouseEvent e)->{
	    			//Se clicchi sulla foto la apre in "grande"
	    			ImageView io=(ImageView)e.getTarget();
	    			actualImage=io;
	    			viewImage.setImage(io.getImage());
	    			viewImagePane.setVisible(true);
	    			OpacityAnimation anim=new OpacityAnimation();
	    			anim.setBackTop(internalPane, viewImagePane);
	    			anim.setLimits(0.1, 0.9);
	    			anim.start();
	    		});
	    		
	    		imageGridPane.add(view, nextCol, nextRow);
	    		updateGridIndex();
	    	}
	    	}
	    }
	    private void updateGridIndex() {
	    	if (nextCol==5) {
	    		nextCol=0;
	    		int numRow=imageGridPane.getRowConstraints().size();
	    		imageGridPane.getRowConstraints().add(new RowConstraints(imageGridPane.getRowConstraints().get(0).getPrefHeight()));
	    		imageGridPane.setPrefHeight(imageGridPane.getHeight()/numRow + imageGridPane.getHeight());
	    		nextRow++;
	    	}
	    	else {
	    		nextCol++;
	    	}
	    }
	    @FXML
	    private void addStepButton() {
	    	if(dayNumber>=0) {
	    	dayImagePane.get(dayNumber).add(makeGridPane());
	    	stepNumber++;
	    	System.out.println("Step numero: "+stepNumber);
	    	StepBean step=new StepBean();
	    	
	    	step.setGroupDay(dayNumber);
	    	stepByDay.get(dayNumber).add(step);
	    	travel.getListStep().add(step);
	    	//incrementa il valore nel travelBean e memorizza questo stepBean nel travelBean
	    	//setta i valori per StepBean;
	    	Button button=makeButton();
	    	stepsBar.getButtons().add(button);
	    	button=(Button)stepsBar.getButtons().get(0);
	    	button.fire();
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
            		System.out.println("StepNumber: "+stepNumber);
            		Button actual=(Button)stepsBar.getButtons().get(stepsBar.getButtons().size()-stepNumber-1);
            		actual.setStyle("-fx-border-color: lightGrey;-fx-background-color: Dcolor;-fx-background-insets: -16 -8 0 -8;-fx-border-insets: -16 -8 0 -8;");
            		break;
            	}
            }
            StepBean step=this.stepByDay.get(dayNumber).get(stepNumber);
            System.out.println("Step numero: "+this.stepNumber);
            this.searchText.setText(null);
            this.searchText.getLastSelectedItem().set(null);
            System.out.println("Place: "+step.getPlace());
            if(step.getPlace()!=null) {
            this.searchText.setText(step.getPlace());
            }
            //Qui penso di costruire un costruttore per PlacePrediction che a partire dal testo ricostruisce l'info iniziale
            this.searchText.getLastSelectedItem().set(new PlacePrediction(this.searchText.getText()));
    		this.stopDescription.setText(step.getDescriptionStep());
    		
    		//add for practical information
    		stepInfoPane.getChildren().remove(imageGridPane);
    		imageGridPane=dayImagePane.get(step.getGroupDay()).get(this.stepNumber);
    		stepInfoPane.getChildren().add(imageGridPane);
    		nextRow=imageGridPane.getRowConstraints().size()-1;
    		nextCol=imageGridPane.getChildren().size() - nextRow*5;
    		});
    		return button;
	    }
	    private GridPane makeGridPane() {
	    	nextCol=0;
    		nextRow=0;
    		GridPane newGrid=new GridPane();
    		newGrid.setPrefHeight(standardImageHeight);
    		newGrid.setPrefWidth(standardImageWidth*5);
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
    		newGrid.getColumnConstraints().addAll(column1,column2,column3,column4,column5);
    		newGrid.setLayoutX(imageGridPane.getLayoutX());
    		newGrid.setLayoutY(imageGridPane.getLayoutY());
    		RowConstraints row1=new RowConstraints();
    		row1.setPrefHeight(imageGridPane.getPrefHeight()/(imageGridPane.getRowConstraints().size()));
    		newGrid.getRowConstraints().add(row1);
    		
    		return newGrid;
	    }
	    private void changeListOfDays() {
	    	if(numOfDays>0) {
	    		searchText.unblock();
	    	}
	    	for(Integer i=0;i<numOfDays;i++) {
	    		System.out.println("Aggiunto il giorno: "+i);
	    		Integer num=i+1;
	    		dayBox.getItems().add((num).toString());
	    		//Add one entry for each day added
	    		stepByDay.add(new ArrayList<>());
	    		StepBean step=new StepBean();
	    		
	    		step.setGroupDay(stepByDay.size()-1);
	    		System.out.println("Aggiunto step per giorno: "+step.getGroupDay());
	    		travel.getListStep().add(step);
	    		stepByDay.get(stepByDay.size()-1).add(step);
	    	}
	    	if(numOfDays>dayImagePane.size()) {
	    		Integer x=dayImagePane.size();
	    		for(Integer i=0;i<(numOfDays-x);i++) {
	    			dayImagePane.add(new ArrayList<GridPane>());
	    			dayImagePane.get(i).add(makeGridPane());
	    			//Add one entry for each day added
		    		stepByDay.add(new ArrayList<>());
		    		StepBean step=new StepBean();
		    		step.setGroupDay(stepByDay.size()-1);
		    		travel.getListStep().add(step);
		    		stepByDay.get(stepByDay.size()-1).add(step);
	    		}
	    	}
	    	else {
	    	if(numOfDays<dayImagePane.size()) {
	    		Integer x=dayImagePane.size();
	    		int y=(int)numOfDays;
	    		for(int i=y;i<x;i++) {
	    			dayImagePane.remove(i);
	    		}
	    	}
	    	}
	    	dayBox.setValue("1");
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
	    	//Remove the selected image from gridPane and step.
	    	int col=imageGridPane.getColumnConstraints().size();
	    	int row=imageGridPane.getRowConstraints().size();
	    	for(int i=0;i<row;i++) {
	    		for(int j=0;j<col;j++) {
	    			if(imageGridPane.getChildren().get(i*col +j)==actualImage) {
	    				imageGridPane.getChildren().remove(i*col+j);
	    				nextCol=j;
	    				nextRow=i;
	    				break;
	    			}
	    			
	    		}
	    	}
	    	//then close the image panel
	    	closeImage();
	    }
	    @FXML
	    private void removeStepHandler() {
	    	//then remove the selected step from the list and the button bar.
	    	Alert confirmAlert=new Alert(AlertType.CONFIRMATION);
	    	confirmAlert.setTitle("Delete step confirmation");
	    	confirmAlert.setHeaderText("Are you sure to remove this step?");
	    	confirmAlert.setContentText("if you remove this step then all the information are deleted");
	    	confirmAlert.initOwner(this.mainPane.getScene().getWindow());
	    	confirmAlert.showAndWait();
	    	ButtonType result=confirmAlert.getResult();
	    	if(result.getButtonData()==ButtonData.OK_DONE) {
	    		//then remove the step
	    		if(stepsBar.getButtons().size()>0) {
	    			//Remove the step from the list and delete the gridPane associated and then also from the buttonbar
	    			stepByDay.get(dayNumber).remove(stepNumber);
	    			dayImagePane.get(dayNumber).remove(stepNumber);
	    			stepsBar.getButtons().remove(stepsBar.getButtons().size()-stepNumber-1);
	    			//if stepsBar has some buttons then fire on the last else remove all the information in the field.
	    			if(stepsBar.getButtons().size()>0) {
	    				Button button=(Button)stepsBar.getButtons().get(0);
	    				button.fire();
	    			}
	    			else {
	    				//We can't have a day without steps so create a new step and fire on it
	    				addStepButton();
	    			}
	    		}
	    		else {
	    			errorDayPanel.setVisible(true);
	    			//then run a thread that wait for seconds and later remove the error message.
	    		}
	    	}
	    }
}
