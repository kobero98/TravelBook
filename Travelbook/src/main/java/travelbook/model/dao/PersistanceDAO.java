package main.java.travelbook.model.dao;

import java.sql.SQLException;
import main.java.travelbook.model.Entity;

public interface PersistanceDAO extends VisualDAO{

	public void setData() throws SQLException;
	public Entity getMyEntity();
	public void delete(Entity object)throws SQLException;
	public void update(Entity object)throws SQLException;
	void setMyEntity(Entity user)throws SQLException;

}
