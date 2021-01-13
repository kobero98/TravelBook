package main.java.travelbook.model.dao;
import java.util.List;
public interface PredictableDAO<T> {
	public List<T> getPredictions(String text);
}
