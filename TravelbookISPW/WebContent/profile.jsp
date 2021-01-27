<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="loginCss.css">
    <link rel="stylesheet" href="profile.css">
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
            
            <div class="profile-panel">
                <img src="resource/travelers.png" style="width: 12.5em; height: 12.5em;" class="image">
                <div class="v">
                    <p class="us">
                        Username
                    </p>
                    <p class="us ds">
                        description
                    </p>
                </div>
            </div>
            <div class="menu-bar" id="p-menubar">
                <input type="button" class="button p-button" name="profile", value="PROFILE">
                <input type="button" class="button" name="add" value="ADD">
                <input type="button" class="button" name="explore" value="EXPLORE">
                <input type="button" class="button" name="chat" value="CHAT">
            </div>
            <div id="bottom">
                <div id="l-bottom">
                    <input type="button" class="profile-button" value="Follower">
                    <input type="button" class="profile-button" value="Following">
                    <input type="button" class="profile-button fav-button">
                    <p class="text">
                        Your favourite travels
                    </p>
                </div>
                <div class="map">
                    <p class=text>
                        You have visited 10 cities
                    </p>
                </div>
            </div>
        </div>
        <div class="panel" id="right-panel">

        </div>
    </div>
</body>