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
            <input type="button", id="back", class="back-button">
            <div class="profile-panel other-panel">
                
                <img src="resource/travelers.png", style="width: 12.5em; height: 12.5em;" class="image">
                <div class="v">
                    <p class="us">
                        Username
                    </p>
                    <p class="us ds">
                        description
                    </p>
                </div>
            </div>
            <div id="bottom">
                <div id="l-bottom">
                    <input type="button" class="profile-button" value="Follower">
                    <input type="button" class="profile-button" value="Following">
                    <input type="button" class="profile-button fav-button">
                    <p class="text">
                        Username favourite travels
                    </p>
                </div>
                <div class="map">
                    <p class=text>
                        Username has visited 10 cities
                    </p>
                </div>
            </div>
        </div>
        <div class="panel" id="right-panel">

        </div>
    </div>
</body>