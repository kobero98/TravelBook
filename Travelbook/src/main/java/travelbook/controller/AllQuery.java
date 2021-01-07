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
	private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	public ResultSet RequestLogin(Statement stmt,String Username,String Password) throws ExceptionLogin{
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
				e.printStackTrace();
				throw new ExceptionLogin("Errore Connessione");
			}
		}
	public ResultSet requestTripById(Statement stmt,int idTrip)
	{	ResultSet rs=null;
		try {
			String id=String.valueOf(idTrip);
			rs = stmt.executeQuery("SELECT * FROM Trip where idTrip="+id);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	
	}
	public ResultSet requestTripByUser(Statement stmt,int idCreator)
	{	ResultSet rs=null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Trip WHERE idCreator="+String.valueOf(idCreator));
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	
	}
	public ResultSet requestStepByTrip(Statement stmt,int idTrip)
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
	public void requestRegistrationUser(UserEntity User) {
	
		Connection connessione = null;
		
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
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
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connessione!=null) {
				try {
					connessione.close();
					} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			}
	}
	public void requestRegistrationStep(StepEntity Step) {
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
			 
				  String query = " insert into Step (groupDay,place,DescriptionStep,codiceTrip,codiceCreatore,Number) values( ?, ?, ?, ?, ?, ?) ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      
			      preparedStmt.setInt (1, Step.getGroupDay());
			      preparedStmt.setString (2, Step.getPlace());
			      preparedStmt.setString (3, Step.getDescriptionStep());
			      preparedStmt.setInt (4, Step.getIDTravel());
			      preparedStmt.setInt (5, Step.getIDCreator());
			      preparedStmt.setInt (6, Step.getNumber());
			      preparedStmt.execute();
			      for(String s : Step.getListPhoto()){
			    	  String queryPhoto = " insert into photostep (LinkPhoto,Step_Number,codiceViaggio,codiceCreatoreViaggio) values(?,?,?,?) ";
				      PreparedStatement preparedStmt1 = connessione.prepareStatement(queryPhoto);
				      preparedStmt1.setString (1,s);
				      preparedStmt1.setInt (2,Step.getNumber());
				      preparedStmt1.setInt (3, Step.getIDTravel());
				      preparedStmt1.setInt (4, Step.getIDCreator());
				      preparedStmt1.execute();  
			      }

				
		}catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	public void requestRegistrationTrip(TravelEntity trip) {
		Connection connessione = null;
		
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
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
			      query = " select idTrip from Trip where nome= ? and costo= ? and tipo=? and StartDate=? and EndDate=? ";
			      preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setString (1, trip.getNameTravel());
			      preparedStmt.setDouble (2, trip.getCostTravel());
			      preparedStmt.setString (3, trip.getTypeTravel());
			      preparedStmt.setDate (4, trip.getStartDate());
			      preparedStmt.setDate   (5,trip.getEndDate());
			      ResultSet rs=preparedStmt.executeQuery();
			      rs.next();
			      
			      for(StepEntity e:trip.getListStep())
			      {
			 
			    	  //e.setUserId(trip.getCreatorId());
			    	  e.setTripId(rs.getInt(1));
			    	  
			    	  requestRegistrationStep(e);
			    	  
			      }
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	public void deleteTravel(int idtrip)
	{
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
				  String query = "Delete from Trip where idTrip=? ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,idtrip);
			      preparedStmt.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteAccount(int iduser)
	{ 	
		Connection connessione = null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
				  String query = "Delete from User where idUser=? ";
			      PreparedStatement preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,iduser);
			      preparedStmt.execute();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connessione.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}