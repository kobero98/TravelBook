package main.java.travelbook.controller;

import main.java.travelbook.model.UserEntity;

public class MyIdentity {

	private static MyIdentity instance=null;
	private UserEntity myEntity=null;
	private MyIdentity() {}
	public static MyIdentity getInstance() {
		if(instance==null) instance=new MyIdentity();
		return  instance;
	}

	public UserEntity getMyEntity() {
		return this.myEntity;
	}
	public void setMyEntity(UserEntity user) {
		this.myEntity=user;
	}
	

}
