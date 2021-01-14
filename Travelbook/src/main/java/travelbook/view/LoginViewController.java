package main.java.travelbook.view;
import java.io.IOException;
import javafx.scene.control.ButtonType;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import main.java.travelbook.MainApp;
import main.java.travelbook.controller.ControllerLogin;
import main.java.travelbook.controller.ExceptionLogin;
import main.java.travelbook.util.DateUtil;
import main.java.travelbook.view.animation.OpacityAnimation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import main.java.travelbook.model.bean.RegistrationBean;
import main.java.travelbook.model.bean.UserBean;
public class LoginViewController {
	private RegistrationBean userToBeRegister;
	private String codeOfreg;
	@FXML
	private Pane codeConfirmPane;
	@FXML
	private TextField codeTextField;
	@FXML
	private Button confirmButton;
	@FXML
	private Button closeConfirmationButton;
	@FXML
	private Button loginButton;
	@FXML
	private Button closeRegister;
	@FXML
	private Pane registerPane;
	@FXML
	private Button registerButton;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane myAnchor;
	@FXML
	private Pane loginPane;
	@FXML
	private StackPane image;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField pswdField;
	@FXML
	private Hyperlink gotoFacebook;
	@FXML
	private Button facebookLogin;
	@FXML
	private Hyperlink forgotEP;
	@FXML
	private StackPane faceStack;
	@FXML
	private ImageView faceImage;
	@FXML
	private ImageView backgroundImage;
	private BorderPane mainPane;

