package main.java.travelbook.model.dao;

import java.sql.SQLException;
import java.util.List;
import main.java.travelbook.model.Entity;

public interface PersistanceDAO{

	public List<Entity>  getData(Entity object) throws SQLException;
	public void setData() throws SQLException;
	public Entity getMyEntity();
	public void delete(Entity object)throws SQLException;
	public void update(Entity object)throws SQLException;
	void setMyEntity(Entity user)throws SQLException;

}
