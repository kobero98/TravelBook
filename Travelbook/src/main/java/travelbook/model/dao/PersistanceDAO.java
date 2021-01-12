package main.java.travelbook.model.dao;

import java.util.List;

public interface PersistanceDAO {

	public Object getData(List<Object> object);
	public void setData();
	public Object getMyEntity();
	
}
