package main.java.travelbook.controller;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.dao.CityDao;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PredictableDAO;
import java.util.List;
import java.util.ArrayList;
public class SearchTravel {
	public static SearchTravel istance=new SearchTravel();
	private SearchTravel() {
		
	}
	public static SearchTravel getIstance() {
		return istance;
	}
	public List<String> getCitiesPredictions(String text) {
		PredictableDAO<CityEntity> dao=(PredictableDAO<CityEntity>) DaoFactory.getInstance().createPredictable(DaoType.CITY);
		List<String> results=new ArrayList<>();
		List<CityEntity> predictions=dao.getPredictions(text);
		String singleResult;
		for(CityEntity entity: predictions) {
			singleResult=entity.getNameC()+","+entity.getState();
			results.add(singleResult);
		}
		return results;
	}
}
