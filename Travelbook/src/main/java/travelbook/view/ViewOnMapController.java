package main.java.travelbook.view;
import javafx.scene.web.WebView;
import java.util.List;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.controller.PredictionController;
import main.java.travelbook.controller.ViewOnMap;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
public class ViewOnMapController {
	private WebView view=new WebView();
	private WebEngine engine;
	private Stage stage;
	private List<StepBean> steps=null;
	public ViewOnMapController() {
		view.setVisible(true);
		this.engine=view.getEngine();
		String url= ViewOnMapController.class.getResource("mapView.html").toString();
		engine.getLoadWorker().stateProperty().addListener((observable,oldValue,newValue)->{
			if(newValue==Worker.State.SUCCEEDED) {
				System.out.println("Done");
				PredictionController controller=new PredictionController();
				String token="\""+controller.getToken()+"\"";
				engine.executeScript("init("+token+");");
				//Sto ancora lavorando a come collegare il controller applicativo per costruire gli script da lanciare in js
				while(this.steps==null) {
					
				}
				List<String> scripts=ViewOnMap.getIstance().loadTravel(this.steps);
				for(String script: scripts) {
					engine.executeScript(script);
				}
			}
			else {
				System.out.println("Niente");
			}
		});
		engine.load(url);
		
	}
	public void load(List<StepBean> steps) {
		 stage=new Stage();
		 AnchorPane anchor=new AnchorPane();
		 view.setPrefHeight(720);
		 view.setPrefWidth(1280);
		 anchor.setPrefHeight(720);
		 anchor.setPrefWidth(1280);
		 anchor.getChildren().add(view);
		 Scene scene=new Scene(anchor);
		 stage.setScene(scene);
		 this.steps=steps;
		 stage.show();
	}
}
