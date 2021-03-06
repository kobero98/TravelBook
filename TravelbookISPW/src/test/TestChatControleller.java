package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.exception.DBException;
import main.java.travelbook.controller.ChatController;
import main.java.travelbook.model.bean.MessageBean;
import main.java.travelbook.util.Chat;
/*
 * @author Matteo Federico aka Kobero
 */
public class TestChatControleller {

	@Test
	public void testgetContactsnotWork()
	{
		//testing that getContacts throws the correct exception if the 
		//id passed as parameter is not linked to any user
		List<Chat> chats= new ArrayList<>();
		Chat c=new Chat(-1);
		chats.add(c);
		ChatController controller=new ChatController();
		assertThrows(DBException.class,()->controller.getContacts(chats,1));
	}
	
	@Test
	public void testsendMessagenotWork() {
		//testing that sendMessage throws the correct exception if the sender or receiver
		//id are not valid values
		MessageBean message= new MessageBean(-1,-1);
		message.setText("ciao");
		message.setTime(Instant.now());
		ChatController controller=new ChatController();
		assertThrows(DBException.class,()->controller.sendMessage(message));
		
	}
	public void sendMessage() throws DBException {
		MessageBean message= new MessageBean(1,6);
		message.setText("ciao");
		message.setTime(Instant.now());
		ChatController controller=new ChatController();
		controller.sendMessage(message);
	}
	
	@Test
	public void testGetNewMessage() throws DBException, InterruptedException {
		//testing that receiveMessage load correctly the messages received after a given time
		Instant x=Instant.now();
		ChatController controller=new ChatController();
		Thread.sleep(1000);
		sendMessage();
		List<MessageBean> m=controller.getNewMessage(1,x);
		boolean test=false;
		for(MessageBean message:m){
				if(message.getText().equals("ciao")) test=true;
			}
			assertEquals(true,test);
		
		
	}
}
