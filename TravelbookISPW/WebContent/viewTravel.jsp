<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.TravelBean" %>
<%@ page import="main.java.travelbook.controller.TravelController" %>
<%@ page import="main.java.travelbook.model.bean.StepBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%
	UserBean myUser=(UserBean)request.getSession().getAttribute("loggedBean");
	System.out.println(request.getParameterMap().keySet());
	Integer id=Integer.valueOf(request.getParameter("travelID"));
	TravelController controller=new TravelController();
	TravelBean myTravel=controller.getTravel(id);
	if(request.getParameter("profile")!=null){
		%>
			<jsp:forward page="profileOther.jsp">
			<jsp:param name="id" value="<%=myTravel.getIdCreator()%>"/>
			</jsp:forward>
		<% 
	}
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
		System.out.println(shareable);
		String[] users=shareable.split(",");
		List<Integer> ids=new ArrayList<>();
		for(String s: users){
			ids.add(Integer.valueOf(s));
		}
		controller.shareTravel(ids, id, myTravel.getIdCreator(), myUser.getId());
	}

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/viewTravel.css">
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
				/*get description(){
					return this.descriptionStep;
				}
				get place(){
					return this.place;
				}
				get precision(){
					return this.precision;
				}
				get photo(){
					return this.photo;
				}
				set photo(array){
					this.photo=array;
				}*/
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
					tablinks[i].className.replace("active","");
				}
				document.getElementById("day"+dayNumber).style.display="block";
				event.currentTarget.className+=" active";
			}
			function openStep(event,dayNumber,stepNumber){
				var div=document.getElementById("right-panel");
				
				var fotoDiv=document.createElement("div");
				fotoDiv.setAttribute("id","fotodiv");
				fotoDiv.setAttribute("class","fotoDiv");
				while( div.lastChild )
					div.removeChild( div.lastChild );
				var step=array[dayNumber][stepNumber];
				var i;
				var desc=document.createTextNode(step.descriptionStep);
				
				var prec=document.createTextNode(step.precision);
				var img;
				for(i=0;i<step.photo.length;i++){
					img=document.createElement("img");
					img.setAttribute("src","data:image/gif;base64,"+step.photo[i]);
					img.setAttribute("style","width: 5em; height: 5em;");
					fotoDiv.appendChild(img);
				}
				div.appendChild(fotoDiv);
				div.appendChild(desc);
				div.appendChild(prec);
			}
	function showFav(idUser){
		jQuery.ajax({
			url: "jsonResponser.jsp",
			dataType: "json",
			data: {"shareable":"true"},
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
				//ADD HERE CHANGE BUTTON COLOR
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
            <input type="button" id="back-button" class="back-button back2" onclick="goBack()">
            <div class="top">
                
                <div id="travel">
                <%
                	byte[] travB=myTravel.getArray();
            		byte[] bytes=Base64.getEncoder().encode(travB);
					String encoded=new String(bytes,"UTF-8");
                %>
				<img src="data:image/*;base64,<%=encoded%>" id="background" style="width: 12.5em; height: 12.5em;"/>
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
                	<form action="viewTravel.jsp" method="POST">
                    <input type="button" id="profile" name="profile" class="bb-button" value="goProfile(<%=myUser.getId()%>)">
                    <input type="button" id="chat" name="chat" class="bb-button" value="chat" onclick="goChat(<%=myUser.getId()%>)">
                    <input type="button" id="fav" name="fav" class="bb-button" value="fav" onclick="addFav()">
                    <input type="button" id="share" class="bb-button" onclick="showFav(<%=myUser.getId()%>)" name="shareButton">
                  
                    </form>
                </div>
               
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