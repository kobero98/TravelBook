package main.java.travelbook.view;
import javafx.scene.web.WebView;
import main.java.travelbook.controller.MapboxException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.controller.PredictionController;
import main.java.travelbook.controller.ViewOnMap;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;
public class ViewOnMapController {
	
	public void load(List<StepBean> steps) {
		
		WebView view;
		WebEngine engine;
		Stage stage;
		 view=new WebView();
		 stage=new Stage();
		 AnchorPane anchor=new AnchorPane();
		 view.setPrefHeight(720);
		 view.setPrefWidth(1280);
		 anchor.setPrefHeight(720);
		 anchor.setPrefWidth(1280);
		 anchor.getChildren().add(view);
		 
		 Scene scene=new Scene(anchor);
		 stage.setScene(scene);
		 stage.show();
		 
		 stage.heightProperty().addListener((observable,oldValue,newValue)->{
			 anchor.setPrefHeight(stage.getHeight());
			 view.setPrefHeight(stage.getHeight());
		 });
		 stage.widthProperty().addListener((observable,oldValue,newValue)->{
			 anchor.setPrefWidth(stage.getWidth());
			 view.setPrefWidth(stage.getWidth());
		 });
		 view.setVisible(true);
			engine=view.getEngine();
			URL myUrl=null;
			try {
				myUrl = new File("src/main/java/travelbook/view/ViewOnMapController.html").toURI().toURL();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String url= myUrl.toString();
			engine.getLoadWorker().stateProperty().addListener((observable,oldValue,newValue)->{
				if(newValue==Worker.State.SUCCEEDED) {
					PredictionController controller=new PredictionController();
					String token="\""+controller.getToken()+"\"";
					engine.executeScript("init("+token+");");
					try {
					List<String> scripts=ViewOnMap.getIstance().loadTravel(steps);
					for(String script: scripts) {
						engine.executeScript(script);
					}
					}catch(MapboxException e) {
						Alert alert=new Alert(AlertType.ERROR);
						alert.setHeaderText("Map service error");
						alert.setContentText(e.getMessage());
						alert.initOwner(stage);
						URL url1 = null;
				   		 try {
				   		 url1= new File("src/main/java/travelbook/css/alert.css").toURI().toURL();
				   		 alert.getDialogPane().getStylesheets().add(url.toString());
				   		 url1 = new File("src/main/java/travelbook/css/project.css").toURI().toURL();
				   		 alert.getDialogPane().getStylesheets().add(url.toString());
				   		 
				   		 
				   			url1 = new File("src/main/resources/error.png").toURI().toURL();
				   		} catch (MalformedURLException e1) {
				   			// TODO Auto-generated catch block
				   			e1.printStackTrace();
				   		}
			  		 	Image image = new Image(url1.toString());
						ImageView imageView = new ImageView(image);
						alert.setGraphic(imageView);
						alert.showAndWait();
					}
					
				}
			});
			engine.load(url);
	}
}
