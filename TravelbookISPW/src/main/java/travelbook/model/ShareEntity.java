package main.java.travelbook.model;

public class ShareEntity implements Entity {
	private int whoShare;
	private int whoReceive;
	private int travelShared;
	private int creator;
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
