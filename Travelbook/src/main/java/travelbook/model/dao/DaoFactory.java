package main.java.travelbook.model.dao;


import main.java.travelbook.model.bean.RegistrationBean;
import main.java.travelbook.model.bean.TravelBean;

import java.sql.Date;

import main.java.travelbook.model.*;
import main.java.travelbook.model.bean.UserBean;

public class DaoFactory {

	private static DaoFactory instance=null;
	
	private DaoFactory(){}

	public static DaoFactory getInstance() {
		if(instance==null) instance=new DaoFactory();
		return instance;
	}
	
	public PersistanceDAO create(DaoType tipo){
		PersistanceDAO dao=null;
		if(tipo.compareTo(DaoType.USER)==0)
			dao=(PersistanceDAO<UserEntity>)new UserDao();
		if(tipo.compareTo(DaoType.TRAVEL)==0)
			dao=new TravellDao();
		if(tipo.compareTo(DaoType.CITY)==0)
			System.out.print("ciao1");
		if(tipo.compareTo(DaoType.MESSAGE)==0)
			System.out.print("ciao1");
		if(tipo.compareTo(DaoType.STEP)==0)
			System.out.print("ciao1");
		return dao;
	}
	public PredictableDAO createPredictable(DaoType tipo) {
		PredictableDAO dao=null;
		if(tipo==DaoType.CITY) 
			dao=new CityDao();
		if(tipo==DaoType.USER)
			dao=new UserDao();
		return dao;
	}

		

}
