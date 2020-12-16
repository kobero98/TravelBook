package travelbook.model.bean;

public class MessageBean {

	
	private int IdMessaggio,IdDestinatario,IdMittente,Type;
	private String Text,DataTime;
	public MessageBean(int iddestinatario,int idmittente) {
		this.IdDestinatario=iddestinatario;
		this.IdMittente=idmittente;
	}
	public MessageBean(int idmessaggio,int iddestinatario,int idmittente) {
		this.IdDestinatario=iddestinatario;
		this.IdMittente=idmittente;
		this.IdMessaggio=idmessaggio;
	}
	
	public int getIdMessaggio()
	{
		return this.IdMessaggio;
	}
	public int getIdDestinatario(){
		return this.IdDestinatario;
	}
	public int getIdMittente(){
		return this.IdMittente;
	}
	public int getType(){
		return this.Type;
	}
	public String getText(){
		return this.Text;
	}
	public String getTime() {
		return this.DataTime;
	}

	public void setText(String testo) {
		this.Text=testo;
	}
	public void setTime(String Time) {
		this.DataTime=Time;
	}
	public void setType(int tipo) {
		this.Type=tipo;
	}

}
