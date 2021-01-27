<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="errorpage.jsp" %>
<%@page import="main.java.travelbook.controller.ControllerLogin"  %>
<%@page import="main.java.travelbook.model.bean.RegistrationBean" %>
<%@page import="main.java.travelbook.model.bean.UserBean" %>
<%		
		if(request.getParameter("confirm")!=null){
			String code=(String)request.getSession().getAttribute("code");
			RegistrationBean user=(RegistrationBean)request.getSession().getAttribute("regBean");
			ControllerLogin controller=new ControllerLogin();
			if(code.equals(request.getParameter("code"))){
				controller.signUp(user);
				UserBean logged=controller.signIn(user.getUsername(),user.getPassword());
				request.getSession().setAttribute("loggedBean",logged);
			%>
				<jsp:forward page="explore.jsp"/>
			<% 
			}
			else{
				throw new Exception("Codice errato");
			}
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css\loginCss.css">
<title>Travelbook</title>
</head>
<body>
	<div id=code>
			<form action="confirm.jsp" method="POST">
				<input id="code" type="text" name="code" value="insert code here">
				<input id="closeCode" type="button" name="closeCode" value="close">
				<input id="confirm" type="submit" name="confirm" value="confirm">
			</form>
	</div>
</body>
</html>