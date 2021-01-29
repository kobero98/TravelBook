<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\search.css">
    <script src="js\jquery.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    
	<title>Travelbook</title>
	<script> 
    $(function()
            {
             $('#auto').autocomplete(
             {
             source:function(request,response)
             {
             //Fetch data
             $.ajax({
                 url:"autocomplete.jsp",
                 method:"get",
                 dataType:'json',
                 data:{search:request.term},
                 success:function(data)
                 {
                     response(data);
                 }
             });
             }
             });   
            }); 
		var soprasotto=0;
		function tornaIndietro()
		{
			  location.replace("explore.jsp");
		}
		function spostamento()
		{
			if(soprasotto==0)
				{
					console.log("ciao");
					$("#advancedSearch").show();
					soprasotto=1;
				}
			else{

				console.log(soprasotto);
				$("#advancedSearch").hide();
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
    <div class="search-bar ui-widget" >
        <input type="button" name="search-button" id=search-button>
        <input type="search" name="search" id=search class="textfield">
    </div>
    <div id=advancedSearch  hidden="true">
    	<div id=cost>
    		<p>Your budget</p>
    		<input type="radio" value="<300" name="costo" >
    		<input type="radio" value="300-1000" name="costo" >
    		<input type="radio" value="1000-2000" name="costo" >
    		<input type="radio" value=">2000" name="costo" >
    	</div>
    	<div id=durat>
    		<p>How many days?
    		<input type="number" min=0 value="costoMin" name="min" >
    		<input type="number" min=0 value="costoMax" name="max" >
    	</div>
    	<div id=type>
    		<p> What do you fancy?
    		<div>
    			<span class="dot"></span><p>Romantic Trip<br>
    			<span class="dot"></span><p>Family Holiday<br>
    			<span class="dot"></span><p>On The Road<br>
    			<span class="dot"></span><p>Children Friendly<br>
    			<span class="dot"></span><p>Travel with Friend<br>
    			<span class="dot"></span><p>Cultural Travel<br>
    			<span class="dot"></span><p>Relaxing Holiday<br>
    		</div>
    		<div>
    			
    		</div>
    		<div hidden=true>
    		</div>
    	</div>
    </div>
    <div id=d class="anchor">
        <input type="button" id="back" name="back" value="back" onclick="tornaIndietro()" class="back-button">
        <div class="panel l-panel">
            <div>
                <input type="button" onclick="spostamento()" id="expand">
                <p class="as-text">
                    advanced search
                </p>
            </div>
            <div id=found class="found">
                <p id=para class="write">
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
     