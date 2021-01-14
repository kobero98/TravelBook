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

public class UserDao implements PersistanceDAO, PredictableDAO<UserEntity>{

	private UserEntity entity;
	private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
	private Connection connection;
	
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
		
	}
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
		Statement stmt=null;
		UserEntity user=(UserEntity) user1;
		AllQuery db=AllQuery.getInstance();
		List <Entity> list=new ArrayList<>();
		try {
			connect();
		} catch (SQLException e1) {
			throw new LoginPageException("errore connect");
		}
		try {
			stmt=connection.createStatement();
			if(user.getUsername()!=null && user.getPassword()!=null)
			{
				rs=db.requestLogin(stmt,user.getUsername(), user.getPassword());
					do
					{
						UserEntity utente=castRStoUser(rs);
						list.add((Entity) utente);
					}while(rs.next());
			}
		} catch (SQLException e) {
			throw new ExceptionLogin("errore SQL durante la Dao");
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
				connect();
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Entity object) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public List<UserEntity> getPredictions(String text){
		List<UserEntity> predictions=new ArrayList<>();
		ResultSet rs;
		try {
			connect();
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
