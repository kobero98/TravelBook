package main.java.travelbook.controller;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ClasseConnessione {

	public ClasseConnessione() {
		/*Constructor, dosn't need any param*/
	}
	public Connection getConenction() throws SQLException {
		
		String myUrl="jdbc:mysql://localhost:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(myUrl,"root","Sara.d-19");
	}
}
