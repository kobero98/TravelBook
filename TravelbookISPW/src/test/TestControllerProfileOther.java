package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import exception.DBException;
import main.java.travelbook.controller.ControllerProfileOther;
import main.java.travelbook.model.bean.UserBean;

/*
 * @author Sara Da Canal
 */
public class TestControllerProfileOther {
	@Test
	public void testUpdateFollow() {
		ControllerProfileOther controller = new ControllerProfileOther();
		UserBean u = new UserBean(6);
		List<Integer> f = new ArrayList<>();
		f.add(19);
		u.setFollowing(f);
		//assertThrows(DBException.class, ()->controller.updateFollow(u));
	}
	
}
