package main.java.travelbook.controller;




import java.util.List;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.java.exception.MapboxException;

import java.util.ArrayList;
public class PredictionController {
	
	private static  String token="pk.eyJ1IjoiZGVtYWdvZ28iLCJhIjoiY2tqMWJybjZtMGpjbzMwbWh6bWt1MHcycyJ9.f9zugEhda3-7YFxBwutEnA";
	
	public List<JSONObject> getPredictions(String text) throws MapboxException {
		return mapboxQuery(text,true,10,"place,address,locality,poi");
	}
	private List<JSONObject> mapboxQuery(String text,boolean bool,int limit,String tipi) throws MapboxException {
		
		if(limit>10) {
			limit=10;
		}
		if(limit<1) {
			limit=1;
		}
		String newText;
		try {
			newText=URLEncoder.encode(text,"UTF-8");
		
		HttpClient client=HttpClientBuilder.create().build();
		String url="https://api.mapbox.com/geocoding/v5/mapbox.places/"+newText+".json"+"?fuzzyMatch="+bool+"&limit="+limit+"&types="+tipi+"&access_token="+token;
		HttpGet request=new HttpGet(url);
		request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(),"UTF-8");
			return parseString(json);
		} catch (IOException e) {
			throw new MapboxException(e.getMessage());
		}
		

	}
	private List<JSONObject> parseString(String json) throws MapboxException {
		List<JSONObject> results=new ArrayList<>();
		try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);

             if (resultObject instanceof JSONObject) {
                JSONObject obj =(JSONObject)resultObject;
                JSONArray array=(JSONArray)obj.get("features");
                for (int i=0;i<array.size();i++) {
                	results.add((JSONObject)array.get(i));
                } 
            }
     		return results;

        } catch (Exception e) {
        	throw new MapboxException("Several error unable to get places information");
        }
	}
	public JSONObject getPlaceByName(String name) throws MapboxException {
		String s = "place_name";
		List<String> types=new ArrayList<>();
		types.add("place");
		types.add("address");
		types.add("locality");
		types.add("poi");
		List<JSONObject> results=this.mapboxQuery(name, false, 10,"place,address,locality,poi");
		JSONObject o=results.get(0);
		for(JSONObject res: results) {
			if(name.startsWith(res.get(s).toString()) || name.endsWith(res.get(s).toString()) ) {
				return res;
			}
		}
		for(String tipo: types) {
			results=this.mapboxQuery(name, false, 10, tipo);
			for(JSONObject res: results) {
				
				if(name.startsWith(res.get(s).toString()) || name.endsWith(res.get(s).toString())) {
					return res;
				}
			}
		}
		return o;
		
	}
	public String getToken() {
		return token;
	}
	
	
}
