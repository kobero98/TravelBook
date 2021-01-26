package main.java.travelbook.model.dao;

import java.sql.SQLException;

import exception.DBException;
import exception.LoginPageException;
import main.java.travelbook.model.Entity;

public interface PersistanceDAO extends VisualDAO{

	public void setData() throws DBException;
	public Entity getMyEntity();
	public void delete(Entity object)throws DBException;
	public void update(Entity object)throws DBException;
	void setMyEntity(Entity user)throws DBException;

}
