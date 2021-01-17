package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;

public class TravellDao implements PersistanceDAO{

	private TravelEntity convertRStoTravel(ResultSet rs) throws SQLException {
		TravelEntity e=new TravelEntity(rs.getInt(1),rs.getInt(11));
		e.setNameTravel(rs.getString(2));
		e.setCostTravel(rs.getDouble(3));
		e.setType(rs.getString(4));
		e.setLikeNumber(5);
		e.setStartTravelDate(rs.getDate(6));
		e.setEndTravelDate(rs.getDate(7));
		e.setStepNumber(rs.getInt(8));
		e.setBackground(rs.getBinaryStream(9));
		e.setDescriptionTravel(rs.getString(10));
		e.setShare( rs.getInt(12));
		return e;
	}
	private TravelEntity entity;
	
	@Override
	public List<Entity> getData(Entity object) throws SQLException {
		TravelEntity e=(TravelEntity) object;
		List <Entity> list=new ArrayList<>();
		ResultSet rs=null;
		Statement stmt=null;
		connect();
		stmt=this.connection.createStatement();
		if(e.getCreatorId()!=0) {
			rs=AllQuery.getInstance().requestTripByUser(stmt, e.getCreatorId());	
		}
		else {
			if(e.getIdTravel()!=0)
					rs=AllQuery.getInstance().requestTripById(stmt, e.getIdTravel());
		}
		if(rs!=null)
		{
			while(rs.next()) {
				TravelEntity ent = convertRStoTravel(rs);
				list.add((Entity) ent);
				
			}
		}
		else list=null;
		stmt.close();
		return list;
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
		int idTravel=AllQuery.getInstance().requestRegistrationTrip(this.connection,entity);
		int i;
		for(i=0;i<entity.getListStep().size();i++)
		{
			PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.STEP);
			StepEntity ent=entity.getListStep().get(i);
			ent.setTripId(idTravel);
			dao.setMyEntity(ent);
			dao.setData();
			
		}
		for(i=0;i<entity.getCityView().size();i++)
		{
			PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.CITY);
			CityEntity citta=entity.getCityView().get(i);
			List<Entity> l=dao.getData(citta);
			if(l.isEmpty()) {
				dao.setMyEntity(citta);
				dao.setData();
			}
			AllQuery.getInstance().setCityToTravel(connection, idTravel, this.entity.getCreatorId(), citta);
		}
		
		
	}

	@Override
	public Entity getMyEntity() {
		return this.entity;
	}

	@Override
	public void delete(Entity object) {
		
		
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
