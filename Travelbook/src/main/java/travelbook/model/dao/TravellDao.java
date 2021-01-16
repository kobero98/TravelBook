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

	public static void main(String [] args) {
		TravelEntity e=new TravelEntity();
		e.setNameTravel("odio questa materia");
		e.setCreatorTravel(1);
		e.setCostTravel(400);
		e.setStartTravelDate(new Date(1999-1-1));
		e.setEndTravelDate(new Date(1999-1-3));
		e.setShare(0);
		e.setDescriptionTravel("descrizione viaggio");
		e.setType("#romanticTrip");
		List<CityEntity> l=new ArrayList<>();
		List<StepEntity> l1=new ArrayList<>();
		
		CityEntity citta= new CityEntity();
		citta.setNameC("Roma");
		citta.setState("Italia");
		l.add(citta);
		citta= new CityEntity();
		citta.setNameC("Frosinone");
		citta.setState("Italia");
		l.add(citta);
		e.setCityView(l);
		
		StepEntity step=new StepEntity();
		step.setUserId(1);
		step.setDay(new Date(1999-1-1));
		step.setDescriptionStep("schifo");
		step.setGroupDay(1);
		step.setNumber(1);
		step.setNumberOfDay(1);
		step.setPlace("Colosseo");
		step.setPrecisionInformation("odio questo progetto");
		l1.add(step);
		
		step=new StepEntity();
		step.setUserId(1);
		step.setDay(new Date(1999-1-1));
		step.setDescriptionStep("schifo parte due");
		step.setGroupDay(1);
		step.setNumber(2);
		step.setNumberOfDay(2);
		step.setPlace("Fori Imperiali");
		step.setPrecisionInformation("é la seconda volta che riscrivo lo stesso codice e sono le 2 di notte");
		l1.add(step);
		
		step=new StepEntity();
		step.setUserId(1);
		step.setDay(new Date(1999-1-2));
		step.setDescriptionStep("schifo parte tre");
		step.setGroupDay(2);
		step.setNumber(3);
		step.setNumberOfDay(1);
		step.setPlace("Via del Corso");
		step.setPrecisionInformation("Però E da dire che le stringhe dei place le metto sempre bene u.u");
		l1.add(step);	
		
		step=new StepEntity();
		step.setUserId(1);
		step.setDay(new Date(1999-1-2));
		step.setDescriptionStep("schifo parte quatro");
		step.setGroupDay(2);
		step.setNumber(4);
		step.setNumberOfDay(2);
		step.setPlace("Fori Imperiali");
		step.setPrecisionInformation("Mi fermo al quarto step che sono cotto");
		l1.add(step);
		
		e.setStepNumber(4);
		e.setListStep(l1);
		
		PersistanceDAO dao =DaoFactory.getInstance().create(DaoType.TRAVEL);
		try {
			dao.setMyEntity(e);
			dao.setData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
