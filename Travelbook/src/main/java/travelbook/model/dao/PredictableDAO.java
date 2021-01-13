package main.java.travelbook.model.dao;
import java.util.List;
public interface PredictableDAO {
	public List<String> getPredictions(String text);
}
