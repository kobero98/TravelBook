package main.java.travelbook.view;
import javafx.fxml.FXMLLoader;
import main.java.travelbook.util.Chat;
import java.util.List;
import main.java.travelbook.model.bean.UserBean;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import main.java.travelbook.util.MessagePollingThread;
import main.java.travelbook.util.Observable;
import main.java.travelbook.util.Observer;
import java.util.ArrayList;
public class MenuBar extends Observable implements Observer{
	//This class want to manage MenuBar operation. Use these methods in the button handler.
	private FXMLLoader loader;
	private  boolean notify = false;  //this information will be in logged user
	private static MenuBar istance=null;
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
	private  List<Chat> myChat=new ArrayList<>();
	public  List<Chat> getMyChat() {
		return myChat;
	}
	
	private  UserBean loggedUser;
	public  void  setUser(UserBean user) {
		loggedUser=user;
		myThread=new MessagePollingThread();
		myThread.start();
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
	}
	public  UserBean getLoggedUser() {
		return loggedUser;
	}
	
	AnchorPane internalPane;
	public void moveToExplore(BorderPane mainPane)throws IOException {
			loader=new FXMLLoader();
			URL url = new File("src/main/java/travelbook/view/ExplorePage.fxml").toURI().toURL();
			loader.setLocation(url);
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ExploreViewController controller=loader.getController();
			controller.setMainPane(mainPane);
	}
	public void moveToProfile(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		URL url = new File("src/main/java/travelbook/view/ProfileUserView.fxml").toURI().toURL();
		loader.setLocation(url);
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		ProfileViewController controller=loader.getController();
		controller.setMainPane(mainPane);
	}
	public void moveToChat(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		URL url = new File("src/main/java/travelbook/view/ChatView.fxml").toURI().toURL();
		loader.setLocation(url);
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		ChatViewController controller=loader.getController();
		controller.setMainPane(mainPane);
	}
	public void moveToAdd(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		URL url = new File("src/main/java/travelbook/view/AddView.fxml").toURI().toURL();
		loader.setLocation(url);
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		AddViewController controller=loader.getController();
		controller.setMain(mainPane);
	}
	public void moveToAddTravel(BorderPane mainPane) throws IOException{
		loader=new FXMLLoader();
		URL url = new File("src/main/java/travelbook/view/AddView.fxml").toURI().toURL();
		loader.setLocation(url);
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		AddViewController controller=loader.getController();
		controller.setMain(mainPane);
		controller.modfiyTravelMode(travelId);
	}
	public static MenuBar getInstance() {
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
	}

}
