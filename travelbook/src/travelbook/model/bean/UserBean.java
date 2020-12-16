package travelbook.model.bean;

import java.util.List;

import javafx.scene.image.Image;
import travelbook.UserEntity;

public class UserBean{
	private String Name,Surname,Description,Sex;
	private Image URLphoto;
	private int  NFollower,NFollowing,NTrip;
	private List <TravelBean> Travel;
	private List <MessageBean> message;
	

	public UserBean() {}
	public UserBean(UserEntity user) {
		this.Name=user.getName();
		this.Surname=user.getSurname();
		this.Description=user.getDescription();
		this.Sex=user.getGender();
		this.URLphoto= new Image(user.getUrlPhoto());
		this.NFollower=user.getNFollower();
		this.NFollowing=user.getNFollowing();
		this.NTrip=user.getNTrip();
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
	public void setUrlPhoto(Image photo)
	{
		this.URLphoto=photo;
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
	public Image getUrlPhoto()
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
