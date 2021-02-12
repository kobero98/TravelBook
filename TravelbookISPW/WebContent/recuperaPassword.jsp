<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@page errorPage="errorpage.jsp" %>
 <%@page import="main.java.travelbook.controller.ControllerLogin"  %>
<%@page import="main.java.travelbook.model.bean.RegistrationBean" %>
<%@page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="exception.DBException" %>
 <%
 	if(request.getParameter("confirm1")!=null){
 		String email=(String)request.getSession().getAttribute("pswd");
 		String pswd=(String)request.getParameter("pswd");
 		String pswdR=(String)request.getParameter("pswdR");
 		if(pswd.equals(pswdR)){
 			ControllerLogin controller=new ControllerLogin();
 			controller.changeMyPassword(email, pswd);
 			%>
 				<jsp:forward page="login.jsp"/>
 			<% 
 		}
 		else{
 			throw new DBException("Le password non sono uguali");
 		}
 	}
 %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="resource\travelbookIcon.ico">
<meta charset="ISO-8859-1">
<title>Travelbook</title>

</head>
<body>
				<form action="recuperaPassword.jsp" method="POST">
				<input id="pswd" type="password" name="pswd" value="insert password" class="textfield" required>
				<input id="pswdR" type="password" name="pswdR" value="repeat password" class="textfield" required>
				<div class="buttons">
				<input id="closeCode" type="button" name="closeCode" value="close" class="form-button">
				<input id="confirm" type="submit" name="confirm1" value="confirm" class="form-button">
				</div>
				</form>
</body>
</html>