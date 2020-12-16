package travelbook.model.bean;

import java.util.List;

public class UserBean{
	private String Name,Surname,Description,Sex,URLphoto;
	private int  id,NFollower,NFollowing,NTrip;
	private List <TravelBean> Travel;
	private List <MessageBean> message;
	

	public UserBean(int Id)
	{
		this.id=Id;
	}
	public UserBean() {}
	
	private void setId(int n){
		this.id=n;
	}
	public void setName(String name)
	{
		this.Name=name;
	}
	public void setSurname(String surname)
	{
		this.Surname=surname;
	}
	public void setDescription(String Description)
	{
		this.Description=Description;
	}
	public void setUrlPhoto(String url)
	{
		this.URLphoto=url;
	}
	public void setSex(String sex)
	{
		this.Sex=sex;
	}
	public void setFollower(int NFollower)
	{
		this.NFollower=NFollower;
	}
	public void setFollowing(int NFollowing)
	{
		this.NFollowing=NFollowing;
	}
	public void setNTravel(int Ntrip)
	{
		this.NTrip=Ntrip;
	}
	public void setTravel(List <TravelBean> T)
	{
		this.Travel= T;
	}
	public void setMessagge(List <MessageBean> M) {
		this.message=M;
	}
	
	public int getId()
	{
		return this.id;
	}
	public String getName()
	{
		return this.Name;
	}
	public String getSurname()
	{
		return this.Surname;
	}
	public String getDescription()
	{
		return this.Description;
	}
	public String getUrlPhoto()
	{
		return this.URLphoto;
	}
	public String getSex()
	{
		return this.Sex;
	}
	public int getNFollower()
	{
		return this.NFollower;
	}
	public int getNFollowing()
	{
		return this.NFollowing;
	}
	public int getNTrip()
	{
		return this.NTrip;
	}
	public List <TravelBean>  getTravel() {
		return this.Travel;
	}
	public List <MessageBean> getMessage(){
		return this.message;
	}
}
