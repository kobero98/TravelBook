package main.java.travelbook.controller;



import main.java.travelbook.model.bean.UserBean;

public class ControllerLogin {
	private static ControllerLogin instance = null;
	
	private ControllerLogin() {}
	
	public static ControllerLogin getInstance() {
		if(instance==null)
			instance = new ControllerLogin();
		return instance;
	}
	
	private UserBean myUser=null;
	private String myErrore="";
	
	public String getMyError()
	{
		return this.myErrore;
	}
	
	public UserBean getMyUser()
	{
		return this.myUser;
	}
	
	



}