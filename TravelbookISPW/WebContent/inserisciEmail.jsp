<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="main.java.travelbook.controller.ControllerLogin" %>
<%
	if(request.getParameter("email")!=null){
		ControllerLogin controller=new ControllerLogin();
		String code=controller.calcoloRegistration(request.getParameter("email"));
		request.getSession().setAttribute("code",code);
		request.getSession().setAttribute("pswd",request.getParameter("email"));
	%>
		<jsp:forward page="confirm.jsp"/>
	<%
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Travelbook</title>
</head>
<body>
	<form action="inserisciEmail.jsp" method="POST">
	<input type="text" name="email" id="email">
	<input type="submit" value="confirm">
	<label for="email">Email</label>
	</form>
</body>
</html>