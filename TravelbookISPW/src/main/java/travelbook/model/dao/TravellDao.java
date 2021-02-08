package main.java.travelbook.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.AddTravelException;
import exception.DBException;
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
	private List<CityEntity> reduceCity(List <CityEntity> c)
	{
		List<CityEntity> newList=new ArrayList<>();
		for(CityEntity a:c)
		{	int i=0;
			for(CityEntity b:newList)
				if(b.getNameC().equals(a.getNameC()) && b.getState().equals(a.getState())) i++;
			if(i==0) newList.add(a);
		}
		return newList;
	}
	@Override
	public List<Entity> getData(Entity object) throws DBException {
		this.entity=(TravelEntity) object;
		List <Entity> list=new ArrayList<>();
		ResultSet rs=null;
		PreparedStatement stmt=null;
		String query="";
		try {
				this.connection=AllQuery.getInstance().getConnection();
				try {
				if(this.entity.getCreatorId()!=0) {
					 query=AllQuery.getInstance().requestTripByUser( this.entity.getCreatorId());	
					 stmt=this.connection.prepareStatement(query);
					 stmt.setInt(1, this.entity.getCreatorId());
				}
				else {
					
							query=AllQuery.getInstance().requestTripById( this.entity.getIdTravel());
							stmt=this.connection.prepareStatement(query);
							stmt.setInt(1, this.entity.getIdTravel());
					
				}
				rs=stmt.executeQuery();
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
						
						String query1=AllQuery.getInstance().requestCityByTravelId( ent.getIdTravel());
						try(PreparedStatement stmt1=con2.prepareStatement(query1)){
						stmt1.setInt(1, ent.getIdTravel());
						rs1=stmt1.executeQuery();
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
						con2.close();
					}
				}
				else list=null;
				stmt.close();
				return list;
				}finally {
					if(stmt!=null)
						stmt.close();
					this.connection.close();
				}
		} catch (SQLException e) {
			throw new DBException("We couldn't reach your travel");
		}
	}
	private Connection connection;
	@Override
	public void setData() throws DBException {
		int idTravel=-1;
		try {
			this.connection=AllQuery.getInstance().getConnection();
		
		idTravel=AllQuery.getInstance().requestRegistrationTrip(this.connection,entity);
		int i;
		for(i=0;i<entity.getListStep().size();i++)
		{
			PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.STEP);
			StepEntity ent=entity.getListStep().get(i);
			ent.setTripId(idTravel);
			dao.setMyEntity(ent);
			dao.setData();
		}
		entity.setCityView(reduceCity(entity.getCityView()));
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
		} catch (SQLException e) {
			AddTravelException ex=new AddTravelException(e.getMessage());
			if(idTravel!=-1)
				ex.setId(idTravel);
			throw ex;
		}
		try {
			AllQuery.getInstance().updateTravelNumberForUser(connection, this.entity.getCreatorId());
		} catch (SQLException e) {
			throw new DBException("Error while updating travel number");
		}
	}
	@Override
	public Entity getMyEntity() {
		return this.entity;
	}

	@Override
	public void delete(Entity object) throws DBException {
		TravelEntity trav=(TravelEntity) object;
		Connection connect;
		try {
			connect = AllQuery.getInstance().getConnection();
		} catch (SQLException e) {
			throw new DBException("Errore nella delete");
		}
		try {
			AllQuery.getInstance().deleteTravel(connect, trav.getIdTravel());
		} catch (SQLException e) {
			throw new DBException("error while deleting, try again later");
		}
	}

	@Override
	public void update(Entity object) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setMyEntity(Entity travel) throws DBException {
		this.entity=(TravelEntity) travel;
		
	}

}
