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
	private String TOKEN="INSERT_HERE_YOUR_TOKEN";
	private List<PlacePrediction> results=new ArrayList<>();
	public List<PlacePrediction> getPredictions(String text) {
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
		String url="https://api.mapbox.com/geocoding/v5/mapbox.places/"+newText+".json"+"?autocomplete=true"+"&limit=10"+"types=place,locality,address,poi"+"&access_token="+TOKEN;
		HttpGet request=new HttpGet(url);
		request.addHeader("accept", "application/json");
		try {
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                 if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;
                    JSONArray array=(JSONArray)obj.get("features");
                    
                    
                   
                    JSONObject place;
                    for(int i=0;i<array.size();i++) {
                    	place=(JSONObject)array.get(i);
                    	PlacePrediction placePred=new PlacePrediction();
                    	placePred.setPlaceName(place.get("place_name").toString());
                    	String type[]=(String[])place.get("place_type");
                    	placePred.setPlaceType(type[0]);
                    	placePred.setCoordinates((double[])place.get("center"));
                    	JSONArray context=(JSONArray)place.get("context");
                    	for(int j=0;j<context.size();j++) {
                    		JSONObject first=(JSONObject)context.get(i);
                    		String id=first.get("id").toString();
                    		if(type[0]=="poi") {
                    			if(id.startsWith("postcode")) {
                    				placePred.setPostCode(Double.parseDouble(first.get("text").toString()));
                    				
                    			}
                    			
                    		}
                    		if(id.startsWith("region")) {
                    			placePred.setCity(first.get("text").toString());
                    		}
                    		else if(id.startsWith("country")) {
                    			placePred.setCountry(first.get("text").toString());
                    		}
                    	}
                    	if(type[0]=="poi") {
                    		JSONObject categoria=(JSONObject)place.get("properties");
                    		placePred.setCategory(categoria.get("category").toString());
                    	}
                    	
                    	results.add(placePred);
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
