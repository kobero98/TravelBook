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
<%@page errorPage="errorpage.jsp" %>
<%
        ControllerProfileOther controller=new ControllerProfileOther();
		UserBean loggedUser=(UserBean)request.getSession().getAttribute("loggedBean");
		UserBean myUser=new UserBean();
		if(myUser==null){
			%>
				<jsp:forward page="login.jsp"/>
			<% 
		}
		if(request.getParameter("user")!=null){
			Integer myId=Integer.valueOf(request.getParameter("user"));
			myUser=controller.getUser(myId);
		}
		else{
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
		}
		if(request.getParameter("addFav")!=null){
			loggedUser.getFollowing().add(myUser.getId());
			controller.updateFollow(loggedUser.getId(),myUser.getId());
		}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/profile.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
						var btn = document.getElementById("follow");
						if(btn.className==="follow"){
							btn.className+=" select";
						}
						else{
							btn.className="follow";
						}
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
            <button type="button" id="back" class="back-button"><span class="material-icons md-48">arrow_back</span></button>
            <div class="profile-panel other-panel">
            <%if(myUser.getFollower()!=null && myUser.getFollower().contains(loggedUser.getId())){
           %> <button type="button" id=follow class="follow select" name="addFav" onclick="addToFav()"><span class="material-icons md-36">person_add</span></button>
               <%}
            else{
            %><button type="button" id=follow class="follow" name="addFav" onclick="addToFav()"><span class="material-icons md-36">person_add</span></button>
        
                <%}
            	byte[] userB=myUser.getArray();
            	if(userB!=null){
            		byte[] bytes=Base64.getEncoder().encode(userB);
					String encoded=new String(bytes,"UTF-8");
					if(!encoded.isEmpty()){
            		%>
            			<img src="data:image/*;base64,<%=encoded%>" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="profile picture">
            		<% 
					}
					else{
						%>
	            		  <img src="resource/travelers.png" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="default profile picture">
	            		<% 
					}
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
                    <button type="button" class="profile-button fav-button" onclick="showFav()"><span class="material-icons">favorite_border</span></button>
                    <p class="text">
                    <%
                    	Integer num=0;
                    	if(myUser.getFav()!=null)
                    		num=myUser.getFav().size();
                    %>
                        <%=myUser.getName()%> has <%=num%> favorite travels
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
					String encoded="";
					if(bytes!=null){
						encoded=new String(bytes,"UTF-8");
						String path="data:image/gif;base64,"+bytes;
					}
					%>
						<div id=<%=i %> class="travel-tile">
						<% 
						if(bytes!=null){
							%>
							<img id="travImg"src="data:image/*;base64,<%=encoded%>" style="width: 12.5em; height: 12.5em;" class="travel-tile-photo" alt="travel picture"/>
							<%
						}
						%>
							<div>
								<form action="profile.jsp" method="POST">
									<p class=text>
									 <%=trav.getNameTravel() %>
									 <%=trav.getDescriptionTravel() %>
									 </p>
									 <button type="submit" class="tile-icon" name=<%=buttonName %> id=<%=trav.getId() %>><span class="material-icons">open_in_full</span></button>
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