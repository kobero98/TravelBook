package main.java.travelbook.controller;



import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import main.java.travelbook.MyIdentity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.RegistrationBean;
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
		PersistanceDAO<UserEntity> userDao=DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE=new UserEntity();
		userE.setUsername(username);
		userE.setPassword(password);
		List<UserEntity> list = userDao.getData(userE);
		MyIdentity.getInstance().setMyEntity(list.get(0));
		user=new UserBean(MyIdentity.getInstance().getMyEntity());
		return user;
	}
	
	private int numberGenerator(){
		int x=0;
		int i;
		for(i=0;i<=5;i++)
		{
			Random r = new Random();
			int rand = r.nextInt(9);
			x=(int) (x+rand*Math.pow(10, i));
		}
		return x;
	}
	
	public String CalcoloRegistration(String email) {
		EmailSenderController s=new EmailSenderController();
		String code= Integer.toString(numberGenerator());
		try {
			s.sendMessage(email,code, "Codice Registrazione TravelBoook");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	
	public void signUp(RegistrationBean user) throws Exception{
		PersistanceDAO<UserEntity> userDao=DaoFactory.getInstance().create(DaoType.USER);
		UserEntity newUser= new UserEntity(user);
		userDao.setMyEntity(newUser);
		try {
			userDao.setData();
		} catch (Exception e) {
			throw e;
		}
		
		
		
	}


	



}