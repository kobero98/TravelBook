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
		this.entity=(TravelEntity) object;
		List <Entity> list=new ArrayList<>();
		ResultSet rs=null;
		Statement stmt=null;
		this.connection=AllQuery.getInstance().getConnection();
		stmt=this.connection.createStatement();
		if(this.entity.getCreatorId()!=0) {
			rs=AllQuery.getInstance().requestTripByUser(stmt, this.entity.getCreatorId());	
		}
		else {
			if(this.entity.getIdTravel()!=0) {
				System.out.println(this.entity.getIdTravel());
					rs=AllQuery.getInstance().requestTripById(stmt, this.entity.getIdTravel());
			}
		}
		if(rs!=null)
		{
			while(rs.next()) {
				TravelEntity ent = convertRStoTravel(rs);
				
				
				PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.STEP);
				StepEntity step=new StepEntity();
				step.setTripId(ent.getIdTravel());
				List<Entity> s=dao.getData(step);
				ent.setListStep(s);
				
				ResultSet rs1=null;
				Connection con2=AllQuery.getInstance().getConnection();
				Statement stmt1=con2.createStatement();
				rs1=AllQuery.getInstance().requestCityByTravelId(stmt1, ent.getIdTravel());
				List <CityEntity> l=new ArrayList<>();
				while(rs1.next())
				{
					CityEntity c=new CityEntity();
					c.setNameC(rs1.getString(1));
					c.setState(rs1.getString(2));
					l.add(c);
				}
				ent.setCityView(l);
				list.add((Entity) ent);
			}
		}
		else list=null;
		stmt.close();
		System.out.println(list==null);
		return list;
	}
	private Connection connection;
	
	@Override
	public void setData() throws SQLException {
		
		this.connection=AllQuery.getInstance().getConnection();
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
