package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;

public class TravellDao implements PersistanceDAO{

	private TravelEntity convertRStoTravel(ResultSet rs) {
		TravelEntity e;
		
		return null;
	}
	private TravelEntity entity;
	@Override
	public List<Entity> getData(Entity object) throws SQLException {
		TravelEntity e=(TravelEntity) object;
		List <Entity> list=new ArrayList<Entity>();
		ResultSet rs=null;
		Statement stmt=null;
		connect();
		stmt=this.connection.createStatement();
		if(e.getCreatorId()!=0) {
			rs=AllQuery.getInstance().requestTripByUser(stmt, e.getCreatorId());	
		}
		else {
			rs=AllQuery.getInstance().requestTripById(stmt, e.getIdTravel());
		}
		if(rs!=null)
		{
			while(rs.next()) {
				TravelEntity e = convertRStoTravel(rs);
				list.add((Entity) e);
			}
		}
		return null;
	}
	private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
	private Connection connection;
	
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
	}
	
	@Override
	public void setData() throws SQLException {
		
		connect();
		AllQuery.getInstance().requestRegistrationTrip(this.connection,entity);
		
	}

	@Override
	public Entity getMyEntity() {
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
	public void setMyEntity(Entity travel) throws SQLException {
		this.entity=(TravelEntity) travel;
		
	}


}
