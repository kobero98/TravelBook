<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="exception.*" %>
<%@ page import="main.java.travelbook.model.bean.*" %>
<%@ page import="main.java.travelbook.controller.*" %>
<%@ page import="java.util.*" %>
<%	List<MiniTravelBean> m=null;
	if(request.getParameter("search-button")!=null){
		
			int costoMin=0;
			int costoMax=0;
			int min=0;
			int max=0;
			if(request.getParameter("search")!=null){
				String city=(String) request.getParameter("search");
				
				if(request.getParameter("costo")!=null){
					String c=(String) request.getParameter("costo");
					if(c.equals("0-300")){
						costoMax=300;
					}
					if(c.equals("300-1000")){
						costoMin=300;
						costoMax=1000;
					}
					if(c.equals("1000-2000")){
						costoMin=1000;
						costoMax=2000;
					}
					if(c.equals(">2000")){
						costoMin=2000;
					}
				}
				
				if(!request.getParameter("min").equals(""))
					 min=Integer.parseInt(request.getParameter("min"));
				if(!request.getParameter("max").equals(""))
					 max=Integer.parseInt(request.getParameter("max"));
				String[] t;
				List <String> types=new ArrayList<>();
				if(request.getParameter("type")!=null){
					t=request.getParameterValues("type");
					for(String s:t) types.add(s);
				}
				SearchTrip trip=new SearchTrip();
				trip.setCity(city);
				trip.setCostoMin(costoMin);
				trip.setCostoMax(costoMax);
				trip.setType(types);
				trip.setDurationMin(min);
				trip.setDurationMax(max);
				m=ControllerSearch.getInstance().search(trip);
				
		}
	}
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css\loginCss.css">
    <link rel="stylesheet" href="css\search.css">
   
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
    <script src="js\jquery.min.js"></script> 
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    
	<title>Travelbook</title>
	<script> 
    $(function()
            {
             $('#search').autocomplete(
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
                 data:{search:request.term},
                 success:function(data)
                 { 
                	 console.log(data);
                	 response(data);
                 },
                 select: function( event, ui ) {
                     log( ui.item ?
                       "Selected: " + ui.item.label :
                       "Nothing selected, input was " + this.value);
                   },
                   open: function() {
                     $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
                   },
                   close: function() {
                     $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
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
					$("#advancedSearch").show();
					soprasotto=1;
				}
			else{

				$("#advancedSearch").hide();
				soprasotto=0;
			}
		}
	</script>
</head>
<body>
    <div class="header">
        <p class="title">Travelbook</p>
        <p class="subtitle">
            Wherever you go, go with all your heart
        </p>
    </div>
    <form  action="search.jsp" method="POST">
    <div class="search-bar ui-widget" >
        <input type="submit" name="search-button" id=search-button>
        <input type="text" name="search" value="" id=search class="textfield">
    </div>
     <div class="advanced-search">
                <input type="button" onclick="spostamento()" id="expand">
                <p id=test class="as-text">
                    advanced search
                </p>
            </div>
    <div id=advancedSearch  hidden="true">
    <div id=advanced-style>
    	<div id=cost>
    		<p class="t">Your budget</p>
    		<input type="radio" id=test value="0-300" name="costo" >0-300<br>
    		<input type="radio" value="300-1000" name="costo" >300-1000<br>
    		<input type="radio" value="1000-2000" name="costo" >1000-2000<br>
    		<input type="radio" value=">2000" name="costo" >>2000<br>
    	</div>
    	<div id=durat>
    		<p class="t">How many days?</p>
    		<input type="number" min=0 value="durationMin" name="min" >
    		<input type="number" min=0 value="durationMax" name="max" >
    	</div>
    	<div id=type>
    		<p class="t"> What do you fancy?</p>
    		<div>
    			<input type="checkbox" name="type" value="Romantic Trip">Romantic Trip<br>
    			<input type="checkbox" name="type" value="Family Holiday">Family Holiday<br>
    			<input type="checkbox" name="type" value="On The Road">On The Road<br>
    			<input type="checkbox" name="type" value="Children Friendly">Children Friendly<br>
    			<input type="checkbox" name="type" value="Travel with Friend">Travel with Friend<br>
    			<input type="checkbox" name="type" value="Cultural Travel">Cultural Travel<br>
    			<input type="checkbox" name="type" value="Relaxing Holiday">Relaxing Holiday<br>
    		</div>
    	</div>
    	</div>
    </div>
    </form>
    <div id=d class="anchor">
        <input type="button" id="back" name="back" value="back" onclick="tornaIndietro()" class="back-button">
        <div class="panel l-panel">
           
            <div id=found class="found">
                <p id=para class="write">
                    This is what we have found
                </p>
                <%
                	if(m!=null){
                		int i=0;
                		for(MiniTravelBean trip:m)
                		{	
                			String buttonName="travel"+trip.getId();
	    					byte[] bytes=Base64.getEncoder().encode(trip.getArray());
	    					String encoded=new String(bytes,"UTF-8");
	    					String path="data:image/gif;base64,"+bytes;
	    					out.println(bytes);
                			%>
                			<div id=<%=i %> class="travel-tile">
								<img id="travImg"src="data:image/*;base64,<%=encoded%>" style="width: 12.5em; height: 12.5em;" class="travel-tile-photo" alt="travel picture"/>
								<div>
									<form action="profile.jsp" method="POST">
										<p class=text>
									 	<%=trip.getNameTravel() %>
										 <%=trip.getDescriptionTravel() %>
										 </p>
										 <input type="submit" name=<%=buttonName %> id=<%=trip.getId() %>/>
									</form>
								</div>
							</div>
                			
                			<%
                			i++;
                		}
                	}
                %>
            </div>
        </div>
        
            <img src="resource/Search-image.png" alt="quote" />
       
    </div>
     