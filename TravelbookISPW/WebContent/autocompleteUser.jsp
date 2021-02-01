<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page import="main.java.travelbook.controller.*" %>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%
    try
    {
    	UserBean log=(UserBean)request.getSession().getAttribute("loggedBean");
    	String query = (String) request.getParameter("search");
		List <UserBean> l=new ChatController().getUserPredictions(query,log.getId());
		JSONObject o=new JSONObject();
		int i=0;
		while(i<l.size()){
			o.put("user"+i, l.get(i).toString());
			i++;
     	 }
		out.print(o);
    } catch(Exception e1)
      {
    	System.out.println(e1.getMessage());
      	out.println(e1);
      }

%>