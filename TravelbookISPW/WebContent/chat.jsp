<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page errorPage="errorpage.jsp" %>
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
		tryContacts = myController.getContacts(c,log.getId());
	}
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>  
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
	<script type="text/javascript">
	var selected=0;
	var myMap = new Map();
	<%
		for(Chat chat:c){
		%>
			myMap.set(<%=chat.getIdUser()%>,{"")
		<%
		}
	%>
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
	function createChat()
	{
		
	}
	function log( message ) {
			 $('#contact').append(
					    $('<div>').prop({
					      id: 'innerdiv',
					      innerHTML: message,
					      className: 'border pad'
					    })
					  );
			    }
	 $(function(){
		 
		 $('#search-bar').autocomplete(
	             {
		           	 position:{ my: "left top", at: "left bottom", collision: "none" },
		           	 minlength:1,
		             source:function(request,response)
		             {
			          		$.ajax({
			                 url:"autocompleteUser.jsp",
			                 method:"get",
			                 dataType:"json",
			                 data:{search:request.term},
			                 success:function(data)
			                 {
			                	 response(data);
			                	 log(data.user0);
			                 }
		             		});
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
				for(UserBean contact:tryContacts)
				{
					int idC=contact.getId();
					%>
					<div id=<%=idC %>>
						<p><%=contact.getName()%> <%=contact.getSurname()%><br>
					</div>			
				
				
			<% }
				%>
					
				
            </div>
            <div class="search ui-widget">
                <input type="search" class="textfield" id=search-bar>
                <input type="Button" id=invia onclick=createChat() >
            </div>
        </div>
        <div class="panel chat-panel">
            <div class="chat">
            </div>
            <div class="write">
                <textarea class="textfield" id="write-bar" wrap="hard"></textarea>
            </div>
        </div>
   </div>