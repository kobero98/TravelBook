package main.java.travelbook.controller;
import java.util.List;
import java.util.ArrayList;

import main.java.exception.MapboxException;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.util.Place;
import main.java.travelbook.util.PlaceAdapter;
public class ViewOnMap {
	private static ViewOnMap istance =null;
	private ViewOnMap() {
	
	}
	public static ViewOnMap getIstance() {
		if(istance==null) {
			istance=new ViewOnMap();
		}
		return istance;
	}
	private void verifyExistance(List<String> placeAdded,List<Double> coordinates,StepBean step) {
		if(placeAdded.contains(step.getFullPlace().getPlaceName())) {
			coordinates.set(0, coordinates.get(0)+0.0001);
			coordinates.set(1, coordinates.get(1)+0.0001);
		}
	}
	public List<String> loadTravel(List<StepBean> steps) throws MapboxException{
		List<String> scripts=new ArrayList<>();
		List<String> placeAdded=new ArrayList<>();
		List<Double> coordinates;
		StringBuilder forPath=new StringBuilder();
		forPath.append("[");
		StringBuilder script;
		boolean start=true;
		for(StepBean step: steps) {
			script=new StringBuilder();
			if(step.getPlace()==null)
				continue;
			if(step.getFullPlace()==null) {
				step.setFullPlace(getPlaceByName(step.getPlace()));
			}
				
			coordinates=step.getFullPlace().getCoordinates();
			this.verifyExistance(placeAdded, coordinates,step);
			forPath.append(coordinates.toString()+",");
			script.append(coordinates.toString()+",");
			StringBuilder popupContent=new StringBuilder();
			popupContent.append("<b>"+step.getPlace().replace("'", "\\'")+"</b><br>");
			popupContent.append("Category: ");
			//Category is a comma separeted string if step is a poi or "city" if is a "place" or other
			String category=step.getFullPlace().getCategory();
			if(category!=null) {
				popupContent.append(category+"<br>");
			}
			else {
				popupContent.append(step.getFullPlace().getType()+"<br>");
			}
			script.append("\"<p>"+popupContent+"</p>\",");
			String icon=getIcon(step, category);
			script.append("\""+icon+"\",");
			script.append(start);
			scripts.add("addMarker("+script+");");
			placeAdded.add(step.getFullPlace().getPlaceName());
			start=false;
		}
		forPath.replace(forPath.length()-1, forPath.length(), "]");
		scripts.add("drawPath("+forPath+");");
		return scripts;
	}
	private String getIcon(StepBean step, String category) {
		String icon=null;
		
		//Ho scoperto maki ma sfortunatamente non funziona come vorrei
		if(category!=null && step.getFullPlace().getIcon()!=null) {
				icon=step.getFullPlace().getIcon();
		}
			else {
				icon=foundIcon(category);
			}
		return icon;
	}
	private String foundIcon (String category) {
		String icon=null;
		//Add here an if statement if you know some other categories
		//See maki on google to know how icon are assigned
		//La risposta json ha un campo maki ma sfortunatamente non funziona come dovrebbe
		//Ad esempio per un ristorante come Tonnarello non ritorna il campo maki.
		//Stessa cosa per un monumento come il colosseo
		//Il mio metodo invece riconosce questi ultimi sfruttando le categories.
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
		if(category.contains("hotel") || category.contains("bnb")) {
			icon="home";
		}
		}
		return icon;
	}
	private Place getPlaceByName(String place)throws MapboxException {
		PredictionController controller=new PredictionController();
		return new PlaceAdapter(controller.getPlaceByName(place));
	}
}
