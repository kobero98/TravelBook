package main.java.travelbook.model;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class TravelEntity implements Entity {

	private double costTravel=0;
	private int id=0;
	private int creatorId=0;
	private int stepNumber=0;
	private int likeNumber=0;
	private String nameTravel=null;
	private String type=null;
	private InputStream background=null; 
	private String description=null;
	private Date startDate=null;
	private Date endDate=null;
	private int share=0;
	private List <StepEntity> step=null;
	private List <CityEntity> cityView=null;
	
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
	public InputStream getImage() {
		return this.background;
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
	public int getShare() {
		return this.share;
	}
	public List <StepEntity> getListStep(){
		return this.step;
	}
	public List<CityEntity> getCityView(){
		return this.cityView;
	}
	
	public void setIdTravel(int id) {
		this.id=id;
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
	public void setBackground(InputStream inputStream)
	{
		this.background=inputStream;
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
	public void setShare(int v)
	{
		this.share=v;
	}
	public void setListStep(List <Entity> step) {
		
		if(!step.isEmpty()) {
			this.step = new ArrayList<>();
			for(int i=0;i<step.size();i++) {
				this.step.add((StepEntity) step.get(i));
			}
		}
	}

	public void setCityView(List <CityEntity> list) {
		if(!list.isEmpty())this.cityView=list;
	}

}