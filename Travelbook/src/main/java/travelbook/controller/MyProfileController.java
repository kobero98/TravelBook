package main.java.travelbook.controller;

import java.io.File;
import java.sql.SQLException;

import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

public class MyProfileController extends ProfileController{

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
}