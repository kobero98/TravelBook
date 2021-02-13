package main.java.travelbook.util;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import main.java.exception.DBException;
import main.java.travelbook.model.OtherUserEntity;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.VisualDAO;
import main.java.travelbook.view.MenuBar;


public class ProfilePollingThread extends Thread{
	private boolean goOn=true;
	public void kill() {
		this.goOn=false;
	}
	private Boolean controllo(List<Integer> l1,List<Integer> l2) {
		if(l1.size()!=l2.size()) return false;
		Collections.sort(l1);
		Collections.sort(l2);
		for(int i=0;i<l1.size();i++) if(!l1.get(i).equals(l2.get(i))) return false;
		return true;
	}
	@Override
	public void run() {
	try {
			while(goOn){
				UserBean user=MenuBar.getInstance().getLoggedUser();
				VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.OTHERUSER);
				OtherUserEntity entity= new OtherUserEntity(user.getId());
				entity=(OtherUserEntity) dao.getData(entity).get(0);
				if(entity.getFavoriteList()!=null && Boolean.FALSE.equals(controllo(entity.getFavoriteList(),user.getFav()))) user.setFav(entity.getFavoriteList());
				if(entity.getListFollower()!=null && Boolean.FALSE.equals(controllo(entity.getListFollower(),user.getFollower())))	user.setFollower(entity.getListFollower());
				if(entity.getListFollowing()!=null && Boolean.FALSE.equals(controllo(entity.getListFollowing(), user.getFollowing()))) user.setFollowing(entity.getListFollowing());	
				if(user.getTravel()!=null && Boolean.FALSE.equals(controllo(user.getTravel(),entity.getTravel()))) user.setTravel(entity.getTravel());
				Thread.sleep(3000);
			}
		} catch (DBException | SQLException | InterruptedException e) {
			Thread.currentThread().interrupt();
		} 
	}
}
