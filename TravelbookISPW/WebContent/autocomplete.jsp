<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page language="java" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@ page import="main.java.travelbook.controller.*" %>
<%
    try
    {
    	String query = (String) request.getParameter("search");
		List <String> l=ControllerSearch.getInstance().getCitiesPredictions(query);
		JSONObject o=new JSONObject();
		int i=0;
		while(i<l.size()){ 
			o.put("citta"+i, l.get(i));
			i++;
     	 }
		out.print(o);
    } catch(Exception e1)
      {
    	System.out.println(e1.getMessage());
      out.println(e1);
      }

%>
