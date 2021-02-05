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
	UserBean log=null;
	if(request.getSession().getAttribute("loggedBean")!=null){
		log=(UserBean)request.getSession().getAttribute("loggedBean");
		out.println(log.getName());
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
	}
	if(request.getParameter("profile")!=null){
%>
	<jsp:forward page="profile.jsp"/>
<%		
	}
	if(request.getParameter("add")!=null){
%>
	<jsp:forward page="add.jsp"/>
<% 
	}
	if(request.getParameter("chat")!=null){
%>
	<jsp:forward page="chat.jsp"/>
<% 
	}
	if(request.getParameter("explore")!=null){
%>
	<jsp:forward page="explore.jsp"/>
<% 
	}
	if(request.getParameter("searchButton")!=null){
%>
	<jsp:forward page="search.jsp"/>
<% 
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\explore.css">

	<title>Travelbook</title>
	<Script>
	function spostamentoSearch(){
		location.replace("search.jsp");
	}		
	$.ajax({
         url:"ChatThread.jsp",
         method:"get",
         data:{id:<%=log.getId()%>},
         success:function(data)
         { 
        	 console.log(id);
         }
     });

	</Script>

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
         
        <div class="panel">
            <div class="menu-bar">
            <form action="explore.jsp" method="POST">
                <input type="submit" class="button" name="profile" value="PROFILE">
                <input type="submit" class="button" name="add" value="ADD">
                <input type="submit" class="button p-button" name="explore" value="EXPLORE">
                <input type="submit" class="button" name="chat" value="CHAT">
            </form>
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
							<input type="submit" name="suggest<%=t.getId() %>" value="Vedi Viaggio"/>
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

            </div>
        </div>
        <div class= "panel" id="right-panel">
            <p class = "write">
                Where do you want to go?
            </p>
            <img src= "resource/cartina-no-sfondo.png" style="width:36.25em;height:16.8125em;" alt="cartina">
            <p id = "search" title>
                ADVANCED SEARCH
            </p>
            <div id="sp">
            	<form action="explore.jsp" method="POST">
                <p id = "searchWrite">
                    Looking for something more specific? Try our research tool, narrowing your desires with a lot of different options
                </p>

                <input type="submit" id="searchButton" name="searchButton" path="M12 8V4l8 8-8 8v-4H4V8z">
                </form>

                <input type="button", id="searchButton", name="searchButton",path="M12 8V4l8 8-8 8v-4H4V8z">

                <input type="button" id="searchButton" name="searchButton" onclick="spostamentoSearch()"path="M12 8V4l8 8-8 8v-4H4V8z">

            </div>
            
        </div>
    </div>
</body>