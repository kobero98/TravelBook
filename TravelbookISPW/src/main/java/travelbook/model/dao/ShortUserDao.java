package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.exception.DBException;
import main.java.exception.LoginPageException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;

public class ShortUserDao implements VisualDAO {
	private UserEntity castRStoUser(ResultSet rs) throws SQLException
	{
		rs.next();
		
		UserEntity user;
		user = new UserEntity(rs.getInt(1));
		user.setName(rs.getString(2));
		user.setSurname(rs.getString(3));
		user.setPhoto(rs.getBinaryStream(4));
		user.setEmail(rs.getString(5));
		return user;
		
	}

	@Override
	public List<Entity> getData(Entity user1) throws DBException, SQLException {
		ResultSet rs=null;
		PreparedStatement stmt=null;
		Connection connection=null;
		UserEntity user=(UserEntity) user1;
		AllQuery db=AllQuery.getInstance();
		List <Entity> list=new ArrayList<>();
		try {
			connection = AllQuery.getInstance().getConnection();
		} catch (SQLException e1) {
			throw new LoginPageException("errore connect");
		}
		try {
			if(user.getId()!=0)
			{
				String query=db.shortUserByID(user.getId());
				stmt=connection.prepareStatement(query);
				stmt.setInt(1, user.getId());
				rs=stmt.executeQuery();
					do
					{
						UserEntity utente=castRStoUser(rs);
						list.add((Entity) utente);
					}while(rs.next());
			}
			
		} catch (SQLException e) {
			throw new DBException("connection lost");
		}finally {
			if(stmt!=null)
			{
				stmt.close();
				
			}
		}
		return list;
	}
	
}