package main.java.travelbook.model.bean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;
import main.java.travelbook.model.UserEntity;

public class UserBean{
	
	private int id;
	private String name="ciao";
	private String surname; 
	private String description;
	private String gender;
	private String nation;
	private String birthdate;
	private File photo=new File(".test");
	private int  nFollower;
	private int nFollowing;
	private int nTrip;
	private List <Integer> travel = null;
	private List <Integer> message = null;
	private List<Integer> follower = null;
	private List<Integer> following = null;
	private List<Integer> fav = null;	

	
	private void convertInputStramToFile(InputStream s)throws IOException{
		
				OutputStream outStream = null;
	    	   try {
	    		    outStream = new FileOutputStream(this.photo);
	    		    byte[] buffer = new byte[8 * 1024];
	    		    int bytesRead;
	    		    while ((bytesRead = s.read(buffer)) != -1) {
	    		    	outStream.write(buffer, 0, bytesRead);
	    		    }
	    		    
	    	    }finally {
	    	    	if(outStream!= null) outStream.close();
	    	    }
	
			
	}
	
	
	public UserBean() {}
	
	
	
	public UserBean(UserEntity user) {
		this.id=user.getId();
		this.name=user.getName();
		this.surname=user.getSurname();
		this.description=user.getDescription();
		this.gender=user.getGender();
		if(user.getPhoto()!=null)
			try {
				convertInputStramToFile(user.getPhoto());
				System.out.print("ciao2\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		this.nFollower=user.getNFollower();
		this.nFollowing=user.getNFollowing();
		this.nTrip=user.getNTrip();
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
	public void setPhoto(File photo)
	{
		this.photo=photo;
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
	public void setMessagge(List <Integer> m) {
		this.message=m;
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
	public File getPhoto()
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
	public List <Integer> getMessage(){
		return this.message;
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


}