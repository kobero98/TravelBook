package main.java.travelbook.util;
import main.java.travelbook.controller.ChatController;
import java.time.Instant;
import main.java.travelbook.view.MenuBar;
import java.util.List;
import main.java.travelbook.model.MessageEntity;
import main.java.travelbook.model.bean.MessageBean;
import java.util.ArrayList;
public class MessagePollingThread extends Thread {
	private Instant lastTime=null;
	private boolean goOn=true;
	ChatController myController = new ChatController();
	public void kill() {
		this.goOn=false;
	}
	@Override
	public void run() {
		Instant lastLocalTime;
		boolean found=false;
		System.out.println("Run");
		while(goOn) {
		try {
			//Non c'e' bisogno di sincronizzarsi sulla chat perche' il thread che scrive e' uno solo
		lastLocalTime=lastTime;
		List<Chat> chats=MenuBar.getInstance().getMyChat();
		if(lastTime!=null) {
			List<MessageEntity> messages=myController.getNewMessage(MenuBar.getInstance().getLoggedUser().getId(),lastLocalTime);
			if(!messages.isEmpty())
				lastTime=Instant.now();
			for(MessageEntity message: messages) {
				found=false;
				for(int i=0;i<chats.size();i++) {
					Chat chat=chats.get(i);
					if(chat.getIdUser()==message.getIdMittente()) {
						chat.getReceive().add(new MessageBean(message));
						chat.setChanged();
						found=true;
						break;
						}
					}
					if(!found) {
						System.out.println("Nuova chat creata");
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(new MessageBean(message));
						Chat nuovaChat=new Chat(message.getIdMittente(),messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
						nuovaChat.setChanged();
					}
				}
		}
		else {
			lastTime = Instant.now();
			List<MessageBean> messages = myController.getReceived(MenuBar.getInstance().getLoggedUser().getId());
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
						System.out.println("Nuova chat creata");
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(message);
						Chat nuovaChat=new Chat(message.getIdMittente(),messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
						nuovaChat.setChanged();
					}
				}
			messages = myController.getSend(MenuBar.getInstance().getLoggedUser().getId());
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
						System.out.println("Nuova chat creata");
						List<MessageBean> messaggi=new ArrayList<>();
						messaggi.add(message);
						Chat nuovaChat=new Chat(message.getIdDestinatario(),null,messaggi);
						MenuBar.getInstance().newChat(nuovaChat);
					}
				}
			
		}
		Thread.sleep(3000);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		}
		
	}
}
