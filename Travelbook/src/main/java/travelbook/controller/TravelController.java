package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.List;

import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

public class TravelController{
	
	private static TravelController instance = null;
	
	private TravelController() {
		
	}
	
	public static TravelController getInstance() {
		if(instance == null) {
			instance = new TravelController();
		}
		return instance;
	}
	
	public TravelBean getTravel(int id) throws SQLException {
		PersistanceDAO travelDao = DaoFactory.getInstance().create(DaoType.TRAVEL);
		TravelEntity travelE = new TravelEntity();
		travelE.setIdTravel(id);
		System.out.println("Questo è il mio id: "+ id);
		List<Entity> rs= travelDao.getData(travelE);
		System.out.println("1 " +rs.size());
		if(!rs.isEmpty())travelE = (TravelEntity)rs.get(0);
		else System.out.println("empty");
		TravelBean bean = new TravelBean(travelE);
		return bean;
	}
}