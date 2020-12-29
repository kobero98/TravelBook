package main.java.travelbook.controller;
import java.util.List;
import java.util.ArrayList;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.util.PlacePrediction;
public class ViewOnMap {
	//Ci sto ancora lavorando per renderlo collegabile alla mappa per adesso non l'ho testato
	private static ViewOnMap istance =null;
	private ViewOnMap() {
		
	}
	public static ViewOnMap getIstance() {
		if(istance==null) {
			istance=new ViewOnMap();
		}
		return istance;
	}
	
	public List<String> loadTravel(TravelBean travel) {
		List<String> scripts=new ArrayList<String>();
		List<StepBean> steps=travel.getListStep();
		for(StepBean step: steps) {
			if(step.getFullPlace()==null) {
				step.setFullPlace(getPlaceByName(step.getPlace()));
			}
			else {
				scripts.add("addMarker("+step.getFullPlace().getCoordinates()+");");
			}
		}
		
		return scripts;
	}
	private PlacePrediction getPlaceByName(String place) {
		PredictionController controller=new PredictionController();
		PlacePrediction predict=controller.getPlaceByName(place);
		return predict;
	}
}
