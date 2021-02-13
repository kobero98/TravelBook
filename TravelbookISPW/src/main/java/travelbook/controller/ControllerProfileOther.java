package main.java.travelbook.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.exception.DBException;
import main.java.travelbook.model.OtherUserEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.VisualDAO;

public class ControllerProfileOther extends ProfileController{

	
	public UserBean getUser(int userId) throws DBException{
		return this.getUser((Integer)userId);
	}

	public UserBean getUser(Integer userId) throws DBException {

		VisualDAO userDao = DaoFactory.getInstance().createVisual(DaoType.OTHERUSER);
		OtherUserEntity userE = new OtherUserEntity(userId);
		try {
			userE = (OtherUserEntity)userDao.getData(userE).get(0);
		} catch ( SQLException e) {
			throw new DBException("connection lost");
		}
		return new UserBean(userE);
	}
	public void updateFav(UserBean u) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(u.getId());
		userE.setFavoriteList(u.getFav());
		userDao.update(userE);
	}
	public void updateFollow(Integer myId, Integer userId) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(myId);
		List<Integer> f= new ArrayList<>();
		f.add(userId)		;
		userE.setListFollowing(f);
		userDao.update(userE);
	}
}