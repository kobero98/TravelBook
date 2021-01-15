package main.java.travelbook.controller;



import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;
import main.java.travelbook.model.Entity;
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

	public UserBean signIn(String username,String password) throws SQLException{
		UserBean user=null;
		PersistanceDAO userDao=DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE=new UserEntity();
		userE.setUsername(username);
		userE.setPassword(password);
		List<Entity> list = userDao.getData(userE);
		MyIdentity.getInstance().setMyEntity((UserEntity) list.get(0));
		user=new UserBean(MyIdentity.getInstance().getMyEntity());
		return user;
	}
	
	private int numberGenerator(){
		int x=0;
		for(int i=0;i<=5;i++)
		{
			int rand =  new Random().nextInt(9);
			x=(int) (x+rand*Math.pow(10, i));
		}
		return x;
	}
	
	public String calcoloRegistration(String email) {
		EmailSenderController s=new EmailSenderController();
		String code= Integer.toString(numberGenerator());
		if(code.length()<6)
			{
				int j=6-code.length();
				for(int i=0;i<j;i++) code="0"+code;
			}
		try {
			s.sendMessage(email,code, "Codice Registrazione TravelBoook");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public void signUp(RegistrationBean user) throws SQLException{
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity newUser= new UserEntity(user);
		userDao.setMyEntity(newUser);
		userDao.setData();
	}


	



}