<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page language="java" %>
<%@page import="java.util.*" %>
<%@page import="exception.MapboxException" %>
<%@ page import="main.java.travelbook.controller.*" %>
<%@ page import="main.java.travelbook.util.PlaceAdapter" %>
<%
	if(request.getParameter("search")!=null){
    try
    {	ControllerSearch myController=new ControllerSearch();
    	String query = (String) request.getParameter("search");
		List <String> l=myController.getCitiesPredictions(query);
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
	}
	if(request.getParameter("place")!=null){
		try{
		String query=(String) request.getParameter("place");
		PredictionController controller=new PredictionController();
		List<JSONObject> result=controller.getPredictions(query);
		JSONObject obj=new JSONObject();
		for(int i=0;i<result.size();i++){
			PlaceAdapter place=new PlaceAdapter(result.get(i));
			obj.put("place"+i,place.toString());
		}
		response.setContentType("application/json");
		response.getWriter().write(obj.toString());
	}catch(MapboxException e){
		throw new MapboxException(e.getMessage());
	}
	}

%>
