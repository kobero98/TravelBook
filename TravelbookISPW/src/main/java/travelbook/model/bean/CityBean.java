package main.java.travelbook.model.bean;

public class CityBean implements Bean{
	private String nameCity;
	private String nameNation;

	public String getNameCity()
	{
		return this.nameCity;
	}
	public String getNameNation(){
		return this.nameNation;
	}

	public void setNameCity(String name){
		this.nameCity=name;
	}
	public void setNameNation(String name){
		this.nameNation=name;
	}
}