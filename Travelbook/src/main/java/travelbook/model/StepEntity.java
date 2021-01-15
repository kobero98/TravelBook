package main.java.travelbook.model;

import java.sql.Date;
import java.util.List;
import java.io.File;



public class StepEntity implements Entity {
	private int number;
	private int idTravel;
	private int idCreator;
	private int groupDay; 
	private int numberOfDay;
	private String descriptionStep;
	private String place;
	private Date day;
	private List <File> photo;
	private String precisionInformation;
	public  StepEntity(){}
	public StepEntity(int idcreator,int number) {
		this.idCreator=idcreator;
		this.number=number;
	}
	public  StepEntity(int number,int idtravel,int idcreator){
		this.number=number;
		this.idCreator=idcreator;
		this.idTravel=idtravel;
	}
	
	public String getPrecisionInformation() {
		return precisionInformation;
	}
	public void setPrecisionInformation(String precisionInformation) {
		this.precisionInformation = precisionInformation;
	}
	public int getNumber()
	{
		return this.number;
	}
	public int getIDTravel() {
		return this.idTravel;
	}
	public int getIDCreator() {
		return this.idCreator;
	}
	public int getGroupDay() {
		return this.groupDay;
	}
	public String getDescriptionStep() {
		return this.descriptionStep;
	}
	public String getPlace() {
		return this.place;
	}
	public Date getDay()
	{
		return this.day;
	}
	public int getNumberOfDay() {
		return this.numberOfDay;
	}
	public List<File> getListPhoto(){
		return this.photo;
	}
	
	public void setDescriptionStep(String description)
	{
		this.descriptionStep=description;
	}
	public void setPlace(String location)
	{
		this.place=location;
	}
	public void setDay(Date day)
	{
		this.day=day;
	}
	public void setGroupDay(int number) {
		this.groupDay=number;
	}
	public void setListPhoto(List <File> photoFile) {
		this.photo=photoFile;
	}
	public void setNumberOfDay(int number) {
		this.numberOfDay=number;
	}
	public void setTripId(int id) {
		this.idTravel=id;
	}
	public void setUserId(int id) {
		this.idCreator=id;
	}
}