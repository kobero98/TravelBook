<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@page errorPage="errorpage.jsp" %>
 <%@page import="main.java.travelbook.controller.ControllerLogin"  %>
<%@page import="main.java.travelbook.model.bean.RegistrationBean" %>
<%@page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="exception.DBException" %>
 <%
 	if(request.getAttribute("confirm")!=null){
 		String email=(String)request.getSession().getAttribute("pswd");
 		String pswd=(String)request.getAttribute(pswd);
 		String pswdR=(String)request.getAttribute(pswdR);
 		if(pswd.equals(pswdR)){
 			ControllerLogin controller=new ControllerLogin();
 			controller.changeMyPassword(email, pswd);
 		}
 		else{
 			throw new DBException("Le password non sono uguali");
 		}
 	}
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Travelbook</title>

</head>
<body>
				<form action="recuperaPassword.jsp" method="POST">
				<input id="pswd" type="password" name="pswd" value="insert password" class="textfield" required>
				<input id="pswdR" type="password" name="pswdR" value="repeat password" class="textfield" required>
				<div class="buttons">
				<input id="closeCode" type="button" name="closeCode" value="close" class="form-button">
				<input id="confirm" type="submit" name="confirm" value="confirm" class="form-button">
				</div>
				</form>
</body>
</html>