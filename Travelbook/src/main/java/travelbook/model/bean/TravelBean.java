package main.java.travelbook.model.bean;

import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import main.java.travelbook.util.Observable;
import javafx.scene.image.Image;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;
import java.io.File;
public class TravelBean extends Observable {

	private int id;
	private Double costTravel;
	private int idCreator;
	private int stepNumber;
	private int likeNumber;
	private String nameTravel;
	private String descriptionTravel;
	private File pathFile;
	public File getPathFile() {
		return pathFile;
	}
	public void setPathFile(File pathFile) {
		this.pathFile = pathFile;
	}

	private Image pathBackground;
	private boolean share;
	private List<String> type;
	private String startDate;
	private String endDate;
	private List <StepBean> step;
	private int dayNum;
	
	public TravelBean() {}
	public TravelBean(TravelEntity travel)
	{
		this.id = travel.getIdTravel();
		this.idCreator = travel.getCreatorId();
		this.costTravel=travel.getCostTravel();
		this.stepNumber=travel.getStepNumber();
		this.likeNumber=travel.getLikeNumber();
		this.nameTravel=travel.getNameTravel();
		if (travel.getImage()!= null)this.pathBackground=new Image(travel.getImage());
		this.share = (travel.getShare()==1);
		if (travel.getTypeTravel() != null )this.type = stringParser(travel.getTypeTravel());
		this.startDate = travel.getStartDate().toLocalDate().toString();
		this.endDate = travel.getEndDate().toLocalDate().toString();
//		this.step = stepConvert(travel.getListStep());
		this.dayNum = dayCalculator(travel.getStartDate(), travel.getEndDate());
		
	}
	
	private List<String> stringParser(String type) {
		List<String> l = new ArrayList<>();
		int start=1;
		int end=1;
		for(int i=1; i<type.length(); i++) {
			if(type.charAt(i)!='#') {
				end++;
			}
			else {
				l.add(type.substring(start, end));
				start=end+1;
				end++;
			}
			
		}
		return l;
	}
	
	private List<StepBean> stepConvert(List<StepEntity> l){
		List<StepBean> b = new ArrayList<>();
		for(int i=0; i<l.size(); i++) {
			StepBean s= new StepBean(l.get(i));
			b.add(s);
		}
		return b;	
	}
	
	private int dayCalculator(Date start, Date end) {
		int n = 0;
		if(start.toLocalDate().getYear()==end.toLocalDate().getYear()) n=helpCalculator(start.toLocalDate(), end.toLocalDate());
		else {
			n= n+helpCalculator(start.toLocalDate(), LocalDate.of(start.toLocalDate().getYear(), 12, 31));
			for(int i=1; i<end.toLocalDate().getYear()-start.toLocalDate().getYear(); i++) {
				int y=start.toLocalDate().getYear()+i;
				if(y%4 ==0) n=n+366;
				else n=n+365;
			}
			n = n+helpCalculator(LocalDate.of(end.toLocalDate().getYear(), 1, 1), end.toLocalDate())+1;
		}
		return n;
	}
	
	private int helpCalculator(LocalDate start, LocalDate end) {
		int n=0;
		if(start.getMonth()==end.getMonth()) {
			n = end.getDayOfMonth()-start.getDayOfMonth();
		}
		else {
			n=n + start.getMonth().length(start.isLeapYear()) - start.getDayOfMonth();
			for(int i=1; i< end.getMonthValue()-start.getMonthValue();i++) {
				n = n + Month.of(start.getMonthValue()+i).length(start.isLeapYear());
			}
			n = n + end.getDayOfMonth();
		}
		return n;
	}
	
	public int getId() {
		return this.id;
	}
	public int getIdCreator() {
		return this.idCreator;
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
	
	public int getDayNum() {
		return this.dayNum;
	}

}
