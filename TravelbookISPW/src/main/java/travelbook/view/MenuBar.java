package main.java.travelbook.view;
import javafx.fxml.FXMLLoader;
import main.java.travelbook.util.Chat;
import java.util.List;

import main.java.exception.MissingPageException;
import main.java.travelbook.model.bean.UserBean;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import main.java.travelbook.util.MessagePollingThread;
import main.java.travelbook.util.Observable;
import main.java.travelbook.util.Observer;
import main.java.travelbook.util.ProfilePollingThread;

import java.util.ArrayList;
public class MenuBar extends Observable implements Observer{
	//This class want to manage MenuBar operation. Use these methods in the button handler.
	private FXMLLoader loader;
	private  boolean notify = false;  //this information will be in logged user
	private static MenuBar istance=new MenuBar();
	private MenuBar() {
		
	}
	@Override
	public void update(Observable chat) {
		notify=true;
		istance.setChanged();
	}
	@Override
	public void update(Observable chat, Object arg) {
		istance.update(chat);
	}
	public  void newChat(Chat chat) {
		myChat.add(chat);
		chat.addObserver(istance);
	}
	private  MessagePollingThread myThread;
	private ProfilePollingThread mySecondThread;
	private  List<Chat> myChat=new ArrayList<>();
	public  List<Chat> getMyChat() {
		return myChat;
	}
	
	private  UserBean loggedUser;
	public  void  setUser(UserBean user) {
		loggedUser=user;
	}
	private int travelId = 0;
	public void setIdTravel(int id) {
		this.travelId=id;
	}
	public int getTravelId() {
		return this.travelId;
	}
	
	private int userId = 0;
	public void setIdUser(int id) {
		this.userId = id;
	}
	public int getUserId() {
		return this.userId;
	}
	public  MessagePollingThread getMyThread() {
		//Tutte le classi devono poter uccidere il thread se necessario
		return myThread;
	}
	public  void setNewThread() {
		if(myThread==null) {
			myThread=new MessagePollingThread();
			myThread.start();
		}
		if(mySecondThread==null) {
			mySecondThread=new ProfilePollingThread();
			mySecondThread.start();
		}
	}
	public  UserBean getLoggedUser() {
		return loggedUser;
	}
	
	AnchorPane internalPane;
	public void moveToExplore(BorderPane mainPane) throws MissingPageException{
			loader=new FXMLLoader();
			URL url;
			try {
				url = new File("src/main/java/travelbook/view/ExplorePage.fxml").toURI().toURL();
			
				loader.setLocation(url);
				internalPane=(AnchorPane)loader.load();
				mainPane.setCenter(internalPane);
				ExploreViewController controller=loader.getController();
				controller.setMainPane(mainPane);
			} catch (IOException e) {
				throw new MissingPageException();
			}
	}
	public void moveToProfile(BorderPane mainPane) throws MissingPageException{
		loader=new FXMLLoader();
		URL url;
		try {
			url = new File("src/main/java/travelbook/view/ProfileUserView.fxml").toURI().toURL();
		
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ProfileViewController controller=loader.getController();
			controller.setMainPane(mainPane);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToChat(BorderPane mainPane) throws MissingPageException{
		loader=new FXMLLoader();
		try {
		URL url = new File("src/main/java/travelbook/view/ChatView.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ChatViewController controller=loader.getController();
			controller.setMainPane(mainPane);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToAdd(BorderPane mainPane)throws MissingPageException{
		loader=new FXMLLoader();
		try {	
			URL url = new File("src/main/java/travelbook/view/AddView.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			AddViewController controller=loader.getController();
			controller.setMain(mainPane);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToAddTravel(BorderPane mainPane) throws MissingPageException{
		loader=new FXMLLoader();
		try {
			URL url = new File("src/main/java/travelbook/view/AddView.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			AddViewController controller=loader.getController();
			controller.setMain(mainPane);
			controller.modfiyTravelMode(travelId);
		} catch (IOException e) {
			throw new MissingPageException();
		}	
	}
	public void moveToProfileOther(BorderPane mainPane, int back, int travelId)throws MissingPageException{
		loader=new FXMLLoader();
		try {	
			URL url = new File("src/main/java/travelbook/view/ProfileUserViewOther.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ProfileOtherController controller=loader.getController();
			controller.setMainPane(mainPane,back,travelId);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToView(BorderPane mainPane, int back)throws MissingPageException{
		loader=new FXMLLoader();
		try {	
			URL url = new File("src/main/java/travelbook/view/ViewTravel.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ViewTravelController controller=loader.getController();
			controller.setMainPane(mainPane,back);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToSearch(BorderPane mainPane)throws MissingPageException{
		loader=new FXMLLoader();
		try {	
			URL url = new File("src/main/java/travelbook/view/SerchPage.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			SearchTravelController controller=loader.getController();
			controller.setMainPane(mainPane);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	public void moveToLogin(BorderPane mainPane)throws MissingPageException{
		loader=new FXMLLoader();
		try {	
			URL url = new File("src/main/java/travelbook/view/LoginView.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			LoginViewController controller=loader.getController();
			controller.setMain(mainPane);
		} catch (IOException e) {
			throw new MissingPageException();
		}
	}
	
	public  static synchronized MenuBar getInstance() {
		if(istance==null) {
			istance=new MenuBar();
		}
		return istance;
	}
	public  boolean getNotified() {
		return notify;
		
	}
	public void setNotified() {
		this.notify=false;
		this.notifyObservers(this.notify);
	}
	public void initialize() {
		this.myChat = new ArrayList<>();
		this.notify = false;
		this.myThread.kill();
		this.myThread=null;
	}
}
