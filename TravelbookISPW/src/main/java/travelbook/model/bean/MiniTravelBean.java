package main.java.travelbook.model.bean;

import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import main.java.travelbook.model.TravelEntity;

public class MiniTravelBean implements Bean{
	private Integer id;
	private String nameTravel;
	private String descriptionTravel;
	private Image pathBackground;
	private InputStream picStream;
	private byte[] array;
	public byte[] getArray() {
		if(this.picStream==null)
			return new byte[0];		
		try {
			ByteArrayOutputStream buffer= new ByteArrayOutputStream();
			int nRead;
			byte[] targetArray=new byte[16384];
			while((nRead=picStream.read(targetArray,0,targetArray.length))!=-1) {
				buffer.write(targetArray,0,nRead);
			}
			this.array=buffer.toByteArray();
			}catch(IOException e) {
				e.printStackTrace();
			}
		return array;
	}

	public void setArray(byte[] array) {
		this.array = array;
	}

	public InputStream getImageStream() {
		return picStream;
	}

	public void setImageStream(InputStream imageStream) {
		this.picStream = imageStream;
	}

	public MiniTravelBean() {}
	
	public MiniTravelBean(TravelEntity travel) //creare modo per prendere dal database sta mini travel bean
	{
		this.id = travel.getIdTravel();
		this.nameTravel=travel.getNameTravel();
		if (travel.getImage() != null) {
			this.picStream=travel.getImage();
		}
		this.descriptionTravel=travel.getDescriptionTravel();
		
	}
	
	public String getDescriptionTravel() {
		return this.descriptionTravel;
	}
	
	public String getNameTravel() {
		return this.nameTravel;
	}
	public Integer getId() {
		return this.id;
	}
	public Image getPathImage() {
		if(this.picStream==null)
			return null;
		if(this.pathBackground==null)
			this.pathBackground=new Image(this.picStream);
		return this.pathBackground;
	}
}