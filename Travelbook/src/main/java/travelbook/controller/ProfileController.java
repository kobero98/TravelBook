package main.java.travelbook.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.ShortUserDao;

public class ProfileController{
	private static ProfileController instance = null;
	
	private ProfileController() {
		
	}
	
	public static ProfileController getInstance() {
		if(instance == null) {
			instance = new ProfileController();
		}
		return instance;
	}
	
	public List<MiniTravelBean> getTravel(List<Integer> l) throws SQLException{
		List<MiniTravelBean> ol = null;
		PersistanceDAO miniTravelDao = DaoFactory.getInstance().create(DaoType.S_TRAVEL);
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
	
	public void updateDescr(int id,String descr) throws SQLException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		userE.setDescription(descr);
		System.out.println("metto la descrizione");
		userDao.update(userE);
	}
	
	public void updatePhoto(int id,File foto) throws SQLException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		System.out.print("metto la foto");
		userE.setPhoto(foto);
		userDao.update(userE);
	}
	
	public List<String> getFollow(List<Integer> l) throws SQLException{
		List<String> f = null;
		PersistanceDAO shortUserDao = DaoFactory.getInstance().create(DaoType.S_USER);
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
	public List<String> getFav(List<Integer> l) throws SQLException{
		List<String> f = null;
		PersistanceDAO miniTravelDao = DaoFactory.getInstance().create(DaoType.TRAVEL);
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
			}
		}
		return f;
	}
}