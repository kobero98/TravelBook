<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 	String [] src={"explore-t.jpg","search-t.jpg","view-t.jpg","profile-other.jpg","add-t.jpg","profile-t.jpg","chat-t.jpg",""};
 	int i=0;
 	if(request.getSession().getAttribute("tutorial")!=null)
 	{
 	 	 i=(Integer) request.getSession().getAttribute("tutorial");
 	}
 	System.out.println(i);
 	if(i>6){%><jsp:forward page="explore.jsp"/> <%}
 	int t=i+1;
 	request.getSession().setAttribute("tutorial",t);
 	
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="display:flex; align-items:center;">
	<form action="Tutorial.jsp" method="POST">
		<img id=img src=<%="Tutorial/"+src[i]%> alt="tutorial image">
		<input type="submit" id=next value="next" onclick="nextPhoto();">
	</form>
</body>
</html>