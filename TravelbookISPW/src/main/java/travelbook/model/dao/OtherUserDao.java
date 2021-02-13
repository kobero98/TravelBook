package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.exception.DBException;
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
			
			user.setNTravel(rs.getInt(5));
			user.setPhoto(rs.getBinaryStream(6));
			return user;
			
		}
		
		@Override
		public List <Entity> getData(Entity user1) throws DBException, SQLException{
			final Connection connection;
			ResultSet otherRs=null;
			PreparedStatement otherStmt=null;
			OtherUserEntity otherUser=(OtherUserEntity) user1;
			AllQuery db=AllQuery.getInstance();
			List <Entity> list=new ArrayList<>();
			try {
				connection = AllQuery.getInstance().getConnection();
				String query=db.requestUserbyID();
				otherStmt=connection.prepareStatement(query);
				if(otherUser.getId()!=0)
				{
					otherStmt.setInt(1, otherUser.getId());
					otherRs=otherStmt.executeQuery();			
					OtherUserEntity utente=castRStoUser(otherRs, otherUser.getId());
					otherStmt.close();
					
					
					query=AllQuery.getInstance().requestListIDFavoriteTrip();	
					otherStmt=connection.prepareStatement(query);
					otherStmt.setInt(1, utente.getId());
					otherRs=otherStmt.executeQuery();
					List <Integer> fav=new ArrayList<>();
					while(otherRs.next())
					{
						fav.add(otherRs.getInt(1));
					}
					utente.setFavoriteList(fav);
					otherStmt.close();
					
					
					query=AllQuery.getInstance().requestListFollowerUser(utente.getId());	
					otherStmt=connection.prepareStatement(query);
					otherStmt.setInt(1, utente.getId());
					otherRs=otherStmt.executeQuery();
					List <Integer> follower=new ArrayList<>();
					while(otherRs.next())
					{
						follower.add(otherRs.getInt(1));
					}
					utente.setListFollower(follower);
					otherStmt.close();
					query=AllQuery.getInstance().requestListFollowingUser(utente.getId());
					otherStmt=connection.prepareStatement(query);
					otherStmt.setInt(1, utente.getId());
					otherRs=otherStmt.executeQuery();
					List <Integer> following=new ArrayList<>();
					while(otherRs.next())
					{
						following.add(otherRs.getInt(1));
					}
					utente.setListFollowing(following);
					otherStmt.close();
					query=AllQuery.getInstance().requestTripByUser(utente.getId());
					otherStmt=connection.prepareStatement(query);
					otherStmt.setInt(1, utente.getId());
					otherRs=otherStmt.executeQuery();
					List <Integer> travel=new ArrayList<>();
					while(otherRs.next())
					{
						travel.add(otherRs.getInt(1));
					}
					utente.setTravel(travel);
					otherStmt.close();
					
					utente.setnPlace(AllQuery.getInstance().getPlaceVisited(connection,utente.getId()));
					
					connection.close();
					list.add((Entity) utente);
				}
				
			}catch(SQLException e){
				throw new DBException("Error while loading data");
			}finally {
				if(otherStmt!=null)
				{
						otherStmt.close();
					
				}
			}
	 		
			
			return list;
		}

}
