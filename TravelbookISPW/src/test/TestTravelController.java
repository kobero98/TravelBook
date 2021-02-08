package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import exception.DBException;
import main.java.travelbook.controller.TravelController;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.model.bean.UserBean;

/*
 * @author Sara Da Canal
 */
public class TestTravelController {
	@Test
	public void testStepInDay() {
		//test if the returned list contains only the expected elements and if they are in the expected order
		List<StepBean> test = new ArrayList<>();
		int j=0;
		for(int i=0;i<10;i++) {
			StepBean s = new StepBean();
			s.setGroupDay(i%2);
			s.setNumberInDay(j);
			if(i%2!=0) j++;
			test.add(s);
		}
		TravelController controller = new TravelController();
		List<StepBean> result = controller.stepInDay(test, 0);
		boolean equal=true;
		for(int i=0;i<result.size();i++) 
			if(result.get(i).getGroupDay()!=0 || result.get(i).getNumberInDay()!=i)
				equal = false;
		assertEquals(equal,true);
	}
	@Test
	public void testGetContactSharing() throws DBException {
		//test if all the returned contact follow admin and are followed by admin
		TravelController controller = new TravelController();
		UserBean user = new UserBean(6);
		List<Integer> follower = new ArrayList<>();
		follower.add(19);
		follower.add(31);
		follower.add(8);
		user.setFollower(follower);
		List<Integer> following = new ArrayList<>();
		following.add(19);
		following.add(13);
		user.setFollowing(following);
		List<UserBean> test = controller.getContactSharing(user);
		
		assertEquals(true,(test.size()==1 && test.get(0).getId()==19));
	}
	//TODO cambiare id quando database fatto bene
}
