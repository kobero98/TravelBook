package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		user.setNTravel(rs.getInt(7));
		user.setPhoto(rs.getBinaryStream(8));
		user.setGender(rs.getString(9));
		user.setNation(rs.getString(10));
		return user;
		
	}
	
	@Override
	public List <Entity> getData(Entity user1) throws LoginPageException, SQLException {
		ResultSet rs=null;
		UserEntity user=(UserEntity) user1;
		AllQuery db=AllQuery.getInstance();
		List <Entity> list=new ArrayList<>();
		PreparedStatement stmt=null;
		try {
			this.connection = AllQuery.getInstance().getConnection();
			String query=db.requestLogin(connection,user.getUsername(), user.getPassword());	
			stmt=connection.prepareStatement(query);
			stmt.setString(1, user.getUsername());	
			stmt.setString(2, user.getPassword());
			rs=stmt.executeQuery();
			rs.next();
			UserEntity utente=castRStoUser(rs);
			stmt.close();
			query=AllQuery.getInstance().requestListIDFavoriteTrip();
			try(PreparedStatement stmt1=connection.prepareStatement(query)){
			stmt1.setInt(1, utente.getId());
			rs=stmt1.executeQuery();
			List <Integer> fav=new ArrayList<>();
			while(rs.next())
			{
				fav.add(rs.getInt(1));
			}
			utente.setFavoriteList(fav);
			stmt.close();
			}
			query=AllQuery.getInstance().requestListFollowingUser(utente.getId());	
			stmt=connection.prepareStatement(query);
			stmt.setInt(1, utente.getId());
			rs=stmt.executeQuery();
			List <Integer> following=new ArrayList<>();
			while(rs.next())
			{
				following.add(rs.getInt(1));
			}
			utente.setListFollowing(following);
			stmt.close();
			query=AllQuery.getInstance().requestListFollowerUser(utente.getId());
			stmt=connection.prepareStatement(query);
			stmt.setInt(1, utente.getId());
			rs=stmt.executeQuery();
			List <Integer> follower=new ArrayList<>();
			while(rs.next())
			{
				follower.add(rs.getInt(1));
			}
			utente.setListFollower(follower);
			stmt.close();
			
			
			query=AllQuery.getInstance().requestTripByUser( utente.getId());
			stmt=connection.prepareStatement(query);
			stmt.setInt(1,utente.getId());
			rs=stmt.executeQuery();
			List <Integer> travel=new ArrayList<>();
			while(rs.next())
			{
				travel.add(rs.getInt(1));
			}
			utente.setTravel(travel);
			stmt.close();
			utente.setnPlace(AllQuery.getInstance().getPlaceVisited(connection,utente.getId()));
			stmt.close();
			list.add((Entity) utente);
			return list;
		}catch (SQLException e1) {
			e1.printStackTrace();
			throw new LoginPageException("we couldn't reach our servers");
		}finally {
			if(stmt!=null) {
				stmt.close();
			}
		}
	}

	@Override
	public void setData() throws LoginPageException{
		if(this.entity!=null)
		{
			try {
				this.connection = AllQuery.getInstance().getConnection();
				AllQuery.getInstance().requestRegistrationUser(this.connection, this.entity);
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ExceptionRegistration("Registration error");
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
	public void delete(Entity object) throws DBException {
		try {
			this.entity=(UserEntity) object;
		
			connection=AllQuery.getInstance().getConnection();
		
		AllQuery.getInstance().deleteAccount(connection, this.entity.getId());
		} catch (SQLException e) {
			throw new DBException("Delete failed");
		}
	}
	@Override
	public void update(Entity object)throws DBException {
		this.entity= (UserEntity) object;
		try {
				this.connection = AllQuery.getInstance().getConnection();
				if(this.entity.getPassword()!=null) 
					AllQuery.getInstance().changePassword(this.entity, connection);
				
				if(this.entity.getDescription()!=null)
					AllQuery.getInstance().updateDescriptionUser(connection, this.entity.getId(), this.entity.getDescription());
				if(this.entity.getPhoto()!=null)
					AllQuery.getInstance().updatePhotoProfile(connection, this.entity.getId(), this.entity.getPhoto());
				if(this.entity.getFavoriteList()!=null && !this.entity.getListFollower().isEmpty())
					AllQuery.getInstance().updateListFavoritTravel(connection,this.entity.getId(),this.entity.getFavoriteList().get(this.entity.getFavoriteList().size()-1));
				if(this.entity.getListFollowing()!=null && !this.entity.getListFollowing().isEmpty()) 
					AllQuery.getInstance().updateListFollower(connection, this.entity.getId(), this.entity.getListFollowing().get(this.entity.getListFollowing().size()-1));
		} catch (SQLException e) {
			throw new DBException("update error");
		}
	}
	@Override
	public List<Entity> getPredictions(String text) throws DBException{
		List<Entity> predictions=new ArrayList<>();
		ResultSet rs;
		try {
			this.connection = AllQuery.getInstance().getConnection();
		} catch (SQLException e) {
			throw new DBException("no suggestion avaiable");
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
			throw new DBException("no suggestion avaiable");
		}
		return predictions;
	}

	
}
