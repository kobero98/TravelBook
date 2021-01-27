<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" 

%>
<%@ page import="main.java.travelbook.controller.ViewOnMap"
%>
<%@ page import="main.java.travelbook.model.bean.StepBean"
 %>
 <%@ page import="java.util.ArrayList"
  %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1" />
<title>Display a map</title>
<meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no" />
<script src="https://api.mapbox.com/mapbox.js/v3.2.1/mapbox.js"></script>
<link href="https://api.mapbox.com/mapbox.js/v3.2.1/mapbox.css" rel="stylesheet" />
<style>
	body { margin: 0; padding: 0; }
	#map { position: absolute; top: 0; bottom: 0; width: 100%; }
</style>
<script>
var map;
var polyline;
function init(token){
    map=null;
    polyline=null;
    L.mapbox.accessToken=token;
    map= L.mapbox.map('map').setView([41.89306,12.48278],10);
    L.mapbox.styleLayer('mapbox://styles/mapbox/streets-v11').addTo(map);
    <%
	List<StepBean> list=new ArrayList<>();
    list=request.getParameter("travel").getListStep();
	/*StepBean step=new StepBean();
	step.setPlace("Roma, Rome, Italy");
	list.add(step);*/
	List<String> scripts=ViewOnMap.getIstance().loadTravel(list);
	for(String script: scripts){
		StringBuilder newS=new StringBuilder();
		newS.append("'");
		newS.append(script);
		newS.append("'");
%>
	eval(<%=newS%>);
<%
	}
%>  
}
function addMarker(coordinates,description,icon,start,imgUrl){
	var color;
	if(start){
		color="#FF0000";
	}
	else{
		color="#0000FF";
	}
	if(icon=="null"){
		icon="marker";
	}
	var marker=L.marker(coordinates, {
		icon: L.mapbox.marker.icon({
			'marker-size': 'large',
	        'marker-symbol': icon,
	        'marker-color': color
		})
	}).addTo(map);
	
	marker.bindPopup(description,{closeOnClick: false});
	marker.on('click',function(e){
		marker.openPopup();
	});
	
}
function drawPath(points){
	//Points must be an array of LatLng elements
	var polyline=L.polyline(points, {color: 'blue', lineJoin: 'arcs'}).addTo(map);
	polyline.redraw();
	//set map view on the path beautiful.
	map.fitBounds(polyline.getBounds());
	//map.setZoom(11,doubleClickZoom=false);
}

	
</script>
</head>
<body onload="init('pk.eyJ1IjoiZGVtYWdvZ28iLCJhIjoiY2tqMWJybjZtMGpjbzMwbWh6bWt1MHcycyJ9.f9zugEhda3-7YFxBwutEnA');">
<h1>Test mapbox</h1>
<div id="map"></div>

 
</body>
</html>