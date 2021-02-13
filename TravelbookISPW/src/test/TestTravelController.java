package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.exception.DBException;
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
	public void testGetContactSharing(){
		//test if this method returns an exception when the requested id are not linked to any user
		TravelController controller = new TravelController();
		UserBean user = new UserBean(1);
		List<Integer> follower = new ArrayList<>();
		follower.add(-1);
		user.setFollower(follower);
		List<Integer> following = new ArrayList<>();
		following.add(-1);
		user.setFollowing(following);
		
		assertThrows(DBException.class,()->controller.getContactSharing(user));
	}
}
