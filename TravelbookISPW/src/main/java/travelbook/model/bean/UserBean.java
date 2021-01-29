package main.java.travelbook.model.bean;

import java.io.InputStream;
import java.util.List;
import javafx.scene.image.Image;
import main.java.travelbook.model.OtherUserEntity;

public class UserBean implements Bean{
	
	private int id;
	private String name="ciao";
	private String surname; 
	private String description;
	private String gender;
	private String nation;
	private String birthdate;
	private Image photo=null;
	private int  nFollower;
	private int nFollowing;
	private int nTrip;
	private int nPlace;
	private List <Integer> travel = null;
	private List<Integer> follower = null;
	private List<Integer> following = null;
	private List<Integer> fav = null;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBean() {}
	
	public UserBean(int id) {
		this.id=id;
	}
	
	public UserBean(OtherUserEntity user) {
		this.id=user.getId();
		this.name=user.getName();
		this.surname=user.getSurname();
		this.description=user.getDescription();
		this.gender=user.getGender();
		if(user.getPhoto()!=null)		this.photo=new Image(user.getPhoto());
		this.nFollower=user.getNFollower();
		this.nFollowing=user.getNFollowing();
		this.nTrip=user.getNTrip();
		this.travel = user.getTravel();
		this.nPlace = user.getnPlace();
		this.follower =user.getListFollower();
		this.following = user.getListFollowing();
		this.fav = user.getFavoriteList();
	}
	
	public int getId() {
		return this.id;
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
	public void setPhoto(Image photo)
	{
		this.photo=photo;
	}
	public void setPhoto(InputStream photo)
	{
		if(photo != null) this.photo=new Image(photo);
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
	public void setTravel(List <Integer> t)
	{
		this.travel= t;
	}
	public void setFollowing(List<Integer> f) {
		this.following=f;
	}
	public void setFollower(List<Integer> f) {
		this.follower=f;
	}
	public void setFav(List<Integer> f) {
		this.fav=f;
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
	public Image getPhoto()
	{
		return this.photo;
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
	public List <Integer>  getTravel() {
		return this.travel;
	}
	public List<Integer> getFollower(){
		return this.follower;
	}
	public List<Integer> getFollowing(){
		return this.following;
	}
	public List<Integer> getFav(){
		return this.fav;
	}
	public int getnPlace() {
		return nPlace;
	}
	public void setnPlace(int nPlace) {
		this.nPlace = nPlace;
	}
	
	@Override
	public String toString() {
		return this.getName()+ " " +this.getSurname();
	}


}