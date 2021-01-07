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
			StringBuffer popupContent=new StringBuffer();
			popupContent.append("<b>"+step.getPlace()+"</b><br>");
			popupContent.append("Category: ");
			//Category is a comma separeted string if step is a poi or "city" if is a "place" or other
			String category=step.getFullPlace().getCategory();
			if(category!=null) {
				popupContent.append(category+"<br>");
			}
			else {
				popupContent.append(step.getFullPlace().getType()+"<br>");
			}
			popupContent.append("User Description: ");
			if(step.getDescriptionStep()!=null && !step.getDescriptionStep().isEmpty()) {
				popupContent.append(step.getDescriptionStep()+"<br>");
			}
			else {
				popupContent.append("No description yet<br>");
			}
			script.append("\"<p>"+popupContent+"</p>\",");
			String icon=null;
			
			//Ho scoperto maki ma sfortunatamente non funziona come vorrei
			if(category!=null) {
				if(step.getFullPlace().getIcon()!=null) {
					icon=step.getFullPlace().getIcon();
				}
				else {
					//Add here an if statement if you know some other categories
					//See maki on google to know how icon are assigned
					//La risposta json ha un campo maki ma sfortunatamente non funziona come dovrebbe
					//Ad esempio per un ristorante come Tonnarello non ritorna il campo maki.
					//Stessa cosa per un monumento come il colosseo
					//Il mio metodo invece riconosce questi ultimi sfruttando le categories.
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
					if(category.contains("hotel") || category.contains("bnb")) {
						icon="home";
					}
					
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
