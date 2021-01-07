package main.java.travelbook.model.bean;

public class PhotoStepBean {
	
	private int idPhoto,StepNumber,IdTravel,IDCreator;
	private String Path;
	public PhotoStepBean(){}
	public PhotoStepBean(int idphoto,int stepnumber,int idtravel,int idcreator)
	{
		this.IDCreator=idcreator;
		this.IdTravel=idtravel;
		this.idPhoto=idphoto;
		this.StepNumber=stepnumber;
	}
	

	public int getIDPhoto() {
		return this.idPhoto;
	}
	public int getStepNumber() {
		return this.StepNumber;
	}
	public int getIdTravel() {
		return this.IdTravel;
	}
	public int getIdCreator() {
		return this.IDCreator;
	}
	public String getPath()
	{
		return this.Path;
	}
	

	public void setPath(String path)
	{
		this.Path=path;
	}
	
}
