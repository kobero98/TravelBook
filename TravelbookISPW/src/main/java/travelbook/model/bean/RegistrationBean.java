package main.java.travelbook.model.bean;

import java.sql.Date;

public class RegistrationBean implements Bean{

	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private Date birthDate;
	private String gender;
	private String nazionalita="Italia";
	
	public void setUsername(String username) {
		this.username=username;
	}
	public void setPassword(String pswd) {
		this.password=pswd;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setSurname(String surname) {
		this.surname=surname;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public void setBirtdate(Date date) {
		this.birthDate=date;
	}
	public void setGender(String gender) {
		this.gender=gender;
	}
	public void setNazionalita(String nazionalita)
	{
		this.nazionalita=nazionalita;
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public String getName() {
		return this.name;
	}
	public String getSurname() {
		return this.surname;
	}
	public String getEmail() {
		return this.email;
	}
	public Date getBirtdate() {
		return this.birthDate;
	}
	public String getGender() {
		return this.gender;
	}	
	@Override
	public String toString() {
		return this.email+" "+this.name+this.password+this.gender+this.username+this.surname+this.birthDate;
	}
	public String getNazionalita() {
		return this.nazionalita;
	}
}
