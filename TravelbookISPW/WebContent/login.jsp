<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.*" %>
<%@page import="java.sql.Date" %>
<%@page errorPage="errorpage.jsp" %>
<%@page import="java.util.Locale" %>
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
		String date=(String)request.getParameter("birthDate");
		userReg.setBirtdate((Date.valueOf(date)));
		userReg.setGender(userReg.getGender().subSequence(0, 0).toString());
		ControllerLogin controller=new ControllerLogin();
		String code= controller.calcoloRegistration(userReg.getEmail());
		request.getSession().setAttribute("regBean",userReg);
		request.getSession().setAttribute("code",code);
		%>
			<jsp:forward page="confirm.jsp"/>
		<%
	}	if(request.getParameter("token")!=null){
		
		ControllerLogin controller=new ControllerLogin();
		UserBean logged=controller.facebookLogin(request.getParameter("token"));
		request.getSession().setAttribute("loggedBean",logged);
		
	}
	
%>



<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="ISO-8859-1">
	<script src="js\jquery.min.js"></script>
	<link rel="stylesheet" href="css\loginCss.css">
	<title>Travelbook</title>
	<script>
			function leggiUrl(){
				var url=document.location.href;
				var token=url.split("#access_token=");
				if(token.length>1){
					var access_token=url.slice("http://localhost:8080/TravelbookISPW/login.jsp?".length);
					
					jQuery.ajax({
						url: "login.jsp",
						type: "POST",
						data: "token="+encodeURIComponent(access_token),
						
						error: function(xhr,ajaxOptions,thrownError){
							alert(xhr.status);
					         alert(thrownError);
					       },
						success: function(){
							document.location.href="http://localhost:8080/TravelbookISPW/explore.jsp";
						}
					});
				}
				
			}
			function apriRegistrazione(){
				$("#login").animate({opacity: '0.1'},"slow");
				$("#registrazione").animate({opacity: '1'},"slow");
			}
			function closeRegistration(){
				$("#login").animate({opacity: '1'},"slow");
				$("#registrazione").animate({opacity: '0'},"slow");
			}
			function closeCode(){
				$("#registrazione").animate({opacity:'1'},"slow");
				$("#code").animate({opacity:'0'},"slow");
			}
			function cambiaPassword(){
				document.location.href="http://localhost:8080/TravelbookISPW/inserisciEmail.jsp";
			}
				
	</script>

</head>
<body onload="leggiUrl()">
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
				<input id="Username" type="text" name="username" class="textfield" required>
				<label for="Username">Username or Email</label>
				<input id="pswd" type="password" name="password" class="textfield" required>
				<label for="pswd">Password</label>
				<div class=buttons>
				<input type="button" value="registrati" class="form-button" onclick="apriRegistrazione()">
				<input type="submit" value="accedi" name="accedi" class="form-button">
				
			</div>
			</form>
			<input type="button" name="forgot" value="forgot your password?" onclick="cambiaPassword()">
			<div id=fb>
			<a href="https://www.facebook.com/v3.2/dialog/oauth?client_id=1332279647110748&response_type=token&redirect_uri=http://localhost:8080/TravelbookISPW/login.jsp&state=\'{st=state123abc,ds=123456789}\'">Accedi con facebook</a>
			<img src="resource\logoFacebook.png" alt="FB logo">
		</div>
		
		</div>
		<div id="immagine">
			<img src="resource\mondo.png" alt="logo">
		</div>
		
	</div>
	<div id=registrazione>
			<form action="login.jsp" method="POST" id="registerTable">
				<input id="username" type="text"  name="username" class="textfield" required>
				<label for="username">Username</label>
				<input type="password" id="password" name="password" value="password" class="textfield" required>
				<label for="password">Password</label>
				<input type="email" id="email"name="email"  class="textfield" required>
				<label for="email">Email</label>
				<input type="text" id="name" name="name"  class="textfield" required>
				<label for="name">Name</label>
				<input type="text" id="surname" name="surname"  class="textfield" required>
				<label for="surname">Surname</label>
				<input type="date" id="dateB" name="birthDate" class="date-picker" required>
				<label for="dateB">Birthdate</label>
				<select id="nation"  name="nation">
					<%
					String[] countryCodes=Locale.getISOCountries();
					for(String cc: countryCodes) {
						Locale obj = new Locale("", cc);
						String nation=obj.getDisplayCountry();
						%>
							<option><%=nation %></option>
						<% 
						
					}
					%>
				</select>
				<div>
				 <input type="radio" id="male" name="gender" value="male" required>
					<label for="male">Male</label><br>
				<input type="radio" id="female" name="gender" value="female" required>
					<label for="female">Female</label><br>
				<input type="radio" id="other" name="gender" value="other" required>
					<label for="other">Other</label> 
					</div>
				<div class="buttons">
				<input type="submit" name="signup" value="signup" class="form-button" required>
				<input type="button" name="close" value="close" class="form-button" onclick="closeRegistration()">
				</div>
			</form>
	</div>
	
</body>
</html>