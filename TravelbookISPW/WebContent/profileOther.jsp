<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.ControllerProfileOther" %>
<%@page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.travelbook.model.bean.Bean" %>
<%@ page import="main.java.travelbook.model.bean.MiniTravelBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>
<%
        ControllerProfileOther controller=new ControllerProfileOther();
		UserBean loggedUser=(UserBean)request.getSession().getAttribute("loggedBean");
		UserBean myUser=new UserBean();
		if(request.getParameter("user")!=null){
			Integer myId=Integer.valueOf(request.getParameter("user"));
			myUser=controller.getUser(myId);
		}
		
		Set<String> params=request.getParameterMap().keySet();
		System.out.println(params);
		for(String s: params){
			if(s.startsWith("travel")){
				String[] arg=s.split("travel");
				%>
					<jsp:forward page="viewTravel.jsp">
					<jsp:param name="travelID" value="<%=arg[1] %>"/>
					</jsp:forward>
				<%
			}
			if(s.startsWith("follow")){
				if(params.contains("inizio")){
					break;
				}
				String[] arg;
				System.out.println(s);
				if(s.startsWith("follower"))
					arg=s.split("follower");
				else
					arg=s.split("following");
				%>
					<jsp:forward page="profileOther.jsp">
					<jsp:param name="user" value="<%=arg[1] %>"/>
					</jsp:forward>
				<% 
			}
		}
		if(request.getParameter("addFav")!=null){
			loggedUser.getFollowing().add(myUser.getId());
			controller.updateFollow(loggedUser);
		}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/profile.css">
    <script src="js\jquery.min.js"></script>
    <script src="js\jsonReader.js"></script>
	<title>Travelbook</title>
	<script>
			var userID=<%=myUser.getId()%>
			var loggedID=<%=loggedUser.getId()%>
			function addToFav(){
				jQuery.ajax({
					url: "profileOther.jsp",
					data:{"user":userID,"addFav":"true"},
					type:"POST",
					error: function(xhr,ajaxOptions,thrownError){
						console.log(xhr.responseText);
						alert(xhr.status);
				         alert(thrownError);
					},
					success: function(){
						//CHANGE BUTTON COLOR
					}
					
				});
			}
			function goBack() {
				  window.history.back();
				}
	</script>

</head>
<body>
    <div class="header">
        <p class="title">
            Travelbook
        </p>
        <p class="subtitle">
            Wherever you go, go with all your heart
        </p>
    </div>
    <div class="anchor">
        <div class="panel">
            <input type="button" id="back" class="back-button">
            <div class="profile-panel other-panel">
            <input type="button" name="addFav" onclick="addToFav()"/>
                <%
            	byte[] userB=myUser.getArray();
            	if(userB!=null){
            		byte[] bytes=Base64.getEncoder().encode(userB);
					String encoded=new String(bytes,"UTF-8");
            		%>
            			<img src="data:image/*;base64,<%=encoded%>" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="profile picture">
            		<% 
            	}
            	else{
            		%>
            		  <img src="resource/travelers.png" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="default profile picture">
            		<% 
            	}
            %>
                <div class="v">
                    <p class="us">
                        <%=myUser.getName()+" "+myUser.getSurname() %>
                    </p>
                    <p class="us ds">
                    	<%=myUser.getDescription() %>
                    </p>
                </div>
            </div>
            <div id="bottom">
                <div id="l-bottom">
                   <input type="button" class="profile-button" value="Follower:<%=myUser.getNFollower() %>" onclick="showFollower()">
                    <input type="button" class="profile-button" value="Following:<%=myUser.getNFollowing()%>" onclick="showFollowing()">
                    <input type="button" class="profile-button fav-button" onclick="showFav()">
                    <p class="text">
                        <%=myUser.getName()%> has <%=myUser.getFav().size()%> favorite travels
                    </p>
                </div>
                <div class="map">
                    <p class=text>
                       <%=myUser.getName() %> has visited <%=myUser.getnPlace() %> cities
                    </p>
                </div>
            </div>
        </div>
         <div class="panel" id="right-panel">
			<%
				List<Bean> myTravel=controller.getTravel(myUser.getTravel());
				List<MiniTravelBean> travel = new ArrayList<>();
				if(myTravel==null)
					myTravel=new ArrayList<>();
				for(Bean i:myTravel){
					travel.add((MiniTravelBean)i);
				}
				int i=0;
				if(travel!=null){
				for(MiniTravelBean trav: travel){
					String buttonName="travel"+trav.getId();
					byte[] bytes=Base64.getEncoder().encode(trav.getArray());
					String encoded;
					if(bytes!=null){
						encoded=new String(bytes,"UTF-8");
						String path="data:image/gif;base64,"+bytes;
					}
					%>
						<div id=<%=i %>>
						<% 
						if(bytes!=null){
							%>
							<img id="travImg"src="data:image/*;base64,<%=encoded%>" style="width: 12.5em; height: 12.5em;" class="image" alt="travel picture"/>
							<%
						}
						%>
							<div>
								<form action="profile.jsp" method="POST">
									<p class=text>
									 <%=trav.getNameTravel() %>
									 <%=trav.getDescriptionTravel() %>
									 </p>
									 <input type="submit" name=<%=buttonName %> id=<%=trav.getId() %>/>
								</form>
							</div>
						</div>
					<% 
					i++;
				}}
			%>
        </div>
    </div>
</body>