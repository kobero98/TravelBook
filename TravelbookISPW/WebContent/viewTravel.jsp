<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.TravelBean" %>
<%@ page import="main.java.travelbook.controller.TravelController" %>
<%@ page import="main.java.travelbook.model.bean.StepBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.ControllerProfileOther" %>
<%@page errorPage="errorpage.jsp" %>
<%
	UserBean myUser=(UserBean)request.getSession().getAttribute("loggedBean");
	if(myUser==null){
		%>
			<jsp:forward page="login.jsp"/>
		<%
	}
	Integer id=Integer.valueOf(request.getParameter("travelID"));
	TravelController controller=new TravelController();
	TravelBean myTravel=controller.getTravel(id);


	if(request.getParameter("chat")!=null){
		response.sendRedirect("chat.jsp");
	}
	if(request.getParameter("fav")!=null){
		if(myUser.getFav()==null)
			myUser.setFav(new ArrayList<>());
		myUser.getFav().add(myTravel.getId());
		controller.updateFav(myUser);
	}
	if(request.getParameter("userToBeShared[]")!=null){
		String shareable=request.getParameter("userToBeShared[]");
		String[] users=shareable.split(",");
		List<Integer> ids=new ArrayList<>();
		List<UserBean> user=new ArrayList<>();
		for(String s: users){
			ids.add(Integer.valueOf(s));
			ControllerProfileOther cp=new ControllerProfileOther();
			UserBean u=cp.getUser(Integer.valueOf(s));
			user.add(u);
		}
		controller.shareTravel(user, id, myTravel.getIdCreator(), myUser.getId());
	}
	

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<link rel="shortcut icon" href="resource\travelbookIcon.ico">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/viewTravel.css">
     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="js\jquery.min.js"></script>
    <script src="js\viewJS.js"></script>
	<title>Travelbook</title>
	<script>
			var travelId=<%=myTravel.getId()%>
			class StepJS{
				constructor(groupDay,numberInDay,descriptionStep,place,precision){
					this.groupDay=groupDay;
					this.numberInDay=numberInDay;
					this.descriptionStep=descriptionStep;
					this.place=place;
					this.precision=precision;
					this.photo=new Array();
				}
			
			}

			<%
				for(int i=0;i<myTravel.getDayNum();i++){
			%>
				array[<%=i%>]=[];
			<%
					List<StepBean> steps=controller.stepInDay(myTravel.getListStep(),i);
					for(int j=0;j<steps.size();j++){
						%>
							array[<%=i%>][<%=j%>]=new StepJS(<%=steps.get(j).getGroupDay()%>,<%=steps.get(j).getNumberInDay()%>,"<%=steps.get(j).getDescriptionStep()%>","<%=steps.get(j).getPlace()%>","<%=steps.get(j).getPrecisionInformation()%>");
						<%
						List<String> encoded=new ArrayList<>();
						List<byte[]> bytes=steps.get(j).getArray();
						for(int c=0;c<bytes.size();c++){
							byte[] tobe=bytes.get(c);
							byte[] byteEncod=Base64.getEncoder().encode(tobe);
							String s=new String(byteEncod,"UTF-8");
							encoded.add(s);
						}
						%>
							var photoArray=new Array();
							<%
								for(int h=0;h<encoded.size();h++){
									String encodedString=encoded.get(h);
							%>
								photoArray[<%=h%>]="<%=encodedString%>";
							<%
								}
							%>
							array[<%=i%>][<%=j%>].photo=photoArray;
						<%
					}
				}
			%>
			
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
        <div class="panel panel2">
            <button type="button" id="back-button" class="back-button back2" onclick="goBack()"><span class="material-icons md-48">arrow_back</span></button>
            <div class="top">
                
                <div id="travel">
                <%
                	byte[] travB=myTravel.getArray();
            		byte[] bytes=Base64.getEncoder().encode(travB);
					String encoded=new String(bytes,"UTF-8");
                %>
				<img src="data:image/*;base64,<%=encoded%>" id="background" style="width: 17.35em; height: 11em;" alt="travel picture"/>
				<p><%=myTravel.getNameTravel() %></p>
                </div>
                <div id="label">
					<%
						if(myTravel.getDescriptionTravel()!=null){
					
					%>
						<p><%=myTravel.getDescriptionTravel() %></p>
					<%
						}
					%>
                </div>
                <div class="bb">
                
                    <button type="button" id="profile" name="profile" class="bb-button" onclick="goProfile(<%=myTravel.getIdCreator()%>)"><span class="material-icons">person</span></button>
                    <button type="button" id="chat" name="chat" class="bb-button" onclick="goChat(<%=myUser.getId()%>)"><span class="material-icons">textsms</span></button>
                    <%if(myUser.getFav()!=null && myUser.getFav().contains(myTravel.getId())){
                    	%><button type="button" id="fav" name="fav" class="bb-button select" onclick="addFav()"><span class="material-icons">favorite_border</span></button>
                   <% }
                    else{
                    %><button type="button" id="fav" name="fav" class="bb-button" onclick="addFav()"><span class="material-icons">favorite_border</span></button>
                    <% }%>
                    <button type="button" id="share" class="bb-button" onclick="showFav(<%=myUser.getId()%>)" name="shareButton"><span class="material-icons">share</span></button>
                  
                </div>
               <input type="button" id="viewMap" value="View On Map" onclick="loadMap()">
            </div>
            <div class="bot">
			<div class="days">
			<%	
				Integer days=myTravel.getDayNum();
				for(int i=0;i<days;i++){
			%>
				<button  class="tablinks" onclick="openDay(event,<%=i %>)">day <%=i+1 %></button>
				
			<%
				}
				%>
				</div>
				<% 
				for(int i=0;i<days;i++){
					%>
						<div id="day<%=i %>" class="tabcontent">
					<% 
					List<StepBean> steps=controller.stepInDay(myTravel.getListStep(),i);
					for(int j=0;j<steps.size();j++){
						StepBean step=steps.get(j);
					%>
						<button class="stepButton" onclick="openStep(event,<%=i %>,<%=j %>)">
						<span class="material-icons md-48">room</span>
						</button>
					<% 
					}
					%>
						</div>
					<% 
					}
			%>
			
            </div>
        </div>
        </div>
        <div class="panel" id="right-panel">
        	
        </div>
         <div id="shareDiv" class="share">
                	<form action="viewTravel.jsp" method="POST" id="formShare">
                		<input type="button" id="condividi" name="share"  value="share" onclick="shareTravel()">
                		<input type="button" value="close" onclick="closeShare()">
                	</form>
         </div>
</body>
</html>