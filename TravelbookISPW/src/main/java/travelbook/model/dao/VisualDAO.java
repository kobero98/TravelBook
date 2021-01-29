package main.java.travelbook.model.dao;

import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;

public interface VisualDAO {

	public List<Entity> getData(Entity object)throws DBException;
	
}
