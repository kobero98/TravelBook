<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="main.java.travelbook.controller.MyProfileController" %>
<%@ page import="java.util.Base64" %>
<%@ page import="main.java.travelbook.model.bean.Bean" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.model.bean.MiniTravelBean" %>
<%@ page import="java.util.List" %>
<%@page import="main.java.travelbook.model.bean.ShareBean" %>
<%@ page import="java.util.ArrayList" %>
<%
	UserBean myUser=(UserBean)request.getSession().getAttribute("loggedBean");
	MyProfileController controller=new MyProfileController();
if(request.getParameter("follower")!=null || request.getParameter("following")!=null){
	JSONObject json;
	List<Bean> names;
	if(request.getParameter("follower")!=null){
		names=controller.getFollow(myUser.getFollower());
	}else{
		names=controller.getFollow(myUser.getFollowing());
	}
	response.setContentType("application/json");
	JSONArray array=new JSONArray();
	JSONObject users=new JSONObject();
	if(names!=null){
	for(int i=0;i<names.size();i++){
		UserBean us=(UserBean)names.get(i);
		json=new JSONObject();
		json.put("name",us.getName());
		json.put("surname",us.getSurname());
		byte[] bytes=us.getArray();
		String encoded;
		if(bytes==null)
			encoded="";
		else{
			bytes=Base64.getEncoder().encode(bytes);
			encoded=new String(bytes,"UTF-8");
		}
		json.put("image",encoded);
		json.put("id",""+us.getId());
		array.add(json);
		System.out.println(json.toString());
	}
		System.out.println(array.toString());
		response.setCharacterEncoding("UTF-8");
		users.put("users",array);
		response.getWriter().write(users.toString());
	}
	else{
		response.getWriter().write("");
	}
}
	if(request.getParameter("travel")!=null || request.getParameter("shared")!=null){
		JSONObject obj=new JSONObject();
		List<Bean> travs=new ArrayList<>();
		List<Bean> sh;
		if(request.getParameter("travel")!=null){
			travs=controller.getFav(myUser.getFav());
		}
		else{
			sh=controller.getShared(myUser.getId());
			for(int j=0;j<sh.size();j++){
				ShareBean shared=(ShareBean)sh.get(j);
				MiniTravelBean trav=controller.getTravel(shared.getTravelShared());
				travs.add(trav);
			}
		}
		response.setContentType("application/json");
		JSONArray ar=new JSONArray();
		JSONObject travels=new JSONObject();
		if(travs!=null){
			for(int i=0;i<travs.size();i++){
				MiniTravelBean trav=(MiniTravelBean)travs.get(i);
				obj=new JSONObject();
				obj.put("title",trav.getNameTravel());
				byte[] foto=trav.getArray();
				String encoded;
				if(foto==null)
					encoded="";
				else{
					foto=Base64.getEncoder().encode(foto);
					encoded=new String(foto,"UTF-8");
				}
				obj.put("image",encoded);
				obj.put("id",""+trav.getId());
				ar.add(obj);
			}
			response.setCharacterEncoding("UTF-8");
			travels.put("travels",ar);
			response.getWriter().write(travels.toString());
		}
		else{
			obj.put("title","not found");
			travels.put("travels",obj);
			response.getWriter().write(travels.toString());
		}
	}

%>