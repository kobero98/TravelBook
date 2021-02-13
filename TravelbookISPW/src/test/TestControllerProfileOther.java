package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.exception.DBException;
import main.java.travelbook.controller.ControllerProfileOther;
import main.java.travelbook.model.bean.UserBean;

/*
 * @author Sara Da Canal
 */
public class TestControllerProfileOther {
	@Test
	public void testGetUser() throws DBException {
		//testing method getUser by checking it effectively returns admin information
		ControllerProfileOther controller = new ControllerProfileOther();
		UserBean u=controller.getUser(1);
		boolean exact = true;
		if(u.getId()!=1)
			exact = false;
		if(!u.getName().equals("Admin"))
			exact=false;
		if(!u.getSurname().equals("Admin"))
			exact=false;
		assertEquals(true,exact);
	}
	
	@Test
	public void testUpdateFollow() {
		//checking that the update follow return an exception when trying to put
		//into the database id that are not linked to any user
		ControllerProfileOther controller = new ControllerProfileOther();
		
		assertThrows(DBException.class, ()->controller.updateFollow(2, -1));
	}
	@Test
	public void testUpdateFav() {
		//checking that the update fav return an exception when trying to put
		//into the database id that are not linked to any travel
		ControllerProfileOther controller = new ControllerProfileOther();
		UserBean u = new UserBean(2);
		List<Integer> f = new ArrayList<>();
		f.add(-1);
		u.setFav(f);
		assertThrows(DBException.class, ()->controller.updateFav(u));
	}

	
}
