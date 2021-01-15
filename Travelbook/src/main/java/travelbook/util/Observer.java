package main.java.travelbook.util;

public interface Observer {
	public void update(Observable obs, Object arg);
	public void update(Observable obs);
}
