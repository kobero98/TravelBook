package main.java.travelbook.model;

import java.util.Date;
import java.time.Instant;
public class MessageEntity {
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private String type;
	private String text;
	private Instant dataTime;
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
	public Instant getTime() {
		return this.dataTime;
	}

	public void setText(String testo) {
		this.text=testo;
	}
	public void setTime(Instant time) {
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
