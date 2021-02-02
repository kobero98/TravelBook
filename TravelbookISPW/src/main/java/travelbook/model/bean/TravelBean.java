package main.java.travelbook.model.bean;

import java.util.ArrayList;
import java.util.List;
import main.java.travelbook.util.DateUtil;
import main.java.travelbook.util.Observable;
import javafx.scene.image.Image;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.model.TravelEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TravelBean extends Observable implements Bean{

	private int id;
	private Double costTravel;
	private int idCreator;
	private int stepNumber;
	private int likeNumber;
	private String nameTravel;
	private String descriptionTravel;
	private File pathFile;
	private byte[] array;
	private InputStream imageStream;
	public File getPathFile() {
		return pathFile;
	}
	public void setPathFile(File pathFile) {
		this.pathFile = pathFile;
	}
	public void setArray(byte[] array) {
		this.array=array;
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
		this.descriptionTravel=travel.getDescriptionTravel();
		if (travel.getImage()!= null)this.imageStream=travel.getImage();
		this.share = (travel.getShare()==1);
		if (travel.getTypeTravel() != null )this.type = stringParser(travel.getTypeTravel());
		this.startDate = travel.getStartDate().toLocalDate().toString();
		this.endDate = travel.getEndDate().toLocalDate().toString();
		if (travel.getListStep()!=null)this.step = stepConvert(travel.getListStep());
		this.dayNum = (int)new DateUtil().numOfDaysBetween(travel.getStartDate().toLocalDate(), travel.getEndDate().toLocalDate()) +1;
		
	}
	public byte[] getArray() {
		if(this.imageStream==null) {
			if(this.array==null)
				return new byte[0];
			return array;
		}
			
		try {
			ByteArrayOutputStream buffer= new ByteArrayOutputStream();
			int nRead;
			byte[] targetArray=new byte[16384];
			while((nRead=imageStream.read(targetArray,0,targetArray.length))!=-1) {
				buffer.write(targetArray,0,nRead);
			}
			this.array=buffer.toByteArray();
			}catch(IOException e) {
				e.printStackTrace();
			}
		return array;
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
		if(this.imageStream==null)
			return null;
		if(this.pathBackground==null)
			this.pathBackground=new Image(this.imageStream);
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
