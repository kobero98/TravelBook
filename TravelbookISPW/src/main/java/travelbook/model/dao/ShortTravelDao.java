package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.exception.DBException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.TravelEntity;

public class ShortTravelDao implements VisualDAO{
	
	
	
	private TravelEntity convertRsToShortTravelEntity(ResultSet rs) throws SQLException {
		TravelEntity e=new TravelEntity();
		e.setNameTravel(rs.getString(2));
		e.setDescriptionTravel(rs.getString(3));
		e.setBackground(rs.getBinaryStream(4));
		e.setShare(rs.getInt(5));
		return e;
	}
	
	

	@Override
	public List<Entity> getData(Entity object) throws DBException {
		TravelEntity entity=(TravelEntity) object;
		List<Entity> l=new ArrayList<>();
		Connection connection;
		try {
			connection = AllQuery.getInstance().getConnection();
			
			
			String query=AllQuery.getInstance().requestShortTravel(entity.getIdTravel());
		try(PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setInt(1, entity.getIdTravel());
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				TravelEntity e=convertRsToShortTravelEntity(rs);
				e.setIdTravel(entity.getIdTravel());
				l.add(e);	
			}
			connection.close();
		}
		} catch (SQLException e1) {
			throw new DBException("we can't reach your travel");
		}
		return l;
	}




}
