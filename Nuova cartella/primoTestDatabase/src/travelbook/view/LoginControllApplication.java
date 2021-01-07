package travelbook.view;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginControllApplication {
	private static LoginControllApplication INSTANCE = null;
	public LoginControllApplication() {}
	private LoginControllApplication getInstance() {
		if(INSTANCE==null)
			INSTANCE= new LoginControllApplication();
		return INSTANCE;
	}
	
	public LoginBean login(String Username, String Password)
	{
		Connection connessione = null;
		try {
			connessione= DriverManager.getConnection("jdbc:mysql:C:\\Users\\matte\\Documents\\DatabaseTest","root","root");
			
		}catch (SQLException e) {
			throw new Error("Problem", e);
		}
		finally {
			try {
				if(connessione!=null)
				{
					connessione.close();
				}
			}catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		LoginBean userBean= null;
		
		return userBean;
	}
}
