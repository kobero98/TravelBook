package main.java.travelbook.controller;

import java.sql.SQLException;
import main.java.travelbook.model.ShareEntity;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.ShareBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.VisualDAO;

public class ProfileController{
	
	public List<MiniTravelBean> getTravel(List<Integer> l) throws DBException{
		List<MiniTravelBean> ol = null;
		VisualDAO miniTravelDao = DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
		if (l != null) {
			for(int i=0; i<l.size(); i++) {
				TravelEntity travelE = new TravelEntity();
			
				travelE.setIdTravel(l.get(i));
				List<Entity> rs= miniTravelDao.getData(travelE);
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
	
	
	public List<String> getFollow(List<Integer> l) throws DBException{
		List<String> f = null;
		VisualDAO shortUserDao = DaoFactory.getInstance().createVisual(DaoType.S_USER);
		if(l != null) {
			for(int i=0; i<l.size(); i++) {
				UserEntity userE = new UserEntity(l.get(i));
				List<Entity> rs= shortUserDao.getData(userE);
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
	public List<String> getFav(List<Integer> l) throws DBException{
		List<String> f = null;
		VisualDAO miniTravelDao = DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
		TravelEntity travelE = new TravelEntity();
		if(l != null) {
			for(int i=0; i<l.size(); i++) {
				travelE.setIdTravel(l.get(i));
				List<Entity> rs= miniTravelDao.getData(travelE);
				travelE = (TravelEntity)rs.get(0);
				if(f==null) {
					f = new ArrayList<>();
					f.add(travelE.getNameTravel());
				}
				else {
					f.add(travelE.getNameTravel());
				}
				System.out.println(travelE.getNameTravel());
			}
		}
		return f;
	}
	public List<ShareBean> getShared(int userId) throws DBException{
		UserEntity us=new UserEntity(userId);
		List<ShareBean> results=new ArrayList<>();
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.SHARE);
		List<Entity> res=dao.getData(us);
		for(Entity ent:res) {
			ShareBean bean=new ShareBean((ShareEntity)ent);
			results.add(bean);
		}
		return results;
	}
}