package main.java.travelbook.model.dao;

public class DaoFactory {

	private static DaoFactory instance=null;
	
	private DaoFactory(){}

	public static DaoFactory getInstance() {
		if(instance==null) instance=new DaoFactory();
		return instance;
	}
	
	public PersistanceDAO create(DaoType tipo){
		if(tipo.compareTo(DaoType.USER)==0) 
			return new UserDao();
		if(tipo.compareTo(DaoType.TRAVEL)==0) 
			return new TravellDao();
		if(tipo.compareTo(DaoType.CITY)==0)
			return new CityDao();
		if(tipo.compareTo(DaoType.MESSAGE)==0)
			return new MessageDao();
		if(tipo.compareTo(DaoType.STEP)==0)
			return new StepDao();
		if(tipo.compareTo(DaoType.S_TRAVEL)==0) {
			return new ShortTravelDao();
		}
		if(tipo.compareTo(DaoType.S_USER)==0){
			return new ShortUserDao();
		}
		return null;
		
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
