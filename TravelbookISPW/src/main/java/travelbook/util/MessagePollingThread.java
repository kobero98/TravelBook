package main.java.travelbook.util;
import main.java.travelbook.controller.ChatController;
import java.time.Instant;
import main.java.travelbook.view.MenuBar;
import java.util.List;

import exception.DBException;
import main.java.travelbook.model.bean.MessageBean;
import java.util.ArrayList;
public class MessagePollingThread extends Thread {
	private Instant lastTime=null;
	private boolean goOn=true;
	private ChatController myController = new ChatController();
	public void kill() {
		this.goOn=false;
	}
	@Override
	public void run() {
		while(goOn) {
		try {
			//Non c'e' bisogno di sincronizzarsi sulla chat perche' il thread che scrive e' uno solo
		List<Chat> chats=MenuBar.getInstance().getMyChat();
		while(!MenuBar.getInstance().isObserved()) {
			//aspettare che questa condizione diventi vera
		}
		if(lastTime!=null) {
			updateMessage(chats);
		}
		else {
			initMessageReceive(chats);
			initMessageSend(chats);
			
		}
		Thread.sleep(3000);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		}
		private void updateMessage(List<Chat> chats) throws DBException {
			boolean found=false;
			Instant lastLocalTime;
			lastLocalTime=lastTime;
			List<MessageBean> messages=myController.getNewMessage(MenuBar.getInstance().getLoggedUser().getId(),lastLocalTime);
			if(!messages.isEmpty())
				lastTime=Instant.now();
			for(MessageBean message: messages) {
				found=false;
				for(int i=0;i<chats.size();i++) {
					Chat chat=chats.get(i);
					if(chat.getIdUser()==message.getIdMittente()) {
						chat.getReceive().add(message);
						chat.setChanged();
						found=true;
						break;
						}
					}
					if(!found) {
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(message);
						Chat nuovaChat=new Chat(message.getIdMittente(),messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
						nuovaChat.setChanged();
					}
				}
		}
		private void initMessageReceive(List<Chat> chats) throws DBException{
			boolean found;
			lastTime = Instant.now();
			List<MessageBean> messages = myController.getMessagesThread(0,MenuBar.getInstance().getLoggedUser().getId());
			for(MessageBean message: messages) {
				found=false;
				for(int i=0;i<chats.size();i++) {
					Chat chat=chats.get(i);
					if(chat.getIdUser()==message.getIdMittente()) {
						chat.getReceive().add(message);
						if(!message.getRead())
							chat.setChanged();
						found=true;
						break;
						}
					}
					if(!found) {
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(message);
						Chat nuovaChat=new Chat(message.getIdMittente(),messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
						if(!message.getRead())
							nuovaChat.setChanged();
					}
				}
			
		}
		private void initMessageSend(List<Chat> chats) throws DBException{
			boolean found;
			List<MessageBean> messages = myController.getMessagesThread(MenuBar.getInstance().getLoggedUser().getId(),0);
			
			for(MessageBean message: messages) {
				found=false;
				for(int i=0;i<chats.size();i++) {
					Chat chat=chats.get(i);
					if(chat.getIdUser()==message.getIdDestinatario()) {
						chat.getSend().add(message);
						found=true;
						break;
						}
					}
					if(!found) {
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(message);
						Chat nuovaChat=new Chat(message.getIdDestinatario(),null,messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
					}
				}
		}
		public void setTimeStampNull() {
			this.lastTime=null;
		}
}
