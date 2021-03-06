/**
 * 
 */
class StepJS {
	constructor(groupDay, numberInDay, descriptionStep, place, precision) {
		this.groupDay = groupDay;
		this.numberInDay = numberInDay;
		this.descriptionStep = descriptionStep;
		this.place = place;
		this.precision = precision;
		this.photo = new Array();
	}
	isComplete() {
		return (this.descriptionStep != undefined && this.place != undefined && this.precision != undefined);
	}
}
var notNew=-1;
var travelName;
var travelDescription;
var startDate;
var endDate;
var dayNumber = 0;
var arrayStep = new Array();
var actualDay = 0;
var actualStep;
var background;
function startDateListener() {
	var date = document.getElementById("s-date").value;
	if (compareDate(date, endDate)) {
		startDate = date;
	}
	else {
		document.getElementById("s-date").nodeValue = startDate;
	}
}
function endDateListener() {
	var date = document.getElementById("e-date").value;
	if (compareDate(startDate, date)) {
		endDate = date;
	}
	else {
		document.getElementById("e-date").nodeValue = endDate;
	}
}
function setStep(evt) {
	var steps = document.getElementsByClassName("stepButton");
	for(let value of steps){
		value.className="stepButton";
	}

	
	actualStep = evt.currentTarget.id.split(";")[1] - 1;
	evt.currentTarget.className+=" p-step";
	var desc = document.getElementById("step-descr");
	var inf = document.getElementById("step-inf");
	var divFoto = document.getElementById("photo-grid");
	var step = arrayStep[actualDay][actualStep];
	var place = document.getElementById("searchPlace");
	if (step.place != undefined && step.place.length > 0)
		place.value = step.place;
	else
		place.value = "";
	if (step.descriptionStep != undefined && step.descriptionStep.length > 0)
		desc.value = step.descriptionStep;
	else
		desc.value = "";
	if (step.precision != undefined && step.precision.length > 0)
		inf.value = step.precision;
	else
		inf.value = "";
	while (divFoto.lastChild)
		divFoto.removeChild(divFoto.lastChild);
	var arrayFoto = step.photo;
	var i;
	for (i = 0; i < arrayFoto.length; i++) {
		var img = document.createElement("img");
		img.setAttribute("src", "data:image/gif;base64," + arrayFoto[i]);
		img.setAttribute("style", "width: 5em; height: 5em;");
		divFoto.appendChild(img);
	}
}
function descriptionListener() {
	var desc = document.getElementById("step-descr");
	var text = desc.value;
	arrayStep[actualDay][actualStep].descriptionStep = text;
}
function precisionListener() {
	var prec = document.getElementById("step-inf");
	var text = prec.value;
	arrayStep[actualDay][actualStep].precision = text;
}

function fileLoaded(frEvnt) {
	var sFBody = frEvnt.target.result;
	var sBodyBase64 = btoa(sFBody);
	var div = document.getElementById("presentation");
	while (div.lastChild)
		div.removeChild(div.lastChild);
	var img = document.createElement("img");
	img.setAttribute("id","backgroundImage");
	img.setAttribute("src", "data:image/gif;base64," + sBodyBase64);
	img.setAttribute("style", "width: 35em; height: 10em;");
	div.appendChild(img);
	background = sBodyBase64;
}
function loadImage() {
	var oFile = document.getElementById("presentationFile").files[0];
	if (oFile) {
		var fReader = new FileReader();
		fReader.onload = fileLoaded;
		fReader.readAsBinaryString(oFile);
	}
}

