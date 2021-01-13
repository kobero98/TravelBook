package main.java.travelbook.model;

import java.util.Date;

public class MessageEntity {
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private int type;
	private String text;
	private Date dataTime;
	
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
	public int getType(){
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
	public void setType(int tipo) {
		this.type=tipo;
	}
}
