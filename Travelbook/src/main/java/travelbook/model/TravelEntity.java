package main.java.travelbook.model;

import java.sql.Date;
import java.util.List;


public class TravelEntity implements Entity {

	private double costTravel;
	private int id;
	private int creatorId;
	private int stepNumber;
	private int likeNumber;
	private String nameTravel;
	private String type;
	private String pathBackground; 
	private String description;
	private Date startDate;
	private Date endDate;
	private Boolean share;
	private List <StepEntity> step;
	private List <String> cityView;
	
	
	public TravelEntity() {}
	public TravelEntity(int idcreator) {
		this.creatorId=idcreator;
	}
	public TravelEntity(int id,int idcreator) {
		this.id=id;
		this.creatorId=idcreator;
	}
	

	public int getIdTravel() {
		return this.id;
	}
	public int getCreatorId() {
		return this.creatorId;
	}
	public int getStepNumber() {
		return this.stepNumber;
	}
	public int getLikeNumber()
	{
		return likeNumber;
	}
	public double getCostTravel()
	{
		return this.costTravel;
	}
	public String getNameTravel() {
		return this.nameTravel;
	}
	public String getTypeTravel() {
		return this.type;
	}
	public String getPathImage() {
		return this.pathBackground;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public Date getEndDate() {
		return this.endDate;
	}
	public String getDescriptionTravel() {
		return this.description;
	}
	public Boolean getShare() {
		return this.share;
	}
	public List <StepEntity> getListStep(){
		return this.step;
	}
	public List<String> getCityView(){
		return this.cityView;
	}
	
	public void setCreatorTravel(int id) {
		this.creatorId=id;
	}
	public void setStepNumber(int number) {
		this.stepNumber=number;
	}
	public void setLikeNumber(int number) {
		this.likeNumber=number;
	}
	public void setNameTravel(String name) {
		this.nameTravel=name;
	}
	public void setType(String type) {
		this.type=type;
	}
	public void setPathBackground(String path)
	{
		this.pathBackground=path;
	}
	public void setCostTravel(double cost)
	{
		this.costTravel=cost;
	}
	public void setStartTravelDate(Date start)
	{
		this.startDate=start;
	}
	public void setEndTravelDate(Date end)
	{
		this.endDate=end;
	}
	public void setDescriptionTravel(String description)
	{
		this.description=description;
	}
	public void setShare(Boolean v)
	{
		this.share=v;
	}
	public void setListStep(List <StepEntity> step) {
		this.step=step;
	}
	public void setCityView(List <String> list) {
		this.cityView=list;
	}

}