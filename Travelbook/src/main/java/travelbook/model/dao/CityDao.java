package main.java.travelbook.model.dao;
import java.util.List;
import java.util.ArrayList;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.CityEntity;
import java.sql.*;
public class CityDao implements PredictableDAO<CityEntity> {
	private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
	private Connection connection;
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
	}
	@Override
	public List<CityEntity> getPredictions(String text){

		List<CityEntity> predictions=new ArrayList<>();
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
}
