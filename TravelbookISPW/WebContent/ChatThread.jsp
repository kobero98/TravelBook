<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.Instant"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="main.java.travelbook.model.*" %>
<%@ page import="main.java.travelbook.controller.ChatController" %>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>

<%if(request.getSession().getAttribute("esiste")==null){
		request.getSession().setAttribute("esiste",1);
		request.getSession().setAttribute("goon",1);
	 	ChatController myController=new ChatController();
		int id=Integer.valueOf(request.getParameter("id"));
		System.out.println(id);
		Instant lastTime=null;
		Instant lastLocalTime;
		List<Chat>chats=new ArrayList<>();
		int goon=(Integer) request.getSession().getAttribute("goon");
		while(goon==1)
		{
			if(request.getSession().getAttribute("ChatList")!=null) chats=(List<Chat>) request.getSession().getAttribute("ChatList");
			JSONObject o=new JSONObject();
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
							if(request.getSession().getAttribute("selettore")!=null){
									int idd= (Integer) request.getSession().getAttribute("selettore");
									if(message.getIdMittente()==idd)
									{
										System.out.println(idd+" "+message.getText());
										o.put("testo",message.getText());
										out.print(o.toString());
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
			goon=(Integer) request.getSession().getAttribute("goon");
			out.print(o.toString());
		}
	}
	
