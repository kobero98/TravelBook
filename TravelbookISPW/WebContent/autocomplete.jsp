<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@page language="java" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@ page import="main.java.travelbook.controller.*" %>
<%
	System.out.println("ciao");
    try
    {
    	ControllerSearch controller=ControllerSearch.getInstance();
    	 String query = (String)request.getParameter("search");
    	 System.out.println(query);
		List <String> l=controller.getCitiesPredictions(query);
        JSONArray json=new JSONArray();
		int i=0;
		while(i<l.size()){
			JSONObject o=new JSONObject();
			o.put("citta", l.get(i));
			json.add(o);
     	 }
		out.print(json.toString());
    } catch(Exception e1)
      {
      out.println(e1);
      }

%>
