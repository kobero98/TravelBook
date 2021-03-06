package main.java.travelbook.view;

import javafx.scene.control.ButtonType;


import java.net.URLEncoder;
import java.time.LocalDate;
import java.sql.Date;import java.util.Optional;

import javafx.scene.web.WebView;

import javafx.scene.web.WebEngine;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceBox;
import java.util.Locale;

import main.java.exception.DBException;
import main.java.exception.LoginPageException;
import main.java.exception.MalformedEmailException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.ControllerLogin;
import main.java.travelbook.view.animation.OpacityAnimation;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import main.java.travelbook.model.bean.RegistrationBean;
import main.java.travelbook.model.bean.UserBean;

public class LoginViewController {
	private String mode="";
	private RegistrationBean userToBeRegister;
	private String codeOfreg;
	@FXML
	private PasswordField newP;
	@FXML
	private PasswordField newPC;
	@FXML
	private Pane changePassword;
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
	private boolean firstTime=false;
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
	private ChoiceBox<String> nations;
	@FXML
	private Label registerError;
	@FXML
	private void initialize() {
		String[] countryCodes=Locale.getISOCountries();
		for(String cc: countryCodes) {
			Locale obj = new Locale("", cc);
			this.nations.getItems().add(obj.getDisplayCountry());
		}
		
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
	private void setAlert() {
		this.mainPane.getScene().getWindow().setOnCloseRequest(e->{
			Alert alert=new TriggerAlert().triggerAlertCreate("Are you sure you want to quit?","help");
			alert.setHeaderText("Exit request");
			alert.showAndWait();
			ButtonType result=alert.getResult();
			if(result.getButtonData()==ButtonData.OK_DONE) {
				if(MenuBar.getInstance().getMyThread()!=null)
					MenuBar.getInstance().getMyThread().kill();	
			}
			else {
				e.consume();
			}
		});
	}
	public void setMain(BorderPane main) {

		this.mainPane=main;
		//then some resize logic
		setAlert();
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
			error.setPrefWidth(width*450/1280);
			error.setLayoutX(50*width/1280);
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
		this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
		this.mainAnchor.setPrefWidth(mainPane.getWidth());
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
				ControllerLogin controller=new ControllerLogin();
				user=controller.signIn(localUsername, pswd);
				user.setFirstTime(firstTime);
			} catch (LoginPageException e1) {
				error.setVisible(true);
				error.setText(e1.getMessage());
			} 
		
		if(user!=null) {
			try {
					MenuBar.getInstance().setUser(user);
			        MenuBar.getInstance().moveToExplore(this.mainPane);

			}catch(MissingPageException e) {
				e.exit();
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
		if(error.isVisible()) error.setVisible(false);
		String redirect="https://www.facebook.com/connect/login_success.html";
		String  redirecturi="";
		try {
			redirecturi=URLEncoder.encode(redirect,"UTF8");
			
		} catch (Exception e) {
			new TriggerAlert().triggerAlertCreate("feature temporarily unavaiable", "err");
		}
		String appId="1332279647110748";
		String request="https://www.facebook.com/v9.0/dialog/oauth?client_id="+appId+"&response_type=token"+"&redirect_uri="+redirecturi+ "&state=\'{st=state123abc,ds=123456789}\'&auth_type=rerequest&scope=email,user_photos,user_gender,user_birthday";
		WebView view=new WebView();
		WebEngine engine=view.getEngine();
					engine.locationProperty().addListener((observable,oldValue,newValue)->{
						String url=engine.getLocation();
						try {
							if (url.startsWith(redirect)) {
								String accessToken=url.substring(redirect.length());
								this.mainAnchor.getChildren().remove(view);
								ControllerLogin controller=new ControllerLogin();
								UserBean u=controller.facebookLogin(accessToken);
								
								MenuBar.getInstance().setUser(u);
						      	MenuBar.getInstance().moveToExplore(this.mainPane);
							}
							}catch(Exception e) {
								error.setVisible(true);
								error.setText(e.getMessage());
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
		if(this.emailField.getText()==null || this.emailField.getText().isEmpty())
			new TriggerAlert().triggerAlertCreate("Inserisci l'email nel campo email e ti invieremo un codice", "warn").showAndWait();
		else {
			ControllerLogin con=new ControllerLogin();
			try {
			this.codeOfreg=con.calcoloRegistration(this.emailField.getText());
			this.mode="pswd";
			this.codeConfirmPane.setVisible(true);
			this.codeConfirmPane.setOpacity(1);
			this.myAnchor.setOpacity(0);
			}catch(MalformedEmailException e) {
				new TriggerAlert().triggerAlertCreate("Something went wrong", "err").showAndWait();
			}
		}
		//Gestisci aprendo link sul browser che porta alla nostra pagina di gestione credenziali cosi la scriviamo una volta sola in tecnologia web

	}
	private void sendEmailReg(String email) {
		new Thread(()->{
      	  ControllerLogin controller=new ControllerLogin();
      try {
        this.codeOfreg=controller.calcoloRegistration(email);
        Platform.runLater(()->{
      	  this.codeConfirmPane.setVisible(true);
    		this.registerPane.setVisible(false);
    		this.mode="reg";
        });
        }catch(MalformedEmailException e) {
      	  new TriggerAlert().triggerAlertCreate("something went wrong unable to send email", "err").showAndWait();
      	  
        }}).start();
	}
	@FXML
	private void registrami() {
		//Esegue la registrazione effettiva
		boolean errore=false;// per vedere se mancano dati
		
		String email=email1.getText();
		String myUsername=this.username.getText();
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
		if(nations.getValue().isEmpty()) {
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
          user.setUsername(myUsername);
          user.setEmail(email);
          user.setPassword(pswd);
          user.setBirtdate(Date.valueOf(data));
          user.setSurname(cognome);
          user.setName(nome);
          user.setGender(gender);
          user.setNazionalita(nations.getValue());
          this.userToBeRegister=user;
          this.sendEmailReg(email);
		
		}
		else {
			registerError.setText("Errore nella registrazione");
		}
	}
	
	private void saveRegistration(){
		try {
			ControllerLogin controller=new ControllerLogin();
			controller.signUp(this.userToBeRegister);
			emailField.setText(userToBeRegister.getUsername());
			pswdField.setText(userToBeRegister.getPassword());
			firstTime=true;
			loginButtonHandler();
			
			}catch(Exception e) {

				new TriggerAlert().triggerAlertCreate("Several system error", "err").showAndWait();
			}
	}

	
	@FXML
	private void confirmCode() {
		String text=this.codeTextField.getText();
		if(text.equals(codeOfreg)) {
			if(mode.equals("reg"))
				saveRegistration();
			else {
				this.codeConfirmPane.setVisible(false);
				this.changePassword.setVisible(true);
				this.myAnchor.setOpacity(0);
				this.changePassword.setOpacity(1);
			}
		}
		else {
			new TriggerAlert().triggerAlertCreate("Wrong code, try again", "err").showAndWait();
			this.codeConfirmPane.setVisible(false);
			this.closeRegisterHandler();
		}
	}
	@FXML
	private void closePasswordHandler() {
		this.myAnchor.setOpacity(1);
		this.changePassword.setOpacity(0);
		this.changePassword.setVisible(false);
	}
	@FXML
	private void confirmPasswordHandler() {
		if(this.newP.getText()!=null && !this.newP.getText().isEmpty() &&
			this.newPC.getText()!=null && !this.newPC.getText().isEmpty() && this.newPC.getText().equals(this.newP.getText())) {
				
					ControllerLogin con=new ControllerLogin();
					try {
					con.changeMyPassword(this.emailField.getText(), this.newP.getText());
					}catch(DBException e) {
						new TriggerAlert().triggerAlertCreate("Something went wrong try again later", "err").showAndWait();
						
					}
					this.changePassword.setVisible(false);
					this.myAnchor.setOpacity(1);
		}
	}
	@FXML
	private void closeConfirmPaneHandler() {
		 Alert saveAlert=new TriggerAlert().triggerAlertCreate("You'll lose given information upon exit", "help");
		 saveAlert.setTitle("Incomplete registration");
		 saveAlert.setHeaderText("You haven't confirmed your registration");
		 ButtonType exit=new ButtonType("Exit");
		 ButtonType notExit=new ButtonType("Don't exit");
		 saveAlert.getButtonTypes().clear();
		 saveAlert.getButtonTypes().addAll(exit,notExit);
		 Optional<ButtonType> results=saveAlert.showAndWait();
		 if(results.isPresent() && results.get()==exit) {
			this.codeConfirmPane.setVisible(false);
			this.closeRegisterHandler();
		 }
		 
	}

}
