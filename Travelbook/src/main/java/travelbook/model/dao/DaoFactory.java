package main.java.travelbook.model.dao;


import main.java.travelbook.model.bean.TravelBean;
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
			dao=new UserDao();
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

		

}
