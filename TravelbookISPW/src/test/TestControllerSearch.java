package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Test;

import main.java.exception.DBException;
import main.java.travelbook.controller.ControllerSearch;
import main.java.travelbook.model.bean.SearchTrip;
/*
 * @author Matteo Federico aka Kobero
 */
public class TestControllerSearch {

	@Test
	public void testGetCitiesPredictionsWork() throws DBException {
		//testing that given a string, all returned predictions starts with that
		//string
		String text="Rom";
		List<String> predictions;
		ControllerSearch myController=new ControllerSearch();
		Boolean flag=true;
		predictions=myController.getCitiesPredictions(text);
		for(String s:predictions)
			if(!s.startsWith(text))flag=false;
		assertEquals(true,flag);
	}
	@Test
	public void testSearchNotWork() {
		//testing that the controller throws an exception when the string parameter
		//doesn't matches any city from the database or is malformed
		SearchTrip trip=new SearchTrip();
		trip.setCity("Narnia");
		trip.setCostoMin(300);
		trip.setCostoMax(1000);
		trip.setDurationMin(2);
		trip.setDurationMax(5);
		trip.setType(null);
		ControllerSearch myController=new ControllerSearch();
		assertThrows(DBException.class,()->myController.search(trip));
	}
}
