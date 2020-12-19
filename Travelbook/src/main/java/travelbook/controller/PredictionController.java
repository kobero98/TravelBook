package main.java.travelbook.controller;

import main.java.travelbook.util.PlacePrediction;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceDetails;
import com.google.maps.QueryAutocompleteRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.PlaceDetailsRequest;
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
	private String API_KEY;
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
	public AutocompletePrediction getPlace(String string) {
		//string must be the Place value of a StepBean
		AutocompletePrediction predict=null;
		QueryAutocompleteRequest query= PlacesApi.queryAutocomplete(new GeoApiContext.Builder().apiKey(API_KEY).build(), string);
				//Qui dovrei catturare eccezzione
				try {
					AutocompletePrediction[] predictions=query.await();
					predict=predictions[0];
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return predict;
				
	}
	public PlaceDetails placeDetailsRequest(AutocompletePrediction predict) {
		if(predict!=null && !predict.placeId.isEmpty()) {
			PlaceDetailsRequest query= PlacesApi.placeDetails(new GeoApiContext.Builder().apiKey(API_KEY).build(),predict.placeId);
			return query.awaitIgnoreError();
		}
		return null;
	}
	public List<String> getCityAndCountry(AutocompletePrediction predict) {
		List<String> cityAndCountry=new ArrayList<String>();
		cityAndCountry.add(new String());
		cityAndCountry.add(new String());
		PlaceDetails place=placeDetailsRequest(predict);
		 for (AddressComponent component : place.addressComponents)
	        {
	            for (AddressComponentType types : component.types)
	            {
	                if (types.equals(AddressComponentType.LOCALITY))
	                {
	                    cityAndCountry.set(0, component.longName);
	                }
	                if(types.equals(AddressComponentType.COUNTRY)) {
	                	cityAndCountry.set(1, component.longName);
	                }
	            }

	        }
		 return cityAndCountry;
		
	}
	
}
