package main.java.travelbook.model.dao;

import main.java.travelbook.model.StepEntity;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import exception.DBException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import java.util.ArrayList;
public class StepDao implements PersistanceDAO {
	private Connection connection;
	
	private StepEntity myEntity;
	@Override
	public void update(Entity step) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void delete(Entity step) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setData() throws DBException{
		try {
			this.connection=AllQuery.getInstance().getConnection();
			AllQuery.getInstance().requestRegistrationStep(connection,myEntity);
			this.connection.close();
		} catch (SQLException e) {
			throw new DBException("connection lost, could't retrieve steps");
		}
	}
	@Override
	public List<Entity> getData(Entity step) throws DBException{
		StepEntity entity=(StepEntity) step;
		List<Entity> stepFound=new ArrayList<>();
		try{
			this.connection=AllQuery.getInstance().getConnection();
		}catch(SQLException e) {
			throw new DBException("Error while loading travel");
		}
		try {
			String query=AllQuery.getInstance().requestStepByTrip( entity.getIDTravel());
			try(PreparedStatement stmt=connection.prepareStatement(query)){
				stmt.setInt(1, entity.getIDTravel());
				ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				StepEntity newStep=new StepEntity();
				newStep.setNumber(rs.getInt("Number"));
				newStep.setTripId(rs.getInt("CodiceTrip"));
				newStep.setPlace(rs.getString("Place"));
				newStep.setGroupDay(rs.getInt("GroupDay"));
				newStep.setDescriptionStep(rs.getString("DescriptionStep"));
				newStep.setUserId(rs.getInt("CodiceCreatore"));
				newStep.setNumberOfDay(rs.getInt("NumberDay"));
				newStep.setPrecisionInformation(rs.getString("PrecisionInformation"));
				stepFound.add((Entity)newStep);
			}
			for(Entity entit: stepFound) {
				StepEntity localStep=(StepEntity)entit;
				
				String query1=AllQuery.getInstance().requestPhotoByStep(localStep.getNumber(), localStep.getIDTravel());
				try(PreparedStatement stmt1=connection.prepareStatement(query1)){
				stmt1.setInt(1, localStep.getNumber());
				stmt1.setInt(2, localStep.getIDTravel());
				ResultSet rs1=stmt1.executeQuery();
				List<InputStream> images=new ArrayList<>();
				while(rs1.next()) {
					images.add(rs1.getBinaryStream(1));
				}
				localStep.setStreamFoto(images);
				}
			}
			}
			this.connection.close();
		}catch(SQLException e1) {
			throw new DBException("Error while loading travel");
		}
		
		return stepFound;
	}
	@Override
	public Entity getMyEntity() {
		return this.myEntity;
	}
	@Override
	public void setMyEntity(Entity stepEntity) {
		this.myEntity=(StepEntity)stepEntity;
	}
}