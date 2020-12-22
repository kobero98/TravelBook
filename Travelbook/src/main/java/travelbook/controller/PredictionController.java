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
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/*
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
import com.esri.arcgisruntime.tasks.geocode.SuggestParameters;
import com.esri.arcgisruntime.tasks.geocode.SuggestResult;*/
import java.util.ArrayList;
import java.io.IOException;
public class PredictionController {
	private String API_KEY;
	private String TOKEN="pk.eyJ1IjoiZGVtYWdvZ28iLCJhIjoiY2tpeXJmMjN1MjRsbzMybW1zNnR1YjQyNSJ9.Tw4ab3jdi3WXYHMlCcalAA";
	private List<PlacePrediction> results=new ArrayList<>();
	public List<PlacePrediction> getPredictions(String text) {
		System.out.println("Ciao");
		//return the predictions or null
		//QueryAutocompleteRequest query= PlacesApi.queryAutocomplete(new GeoApiContext.Builder().apiKey(API_KEY).build(), text);
		//Qui dovrei catturare eccezzione
		//try {
		
		 //AutocompletePrediction[] predictions=query.await();
		
		//List<String> predictions=new ArrayList<>();
	/*String[] database= {"Roma","Firenze","Milano","Bergamo","Torino","Vibo Marina","Bologna","Napoli","Palermo","Lauro","Cellole","Firenze","Grosseto","Genova","Frascati","Aprilia","Latina","Baia Domizia","Formia","Gaeta","Minturno","Teano","Caserta"};
		for(String parse: database) {
			if(parse.contains(text)) {
				predictions.add(parse);
			}
		}
		 for(String pred: predictions) {
			 results.add(new PlacePrediction(pred));
		 }*/
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
		String[] part=text.split(" ");
		StringBuffer newText=new StringBuffer();
		for(int i=0;i<part.length;i++) {
			//Le stringhe con gli spazi non funzionano.
			newText.append(part[i]);
			if(i<part.length-1) {
				newText.append("%20");
			}
		}
		HttpClient client=HttpClientBuilder.create().build();
		String url="https://api.mapbox.com/geocoding/v5/mapbox.places/"+newText+".json"+"?autocomplete=true"+"&limit=10"+"&access_token="+TOKEN;
		HttpGet request=new HttpGet(url);
		request.addHeader("accept", "application/json");
		try {
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        JSONArray arr=(JSONArray)obj.get("features");
                        JSONObject place=(JSONObject)arr.get(0);
                        results.add(new PlacePrediction((String)place.get("place_name")));
                        System.out.println(obj.toString());
                        System.out.println(obj.get("place_name"));
                        System.out.println(obj.get("text"));
                        return results;
                    }

                }else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;
                    JSONArray array=(JSONArray)obj.get("features");
                    
                    
                   
                    JSONObject place;
                    for(int i=0;i<array.size();i++) {
                    	place=(JSONObject)array.get(i);
                    	results.add(new PlacePrediction((String)place.get("place_name")));
                    }
                    
                    return results;
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	
}
