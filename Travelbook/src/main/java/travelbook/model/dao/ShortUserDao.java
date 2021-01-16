package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.LoginPageException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;

public class ShortUserDao implements PersistanceDAO {
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
		return user;
		
	}

	@Override
	public List<Entity> getData(Entity user1) throws SQLException {
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
			if(user.getId()!=0)
			{
				rs=db.shortUserByID(stmt,user.getId());
					do
					{
						UserEntity utente=castRStoUser(rs);
						list.add((Entity) utente);
					}while(rs.next());
					System.out.println("finisco il get della Dao");
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
	public void setData() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity getMyEntity() {
		// TODO Auto-generated method stub
		return null;
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
	public void setMyEntity(Entity user) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}