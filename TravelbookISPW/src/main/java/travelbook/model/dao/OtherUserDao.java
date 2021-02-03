package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exception.LoginPageException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.OtherUserEntity;

public class OtherUserDao implements VisualDAO {

		
		
		private OtherUserEntity castRStoUser(ResultSet rs, int id) throws SQLException
		{
			rs.next();
			OtherUserEntity user;
			user = new OtherUserEntity(id);
			user.setName(rs.getString(1));
			user.setSurname(rs.getString(2));
			user.setBirthDate(rs.getDate(3));
			user.setDescription(rs.getString(4));
			user.setFollower(rs.getInt(5));
			user.setFollowing(rs.getInt(6));
			user.setNTravel(rs.getInt(7));
			user.setPhoto(rs.getBinaryStream(8));
			return user;
			
		}
		
		@Override
		public List <Entity> getData(Entity user1) throws LoginPageException, SQLException{
			final Connection connection;
			ResultSet rs=null;
			Statement stmt=null;
			OtherUserEntity user=(OtherUserEntity) user1;
			AllQuery db=AllQuery.getInstance();
			List <Entity> list=new ArrayList<>();
			try {
				connection = AllQuery.getInstance().getConnection();
			} catch (SQLException e1) {
				throw new LoginPageException("we couldn't reach our servers");
			}
			try {
				stmt=connection.createStatement();
				if(user.getId()!=0)
				{
					rs=db.requestUserbyID(stmt,user.getId());				
					OtherUserEntity utente=castRStoUser(rs, user.getId());
					
					stmt.close();
					
					stmt=connection.createStatement();
					rs=AllQuery.getInstance().requestListIDFavoriteTrip(stmt,utente.getId());	
					List <Integer> fav=new ArrayList<>();
					while(rs.next())
					{
						fav.add(rs.getInt(1));
					}
					utente.setFavoriteList(fav);
					stmt.close();
					
					stmt=connection.createStatement();
					rs=AllQuery.getInstance().requestListFollowerUser(stmt,utente.getId());	
					List <Integer> follower=new ArrayList<>();
					while(rs.next())
					{
						follower.add(rs.getInt(1));
					}
					utente.setListFollower(follower);
					stmt.close();
					
					stmt=connection.createStatement();
					rs=AllQuery.getInstance().requestListFollowingUser(stmt,utente.getId());	
					List <Integer> following=new ArrayList<>();
					while(rs.next())
					{
						following.add(rs.getInt(1));
					}
					utente.setListFollowing(following);
					stmt.close();
					
					stmt=connection.createStatement();
					rs=AllQuery.getInstance().requestTripByUser(stmt, utente.getId());	
					List <Integer> travel=new ArrayList<>();
					while(rs.next())
					{
						travel.add(rs.getInt(1));
					}
					utente.setTravel(travel);
					stmt.close();
					
					stmt=connection.createStatement();
					utente.setnPlace(AllQuery.getInstance().getPlaceVisited(stmt,utente.getId()));
					
					stmt.close();
					list.add((Entity) utente);
				}
				
			}catch(SQLException e){
				throw new LoginPageException("errore");
			}finally {
				if(stmt!=null)
				{
						stmt.close();
					
				}
			}
	 		
			
			return list;
		}

}
