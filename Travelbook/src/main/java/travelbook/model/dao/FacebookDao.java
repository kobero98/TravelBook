package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.UserEntity;

public class FacebookDao extends UserDao{
	
	public UserEntity getData(String id) throws SQLException{
		UserEntity user=AllQuery.getInstance().controlloEsistenzaAccount(id);
		return user;
	}
	public UserEntity setData(String idFacebook,int id) throws SQLException
	{
		Connection connessione=AllQuery.getInstance().getConnection();
		return AllQuery.getInstance().insertFacebookUser(connessione,idFacebook,id);
		
	}
}
