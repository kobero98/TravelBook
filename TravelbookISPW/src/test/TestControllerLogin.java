package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.sql.Date;
import org.junit.Test;

import main.java.exception.DBException;
import main.java.exception.LoginPageException;
import main.java.travelbook.controller.ControllerLogin;
import main.java.travelbook.model.bean.RegistrationBean;
import main.java.travelbook.model.bean.UserBean;

/*
 * @author Matteo Federico aka Kobero
 */
public class TestControllerLogin {
	String username="admin";
	@Test
	public void testSignInWork() throws LoginPageException {
		Boolean test=true;
		ControllerLogin controller=new ControllerLogin();
		UserBean user=controller.signIn(username, username);
		if(!user.getName().equals("Admin")) test=false;
		if(!user.getSurname().equals("Admin")) test=false;
		assertEquals(true,test);
	}
	@Test
	public void testSignUpNotWork() {
		RegistrationBean user=new RegistrationBean();
		user.setName(username);
		user.setSurname(username);
		user.setBirtdate(new Date(0));
		user.setUsername(username);
		user.setPassword("ciao");
		user.setEmail("travelbookispw@outlook.com");
		user.setGender("a");
		user.setNazionalita("Italiana");
		ControllerLogin controller= new ControllerLogin();
		assertThrows(DBException.class,()->controller.signUp(user));
	}
	
	@Test
	public void testSignInNotWork(){
		ControllerLogin controller=new ControllerLogin();
		assertThrows(DBException.class,()->controller.signIn(username, "non é la password"));
	}
}
