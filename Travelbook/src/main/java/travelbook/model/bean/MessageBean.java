package main.java.travelbook.model.bean;

import main.java.travelbook.model.MessageEntity;

public class MessageBean {

	
	private int idMessaggio;
	private int idDestinatario;
	private int idMittente;
	private int type;
	private String text;
	private String dataTime;
	
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
		this.dataTime=mex.getTime().toString();
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
	public String getTime() {
		return this.dataTime;
	}

	public void setText(String testo) {
		this.text=testo;
	}
	public void setTime(String time) {
		this.dataTime=time;
	}
	public void setType(int tipo) {
		this.type=tipo;
	}

}
