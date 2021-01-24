package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import exception.ExceptionRegistration;
import exception.LoginPageException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;
public class UserDao implements PersistanceDAO, PredictableDAO{

	private UserEntity entity;	
	private Connection connection;
	
	protected UserEntity castRStoUser(ResultSet rs) throws SQLException
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
	public List <Entity> getData(Entity user1) throws LoginPageException {
		ResultSet rs=null;
		Statement stmt=null;
		UserEntity user=(UserEntity) user1;
		AllQuery db=AllQuery.getInstance();
		List <Entity> list=new ArrayList<>();
		try {
			this.connection = AllQuery.getInstance().getConnection();
		}catch (SQLException e1) {
			throw new LoginPageException("we couldn't reach our servers");
		}
		try {
			stmt=connection.createStatement();
			rs=db.requestLogin(stmt,user.getUsername(), user.getPassword());				
			UserEntity utente=castRStoUser(rs);
			stmt.close();
			stmt=this.connection.createStatement();
			rs=AllQuery.getInstance().requestListIDFavoriteTrip(stmt,utente.getId());	
			List <Integer> fav=new ArrayList<>();
			while(rs.next())
			{
				fav.add(rs.getInt(1));
			}
			utente.setFavoriteList(fav);
			stmt.close();
			stmt=this.connection.createStatement();
			rs=AllQuery.getInstance().requestListFollowerUser(stmt,utente.getId());	
			List <Integer> follower=new ArrayList<>();
			while(rs.next())
			{
				follower.add(rs.getInt(1));
			}
			utente.setListFollower(follower);
			stmt.close();
			stmt=this.connection.createStatement();
			rs=AllQuery.getInstance().requestListFollowingUser(stmt,utente.getId());	
			List <Integer> following=new ArrayList<>();
			while(rs.next())
			{
				following.add(rs.getInt(1));
			}
			utente.setListFollowing(following);
			stmt.close();
			stmt=this.connection.createStatement();
			rs=AllQuery.getInstance().requestTripByUser(stmt, utente.getId());	
			List <Integer> travel=new ArrayList<>();
			while(rs.next())
			{
				travel.add(rs.getInt(1));
			}
			utente.setTravel(travel);
			stmt.close();
			stmt=this.connection.createStatement();
			utente.setnPlace(AllQuery.getInstance().getPlaceVisited(stmt,utente.getId()));
			stmt.close();
			list.add((Entity) utente);
			return list;
		}catch(LoginPageException e){
			throw e;
		}catch(SQLException e){
			throw new LoginPageException("Errore nelle funzioni di SQL");
		}
	}

	@Override
	public void setData() throws LoginPageException{
		if(this.entity!=null)
		{
			try {
				this.connection = AllQuery.getInstance().getConnection();
				AllQuery.getInstance().requestRegistrationUser(this.connection, this.entity);
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
		try {
			this.entity=(UserEntity) object;
		
			connection=AllQuery.getInstance().getConnection();
		
		AllQuery.getInstance().deleteAccount(connection, this.entity.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void update(Entity object)throws DBException {
		this.entity= (UserEntity) object;
		try {
				this.connection = AllQuery.getInstance().getConnection();
				if(this.entity.getDescription()!=null)
					AllQuery.getInstance().updateDescriptionUser(connection, this.entity.getId(), this.entity.getDescription());
				if(this.entity.getPhoto()!=null)
					AllQuery.getInstance().updatePhotoProfile(connection, this.entity.getId(), this.entity.getPhoto());
				if(this.entity.getFavoriteList()!=null)
					AllQuery.getInstance().updateListFavoritTravel(connection,this.entity.getId(),this.entity.getFavoriteList().get(this.entity.getFavoriteList().size()-1));
				if(this.entity.getListFollowing()!=null) 
					AllQuery.getInstance().updateListFollower(connection, this.entity.getId(), this.entity.getListFollowing().get(this.entity.getListFollowing().size()-1));
		} catch (SQLException e) {
			throw new DBException("errore update");
		}
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
					localEntity=new UserEntity(rs.getInt(1));
					localEntity.setUsername(rs.getString(4));
					localEntity.setName(rs.getString(2));
					localEntity.setSurname(rs.getString(3));
					localEntity.setPhoto(rs.getBinaryStream(5));
					predictions.add(localEntity);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predictions;
	}

	
}
