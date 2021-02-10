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
		JSONArray array=new JSONArray();
		int i=0;
		while(i<l.size()){
			JSONObject o=new JSONObject();
			o.put("id",l.get(i).getId());
			o.put("nome", l.get(i).toString());
			array.add(o);
			i++;
     	 }
		out.print(array);
    } catch(Exception e1)
      {
      	out.println(e1);
      }

%>