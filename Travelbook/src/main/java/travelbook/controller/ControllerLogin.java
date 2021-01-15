package main.java.travelbook.controller;



import java.sql.SQLException;
import java.security.MessageDigest;
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
	private String passwordHash(String pswd)throws Exception {
		MessageDigest hasher=MessageDigest.getInstance("SHA-1");
		hasher.update(pswd.getBytes("UTF-8"));
		return toHex(hasher.digest());
	}
	private static String toHex(byte[] data)  {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			String digit = Integer.toString(b & 0xFF, 16);
 
			if (digit.length() == 1) {
				sb.append("0");
			}
			sb.append(digit);
		}
		return sb.toString();
	}
	public UserBean signIn(String username,String password) throws SQLException{
		UserBean user=null;
		PersistanceDAO userDao=DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE=new UserEntity();
		userE.setUsername(username);
		try {
		userE.setPassword(this.passwordHash(password));
		}catch(Exception e) {
			throw new SQLException(e.getMessage());
		}
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
				for(int i=0;i<j;i++) code="0".concat(code);
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
		try {
		newUser.setPassword(this.passwordHash(user.getPassword()));
		}catch(Exception e) {
			throw new SQLException(e.getMessage());
		}
		userDao.setMyEntity(newUser);
		userDao.setData();
	}


	



}