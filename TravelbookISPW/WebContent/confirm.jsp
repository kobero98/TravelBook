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
				if(request.getSession().getAttribute("pswd")!=null){
					
					%>
						<jsp:forward page="recuperaPassword.jsp"/>
					<%
				}
				controller.signUp(user);
				UserBean logged=controller.signIn(user.getUsername(),user.getPassword());
				logged.setFirstTime(true);
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
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css\loginCss.css">
<link rel="shortcut icon" href="resource\travelbookIcon.ico">
<title>Travelbook</title>
</head>
<body>
			<form action="confirm.jsp" method="POST" id="codice">


				<p id=code-text>
					Insert here the code we have sent you
				</p>
				<input id="code" type="text" name="code" value="insert code here" class="textfield" required>

				<div id=c-buttons class="buttons">
				<input id="closeCode" type="button" name="closeCode" value="close" class="form-button code-button">
				<input id="confirm" type="submit" name="confirm" value="confirm" class="form-button code-button">
				</div>
				
			</form>
</body>
</html>