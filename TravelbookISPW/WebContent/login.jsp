<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page errorPage="errorpage.jsp" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.*" %>
<jsp:useBean id="user" scope="session" class="main.java.travelbook.model.bean.UserBean"/>
<jsp:setProperty name="user" property="*" />
<jsp:useBean id="userReg" scope="request" class="main.java.travelbook.model.bean.RegistrationBean"/>
<jsp:setProperty name="userReg" property="*"/> 

<%	
	if (request.getParameter("accedi")!=null){
		ControllerLogin controller=new ControllerLogin();
		UserBean logged=controller.signIn(user.getUsername(), user.getPassword());
		request.getSession().setAttribute("loggedBean",logged);
%>
			<jsp:forward page="explore.jsp"/>
<% 
	}
	if(request.getParameter("signup")!=null){
		out.println(userReg.toString());
		if(userReg.getUsername()!=null && userReg.getEmail()!=null && userReg.getPassword()!=null && userReg.getName()!=null && userReg.getSurname()!=null && userReg.getBirtdate()!=null && userReg.getGender()!=null){
		ControllerLogin controller=new ControllerLogin();
		String code= controller.calcoloRegistration(userReg.getEmail());
		request.getSession().setAttribute("regBean",userReg);
		request.getSession().setAttribute("code",code);
		%>
			<jsp:forward page="confirm.jsp"/>
		<%
	}
	}
%>

	

<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<script src="js\jquery.min.js"></script>
	<link rel="stylesheet" href="css\loginCss.css">
	<title>Travelbook</title>
	<script>
			
			function apriRegistrazione(){
				$("#login").animate({opacity: '0.1'},"slow");
				$("#registrazione").animate({opacity: '0.9'},"slow");
			}
			function closeRegistration(){
				$("#login").animate({opacity: '1'},"slow");
				$("#registrazione").animate({opacity: '0'},"slow");
			}
			function closeCode(){
				$("#registrazione").animate({opacity:'0.9'},"slow");
				$("#code").animate({opacity:'0'},"slow");
			}
			
				
	</script>

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
		<div id=login>
			<form action="login.jsp" id="loginTable" method="POST">
				<input id="username" type="text" name="username" class="textfield">
				<input id="pswd" type="password" name="password" class="textfield">
				<div id=buttons>
				<input type="button" value="registrati" class="form-button" onclick="apriRegistrazione()">
				<input type="submit" value="accedi" name="accedi" class="form-button">
			</div>
			</form>
			<div id=fb>
			<a href="https://www.facebook.com/v3.2/dialog/oauth?client_id=1332279647110748&response_type=token&redirect_uri=http://localhost:8080/ProvaTomcat/facebook.jsp&state=\'{st=state123abc,ds=123456789}\'">Accedi con facebook</a>
			<img src="resource\logoFacebook.png">
		</div>
		</div>
		<div id="immagine">
			<img src="resource\mondo.png">
		</div>
		
	</div>
	<div id="registrazione" class="stack-top">
			<form action="login.jsp" method="POST">
				<input id="username" type="text" name="username">
				<input type="password" name="password">
				<input type="email" name="email">
				<input type="text" id="name" name="name">
				<input id="surname" type="text" name="surname">
				 <input type="radio" id="male" name="gender" value="male">
					<label for="male">Male</label><br>
				<input type="radio" id="female" name="gender" value="female">
					<label for="female">Female</label><br>
				<input type="radio" id="other" name="gender" value="other">
					<label for="other">Other</label> 
				<input type="date" name="birthDate">
				<input type="submit" name="signup" value="signup">
				<input type="button" name="close" value="close" onclick="closeRegistration()">
			</form>
	</div>
	
</body>
</html>