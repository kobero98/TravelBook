package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.SearchEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.bean.*;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PredictableDAO;
import main.java.travelbook.model.dao.VisualDAO;

public class ControllerSearch {
	private static ControllerSearch instance=null;
	private ControllerSearch() {}
	public static ControllerSearch getInstance()
	{
		if(instance==null) instance= new ControllerSearch();
		return instance;
	}

	/*
	 ordine dei Type:
	 1.Romantic Trip
	 2.Family Holiday
	 3.On The Road
	 4.Children Friendly
	 5.Travel with Friend
	 6.Cultural Travel
	 7.Relaxion Holiday
	 */

	private int[] calcoloVettore(List<String>s)
	{
		int [] v = {-1,-1,-1,-1,-1,-1,-1};
		
		for(int i=0;i<s.size();i++)
		{
			if(s.get(i).equals("Romantic Trip"))          v[0]=i;
			if(s.get(i).equals("Family Holiday"))         v[1]=i;
			if(s.get(i).equals("On The Road"))            v[2]=i;
			if(s.get(i).equals("Children Friendly"))      v[3]=i;
			if(s.get(i).equals("Travel with Friend"))     v[4]=i;
			if(s.get(i).equals("Cultural Travel"))        v[5]=i;
			if(s.get(i).equals("Relaxing Holiday"))       v[6]=i;
		}
		return v;
	}
	private String setTypeOrder(List<String> s)
	{
		String ritorno="#";
		if(s==null) return ritorno;
		int [] v=calcoloVettore(s);
		for(int i=0;i<7;i++)
		{
			if(v[i]!=-1){
				ritorno=ritorno.concat(s.get(v[i]));
				ritorno=ritorno.concat("#");
			}
		}
		return ritorno;
			
	}
	private CityEntity convertCity(String s) throws DBException {
		CityEntity citta=new CityEntity();
		int i=s.indexOf(",");
		if(i==-1 || i==s.length()-1) throw new DBException("Erroe citta non presente");
		citta.setNameC(s.substring(0, i));
		citta.setState(s.substring(i+1));
		return citta;
		
	}
	public List <MiniTravelBean> search(SearchTrip trip) throws DBException{
		SearchEntity search=new SearchEntity();
		search.setType(setTypeOrder(trip.getType()));
		search.setCity(convertCity(trip.getCity()));
		search.setMinDay(trip.getDurationMin());
		if(trip.getDurationMax()<trip.getDurationMin() && trip.getDurationMax()<=0) throw new DBException("durata min < durataMax");
		if(trip.getDurationMax()!=0) search.setMaxDay(trip.getDurationMax());
		else search.setMaxDay(null);
		search.setMinCost(trip.getCostoMin());
		if(trip.getCostoMax()!=0)search.setMaxCost(trip.getCostoMax());
		else search.setMaxCost(null);
		VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.SEARCH_TRAVEL);
		List<Entity> l;
		try {
			l = dao.getData(search);
		} catch ( SQLException e) {
			throw new DBException("connection lost");
		}
		List<MiniTravelBean> list=new ArrayList<>();
		for(int i=0;i<l.size();i++) {
			list.add( new MiniTravelBean( (TravelEntity)  l.get(i) ));
		}
		return list;
	}
	public List<String> getCitiesPredictions(String text) {
		PredictableDAO dao= DaoFactory.getInstance().createPredictable(DaoType.CITY);
		List<String> results=new ArrayList<>();
		List<Entity> predictions=dao.getPredictions(text);
		String singleResult;
		for(Entity city: predictions) {
			CityEntity entity=(CityEntity) city;
			singleResult=entity.getNameC()+","+entity.getState();
			results.add(singleResult);
		}
		return results;
	}
}
