package main.java.travelbook.model.bean;

import java.time.Instant;

import main.java.travelbook.model.MessageEntity;

public class MessageBean {

	
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private int type;
	private String text;
	private Instant dataTime;
	private boolean read;
	
	public MessageBean(int iddestinatario,int idmittente) {
		this.idDestinatario=iddestinatario;
		this.idMittente=idmittente;
	}
	public MessageBean(int idmessaggio,int iddestinatario,int idmittente) {
		this.idDestinatario=iddestinatario;
		this.idMittente=idmittente;
		this.idMessaggio=idmessaggio;
	}
	public MessageBean(MessageEntity mex) {
		this.idDestinatario=mex.getIdDestinatario();
		this.idMessaggio=mex.getIdMessaggio();
		this.idMittente=mex.getIdMittente();
		this.text=mex.getText();
		this.dataTime=mex.getTime();
		this.read = mex.getRead();
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
	public int getType(){
		return this.type;
	}
	public String getText(){
		return this.text;
	}
	public Instant getTime() {
		return this.dataTime;
	}
	public boolean getRead() {
		return this.read;
	}
	public void setRead(boolean b) {
		this.read = b;
	}
	public void setText(String testo) {
		this.text=testo;
	}
	public void setTime(Instant time) {
		this.dataTime=time;
	}
	public void setType(int tipo) {
		this.type=tipo;
	}

}
