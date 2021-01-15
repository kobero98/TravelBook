package main.java.travelbook.model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.Entity;
import java.util.List;
import java.util.ArrayList;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.MessageEntity;
public class MessageDao implements PersistanceDAO {
	private MessageEntity myEntity;
	private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Connection connection;
	private void connect() throws SQLException{
		if(connection==null || connection.isClosed()) {
			connection= DriverManager.getConnection(myUrl,"root","root");
		}
	}
	@Override
	public List<Entity> getData(Entity message){
		MessageEntity messaggio=(MessageEntity) message;
		List<Entity> results=new ArrayList<>();
		try {
			connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs=AllQuery.getInstance().getMessage(stmt, messaggio);
			while(rs.next()) {
				MessageEntity newM=new MessageEntity(rs.getInt("Mittente"),rs.getInt("Destinatario"));
				newM.setText(rs.getString("Testo"));
				newM.setTime(rs.getDate("data"));
				newM.setType(rs.getString("NomeViaggio"));
				results.add((Entity)newM);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	@Override
	public void setData() {
		
	}
	@Override
	public void delete(Entity obj) {
		
	}
	@Override
	public void update(Entity obj) {
		
	}
	@Override
	public Entity getMyEntity() {
		return (Entity)this.myEntity;
	}
	@Override
	public void setMyEntity(Entity entity) {
		this.myEntity=(MessageEntity) entity;
	}
}
