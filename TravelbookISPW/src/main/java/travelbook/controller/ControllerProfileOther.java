package main.java.travelbook.controller;


import exception.DBException;
import main.java.travelbook.model.OtherUserEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.VisualDAO;

public class ControllerProfileOther extends ProfileController{
	
	
	public UserBean getUser(int id) throws DBException {
		VisualDAO userDao = DaoFactory.getInstance().createVisual(DaoType.OTHERUSER);
		OtherUserEntity userE = new OtherUserEntity(id);
		userE = (OtherUserEntity)userDao.getData(userE).get(0);
		return new UserBean(userE);
	}
	public void updateFav(UserBean u) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(u.getId());
		userE.setFavoriteList(u.getFav());
		userDao.update(userE);
	}
	public void updateFollow(UserBean u) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(u.getId());
		userE.setListFollowing(u.getFollowing());
		userDao.update(userE);
	}
}