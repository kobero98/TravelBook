<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.TravelBean" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.AddTravel" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="main.java.travelbook.model.bean.StepBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@page errorPage="errorpage.jsp" %>
<%
	request.getSession().setAttribute("selettore",null);
	TravelBean myTravel=null;
	AddTravel myController=new AddTravel();
	UserBean loggedUser=(UserBean)request.getSession().getAttribute("loggedBean");
	System.out.println(request.getParameterMap().keySet().toString());
	if(loggedUser==null){
		%>
			<jsp:forward page="login.jsp"/>
		<% 
	}
	if(request.getParameter("POSTTRAVEL")!=null){
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject)parser.parse(request.getParameter("POSTTRAVEL"));
		TravelBean travel=new TravelBean();
		travel.setNameTravel(obj.get("travelName").toString());
		travel.setDescriptionTravel(obj.get("travelDescription").toString());
		travel.setStartTravelDate(obj.get("dateS").toString());
		travel.setEndTravelDate(obj.get("dateE").toString());
		List<String> types=new ArrayList<>();
		JSONArray array=(JSONArray)obj.get("tipi");
		for(int i=0;i<array.size();i++){
			types.add(array.get(i).toString());
		}
		String s1=(String)obj.get("foto");
		
		if(s1!=null){
			byte[] bytesB641=s1.getBytes();
			byte[] bytes1=Base64.getDecoder().decode(bytesB641);
			travel.setArray(bytes1);
		}
		boolean b=false;
		if(request.getParameter("SHARED")!=null){
			 b=Boolean.valueOf(request.getParameter("SHARED"));
			travel.setShare(b);
		}
		travel.setType(types);
		travel.setListStep(new ArrayList<>());
		StepBean stepBean;
		JSONArray steps=(JSONArray)obj.get("steps");
		for(int i=0;i<steps.size();i++){
			stepBean=new StepBean();
			JSONObject step=(JSONObject)steps.get(i);
			stepBean.setNumberInDay(Integer.valueOf(step.get("numberInDay").toString()));
			stepBean.setGroupDay(Integer.valueOf(step.get("groupDay").toString()));
			stepBean.setPrecisionInformation(step.get("precision").toString());
			stepBean.setDescriptionStep(step.get("description").toString());
			stepBean.setPlace(step.get("place").toString());
			stepBean.setBytes(new ArrayList<>());
			travel.getListStep().add(stepBean);
			JSONArray images=(JSONArray)step.get("photo");
			if(images!=null){
			for(int j=0;j<images.size();j++){
				String s=(String)images.get(j);
				byte[] bytesB64=s.getBytes();
				byte[] bytes=Base64.getDecoder().decode(bytesB64);
				ByteArrayOutputStream is=new ByteArrayOutputStream();
				is.writeBytes(bytes);
				stepBean.getBytes().add(is);
			}
			}
		}
		boolean nuovo=true;
		if(request.getParameter("NEW")!=null){
			if(!request.getParameter("NEW").equals("-1")){
				nuovo=false;
			}
		}
		if(nuovo){
			myController.saveTravel(travel,loggedUser.getId());
		}
		else{
			myController.saveAndDelete(travel, Integer.valueOf(request.getParameter("NEW")), loggedUser.getId());
		}
	}
	if(request.getParameter("modifyTravel")!=null){
		myTravel=myController.getTravelById(Integer.valueOf(request.getParameter("modifyTravel")));
	}
	if(request.getParameter("forward")!=null){
		System.out.println("2HERE");
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject)parser.parse(request.getParameter("forward"));
		JSONArray array=(JSONArray)obj.get("places");
		List<StepBean> steps=new ArrayList<>();
		for(int i=0;i<array.size();i++){
			JSONObject step=(JSONObject)array.get(i);
			StepBean stepBean=new StepBean();
			stepBean.setNumberInDay(Integer.valueOf(step.get("numberInDay").toString()));
			stepBean.setGroupDay(Integer.valueOf(step.get("groupDay").toString()));
			stepBean.setPlace(step.get("place").toString());
			steps.add(stepBean);
		}
		TravelBean travel=new TravelBean();
		travel.setListStep(steps);
		request.getSession().setAttribute("travelOnMap",travel);
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/add.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="js/addJS.js"></script>
    <script src="js/jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.10.2.js" ></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<title>Travelbook</title>
<script>
function goToChat()
{
	  location.replace("chat.jsp");
}
function goToExplore()
{
	  location.replace("explore.jsp");
}
function goToProfile()
{
	  location.replace("profile.jsp");
}

