package main.java.travelbook.model;

import java.util.Date;
import java.time.Instant;
public class MessageEntity implements Entity {
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private String type;
	private String text;
	private boolean read;
	private Instant dataTime;
	private boolean soloNuovi;
	private Instant lastTimeStamp;
	public Instant getLastTimeStamp() {
		return lastTimeStamp;
	}
	public void setLastTimeStamp(Instant lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}
	public void setRead(boolean r) {
		this.read=r;
	}
	public boolean getSoloNuovi() {
		return this.soloNuovi;
	}
	public void setSoloNuovi(boolean val) {
		this.soloNuovi=val;
	}
	public boolean getRead() {
		return this.read;
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
