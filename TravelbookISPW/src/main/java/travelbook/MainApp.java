package main.java.travelbook;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import main.java.travelbook.view.LoginViewController;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
public class MainApp extends Application {
   //This is just a test in order to show how explore page work but no methods is implemented for the graphic controller of this view
	private Stage rootStage;
	private BorderPane rootLayout;
	private LoginViewController controller;
	@Override
	public void start(Stage rootStage) {
		this.rootStage=rootStage;
		this.rootStage.setTitle("Travelbook");
		initRootLayout();
		controller.setMain(rootLayout);
	}
	private void initRootLayout() {
		try {
		AnchorPane exploreLayout;
		URL url = new File("src/main/java/travelbook/view/RootLayout.fxml").toURI().toURL();
		this.rootLayout=(BorderPane) FXMLLoader.load(url);
		Scene scene=new Scene(rootLayout);
		rootStage.setScene(scene);
		FXMLLoader loader2=new FXMLLoader();
		url = new File("src/main/java/travelbook/view/LoginView.fxml").toURI().toURL();
		loader2.setLocation(url);
		exploreLayout=(AnchorPane) loader2.load();
		this.rootLayout.setCenter(exploreLayout);
		this.rootStage.show();
		controller=loader2.getController();	
	}catch (IOException e){
		e.printStackTrace();
	}
	}
	public BorderPane getPane() {
		return this.rootLayout;
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
