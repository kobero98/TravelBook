package main.java.travelbook.model;

import java.util.Date;

public class MessageEntity {
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private String type;
	private String text;
	private Date dataTime;
	private boolean soloNuovi;
	public boolean getSoloNuovi() {
		return this.soloNuovi;
	}
	public void setSoloNuovi(boolean val) {
		this.soloNuovi=val;
	}
	public int getIdMessaggio()
	{
		return this.idMessaggio;
	}
	public int getIdDestinatario(){
		return this.idDestinatario;
	}
	public int getIdMittente(){
		return this.idMittente;
	}
	public String getType(){
		return this.type;
	}
	public String getText(){
		return this.text;
	}
	public Date getTime() {
		return this.dataTime;
	}

	public void setText(String testo) {
		this.text=testo;
	}
	public void setTime(Date time) {
		this.dataTime=time;
	}
	public void setType(String tipo) {
		this.type=tipo;
	}
	public MessageEntity(int mit,int dest) {
		this.idMittente=mit;
		this.idDestinatario=dest;
	}
	
}
