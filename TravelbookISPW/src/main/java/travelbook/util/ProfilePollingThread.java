package main.java.travelbook.util;

import main.java.travelbook.model.bean.UserBean;
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
			
		}
	}
}
