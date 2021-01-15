package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

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
	
	public ObservableList<TravelBean> getTravel(List<Integer> l){
		ObservableList<TravelBean> ol = null;
		//TODO query al database
		return ol;
	}
	
	public void updateDescr(UserBean user) throws SQLException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity();
		userE.setDescription(user.getDescription());
		userDao.setMyEntity(userE);
		userDao.setData();//TODO update con la dao		
	}
	
	public void updatePhoto(UserBean user) throws SQLException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity();
		userE.setPhoto(user.getPhoto());
		userDao.setMyEntity(userE);
		userDao.setData();//TODO update con la dao
	}
}