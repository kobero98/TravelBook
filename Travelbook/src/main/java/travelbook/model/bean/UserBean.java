package main.java.travelbook.model.bean;
import java.util.List;
import javafx.scene.image.Image;
import main.java.travelbook.model.UserEntity;

public class UserBean{
	
	private String name="ciao";
	private String surname; 
	private String description;
	private String gender;
	private String nation;
	private String birthdate;
	private Image urlPhoto;
	private int  nFollower;
	private int nFollowing;
	private int nTrip;
	private List <TravelBean> travel;
	private List <MessageBean> message;
	

	public UserBean() {}
	public UserBean(UserEntity user) {
		this.name=user.getName();
		this.surname=user.getSurname();
		this.description=user.getDescription();
		this.gender=user.getGender();
		if(user.getUrlPhoto()!=null) this.urlPhoto= new Image(user.getUrlPhoto());
		this.nFollower=user.getNFollower();
		this.nFollowing=user.getNFollowing();
		this.nTrip=user.getNTrip();
	}
	

	public void setBirthDate(String birthdate) {
		this.birthdate=birthdate;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public void setUrlPhoto(Image photo)
	{
		this.urlPhoto=photo;
	}
	public void setSex(String sex)
	{
		this.gender=sex;
	}
	public void setFollower(int nFollower)
	{
		this.nFollower=nFollower;
	}
	public void setFollowing(int nFollowing)
	{
		this.nFollowing=nFollowing;
	}
	public void setNTravel(int ntrip)
	{
		this.nTrip=ntrip;
	}
	public void setNation(String nation) {
		this.nation=nation;
	}
	public void setTravel(List <TravelBean> t)
	{
		this.travel= t;
	}
	public void setMessagge(List <MessageBean> m) {
		this.message=m;
	}
	
	
	public String getName()
	{
		return this.name;
	}
	public String getSurname()
	{
		return this.surname;
	}
	public String getDescription()
	{
		return this.description;
	}
	public Image getUrlPhoto()
	{
		return this.urlPhoto;
	}
	public String getSex()
	{
		return this.gender;
	}
	public String getNation() {
		return this.nation;
	}
	public int getNFollower()
	{
		return this.nFollower;
	}
	public int getNFollowing()
	{
		return this.nFollowing;
	}
	public String getBirthDate()
	{
		return this.birthdate;
	}
	public int getNTrip()
	{
		return this.nTrip;
	}
	public List <TravelBean>  getTravel() {
		return this.travel;
	}
	public List <MessageBean> getMessage(){
		return this.message;
	}

}