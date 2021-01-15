package main.java.travelbook.model.bean;

import java.util.List;
import main.java.travelbook.util.Observable;
import javafx.scene.image.Image;
import main.java.travelbook.model.TravelEntity;
public class TravelBean extends Observable {

	private Double costTravel;
	private int stepNumber;
	private int likeNumber;
	private String nameTravel;
	private String descriptionTravel;
	
	private Image pathBackground;
	private boolean share;
	private List<String> type;
	private String startDate;
	private String endDate;
	private List <StepBean> step;
	
	public TravelBean() {}
	public TravelBean(TravelEntity travel)
	{
		this.costTravel=travel.getCostTravel();
		this.stepNumber=travel.getStepNumber();
		this.likeNumber=travel.getLikeNumber();
		this.nameTravel=travel.getNameTravel();
		this.pathBackground=new Image(travel.getPathImage());
		
	}
	public int getStepNumber() {
		return this.stepNumber;
	}
	public String getDescriptionTravel() {
		return this.descriptionTravel;
	}
	public int getLikeNumber()
	{
		return likeNumber;
	}
	public Double getCostTravel()
	{
		return this.costTravel;
	}
	public String getNameTravel() {
		return this.nameTravel;
	}
	public List<String> getTypeTravel() {
		return this.type;
	}
	public Image getPathImage() {
		return this.pathBackground;
	}
	public String getStartDate() {
		return this.startDate;
	}
	public String getEndDate() {
		return this.endDate;
	}
	public List <StepBean> getListStep(){
		return this.step;
	}
	public boolean getShare() {
		return this.share;
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
	public void setDescriptionTravel(String description) {
		this.descriptionTravel=description;
	}
	public void setType(List<String> t) {
		this.type=t;
	}
	public void setPathBackground(Image path)
	{
		this.pathBackground=path;
	}
	public void setCostTravel(Double cost)
	{
		this.costTravel=cost;
	}
	public void setStartTravelDate(String start)
	{
		this.startDate=start;
	}
	public void setEndTravelDate(String end)
	{
		this.endDate=end;
	}
	public void setListStep(List <StepBean> step) {
		this.step=step;
	}
	public void setShare(boolean v) {
		this.share=v;
	}

}
