package main.java.travelbook.model.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.util.Place;

public class StepBean implements Bean{
	private List<ByteArrayOutputStream> bytes=new ArrayList<>();
	public List<ByteArrayOutputStream> getBytes() {
		return bytes;
	}
	public void setBytes(List<ByteArrayOutputStream> bytes) {
		this.bytes = bytes;
	}
	private int number;
	private int iDTravel;
	private int iDCreator;
	private int groupDay;
	private int numberInDay;
	private String descriptionStep;
	private String place;
	private List <Image> photo;            //abbiamo sia foto come immagini che foto come file. ne serve solo una
	private Place fullPlace;
	private String precisionInformation;
	private List<InputStream> isL;
	private List<byte[]> array=new ArrayList<>();
	public List<InputStream> getIs() {
		return isL;
	}
	public void setIs(List<InputStream> is) {
		this.isL = is;
	}
	public List<byte[]> getArray() {
		
		try {
			for(InputStream is:isL) {
			ByteArrayOutputStream buffer= new ByteArrayOutputStream();
			int nRead;
			byte[] targetArray=new byte[16384];
			while((nRead=is.read(targetArray,0,targetArray.length))!=-1) {
				buffer.write(targetArray,0,nRead);
			}
			this.array.add(buffer.toByteArray());
			}
			}catch(IOException e) {
				return new ArrayList<>();
			}
		
		return this.array;
	}
	public void setArray(List<byte[]> array) {
		this.array = array;
	}
	private List<File> imageFile=new ArrayList<>();
	public List<File> getImageFile() {
		return imageFile;
	}
	public void setImageFile(List<File> imageFile) {
		this.imageFile = imageFile;
	}
	public String getPrecisionInformation() {
		return precisionInformation;
	}
	public void setPrecisionInformation(String precisionInformation) {
		this.precisionInformation = precisionInformation;
	}
	public  StepBean(){}
	public  StepBean(int number,int idtravel,int idcreator){
		this.number=number;
		this.iDCreator=idcreator;
		this.iDTravel=idtravel;
	}
	public StepBean(StepEntity s) {
		this.number = s.getNumber();
		this.iDTravel = s.getIDTravel();
		this.iDCreator = s.getIDCreator();
		this.groupDay = s.getGroupDay();
		this.numberInDay = s.getNumberOfDay();
		this.descriptionStep = s.getDescriptionStep();
		this.place = s.getPlace();
		if (s.getStreamFoto() != null) {
			this.isL=s.getStreamFoto();
			
			this.imageFile = s.getListPhoto();
		}
		
		//full place non è sulla entity, non lo posso settare per ora
		//non devi farlo
		this.precisionInformation = s.getPrecisionInformation();
	}
	
	private List<Image> photoConvert(List<InputStream> f){
		List<Image> i = new ArrayList<>();
		for(int j=0; j<f.size(); j++) {
			Image e = new Image(f.get(j));
			i.add(e);
		}
		return i;
	}
	
	public Place getFullPlace() {
		return fullPlace;
	}
	public void setFullPlace(Place fullPlace) {
		this.fullPlace = fullPlace;
	}
	public int getNumberInDay() {
		return numberInDay;
	}
	public void setNumberInDay(int numberInDay) {
		this.numberInDay = numberInDay;
	}
	public int getNumber()
	{
		return this.number;
	}
	public int getIDTravel() {
		return this.iDTravel;
	}
	public int getIDCreator() {
		return this.iDCreator;
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
	public List<Image> getListPhoto(){
		if(this.photo==null) {
			if(this.isL!=null)
				this.photo = photoConvert(this.isL);
			else
				this.photo=new ArrayList<>();
		}
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
	public void setGroupDay(int number) {
		this.groupDay=number;
	}
	public void setListPhoto(List <String> path) {
		for(String string: path) {
			this.imageFile.add(new File(string));
		}
	}
}