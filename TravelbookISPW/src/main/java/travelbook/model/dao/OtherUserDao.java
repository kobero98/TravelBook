package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			PreparedStatement stmt=null;
			OtherUserEntity user=(OtherUserEntity) user1;
			AllQuery db=AllQuery.getInstance();
			List <Entity> list=new ArrayList<>();
			try {
				connection = AllQuery.getInstance().getConnection();
			} catch (SQLException e1) {
				throw new LoginPageException("we couldn't reach our servers");
			}
			try {
				String query=db.requestUserbyID();
				stmt=connection.prepareStatement(query);
				if(user.getId()!=0)
				{
					stmt.setInt(1, user.getId());
					rs=stmt.executeQuery();			
					OtherUserEntity utente=castRStoUser(rs, user.getId());
					stmt.close();
					
					
					query=AllQuery.getInstance().requestListIDFavoriteTrip();	
					stmt=connection.prepareStatement(query);
					stmt.setInt(1, utente.getId());
					rs=stmt.executeQuery();
					List <Integer> fav=new ArrayList<>();
					while(rs.next())
					{
						fav.add(rs.getInt(1));
					}
					utente.setFavoriteList(fav);
					stmt.close();
					
					
					query=AllQuery.getInstance().requestListFollowerUser(utente.getId());	
					stmt=connection.prepareStatement(query);
					stmt.setInt(1, utente.getId());
					List <Integer> follower=new ArrayList<>();
					while(rs.next())
					{
						follower.add(rs.getInt(1));
					}
					utente.setListFollower(follower);
					stmt.close();
					query=AllQuery.getInstance().requestListFollowingUser(utente.getId());
					stmt=connection.prepareStatement(query);
					stmt.setInt(1, utente.getId());
					List <Integer> following=new ArrayList<>();
					while(rs.next())
					{
						following.add(rs.getInt(1));
					}
					utente.setListFollowing(following);
					stmt.close();
					query=AllQuery.getInstance().requestTripByUser(utente.getId());
					stmt=connection.prepareStatement(query);
					stmt.setInt(1, utente.getId());;
					rs=stmt.executeQuery();
					List <Integer> travel=new ArrayList<>();
					while(rs.next())
					{
						travel.add(rs.getInt(1));
					}
					utente.setTravel(travel);
					stmt.close();
					
					
					utente.setnPlace(AllQuery.getInstance().getPlaceVisited(connection,utente.getId()));
					
					connection.close();
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
