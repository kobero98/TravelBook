package main.java.travelbook.controller;

import java.io.File;
import java.sql.SQLException;

import exception.DBException;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

public class MyProfileController extends ProfileController{

		public void updateDescr(int id,String descr) throws DBException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		userE.setDescription(descr);
		userDao.update(userE);
	}
	
	public void updatePhoto(int id,File foto) throws DBException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		userE.setPhoto(foto);
		userDao.update(userE);
		
	}
}