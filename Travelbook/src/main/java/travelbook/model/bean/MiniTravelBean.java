package main.java.travelbook.model.bean;

import javafx.scene.image.Image;
import main.java.travelbook.model.TravelEntity;

public class MiniTravelBean{
	private String nameTravel;
	private String descriptionTravel;
	private Image pathBackground;
	
	public MiniTravelBean() {}
	
	public MiniTravelBean(TravelEntity travel) //creare modo per prendere dal database sta mini travel bean
	{
		this.nameTravel=travel.getNameTravel();
		this.pathBackground=new Image(travel.getPathImage());
		this.descriptionTravel=travel.getDescriptionTravel();
		
	}
	
	public String getDescriptionTravel() {
		return this.descriptionTravel;
	}
	
	public String getNameTravel() {
		return this.nameTravel;
	}
	
	public Image getPathImage() {
		return this.pathBackground;
	}
}