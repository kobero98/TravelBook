package main.java.travelbook.util;
import java.util.List;
import java.util.ArrayList;
public class Observable {
	private List<Observer> observers=new ArrayList<>();
	public void notifyObservers() {
		for(Observer obs: observers) {
			System.out.println("NOTIFICATO by"+this);
			obs.update(this);
		}
	}
	public void notifyObservers(Object arg) {
		for(Observer obs: observers) {
			obs.update(this, arg);
		}
	}
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}
	public void cleanObserver() {
		this.observers.removeAll(this.observers);
	}
	public void deleteObserver(Observer o) {
		this.observers.remove(o);
	}
	public void setChanged() {
		this.notifyObservers();
	}
	public boolean isObserved() {
		return !(this.observers.isEmpty());
	}
}
