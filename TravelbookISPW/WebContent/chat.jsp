<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.Instant"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="main.java.travelbook.model.*" %>
<%@ page import="main.java.travelbook.controller.ChatController" %>
<%@page errorPage="errorpage.jsp" %>
<%
	request.getSession().setAttribute("notifica",null);
	ChatController myController=new ChatController();
	List <Chat> c=new ArrayList<>();
	UserBean log=(UserBean)request.getSession().getAttribute("loggedBean");
	if(log==null){
		%>
			<jsp:forward page="login.jsp"/>
		<% 
	}
	List<UserBean> tryContacts=new ArrayList<>();
	if(request.getSession().getAttribute("ChatList")!=null){ 
		c=(List<Chat>) request.getSession().getAttribute("ChatList");				
		if(c!=null) tryContacts = myController.getContacts(c,log.getId());
	}
	Integer sel=-1;
	int selettore=-1;
	if(request.getParameter("invia")!=null)
	{
					int controlloEsistenzaUtente=0;
					String s= (String) request.getParameter("invia");
					int nuovoId=Integer.valueOf(s);
					
					for(int j=0;j<tryContacts.size();j++){
						if(tryContacts.get(j).getId()==nuovoId) {
							selettore=j;
				    		sel=tryContacts.get(j).getId();
				    		request.getSession().setAttribute("sel",sel);
				    		request.getSession().setAttribute("selettore",j);
				    		 controlloEsistenzaUtente=1;
						}
					}
					if(controlloEsistenzaUtente==0)
					{
						Chat newChat=new Chat(nuovoId);
						c.add(newChat);
						request.getSession().setAttribute("ChatList",c);
						tryContacts = myController.getContacts(c,log.getId());
					}
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
<link rel="shortcut icon" href="resource\travelbookIcon.ico">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
     
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.12.4.js" integrity="sha384-KcyRSlC9FQog/lJsT+QA8AUIFBgnwKM7bxm7/YaX+NTr4D00npYawrX0h+oXI3a2" crossorigin="anonymous"></script>  
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" integrity="sha384-JPbtLYL10d/Z1crlc6GGGGM3PavCzzoUJ1UxH0bXHOfguWHQ6XAWrIzW+MBGGXe5" crossorigin="anonymous"></script>
    
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
			document.getElementById("invia").value = selected;
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
		                		p.className = "msg-s";
		                		var objDiv = document.getElementById("chat");
		                		objDiv.scrollTop = objDiv.scrollHeight;
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
	            	 position:{ my: "center bottom", at: "center top", collision: "flip" },
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
								 var temp={};
									 for (i = 0; i < data.length; i++) {
									  temp[i]={};
									  temp[i].value=data[i].nome;
									  temp[i].label=data[i].nome;
									  temp[i].id=data[i].id;
									}
								 console.log(temp);
			                	 response(temp);
			                 }
		             		});
		             },
		             select: function( event, ui ) {
		            	 	selected=ui.item.id;
		            		
		            	 }
	             }); 
	            });
		
		function updateScroll(){
		var element = document.getElementById("chat");
		element.scrollTop = element.scrollHeight;
		}
	
	</script>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/chat.css">
	<title>Travelbook</title>
	
</head>
<body onload=updateScroll()>
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
            <div class="menu-bar">
                <button type="button" class="button" name="profile" onclick=goToProfile()><span class="material-icons">person</span>PROFILE</button>
                <button type="button" class="button" name="add" onclick=goToAdd()><span class="material-icons">edit</span>ADD</button>
                <button type="button" class="button" name="explore" onclick=goToExplore()><span class="material-icons">explore</span>EXPLORE</button>
                <button type="button" class="button p-button" name="chat"><span class="material-icons">textsms</span>CHAT</button>
            </div>
            <div class="contact" id=contact>
				<%
				int i=0;

				for(UserBean contact:tryContacts)
				{
					int idC=contact.getId();
					Integer id=(Integer) request.getSession().getAttribute("selettore");
					if(id!=null && idC==tryContacts.get(id.intValue()).getId()){
					%>
					<form id=<%=idC %> action="chat.jsp" method="post" class="contact-widget selected">
					<%}
					else{
					%><form id=<%=idC %> action="chat.jsp" method="post" class="contact-widget"><% 
					}
					byte[] userB=contact.getArray();
	            	if(userB.length!=0){
	            		byte[] bytes=Base64.getEncoder().encode(userB);
						String encoded=new String(bytes,"UTF-8");
					%>
					 <img src="data:image/*;base64,<%=encoded%>" id="profileIm" style="width: 4em; height: 4em;" class="image" alt="Profile picture">
            		<% 
            	}
            	else{
            		%>
            		  <img src="resource/travelers.png" id="profileIm" style="width: 4em; height: 4em;" class="image" alt="default profile picture">
            		<% 
            	}
            %>			<p><%=contact.getName()%> <%=contact.getSurname()%></p>
						<button type="submit" name=contatto<%=String.valueOf(i)%> class="c-expand"onclick=avvioThread()><span class="material-icons">chevron_right</span></button>
						
					</form>			
			
				<%
				i++;
				}
				
				%>
					
				
            </div>
            <form class="search ui-widget" action=chat.jsp method="post">
                <input type="search" class="textfield" id=search-bar>
                <button type="submit" id=invia name=invia onclick=getData() class="c-expand"><span class="material-icons md-36">search</span></button>
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
						if(!messaggio.getRead()) myController.setReadMex(messaggio);
						if(messaggio.getIdDestinatario()==c.get(x).getIdUser()){
							%>
							<div>
								<p class="msg-r"><%=messaggio.getText()%><br>
							</div>
							<%
						}
						else{
							%>
							<div>
								<p class="msg-s"><%=messaggio.getText()%><br>
							</div>
							<%
						}
					}
					
				}
		%>
            </div>
            <form class="write" action="chat.jsp" method="get">
                <textarea class="textfield" name=mex id=write-bar wrap="hard"></textarea>
                <button type="submit" class="c-expand" name=invioMex><span class="material-icons md-48">send</span></button>
            </form>
        </div>
   </div>