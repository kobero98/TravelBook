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
	
	public PersistanceDAO create(Object bean){
		PersistanceDAO dao=null;
		if(bean instanceof UserBean)
			System.out.print("ciao");
		if(bean instanceof TravelBean)
			System.out.print("ciao1");
		return dao;
	}


}
