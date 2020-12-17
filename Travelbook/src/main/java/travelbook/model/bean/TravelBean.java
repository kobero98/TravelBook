package main.java.travelbook.model.bean;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.java.travelbook.model.TravelEntity;
public class TravelBean {

	private double CostTravel;
	private int StepNumber,likeNumber;
	private String NameTravel;
	Image PathBackground;
	List<String> Type;
	private String StartDate,EndDate;
	private List <StepBean> Step;
	
	public TravelBean() {
	}
	public TravelBean(TravelEntity travel)
	{
		this.CostTravel=travel.getCostTravel();
		this.StepNumber=travel.getStepNumber();
		this.likeNumber=travel.getLikeNumber();
		this.NameTravel=travel.getNameTravel();
		this.PathBackground=new Image(travel.getPathImage());
		
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
	public List<String> getTypeTravel() {
		return this.Type;
	}
	public Image getPathImage() {
		return this.PathBackground;
	}
	public String getStartDate() {
		return this.StartDate;
	}
	public String getEndDate() {
		return this.EndDate;
	}
	public List <StepBean> getListStep(){
		return this.Step;
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
	public void setType(List<String> Type) {
		this.Type=Type;
	}
	public void setPathBackground(Image Path)
	{
		this.PathBackground=Path;
	}
	public void setCostTravel(double Cost)
	{
		this.CostTravel=Cost;
	}
	public void setStartTravelDate(String start)
	{
		this.StartDate=start;
	}
	public void setEndTravelDate(String End)
	{
		this.EndDate=End;
	}
	public void setListStep(List <StepBean> step) {
		this.Step=step;
	}

}
