package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.util.NumberInDayComparator;

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
	
	public TravelBean getTravel(int id) throws DBException {
		PersistanceDAO travelDao = DaoFactory.getInstance().create(DaoType.TRAVEL);
		TravelEntity travelE = new TravelEntity();
		travelE.setIdTravel(id);
		List<Entity> rs= travelDao.getData(travelE);
		if(!rs.isEmpty())travelE = (TravelEntity)rs.get(0);
		return new TravelBean(travelE);
	}
	
	public List<StepBean> stepInDay(List<StepBean> s, Integer n){
		List<StepBean> l = new ArrayList<>();
		for(int i=0; i<s.size(); i++) {
			if(s.get(i).getGroupDay() == n) l.add(s.get(i));
		}
		sortStepinDay(l);
		return l;
	}
	
	private void sortStepinDay(List<StepBean> s) {
		s.sort(new NumberInDayComparator());
	}
	
	public void updateFav(UserBean u) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(u.getId());
		userE.setFavoriteList(u.getFav());
		userDao.update(userE);
		}
	}