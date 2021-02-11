package main.java.travelbook.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import exception.DBException;
import exception.FewParametersException;
import exception.MalformedEmailException;
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
		/*this contructor doesn't need param*/
	}
	
	public TravelBean getTravel(int id) throws DBException {
		PersistanceDAO travelDao = DaoFactory.getInstance().create(DaoType.TRAVEL);
		TravelEntity travelE = new TravelEntity();
		travelE.setIdTravel(id);
		List<Entity> rs;
		try {
			rs = travelDao.getData(travelE);
		} catch ( SQLException e) {
			throw new DBException("connection lost");
		}
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
				UserEntity res;
				try {
					res = (UserEntity)(dao.getData(entity).get(0));
				} catch ( SQLException e) {
					throw new DBException("connection lost");
				}
				UserBean u=new UserBean(res);
				u.setEmail(res.getEmail());
				contact.add(u);
			}
		}
		return contact;
	}
	public void shareTravel(List<UserBean> user, int travelId, int travelC, int userId) throws DBException {
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.SHARE);
		EmailSenderController c=new EmailSenderController();
		List<String> messages=new ArrayList<>();
		List<String> subj=new ArrayList<>();
		List<String> dest=new ArrayList<>();
		String sub="A new travel shared on Travelbook";
		for(UserBean us: user) {
			String emailStub="Dear "+us.getName()+" a your friend shared with you a travel, go on travelbook to view it!";
			messages.add(emailStub);
			dest.add(us.getEmail());
			subj.add(sub);
			ShareEntity sh=new ShareEntity();
			sh.setTravelShared(travelId);
			sh.setWhoShare(userId);
			sh.setWhoReceive(us.getId());
			sh.setCreator(travelC);
			dao.setMyEntity((Entity)sh);
			dao.setData();
		}
		try {
		c.sendMessage(dest, messages, subj);
		}catch(FewParametersException | MessagingException | MalformedEmailException e) {
			throw new DBException(e.getMessage());
		}
		
	}
	}