function fileLoadedStep(frEvnt) {

	var sFBody = frEvnt.target.result;
	var sBodyBase64 = btoa(sFBody);
	var div = document.getElementById("photo-grid");
	var img = document.createElement("img");
	img.setAttribute("src", "data:image/gif;base64," + sBodyBase64);
	img.setAttribute("onclick", "openImg(" + arrayStep[actualDay][actualStep].photo.length + ")");
	img.setAttribute("style", "width: 10em; height: 10em;");
	div.appendChild(img);
	arrayStep[actualDay][actualStep].photo[arrayStep[actualDay][actualStep].photo.length] = sBodyBase64;
}
function loadMultipleImage() {

	var arrayFile = document.getElementById("img-choose").files;
	var i;
	for (i = 0; i < arrayFile.length; i++) {
		var fReader = new FileReader();
		fReader.onload = fileLoadedStep;
		fReader.readAsBinaryString(arrayFile[i]);
	}
}
function addButton() {
	arrayStep[actualDay][arrayStep[actualDay].length] = new StepJS(actualDay, arrayStep[actualDay].length);
	var btn = document.createElement("button");
	btn.setAttribute("class", "stepButton");
	btn.setAttribute("type", "button");
	btn.setAttribute("onclick", "setStep(event)");
	btn.setAttribute("id", actualDay + ";" + arrayStep[actualDay].length);
	var icon = document.createElement("i");
	icon.setAttribute("class", "material-icons md-48");
	icon.innerHTML = "place";
	var div1 = document.getElementById("steps");
	btn.appendChild(icon);
	div1.appendChild(btn);
	btn.click();
	return btn;
}
function removeButton() {
	arrayStep[actualDay].splice(actualStep, 1);
	var j;
	for (j = actualStep; j < arrayStep[actualDay].length; j++) {
		var button = document.getElementById(actualDay + ";" + (j + 2));
		if (button != undefined)
			button.id = actualDay + ";" + j;
		arrayStep[actualDay][j].numberInDay = arrayStep[actualDay][j].numberInDay - 1;
	}
	var div = document.getElementById("steps");
	var btn = document.getElementById(actualDay + ";" + (actualStep + 1));
	div.removeChild(btn);
	btn=document.getElementById(actualDay+";"+(actualStep));
	if(btn!=undefined)
		btn.click();
	else{
		btn=addButton();
		btn.click();
		}
}
function changeDay() {
	var select = document.getElementById("days");
	actualDay = select.value;
	var div = document.getElementById("steps");
	while (div.lastChild)
		div.removeChild(div.lastChild);
	var i;
	for (i = 0; i < arrayStep[actualDay].length; i++) {
		var btn = document.createElement("button");
		btn.setAttribute("class", "stepButton");
		btn.setAttribute("type", "button");
		btn.setAttribute("onclick", "setStep(event)");
		btn.setAttribute("id", actualDay + ";" + (i + 1));
		var icon = document.createElement("i");
		icon.setAttribute("class", "material-icons md-48");
		icon.innerHTML = "place";
		div = document.getElementById("steps");
		btn.appendChild(icon);
		div.appendChild(btn);
	}
	btn = document.getElementById(actualDay + ";" + (1));
	if (btn != null)
		btn.click();
}
function changeDayNumber(num) {
	if (num >= dayNumber) {
		var x;
		for (x = dayNumber; x < num; x++) {
			arrayStep[x] = new Array();
			actualDay = x;
			choice = document.createElement("option");
			choice.value = x;
			choice.appendChild(document.createTextNode(x + 1));
			var select = document.getElementById("days");
			select.appendChild(choice);
			select.value = x;
			changeDay();
			addButton();
		}
		dayNumber = num;
		select.value = 0;
		changeDay();
	}
	else {
		select = document.getElementById("days");
		var nodes=select.childNodes;
		var limit=nodes.length;
		for(x=0;x<limit;x++){
			select.removeChild(nodes[0]);
		}
		for(x=0;x<num;x++){
			var choice=document.createElement("option");
			choice.value=x;
			choice.appendChild(document.createTextNode(x+1));
			
			select.appendChild(choice);
		}
		arrayStep.length = num;
		dayNumber = num;
		actualDay = num;
		select.value=0;
		changeDay();
	}
}
function compareDate(start, end) {
	if (start == undefined || end == undefined)
		return true;
	var array1 = start.split("-");
	var array2 = end.split("-");
	var data1 = new Date(array1[0], array1[1], array1[2]);
	var data2 = new Date(array2[0], array2[1], array2[2]);
	if (data1.getTime <= data2.getTime) {
		//Devo passare da millisecondi a giorni quindi divido per quel bestione
		document.getElementById("stepPanel").style = "opacity:1;"
		changeDayNumber((data2.getTime() - data1.getTime()) / 86400000 + 1);
		return true;
	}
	return false;
}
function travelNameListener() {
	document.getElementById("travelName").className = "add-text";
	travelName = document.getElementById("travelName").value;
}
function travelDescriptionListener() {
	travelDescription = document.getElementById("descr").value;
}
function onlyNumber(string) {

	if (! /^[0-9]+$/.test(string)) {
		return false;
	}
	return true;
}
function removeImg() {
	var src = document.getElementById("immagineSelezionata").src;
	$("#fotoingrande").animate({ opacity: '0' }, "slow");
	$("#addAnchor").animate({ opacity: '1' }, "slow");
	var images = document.getElementById("photo-grid").childNodes;
	var i;
	for (i = 0; i < images.length; i++) {
		if (images[i].src.localeCompare(src) == 0) {
			document.getElementById("photo-grid").removeChild(images[i]);
			arrayStep[actualDay][actualStep].photo.splice(i,1);
			break;
		}
	}
}
function openImg(s) {
	var img = document.getElementById("immagineSelezionata");
	var src = arrayStep[actualDay][actualStep].photo[s];
	img.setAttribute("src", "data:image/gif;base64," + src);
	alert("DONE");
	$("#fotoingrande").animate({ opacity: '0.9' }, "slow");
	$("#addAnchor").animate({ opacity: '0.1' }, "slow");
}
function closeImg() {
	$("#fotoingrande").animate({ opacity: '0' }, "slow");
	$("#addAnchor").animate({ opacity: '1' }, "slow");
	document.getElementById("immagineSelezionata").src="";
}
function analizzaElementi(cost,elements,actual){
		if (travelName == undefined || travelName == "") {
		elements[actual] = document.getElementById("travelName");
		actual++;
	}
	if (travelDescription == undefined || travelDescription == "") {
		elements[actual] = document.getElementById("descr");
		actual++;
	}
	if (background == undefined) {
		elements[actual] = document.getElementById("presentation");
		actual++;
	}
	if (startDate == undefined) {
		elements[actual] = document.getElementById("s-date");
		actual++;
	}
	if (endDate == undefined) {
		elements[actual] = document.getElementById("e-date");
		actual++;
	}
	if (cost == undefined || !onlyNumber(cost)) {
		elements[actual] = document.getElementById("costTravel");

	}
}
function analizzaStep(steps){
		var j;
		var i;
		var step;
		var stepJSON;
		var actualElem = 0;
		for (i = 0; i < arrayStep.length; i++) {
		for (j = 0; j < arrayStep[i].length; j++) {
			step = arrayStep[i][j];
			if (step.isComplete()) {
				stepJSON = { "groupDay": step.groupDay, "numberInDay": step.numberInDay, "description": step.descriptionStep, "precision": step.precision, "photo": step.photo, "place": step.place }
				steps[actualElem] = stepJSON;
				actualElem++;
			}
			else {
				alert("STEP INCOMPLETI IMPOSSIBILE PROCEDERE");
				return;
			}
		}
	}
}
function post(blocked = true) {
	var cost = document.getElementById("costTravel").value;
	var requestJSON;
	var elements = new Array();
	var actual = 0;
	var types = new Array();
	var checked = $("input[type=checkbox]:checked");
	var i;
	for (i = 0; i < checked.length; i++) {
		types[i] = checked[i].value;
	}
	if (types.length == 0) {
		elements[actual] = document.getElementById("check-box");
		actual++;
	}
	analizzaElementi(cost,elements,actual);

	var steps = new Array();
	
	analizzaStep(steps);
	if (elements.length == 0 || !blocked) {
		requestJSON = { "travelName": travelName, "travelDescription": travelDescription, "tipi": types, "foto": background, "dateS": startDate, "dateE": endDate, "steps": steps };
		openProgress();
		jQuery.ajax({
			url: "add.jsp",
			type: "POST",
			data: { "POSTTRAVEL": JSON.stringify(requestJSON), "SHARED": blocked, "NEW": notNew },
			error: function(xhr, thrownError) {
				alert(xhr.status);
				alert(thrownError);
			},
			success: function() {
				//STOP ALLA PROGRESS BAR
				handleProgress(true);
				alert("VIAGGIO CARICATO");
			}
		});
	}
	else {
		//DAI BORDO ROSSO A TUTTI GLI ELEMENTS
		for (i = 0; i < elements.length; i++) {
			elements[i].className += " errorElements";
		}
	}
}
function save() {
	post(false);
}
function apriMappa() {
	var i;
	var arg = new Array();
	var c = 0;
	var j;
	var step;
	for (i = 0; i < arrayStep.length; i++) {
		for (j = 0; j < arrayStep[i].length; j++) {
			step = arrayStep[i][j];
			arg[c] = { "groupDay": step.groupDay, "numberInDay": step.numberInDay ,  "place": step.place };
			c++;
		}
	}
	var places = { "places": arg };
	jQuery.ajax({
		url: "add.jsp",
		data: { "forward": JSON.stringify(places) },
		type: "GET",
		error: function(xhr, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		},
		success: function() {
			window.open("http://localhost:8080/TravelbookISPW/map.jsp");
		}
	});

}
function openProgress(){
	$("#progressPane").animate({ opacity: '0.9' }, "slow");
	$("#addAnchor").animate({ opacity: '0.1' }, "slow");
	handleProgress(false);
}
function handleProgress(val){
	var elem = document.getElementById("progressValue");
	if(!val){



    
    var width = 1;
    var id = setInterval(frame, 10);
    function frame() {
      if (width >= 80) {
        clearInterval(id);
      } else {
        width++;
        elem.style.width = width + "%";
      }
    }
	}
	else{
		elem.style.width="100%";
		makeClickable();
	}
}
function makeClickable(){
	var elem=document.getElementById("closeProgress");
	elem.setAttribute("onclick","closeProgressPane()");
}
function closeProgressPane(){
	var elem=document.getElementById("closeProgress");
	elem.removeAttribute("onclick");
	$("#progressPane").animate({ opacity: '0' }, "slow");
	$("#addAnchor").animate({ opacity: '1' }, "slow");
	document.location.href="http://localhost:8080/TravelbookISPW/profile.jsp";
}
function removePresentation(){
	if(background!=undefined && background!=""){
		var node=document.getElementById("backgroundImage");
		document.getElementById("presentation").removeChild(node);
		background=undefined;
	}
}

