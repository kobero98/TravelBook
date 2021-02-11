package main.java.travelbook.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClasseConnessione {

	private String username="root";
	private String ps="Sara.d-19";
	
	public ClasseConnessione() {
		/*Constructor, dosn't need any param*/
	}
	public Connection getConenction() throws SQLException {
		
		String myUrl="jdbc:mysql://localhost:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
				Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException e) {
			throw new SQLException();
		} 
		
		return DriverManager.getConnection(myUrl,this.username,this.ps);
	}

}