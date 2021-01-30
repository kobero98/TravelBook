package main.java.travelbook.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.Bean;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.VisualDAO;

public class ProfileController{
	
	public List<Bean> getTravel(List<Integer> l) throws DBException{
		List<Bean> ol = null;
		VisualDAO miniTravelDao = DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
		if (l != null) {
			for(int i=0; i<l.size(); i++) {
				TravelEntity travelE = new TravelEntity();
			
				travelE.setIdTravel(l.get(i));
				List<Entity> rs=new ArrayList<>();
				try {
					rs = miniTravelDao.getData(travelE);
				} catch (SQLException e) {
					throw new DBException("we couldn't find this information");
				}
				travelE = (TravelEntity)rs.get(0);
				MiniTravelBean bean = new MiniTravelBean(travelE);
				if(ol==null) {
					ol = new ArrayList<>();
					ol.add(bean);
				}
				else {
					ol.add(bean);
				}
			}
		}
		return ol;
	}
	
	
	public List<Bean> getFollow(List<Integer> l) throws DBException{
		List<Bean> f = null;
		VisualDAO shortUserDao = DaoFactory.getInstance().createVisual(DaoType.S_USER);
		if(l != null) {
			for(int i=0; i<l.size(); i++) {
				UserEntity userE = new UserEntity(l.get(i));
				List<Entity> rs=new ArrayList<>();
				try {
					rs = shortUserDao.getData(userE);
				
				} catch (SQLException e) {
					throw new DBException("we could't find this information");
				}
				userE = (UserEntity)rs.get(0);
				if(f==null) {
					f = new ArrayList<>();
					f.add(new UserBean(userE));
				}
				else {
					f.add(new UserBean(userE));
				}
			}	
		}
		return f;
	}
	public List<String> getFollowS(List<Integer> l) throws DBException{
		List<String> f = null;
		VisualDAO shortUserDao = DaoFactory.getInstance().createVisual(DaoType.S_USER);
		if(l != null) {
			for(int i=0; i<l.size(); i++) {
				UserEntity userE = new UserEntity(l.get(i));
				List<Entity> rs=new ArrayList<>();
				try {
					rs = shortUserDao.getData(userE);
				} catch (SQLException e) {
					throw new DBException("we could't find this information");
				}
				userE = (UserEntity)rs.get(0);
				if(f==null) {
					f = new ArrayList<>();
					f.add(userE.getName()+" "+userE.getSurname());
				}
				else {
					f.add(userE.getName()+" "+userE.getSurname());
				}
			}	
		}
		return f;
	}

	public List<String> getFavS(List<Integer> l) throws DBException{
		List<String> f = null;
		VisualDAO miniTravelDao = DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
		TravelEntity travelE = new TravelEntity();
		if(l != null) {
			for(int i=0; i<l.size(); i++) {
				travelE.setIdTravel(l.get(i));
				List<Entity> rs=new ArrayList<>();
				try {
					rs = miniTravelDao.getData(travelE);
				} catch (SQLException e) {
					throw new DBException("we couldn't find this information");
				}
				travelE = (TravelEntity)rs.get(0);
				if(f==null) {
					f = new ArrayList<>();
					f.add(travelE.getNameTravel());
				}
				else {
					f.add(travelE.getNameTravel());
				}
			}
		}
		return f;
	}

}