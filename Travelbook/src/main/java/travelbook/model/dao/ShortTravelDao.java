package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;

public class ShortTravelDao implements VisualDAO{
	
	
	
	private TravelEntity convertRsToShortTravelEntity(ResultSet rs) throws SQLException {
		TravelEntity e=new TravelEntity();
		e.setNameTravel(rs.getString(2));
		e.setDescriptionTravel(rs.getString(3));
		e.setBackground(rs.getBinaryStream(4));
		return e;
	}
	
	

	@Override
	public List<Entity> getData(Entity object) throws SQLException {
		TravelEntity entity=(TravelEntity) object;
		List<Entity> l=new ArrayList<>();
		Connection connection = AllQuery.getInstance().getConnection();
		Statement stmt=connection.createStatement();
		ResultSet rs=AllQuery.getInstance().requestShortTravel(stmt, entity.getIdTravel());
		while(rs.next())
		{
			TravelEntity e=convertRsToShortTravelEntity(rs);
			e.setIdTravel(entity.getIdTravel());
			l.add(e);	
		}
		connection.close();
		return l;
	}




}
