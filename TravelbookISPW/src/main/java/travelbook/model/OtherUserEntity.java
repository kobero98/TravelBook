package main.java.travelbook.model;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import main.java.travelbook.model.bean.MessageBean;

public class OtherUserEntity implements Entity{
	protected String name=null;
	protected String surname=null;
	private String description=null;
	protected String gender=null;
	protected InputStream photo=null;
	protected Date birthDate=null;
	protected int  id=0;
	private int nFollower;
	private int nFollowing;
	private int nTrip;
	private int nPlace=0;
	private List <Integer> travel = null;
	private List <Integer> favourite = null;
	private List <Integer> follower = new ArrayList<>();
	private List <Integer> following = new ArrayList<>();
	private List <MessageBean> message = null;
	
	public OtherUserEntity(int codice)
	{
		this.id=codice;
	}
	public OtherUserEntity() {}
	

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
	public void setPhoto(InputStream photo)
	{
		this.photo=photo;
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
	
	public void setTravel(List <Integer> t)
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
	public InputStream getPhoto()
	{
		return this.photo;
	}
	public String getGender()
	{
		return this.gender;
	}
	public int getNFollower()
	{
		if(this.follower!=null)
			return this.follower.size();
		return 0;
	}
	public int getNFollowing()
	{
		if(this.following!=null)
			return this.following.size();
		return 0;
	}
	public int getNTrip()
	{
		return this.nTrip;
	}
	public Date getBirthDate() {
		return this.birthDate;
	}
	public List <Integer>  getTravel() {
		return this.travel;
	}
	public List <MessageBean> getMessage(){
		return this.message;
	}
	public List<Integer> getFavoriteList()
	{
		return this.favourite;
	}
	public void setFavoriteList(List<Integer> list)
	{
		if(!list.isEmpty())this.favourite=list;
	}
	public List<Integer> getListFollower() {
		return follower;
	}
	public void setListFollower(List<Integer> follower) {
		if(!follower.isEmpty())this.follower = follower;
	}
	
	public List<Integer> getListFollowing() {
		return following;
	}
	public void setListFollowing(List<Integer> following) {
		if(!following.isEmpty())this.following = following;
	}
	public int getnPlace() {
		return nPlace;
	}
	public void setnPlace(int nPlace) {
		this.nPlace = nPlace;
	}
}
