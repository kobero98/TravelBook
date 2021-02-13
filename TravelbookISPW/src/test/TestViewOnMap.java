package test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.exception.MapboxException;
import main.java.travelbook.controller.ViewOnMap;
import main.java.travelbook.model.bean.StepBean;
/*
 * @author Matteo Ciccaglione
 */
public class TestViewOnMap {
	
	@Test
	public void testLoadTravel() throws MapboxException{
		//Test if the returned list of scripts are in correct number
		//note that load travel create a script for each step with a defined Place name
		List<StepBean> steps=new ArrayList<>();
		StepBean step=new StepBean();
		step.setPlace("Colosseo, Piazza del Colosseo, Roma, Rome 00184, Italy");
		steps.add(step);
		step=new StepBean();
		step.setPlace("Verona, Verona, Italy");
		steps.add(step);
		step=new StepBean();
		steps.add(step);
		//I expect exactly 3 scripts (1 for each step with place value +1)
		ViewOnMap controller=ViewOnMap.getIstance();
		List<String> scripts=controller.loadTravel(steps);
		assertEquals(3,scripts.size());
	}
	@Test
	public void testLoadTravelMultipleEqualsStep() throws MapboxException{
		//Test if controller correctly setup a script for each step also if they are equals
		List<StepBean> steps=new ArrayList<>();
		for(int i=0;i<20;i++) {
			StepBean step=new StepBean();
			step.setPlace("Verona, Verona, Italy");
			steps.add(step);
		}
		ViewOnMap controller=ViewOnMap.getIstance();
		List<String> scripts=controller.loadTravel(steps);
		assertEquals(21,scripts.size());
	}
	@Test
	public void testLoadTravelIncorrectPlace() {
		List<StepBean> steps=new ArrayList<>();
		StepBean step=new StepBean();
		step.setPlace("");
		steps.add(step);
		for(int i=0;i<25;i++) {
			step=new StepBean();
			step.setPlace("Colosseo, Piazza del Colosseo, Roma, Rome 00184, Italy");
			steps.add(step);
		}
		assertThrows(MapboxException.class,()->ViewOnMap.getIstance().loadTravel(steps));
	}
}
