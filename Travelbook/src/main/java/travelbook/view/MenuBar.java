package main.java.travelbook.view;
import javafx.fxml.FXMLLoader;
import main.java.travelbook.util.Chat;
import java.util.List;
import main.java.travelbook.model.bean.UserBean;
import java.io.IOException;
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
		Chat val=(Chat)chat;
		notify=true;
		istance.setChanged();
		System.out.println("Nuova chat arrivata: "+val.getIdUser()+" testo: "+val.getReceive().get(val.getReceive().size()-1).getText());
	}
	@Override
	public void update(Observable chat, Object arg) {
		istance.update(chat);
	}
	public static void newChat(Chat chat) {
		myChat.add(chat);
		chat.addObserver(istance);
	}
	private static MessagePollingThread myThread;
	private static List<Chat> myChat=new ArrayList<>();
	public static List<Chat> getMyChat() {
		return myChat;
	}
	
	private static UserBean loggedUser;
	public static void  setUser(UserBean user) {
		loggedUser=user;
		myThread=new MessagePollingThread();
		myThread.start();
	}
	public static MessagePollingThread getMyThread() {
		//Tutte le classi devono poter uccidere il thread se necessario
		return myThread;
	}
	public static void setNewThread() {
		if(myThread==null) {
			myThread=new MessagePollingThread();
			myThread.start();
		}
	}
	public static UserBean getLoggedUser() {
		return loggedUser;
	}
	
	AnchorPane internalPane;
	public void moveToExplore(BorderPane mainPane)throws IOException {
			loader=new FXMLLoader();
			loader.setLocation(MenuBar.class.getResource("ExplorePage.fxml"));
			internalPane=(AnchorPane)loader.load();
			mainPane.setCenter(internalPane);
			ExploreViewController controller=loader.getController();
			controller.setMainPane(mainPane);
	}
	public void moveToProfile(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		loader.setLocation(MenuBar.class.getResource("ProfileUserView.fxml"));
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		ProfileViewController controller=loader.getController();
		controller.setMainPane(mainPane);
	}
	public void moveToChat(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		loader.setLocation(MenuBar.class.getResource("ChatView.fxml"));
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		ChatViewController controller=loader.getController();
		controller.setMainPane(mainPane);
	}
	public void moveToAdd(BorderPane mainPane)throws IOException{
		loader=new FXMLLoader();
		loader.setLocation(MenuBar.class.getResource("AddView.fxml"));
		internalPane=(AnchorPane)loader.load();
		mainPane.setCenter(internalPane);
		AddViewController controller=loader.getController();
		controller.setMain(mainPane);
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
