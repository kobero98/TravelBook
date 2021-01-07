package main.java.travelbook.model;

import java.util.List;
import java.sql.Date;
import main.java.travelbook.model.bean.MessageBean;
import main.java.travelbook.model.bean.TravelBean;



public class UserEntity{
	private String Username,Password;
	private String Name,Surname,Email,Description,Sex,URLphoto;
	private Date BirthDate;
	private int  id,NFollower,NFollowing,NTrip;
	private String Nation;
	private List <TravelEntity> Travel;
	private List <MessageBean> message;
	
	public UserEntity(int Id)
	{
		this.id=Id;
	}
	public UserEntity() {}
	
	public void setUsername(String username)
	{
		this.Username=username;
	}
	public void setPassword(String password) {
		this.Password=password;
	}
	public void setName(String name)
	{
		this.Name=name;
	}
	public void setEmail(String mail) {
		this.Email=mail;
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
	public void setGender(String gender)
	{
		this.Sex=gender;
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
	public void setBirthDate(Date BirthDate) {
		this.BirthDate=BirthDate;
	}
	public void setNation(String nation) {
		this.Nation=nation;
	}
	
	public void setTravel(List <TravelEntity> T)
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
	public String getUsername()
	{
		return this.Username;
	}
	public String getPassword() {
		return this.Password;
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
	public String getGender()
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
	public Date getBirthDate() {
		return this.BirthDate;
	}
	public String	getNation() {
		return this.Nation;
	}
	public String getEmail() {
		return this.Email;
	}
	public List <TravelEntity>  getTravel() {
		return this.Travel;
	}
	public List <MessageBean> getMessage(){
		return this.message;
	}


}