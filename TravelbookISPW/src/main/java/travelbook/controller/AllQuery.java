package main.java.travelbook.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

import java.io.InputStream;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import exception.ExceptionLogin;
import exception.ExceptionRegistration;
import exception.LoginPageException;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.MessageEntity;
import main.java.travelbook.model.SearchEntity;
import main.java.travelbook.model.ShareEntity;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;
import main.java.travelbook.model.UserEntity;

public class AllQuery {
	private static final String EMAILREQUEST="select EmailT,PswdEmail from emailtravelbook";
	private static final String CITYREQUEST="SELECT NameC,State from City where NameC=? and State=?";
	private static final String CITYAUTOCOMPLETE="SELECT NameC,State from City where NameC like ? order by char_length(NameC),char_length(State)";
	private static final String QUERYUSERID= "Select NameUser, Surname, BirthDate, DescriptionProfile, TripNumber, ProfileImage from User where idUser=?";
	private static AllQuery instance=null;
	private static final String FAVORITEID="Select CodiceTravel from Favorite where codiceUser=?";
	private static final String USERAUTOCOMPLETE="SELECT idUser, NameUser, Surname,Username,ProfileImage from User where NameUser like ? and Surname like ? order by char_length(NameUser),char_length(Surname)";
	private AllQuery() {}
	public static AllQuery getInstance() {
		if(instance==null) instance=new AllQuery();
		return instance;
	}
	public Connection getConnection() throws SQLException {
		ClasseConnessione c=new ClasseConnessione();
		return c.getConenction();
	}
	
	public String searchTrip(SearchEntity entity) 
	{
		StringBuilder q=new StringBuilder();
		q.append("Select idTrip,nome,Descriptiontravel,PhotoBackground from trip join trip_has_city on idTrip=CodiceViaggi and CreatorTrip=CodiceCreatore where Condiviso=1 and City_NameC like ? and City_State like ?  and costo>=?  and tipo like ? and DATEDIFF(EndDate,StartDate)>=?");
		if(entity.getMaxCost()!=null) {
			q.append( " and costo<=?");

		}
		if(entity.getMaxDay()!=null)
		{
			q.append( " and DATEDIFF(EndDate,StartDate)<=?");

		}
		return q.toString();
	}
	public String requestLogin(Connection conn,String username,String password) throws ExceptionLogin{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String query="";
			try {
			try {
			stmt=conn.prepareStatement("Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where Username=?");
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if(rs.next()) {
				stmt.close();
				query="Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where Username=? and password=?";
				stmt=conn.prepareStatement("Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where Username=? and password=?");
				rs=selectUser(stmt,username,password);
				stmt.close();
			}
			else {
				stmt.close();
				rs.close();
				stmt=conn.prepareStatement("Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where email=?");
				stmt.setString(1, username);
				 rs = stmt.executeQuery();
				 if(rs.next()) {
					 	stmt.close();
					 	query="Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where email=? and password=?";
					 	stmt=conn.prepareStatement("Select idUser,NameUser,Surname,Birthdate,DescriptionProfile,Email,TripNumber,ProfileImage,Gender,Nazionalita FROM User where email=? and password=?");
					 	rs=selectUser(stmt,username,password); 
					}
				 else throw new ExceptionLogin("Wrong Username or password");	 
			}
			return query;
			}finally {
				if(stmt!=null)
					stmt.close();
			}
			}catch(SQLException e){
				throw new ExceptionLogin("Unexpected error");
			}
	
	}
	private ResultSet selectUser(PreparedStatement stmt, String username, String password) throws SQLException, ExceptionLogin{
		ResultSet rs=null;
		stmt.setString(1, username);
	 	stmt.setString(2, password);
	    rs= stmt.executeQuery();
		if(!rs.next()) throw new ExceptionLogin("Wrong password");	
		return rs;
	}
	public String requestTripById(int idTrip)
	{	
		
			String query="SELECT idTrip,nome,costo,tipo,Nlike,StartDate,EndDate,StepNumber,PhotoBackground,DescriptionTravel,CreatorTrip,Condiviso FROM Trip where idTrip=?";
			if(idTrip>=0)
				return query;
			return null;
	
	
	}
	public String getEmail(){
		return EMAILREQUEST;
	}
	public int getPlaceVisited( Connection conn,int id) throws SQLException
	{
		
		String query="Select count(*) as name from (SELECT Distinct city.NameC FROM trip_has_city join city  on City_NameC=NameC and city_State=State where codiceCreatore=?) as Place";
		try(PreparedStatement stmt=conn.prepareStatement(query)){
		stmt.setInt(1, id);
		ResultSet rs= stmt.executeQuery();
		rs.next();
		return rs.getInt(1);
		}
	}
	public String requestTripByUser(int idCreator)
	{	
		String query="SELECT idTrip, nome, costo, tipo, Nlike , StartDate, EndDate, StepNumber, PhotoBackground, DescriptionTravel, CreatorTrip, Condiviso FROM Trip WHERE CreatorTrip=?";
		if(idCreator>=0)
			return query;
		return null;
	
	}
	
