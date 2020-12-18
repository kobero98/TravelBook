package main.java.travelbook.view;
import java.io.IOException;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.DialogPane;
import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.application.Platform;
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
	private Integer dayNumber;
	private List<List<StepBean>> stepByDay=new ArrayList<List<StepBean>>();
	private TravelBean travel;
	private List<List<ImageGridPane>> dayImagePane=new ArrayList<List<ImageGridPane>>();
	private int stepNumber,nextRow,nextCol;
	private double standardImageHeight=89.34,standardImageWidth=89.34;
	private Image presentationImage;
	private long numOfDays;
	private AutocompleteTextField searchText;
	private ImageView actualImage;
	@FXML
	private void initialize() {
		//set travel and the first day and the first step by default.
		imageGridPane=new ImageGridPane();
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
	public class ImageGridPane extends GridPane{
		//GridPane with a matrix that show if an entry (row,col) is empty or not. 
		//If is empty positions[row][col]==1
		private List<List<Integer>> positions=new ArrayList<List<Integer>>();
		
		public ImageGridPane() {
			super();
			//setup to a gridPane with 5 col and 1 row
			nextCol=0;
    		nextRow=0;
    		this.setPrefHeight(standardImageHeight);
    		this.setPrefWidth(standardImageWidth*5);
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
    		this.getColumnConstraints().addAll(column1,column2,column3,column4,column5);
    		this.setLayoutX(35);
    		this.setLayoutY(520);
    		RowConstraints row1=new RowConstraints();
    		row1.setPrefHeight(standardImageHeight);
    		this.getRowConstraints().add(row1);
			positions.add(new ArrayList<Integer>());
			for(int i=0;i<5;i++) {
				positions.get(0).add(1);
			}
			
		}
		@Override
		public void add(Node node,int col,int row) {
			super.add(node, col,row);
			positions.get(row).set(col, 0);
		}
		public void updateRow() {
			positions.add(new ArrayList<Integer>());
			for(int i=0;i<5;i++) {
				positions.get(positions.size()-1).add(1);
			}
		}
		public void remove(int row,int col) {
			//set valid the entry
			this.positions.get(row).set(col, 1);
		}
		public boolean isValid(int row,int col) {
			//return ture if the position is valid
			if(this.positions.get(row).get(col)==1) {
				return true;
			}
			return false;
		}
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
	    	travel=new TravelBean();
	    	travel.setShare(true);
	    	List<Object> listOfErrors=new ArrayList<Object>();
	    	travel.setListStep(new ArrayList<StepBean>());
	    	progressPane.setVisible(true);
	    	progressPane.setOpacity(0.9);
	    	internalPane.setOpacity(0.1);
	    	this.closeProgressBar.setVisible(false);
	    	// sostituire questo codice con invio al controller applicativo dei dati.
	    	if(!travelName.getText().isEmpty()) {
	    		travel.setNameTravel(travelName.getText());
	    		incrementProgress();
	    	}
	    	else {
	    		//Then show an error message and mostra rosso il bordo di name travel
	    		//termina
	    		listOfErrors.add(travelName);
	    	}
	    	if(!travelDescription.getText().isEmpty()) {
	    		//manca l'attributo nel travel bean
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(travelDescription);
	    	}
	    	if(viewPresentation.getImage()!=null) {
	    		travel.setPathBackground(viewPresentation.getImage());
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(viewPresentation);
	    	}
	    	CheckBox element;
	    	List<String> filtri=new ArrayList<String>();
	    	for(int i=0;i<filterPane.getChildren().size();i++) {
	    		element=(CheckBox)filterPane.getChildren().get(i);
	    		if(element.isSelected()) {
	    			filtri.add(element.getText());
	    		}
	    	}
	    	if(filtri.size()>0) {
	    		travel.setType(filtri);
	    		incrementProgress();
	    	}
	    	else {
	    		listOfErrors.add(filterPane);
	    	}
	    	//then take every steps for each day
	    	List<StepBean> incompleteSteps=new ArrayList<StepBean>();
	    	travel.setListStep(new ArrayList<StepBean>());
	    	for(int day=0;day<this.stepByDay.size();day++) {
	    		List<StepBean> steps=stepByDay.get(day);
	    		for(int stepN=0;stepN<steps.size();stepN++) {
	    			//for each step
	    			StepBean step=steps.get(stepN);
	    			if(step.getPlace()!=null && step.getDescriptionStep()!=null) {
	    			if(!step.getPlace().isEmpty() && !step.getDescriptionStep().isEmpty()) {
	    				step.setListPhoto(new ArrayList<Image>());
	    				List<Node> pics=dayImagePane.get(day).get(stepN).getChildren();
	    				for(int picN=0;picN<pics.size();picN++) {
	    					ImageView foto= (ImageView)pics.get(picN);
	    					step.getListPhoto().add(foto.getImage());
	    				}
	    				travel.getListStep().add(step);
	    			}
	    			else {
	    				incompleteSteps.add(step);
	    				
	    			}
	    		}
	    		}
	    		
	    	}
	    	if(!incompleteSteps.isEmpty()) {
	    		//Vorrei gestire questa situazione in maniera diversa mostrando dove sono
	    		//gli errori all'utente ma non so bene come
	    		//Per ora lo avverto che ci sono
	    		progressPane.setOpacity(0);
    			internalPane.setOpacity(1);
    			progressPane.setVisible(false);
	    		System.out.println("Step mancanti");
	    		Alert alert=new Alert(AlertType.ERROR);
	    		alert.setHeaderText("Incomplete steps found");
	    		alert.setContentText("There are some incomplete steps, complete them and then retry");
	    		alert.setTitle("Error post message");
	    		alert.showAndWait();
	    		
	    	}
	    		if(!listOfErrors.isEmpty()) {
	    			System.out.println("Errori nel resto");
	    			for(int i=0;i<listOfErrors.size();i++) {
	    				Node node=(Node) listOfErrors.get(i);
	    				node.setStyle("-fx-border-color: #FF0000");
	    			}
	    			progressPane.setOpacity(0);
	    			internalPane.setOpacity(1);
	    			progressPane.setVisible(false);
	    		}
	    	if(listOfErrors.isEmpty()&&incompleteSteps.isEmpty()) {
	    		//TODO call the controller applicativo and make a thread that wait for it.
	    		new Thread(()->{
	    			
	    			Platform.runLater(()->{
	    				progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	    			});
	    			//Call the controller applicativo
	    			try {
	    				//Sleep set to try how work with progress bar but it is not necessary
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			System.out.println("Il controller applicativo ha finito");
	    			Platform.runLater(()->{
	    				progressBar.setProgress(1);
	    				//when done activate the close button
	    		    	closeProgressBar.setVisible(true);
	    			});
	    		}).start();
	    		
	    	}	
	    }
	    @FXML
	    private void saveAsDraftHandler() {
	    	DateUtil util=new DateUtil();
	    	travel=new TravelBean();
	    	travel.setNameTravel(travelName.getText());
	    	travel.setDescriptionTravel(this.travelDescription.getText());
	    	String startDate=util.toString(this.startDate.getValue());
	    	String endDate=util.toString(this.endDate.getValue());
	    	travel.setStartTravelDate(startDate);
	    	travel.setEndTravelDate(endDate);
	    	travel.setPathBackground(this.viewPresentation.getImage());
	    	travel.setShare(false);
	    	List<String> filtri=new ArrayList<String>();
	    	CheckBox element;
	    	for(int i=0;i<filterPane.getChildren().size();i++) {
	    		element=(CheckBox)filterPane.getChildren().get(i);
	    		if(element.isSelected()) {
	    			filtri.add(element.getText());
	    		}
	    	}
	    	travel.setType(filtri);
	    	List<StepBean> steps;
	    	travel.setListStep(new ArrayList<StepBean>());
	    	for(int day=0;day<stepByDay.size();day++) {
	    		steps=this.stepByDay.get(day);
	    		for(int step=0;step<steps.size();step++) {
	    			//modify number of day
	    			travel.getListStep().add(steps.get(step));
	    		}
	    	}
	    	//then call the controller and send data
	    }
	    private void incrementProgress() {
	    	this.progressBar.setProgress(this.progressBar.getProgress()+ 0.001);
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
	    	//redirect to the view of the travel
	    }
	    @FXML
	    private void multipleChoosHandler() {
	    	if(dayNumber>=0) {
	    	FileChooser dialog=new FileChooser();
	    	//Visto che place dovrebbe essere una stringa lunga forse meglio solo la città
	    	dialog.setTitle("Choose some photos for this step" + stepByDay.get(dayNumber).get(stepNumber).getPlace());
	    	dialog.getExtensionFilters().add(new ExtensionFilter("Image","*.png","*.jpg"));
	    	List<File> files = dialog.showOpenMultipleDialog(mainPane.getScene().getWindow());
	    	if(files!=null) {
	    	Image im;
	    	ImageView view;
	    	double percentuale=(double)1/(double)files.size();
	    	
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
	    		while(!imageGridPane.isValid(nextRow,nextCol)) {
	    			nextCol++;
	    			if(nextCol==5) {
	    				nextCol=0;
	    				nextRow++;
	    			}
	    		}
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
	    		int numRow=imageGridPane.getRowConstraints().size();
	    		imageGridPane.getRowConstraints().add(new RowConstraints(imageGridPane.getRowConstraints().get(0).getPrefHeight()));
	    		imageGridPane.setPrefHeight(imageGridPane.getHeight()/numRow + imageGridPane.getHeight());
	    		nextRow++;
	    		imageGridPane.updateRow();
	    	}
	    }
	    @FXML
	    private void addStepButton() {
	    	if(dayNumber>=0) {
	    	dayImagePane.get(dayNumber).add(new ImageGridPane());
	    	stepNumber++;
	    	System.out.println("Step numero: "+stepNumber);
	    	StepBean step=new StepBean();
	    	
	    	step.setGroupDay(dayNumber);
	    	stepByDay.get(dayNumber).add(step);
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
	    
	    private void changeListOfDays() {
	    	if(numOfDays>0) {
	    		searchText.unblock();
	    		this.stepsScroll.setVisible(true);
	    		this.arrowImage.setVisible(true);
	    		this.nowGiveUs.setVisible(true);
	    		this.youAreEditing.setVisible(true);
	    		this.dayBox.setVisible(true);
	    	}
	    	/*for(Integer i=0;i<numOfDays;i++) {
	    		System.out.println("Aggiunto il giorno: "+i);
	    		Integer num=i+1;
	    		dayBox.getItems().add((num).toString());
	    		//Add one entry for each day added
	    		stepByDay.add(new ArrayList<>());
	    		StepBean step=new StepBean();
	    		
	    		step.setGroupDay(stepByDay.size()-1);
	    		System.out.println("Aggiunto step per giorno: "+step.getGroupDay());
	    		
	    		stepByDay.get(stepByDay.size()-1).add(step);
	    	}*/
	    	if(numOfDays>dayImagePane.size()) {
	    		Integer x=dayImagePane.size();
	    		for(Integer i=x;i<numOfDays;i++) {
	    			Integer num=i+1;
		    		dayBox.getItems().add(num.toString());
	    			dayImagePane.add(new ArrayList<ImageGridPane>());
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
	    		/*Alert alert=new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Delete days confirmation");
	    		alert.setHeaderText("Richiesta implicita di cancellazione");
	    		
	    		alert.setContentText("Con questa operazione saranno rimossi i dati corrispondenti ad alcuni giorni, sei sicuro di voler continuare?" );
	    		alert.initOwner(this.mainPane.getScene().getWindow());
	    		alert.showAndWait();*
	    		ButtonType result=alert.getResult();
	    		if(result.getButtonData()==ButtonData.OK_DONE) {*/
	    		int deleted=0;
	    		for(int i=y;i<x;i++) {
	    			dayBox.getItems().remove(i-deleted);
	    			dayImagePane.remove(i-deleted);
	    			stepByDay.remove(i-deleted);
	    			deleted++;
	    		}
	    		//}
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
	    				imageGridPane.remove(i, j);
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
	    	if(stepsBar.getButtons().size()>0) {
	    	Alert confirmAlert=new Alert(AlertType.CONFIRMATION);
	    	confirmAlert.setTitle("Delete step confirmation");
	    	confirmAlert.setHeaderText("Are you sure to remove this step?");
	    	confirmAlert.setContentText("if you remove this step then all the information are deleted");
	    	confirmAlert.initOwner(this.mainPane.getScene().getWindow());
	    	confirmAlert.showAndWait();
	    	ButtonType result=confirmAlert.getResult();
	    	if(result.getButtonData()==ButtonData.OK_DONE) {
	    		//then remove the step
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
	    	}
	    	else {
    			errorDayPanel.setVisible(true);
    			//then run a thread that wait for seconds and later remove the error message.
    		}
	    }
}
