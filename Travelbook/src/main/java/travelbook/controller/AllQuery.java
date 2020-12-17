package main.java.travelbook.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.TravelBean;
import main.java.travelbook.util.DateUtil;

public class AllQuery {
	private String MyUrl="jdbc:mysql://localhost:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public ResultSet RequestLogin(Statement stmt,String Username,String Password) throws ExceptionLogin{
		ResultSet rs=null;
			try {
				rs = stmt.executeQuery("SELECT NameUser FROM User where Username='"+Username+"'");
			
			
			if(rs.next()) {
				rs= stmt.executeQuery("SELECT NameUser FROM User where Username='"+Username+"' and password='"+Password+"'");
				if(!rs.next()) throw new ExceptionLogin("Errore Password");	 
			}
			else {
				 rs = stmt.executeQuery("SELECT NameUser FROM User where email='"+Username+"'");
				 if(rs.next()) {
					    rs= stmt.executeQuery("SELECT NameUser FROM User where email='"+Username+"' and password='"+Password+"'");
						if(!rs.next()) throw new ExceptionLogin("Errore Password");	 
					}
				 else throw new ExceptionLogin("Errore Username o password");	 
			}
			return rs;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ExceptionLogin("Errore Connessione");
			}
		}
	
	public ResultSet RequestTripById(Statement stmt,int idTrip)
	{	ResultSet rs=null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Trip where idTrip="+String.valueOf(idTrip));
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	
	}
	public ResultSet RequestTripByUser(Statement stmt,int idCreator)
	{	ResultSet rs=null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Trip WHERE idCreator="+String.valueOf(idCreator));
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	
	}
	public ResultSet RequestStepByTrip(Statement stmt,int idTrip)
	{
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Trip WHERE CodiceTrip="+String.valueOf(idTrip));
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	public void RequestRegistrationUser(UserEntity User) {
	
		Connection connessione = null;
		
		try {
			  connessione= DriverManager.getConnection(MyUrl,"root","root");
		
			if (connessione != null) { 
				  String query = " insert into User (Username, password,NameUser, Surname, BirthDate,Email,Gender)" + " values (?, ?, ?, ?, ?, ?, ?)";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setString (1, User.getUsername());
			      preparedStmt.setString (2, User.getPassword());
			      preparedStmt.setString (3, User.getName());
			      preparedStmt.setString (4, User.getSurname());
			      preparedStmt.setDate   (5,User.getBirthDate());// il data va sistemato
			      preparedStmt.setString (6,User.getEmail());
			      preparedStmt.setString (7,User.getGender());
			      preparedStmt.execute();
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
	public void RequestRegistrationStep(StepEntity Step) {
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(MyUrl,"root","root");
		
			if (connessione != null) { 
				  String query = " insert into Step (groupDay,place,DescriptionStep) ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1, Step.getGroupDay());
			      preparedStmt.execute();
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void RequestRegistrationTrip(TravelEntity trip) {
		Connection connessione = null;
		
		try {
			  connessione= DriverManager.getConnection(MyUrl,"root","root");
		
			if (connessione != null) { 
				  String query = " insert into Trip (nome,costo,tipo,StartDate,EndDate,PhotoBackground,DescriptionTravel,CreatorTrip)" + " values (?, ?, ?, ?, ?, ? ,? ,?)";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setString (1, trip.getNameTravel());
			      preparedStmt.setDouble (2, trip.getCostTravel());
			      preparedStmt.setString (3, trip.getTypeTravel());
			      preparedStmt.setDate (4, trip.getStartDate());
			      preparedStmt.setDate   (5,trip.getEndDate());// il data va sistemato
			      preparedStmt.setString (6,trip.getPathImage());
			      preparedStmt.setString (7,trip.getDescriptionTravel());
			      preparedStmt.setInt (8,trip.getCreatorId());
			      preparedStmt.execute();
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	public void deleteTravel(int idtrip)
	{
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(MyUrl,"root","root");
		
			if (connessione != null) { 
				  String query = "Delete from Trip where idTrip=? ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,idtrip);
			      preparedStmt.execute();
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void deleteAccount(int iduser)
	{ 	
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(MyUrl,"root","root");
		
			if (connessione != null) { 
				  String query = "Delete from User where idUser=? ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,iduser);
			      preparedStmt.execute();
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}