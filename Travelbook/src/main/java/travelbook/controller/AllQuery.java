package main.java.travelbook.controller;

import java.sql.Connection;
import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import exception.ExceptionLogin;
import exception.ExceptionRegistration;
import exception.LoginPageException;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.MessageEntity;
import main.java.travelbook.model.SearchEntity;
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
	private String myUrl="jdbc:mysql://127.0.0.1:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(myUrl,"root","Sara.d-19");
	}
	private String userAttributeQuery="Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,FollowerNumber,FollowingNumber,TripNumber,ProfileImage,Gender,Nazionalita";
	public ResultSet searchTrip(Statement stmt,SearchEntity entity) throws SQLException
	{
		if(entity.getMaxCost()==null) {
			Connection conn=getConnection();
			Statement stmt1=conn.createStatement();
			ResultSet rs1=stmt1.executeQuery("Select Max(costo) from trip");
			rs1.next();
			entity.setMaxCost(rs1.getInt(1));
			conn.close();
		}
		if(entity.getMaxDay()==null)
		{
			Connection conn=getConnection();
			Statement stmt1=conn.createStatement();
			ResultSet rs1=stmt1.executeQuery("Select Max( DATEDIFF(EndDate,StartDate)) from trip");
			rs1.next();
			entity.setMaxDay(rs1.getInt(1));
			conn.close();
		}
		
		String query="Select idTrip,nome,Descriptiontravel,PhotoBackground from trip join trip_has_city on idTrip=CodiceViaggi and CreatorTrip=CodiceCreatore where City_NameC like'"+entity.getCity().getNameC() +"' and City_State like '"+entity.getCity().getState() +"' and Condiviso=0 and costo>="+entity.getMinCost()+" and costo<="+entity.getMaxCost()+" and tipo like '%"+entity.getType()+"%' and DATEDIFF(EndDate,StartDate)>="+(entity.getMinDay()-1)+" and DATEDIFF(EndDate,StartDate)<="+(entity.getMaxDay()-1);
		System.out.println(query);
		return stmt.executeQuery(query);
	}
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
			rs = stmt.executeQuery("SELECT idTrip,nome,costo,tipo,Nlike,StartDate,EndDate,StepNumber,PhotoBackground,DescriptionTravel,CreatorTrip,Condiviso FROM Trip where idTrip="+id);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	
	}
	public int getPlaceVisited(Statement stmt, int id) throws SQLException
	{
		String query="Select count(*) as name from (SELECT Distinct city.NameC FROM trip_has_city join city  on City_NameC=NameC and city_State=State where codiceCreatore="+id+") as Place";
		ResultSet rs= stmt.executeQuery(query);
		rs.next();
		return rs.getInt(1);
	}
	public ResultSet requestTripByUser(Statement stmt,int idCreator)
	{	ResultSet rs=null;
		try {
			
			rs = stmt.executeQuery("SELECT idTrip, nome, costo, tipo, Nlike , StartDate, EndDate, StepNumber, PhotoBackground, DescriptionTravel, CreatorTrip, Condiviso FROM Trip WHERE CreatorTrip="+idCreator);
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
			rs = stmt.executeQuery("SELECT * FROM Step WHERE CodiceTrip="+idTrip);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	public ResultSet requestPhotoByStep(Statement stmt,int idStep) throws SQLException {
		ResultSet rs=null;
		String query="SELECT LinkPhoto from PhotoStep where Step_Number="+idStep;
		rs=stmt.executeQuery(query);
		return rs;
	}

	public void requestRegistrationUser(Connection conn,UserEntity user) throws SQLException {
				  PreparedStatement preparedStmt =null;
				
					  String query = " insert into User (Username, password,NameUser, Surname, BirthDate,Email,Gender)" + " values (?, ?, ?, ?, ?, ?, ?)";
					  try {
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
				  String query = " insert into Step (groupDay,place,DescriptionStep,codiceTrip,codiceCreatore,NumberDay,Number) values( ?, ?, ?, ?, ?, ?,?) ";
			      preparedStmt = connessione.prepareStatement(query);
			      
			      preparedStmt.setInt (1, step.getGroupDay());
			      preparedStmt.setString (2, step.getPlace());
			      preparedStmt.setString (3, step.getDescriptionStep());
			      preparedStmt.setInt (4, step.getIDTravel());
			      preparedStmt.setInt (5, step.getIDCreator());
			      preparedStmt.setInt (6, step.getNumberOfDay());
			      preparedStmt.setInt(7, step.getNumber());
			      preparedStmt.execute();
			      preparedStmt.close();
			      
			    
			      
			      if(step.getListPhoto()!=null) {
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
	
	public Integer requestRegistrationTrip(Connection connessione,TravelEntity trip) throws SQLException {
			PreparedStatement preparedStmt=null;
			  try{
					  String query = " insert into Trip (nome,costo,tipo,StartDate,EndDate,PhotoBackground,DescriptionTravel,CreatorTrip)" + " values (?, ?, ?, ?, ?, ? ,? ,?)";
					  preparedStmt = connessione.prepareStatement(query);
					  preparedStmt.setString (1, trip.getNameTravel());
					  preparedStmt.setDouble (2, trip.getCostTravel());
					  preparedStmt.setString (3, trip.getTypeTravel());
					  preparedStmt.setDate (4, trip.getStartDate());
					  preparedStmt.setDate   (5,trip.getEndDate());// il data va sistemato
					  preparedStmt.setBinaryStream(6,trip.getImage());
					  preparedStmt.setString (7,trip.getDescriptionTravel());
					  preparedStmt.setInt (8,trip.getCreatorId());
					  preparedStmt.execute();
					  preparedStmt.close();
					  query = " select idTrip from Trip where nome= ? and CreatorTrip= ? and StartDate=? and EndDate=? ";
					  preparedStmt = connessione.prepareStatement(query);
					  preparedStmt.setString (1, trip.getNameTravel());
					  preparedStmt.setInt(2, trip.getCreatorId());
					  preparedStmt.setDate(3, trip.getStartDate());
					  preparedStmt.setDate(4,trip.getEndDate());
					  ResultSet rs=preparedStmt.executeQuery();
					  rs.next();
					  return rs.getInt(1);
			   }finally {
				   if(preparedStmt!=null)preparedStmt.close();
			  }
			
		
	}	
	
	public ResultSet requestListIDFavoriteTrip(Statement stmt,int idUser) throws SQLException {
		String query="Select CodiceTravel from Favorite where codiceUser="+idUser;
		return stmt.executeQuery(query);	 
	}
	
	public ResultSet requestListFollowerUser(Statement stmt,int idUser) throws SQLException {
		String query="Select Follower from Follow where Following="+idUser;
		return stmt.executeQuery(query);	 
	}
	public ResultSet requestListFollowingUser(Statement stmt,int idUser) throws SQLException {
		String query="Select Following from Follow where Follower="+idUser;
		return stmt.executeQuery(query);	 
	}
	public ResultSet requestShortTravel(Statement stmt,int idTrip)throws SQLException{
		String query="Select idTrip,nome,Descriptiontravel,PhotoBackground from trip where idTrip="+idTrip;
		return stmt.executeQuery(query);
	}
	public ResultSet requestCityByTravelId(Statement stmt,int idTravel) throws SQLException
	{
		String query="Select City.NameC, City.State from Trip_has_City join City on NameC=City_NameC and City_State=State where CodiceViaggi="+idTravel;
		return stmt.executeQuery(query);
		
	}
	public void setCityToTravel(Connection connessione,int idTravel,int idCrator,CityEntity entity) throws SQLException {
		PreparedStatement preparedStmt=null;
		  try{
				  String query = " insert into trip_has_city (CodiceViaggi,CodiceCreatore,City_NameC,City_State)" + " values (?, ?, ?, ?)";
				  preparedStmt = connessione.prepareStatement(query);
				  preparedStmt.setInt(1, idTravel);
				  preparedStmt.setInt(2, idCrator);
				  preparedStmt.setString(3, entity.getNameC());
				  preparedStmt.setString(4, entity.getState());
				  preparedStmt.execute();
				  
		   }finally {
			   if(preparedStmt!=null)preparedStmt.close();
		  }
	}
	public void updateListFavoritTravel(Connection connessione,int idUser,int idTravel) throws SQLException
	{
		PreparedStatement stmt1=null;
		Statement stmt=null;
		try {
				stmt=connessione.createStatement();
				
				ResultSet rs=stmt.executeQuery("select CreatorTrip from trip where idTrip="+idTravel);
				rs.next();
				int cretorTrip=rs.getInt(1);
				stmt.close();
				stmt=connessione.createStatement();
				rs=stmt.executeQuery("select * from favorite where CodiceUser="+idUser+" and CodiceTravel="+idTravel);
				if(!rs.next()) {
					String query="insert into favorite (CodiceUser,codiceTravel,codiceCreatore) values( ?,?,?)";
					stmt1=connessione.prepareStatement(query);
					stmt1.setInt(1,idUser );
					stmt1.setInt(2, idTravel);
					stmt1.setInt(3, cretorTrip);
					stmt1.execute();
					stmt1.close();
					stmt=connessione.createStatement();
					ResultSet rs1=stmt.executeQuery("Select Nlike from trip where idTrip="+idTravel);
					rs1.next();
					int i=rs1.getInt(1);
					stmt.close();
					stmt1=connessione.prepareStatement("update Trip set Nlike= ? where idTrip= ?");
					stmt1.setInt(1, i+1);
					stmt1.setInt(2,idTravel );
					stmt1.execute();
					stmt1.close();
				}
				else {
					stmt=connessione.createStatement();
					String s="delete from Favorite where CodiceUser=? and CodiceTravel=? ";
					stmt1=connessione.prepareStatement(s);
					stmt1.setInt(1, idUser);
					stmt1.setInt(2, idTravel);
					stmt1.execute();
					stmt.close();
					stmt=connessione.createStatement();
					ResultSet rs1=stmt.executeQuery("Select Nlike from trip where idTrip="+idTravel);
					rs1.next();
					int i=rs1.getInt(1);
					stmt.close();
					stmt1=connessione.prepareStatement("update Trip set Nlike= ? where idTrip= ?");
					stmt1.setInt(1, i-1);
					stmt1.setInt(2,idTravel );
					stmt1.execute();
					stmt1.close();
					
				}
		}finally {
			if(stmt!=null) stmt.close();
			if(stmt1!=null) stmt1.close();
 		}
		
		
	}
	
	public void updateTravelNumberForUser(Connection connessione,int idUser) {
		PreparedStatement stmt=null;
		String query="update User set TripNumber= ? where idUser=?";
		Statement stmt1=null;
		try {
			Statement stmt2=connessione.createStatement();
			ResultSet rs=stmt2.executeQuery("Select Count(idTrip) as tripNumber from trip where CreatorTrip="+idUser);
			rs.next();
			stmt=connessione.prepareStatement(query);
			stmt.setInt(1, rs.getInt(1));
			stmt.setInt(2, idUser);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null )
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(stmt1!=null )
				try {
					stmt1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void updateDescriptionUser(Connection connessione,int iduser,String description) {
		PreparedStatement stmt=null;
		String query="update User set DescriptionProfile= ? where idUser=?";
		try {
			stmt=connessione.prepareStatement(query);
			stmt.setString(1, description);
			stmt.setInt(2, iduser);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void updatePhotoProfile(Connection connessione,int idUser,InputStream image) {
		PreparedStatement stmt=null;
		String query="update User set ProfileImage= ? where idUser=?";
		try {
			stmt=connessione.prepareStatement(query);
			stmt.setBlob(1, image);
			stmt.setInt(2, idUser);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void deleteTravel(Connection connessione,int idtrip)
	{
		PreparedStatement preparedStmt=null;
		try {
				String query = "Delete from Trip where idTrip=? ";
			    preparedStmt = connessione.prepareStatement(query);
			    preparedStmt.setInt (1,idtrip);
			    preparedStmt.execute();
			    preparedStmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
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
	public void deleteAccount(Connection connessione,int iduser)
	{ 	
		PreparedStatement preparedStmt=null;
		try {
		
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
			 String query= userAttributeQuery+" from User where idUser="+id;
			 rs=stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet shortUserByID(Statement stmt, int id) throws SQLException {
		ResultSet rs=null;
		String query = "Select idUser,NameUser,Surname from User where idUser_"+id;
		rs=stmt.executeQuery(query);
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
	public ResultSet getCityByName(Statement stmt, CityEntity entity) {
		String query="SELECT NameC,State from City where NameC='"+entity.getNameC()+"' and State='"+entity.getState()+"'";
		ResultSet rs=null;
		try {
			 rs=stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void setCity(Connection connect, CityEntity entity) {
		String query="INSERT into City(NameC,State) values (?,?)";
		PreparedStatement stmt=null;
		try {
			 stmt=connect.prepareStatement(query);
			stmt.setString(1, entity.getNameC());
			stmt.setString(2, entity.getState());
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void deleteCity(Connection connect,CityEntity entity) {
		String query="DELETE from City where NameC=? and State=?";
		try {
			PreparedStatement prp=connect.prepareStatement(query);
			prp.setString(1, entity.getNameC());
			prp.setString(2, entity.getState());
			prp.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ResultSet getMessage(Statement stmt, MessageEntity message) {
		ResultSet rs=null;
		StringBuilder query=new StringBuilder();
		if(message.getIdMittente()==0) {
			if(message.getSoloNuovi()) {
				
				query.append("SELECT * FROM messaggio where Destinatario="+message.getIdDestinatario()+" and letto="+0);
				if(message.getLastTimeStamp()!=null) {
						query.append(" and data>'"+Timestamp.from(message.getLastTimeStamp())+"'");
						System.out.println("Try at :"+Timestamp.from(message.getLastTimeStamp()));
					}
			}
			else {
			query.append("SELECT * FROM messaggio where Destinatario="+message.getIdDestinatario());
			}
			
			
		}
		else {
			//Legge solo i messaggi inviati!!
			
				query.append("SELECT * FROM messaggio where  Mittente="+message.getIdMittente());
			
		}
		try {
			rs=stmt.executeQuery(query.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void sendMessage(Connection connect, MessageEntity message)throws SQLException {
		ResultSet rs=null;
		String query="INSERT INTO messaggio(Destinatario,Mittente,Testo,data) values (?,?,?,?)";
		PreparedStatement insertMex=connect.prepareStatement(query);
        insertMex.setInt(1, message.getIdDestinatario());
        insertMex.setInt(2, message.getIdMittente());
        insertMex.setString(3, message.getText());
        insertMex.setTimestamp(4, Timestamp.from(Instant.now()));
        //Mi pare che letto � a 0 di default
        insertMex.execute();
	}
	public void setReadMex(Statement stmt, MessageEntity message)throws SQLException{
		String query="UPDATE messaggio SET letto=1 where idMessaggio="+message.getIdMessaggio();
		stmt.execute(query);
	}
	public void deleteMex(Statement stmt, MessageEntity message)throws SQLException{
		String query;
		if(message.getIdMessaggio()!=0) {
			query="DELETE FROM messaggio where idMessaggio="+message.getIdMessaggio();
		}
		else {
			query="DELETE FROM messaggio where Destinatario="+message.getIdDestinatario()+" and Mittente="+message.getIdMittente();
		}
		stmt.execute(query);
	}	

	public static void main(String [] args)
	{
		try {
			Connection connessione=AllQuery.getInstance().getConnection();
			Statement stmt=connessione.createStatement();
			SearchEntity search=new SearchEntity();
			CityEntity citta=new CityEntity();
			citta.setNameC("Rome");
			citta.setState("Italy");
			search.setCity(citta);
			search.setMaxCost(2000);
			search.setMaxDay(10);
			search.setMinCost(0);
			search.setMinDay(0);
			search.setType("#");
			ResultSet rs=AllQuery.getInstance().searchTrip(stmt, search);
			rs.next();
			System.out.println(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}