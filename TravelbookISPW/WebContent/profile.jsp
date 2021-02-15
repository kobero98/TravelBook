<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page errorPage="errorpage.jsp" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.model.bean.MiniTravelBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.travelbook.controller.ProfileController" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Base64" %>
<%@ page import="main.java.travelbook.model.bean.Bean" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="main.java.travelbook.controller.MyProfileController" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="main.java.travelbook.controller.ControllerProfileOther" %>
<%
    request.getSession().setAttribute("selettore",null);
	ControllerProfileOther c=new ControllerProfileOther();
	MyProfileController controller=new MyProfileController();
	UserBean myUser=(UserBean) request.getSession().getAttribute("loggedBean");
	if(request.getSession().getAttribute("loggedBean")==null){
		%>
			<jsp:forward page="login.jsp"/>
		<% 
	}
	myUser=c.getUser(myUser.getId());
	Set<String> params=request.getParameterMap().keySet();
	for(String s: params){
		if(s.startsWith("shared")){
			String[] arg1=s.split("shared");
			%>
				<jsp:forward page="viewTravel.jsp">
				<jsp:param name="travelID" value="<%=arg1[1] %>"/>
				</jsp:forward>
			<% 
		}
		if(s.startsWith("travel")){
			String[] arg=s.split("travel");
			%>
				<jsp:forward page="viewTravel.jsp">
				<jsp:param name="travelID" value="<%=arg[1] %>"/>
				</jsp:forward>
			<%
		}
		if(s.startsWith("fav")){
			String[] arg=s.split("fav");
			%>
				<jsp:forward page="viewTravel.jsp">
				<jsp:param name="travelID" value="<%=arg[1] %>"/>
				</jsp:forward>
			<% 
		}
		if(s.startsWith("removeTravel")){
			String[] ar=s.split("removeTravel");
			controller.deleteTravel(Integer.valueOf(ar[1]));
			myUser.getTravel().remove(Integer.valueOf(ar[1]));
			if(myUser.getFav().contains(Integer.valueOf(ar[1])))
				myUser.getFav().remove(Integer.valueOf(ar[1]));
			request.getSession().setAttribute("loggedBean",myUser);
		}
		if(s.startsWith("follow")){
			String[] arg;
			if(s.startsWith("follower")){
				arg=s.split("follower");
			
			}
			else{
				arg=s.split("following");
			}
			%>
				<jsp:forward page="profileOther.jsp">
				<jsp:param name="user" value="<%=arg[1] %>"/>
				<jsp:param name="inizio" value="true"/>
				</jsp:forward>
			<% 
		}
	}
	if(request.getParameter("profileIm")!=null){
		String s=(String)request.getParameter("profileIm");
		byte[] bytesB64=s.getBytes();
		byte[] bytes=Base64.getDecoder().decode(bytesB64);
		InputStream is=new ByteArrayInputStream(bytes);
		MyProfileController cont=new MyProfileController();
		cont.updatePhoto(myUser.getId(),is);
	}
	if(request.getParameter("descrizione")!=null){
		String s=request.getParameter("descrizione");
		controller.updateDescr(myUser.getId(), s);
	}
	
	
