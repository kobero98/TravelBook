package main.java.travelbook.model.bean;

public class PhotoStepBean {
	
	private int idPhoto;
	private int stepNumber;
	private int idTravel;
	private int idCreator;
	private String path;
	public PhotoStepBean(){}
	public PhotoStepBean(int idphoto,int stepnumber,int idtravel,int idcreator)
	{
		this.idCreator=idcreator;
		this.idTravel=idtravel;
		this.idPhoto=idphoto;
		this.stepNumber=stepnumber;
	}
	

	public int getIDPhoto() {
		return this.idPhoto;
	}
	public int getStepNumber() {
		return this.stepNumber;
	}
	public int getIdTravel() {
		return this.idTravel;
	}
	public int getIdCreator() {
		return this.idCreator;
	}
	public String getPath()
	{
		return this.path;
	}
	

	public void setPath(String path)
	{
		this.path=path;
	}
	
}
