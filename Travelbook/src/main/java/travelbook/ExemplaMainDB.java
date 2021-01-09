package main.java.travelbook;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;

public class ExemplaMainDB {

	public static void main(String[]args) {
		AllQuery domande=new AllQuery();
		domande.deleteTravel(20);
		TravelEntity viaggio=new TravelEntity();
		viaggio.setCreatorTravel(1);
		viaggio.setStepNumber(2);
		viaggio.setType("#Romantic Trip");
		viaggio.setCostTravel(405);
		viaggio.setStartTravelDate(new Date(2018,6,6));
		viaggio.setEndTravelDate(new Date(2018,6,12));
		viaggio.setNameTravel("Venezia");
		viaggio.setPathBackground("no Path Avaliable");
		viaggio.setDescriptionTravel("viaggio a venezia con la mia ragazza");
		StepEntity step1,step2,step3;
		step1=new StepEntity(1,1);
		step1.setDescriptionStep("step 1");
		step1.setPlace("Piazza San Marco");
		step1.setDay(new Date(2018,6,6));
		step1.setGroupDay(1);
		List<String> photo=new ArrayList();
		photo.add("file not found");
		photo.add("file not found");
		photo.add("file not found");
		photo.add("file not found");
		step1.setListPhoto(photo);
		
		step2=new StepEntity(1,2);
		step2.setDescriptionStep("step 2");
		step2.setPlace("Canale di venezia");
		step2.setDay(new Date(2018,6,7));
		step2.setGroupDay(2);
		List<String> photo1=new ArrayList();
		photo1.add("file not found");
		photo1.add("file not found");
		photo1.add("file not found");
		photo1.add("file not found");
		step2.setListPhoto(photo1);
		
		List<StepEntity> lista=new ArrayList<>();
		lista.add(step1);
		lista.add(step2);
		viaggio.setListStep(lista);
		
		domande.requestRegistrationTrip(viaggio);
	}
}
