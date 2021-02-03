package main.java.travelbook.model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import java.util.List;

import exception.DBException;

import java.util.ArrayList;
import main.java.travelbook.model.MessageEntity;
public class MessageDao implements PersistanceDAO {
	private MessageEntity myEntity;
	private Connection connection;

	@Override
	public List<Entity> getData(Entity message)throws DBException{
		MessageEntity messaggio=(MessageEntity) message;
		List<Entity> results=new ArrayList<>();
		
		try {
			this.connection = AllQuery.getInstance().getConnection();
		
			Statement stmt=connection.createStatement();
			ResultSet rs=AllQuery.getInstance().getMessage(stmt, messaggio,connection);
			while(rs.next()) {
				MessageEntity newM=new MessageEntity(rs.getInt("idmessaggio"),rs.getInt("Mittente"),rs.getInt("Destinatario"));
				newM.setText(rs.getString("Testo"));
				System.out.println("Testo:"+rs.getString("Testo")+" timestamp"+rs.getTimestamp("data"));
				newM.setTime(rs.getTimestamp("data").toInstant());
				newM.setType(rs.getString("NomeViaggio"));
				newM.setRead(rs.getInt("letto")==1);
				results.add((Entity)newM);
			}
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//throw new DBException("we can't reach your messages");
		}
		return results;
	}
	@Override
	public void setData() throws DBException {
		try {
				if(this.myEntity!=null) {
					this.connection = AllQuery.getInstance().getConnection();
					AllQuery.getInstance().sendMessage(this.connection, this.myEntity);
				}
			} catch (SQLException e) {
				throw new DBException("we can't send your message");
			}
	}
	@Override
	public void delete(Entity obj) {
		throw new UnsupportedOperationException();
		
	}
	@Override
	public void update(Entity obj) throws DBException  {
		MessageEntity entityToBeUpdated=(MessageEntity) obj;
		try {
			this.connection = AllQuery.getInstance().getConnection();
		AllQuery.getInstance().setReadMex(this.connection.createStatement(), entityToBeUpdated);
		}catch(SQLException e) {
			throw new DBException("we couldn't update your information");
		}
	}
	@Override
	public Entity getMyEntity() {
		return this.myEntity;
	}
	@Override
	public void setMyEntity(Entity entity) {
		this.myEntity=(MessageEntity) entity;
	}
}
