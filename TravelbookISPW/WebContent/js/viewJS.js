/**
 * 
 */
			var array=new Array();
			function openDay(event,dayNumber){
				var i, tabcontent,tablinks;
				tabcontent=document.getElementsByClassName("tabcontent");
				for(i=0;i<tabcontent.length;i++){
					tabcontent[i].style.display="none";
				}
				tablinks=document.getElementsByClassName("tablinks");
				for(i=0;i<tablinks.length;i++){
					tablinks[i].className="tablinks";
				}
				document.getElementById("day"+dayNumber).style.display="flex";
				event.currentTarget.className+=" active";
			}
			function openStep(event,dayNumber,stepNumber){
				var steps = document.getElementsByClassName("stepButton");
				for(let value of steps){
					value.className="stepButton";
				}
				event.currentTarget.className+=" p-step";
				var div=document.getElementById("right-panel");
				var fotoDiv=document.createElement("div");
				fotoDiv.setAttribute("id","fotodiv");
				fotoDiv.setAttribute("class","fotoDiv");
				while( div.lastChild )
					div.removeChild( div.lastChild );
				var step=array[dayNumber][stepNumber];
				var p=document.createElement("P");
				p.setAttribute("id", "step-title");
				
				p.innerHTML=step.place;
				var descr=document.createElement("P");
				descr.setAttribute("class","descr");
				descr.innerHTML=step.descriptionStep;
				
				var prec=document.createElement("P");
				prec.setAttribute("class","descr prec");
				prec.innerHTML=step.precision;
				var img;
				for(let value of step.photo){
					img=document.createElement("img");
					img.setAttribute("src","data:image/gif;base64,"+value);
					img.setAttribute("style","width: 8em; height: 8em;");
					fotoDiv.appendChild(img);
				}
				div.appendChild(p);
				div.appendChild(fotoDiv);
				div.appendChild(descr);
				div.appendChild(prec);
			}
	function showFav(idUser){
		jQuery.ajax({
			url: "jsonResponser.jsp",
			dataType: "json",
			data: {"shareable":"true", "userID":idUser},
			type: "GET",
			error:function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(data){
				var dataO=data.shareable;
				var checkBox;
				var label;
				var div;
				var newDiv;
				var img;
				$.each(dataO,function(index,element){
					checkBox=document.createElement("input");
					checkBox.setAttribute("type","checkbox");
					checkBox.setAttribute("id","checkbox"+dataO[index].userId);
					checkBox.setAttribute("value","checkbox"+dataO[index].userId);
					checkBox.setAttribute("name","checkbox"+dataO[index].userId);
					newDiv=document.createElement("div");
					newDiv.setAttribute("class","shareableElements");
					label=document.createElement("label");
					label.setAttribute("for","checkbox"+dataO[index].userId);
					label.innerHTML=dataO[index].user;
					img=document.createElement("img");
					if(dataO[index].image)
						img.setAttribute("src","data:image/*;base64,"+dataO[index].image);
					else
						img.setAttribute("src","resource/travelers.png");
					img.setAttribute("style","width: 2em; height: 2em;");
					newDiv.appendChild(img);
					div=document.getElementById("shareDiv");
					newDiv.appendChild(checkBox);
					newDiv.appendChild(label);
					div.appendChild(newDiv);
				});
				div=document.getElementById("shareDiv");
				div.style.opacity=1;
			}
		});
	}
	function shareTravel(){
		
		
		var i;
		var users=new Array();
		var text;
		
		var checked = $("input[type=checkbox]:checked");
		for(i=0;i<checked.length;i++){
			text=checked[i].id.split("checkbox");
			users[i]=text[1];
		}
		
		jQuery.ajax({
			url:"viewTravel.jsp",
			type: "POST",
			data:{"userToBeShared":users, "travelID":travelId},
			error: function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				alert("Shared!");
			}
		});
	}
	function closeShare(){
		var div=document.getElementById("shareDiv");
		div.style.opacity=0;
		while( div.lastChild )
			div.removeChild( div.lastChild );
		div.innerHTML='<form action="viewTravel.jsp" method="POST" id="formShare"><input type="button" id="condividi" name="share" value="share" onclick="shareTravel()"><input type="button" value="close" onclick="closeShare()"></form>'
	}
	function addFav(){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"fav":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
			var btn=document.getElementById("fav");
			if(btn.className==="bb-button"){
				btn.className+=" select";
			}
			else{
				btn.className="bb-button";
			}
			}
		});
	}
	function goChat(userId){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"chat":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				document.location.href="http://localhost:8080/TravelbookISPW/chat.jsp";
			}
		});
	}
	function goProfile(userId){
		jQuery.ajax({
			url: "viewTravel.jsp",
			data:{"travelID":travelId,"profile":"true"},
			type:"POST",
			error: function(xhr,ajaxOptions,thrownError){
				alert(xhr.status);
		         alert(thrownError);
			},
			success: function(){
				document.location.href="http://localhost:8080/TravelbookISPW/profileOther.jsp?user="+userId;
			}
		})
	}
	function loadMap(){
		var i;
		var arg = new Array();
		var c = 0;
		var j;
		var step;
		for (i = 0; i < array.length; i++) {
			for (j = 0; j < array[i].length; j++) {
				step = array[i][j];
				arg[c] = { "groupDay": step.groupDay, "numberInDay": step.numberInDay, "place": step.place };
				c++;
			}
		}
		var places = { "places": arg };
		jQuery.ajax({
			url: "add.jsp",
			data: { "forward": JSON.stringify(places) },
			type: "GET",
			dataType:"html",
			error: function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
			},
			success: function(data) {
				window.open("http://localhost:8080/TravelbookISPW/map.jsp");
			}
		});
	}
	function goBack() {
		  window.history.back();
		}