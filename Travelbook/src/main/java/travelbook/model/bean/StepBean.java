package main.java.travelbook.model.bean;

import java.util.List;

import javafx.scene.image.Image;

public class StepBean {
	private int Number,IDTravel,IDCreator,GroupDay;
	private String DescriptionStep,Place;
	private String Day;
	private List <Image> Photo;
	
	public  StepBean(){}
	public  StepBean(int Number,int IDTravel,int IDCreator){
		this.Number=Number;
		this.IDCreator=IDCreator;
		this.IDTravel=IDTravel;
	}
	
	public int getNumber()
	{
		return this.Number;
	}
	public int getIDTravel() {
		return this.IDTravel;
	}
	public int getIDCreator() {
		return this.IDCreator;
	}
	public int getGroupDay() {
		return this.GroupDay;
	}
	public String getDescriptionStep() {
		return this.DescriptionStep;
	}
	public String getPlace() {
		return this.Place;
	}
	public String getDay()
	{
		return this.Day;
	}
	public List<Image> getListPhoto(){
		return this.Photo;
	}
	
	public void setDescriptionStep(String Description)
	{
		this.DescriptionStep=Description;
	}
	public void setPlace(String Location)
	{
		this.Place=Location;
	}
	public void setDay(String day)
	{
		this.Day=day;
	}
	public void setGroupDay(int Number) {
		this.GroupDay=Number;
	}
	public void setListPhoto(List <Image> photo) {
		this.Photo=photo;
	}
	
}