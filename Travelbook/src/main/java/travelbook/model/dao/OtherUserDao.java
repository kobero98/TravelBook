package main.java.travelbook.model.dao;

import java.sql.SQLException;
import java.util.List;

import exception.ExceptionLogin;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.OtherUserEntity;

public class OtherUserDao implements PersistanceDAO {

	//private String myUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String myUrl="jdbc:mysql://25.93.110.25:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		
	@Override
	public List<Entity> getData(Entity object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity getMyEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Entity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Entity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMyEntity(Entity user) throws SQLException {
		// TODO Auto-generated method stub
		
	}



}
