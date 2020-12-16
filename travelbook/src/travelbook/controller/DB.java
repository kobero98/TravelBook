package travelbook.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import travelbook.model.bean.TravelBean;
import travelbook.model.bean.UserBean;

public class DB {
	private String MyUrl="jdbc:mysql://localhost:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private UserBean ConvertValueToUserBean(ResultSet rs) {

		try {
			UserBean User1=new UserBean(rs.getInt(1));
			User1.setName(rs.getString(4));
			User1.setSurname(rs.getString(5));
			User1.setDescription(rs.getString(7));
			User1.setFollower(rs.getInt(9));
			User1.setFollowing(rs.getInt(10));
			User1.setNTravel(rs.getInt(11));
			User1.setUrlPhoto(rs.getString(12));
			return User1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public UserBean login(String Username,String Password) throws ExceptionLogin{
		Connection connessione = null;
		UserBean User=null;
		try{
			connessione= DriverManager.getConnection(this.MyUrl,"root","root");
			if (connessione != null) { 
				System.out.println("Successfully connected to MySQL database test");
				Statement stmt=connessione.createStatement();
				boolean controllo= stmt.execute("select * from user where Username='"+Username+"'"); 
				if(controllo==true)
				{
					 controllo= stmt.execute("select * from user where Username='"+Username+"' and password='"+Password+"'"); 
					 if(controllo== true)
					 {
						 ResultSet rs=stmt.executeQuery("select * from user where Username='"+Username+"' and password='"+Password+"'"); 
						 User=this.ConvertValueToUserBean(rs);
					 }
					 else{		
					 	ExceptionLogin ex= new ExceptionLogin("Errore della passworld!!"); 
						throw ex;
					 }
				}
				else{
					controllo= stmt.execute("select * from user where Email='"+Username+"'");
					if(controllo==true )
					{
						controllo= stmt.execute("select * from user where Email='"+Username+"' and password='"+Password+"'"); 
						if(controllo== true)
						 {
							 ResultSet rs=stmt.executeQuery("select * from user where Username='"+Username+"' and password='"+Password+"'"); 
							 User=this.ConvertValueToUserBean(rs);
						 }
						 else{		
					 	ExceptionLogin ex= new ExceptionLogin("Errore della passworld!!"); 
						throw ex;
						 }
					} 
					else{
					 	ExceptionLogin ex= new ExceptionLogin("Errore nello Usernam o Email!!"); 
						throw ex;
					 }
					
				}
					
				}
		}catch (SQLException e) {
			ExceptionLogin ex= new ExceptionLogin("Errore di connessione"); 
			throw ex;
		
		}
		finally {
			try {
				if(connessione!=null)
				{
					connessione.close();
					return User;
				}
			}catch(SQLException ex) {
				return User;
			}
		}
		return User;
		}
	
	public UserBean Register(String Username,String password,String Name,String Surname,String Email,Date Data,String gender)
	{
		UserBean User=null;
		Connection connessione = null;
		
		try {
			connessione= DriverManager.getConnection(this.MyUrl,"root","root");
		
			PreparedStatement stmt = connessione.prepareStatement("INSERT INTO User(Username,password,NameUser,Surname,BirthDate,Email, Gender) VALUES ( ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, Username);
			stmt.setString(2, password);
			stmt.setString(3, Name);
			stmt.setString(4,Surname);
			stmt.setString(5,Email);
			stmt.setDate(6,Data);
			stmt.setString(7,gender);
			stmt.executeUpdate();
			ResultSet rs=stmt.executeQuery("select * from user where Username='"+Username+"' and password='"+password+"'"); 
			User=this.ConvertValueToUserBean(rs);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(connessione!=null)
				{
					connessione.close();
					return User;
				}
			}catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
			
		}
		
		return User;
	}
	
	public List <TravelBean> MyTravel(int idUser){
		Connection connessione = null;
		List <TravelBean> Travel=null;
		try {
			connessione= DriverManager.getConnection(this.MyUrl,"root","root");
			
		
			
			return Travel;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			

			try {
				connessione.close();
				return Travel;
			} catch (SQLException e) {
				return Travel;
			}
		
		}
		
	}
	public void UpdateLike(int id) {
	}
	public void UpdateFollower(int id) {
	}
	public void UpdatePathPhoto(String Path)
	{
		
	}
}