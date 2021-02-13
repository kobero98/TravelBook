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
		UserBean u=controller.getUser(6);
		boolean exact = true;
		if(u.getId()!=6)
			exact = false;
		if(!u.getName().equals("Admin"))
			exact=false;
		if(!u.getSurname().equals("Admin"))
			exact=false;
		List<Integer> follower = new ArrayList<>();
		follower.add(19);
		List<Integer> following = new ArrayList<>();
		following.add(19);
		following.add(13);
		for(Integer i:u.getFollower())
			if (!follower.contains(i))
				exact=false;
		for(Integer j:u.getFollowing())
			if(!following.contains(j))
				exact=false;
		assertEquals(true,exact);
	}
	@Test
	public void testUpdateFollowAdd() throws DBException {
		//checking if the followers get added, using the method getUser that has been tested before
		//to retrieve data from the database and check if they are correct
		ControllerProfileOther controller = new ControllerProfileOther();
		controller.updateFollow(6, 25);
		UserBean u=controller.getUser(6);
		boolean check = u.getFollowing().contains(25);
		assertEquals(true,check);
	}
	@Test
	public void testUpdateFollowRemove() throws DBException{
		//now checking if the same function removes the element when it is already there, an id that we know
		//is in the database
		ControllerProfileOther controller = new ControllerProfileOther();
		controller.updateFollow(6, 13);
		UserBean u=controller.getUser(6);
		boolean check = !u.getFollowing().contains(13);
		assertEquals(true,check);
	}
	
}
