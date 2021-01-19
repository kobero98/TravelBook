package main.java.travelbook.controller;

import java.sql.SQLException;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

public class ControllerProfileOther extends ProfileController{
	
	
	public UserBean getUser(int id) throws SQLException {
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		userE = (UserEntity)userDao.getData(userE).get(0);
		return new UserBean(userE);
	}
}