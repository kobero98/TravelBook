package travelbook.controller;

import java.sql.Date;

import travelbook.model.bean.UserBean;

public class ControllerLogin {
	private static ControllerLogin INSTANCE = null;
	
	private ControllerLogin() {}
	
	public ControllerLogin getInstance() {
		if(INSTANCE==null)
			INSTANCE= new ControllerLogin();
		return INSTANCE;
	}
	
	private UserBean myUser=null;
	private String MyErrore="";
	
	public String getMyError()
	{
		return this.MyErrore;
	}
	
	public UserBean getMyUser()
	{
		return this.myUser;
	}
	
	public Boolean SignIn(String Username, String Password)
	{
		DB database = new DB();
		try{
			myUser=database.login(Username,Password);
			return true;
		}catch(ExceptionLogin ex){
			this.MyErrore=ex.getMessage();
			return false;
		}catch(Exception e){
			this.MyErrore="Errore di Connessione";
			return false;
		}
		
	}
	public Boolean Regist(String Username,String password,String Name,String Surname,String Email,Date Data,String gender)
	{
		DB database = new DB();
		this.myUser=database.Register(Username,password, Name, Surname, Email, Data, gender);
		if(myUser!=null)return true;
		return false;
	}
	

}
