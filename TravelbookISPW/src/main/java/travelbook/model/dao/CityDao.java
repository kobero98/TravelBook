package main.java.travelbook.model.dao;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
import java.util.ArrayList;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.CityEntity;
import java.sql.*;
public class CityDao implements PredictableDAO,PersistanceDAO{
	private Connection connection;
	private CityEntity myEntity;
	@Override
	public List<Entity> getPredictions(String text) throws DBException{

		List<Entity> predictions=new ArrayList<>();
		try {
			this.connection = AllQuery.getInstance().getConnection();
		}catch(SQLException e4) {
			throw new DBException("servers unreacheable");
		}
		try {
		
		Statement stmt=connection.createStatement();
		ResultSet rs=AllQuery.getInstance().cityAutocompleteRequest(stmt, text);
		if(rs!=null) {
			CityEntity city;
			
		while(rs.next()) {	
			city=new CityEntity();
			city.setNameC(rs.getString(1));
			city.setState(rs.getString(2));
			predictions.add(city);
		}
		}
		}catch(SQLException e1) {
			throw new DBException("we can't reach our servers");
		}
		return predictions;

	}
	@Override
	public List<Entity> getData(Entity citta) throws DBException{
		CityEntity city=(CityEntity) citta;
		List<Entity> results=new ArrayList<>();
		try {
			this.connection = AllQuery.getInstance().getConnection();
			Statement stmt=connection.createStatement();
			ResultSet rs=AllQuery.getInstance().getCityByName(stmt,city );
			while(rs.next()) {
				CityEntity newC=new CityEntity();
				newC.setNameC(rs.getString("NameC"));
				newC.setState(rs.getString("State"));
				results.add((Entity)newC);
			}
		}catch(SQLException e1) {
			throw new DBException("data unreachable");
		}
		return results;
	}
	@Override
	public void setData() throws DBException {
		try {
			this.connection = AllQuery.getInstance().getConnection();
		}catch(SQLException e) {
			throw new DBException("we can't save your data, try again");
		}
		if(myEntity!=null)
			AllQuery.getInstance().setCity(connection, myEntity);
	}
	@Override
	public void delete(Entity object) {
		CityEntity city=(CityEntity) object;
		AllQuery.getInstance().deleteCity(connection, city);
	}
	@Override
	public void update(Entity object) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Entity getMyEntity() {
		return this.myEntity;
	}
	@Override
	public void setMyEntity(Entity obj) {
		this.myEntity=(CityEntity)obj;
	}
}
