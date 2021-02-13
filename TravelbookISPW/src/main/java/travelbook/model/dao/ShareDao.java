package main.java.travelbook.model.dao;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.java.travelbook.model.ShareEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;

import main.java.exception.DBException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;

public class ShareDao implements PersistanceDAO {
	ShareEntity myEntity;
	@Override
	public List<Entity> getData(Entity obj) throws DBException{
		List<Entity> condivisioni=new ArrayList<>();
		try {
		ShareEntity user=(ShareEntity)obj;
		String query=AllQuery.getInstance().getShared(user.getWhoReceive());
		Connection conn=AllQuery.getInstance().getConnection();
		try(PreparedStatement stmt=conn.prepareStatement(query)){
		stmt.setInt(1, user.getWhoReceive());
		ResultSet rs=stmt.executeQuery();
		while(rs.next()) {
			ShareEntity share=new ShareEntity();
			share.setCreator(rs.getInt(4));
			share.setTravelShared(rs.getInt(3));
			share.setWhoReceive(rs.getInt(2));
			share.setWhoShare(rs.getInt(1));
			condivisioni.add(share);
		}
		conn.close();
		}
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		return condivisioni;
	}
	@Override
	public void setData() throws DBException{
		try {
		Connection conn=AllQuery.getInstance().getConnection();
		AllQuery.getInstance().shareTravel(conn, this.myEntity);
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	@Override
	public void delete(Entity object) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Entity getMyEntity() {
		return this.myEntity;
	}
	
	@Override
	public void update(Entity object) throws DBException{
		throw new UnsupportedOperationException();
		
	}
	@Override
	public void setMyEntity(Entity user) throws DBException {
		this.myEntity=(ShareEntity)user;
		
	}
}
