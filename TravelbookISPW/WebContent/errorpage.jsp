<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
<link rel="stylesheet" href="css/loginCss.css">
<link rel="shortcut icon" href="resource\travelbookIcon.ico">
<link rel="stylesheet" href="css/error.css">
</head>
<body>
	<p id=txt>
		Exception is: <%= exception %>
	</p>
</body>
</html>