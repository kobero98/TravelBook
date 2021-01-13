package main.java.travelbook.model.dao;

import java.util.List;

import main.java.travelbook.controller.ExceptionLogin;
import main.java.travelbook.model.UserEntity;

public interface PersistanceDAO<T>{

	public List<T>  getData(T object) throws ExceptionLogin;
	public void setData() throws Exception;
	public Object getMyEntity();
	public void delete(T object);
	public void update(T object);
	void setMyEntity(T user);

}
