package main.java.travelbook.model.dao;
import main.java.travelbook.model.StepEntity;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.List;

import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import java.util.ArrayList;
public class StepDao implements PersistanceDAO {
	private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
	private Connection connection;
	
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
	}
	private StepEntity myEntity;
	@Override
	public void update(Entity step) {
		
	}
	@Override
	public void delete(Entity step) {
		
	}
	@Override
	public void setData() throws SQLException{
		connect();
		AllQuery.getInstance().requestRegistrationStep(connection,myEntity);
	}
	@Override
	public List<Entity> getData(Entity step){
		StepEntity entity=(StepEntity) step;
		List<Entity> stepFound=new ArrayList<>();
		try{
			connect();
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
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return stepFound;
	}
	@Override
	public Entity getMyEntity() {
		return (Entity)this.myEntity;
	}
	@Override
	public void setMyEntity(Entity stepEntity) {
		this.myEntity=(StepEntity)stepEntity;
	}
}