(function NotifyMes() {
    setTimeout(function() {
        $.ajax({
            url: "ChatNewChat.jsp",
            type: "POST",
            dataType: "json",
            error:function(xhr,ajaxOptions,thrownError){
				console.log(xhr.responseText);
				alert(xhr.status);
		         alert(thrownError);
			},
            success: function(data) {
            	 if(data.text!=null){
            		 document.getElementById("chatNotify").innerHTML = "mark_chat_unread";
            		}
            },
            
            complete: NotifyMes,
            timeout: 2000
        })
    }, 5000);
})();
$(function()
        {
         $('#searchPlace').autocomplete(
         {
        	 position:{ my: "left top", at: "left bottom", collision: "none" },
       	 minlength:1,
         source:function(request,response)
         {
        
         //Fetch data
         $.ajax({
             url:"autocomplete.jsp",
             method:"get",
             dataType:"json",
             data:{place:request.term},
             success:function(data)
             { 
            	 console.log(data);
            	 response(data);
             },
             
               open: function() {
                 $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
               },
               close: function() {
                 $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
               }
         });
         },
        	 select: function( event, ui ) {
                 log( ui.item ?
                    ui.item.value:
                   "nothing");
               }
         });   
        }); 
        function log(message){
        	if(message.localeCompare("nothing")==0){
        		 $( this ).value="";
        	}
        	else{
        		arrayStep[actualDay][actualStep].place=message;
        	}
        }
	function init(){
<%
	if(myTravel!=null){
		//ModifyTravelMode
		%>
			notNew=<%=myTravel.getId()%>;
		<%
		List<StepBean> steps=myTravel.getListStep();
		%>
			startDate="<%=myTravel.getStartDate()%>";
			endDate="<%=myTravel.getEndDate()%>";
			compareDate(startDate,endDate);
			arrayStep=new Array();
			document.getElementById("s-date").value=startDate;
			document.getElementById("e-date").value=endDate;
		<%
		for(StepBean step: steps){
		%>
			if(arrayStep[<%=step.getGroupDay()%>]==undefined){
				arrayStep[<%=step.getGroupDay()%>]=new Array();
			}
			arrayStep[<%=step.getGroupDay()%>][<%=step.getNumberInDay()%>]=new StepJS(<%=step.getGroupDay()%>,<%=step.getNumberInDay()%>,"<%=step.getDescriptionStep()%>","<%=step.getPlace()%>","<%=step.getPrecisionInformation()%>");
		<%
			if(step.getArray()!=null){
				String encoded;
				List<byte[]> bytes=step.getArray();
				int i=0;
				for(byte[] bytes1: bytes){
					bytes1=Base64.getEncoder().encode(bytes1);
					encoded=new String(bytes1,"UTF-8");
					%>
						arrayStep[<%=step.getGroupDay()%>][<%=step.getNumberInDay()%>].photo[<%=i%>]=<%=encoded%>;
					<%
					i++;
				}
			}
		}
		if(myTravel.getNameTravel()!=null){
			%>
				travelName="<%=myTravel.getNameTravel()%>";
				document.getElementById("travelName").value=travelName;
			<%
		}
		else{
			%>
				travelName=undefined;
			<%
		}
		if(myTravel.getDescriptionTravel()!=null){
			%>
				travelDescription="<%=myTravel.getDescriptionTravel()%>";
				document.getElementById("descr").value=travelDescription;
			<%
		}
		else{
			%>
				travelDescription=undefined;
			<%
		}
		if(myTravel.getStartDate()!=null && myTravel.getEndDate()!=null){
		%>
			var select=document.getElementById("days");
			select.value=0;
		<%
		}
		byte[] background=myTravel.getArray();
		if(background!=null){
			background=Base64.getEncoder().encode(background);
			String encodedB=new String(background,"UTF-8");
			%>
				var div=document.getElementById("presentation");
				background="<%=encodedB%>";
				var fotoSfondo=document.createElement("img");
				fotoSfondo.setAttribute("src","data:image/gif;base64,"+background);
				fotoSfondo.setAttribute("style","width: 35em; height: 10em;");
				div.appendChild(fotoSfondo);
			<%
		}
	}
%>
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
    <div class="anchor" id="addAnchor">
         
        <div class="panel">
            <div class="menu-bar">
                <button type="button" class="button" name="profile" onclick=goToProfile()><span class="material-icons">person</span>PROFILE</button>
                <button type="button" class="button p-button" name="add"><span class="material-icons">edit</span>ADD</button>
                <button type="button" class="button" name="explore" onclick=goToExplore()><span class="material-icons">explore</span>EXPLORE</button>
                <button type="button" class="button" name="chat" onclick=goToChat()><span id=chatNotify class="material-icons">textsms</span>CHAT</button>
            </div>
            <p class=write>
                Hi, so glad you decided to share your travels
            </p>
            <form id="add-travel" class="add-travel">
                <div class="line">
                    <p class="text">
                        Give us a name for your travel:
                    </p>
                    <input type="text" name="name" class="add-text" id="travelName" onchange="travelNameListener()" maxlength="20">
                </div>
                <div class="line">
                    <p class="text">
                        Select your dates:
                    </p>
                    <input type="date" id="s-date" name="s-date" class="date-picker" value="start" onchange="startDateListener()">
                    <input type="date" id="e-date" name="e-date" class="date-picker" value="end" onchange="endDateListener()">
                </div>
                <div class="line">
                    <p class="text">
                        Add a description:
                    </p>
                    <textarea class="add-text area" id="descr" wrap="hard" onchange="travelDescriptionListener()" maxlength="100"></textarea>
                </div>
                <div class="line">    
                    <p class="text">
                        What about the cost?
                    </p>
                    <input id="costTravel" type="text" name="cost" class="add-text">
                </div> 
                <div class="line">   
                    <p class="text">
                        Upload your presentation photo:
                    </p>
                    <button type="button" onclick="removePresentation()" class="icon-button"><span class="material-icons">delete</span></button> 
                    <input type="file" id="presentationFile" name="choose" class="add-button" accept="image/jpg, image/png" onchange="loadImage()">
                </div> 
                  
                <div class="photo-grid" id="presentation">
                    
                </div>
                <p class="text">
                    What type of trip?
                </p>
                <div class="line">
                    <div class="check-box" id="check-box">
                        <input type="checkbox" id="check1" name="check1" value="Romantic Trip" class="check">
                        <label for="check1" class="text"> Romantic Trip</label>
                        <input type="checkbox" id="check2" name="check2" value="Family Holiday" class="check">
                        <label for="check2" class="text"> Family Holiday</label><br>
                        <input type="checkbox" id="check3" name="check3" value="On the road" class="check">
                        <label for="check3" class="text"> On the road</label>
                        <input type="checkbox" id="check4" name="check4" value="Children friendly" class="check">
                        <label for="check4" class="text"> Children friendly</label><br>
                        <input type="checkbox" id="check5" name="check5" value="Travel with friends" class="check">
                        <label for="check5" class="text"> Travel with friends</label>
                        <input type="checkbox" id="check6" name="check6" value="Cultural travel" class="check">
                        <label for="check6" class="text"> Cultural travel</label><br>    
                        <input type="checkbox" id="check7" name="check7" value="Relaxing Holiday" class="check">
                        <label for="check7" class="text"> Relaxing Holiday</label><br>
                    </div>
                    <div class="submit">    
                        <input type="button" name="saveButton" class="add-button" value="Save as draft" onclick="save()">  
                        <input type="button" name="postButton" class="add-button" value="All done! Post" onclick="post()"> 
                    </div>    
                </div>                              
            </form>
        </div>
        <div class="panel" id="stepPanel" style="opacity:0;">
            <form id="add-step" class="add-travel">
                <p class="write">
                    Now give us something more specific
                </p>
                <div class="line">
                    <p class="text">
                        You are editing day:
                    </p>

                    <select name="days" id="days" onchange="changeDay()">
                    </select>
                </div>  
                <div class="line">  
                    <p class="text">
                        Select your stops:
                    </p>
                    <div>
                    <input type="text" name="searchPlace" id="searchPlace" class="ui-widget" /> 
                    </div>
                    <input type="button" class="vom" value="View on map" onclick="apriMappa()">

                </div>
                <div class="line">  
                	 
                    <div id="steps">
                    </div>
                    <button type="button" name="remove" class="icon-button" onclick="removeButton()"><span class="material-icons">remove</span></button> 
                    <button type="button" name="add" class="icon-button" onclick="addButton()"><span class="material-icons">add</span></button>
                </div>
                <div class="line">  
                    <p class="text">
                        Add a description:
                    </p>
                    <textarea class="add-text area" id="step-descr" wrap="hard" onchange="descriptionListener()" maxlength="300"></textarea>
                </div>
                <div class="line">   
                    <p class="text">
                        Do you want to give some pratical information?
                    </p>
                    <textarea class="add-text area" id="step-inf" wrap="hard" onchange="precisionListener()" maxlength="500"></textarea>
                </div>
                <div class="line"> 
                    <p class="text">
                        Upload some photos:
                    </p>
                    <input type="file" name="img-choose" id="img-choose" class="add-button"  accept="image/jpg, image/png" onchange="loadMultipleImage()" multiple>
                    <progress>0%</progress>
                </div>
                <div class="photo-grid" id="photo-grid">
                
                </div>
            </form>
        </div>
        
        </div>
        <div id="fotoingrande">
        	<img src="" id="immagineSelezionata" alt="immagineSelezionata" style="width:20em;heigth:20em;"/>
        	<button type="button"  class="icon-button" id="closeImage" onclick="closeImg()"><span class="material-icons">clear</span></button>
        	<button type="button" class="icon-button" id="removeImage" onclick="removeImg()"><span class="material-icons">delete</span></button>
        </div>
        <div id="progressPane">
        	<div id="progressValue">
        	</div>
        	<button id=closeProgress type="button" class="icon-button"><span class="material-icons">clear</span></button>
        </div>
        </body>
        </html>