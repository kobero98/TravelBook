package main.java.travelbook.model.bean;

import main.java.travelbook.model.ShareEntity;

public class ShareBean {
	private int whoShare;
	private int whoReceive;
	private int travelShared;
	private int creator;
	public ShareBean() {
		
	}
	public ShareBean(ShareEntity sh) {
		this.whoShare=sh.getWhoShare();
		this.whoReceive=sh.getWhoReceive();
		this.travelShared=sh.getTravelShared();
		this.creator=sh.getCreator();
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public int getWhoShare() {
		return whoShare;
	}
	public void setWhoShare(int whoShare) {
		this.whoShare = whoShare;
	}
	public int getWhoReceive() {
		return whoReceive;
	}
	public void setWhoReceive(int whoReceive) {
		this.whoReceive = whoReceive;
	}
	public int getTravelShared() {
		return travelShared;
	}
	public void setTravelShared(int travelShared) {
		this.travelShared = travelShared;
	}
}