	@FXML
	private Label error;
	//Register Pane elements
	@FXML
	private TextField email1;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private PasswordField pswd1;
	@FXML
	private PasswordField pswdRepeat;
	@FXML
	private Button signUp;
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton other;
	@FXML
	private Label genere;
	@FXML
	private DatePicker date;
	@FXML
	private TextField username;
	@FXML
	private Label registerError;
	@FXML
	private void initialize() {
		//Add some listener for focused property
		name.focusedProperty().addListener((observable,oldValue,newValue)->{
			//se lasci o entri nel text field il primo carattere va in maiuscolo
			String value=name.getText();
			if(!value.isEmpty()) {
				name.setText(value.substring(0,1).toUpperCase()+value.substring(1));
			}
				
		});
		surname.focusedProperty().addListener((observable,oldValue,newValue)->{
			String value=surname.getText();
			if(!value.isEmpty()) {
				surname.setText(value.substring(0,1).toUpperCase()+value.substring(1));
			}
				
		});
		email1.focusedProperty().addListener((observable,oldValue,newValue)->{
			//se entri lasci il text field tutti i caratteri vanno in minuscolo
			String value=email1.getText();
			if(!value.isEmpty()) {
				email1.setText(value.toLowerCase());
			}
				
		});
	}
	public void setMain(BorderPane main) {

		this.mainPane=main;
		//then some resize logic

		this.mainPane.heightProperty().addListener((observable,oldValue,newValue)->{
			AnchorPane anchor=(AnchorPane)mainPane.getCenter();
			DoubleProperty fontSize = new SimpleDoubleProperty(this.mainPane.getHeight()*20/720); // font size in pt
			this.mainPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
			StackPane title=(StackPane)mainPane.getTop();
			title.setPrefHeight(mainPane.getHeight()*94/720);
			anchor.setPrefHeight(mainPane.getHeight()*625/720);
		});

		this.mainPane.widthProperty().addListener((observable,oldValue,newValue)->{
			AnchorPane anchor=(AnchorPane)mainPane.getCenter();
			anchor.setPrefWidth(mainPane.getWidth());
		});
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
			double height=this.mainAnchor.getHeight();
			myAnchor.setPrefHeight(this.mainAnchor.getHeight());
			registerPane.setPrefHeight(610*height/625);
			registerPane.setLayoutY(0);
			loginPane.setPrefHeight(this.mainAnchor.getHeight()*479/625);
			loginPane.setLayoutY(this.mainAnchor.getHeight()*80/625);
			forgotEP.setPrefHeight(height*48/625);
			forgotEP.setLayoutY(height*550/625);
			image.setPrefHeight(height*594/625);
			image.setLayoutY(height*20/625);
			backgroundImage.setFitHeight(height*594/625);
			error.setPrefHeight(this.mainAnchor.getHeight()*36/625);
			error.setLayoutY(height*20/625);
			emailField.setPrefHeight(height*63/625);
			emailField.setLayoutY(height*59/625);
			pswdField.setPrefHeight(height*63/625);
			pswdField.setLayoutY(height*171/625);
			loginButton.setPrefHeight(height*70/625);
			loginButton.setLayoutY(height*293/625);
			registerButton.setPrefHeight(height*70/625);
			registerButton.setLayoutY(height*293/625);
			gotoFacebook.setPrefHeight(height*108/625);
			gotoFacebook.setLayoutY(381*height/625);
			faceStack.setPrefHeight(height*70/625);
			faceStack.setLayoutY(height*403/625);
			faceImage.setFitHeight(height*70/625);
			facebookLogin.setPrefHeight(height*70/625);
			name.setPrefHeight(height*61/625);
			name.setLayoutY(height*78/625);
			surname.setPrefHeight(height*61/625);
			surname.setLayoutY(height*78/625);
			email1.setPrefHeight(height*61/625);
			email1.setLayoutY(height*148/625);
			username.setPrefHeight(height*61/625);
			username.setLayoutY(height*210/625);
			pswd1.setPrefHeight(61*height/625);
			pswd1.setLayoutY(274*height/625);
			pswdRepeat.setPrefHeight(61*height/625);
			pswdRepeat.setLayoutY(height*341/625);
			date.setPrefHeight(46*height/625);
			date.setLayoutY(height*437/625);
			genere.setPrefHeight(36*height/625);
			genere.setLayoutY(426*height/625);
			male.setPrefHeight(26.8*height/625);
			male.setLayoutY(428*height/625);
			female.setPrefHeight(26.8*height/625);
			female.setLayoutY(465*height/625);
			other.setPrefHeight(26.8*height/625);
			other.setLayoutY(502*height/625);
			signUp.setPrefHeight(44*height/625);
			signUp.setLayoutY(532*height/625);
			closeRegister.setPrefHeight(38*height/625);
			closeRegister.setLayoutY(15*height/625);
			registerError.setPrefHeight(36*height/625);
			registerError.setLayoutY(40*height/625);
		});
		
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			double width=this.mainAnchor.getWidth();
			myAnchor.setPrefWidth(width);
			loginPane.setPrefWidth(width*550/1280);
			loginPane.setLayoutX(width*20/1280);
			image.setPrefWidth(600*width/1280);
			backgroundImage.setFitWidth(600*width/1280);
			image.setLayoutX(625*width/1280);
			forgotEP.setPrefWidth(365.6*width/1280);
			forgotEP.setLayoutX(200*width/1280);
			error.setPrefWidth(width*197/1280);
			error.setLayoutX(160*width/1280);
			emailField.setPrefWidth(448*width/1280);
			emailField.setLayoutX(50*width/1280);
			pswdField.setPrefWidth(448*width/1280);
			pswdField.setLayoutX(50*width/1280);
			loginButton.setPrefWidth(169.42*width/1280);
			loginButton.setLayoutX(329*width/1280);
			registerButton.setPrefWidth(169.42*width/1280);
			registerButton.setLayoutX(128*width/1280);
			faceStack.setPrefWidth(70*width/1280);
			faceStack.setLayoutX(379*width/1280);
			faceImage.setFitWidth(70*width/1280);
			facebookLogin.setPrefWidth(70*width/1280);
			
			
			registerPane.setPrefWidth(644*width/1280);
			registerPane.setLayoutX(293*width/1280);
			name.setPrefWidth(271*width/1280);
			name.setLayoutX(26*width/1280);
			surname.setPrefWidth(271*width/1280);
			surname.setLayoutX(338*width/1280);
			email1.setPrefWidth(584*width/1280);
			email1.setLayoutX(25*width/1280);
			username.setPrefWidth(584*width/1280);
			username.setLayoutX(26*width/1280);
			pswd1.setPrefWidth(width*584/1280);
			pswd1.setLayoutX(width*26/1280);
			pswdRepeat.setPrefWidth(width*584/1280);
			pswdRepeat.setLayoutX(26*width/1280);
			date.setPrefWidth(width*221/1280);
			date.setLayoutX(width*26/1280);
			genere.setPrefWidth(116.7*width/1280);
			genere.setLayoutX(290*width/1280);
			male.setPrefWidth(79.34*width/1280);
			male.setLayoutX(413*width/1280);
			female.setPrefWidth(103*width/1280);
			female.setLayoutX(413*width/1280);
			other.setPrefWidth(86.01*width/1280);
			other.setLayoutX(413*width/1280);
			signUp.setPrefWidth(125*width/1280);
			signUp.setLayoutX(255*width/1280);
			registerError.setPrefWidth(113*width/1280);
			registerError.setLayoutX(191*width/1280);
			closeRegister.setPrefWidth(200*width/1280);
			closeRegister.setLayoutX(400*width/1280);
		});
		
	}
	@FXML
	private void loginButtonHandler() {
		
		String localUsername;
		String pswd;
		UserBean user = null;
		if(error.isVisible()){
			error.setVisible(false);
		}
		localUsername=emailField.getText();
		pswd=pswdField.getText();
		
			try {
				user=ControllerLogin.getInstance().signIn(localUsername, pswd);
			} catch (SQLException e1) {
				error.setVisible(true);
				error.setText("Errore nel LogIn");
			} catch (ExceptionLogin e1) {
				error.setVisible(true);
				error.setText(e1.getMessage());
			}
		
		if(user!=null) {

			try {
			        MenuBar.getInstance().moveToExplore(this.mainPane);

			}catch(IOException e) {
				e.printStackTrace();
			}

			}
	}
	@FXML
    private void signUpHandler() {
		if(error.isVisible()) {
			error.setVisible(false);
		}
    	OpacityAnimation anim=new OpacityAnimation();
    	anim.setBackTop(myAnchor, registerPane);
    	anim.setLimits(0.1, 0.9);
    	registerPane.setVisible(true);
    	anim.start();
    }
	@FXML
	private void closeRegisterHandler() {
		OpacityAnimation anim=new OpacityAnimation();
		anim.setBackTop(registerPane, myAnchor);
		anim.setLimits(0, 1);
		anim.start();
		registerPane.setVisible(false);
	}
	@FXML
	private void goToFacebook() {
		if(error.isVisible())
			error.setVisible(false);
		String redirect="https://www.facebook.com/connect/login_success.html";
		 String  redirecturi="";
		try {
			redirecturi=URLEncoder.encode(redirect,"UTF8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String app_id="1332279647110748";
		String request="https://www.facebook.com/v3.2/dialog/oauth?client_id="+app_id+"&response_type=token"+"&redirect_uri="+redirecturi+ "&state=\'{st=state123abc,ds=123456789}\'";
		WebView view=new WebView();
		WebEngine engine=view.getEngine();
		engine.locationProperty().addListener((observable,oldValue,newValue)->{
			String url=engine.getLocation();
			try {
			if (url.startsWith(redirect)) {
				System.out.println("Tutto ok");
				//Ask controller applicativo affinchè chieda a fb i dati utente
			}
			}catch(Exception e) {
				
			}
		});
		engine.load(request);
		this.mainAnchor.getChildren().add(view);
	}
	@FXML
	private void goToFacebookButton() {
		goToFacebook();
	}
	@FXML
	private void forgot() {
		if(error.isVisible())
			error.setVisible(false);
		//Gestisci aprendo link sul browser che porta alla nostra pagina di gestione credenziali cosi la scriviamo una volta sola in tecnologia web

	}
	@FXML
	private void registrami() {
		//Esegue la registrazione effettiva
		boolean errore=false;// per vedere se mancano dati
		
		String email=email1.getText();
		String username=this.username.getText();
		if(email.isEmpty()) {
			errore=true;
		}
		String nome=this.name.getText();
		if (nome.isEmpty()) {
			errore=true;
		}
		String cognome= this.surname.getText();
		if(cognome.isEmpty()) {
			errore=true;
		}
		String pswd=this.pswd1.getText();
		if(pswd.isEmpty()) {
			errore=true;
		}
		String ripetiPswd=this.pswdRepeat.getText();
		if(ripetiPswd.isEmpty() || !(ripetiPswd.equals(pswd))) {
			errore=true;
		}
		LocalDate data=null;
		if(date.getValue()!=null) 
			data=date.getValue();
		else {
			errore=true;
		}
		String gender=null;
		if(male.isSelected()) {
			gender="m";
		}
		if(female.isSelected()) {
			gender= "f";
		}
		if(other.isSelected()) {
			gender="o";
		}
		if(!errore && gender!=null) {
          RegistrationBean user=new RegistrationBean();
          user.setUsername(username);
          user.setEmail(email);
          user.setPassword(pswd);
          user.setBirtdate(Date.valueOf(data));
          user.setSurname(cognome);
          user.setName(nome);
          user.setGender(gender);
          this.userToBeRegister=user;
          this.codeOfreg=ControllerLogin.getInstance().CalcoloRegistration(email);
          this.showConfirmCode();   
		}
		else {
			//ora stampa messagggio di errore
			// da aggiungere in base al messaggio della eccezione
			registerError.setText("Errore nella registrazione");
		}
	}
	private void saveRegistration() {
		//Chiama il controller e passa i dati
	}
	private void showConfirmCode() {
		this.codeConfirmPane.setVisible(true);
		this.registerPane.setVisible(false);
		try {
		ControllerLogin.getInstance().signUp(this.userToBeRegister);
		//Subito dopo esegue il login
		ControllerLogin.getInstance().signIn(userToBeRegister.getUsername(), this.userToBeRegister.getPassword());
		}catch(ExceptionLogin e1) {
			error.setVisible(true);
			error.setText(e1.getMessage());
		}
		catch(Exception e) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setHeaderText("Several System Error");
			alert.setContentText("Something went wrong try again");
			alert.showAndWait();
		}
	}
	@FXML
	private void confirmCode() {
		String text=this.codeTextField.getText();
		if(text.equals(codeOfreg)) {
			saveRegistration();
		}
		else {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setHeaderText("Registration Error");
		alert.setContentText("Il Codice inserito non è corretto la registrazione è stata annullata");
		alert.showAndWait();
		this.codeConfirmPane.setVisible(false);
		this.closeRegisterHandler();
		}
	}
	@FXML
	private void closeConfirmPaneHandler() {
		//Da cambiare e farlo uguale al save exit warning dell'add 
		Alert saveAlert=new Alert(AlertType.CONFIRMATION);
		 saveAlert.setTitle("Incomplete registration");
		 saveAlert.setHeaderText("You don't confirm your registration");
		 saveAlert.setContentText("Se esci perderai tutte le informazioni inserite" );
		 ButtonType exit=new ButtonType("Exit");
		 ButtonType notExit=new ButtonType("Don't exit");
		 saveAlert.getButtonTypes().clear();
		 saveAlert.getButtonTypes().addAll(exit,notExit);
		 Optional<ButtonType> results=saveAlert.showAndWait();
		 if(results.isPresent()) {
			 if(results.get()==exit) {
				 this.codeConfirmPane.setVisible(false);
				 this.closeRegisterHandler();
			 }
		 }
		 
	}

}
