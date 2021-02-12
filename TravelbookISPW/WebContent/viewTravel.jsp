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
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/viewTravel.css">
     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="js\jquery.min.js"></script>
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
			var array=new Array();
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
							System.out.println(byteEncod);
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
			function openDay(event,dayNumber){
				var i, tabcontent,tablinks;
				tabcontent=document.getElementsByClassName("tabcontent");
				for(i=0;i<tabcontent.length;i++){
					tabcontent[i].style.display="none";
				}
				tablinks=document.getElementsByClassName("tablinks");
				for(i=0;i<tablinks.length;i++){
					tablinks[i].className="tablinks";
				}
				document.getElementById("day"+dayNumber).style.display="flex";
				event.currentTarget.className+=" active";
			}
			function openStep(event,dayNumber,stepNumber){
				var steps = document.getElementsByClassName("stepButton");
				for(var i=0;i<steps.length;i++){
					steps[i].className="stepButton";
				}
				event.currentTarget.className+=" p-step";
				var div=document.getElementById("right-panel");
				var fotoDiv=document.createElement("div");
				fotoDiv.setAttribute("id","fotodiv");
				fotoDiv.setAttribute("class","fotoDiv");
				while( div.lastChild )
					div.removeChild( div.lastChild );
				var step=array[dayNumber][stepNumber];
				var p=document.createElement("P");
				p.setAttribute("id", "step-title");
				
				p.innerHTML=step.place;
				var i;
				var descr=document.createElement("P");
				descr.setAttribute("class","descr");
				descr.innerHTML=step.descriptionStep;
				
				var prec=document.createElement("P");
				prec.setAttribute("class","descr prec");
				prec.innerHTML=step.precision;
				var img;
				for(i=0;i<step.photo.length;i++){
					img=document.createElement("img");
					img.setAttribute("src","data:image/gif;base64,"+step.photo[i]);
					img.setAttribute("style","width: 8em; height: 8em;");
					fotoDiv.appendChild(img);
				}
				div.appendChild(p);
				div.appendChild(fotoDiv);
				div.appendChild(descr);
				div.appendChild(prec);
			}
	function showFav(idUser){
		jQuery.ajax({
			url: "jsonResponser.jsp",
			dataType: "json",
			data: {"shareable":"true", "userID":idUser},
			type: "GET",
			error:function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(data){
				var dataO=data.shareable;
				var checkBox;
				var label;
				var div;
				var newDiv;
				var img;
				$.each(dataO,function(index,element){
					checkBox=document.createElement("input");
					checkBox.setAttribute("type","checkbox");
					checkBox.setAttribute("id","checkbox"+dataO[index].userId);
					checkBox.setAttribute("value","checkbox"+dataO[index].userId);
					checkBox.setAttribute("name","checkbox"+dataO[index].userId);
					newDiv=document.createElement("div");
					newDiv.setAttribute("class","shareableElements");
					label=document.createElement("label");
					label.setAttribute("for","checkbox"+dataO[index].userId);
					label.innerHTML=dataO[index].user;
					img=document.createElement("img");
					if(dataO[index].image)
						img.setAttribute("src","data:image/*;base64,"+dataO[index].image);
					else
						img.setAttribute("src","resource/travelers.png");
					img.setAttribute("style","width: 2em; height: 2em;");
					newDiv.appendChild(img);
					div=document.getElementById("shareDiv");
					newDiv.appendChild(checkBox);
					newDiv.appendChild(label);
					div.appendChild(newDiv);
				});
				div=document.getElementById("shareDiv");
				div.style.opacity=1;
			}
		});
	}
	function shareTravel(){
		var div=document.getElementById("shareDiv");
		var array=div.childNodes;
		var i;
		var users=new Array();
		var text;
		var j=0;
		var checked = $("input[type=checkbox]:checked");
		for(i=0;i<checked.length;i++){
			text=checked[i].id.split("checkbox");
			users[i]=text[1];
		}
		
		jQuery.ajax({
			url:"viewTravel.jsp",
			type: "POST",
			data:{"userToBeShared":users, "travelID":travelId},
			error: function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				alert("Shared!");
			}
		});
	}
	function closeShare(){
		var div=document.getElementById("shareDiv");
		div.style.opacity=0;
		while( div.lastChild )
			div.removeChild( div.lastChild );
		div.innerHTML='<form action="viewTravel.jsp" method="POST" id="formShare"><input type="button" id="condividi" name="share" value="share" onclick="shareTravel()"><input type="button" value="close" onclick="closeShare()"></form>'
	}
	function addFav(){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"fav":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
			var btn=document.getElementById("fav");
			if(btn.className==="bb-button"){
				btn.className+=" select";
			}
			else{
				btn.className="bb-button";
			}
			}
		});
	}
	function goChat(userId){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"chat":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				document.location.href="http://localhost:8080/TravelbookISPW/chat.jsp";
			}
		});
	}
	function goProfile(userId){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"profile":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				document.location.href="http://localhost:8080/TravelbookISPW/profileOther.jsp?user="+userId;
			}
		})
	}
	function loadMap(){
		var i;
		var arg = new Array();
		var c = 0;
		var j;
		var step;
		for (i = 0; i < array.length; i++) {
			for (j = 0; j < array[i].length; j++) {
				step = array[i][j];
				arg[c] = { "groupDay": step.groupDay, "numberInDay": step.numberInDay, "description": step.descriptionStep, "precision": step.precision, "photo": step.photo, "place": step.place };
				c++;
			}
		}
		var places = { "places": arg };
		jQuery.ajax({
			url: "add.jsp",
			data: { "forward": JSON.stringify(places) },
			type: "POST",
			error: function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.responseText);
				alert(xhr.status);
				alert(thrownError);
			},
			success: function(data) {
				window.open("http://localhost:8080/TravelbookISPW/map.jsp");
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
        <div class="panel panel2">
            <button type="button" id="back-button" class="back-button back2" onclick="goBack()"><span class="material-icons md-48">arrow_back</span></button></button>
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