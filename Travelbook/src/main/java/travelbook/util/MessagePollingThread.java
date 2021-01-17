package main.java.travelbook.util;
import main.java.travelbook.controller.ChatController;
import main.java.travelbook.view.MenuBar;
import java.util.List;
import main.java.travelbook.model.MessageEntity;
import main.java.travelbook.model.bean.MessageBean;
import java.util.ArrayList;
public class MessagePollingThread extends Thread {
	
	
	@Override
	public void run() {
		boolean found=false;
		System.out.println("Run");
		try {
			//Non c'e' bisogno di sincronizzarsi sulla chat perche' il thread che scrive e' uno solo
		List<MessageEntity> messages=ChatController.getIstance().getNewMessage( MenuBar.getLoggedUser().getId());
		List<Chat> chats=MenuBar.getMyChat();
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
				MenuBar.newChat(nuovaChat);
				nuovaChat.setChanged();
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
