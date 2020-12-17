package travelbook;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import travelbook.model.bean.StepBean;

public class TravelEntity {

	private double CostTravel;
	private int Id,CreatorId,StepNumber,likeNumber;
	private String NameTravel,Type,PathBackground;
	private Date StartDate,EndDate;
	private List <StepBean> Step;
	
	public TravelEntity() {}
	public TravelEntity(int idcreator) {
		this.CreatorId=idcreator;
	}
	public TravelEntity(int id,int idcreator) {
		this.Id=id;
		this.CreatorId=idcreator;
	}
	

	public int getIdTravel() {
		return this.Id;
	}
	public int getCreatorId() {
		return this.CreatorId;
	}
	public int getStepNumber() {
		return this.StepNumber;
	}
	public int getLikeNumber()
	{
		return likeNumber;
	}
	public double getCostTravel()
	{
		return this.CostTravel;
	}
	public String getNameTravel() {
		return this.NameTravel;
	}
	public String getTypeTravel() {
		return this.Type;
	}
	public String getPathImage() {
		return this.PathBackground;
	}
	public Date getStartDate() {
		return this.StartDate;
	}
	public Date getEndDate() {
		return this.EndDate;
	}
	public List <StepBean> getListStep(){
		return this.Step;
	}

	
	//private void setIdTravel(int Id) {
	//	this.Id=Id;
	//}
	public void setCreatorTravel(int id) {
		this.CreatorId=id;
	}
	public void setStepNumber(int Number) {
		this.StepNumber=Number;
	}
	public void setLikeNumber(int Number) {
		this.likeNumber=Number;
	}
	public void setNameTravel(String Name) {
		this.NameTravel=Name;
	}
	public void setType(String Type) {
		this.Type=Type;
	}
	public void setPathBackground(String Path)
	{
		this.PathBackground=Path;
	}
	public void setCostTravel(double Cost)
	{
		this.CostTravel=Cost;
	}
	public void setStartTravelDate(Date start)
	{
		this.StartDate=start;
	}
	public void setEndTravelDate(Date End)
	{
		this.EndDate=End;
	}
	public void setListStep(List <StepBean> step) {
		this.Step=step;
	}

}
