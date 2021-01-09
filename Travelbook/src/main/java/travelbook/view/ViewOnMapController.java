package main.java.travelbook.view;
import javafx.scene.web.WebView;
import main.java.travelbook.controller.MapboxException;
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
		 view.setVisible(true);
			engine=view.getEngine();
			String url= ViewOnMapController.class.getResource("mapView.html").toString();
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
						alert.getDialogPane().getStylesheets().add("main/java/travelbook/css/alert.css");
						alert.getDialogPane().getStylesheets().add("main/java/travelbook/css/project.css");
						Image image = new Image("main/resources/AddViewImages/error.png");
						ImageView imageView = new ImageView(image);
						alert.setGraphic(imageView);
						alert.showAndWait();
					}
					
				}
			});
			engine.load(url);
	}
}
