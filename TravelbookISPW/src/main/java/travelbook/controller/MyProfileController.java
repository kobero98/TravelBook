package main.java.travelbook.controller;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.ShareEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.Bean;
import main.java.travelbook.model.bean.MiniTravelBean;
import main.java.travelbook.model.bean.ShareBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.VisualDAO;

public class MyProfileController extends ProfileController{

		public void updateDescr(int id,String descr) throws DBException {
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(id);
		userE.setDescription(descr);
		userDao.update(userE);
	}
	
	public void updatePhoto(int id,InputStream bytes) throws DBException {
		PersistanceDAO userDao=DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE=new UserEntity(id);
		userE.setPhoto(bytes);
		userDao.update(userE);
	}
	public List<Bean> getShared(int userId) throws DBException{
		ShareEntity us=new ShareEntity();
		us.setWhoReceive(userId);
		List<Bean> results=new ArrayList<>();
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.SHARE);
		List<Entity> res;
		try {
			res = dao.getData(us);
		} catch (SQLException e) {
			throw new DBException("connection lost");
		}
		for(Entity ent:res) {
			ShareBean bean=new ShareBean((ShareEntity)ent);
			results.add(bean);
		}
		return results;
	}
	
	public MiniTravelBean getTravel(Integer id) throws DBException{
		VisualDAO miniTravelDao = DaoFactory.getInstance().createVisual(DaoType.S_TRAVEL);
		TravelEntity travelE = new TravelEntity();
		travelE.setIdTravel(id);
		TravelEntity rs;
		try {
			rs = (TravelEntity)miniTravelDao.getData(travelE).get(0);
		} catch ( SQLException e) {
			throw new DBException("connection lost, we can't upload this travel");
		}
		return new MiniTravelBean(rs);

	}
	public UserBean getUser(Integer userId) throws DBException{
		VisualDAO shortUserDao = DaoFactory.getInstance().createVisual(DaoType.S_USER);
		UserEntity userE = new UserEntity(userId);
		UserEntity rs;
		try {
			rs = (UserEntity)shortUserDao.getData(userE).get(0);
		} catch (SQLException e) {
			throw new DBException("connection lost");
		}
		return new UserBean(rs);

	}
	public void deleteTravel(int id) throws DBException{
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.TRAVEL);
		TravelEntity t=new TravelEntity();
		t.setIdTravel(id);
		dao.delete(t);
	}

}