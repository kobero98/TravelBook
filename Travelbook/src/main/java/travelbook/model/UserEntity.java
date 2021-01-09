package main.java.travelbook.model;

import java.util.List;
import java.sql.Date;
import main.java.travelbook.model.bean.MessageBean;



public class UserEntity{
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String description;
	private String gender;
	private String urlPhoto;
	private Date birthDate;
	private int  id;
	private int nFollower;
	private int nFollowing;
	private int nTrip;
	private String nation;
	private List <TravelEntity> travel;
	private List <MessageBean> message;
	
	public UserEntity(int codice)
	{
		this.id=codice;
	}
	public UserEntity() {}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setEmail(String mail) {
		this.email=mail;
	}
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public void setUrlPhoto(String url)
	{
		this.urlPhoto=url;
	}
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	public void setFollower(int nfollower)
	{
		this.nFollower=nfollower;
	}
	public void setFollowing(int nfollowing)
	{
		this.nFollowing=nfollowing;
	}
	public void setNTravel(int ntrip)
	{
		this.nTrip=ntrip;
	}
	public void setBirthDate(Date birthdate) {
		this.birthDate=birthdate;
	}
	public void setNation(String nation) {
		this.nation=nation;
	}
	
	public void setTravel(List <TravelEntity> t)
	{
		this.travel= t;
	}
	public void setMessagge(List <MessageBean> m) {
		this.message=m;
	}
	
	public int getId()
	{
		return this.id;
	}
	public String getUsername()
	{
		return this.username;
	}
	public String getPassword() {
		return this.password;
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
	public String getUrlPhoto()
	{
		return this.urlPhoto;
	}
	public String getGender()
	{
		return this.gender;
	}
	public int getNFollower()
	{
		return this.nFollower;
	}
	public int getNFollowing()
	{
		return this.nFollowing;
	}
	public int getNTrip()
	{
		return this.nTrip;
	}
	public Date getBirthDate() {
		return this.birthDate;
	}
	public String	getNation() {
		return this.nation;
	}
	public String getEmail() {
		return this.email;
	}
	public List <TravelEntity>  getTravel() {
		return this.travel;
	}
	public List <MessageBean> getMessage(){
		return this.message;
	}


}