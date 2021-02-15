package main.java.travelbook.util;
import java.util.List;
import java.util.ArrayList;
public class Observable {
	private List<Observer> observers=new ArrayList<>();
	private boolean changed=false;
	public void notifyObservers() {
		for(Observer obs: observers) {
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
		if(this.observers.contains(o))
			this.observers.remove(o);
	}
	public void setChanged() {
		this.changed=true;
		this.notifyObservers();
	}
	public boolean isChanged() {
		return this.changed;
	}
	public void setRead() {
		this.changed=false;
	}
	public boolean isObserved() {
		return !(this.observers.isEmpty());
	}
}