%>

	
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="shortcut icon" href="resource\travelbookIcon.ico">
    <link rel="stylesheet" href="css/profile.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="js\jquery.min.js"></script>
    <script src="js\jsonReader.js"></script>
	<title>Travelbook</title>
	<script>
	var userID=<%=myUser.getId()%>
	var op=true;
	function goToExplore()
	{
		  location.replace("explore.jsp");
	}
	function goToChat()
	{
		  location.replace("chat.jsp");
	}
	function goToAdd()
	{
		  location.replace("add.jsp");
	}
	
	(function Notify() {
	    setTimeout(function() {
	        $.ajax({
	            url: "ChatNewChat.jsp",
	            type: "POST",
	            dataType: "json",
	            error:function(xhr,ajaxOptions,thrownError){
					alert(xhr.status);
			         alert(thrownError);
				},
	            success: function(data) {
	            	 if(data.text!=null){
	            		 document.getElementById("chatNotify").innerHTML = "mark_chat_unread";
	            		}
	            },
	            
	            complete: Notify,
	            timeout: 2000
	        })
	    }, 5000);
	})();
	function fileLoaded(frEvnt){
		var sFBody = frEvnt.target.result;
		var sBodyBase64 = btoa(sFBody);
		var im=document.getElementById("profileIm");
		im.setAttribute("src","data:image/gif;base64,"+sBodyBase64);
		jQuery.ajax({
			url:"profile.jsp",
			type:"POST",
			data:{"profileIm":sBodyBase64},
			error:function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success:function(){
				alert("Successo");
			}
		})
	}
	function loadImage(){
		var oFile=document.getElementById("chooseIm").files[0];
		if(oFile){
			var fReader=new FileReader();
			fReader.onload=fileLoaded;
			fReader.readAsBinaryString(oFile);
		}
	}
	
	function logout(){
		document.location.href="http://localhost:8080/TravelbookISPW/login.jsp";
	}
			
			function showShared(){
				jQuery.ajax({
					url:"jsonResponser.jsp",
					type:"GET",
					data:{"shared":'true',"userID":userID},
					dataType:"json",
					error: function(xhr,ajaxOptions,thrownError){
						alert(xhr.status);
				         alert(thrownError);
				       },
				    success: function(data){
				    	readTravel(data,"shared");
				    	}
				    });
			}
			function modifyTravel(id){
				document.location.href="http://localhost:8080/TravelbookISPW/add.jsp?modifyTravel="+id;
			}
			var block=true;
			function modificaDesc(){
				if(block){
					block=false;
					document.getElementById("descText").removeAttribute("disabled");
				}
				else{
					block=true;
					document.getElementById("descText").setAttribute("disabled","");
					jQuery.ajax({
						url:"profile.jsp",
						data:{"descrizione":document.getElementById("descText").value},
						error: function(xhr,ajaxOptions,thrownError){
							alert(xhr.status);
					         alert(thrownError);
					       }
					});
				}
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
            
            <div class="profile-panel">
            <button type="button" id="logoutButton" onclick="logout()"><span class="material-icons">logout</span></button>
            <div id=fotoFile>
            <%
            	byte[] userB=myUser.getArray();
            	if(userB.length!=0){
            		byte[] bytes=Base64.getEncoder().encode(userB);
					String encoded=new String(bytes,"UTF-8");
            		%>
            			<img src="data:image/*;base64,<%=encoded%>" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="Profile picture">
            		<% 
            	}
            	else{
            		%>
            		  <img src="resource/travelers.png" id="profileIm" style="width: 12.5em; height: 12.5em;" class="image" alt="default profile picture">
            		<% 
            	}
            %>
              	
                <input type="file" id="chooseIm" name="profileImage" accept="image/jpg, image/png" class="custom-file-input" onchange="loadImage()"/>
                </div>
                <div class="v">
                    <p class="us">
                       <%=myUser.getName() %> <%=myUser.getSurname() %>
                    </p>
                    <textarea id="descText" style="width:20em; height:10em;"disabled maxlength="150"><%=myUser.getDescription() %></textarea>
                    <button type="button" class="s-back-button" value="modificaDesc" onclick="modificaDesc()"><span class="material-icons md-18">edit</span>
                    </button>
                </div>
            </div>
            <div class="menu-bar" id=p-menubar>
                <button type="button" class="button p-button" name="profile" ><span class="material-icons">person</span>PROFILE</button>
                <button type="button" class="button" name="add" onclick=goToAdd()><span class="material-icons">edit</span>ADD</button>
                <button type="button" class="button" name="explore" onclick=goToExplore()><span class="material-icons">explore</span>EXPLORE</button>
                <button type="button" class="button" name="chat" onclick=goToChat()><span id=chatNotify class="material-icons">textsms</span>CHAT</button>
            </div>
            <div id="bottom">
                <div id="l-bottom">
                    <input type="button" class="profile-button" value="Follower:<%=myUser.getNFollower() %>" onclick="showFollower()">
                    <input type="button" class="profile-button" value="Following:<%=myUser.getNFollowing()%>" onclick="showFollowing()">
                    <button type="button" class="profile-button fav-button" onclick="showFav()"><span class="material-icons">favorite_border</span></button>
                    
                    <p class="text">
                        Your favourite travels
                    </p>
                    <button type="button" class="profile-button fav-button" onclick="showShared()"><span class="material-icons">share</span></button>
                    <p class="text">
                    Your shared travels
                    </p>
                </div>
                <div class="map">
                    <p class=text>
                        You have visited <%=myUser.getnPlace() %> cities
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
							<img id="travImg"src="data:image/*;base64,<%=encoded%>" style="width: 12.5em; height: 12.5em;" class="travel-tile-photo" alt="travel presentation picture" />
							<%
						}
							%>
							<div>
								<form action="profile.jsp" method="POST">
									<p class=text>
									 <h3><%=trav.getNameTravel() %></h3>
									 <p><%=trav.getDescriptionTravel() %><br>

									 </p>
									 <button type="submit" class="tile-icon" name=<%=buttonName %> id=<%=trav.getId() %>><span class="material-icons">open_in_full</span></button>
									 <% 
									 	if(!trav.isShared()){
									 %>
									 
									 <button type="button" class="tile-icon" onclick="modifyTravel(<%=trav.getId()%>)"><span class="material-icons">edit</span></button>
									 <% 
									 	}
									 %>
									 <button type="submit" class="tile-icon" name="removeTravel<%=trav.getId() %>"><span class="material-icons">delete</span></button>
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