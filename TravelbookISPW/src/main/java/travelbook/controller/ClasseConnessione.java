package main.java.travelbook.controller;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ClasseConnessione {

	public ClasseConnessione() {}
	public Connection getConenction() throws SQLException {
		
		String myUrl="jdbc:mysql://localhost:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
		Class.forName("com.mysql.jdbc.Driver");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(myUrl,"root","root");
	}
}
