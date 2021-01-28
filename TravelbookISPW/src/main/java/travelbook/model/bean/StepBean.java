package main.java.travelbook.model.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import main.java.travelbook.model.StepEntity;
import main.java.travelbook.util.PlaceAdapter;
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
	private PlaceAdapter fullPlace;
	private String precisionInformation;
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
			this.photo = photoConvert(s.getStreamFoto());
			this.imageFile = s.getListPhoto();
		}
		//full place non è sulla entity, non lo posso settare per ora
		this.precisionInformation = s.getPrecisionInformation();
	}
	
	private List<Image> photoConvert(List<InputStream> f){
		List<Image> i = new ArrayList<>();
		for(int j=0; j<f.size(); j++) {
			Image e = new Image(f.get(j));
			i.add(e);
		}
		System.out.println(i.size());
		return i;
	}
	
	public PlaceAdapter getFullPlace() {
		return fullPlace;
	}
	public void setFullPlace(PlaceAdapter fullPlace) {
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