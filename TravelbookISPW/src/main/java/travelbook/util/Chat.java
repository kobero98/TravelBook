package main.java.travelbook.util;

import main.java.travelbook.model.bean.MessageBean;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
public class Chat extends Observable{
	private List<MessageBean> send=new ArrayList<>();
	private List<MessageBean> receive=new ArrayList<>();
	private int idUser;
	public Chat(int idUser) {
		this.idUser=idUser;
	}
	public Chat(int idUser, Collection<MessageBean> messageReceive) {
		this(idUser);
		this.receive=(List<MessageBean>)messageReceive;
	}
	public Chat(int idUser,Collection<MessageBean> messageReceive,Collection<MessageBean> messageSend) {
		this(idUser,messageReceive);
		this.send=(List<MessageBean>)messageSend;
	}
	public List<MessageBean> getSend() {
		return send;
	}
	public void setSend(List<MessageBean> send) {
		if(send!=null)this.send = send;
	}
	public List<MessageBean> getReceive() {
		return receive;
	}
	public void setReceive(List<MessageBean> receive) {
		if(receive!=null)this.receive = receive;
	}
	public int getIdUser() {
		return idUser;
	}
	
}
