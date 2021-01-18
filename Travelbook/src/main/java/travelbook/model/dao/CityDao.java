package main.java.travelbook.model.dao;
import java.util.List;
import main.java.travelbook.model.Entity;
import java.util.ArrayList;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.CityEntity;
import java.sql.*;
public class CityDao implements PredictableDAO,PersistanceDAO{
	//private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private Connection connection;
	private CityEntity myEntity;
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
	}
	@Override
	public List<Entity> getPredictions(String text){

		List<Entity> predictions=new ArrayList<>();
		try {
			connect();
		}catch(SQLException e4) {
			e4.printStackTrace();
		}
		try {
		
		Statement stmt=connection.createStatement();
		ResultSet rs=AllQuery.getInstance().cityAutocompleteRequest(stmt, text);
		if(rs!=null) {
			CityEntity city;
			
		while(rs.next()) {	
			city=new CityEntity();
			System.out.println(rs.getString(1)+rs.getString(2));
			city.setNameC(rs.getString(1));
			city.setState(rs.getString(2));
			predictions.add(city);
		}
		}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return predictions;

	}
	@Override
	public List<Entity> getData(Entity citta){
		CityEntity city=(CityEntity) citta;
		List<Entity> results=new ArrayList<>();
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs=AllQuery.getInstance().getCityByName(stmt,city );
			while(rs.next()) {
				CityEntity newC=new CityEntity();
				newC.setNameC(rs.getString("NameC"));
				newC.setState(rs.getString("State"));
				results.add((Entity)newC);
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}
	@Override
	public void setData() {
		try {
			connect();
		}catch(SQLException e) {
			e.printStackTrace();
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
		//E' inutile cambiare una citta'
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
