<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="org.json.simple.JSONObject"%>
<%
if(request.getSession().getAttribute("NuovoMessaggio")!=null){
	MessageBean m=(MessageBean) request.getSession().getAttribute("NuovoMessaggio");
	JSONObject o=new JSONObject();
	o.put("text", m.getText());
	out.println(o);
	request.getSession().setAttribute("NuovoMessaggio",null);
}
else{
	
	out.println("{}");
}
%>