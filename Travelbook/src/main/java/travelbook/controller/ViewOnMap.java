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
	
	public List<String> loadTravel(List<StepBean> steps) {
		List<String> scripts=new ArrayList<String>();
		StringBuffer forPath=new StringBuffer();
		forPath.append("[");
		StringBuffer script;
		boolean start=true;
		for(StepBean step: steps) {
			script=new StringBuffer();
			if(step.getFullPlace()==null) {
				step.setFullPlace(getPlaceByName(step.getPlace()));
			}
				forPath.append(step.getFullPlace().getCoordinates().toString()+",");
				script.append(step.getFullPlace().getCoordinates().toString()+",");
			
			if(step.getDescriptionStep()!=null && !step.getDescriptionStep().isEmpty()) {
				script.append("\""+step.getDescriptionStep()+"\",");
			}
			else {
				script.append("\"No description yet\",");
			}
			String icon=null;
			String category=step.getFullPlace().getCategory();
			//Do you have some other idea on possible category? put it here 
			//N.B. Se non le copriamo tutte non è una tragedia uscirà un marker di default
			if(category!=null) {
			if(category.contains("restaurant")) {
				icon="restaurant";
			}
			if(category.contains("historic")||category.contains("museum")) {
				icon="museum";
			}
			if(category.contains("bar")) {
				icon="bar";
			}
			if(category.contains("beach")) {
				icon="beach";
			}
			if(category.contains("mountain")) {
				icon="mountain";
			}
			}
			script.append("\""+icon+"\",");
			script.append(start);
			scripts.add("addMarker("+script+");");
			start=false;
			System.out.println(scripts.get(scripts.size()-1));
		}
		forPath.replace(forPath.length()-1, forPath.length(), "]");
		System.out.println(forPath);
		scripts.add("drawPath("+forPath+");");
		return scripts;
	}
	private PlacePrediction getPlaceByName(String place) {
		PredictionController controller=new PredictionController();
		PlacePrediction predict=controller.getPlaceByName(place);
		return predict;
	}
}
