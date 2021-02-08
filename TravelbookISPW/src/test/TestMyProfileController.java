package test;
import static org.junit.Assert.*;

import org.junit.Test;

import exception.DBException;
import main.java.travelbook.controller.MyProfileController;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.UserBean;

/*
 * @author Sara Da Canal
 */
public class TestMyProfileController {
	@Test
	public void testGetUser() throws DBException {
		//Test if getUser method return expected result, trying with a known user (id 1 is associated with our first user, used for test)
		MyProfileController controller = new MyProfileController();
		UserBean adminBean = controller.getUser(6);
		boolean equal = true;
		if(adminBean.getId()!=6) {
			equal=false;
		}
		if(!adminBean.getName().equals("Admin")){
			equal=false;
		}
		if(!adminBean.getSurname().equals("Admin")){
			equal=false;
		}
		assertEquals(equal,true);
	}
	@Test
	public void testUpdateDescription() {
		//Test if DBException is correctly thrown when the description is too long
		MyProfileController controller=new MyProfileController();
		String text="a".repeat(151);
		assertThrows(DBException.class, ()->controller.updateDescr(6, text));
	}
	//TODO cambiare id per updateDescr, getUser e controllo con 1 quando admin avrà 1
	//cambiare viaggio con primo viaggio nel db
	@Test
	public void testGetTravel() throws DBException{
		//test if getTravel method return expected result, trying with a known travel
		MyProfileController controller = new MyProfileController();
		MiniTravelBean bean = controller.getTravel(69);
		boolean equal = true;
		if(bean.getId()!=69) {
			equal=false;
		}
		if(!bean.getNameTravel().equals("primoViaggio")) {
			equal = false;
		}
		assertEquals(equal,true);
	}
}
