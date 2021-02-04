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
<%
	ProfileController controller=new ProfileController();
	UserBean myUser=(UserBean) request.getSession().getAttribute("loggedBean");
	Set<String> params=request.getParameterMap().keySet();
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
			String[] arg;
			System.out.println(s);
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
		cont.updatePhotoInputStream(myUser.getId(),is);
	}
	
%>

	
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/profile.css">
    <script src="js\jquery.min.js"></script>
    <script src="js\jsonReader.js"></script>
	<title>Travelbook</title>
	<script>
	var userID=<%=myUser.getId()%>
	function init(){
		document.getElementById("fotoFile").addEventListener("mouseover",function(){
			$("#chooseIm").animate({opacity: '1'},"slow");
		});
		document.getElementById("fotoFile").addEventListener("mouseout",function(){
			$("#chooseIm").animate({opacity: '0'},"slow");
		});
	}
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
				console.log(xhr.responseText);
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
	

			
			function showShared(){
				jQuery.ajax({
					url:"jsonResponser.jsp",
					type:"GET",
					data:{"shared":'true',"userID":userID},
					dataType:"json",
					error: function(xhr,ajaxOptions,thrownError){
						console.log(xhr.responseText);
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
	</script>

</head>
<body onload="init()">
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
            <div id=fotoFile>
            <%
            	byte[] userB=myUser.getArray();
            	if(userB!=null){
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
              
                <input type="file" id=chooseIm name="profileImage" accept="image/jpg, image/png" class="custom-file-input" onchange="loadImage()"/>
                </div>
                <div class="v">
                    <p class="us">
                       <%=myUser.getName() %>
                    </p>
                    <p class="us ds">
                        <%=myUser.getDescription() %>
                    </p>
                </div>
            </div>
            <div class="menu-bar" id="p-menubar">
            	<form action="explore.jsp" method="POST">
                <input type="submit" class="button p-button" name="profile" value="PROFILE">
                <input type="submit" class="button" name="add" value="ADD">
                <input type="submit" class="button" name="explore" value="EXPLORE">
                <input type="submit" class="button" name="chat" value="CHAT">
                </form>
            </div>
            <div id="bottom">
                <div id="l-bottom">
                    <input type="button" class="profile-button" value="Follower:<%=myUser.getNFollower() %>" onclick="showFollower()">
                    <input type="button" class="profile-button" value="Following:<%=myUser.getNFollowing()%>" onclick="showFollowing()">
                    <input type="button" class="profile-button fav-button" onclick="showFav()">
                    
                    <p class="text">
                        Your favourite travels
                    </p>
                    <input type="button" class="profile-button fav-button" onclick="showShared()">
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
							<img id="travImg"src="data:image/*;base64,<%=encoded%>" style="width: 12.5em; height: 12.5em;" class="image" alt="travel presentation picture" />
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
									 <input type="button"  onclick="modifyTravel(<%=trav.getId()%>)"/>
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