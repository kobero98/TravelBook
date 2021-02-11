<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="org.json.simple.JSONObject"%>

<%
if(request.getSession().getAttribute("notifica")!=null){
	JSONObject o=new JSONObject();
	o.put("text", "notifica");
	out.println(o);
}
else{
	out.println("{}");
}
%>