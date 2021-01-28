package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.ShareEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.StepBean;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.VisualDAO;
import main.java.travelbook.util.NumberInDayComparator;

public class TravelController{
	
	
	public TravelController() {
		
	}
	
	public TravelBean getTravel(int id) throws DBException {
		PersistanceDAO travelDao = DaoFactory.getInstance().create(DaoType.TRAVEL);
		TravelEntity travelE = new TravelEntity();
		travelE.setIdTravel(id);
		List<Entity> rs= travelDao.getData(travelE);
		if(!rs.isEmpty())travelE = (TravelEntity)rs.get(0);
		return new TravelBean(travelE);
	}
	
	public List<StepBean> stepInDay(List<StepBean> s, Integer n){
		List<StepBean> l = new ArrayList<>();
		for(int i=0; i<s.size(); i++) {
			if(s.get(i).getGroupDay() == n) l.add(s.get(i));
		}
		sortStepinDay(l);
		return l;
	}
	
	private void sortStepinDay(List<StepBean> s) {
		s.sort(new NumberInDayComparator());
	}
	
	public void updateFav(UserBean u) throws DBException{
		PersistanceDAO userDao = DaoFactory.getInstance().create(DaoType.USER);
		UserEntity userE = new UserEntity(u.getId());
		userE.setFavoriteList(u.getFav());
		userDao.update(userE);
		}
	public List<UserBean> getContactSharing(UserBean myUser) throws DBException{
		List<UserBean> contact=new ArrayList<>();
		List<Integer> followFollowing=new ArrayList<>();
		if(myUser.getFollowing()!=null) {
			for(Integer seguito: myUser.getFollowing()) {
				if(myUser.getFollower().contains(seguito)) {
					followFollowing.add(seguito);
				}
			}
			VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.S_USER);
			for(Integer fol:followFollowing) {
				UserEntity entity=new UserEntity(fol);
				UserEntity res=(UserEntity)(dao.getData(entity).get(0));
				contact.add(new UserBean(res));
			}
		}
		return contact;
	}
	public void shareTravel(List<Integer> user, int travelId, int travelC, int userId) throws DBException {
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.SHARE);
		for(Integer us: user) {
			ShareEntity sh=new ShareEntity();
			sh.setTravelShared(travelId);
			sh.setWhoShare(userId);
			sh.setWhoReceive(us);
			sh.setCreator(travelC);
			dao.setMyEntity((Entity)sh);
			dao.setData();
		}
		
		
	}
	}