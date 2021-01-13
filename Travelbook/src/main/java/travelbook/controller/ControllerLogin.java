package main.java.travelbook.controller;



import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.LoginException;

import main.java.travelbook.MyIdentity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;

public class ControllerLogin {
	private static ControllerLogin instance = null;
	
	private ControllerLogin() {}
	
	public static ControllerLogin getInstance() {
		if(instance==null)
			instance = new ControllerLogin();
		return instance;
	}
	
	public UserBean signIn(String username,String password) throws SQLException, ExceptionLogin{
		UserBean user=new UserBean();
		DaoFactory factory=DaoFactory.getInstance();
		PersistanceDAO userDao=factory.create(DaoType.USER);
		UserEntity userE=new UserEntity();
		userE.setUsername(username);
		userE.setPassword(password);
		List<UserEntity> list = userDao.getData(userE);
		MyIdentity.getInstance().setMyEntity(list.get(0));
		user=new UserBean(MyIdentity.getInstance().getMyEntity());
		return user;
		
	}
	

	



}