<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="org.json.simple.JSONObject"%>
<%
if(request.getSession().getAttribute("nuovaChat")!=null){
	MessageBean m=(MessageBean) request.getSession().getAttribute("nuovaChat");
	JSONObject o=new JSONObject();
	o.put("text", m.getText());
	out.println(o);
	request.getSession().setAttribute("nuovaChat",null);
}
else{
	
	out.println("{}");
}
%>