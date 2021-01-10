package main.java.travelbook.view;
import javafx.fxml.FXMLLoader;
import main.java.travelbook.model.bean.UserBean;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
public class MenuBar {
	//This class want to manage MenuBar operation. Use these methods in the button handler.
	private FXMLLoader loader;
	private static boolean notify = true;  //this information will be in logged user
	private static MenuBar istance=null;
	private MenuBar() {
		
	}
	private static UserBean loggedUser;
	public static void  setUser(UserBean user) {
		loggedUser=user;
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
	public static boolean getNotified() {
		return notify;
		
	}

}
