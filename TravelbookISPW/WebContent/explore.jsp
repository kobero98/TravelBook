<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="main.java.travelbook.model.bean.UserBean" %>
<%
	if(request.getSession().getAttribute("loggedBean")!=null){
		UserBean log=(UserBean)request.getSession().getAttribute("loggedBean");
		
		out.println(log.getName());
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\explore.css">
	<title>Travelbook</title>
	<Script>
	function spostamentoSearch(){
		location.replace("search.jsp");
	}
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
                <input type="button", class="button", name="profile", value="PROFILE">
                <input type="button", class="button", name="add", value="ADD">
                <input type="button", class="button p-button", name="explore", value="EXPLORE">
                <input type="button", class="button", name="chat", value="CHAT">
            </div>
            <p class = "write">
                Suggestions
            </p>
            <div class="scroll">

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
            <img src= "resource/cartina-no-sfondo.png" style="width:36.25em;height:16.8125em;">
            <p id = "search" title>
                ADVANCED SEARCH
            </p>
            <div id="sp">
                <p id = "searchWrite">
                    Looking for something more specific? Try our research tool, narrowing your desires with a lot of different options
                </p>
                <input type="button" id="searchButton" name="searchButton" onclick="spostamentoSearch()"path="M12 8V4l8 8-8 8v-4H4V8z">
            </div>
            
        </div>
    </div>
</body>