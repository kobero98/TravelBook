package main.java.travelbook.controller;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.VisualDAO;
import java.util.List;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.UserEntity;
import exception.DBException;
import java.sql.SQLException;
public class ExploreController {
	public void setSuggests(List<MiniTravelBean> travels,UserBean myUser) throws DBException {
		VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.EXPLORE);
		UserEntity user=new UserEntity(myUser.getId());
		user.setListFollower(myUser.getFollower());
		user.setListFollowing(myUser.getFollowing());
		user.setUsername(myUser.getUsername());
		try {
		List<Entity> results=dao.getData(user);
		getResults(travels, results);
		
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	public void setTopTen(List<MiniTravelBean> travels)throws DBException {
			//da implementare
			VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.EXPLORE);
			UserEntity user=new UserEntity(-1);
			try {
				List<Entity> results=dao.getData(user);
				getResults(travels,results);
			}catch(SQLException e) {
				e.printStackTrace();
				throw new DBException(e.getMessage());
			}
		}
	private void getResults(List<MiniTravelBean> travels, List<Entity> results) {
		Integer count=0;
		for(Entity en: results) {
			TravelEntity travel=(TravelEntity)en;
			travels.get(count).setImageStream(travel.getImage());
			travels.get(count).setDescriptionTravel(travel.getDescriptionTravel());
			travels.get(count).setNameTravel(travel.getNameTravel());
			travels.get(count).setId(travel.getIdTravel());
			travels.get(count).setChanged();
			count++;
		}
	}
}
