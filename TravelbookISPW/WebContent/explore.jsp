<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="errorpage.jsp" %>
<%@page import="main.java.travelbook.model.bean.UserBean" %>
<%@page import="main.java.travelbook.model.bean.MiniTravelBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="main.java.travelbook.controller.ExploreController" %>
<%@page import="java.util.Base64" %>
<%@page import="java.util.Set" %>
<%
	System.out.println(request.getParameterMap().keySet().toString());
    request.getSession().setAttribute("selettore",null);
	UserBean log=null;
	if(request.getSession().getAttribute("loggedBean")!=null){
		log=(UserBean)request.getSession().getAttribute("loggedBean");
	}
	else{
		%>
			<jsp:forward page="login.jsp"/>
		<% 
	}
	if(log.isFirstTime()){
		log.setFirstTime(false);
		request.getSession().setAttribute("loggedBean",log);
		%> <jsp:forward page="Tutorial.jsp"/> <% 
	}
	Set<String> params=request.getParameterMap().keySet();
	for(String s: params){
		if(s.startsWith("suggest")){
			String[] arg=s.split("suggest");
			%>
				<jsp:forward page="viewTravel.jsp">
				<jsp:param name="travelID" value="<%=arg[1] %>"/>
				</jsp:forward>
			<% 
		}
		if(s.startsWith("topten")){
			String[] arg=s.split("topten");
			System.out.println(arg[1]);
			%>
				<jsp:forward page="viewTravel.jsp">
				<jsp:param name="travelID" value="<%=arg[1] %>"/>
				</jsp:forward>
			<% 
		}
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="shortcut icon" href="resource\travelbookIcon.ico">
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.10.2.js" integrity="sha384-r0tJvB87edk25TJle8mfwmdYBwaGtkX3r4CYHXS+2yZ7VPdI8xd2rHl6KTQ6oij4" crossorigin="anonymous"></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js" integrity="sha384-YwCdhNQ2IwiYajqT/nGCj0FiU5SR4oIkzYP3ffzNWtu39GKBddP0M0waDU7Zwco0" crossorigin="anonymous"></script>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\explore.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

	<title>Travelbook</title>
	<Script>
	function goToChat()
	{
		  location.replace("chat.jsp");
	}
	function goToProfile()
	{
		  location.replace("profile.jsp");
	}
	function goToAdd()
	{
		  location.replace("add.jsp");
	}
	function spostamentoSearch(){
		location.replace("search.jsp");
	}	
	
	function avvioThread(){
		$.ajax({
         url:"ChatThread.jsp",
         method:"get",
         data:{id:<%=log.getId()%>},
     });
	}
	
	(function NotifyMessage() {
		    setTimeout(function() {
		        $.ajax({
		            url: "ChatNewChat.jsp",
		            type: "POST",
		            dataType: "json",
		            error:function(xhr,ajaxOptions,thrownError){
						alert(xhr.status);
				         alert(thrownError);
					},
		            success: function(data) {
		            	 if(data.text!=null){
		            		 document.getElementById("chatNotify").innerHTML = "mark_chat_unread";
		            		}
		            },
		            
		            complete: NotifyMessage,
		            timeout: 2000
		        })
		    }, 5000);
	})();

	</Script>

</head>
<body onload=avvioThread()>
    <div class="header">
        <p class="title">
            Travelbook
        </p>
        <p class="subtitle">
            Wherever you go, go with all your heart
        </p>
    </div>
    <div class="anchor">
         
        <div class="panel">
            <div class="menu-bar">
                <button type="button" class="button" name="profile" onclick=goToProfile()><span class="material-icons">person</span>PROFILE</button>
                <button type="button" class="button" name="add" onclick=goToAdd()><span class="material-icons">edit</span>ADD</button>
                <button type="button" class="button p-button" name="explore"><span class="material-icons">explore</span>EXPLORE</button>
                <button type="button" class="button" name="chat" onclick=goToChat()><span id=chatNotify class="material-icons">textsms</span>CHAT</button>
            </div>
            <p class = "write">
                Suggestions
            </p>
            <div class="scroll">
			<%
				List<MiniTravelBean> travels=new ArrayList<>();
				for(int i=0;i<15;i++){
					MiniTravelBean tr=new MiniTravelBean();
					travels.add(tr);
				}
				ExploreController c=new ExploreController();
				c.setSuggests(travels, log);
				for(int i=0;i<15;i++){
					MiniTravelBean t=travels.get(i);
					String encoded="";
					byte[] bytes=t.getArray();
					bytes=Base64.getEncoder().encode(bytes);
					encoded=new String(bytes,"UTF-8");
					%>
						<form action="explore.jsp" method="POST">
						<div id="suggest<%=i %>" class="travelButton" >
							<button type="submit" class="expand" name="suggest<%=t.getId() %>"><span class="material-icons">open_in_full</span></button>
							<img src="data:image/*;base64,<%=encoded%>" style="width: 8em; height: 6em;" alt="travelImage">
							<h1 class="travel-text"><%=t.getNameTravel() %></h1>
							<p class="travel-text"><%=t.getDescriptionTravel() %></p>
						</div>
						</form>
					<% 
				}
			%>
            </div>
            <p class = "write">
                Top ten
            </p>
            <div class="scroll">
			<%
				travels=new ArrayList<>();
				for(int i=0;i<15;i++){
					MiniTravelBean tr=new MiniTravelBean();
					travels.add(tr);
				}
				c.setTopTen(travels);
				for(int i=0;i<10;i++){
					MiniTravelBean t=travels.get(i);
					String encoded="";
					byte[] bytes=t.getArray();
					bytes=Base64.getEncoder().encode(bytes);
					encoded=new String(bytes,"UTF-8");
					%>
						<form action="explore.jsp" method="POST">
						<div id="topten<%=i %>" class="travelButton" >
							<button type="submit" class="expand" name="topten<%=t.getId()%>"><span class="material-icons">open_in_full</span></button>
							<img src="data:image/*;base64,<%=encoded%>" style="width: 8em; height: 6em;" alt="travelImage">
							<h1 class="travel-text"><%=t.getNameTravel() %></h1>
							<p class="travel-text"><%=t.getDescriptionTravel() %></p>
						</div>
						</form>
					<% 
				}
			%>
            </div>
        </div>
        <div class= "panel" id="right-panel">
            <p class = "write">
                Where do you want to go?
            </p>
            <img src= "resource/cartina-no-sfondo.png" style="width:36.25em;height:16.8125em;" alt="cartina">
            <p id = "search">
                ADVANCED SEARCH
            </p>
            <div id="sp">
            	<div>
                <p id = "searchWrite">
                    Looking for something more specific? Try our research tool, narrowing your desires with a lot of different options
                </p>

                </div>

                <button type="button" id="searchButton" name="searchButton" onclick="spostamentoSearch()"><span class="material-icons md-48">forward</span></button>

            </div>
            
        </div>
    </div>
</body>