<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.Instant"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="main.java.travelbook.model.*" %>
<%@ page import="main.java.travelbook.controller.ChatController" %>

<%


	Boolean goon=true;
 	ChatController myController=new ChatController();
	int id=Integer.valueOf(request.getParameter("id"));
	System.out.println(id);
	Instant lastTime=null;
	Instant lastLocalTime;
	List<Chat>chats=new ArrayList<>();
	while(goon)
	{
		if(request.getSession().getAttribute("ChatList")!=null) chats=(List<Chat>) request.getSession().getAttribute("ChatList");
		
		if(lastTime!=null) {
					boolean found=false;
					lastLocalTime=lastTime;
					List<MessageEntity> messages=myController.getNewMessage(id,lastLocalTime);
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
								List<MessageBean> messaggi=new ArrayList<>();
								messaggi.add(new MessageBean(message));
								Chat nuovaChat=new Chat(message.getIdMittente(),messaggi);
								chats.add(nuovaChat);
								nuovaChat.setChanged();
							}
						}
					
		}
		else {
			System.out.println("sto vedendo se ci sono nuovi messaggi");
			boolean found;
			lastTime = Instant.now();
			
			List<MessageBean> messages = myController.getMessagesThread(0,id);
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
						chats.add(nuovaChat);
						nuovaChat.setChanged();
					}
				}
			messages = myController.getMessagesThread(id,0);
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
						chats.add(nuovaChat);
					}
				}
		}
		request.getSession().setAttribute("ChatList",chats);
		Thread.sleep(3000);
	}
	%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>