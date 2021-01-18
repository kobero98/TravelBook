package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.ExceptionLogin;
import exception.ExceptionRegistration;
import exception.LoginPageException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;

public class UserDao implements PersistanceDAO, PredictableDAO{

	private UserEntity entity;	
	private Connection connection;
	
	private UserEntity castRStoUser(ResultSet rs) throws SQLException
	{
		
		UserEntity user;
		user = new UserEntity(rs.getInt(1));
		user.setName(rs.getString(2));
		user.setSurname(rs.getString(3));
		user.setBirthDate(rs.getDate(4));
		user.setDescription(rs.getString(5));
		user.setEmail(rs.getString(6));
		user.setFollower(rs.getInt(7));
		user.setFollowing(rs.getInt(8));
		user.setNTravel(rs.getInt(9));
		user.setPhoto(rs.getBinaryStream(10));
		user.setGender(rs.getString(11));
		user.setNation(rs.getString(12));
		return user;
		
	}
	
	@Override
	public List <Entity> getData(Entity user1) throws SQLException {
		ResultSet rs=null;
		ResultSet favorite=null;
		Statement stmt=null;
		UserEntity user=(UserEntity) user1;
		AllQuery db=AllQuery.getInstance();
		List <Entity> list=new ArrayList<>();
		try {
			this.connection = AllQuery.getInstance().getConnection();
		} catch (SQLException e1) {
			throw new LoginPageException("errore connect");
		}
		try {
			stmt=connection.createStatement();
			if(user.getUsername()!=null && user.getPassword()!=null)
			{
				rs=db.requestLogin(stmt,user.getUsername(), user.getPassword());				
				UserEntity utente=castRStoUser(rs);
				list.add((Entity) utente);
				stmt.close();
				this.connection = AllQuery.getInstance().getConnection();
				stmt=this.connection.createStatement();
				AllQuery.getInstance().requestListIDFavoriteTrip(stmt,utente.getId());	
					
			}
			
		}finally {
			if(stmt!=null)
			{
				try {
					stmt.close();
				}catch(SQLException e) {
					e.getStackTrace();
				}
				
			}
		}
 		
		
		return list;
	}

	@Override
	public void setData() throws SQLException{
		if(this.entity!=null)
		{
			try {

				System.out.println("inizio il set della Dao");
				this.connection = AllQuery.getInstance().getConnection();
				AllQuery.getInstance().requestRegistrationUser(this.connection, this.entity);
				System.out.println("finisco il get della Dao");
			} catch (SQLException e) {
				throw new ExceptionRegistration("Errore registrazione");
			}
			
		}
	}
	
	
	
	@Override
	public void setMyEntity(Entity user) {
		this.entity=(UserEntity) user;
	}
	
	@Override
	public UserEntity getMyEntity() {
		
		return this.entity;
	}
	
	@Override
	public void delete(Entity object) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Entity object)throws SQLException {
		this.entity= (UserEntity) object;
		this.connection = AllQuery.getInstance().getConnection();
		if(this.entity.getDescription()!=null)
			AllQuery.getInstance().updateDescriptionUser(connection, this.entity.getId(), this.entity.getDescription());
		if(this.entity.getPhoto()!=null)
			AllQuery.getInstance().updatePhotoProfile(connection, this.entity.getId(), this.entity.getPhoto());
		if(this.entity.getBirthDate()!=null)
			System.out.println("ciao1");

		if(this.entity.getName()!=null)
			System.out.println("ciao1");

		if(this.entity.getSurname()!=null)
			System.out.println("ciao1");

		if(this.entity.getPassword()!=null)
			System.out.println("ciao1");
	}
	
	
	@Override
	public List<Entity> getPredictions(String text){
		List<Entity> predictions=new ArrayList<>();
		ResultSet rs;
		try {
			this.connection = AllQuery.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement stmt=connection.createStatement();
			rs=AllQuery.getInstance().userAutocompleteRequest(stmt, text);
			if(rs!=null) {
				UserEntity localEntity;
				while(rs.next()) {
					localEntity=new UserEntity();
					localEntity.setUsername(rs.getString(3));
					localEntity.setName(rs.getString(1));
					localEntity.setSurname(rs.getString(2));
					predictions.add(localEntity);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return predictions;
	}

	
}
