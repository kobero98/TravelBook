<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 	String [] src={"explore-t.jpg","search-t.jpg","view-t.jpg","profile-other.jpg","add-t.jpg","profile-t.jpg","chat-t.jpg"};

 	int i=0;
 	if(request.getSession().getAttribute("tutorial")!=null)
 	{
 	 	 i=(Integer) request.getSession().getAttribute("tutorial");
 	}
 	if(request.getParameter("prew")!=null){i=i-1;}
 	if(request.getParameter("next")!=null){i=i+1;}
 	request.getSession().setAttribute("tutorial",i);
 	if(i>=7){%><jsp:forward page="explore.jsp"/> <%}
 %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="resource\travelbookIcon.ico">
<meta charset="ISO-8859-1">
<title>Travelbook tutorial</title>
</head>
<body style="display:flex; align-items:center;">
	<form action="Tutorial.jsp" method="POST">
		<%if(i<=0){%><input type="submit" name=prew id=prew value="prew"  style="display: none;"> <% } 
		else{
		%>
			<input type="submit" name=prew id=prew value="prew" > 	
		<%}%>
		<img id=img src=<%="Tutorial/"+src[i]%> alt="tutorial image">
		<input type="submit" name=next id=next value="next">
	</form>
</body>
</html>