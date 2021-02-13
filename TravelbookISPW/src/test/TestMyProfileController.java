package test;
import static org.junit.Assert.*;

import org.junit.Test;

import main.java.exception.DBException;
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
		UserBean adminBean = controller.getUser(1);
		boolean equal = true;
		if(adminBean.getId()!=1) {
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
		assertThrows(DBException.class, ()->controller.updateDescr(1, text));
	}
	@Test
	public void testGetTravel() throws DBException{
		//test if getTravel method return expected result, trying with a known travel
		MyProfileController controller = new MyProfileController();
		MiniTravelBean bean = controller.getTravel(76);
		boolean equal = true;
		if(bean.getId()!=76) {
			equal=false;
		}
		if(!bean.getNameTravel().equals("San Valentino paris")) {
			equal = false;
		}
		assertEquals(equal,true);
	}
}
