<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.java.travelbook.model.bean.TravelBean" %>
<%@ page import="main.java.travelbook.model.bean.UserBean" %>
<%@ page import="main.java.travelbook.controller.AddTravel" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/loginCss.css">
    <link rel="stylesheet" href="css/add.css">
    <script src="js/addJS.js"></script>
	<title>Travelbook</title>
	

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
            <div class="menu-bar">
                <input type="button" class="button" name="profile" value="PROFILE">
                <input type="button" class="button p-button" name="add" value="ADD">
                <input type="button" class="button" name="explore" value="EXPLORE">
                <input type="button" class="button" name="chat" value="CHAT">
            </div>
            <p class=write>
                Hi, so glad you decided to share your travels
            </p>
            <form id="add-travel" class="add-travel">
                <div class="line">
                    <p class="text">
                        Give us a name for your travel:
                    </p>
                    <input type="text" name="name" class="add-text">
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
                    <textarea class="add-text area" id="descr" wrap="hard"></textarea>
                </div>
                <div class="line">    
                    <p class="text">
                        What about the cost?
                    </p>
                    <input type="text" name="cost" class="add-text">
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
                    <div class="check-box">
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
                        <input type="submit" name="save" class="add-button" value="Save as draft">  
                        <input type="submit" name="post" class="add-button" value="All done! Post"> 
                    </div>    
                </div>                              
            </form>
        </div>
        <div class="panel">
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
                    <input type="text" name="searchPlace" id="searchPlace"/> 
                    <a href="map.jsp" class="vom">View on maps</a>

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
                    <input type="file" name="img-choose" id="img-choose" class="add-button"  accept="image/jpg, image/png" onchange="loadMultipleImage()">
                    <progress>0%</progress>
                </div>
                <div class="photo-grid" id="photo-grid">
                
                </div>
            </form>
        </div>