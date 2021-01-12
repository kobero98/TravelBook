package main.java.travelbook.controller;



import java.sql.SQLException;

import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.PersistanceDAO;

public class ControllerLogin {
	private static ControllerLogin instance = null;
	
	private ControllerLogin() {}
	
	public static ControllerLogin getInstance() {
		if(instance==null)
			instance = new ControllerLogin();
		return instance;
	}
	
	public UserBean signIn(String username,String password) throws LoginException,SQLException{
		UserBean user=null;
		DaoFactory factory=DaoFactory.getInstance();
		PersistanceDAO userDao=factory.create(user);
		
		MyIdentity.getInstance().setMyEntity((UserEntity) UserDao.getData(userE));
		
	
		
		return null;
		
	}
	
	



}