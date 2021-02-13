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
		//testing that upon logging in, the logged user is the right one
		Boolean test=true;
		ControllerLogin controller=new ControllerLogin();
		UserBean user=controller.signIn(username, username);
		if(!user.getName().equals("Admin")) test=false;
		if(!user.getSurname().equals("Admin")) test=false;
		assertEquals(true,test);
	}
	@Test
	public void testSignUpNotWork() {
		//testing that if sign up is attempted with an already existing
		//email or username, the controller throws an exception
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
		//testing that upon trying login with an incorrect password
		//the controller throws an exception
		ControllerLogin controller=new ControllerLogin();
		assertThrows(DBException.class,()->controller.signIn(username, "non � la password"));
	}
}
