package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		ResultSet rs;
		SearchEntity travel=(SearchEntity) object;
		Connection connessione;
		List<Entity> l=new ArrayList<>();
		try {
				connessione = AllQuery.getInstance().getConnection();
					
				
				String query=AllQuery.getInstance().searchTrip(travel);
				try(PreparedStatement stmt=connessione.prepareStatement(query)){
					stmt.setString(1, travel.getCity().getNameC());
					stmt.setString(2, travel.getCity().getState());
					stmt.setInt(3, travel.getMinCost());
					stmt.setString(4, "%"+travel.getType()+"%");
					stmt.setInt(5, travel.getMinDay());
					if(travel.getMaxCost()!=null) {
						stmt.setInt(6, travel.getMaxCost());
						if(travel.getMaxDay()!=null) {
							stmt.setInt(7, travel.getMaxDay());
						}
					}
					else if(travel.getMaxDay()!=null) {
						stmt.setInt(6, travel.getMaxDay());
					}
					rs=stmt.executeQuery();
				while(rs.next())
				{
					TravelEntity e=convertMiniTravel(rs);
					l.add(e);
				}
				return l;
				}
		} catch (SQLException e1) {
			throw new DBException("servers unreachable");
		}
		
	}
}
