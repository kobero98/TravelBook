package travelbook;
import java.io.IOException;
import travelbook.view.LoginViewController;
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
	private AnchorPane exploreLayout;
	private LoginViewController controller;
	@Override
	public void start(Stage rootStage) {
		this.rootStage=rootStage;
		this.rootStage.setTitle("Travelbook");
		initRootLayout();
		controller.setMain(this);
	}
	private void initRootLayout() {
		try {
		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
		this.rootLayout=(BorderPane) loader.load();
		Scene scene=new Scene(rootLayout);
		rootStage.setScene(scene);
		FXMLLoader loader2=new FXMLLoader();
		loader2.setLocation(MainApp.class.getResource("view/LoginView.fxml"));
		this.exploreLayout=(AnchorPane) loader2.load();
		this.rootLayout.setCenter(this.exploreLayout);
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
