package main.java.travelbook.model.dao;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.Entity;
public interface PredictableDAO {
	public List<Entity> getPredictions(String text) throws DBException;
}
