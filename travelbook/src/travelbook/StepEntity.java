package travelbook;

import java.sql.Date;
import java.util.List;


public class StepEntity {
	private int Number,IDTravel,IDCreator,GroupDay;
	private String DescriptionStep,Place;
	private Date Day;
	private List <String> Photo;
	
	public  StepEntity(){}
	public  StepEntity(int Number,int IDTravel,int IDCreator){
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
	public Date getDay()
	{
		return this.Day;
	}
	public List<String> getListPhoto(){
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
	public void setDay(Date day)
	{
		this.Day=day;
	}
	public void setGroupDay(int Number) {
		this.GroupDay=Number;
	}
	public void setListPhoto(List <String> photo) {
		this.Photo=photo;
	}
	

}
