package main.java.travelbook.model.dao;



import exception.DBException;
import main.java.travelbook.model.Entity;

public interface PersistanceDAO extends VisualDAO{

	public void setData() throws DBException;
	public Entity getMyEntity();
	public void delete(Entity object)throws DBException;
	public void update(Entity object)throws DBException;
	void setMyEntity(Entity user)throws DBException;

}
