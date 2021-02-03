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
<%
	TravelBean myTravel=null;
	UserBean loggedUser=(UserBean)request.getSession().getAttribute("loggedBean");
	if(request.getParameter("POSTTRAVEL")!=null){
		System.out.println(request.getParameterMap().keySet());
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject)parser.parse(request.getParameter("POSTTRAVEL"));
		System.out.println(obj);
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
		AddTravel.getIstance().saveTravel(travel,loggedUser.getId());
	}
	if(request.getParameter("modifyTravel")!=null){
		myTravel=AddTravel.getIstance().getTravelById(Integer.valueOf(request.getParameter("modifyTravel")));
	}
	if(request.getParameter("forward")!=null){
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject)parser.parse(request.getParameter("forward"));
		JSONArray array=(JSONArray)obj.get("places");
		List<StepBean> steps=new ArrayList<>();
		for(int i=0;i<array.size();i++){
			JSONObject step=(JSONObject)array.get(i);
			StepBean stepBean=new StepBean();
			stepBean.setNumberInDay(Integer.valueOf(step.get("numberInDay").toString()));
			stepBean.setGroupDay(Integer.valueOf(step.get("groupDay").toString()));
			stepBean.setPrecisionInformation(step.get("precision").toString());
			stepBean.setDescriptionStep(step.get("description").toString());
			stepBean.setPlace(step.get("place").toString());
			steps.add(stepBean);
		}
		TravelBean travel=new TravelBean();
		travel.setListStep(steps);
		request.getSession().setAttribute("travelOnMap",travel);
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/add.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <script src="js/addJS.js"></script>
    <script src="js/jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<title>Travelbook</title>
<script>
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
    <div class="anchor">
         
        <div class="panel">
            <div class="menu-bar">
            <form action="explore.jsp" method="POST">
                <input type="submit" class="button" name="profile" value="PROFILE">
                <input type="submit" class="button p-button" name="add" value="ADD">
                <input type="submit" class="button" name="explore" value="EXPLORE">
                <input type="submit" class="button" name="chat" value="CHAT">
                </form>
            </div>
            <p class=write>
                Hi, so glad you decided to share your travels
            </p>
            <form id="add-travel" class="add-travel">
                <div class="line">
                    <p class="text">
                        Give us a name for your travel:
                    </p>
                    <input type="text" name="name" class="add-text" id="travelName" onchange="travelNameListener()">
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
                    <textarea class="add-text area" id="descr" wrap="hard" onchange="travelDescriptionListener()"></textarea>
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
                        <input type="button" name="saveButton" class="add-button" value="Save as draft">  
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
                    <input type="button" class="vom" onclick="apriMappa()">

                </div>
                <div class="line">  
                	 
                    <div id="steps">
                    </div>
                    <input type="button" name="remove" class="icon-button" onclick="removeButton()">
                    <input type="button" name="add" class="icon-button" onclick="addButton()">
                </div>
                <div class="line">  
                    <p class="text">
                        Add a description:
                    </p>
                    <textarea class="add-text area" id="step-descr" wrap="hard" onchange="descriptionListener()"></textarea>
                </div>
                <div class="line">   
                    <p class="text">
                        Do you want to give some pratical information?
                    </p>
                    <textarea class="add-text area" id="step-inf" wrap="hard" onchange="precisionListener()"></textarea>
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
        </body>
        </html>