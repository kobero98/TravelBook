/**
 * 
 */
class StepJS{
				constructor(groupDay,numberInDay,descriptionStep,place,precision){
					this.groupDay=groupDay;
					this.numberInDay=numberInDay;
					this.descriptionStep=descriptionStep;
					this.place=place;
					this.precision=precision;
					this.photo=new Array();
				}
			
			}
var startDate;
var endDate;
var dayNumber=0;
var arrayStep=new Array();
var actualDay;
var actualStep;
var background;
function startDateListener(){
	var date=document.getElementById("s-date").value;
	if(compareDate(date,endDate)){
		startDate=date;
	}
	else{
		document.getElementById("s-date").nodeValue=startDate;
	}
}
function endDateListener(){
	var date=document.getElementById("e-date").value;
	if(compareDate(startDate,date)){
		endDate=date;
	}
	else{
		document.getElementById("e-date").nodeValue=endDate;
	}
}
function setStep(evt){
	alert("FUNZIONE");
	console.log(evt.currentTarget.id);
	actualStep=evt.currentTarget.id.split(";")[1]-1;
	var desc=document.getElementById("step-descr");
	var inf=document.getElementById("step-inf");
	var divFoto=document.getElementById("photo-grid");
	var step=arrayStep[actualDay][actualStep];
	desc.value=step.descriptionStep;
	inf.value=step.precision;
	while(divFoto.lastChild)
		divFoto.removeChild(divFoto.lastChild);
	var arrayFoto=step.photo;
	var i;
	for(i=0;i<arrayFoto.length;i++){
		var img=document.createElement("img");
		img.setAttribute("src","data:image/gif;base64,"+arrayFoto[i]);
		img.setAttribute("style","width: 5em; height: 5em;");
		divFoto.appendChild(img);
	}
}
function descriptionListener(){
	var desc=document.getElementById("step-descr");
	var text=desc.value;
	arrayStep[actualDay][actualStep].descriptionStep=text;
}
function precisionListener(){
	var prec=document.getElementById("step-inf");
	var text=prec.value;
	arrayStep[actualDay][actualStep].precision=text;
}
function fileLoaded(frEvnt){
		var sFBody = frEvnt.target.result;
		var sBodyBase64 = btoa(sFBody);
		var div=document.getElementById("presentation");
		while(div.lastChild)
			div.removeChild(div.lastChild);
		var img=document.createElement("img");
		img.setAttribute("src","data:image/gif;base64,"+sBodyBase64);
		img.setAttribute("style","width: 35em; height: 10em;");
		div.appendChild(img);
		background=sBodyBase64;
		}
function loadImage(){
		var oFile=document.getElementById("presentationFile").files[0];
		if(oFile){
			var fReader=new FileReader();
			fReader.onload=fileLoaded;
			fReader.readAsBinaryString(oFile);
		}
	}
function fileLoadedStep(frEvnt){
	
	var sFBody = frEvnt.target.result;
	var sBodyBase64 = btoa(sFBody);
	var div=document.getElementById("photo-grid");
	var img=document.createElement("img");
	img.setAttribute("src","data:image/gif;base64,"+sBodyBase64);
	img.setAttribute("style","width: 10em; height: 10em;");
	div.appendChild(img);
	arrayStep[actualDay][actualStep].photo[arrayStep[actualDay][actualStep].photo.length-1]=sBodyBase64;
}
function loadMultipleImage(){
	
	var arrayFile=document.getElementById("img-choose").files;
	var i;
	for(i=0;i<arrayFile.length;i++){
		var fReader=new FileReader();
		fReader.onload=fileLoadedStep;
		fReader.readAsBinaryString(arrayFile[i]);
	}
}
function addButton(){
	arrayStep[actualDay][arrayStep[actualDay].length]=new StepJS();
	var btn=document.createElement("input");
	btn.setAttribute("type","button");
	btn.setAttribute("onclick","setStep(event)");
	btn.setAttribute("id",actualDay+";"+arrayStep[actualDay].length);
	var div=document.getElementById("steps");
	div.appendChild(btn);
}
function removeButton(){
	arrayStep[actualDay][actualStep]=undefined;
	var div=document.getElementById("steps");
	var btn=document.getElementById(actualDay+";"+actualStep);
	div.removeChild(btn);
}
function changeDay(){
	var select=document.getElementById("days");
	actualDay=select.value;
	var div=document.getElementById("steps");
	while(div.lastChild)
		div.removeChild(div.lastChild);
	var i;
	for(i=0;i<arrayStep[actualDay].length;i++){
		var btn=document.createElement("input");
		btn.setAttribute("type","button");
		btn.setAttribute("onclick","setStep(event)");
		btn.setAttribute("id",actualDay+";"+arrayStep[actualDay].length);
		var div=document.getElementById("steps");
		div.appendChild(btn);
	}
}
function changeDayNumber(num){
	console.log(num);
	if(num>=dayNumber){
		for(x=dayNumber;x<num;x++){
			arrayStep[x]=new Array();
			actualDay=x;
			var choice=document.createElement("option");
			choice.value=x;
			choice.appendChild(document.createTextNode(x+1));
			var select=document.getElementById("days");
			select.appendChild(choice);
			select.value=x;
			changeDay();
			addButton();
		}
		dayNumber=num;
		select.value=1;
	}
	else{
		arrayStep.length=num;
		dayNumber=num;
		actualDay=num;
	}
}
function compareDate(start,end){
	console.log(start);
	console.log(end);
	if(start==undefined || end==undefined)
		return true;
	var array1=start.split("-");
	var array2=end.split("-");
	var data1=new Date(array1[0],array1[1],array1[2]);
	var data2=new Date(array2[0],array2[1],array2[2]);
	if(data1.getTime<=data2.getTime){
		//Devo passare da millisecondi a giorni quindi divido per quel bestione
		changeDayNumber((data2.getTime()-data1.getTime())/86400000+1);
		return true;
		}
	return false;
}