package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.DBException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.SearchEntity;
import main.java.travelbook.model.TravelEntity;

public class SearchTravelDao implements VisualDAO {

	private TravelEntity convertMiniTravel(ResultSet rs) throws SQLException
	{
		
			TravelEntity e=new TravelEntity();
			e.setIdTravel(rs.getInt(1));
			e.setNameTravel(rs.getString(2));
			e.setDescriptionTravel(rs.getString(3));
			e.setBackground(rs.getBinaryStream(4));
			return e;
		
	}
	@Override
	public List<Entity> getData(Entity object) throws DBException {
		SearchEntity travel=(SearchEntity) object;
		Connection connessione;
		List<Entity> l=new ArrayList<>();
		try {
				connessione = AllQuery.getInstance().getConnection();
					
				Statement stmt=connessione.createStatement();
				ResultSet rs=AllQuery.getInstance().searchTrip(stmt,travel);
				
				while(rs.next())
				{
					TravelEntity e=convertMiniTravel(rs);
					l.add(e);
				}
				return l;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return l;
	}
}
