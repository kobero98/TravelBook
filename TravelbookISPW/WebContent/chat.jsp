<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.Instant"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="main.java.travelbook.model.*" %>
<%@ page import="main.java.travelbook.controller.ChatController" %>
<%
	ChatController myController=new ChatController();
	List <Chat> c=new ArrayList<>();
	UserBean log=(UserBean)request.getSession().getAttribute("loggedBean");
	List<UserBean> tryContacts=new ArrayList<>();
	if(request.getSession().getAttribute("ChatList")!=null){ 
		c=(List<Chat>) request.getSession().getAttribute("ChatList");				
		if(c!=null) tryContacts = myController.getContacts(c,log.getId());
	}
	Integer sel=-1;
	int selettore=-1;
	if(request.getParameter("invia")!=null)
	{
					String s= (String) request.getParameter("invia");
					int nuovoId=Integer.valueOf(s);
					Chat newChat=new Chat(nuovoId);
					c.add(newChat);
					request.getSession().setAttribute("ChatList",c);
					tryContacts = myController.getContacts(c,log.getId());
		}
	for(int j=0;j<tryContacts.size();j++)
    {
    	if(request.getParameter("contatto"+j)!=null) {
    		selettore=j;
    		sel=tryContacts.get(j).getId();
    		request.getSession().setAttribute("sel",sel);
    		request.getSession().setAttribute("selettore",j);
    	}
    	
    }
	if(request.getParameter("invioMex")!=null)
	{	        
			StringBuffer text = new StringBuffer(request.getParameter("mex"));
	        /*int loc = (new String(text)).indexOf('\n');
	        while(loc > 0){
	            text.replace(loc, loc+1, "<BR>");
	            loc = (new String(text)).indexOf('\n');
	       }
	        */
	        
		if(request.getSession().getAttribute("selettore")!=null && !text.toString().isBlank() )
		{
			int index= (Integer) request.getSession().getAttribute("selettore");
			int id=tryContacts.get(index).getId();
			MessageBean messagge= new MessageBean(id,log.getId());
	        messagge.setText(text.toString()); 
	       	messagge.setRead(false);
		    messagge.setTime(Instant.now());
		    myController.sendMessage(messagge);
		    for(Chat l:c)
		    {
		    	if(l.getIdUser()==id)
		    	{
		    		l.getSend().add(messagge);
		    	}
		    }
		    
		}
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>  
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
	<script type="text/javascript">
	var selected;

	function goToExplore()
	{
		  location.replace("explore.jsp");
	}
	function goToProfile()
	{
		  location.replace("profile.jsp");
	}
	function goToAdd()
	{
		  location.replace("add.jsp");
	}
	var id={};
		function getData() { 
			document.getElementById("invia").value = selected.id;
		}
		(function poll() {
		    setTimeout(function() {
		        $.ajax({
		            url: "ChatReceiveMessagge.jsp",
		            type: "POST",
		            dataType: "json",
		            error:function(xhr,ajaxOptions,thrownError){
						console.log(xhr.responseText);
						alert(xhr.status);
				         alert(thrownError);
					},
		            data:{sel:<%=sel.toString()%>},
		            success: function(data) {
		                if(data.text!=null)
		                	{
		                		console.log(data.text);
		                		var div=document.createElement("div");
		                		var p=document.createElement("p");
		                		var text = document.createTextNode(data.text);
		                		p.appendChild(text);
		                		div.appendChild(p);
		                		var element = document.getElementById("chat");
		                		element.appendChild(div);
		                	}
		            },
		            
		            complete: poll,
		            timeout: 2000
		        })
		    }, 5000);
		})();
		
		$(function(){
			$('#search-bar').autocomplete(
	             {
		           	 position:{ my: "left top", at: "left bottom", collision: "none" },
		           	 minlength:1,
		           	 async: true,
		             source:function(request,response)
		             {
			          		$.ajax({
			                 url:"autocompleteUser.jsp",
			                 method:"get",
			                 dataType:"json",
			                 data:{search:request.term},
			                 error:function(xhr,ajaxOptions,thrownError){
									console.log(xhr.responseText);
									alert(xhr.status);
							         alert(thrownError);
								},
			                 success:function(data)
			                 {
			                	 console.log(data);
			                	 id=data;
								 var temp={}
									 for (i = 0; i < data.length; i++) {
									  temp[i]=data[i].nome;
									}
			                	 response(temp);
			                 }
		             		});
		             },
		             select: function( event, ui ) {
		            	 for(i=0;i<id.length;i++){
		            		 if(ui.item.value==id[i].nome){
		            			 selected=id[i];
		            		 }
		            	 }
	                   }
	             }); 
	            });
	</script>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/chat.css">
	<title>Travelbook</title>
	
</head>
<body>
    <div class="header">
        <p class="title">
            Travelbook
        </p>
        <p class="subtitle">
            Wherever you go, go with all your heart
        </p>
    </div>
    <div class="anchor">
     <div class="panel contact-panel">
            <form action="chat.jsp" class="menu-bar">
                <input type="button" class="button" name="profile" value="PROFILE" onclick=goToProfile()>
                <input type="button" class="button" name="add" value="ADD" onclick=goToAdd()>
                <input type="button" class="button" name="explore" value="EXPLORE" onclick=goToExplore()>
                <input type="button" class="button p-button" name="chat" value="CHAT">
            </form>
            <div class="contact" id=contact>
				<%
				int i=0;

				for(UserBean contact:tryContacts)
				{
					int idC=contact.getId();
					%>
					<form id=<%=idC %> action="chat.jsp" method="post">
					<%
					byte[] userB=contact.getArray();
	            	if(userB.length!=0){
	            		byte[] bytes=Base64.getEncoder().encode(userB);
						String encoded=new String(bytes,"UTF-8");
						System.out.println("valroe: "+encoded);
					%>
					 <img src="data:image/*;base64,<%=encoded%>" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="Profile picture">
            		<% 
            	}
            	else{
            		%>
            		  <img src="resource/travelers.png" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="default profile picture">
            		<% 
            	}
            %>
						<input type="submit" name=contatto<%=String.valueOf(i)%> onclick=avvioThread()>
						<p><%=contact.getName()%> <%=contact.getSurname()%><br>
					</form>			
			
				<%
				i++;
				}
				
				%>
					
				
            </div>
            <form class="search ui-widget" action=chat.jsp method="post">
                <input type="search" class="textfield" id=search-bar>
                <input type="submit" id=invia name=invia onclick=getData() >
            </form>
        </div>
        <div class="panel chat-panel">
            <div class="chat" id=chat>
            <%
				if(request.getSession().getAttribute("selettore")!=null)
				{	
					selettore=(Integer) request.getSession().getAttribute("selettore");
					int j=0;
					int x=0;
					for(Chat chat:c)
					{
						if(chat.getIdUser()== tryContacts.get(selettore).getId()) x=j;
						j++;
					}
					List<MessageBean> msgS=  c.get(x).getSend();
					List<MessageBean> msgR= c.get(x).getReceive();
					List<MessageBean> m= myController.getMessages(msgR, msgS);
					for(MessageBean messaggio:m)
					{
						if(messaggio.getIdDestinatario()==c.get(x).getIdUser()){
							%>
							<div>
								<p Style="background-color:red"><%=messaggio.getText()%><br>
							</div>
							<%
						}
						else{
							%>
							<div>
								<p Style="background-color:blue"><%=messaggio.getText()%><br>
							</div>
							<%
						}
					}
					
				}
		%>
            </div>
            <form class="write" action="chat.jsp" method="get">
                <textarea class="textfield" name=mex id=write-bar wrap="hard"></textarea>
                <input type="submit" name=invioMex>
            </form>
        </div>
   </div>