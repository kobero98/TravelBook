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
		return user;
		
	}

	@Override
	public List<Entity> getData(Entity user1) throws SQLException {
		ResultSet rs=null;
		Statement stmt=null;
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
			stmt=connection.createStatement();
			if(user.getId()!=0)
			{
				rs=db.shortUserByID(stmt,user.getId());
					do
					{
						UserEntity utente=castRStoUser(rs);
						list.add((Entity) utente);
					}while(rs.next());
			}
			
		}finally {
			if(stmt!=null)
			{
				try {
					stmt.close();
				}catch(SQLException e) {
					e.getStackTrace();
				}
				
			}
		}
		return list;
	}
	
}