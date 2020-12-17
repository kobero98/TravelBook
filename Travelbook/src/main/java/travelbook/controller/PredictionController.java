package main.java.travelbook.controller;

import main.java.travelbook.util.PlacePrediction;

import java.util.List;
import java.util.concurrent.ExecutionException;
/*
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
import com.esri.arcgisruntime.tasks.geocode.SuggestParameters;
import com.esri.arcgisruntime.tasks.geocode.SuggestResult;*/
import java.util.ArrayList;
import java.io.IOException;
public class PredictionController {
	private String ACCESS_TOKEN="pk.eyJ1IjoiY2ljY2E5OSIsImEiOiJja2lvcnhvMTkxMTFzMzJxc2szdDdqdHdtIn0.2dXv3HXu9G8sXNKupBDjtw";
	private boolean fatto=false;
	private List<PlacePrediction> results=new ArrayList<>();
	public List<PlacePrediction> getPredictions(String text) {
		System.out.println("Ciao");
		//return the predictions or null
		//QueryAutocompleteRequest query= PlacesApi.queryAutocomplete(new GeoApiContext.Builder().apiKey(API_KEY).build(), text);
		//Qui dovrei catturare eccezzione
		//try {
		
		 //AutocompletePrediction[] predictions=query.await();
		
		List<String> predictions=new ArrayList<>();
	String[] database= {"Roma","Firenze","Milano","Bergamo","Torino","Vibo Marina","Bologna","Napoli","Palermo","Lauro","Cellole","Firenze","Grosseto","Genova","Frascati","Aprilia","Latina","Baia Domizia","Formia","Gaeta","Minturno","Teano","Caserta"};
		for(String parse: database) {
			if(parse.contains(text)) {
				predictions.add(parse);
			}
		}
		 for(String pred: predictions) {
			 results.add(new PlacePrediction(pred));
		 }
		/*SuggestParameters geocodeParameters= new SuggestParameters();
		LocatorTask locator=new LocatorTask("https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer");
		geocodeParameters.getCategories().add("POI");
		ListenableFuture<List<SuggestResult>> suggests=locator.suggestAsync(text,geocodeParameters);
		suggests.addDoneListener(()->{
			try {
				List<SuggestResult> res=suggests.get();
				for(SuggestResult risultato: res) {
					results.add(new PlacePrediction(risultato));
				}
				fatto=true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		while(!fatto) {
			System.out.println("Sto aspettando");
		}
		System.out.println("Fatto");*/
		
		return results;
	}
	
}