	public String requestStepByTrip(int idTrip)
	{
		String query="SELECT * FROM Step WHERE CodiceTrip=?";
		if(idTrip>=0)
			return query;
		return null;
	}
	
	public Integer getVerifiedEmail(String email)throws SQLException
	{
		Connection conn=getConnection();
		String query="select idUser from user where email like ?";
		try(	PreparedStatement stmt=conn.prepareStatement(query)){
			stmt.setString(1, email);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) return rs.getInt(1);
			else return 0;
	}
	}
	
	public UserEntity controlloEsistenzaAccount(String id) throws SQLException
	{
			PreparedStatement stmt=null;
			Connection conn=null;
			try {	
				String query=" Select username,password from user join facebooklogin on facebooklogin.idUser=user.idUser where idFacebookLogin = ?";
				conn=getConnection();
				stmt=conn.prepareStatement(query);
				stmt.setString(1, id);
				ResultSet rs=stmt.executeQuery();
				if(rs.next()) { 
					UserEntity user= new UserEntity();
					user.setPassword(rs.getString(2));
					user.setUsername(rs.getString(1));
					return user;
				}
				else return null;
				
				}finally{
					if(stmt!=null)stmt.close();
					if(conn!=null)conn.close();
				}
	}
	
	public String requestPhotoByStep(int idStep,int idTravel)  {
		
		String query="SELECT LinkPhoto from photostep where Step_Number=? and CodiceViaggio=?";
		if(idStep>=0 && idTravel>=0)
			return query;
		return null;
	}
	public UserEntity insertFacebookUser(Connection conn,String idF,int id) throws SQLException
	{
		PreparedStatement stmt=null;
		PreparedStatement stmt1=null;
		try{
			String query="Insert into facebooklogin(idFacebookLogin,idUser) values (?,?)";
			stmt=conn.prepareStatement(query);
			stmt.setString(1, idF);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			query="select username,password from user where idUser=?";
			try {
			stmt1=conn.prepareStatement(query);
			stmt1.setInt(1, id);
			ResultSet rs=stmt1.executeQuery();
			rs.next();
			UserEntity user=new UserEntity();
			user.setUsername(rs.getString(1));
			user.setPassword(rs.getString(2));
			return user;
			}finally {
				if(stmt1!=null)
					stmt1.close();
			}
		}finally {
			if(stmt!=null) 
				stmt.close();
			if(stmt1!=null) 
				stmt1.close();
			conn.close();
		}
		}

	public void requestRegistrationUser(Connection conn,UserEntity user) throws LoginPageException,SQLException {
				  PreparedStatement preparedStmt =null;
				
					  String query = " insert into User (Username, password,NameUser, Surname, BirthDate,Email,Gender,Nazionalita)" + " values (?, ?, ?, ?, ?, ?, ?,?)";
					  try {
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString (1, user.getUsername());
						    preparedStmt.setString (2, user.getPassword());
						    preparedStmt.setString (3, user.getName());
						    preparedStmt.setString (4, user.getSurname());
						    preparedStmt.setDate   (5,user.getBirthDate());// il data va sistemato
						    preparedStmt.setString (6,user.getEmail());
						    preparedStmt.setString (7,user.getGender());
						    preparedStmt.setString (8, user.getNation());
						    preparedStmt.execute();
						    preparedStmt.close();
				     }catch(SQLIntegrityConstraintViolationException e) {
						throw new ExceptionRegistration("User already exist");
					}catch(MysqlDataTruncation e){
						throw new ExceptionRegistration("Unvalid data");
					}catch(SQLException e){
						throw new LoginPageException("Error while cennecting to database");
					}finally {
						if(preparedStmt!=null) preparedStmt.close();
							
					}
			   
				  
	}
	
	public void requestRegistrationStep(Connection connessione,StepEntity step) throws SQLException {
		PreparedStatement preparedStmt=null;
		try {
				  String query = " insert into Step (groupDay,place,DescriptionStep,codiceTrip,codiceCreatore,NumberDay,Number,PrecisionInformation) values( ?, ?, ?, ?, ?, ?,?,?) ";
			      preparedStmt = connessione.prepareStatement(query);
			      
			      preparedStmt.setInt (1, step.getGroupDay());
			      preparedStmt.setString (2, step.getPlace());
			      preparedStmt.setString (3, step.getDescriptionStep());
			      preparedStmt.setInt (4, step.getIDTravel());
			      preparedStmt.setInt (5, step.getIDCreator());
			      preparedStmt.setInt (6, step.getNumberOfDay());
			      preparedStmt.setInt(7, step.getNumber());
			      preparedStmt.setString(8, step.getPrecisionInformation());
			      
			      preparedStmt.execute();
			      preparedStmt.close();
			      
			    if(step.getListPhoto()!=null) {
			    	for(int i=0;i<step.getBytes().size();i++) {
			    		String queryPhoto = " insert into photostep (LinkPhoto,Step_Number,codiceViaggio,codiceCreatoreViaggio) values(?,?,?,?) ";
					    preparedStmt = connessione.prepareStatement(queryPhoto);
					    
							
							preparedStmt.setBytes(1, step.getBytes().get(i).toByteArray());
							
						      preparedStmt.setInt (2, step.getNumber());
						      preparedStmt.setInt (3, step.getIDTravel());
						      preparedStmt.setInt (4, step.getIDCreator());
						      preparedStmt.execute();  
						      preparedStmt.close();
						
			    	}
			    }
			      
			     
		}finally {
			
			if(preparedStmt!=null) {
						preparedStmt.close();
			}
			
		}
	}
	
	public Integer requestRegistrationTrip(Connection connessione,TravelEntity trip) throws SQLException {
			PreparedStatement preparedStmt=null;
			  try{
					  String query = " insert into Trip (nome,costo,tipo,StartDate,EndDate,StepNumber,PhotoBackground,DescriptionTravel,CreatorTrip,Condiviso)" + " values (?, ?, ?, ?, ?, ?, ? ,? ,?,?)";
					  preparedStmt = connessione.prepareStatement(query);
					  preparedStmt.setString (1, trip.getNameTravel());
					  preparedStmt.setDouble (2, trip.getCostTravel());
					  preparedStmt.setString (3, trip.getTypeTravel());
					  preparedStmt.setDate (4, trip.getStartDate());
					  preparedStmt.setDate   (5,trip.getEndDate());
					  preparedStmt.setInt(6, trip.getStepNumber());
					  preparedStmt.setBinaryStream(7,trip.getImage());
					  preparedStmt.setString (8,trip.getDescriptionTravel());
					  preparedStmt.setInt (9,trip.getCreatorId());
					  preparedStmt.setInt(10, trip.getShare());
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
	
	public String requestListIDFavoriteTrip()  {
	
		return FAVORITEID;	 
	}
	
	public String requestListFollowerUser(int idUser)  {
		
		String query="Select Follower from Follow where Following=?";
		if(idUser>=0)
			return query;
		return null;
	}
	public String requestListFollowingUser(int idUser)  {
		String query="Select Following from Follow where Follower=?";
		if(idUser>=0)
			return query;
		return null; 
	}
	public String requestShortTravel(int idTrip){
		String query="Select idTrip,nome,Descriptiontravel,PhotoBackground,Condiviso from trip where idTrip=?";
		if(idTrip>=0)
			return query;
		return null;
	}
	public String requestCityByTravelId(int idTravel) 
	{
		String query="Select City.NameC, City.State from Trip_has_City join City on NameC=City_NameC and City_State=State where CodiceViaggi=?";
		if(idTravel>=0)
			return query;
		return null;
		
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
		PreparedStatement stmt=null;
		try {
				String query="select CreatorTrip from trip where idTrip=?";
				stmt=connessione.prepareStatement(query);
				stmt.setInt(1, idTravel);
				ResultSet rs=stmt.executeQuery();
				rs.next();
				int cretorTrip=rs.getInt(1);
				stmt.close();
				query="select * from favorite where CodiceUser=? and CodiceTravel=?";
				try(PreparedStatement stmt2=connessione.prepareStatement(query)) {
						
						stmt2.setInt(1, idUser);
						stmt2.setInt(2, idTravel);
						rs=stmt2.executeQuery();
						
						if(!rs.next()) {
							try {
							String query3="insert into favorite (CodiceUser,codiceTravel,codiceCreatore) values( ?,?,?)";
							stmt1=connessione.prepareStatement(query3);
							stmt1.setInt(1,idUser );
							stmt1.setInt(2, idTravel);
							stmt1.setInt(3, cretorTrip);
							stmt1.execute();
							}finally {
								if(stmt1!=null)
									stmt1.close();
							}
						
							String query2="Select Nlike from trip where idTrip=?";
							stmt=connessione.prepareStatement(query2);
							stmt.setInt(1, idTravel);
							ResultSet rs1=stmt.executeQuery();
							rs1.next();
							int i=rs1.getInt(1);
							stmt.close();
							try {
								stmt1=connessione.prepareStatement("update Trip set Nlike= ? where idTrip= ?");
								stmt1.setInt(1, i+1);
								stmt1.setInt(2,idTravel );
								stmt1.execute();
							}finally {
									stmt1.close();
							}
						}
						else {
							
							String s="delete from Favorite where CodiceUser=? and CodiceTravel=? ";
							try {
							stmt1=connessione.prepareStatement(s);
							stmt1.setInt(1, idUser);
							stmt1.setInt(2, idTravel);
							stmt1.execute();
							}finally {
								if(stmt1!=null)
									stmt1.close();
							}
							query="Select Nlike from trip where idTrip=?";
							stmt=connessione.prepareStatement(query);
							stmt.setInt(1, idTravel);
							ResultSet rs1=stmt.executeQuery();
							rs1.next();
							int i=rs1.getInt(1);
							stmt.close();
							try {
							stmt1=connessione.prepareStatement("update Trip set Nlike= ? where idTrip= ?");
							stmt1.setInt(1, i-1);
							stmt1.setInt(2,idTravel );
							stmt1.execute();
							stmt1.close();
					}finally {
							
							stmt1.close();
					}
				}}finally {
						stmt.close();
				
				}
		}finally {
			if(stmt!=null) stmt.close();
			if(stmt1!=null) stmt1.close();
 		}
		
		
	}
	
	public void updateTravelNumberForUser(Connection connessione,int idUser) throws SQLException {
		PreparedStatement stmt=null;
		String query="update User set TripNumber= ? where idUser=?";
			String q="Select Count(idTrip) as tripNumber from trip where CreatorTrip=?";
			try(PreparedStatement stmt2=connessione.prepareStatement(q)){
				stmt2.setInt(1, idUser);
				ResultSet rs=stmt2.executeQuery();
				rs.next();
				stmt=connessione.prepareStatement(query);
				stmt.setInt(1, rs.getInt(1));
				stmt.setInt(2, idUser);
				stmt.execute();
		}finally {
			if(stmt!=null ) {
					stmt.close();
			}
				
		}
	}
	public void updateListFollower(Connection connessione, Integer idFollower, Integer idFollowed) throws SQLException {
		PreparedStatement stmt1=null;
		PreparedStatement stmt=null;
		try {
				String query="Select * from Follow where follower = ? and following =?";
				try {
				stmt1 = connessione.prepareStatement(query);
				stmt1.setInt(1, idFollower);
				stmt1.setInt(2, idFollowed);
				ResultSet rs = stmt1.executeQuery();
				if(!rs.next()) {
					stmt=connessione.prepareStatement("Insert Into Follow (follower,following) values (?,?)");
					stmt.setInt(1, idFollower);
					stmt.setInt(2,idFollowed);
					stmt.execute();
					stmt.close();
				}
				else {
					stmt = connessione.prepareStatement("Delete From Follow where follower = ? and following = ?");
					stmt.setInt(1, idFollower);
					stmt.setInt(2,idFollowed);
					stmt.execute();
					stmt.close();
				}
				}finally {
					if(stmt1!=null)
						stmt1.close();
				}
		}finally {
			if(stmt!=null) stmt.close();
			if(stmt1!=null) stmt1.close();
 		}
	}
	
	public void updateDescriptionUser(Connection connessione,int iduser,String description) throws SQLException {
		PreparedStatement stmt=null;
		String query="update User set DescriptionProfile= ? where idUser=?";
		try {
			stmt=connessione.prepareStatement(query);
			stmt.setString(1, description);
			stmt.setInt(2, iduser);
			stmt.execute();
		
		}finally {
			if(stmt!=null)
					stmt.close();
		}
	}
	
	public void updatePhotoProfile(Connection connessione,int idUser,InputStream image) throws SQLException {
		PreparedStatement stmt=null;
		String query="update User set ProfileImage= ? where idUser=?";
		try {
			stmt=connessione.prepareStatement(query);
			stmt.setBlob(1, image);
			stmt.setInt(2, idUser);
			stmt.execute();
		}finally {
			if(stmt!=null)
					stmt.close();
		}
	}

	public void deleteTravel(Connection connessione,int idtrip) throws SQLException
	{
		PreparedStatement preparedStmt=null;
		try {
				String query = "Delete from Trip where idTrip=? ";
			    preparedStmt = connessione.prepareStatement(query);
			    preparedStmt.setInt (1,idtrip);
			    preparedStmt.execute();
			    preparedStmt.close();
		}finally {
			if(preparedStmt!=null) {

						preparedStmt.close();
			}
		}
	}
	public void deleteAccount(Connection connessione,int iduser) throws SQLException
	{ 	
		
		String query = "Delete from User where idUser=? ";
		try (PreparedStatement preparedStmt = connessione.prepareStatement(query)){     
			 preparedStmt.setInt (1,iduser);
			 preparedStmt.execute();
		}finally {
			connessione.close();
		}
	}
	public String requestUserbyID() {
		
			
			 return QUERYUSERID;
	}
	public String shortUserByID( int id)  {
		
		String query = "Select idUser,NameUser,Surname, ProfileImage, Email from User where idUser=?";
		if(id>=0)
			return query;
		return null;
	
	}
	public String cityAutocompleteRequest() {
		
		return CITYAUTOCOMPLETE;
	}
	public String userAutocompleteRequest(){
		return USERAUTOCOMPLETE;
	}
	public String getCityByName() {
		
		return CITYREQUEST;
	}
	public void setCity(Connection connect, CityEntity entity) throws SQLException {
		String query="INSERT into City(NameC,State) values (?,?)";
		PreparedStatement stmt=null;
		try {				

			 stmt=connect.prepareStatement(query);
			 stmt.setString(1, entity.getNameC());
		     stmt.setString(2, entity.getState());
			 stmt.execute();
		}finally {
			if(stmt!=null)
					stmt.close();
		}
	}
	public void deleteCity(Connection connect,CityEntity entity) throws SQLException {
		String query="DELETE from City where NameC=? and State=?";
				try(PreparedStatement prp=connect.prepareStatement(query)){
				prp.setString(1, entity.getNameC());
				prp.setString(2, entity.getState());
				prp.execute();
			}
		
	}
	public String getMessage(MessageEntity message)  {
		String query="";
		if(message.getIdMittente()==0) {
			if(message.getSoloNuovi())
				query="SELECT * FROM messaggio where Destinatario=? and letto=0 and data>=?";
			else {
				query="SELECT * FROM messaggio where Destinatario=?";
			}
		}
		else {
			//Legge solo i messaggi inviati!!
			
				query="SELECT * FROM messaggio where  Mittente=?";
			
		}
		return query;
	}
	public void sendMessage(Connection connect, MessageEntity message)throws SQLException {
		String query="INSERT INTO messaggio(Destinatario,Mittente,Testo,data) values (?,?,?,?)";
		try(PreparedStatement insertMex=connect.prepareStatement(query)){
        insertMex.setInt(1, message.getIdDestinatario());
        insertMex.setInt(2, message.getIdMittente());
        insertMex.setString(3, message.getText());
        insertMex.setTimestamp(4, Timestamp.from(Instant.now()));
        //Mi pare che letto � a 0 di default
        insertMex.execute();
		}
	}
	public void setReadMex(Connection conn, MessageEntity message)throws SQLException{
		String query="UPDATE messaggio SET letto=1 where idmessaggio=?";
		try(PreparedStatement stmt=conn.prepareStatement(query)){
			stmt.setInt(1, message.getIdMessaggio());
			stmt.execute();
		}
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
	public void shareTravel(Connection conn, ShareEntity shared) throws SQLException {
		String query="INSERT INTO viaggicondivisi values(?,?,?,?)";
		try(PreparedStatement insert=conn.prepareStatement(query)){
		insert.setInt(1, shared.getWhoShare());
		insert.setInt(2, shared.getWhoReceive());
		insert.setInt(3, shared.getTravelShared());
		insert.setInt(4, shared.getCreator());
		insert.execute();
		conn.close();
		}
	}
	public String getShared(int userId){
		
		String query="SELECT * FROM viaggicondivisi where AchiVieneCondiviso=?";
		if(userId>=0)
			return query;
		return null;
	}
	public String getTravels(UserEntity user){
		String query="";
		if(!user.getListFollower().isEmpty() || !user.getListFollowing().isEmpty()) {
			query="SELECT distinct idTrip,dataCreazione from trip join follow on (CreatorTrip=Follower or CreatorTrip=Following) where (Follower=? or Following=?) and CreatorTrip!=? and Condiviso=1 order by dataCreazione desc";
		}
		else {
			query="SELECT idTrip from trip  where CreatorTrip!=? and Condiviso=1 order by dataCreazione desc";
		}
		return query;
	}
	public void changePassword(UserEntity user,Connection conn)throws SQLException {
		String query="UPDATE user set password=? where email=?";
		try(PreparedStatement stmt=conn.prepareStatement(query)){
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getEmail());
			stmt.execute();
		}
	}
	
}