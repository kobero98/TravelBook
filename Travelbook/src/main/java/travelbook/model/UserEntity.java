package main.java.travelbook.model;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import main.java.travelbook.model.bean.MessageBean;
import main.java.travelbook.model.bean.RegistrationBean;



public class UserEntity extends OtherUserEntity implements Entity{
	private String username=null;
	private String password=null;
	private String email=null;
	private String nation;
	
	public UserEntity(RegistrationBean user) {
		super.name=user.getName();
		super.surname=user.getSurname();
		this.email=user.getEmail();
		this.username=user.getUsername();
		this.password=user.getPassword();
		super.birthDate=user.getBirtdate();
		super.gender=user.getGender();
	}
	public UserEntity(int id) {
		super(id);
	}
	public UserEntity() {
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public void setPhoto(File photo)
	{
		if(photo!=null)
			try {
				super.photo=new FileInputStream(photo);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		else super.photo=null;
	}
	public void setNation(String nation) {
		this.nation=nation;
	}
	public String getUsername()
	{
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public String	getNation() {
		return this.nation;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
}

