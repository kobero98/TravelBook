package test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import main.java.travelbook.util.Place;
import main.java.travelbook.util.PlaceAdapter;
import exception.MapboxException;
import main.java.travelbook.controller.PredictionController;
import org.json.simple.JSONObject;
/*
 * @author Matteo Ciccaglione
 */
public class TestPredictionController {
	@Test
	public void testGetPredictions() throws MapboxException {
		//Test if results of a prediction request method starts with the text string used for search
		PredictionController con=new PredictionController();
		String text="Colosseo";
		List<JSONObject> json=con.getPredictions(text);
		boolean correct=true;
		for(JSONObject obj: json) {
			//Construct a Place to test if results are correct
			Place p=new PlaceAdapter(obj);
			if(!(p.getPlaceName().startsWith(text))) 
				correct=false;
		}
		assertEquals(true,correct);
	}
	@Test
	public void testGetPlaceByNameByKnownPlace() throws MapboxException{
		//Test if search of a known place return correct result
		PredictionController con=new PredictionController();
		String text="Colosseo, Piazza del Colosseo, Roma, Rome 00184, Italy";
		JSONObject res=con.getPlaceByName(text);
		Place place=new PlaceAdapter(res);
		boolean correct=true;
		if(!place.getPlaceName().equals(text))
			correct=false;
		if(!place.getCity().equals("Rome"))
			correct=false;
		if(!place.getCountry().equals("Italy"))
			correct=false;
		if(place.getCoordinates().get(0)!=41.89059725) {
			correct=false;
		}
		if(place.getCoordinates().get(1)!=12.492115) {
			correct=false;
		}
		if(!(place.getPostCode().equals("00184"))){
			correct=false;
		}
		if(!(place.getType().equals("poi"))) {
			correct=false;
		}
		if(!(place.getCategory().startsWith("historic"))) {
			correct=false;
		}
		assertEquals(true,correct);
	}
}
