package main.java.travelbook.util;

import java.sql.SQLException;

import exception.DBException;
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
	@Override
	public void run() {
	
		while(goOn)
		{
			UserBean user=MenuBar.getInstance().getLoggedUser();
			VisualDAO dao=DaoFactory.getInstance().createVisual(DaoType.OTHERUSER);
			OtherUserEntity entity= new OtherUserEntity(id);
			try {
				entity=(OtherUserEntity) dao.getData(entity);
				if(user.get
				
				
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Thread.sleep(3000);
		}
		
	}
}
