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

public class ShortTravelDao implements PersistanceDAO {
	
	private Connection connection;
	
	private TravelEntity convertRsToShortTravelEntity(ResultSet rs) throws SQLException {
		TravelEntity e=new TravelEntity();
		e.setNameTravel(rs.getString(2));
		e.setDescriptionTravel(rs.getString(3));
		e.setBackground(rs.getBinaryStream(4));
		return e;
	}
	
	private TravelEntity entity;

	@Override
	public List<Entity> getData(Entity object) throws SQLException {
		this.entity=(TravelEntity) object;
		List<Entity> l=new ArrayList<>();
		this.connection = AllQuery.getInstance().getConnection();
		Statement stmt=this.connection.createStatement();
		ResultSet rs=AllQuery.getInstance().requestShortTravel(stmt, this.entity.getIdTravel());
		while(rs.next())
		{
			TravelEntity e=convertRsToShortTravelEntity(rs);
			e.setIdTravel(this.entity.getIdTravel());
			l.add(e);	
		}
		return l;
	}

	@Override
	public void setData() throws SQLException {
		// Do nothing because of X and Y.
	}

	@Override
	public Entity getMyEntity() {
		
		return  this.entity;
	}

	@Override
	public void delete(Entity object) throws SQLException {
		// Do nothing because of X and Y.

	}

	@Override
	public void update(Entity object) throws SQLException {
		// Do nothing because of X and Y.
		
	}

	@Override
	public void setMyEntity(Entity user) throws SQLException {
		this.entity=(TravelEntity) user;
		
	}


}
