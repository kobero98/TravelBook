<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\search.css">
    <script src="js\jquery.min.js"></script>
	<title>Travelbook</title>
	<script>
		var soprasotto=0;
		function tornaIndietro()
		{
			  location.replace("explore.jsp");
		}
		function spostamento()
		{
			if(soprasotto==0)
				{
					$("#found").animate({height: '+=50em'},"slow");
					soprasotto=1;
				}
			else{
				$("#found").animate({height: '-=50em'},"slow");
				soprasotto=0;
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
    <div class="search-bar">
        <input type="button" name="search-button" id="search-button">
        <input type="search" name="search" id="search" class="textfield">
    </div>
    <div class="anchor">
        <input type="button" id="back" name="back" value="back" onclick="tornaIndietro()" class="back-button">
        <div class="panel l-panel">
            <div class="advanced-search">
                <input type="button" onclick="spostamento()" id="expand">
                <p class="as-text">
                    advanced search
                </p>
            </div>
            <div id="found" class="found">
                <p class="write">
                    This is what we have found
                </p>
            </div>
        </div>
      
        <div class="panel suggestion">
            <p class="write">
                Our suggestions
            </p>
        </div>
    </div>
     