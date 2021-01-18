package main.java.travelbook.model.dao;
import main.java.travelbook.model.StepEntity;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import java.util.ArrayList;
public class StepDao implements PersistanceDAO {
	private Connection connection;
	
	private StepEntity myEntity;
	@Override
	public void update(Entity step) {
		
	}
	@Override
	public void delete(Entity step) {
		
	}
	@Override
	public void setData() throws SQLException{
		this.connection=AllQuery.getInstance().getConnection();
		AllQuery.getInstance().requestRegistrationStep(connection,myEntity);
	}
	@Override
	public List<Entity> getData(Entity step){
		StepEntity entity=(StepEntity) step;
		List<Entity> stepFound=new ArrayList<>();
		try{
			this.connection=AllQuery.getInstance().getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs=AllQuery.getInstance().requestStepByTrip(stmt, entity.getIDTravel());
			while(rs.next()) {
				StepEntity newStep=new StepEntity();
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
				Statement stmt1=connection.createStatement();
				ResultSet rs1=AllQuery.getInstance().requestPhotoByStep(stmt1, localStep.getNumber());
				List<InputStream> images=new ArrayList<>();
				while(rs1.next()) {
					images.add(rs1.getBinaryStream(1));
				}
				localStep.setStreamFoto(images);
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
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