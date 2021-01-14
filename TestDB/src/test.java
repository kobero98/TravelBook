import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

public class test extends Application{
	private Stage rootStage;
	private AnchorPane rootLayout;
	private Main controller;
	@Override
	public void start(Stage rootStage) {
		this.rootStage=rootStage;
		this.rootStage.setTitle("Travelbook");
		initRootLayout();
		long startTime = System.nanoTime();
		controller.metti();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration);
	}
	private void initRootLayout() {
		try {
			AnchorPane exploreLayout;
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(test.class.getResource("testDB.fxml"));
			this.rootLayout=(AnchorPane) loader.load();
			Scene scene=new Scene(rootLayout);
			rootStage.setScene(scene);
			this.rootStage.show();
			controller= (Main) loader.getController();
	}catch (IOException e){
		e.printStackTrace();
	}
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
