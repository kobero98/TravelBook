package main.java.travelbook.controller;

import java.sql.Connection;
import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import exception.ExceptionLogin;
import exception.ExceptionRegistration;
import exception.LoginPageException;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;

public class AllQuery {
	private static AllQuery instance=null;
	private AllQuery() {}
	public static AllQuery getInstance() {
		if(instance==null) instance=new AllQuery();
		
		return instance;
	}
	private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
	private Connection connection;
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
		
	}
	private String userAttributeQuery="Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,FollowerNumeber,FollowingNumber,TripNumber,ProfileImage,Gender,Nazionalità";
	public ResultSet requestLogin(Statement stmt,String username,String password) throws ExceptionLogin{
		ResultSet rs=null;
		
		try {
				rs = stmt.executeQuery(userAttributeQuery+" FROM User where Username='"+username+"'");
			
			
			if(rs.next()) {
				rs= stmt.executeQuery(userAttributeQuery+" FROM User where Username='"+username+"' and password='"+password+"'");
				if(!rs.next()) throw new ExceptionLogin("Errore Password");	 
			}
			else {
				 rs = stmt.executeQuery(userAttributeQuery+" FROM User where email='"+username+"'");
				 if(rs.next()) {
					    rs= stmt.executeQuery(userAttributeQuery+" FROM User where email='"+username+"' and password='"+password+"'");
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
			
			rs = stmt.executeQuery("SELECT * FROM Trip WHERE idCreator="+idCreator);
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
			rs = stmt.executeQuery("SELECT * FROM Trip WHERE CodiceTrip="+idTrip);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}

	public void requestRegistrationUser(Connection conn,UserEntity user) throws SQLException {
				  PreparedStatement preparedStmt =null;
				  try{
					  String query = " insert into User (Username, password,NameUser, Surname, BirthDate,Email,Gender)" + " values (?, ?, ?, ?, ?, ?, ?)";
					  preparedStmt = conn.prepareStatement(query);
					  preparedStmt.setString (1, user.getUsername());
				      preparedStmt.setString (2, user.getPassword());
				      preparedStmt.setString (3, user.getName());
				      preparedStmt.setString (4, user.getSurname());
				      preparedStmt.setDate   (5,user.getBirthDate());// il data va sistemato
				      preparedStmt.setString (6,user.getEmail());
				      preparedStmt.setString (7,user.getGender());
				      preparedStmt.execute();
				      preparedStmt.close();
				  }catch(SQLIntegrityConstraintViolationException e) {
						throw new ExceptionRegistration("Errore Utente gia presente nel Database");
					}catch(MysqlDataTruncation e){
						throw new ExceptionRegistration("Dati non validi");
					}catch(SQLException e){
						throw new LoginPageException("Errore nell'accesso al database");
					}finally {
					
					  if(preparedStmt!=null) preparedStmt.close();
					}
			   
	}
	
	public void requestRegistrationStep(Connection connessione,StepEntity step) throws SQLException {
		PreparedStatement preparedStmt=null;
		try {
				  String query = " insert into Step (groupDay,place,DescriptionStep,codiceTrip,codiceCreatore,Number) values( ?, ?, ?, ?, ?, ?) ";
			      preparedStmt = connessione.prepareStatement(query);
			      
			      preparedStmt.setInt (1, step.getGroupDay());
			      preparedStmt.setString (2, step.getPlace());
			      preparedStmt.setString (3, step.getDescriptionStep());
			      preparedStmt.setInt (4, step.getIDTravel());
			      preparedStmt.setInt (5, step.getIDCreator());
			      preparedStmt.setInt (6, step.getNumber());
			      preparedStmt.execute();
			      preparedStmt.close();
			      for(File f : step.getListPhoto()){
			    	  String queryPhoto = " insert into photostep (LinkPhoto,Step_Number,codiceViaggio,codiceCreatoreViaggio) values(?,?,?,?) ";
				      preparedStmt = connessione.prepareStatement(queryPhoto);
				      FileInputStream fis;
					try {
						fis = new FileInputStream(f);
						preparedStmt.setBinaryStream(1,fis,(int)f.length());
					      preparedStmt.setInt (2, step.getNumber());
					      preparedStmt.setInt (3, step.getIDTravel());
					      preparedStmt.setInt (4, step.getIDCreator());
					      preparedStmt.execute();  
					      preparedStmt.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      
				 
			      }
			      	
		}finally {
			
			if(preparedStmt!=null) {
					try {
						preparedStmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public void requestRegistrationTrip(TravelEntity trip) {
		Connection connessione = null;
		 PreparedStatement preparedStmt=null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
				  String query = " insert into Trip (nome,costo,tipo,StartDate,EndDate,PhotoBackground,DescriptionTravel,CreatorTrip)" + " values (?, ?, ?, ?, ?, ? ,? ,?)";
			      preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setString (1, trip.getNameTravel());
			      preparedStmt.setDouble (2, trip.getCostTravel());
			      preparedStmt.setString (3, trip.getTypeTravel());
			      preparedStmt.setDate (4, trip.getStartDate());
			      preparedStmt.setDate   (5,trip.getEndDate());// il data va sistemato
			      preparedStmt.setString (6,trip.getPathImage());
			      preparedStmt.setString (7,trip.getDescriptionTravel());
			      preparedStmt.setInt (8,trip.getCreatorId());
			      preparedStmt.execute();
			      preparedStmt.close();
			      query = " select idTrip from Trip where nome= ? and costo= ? and tipo=? and StartDate=? and EndDate=? ";
			      preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setString (1, trip.getNameTravel());
			      preparedStmt.setDouble (2, trip.getCostTravel());
			      preparedStmt.setString (3, trip.getTypeTravel());
			      preparedStmt.setDate (4, trip.getStartDate());
			      preparedStmt.setDate   (5,trip.getEndDate());
			      ResultSet rs=preparedStmt.executeQuery();
			      preparedStmt.close();
			      rs.next();
			      
			      for(StepEntity e:trip.getListStep())
			      {
			 
			    	  e.setTripId(rs.getInt(1));

			    	  
			      }
			
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
			if(preparedStmt!=null) {
					try {
						preparedStmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}	

	public void deleteTravel(int idtrip)
	{
		Connection connessione = null;
		PreparedStatement preparedStmt=null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
				  String query = "Delete from Trip where idTrip=? ";
			      preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,idtrip);
			      preparedStmt.execute();
			      preparedStmt.close();
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
			if(preparedStmt!=null) {
					try {
						preparedStmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public void deleteAccount(int iduser)
	{ 	
		Connection connessione = null;
		PreparedStatement preparedStmt=null;
		try {
			  connessione= DriverManager.getConnection(myUrl,"root","root");
		
				  String query = "Delete from User where idUser=? ";
			      preparedStmt = connessione.prepareStatement(query);
			      preparedStmt.setInt (1,iduser);
			      preparedStmt.execute();
			      preparedStmt.close();
		
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
			if(preparedStmt!=null) {
					try {
						preparedStmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public ResultSet requestUserbyID(Statement stmt,int id) {
		ResultSet rs=null;
		try {
			connect();
			stmt=this.connection.createStatement();
			String query= userAttributeQuery+" from User where idUser="+id;
			 rs=stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet cityAutocompleteRequest(Statement stmt, String text) {
		ResultSet rs=null;
		String query="SELECT NameC,State from City where NameC like '"+text+"%' order by char_length(NameC),char_length(State)";
		try {
		 rs=stmt.executeQuery(query);
		 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet userAutocompleteRequest(Statement stmt, String text) {
		String[] nameSurname=text.split(" ");
		String name=nameSurname[0];
		String surname="";
		ResultSet rs=null;
		if(nameSurname.length>1)
			surname=nameSurname[2];
		String query="SELECT NameUser, Surname,Username from User where NameUser like '"+name+"%' and Surname like '"+surname+"%' order by char_length(NameUser),char_length(Surname)";
		try {
			rs=stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
}