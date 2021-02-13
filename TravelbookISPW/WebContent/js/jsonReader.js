/**
 * 
 */

function readTravel(data,type){
    	var dataO=data.travels;
    	var divInt=document.createElement("div");
    	var title=document.createElement("div");
    	var backButton=document.createElement("button");
		var iconBack=document.createElement("span");
		iconBack.setAttribute("class","material-icons md-48");
		iconBack.innerHTML="arrow_back";
		backButton.appendChild(iconBack);
    	backButton.setAttribute("class","s-back-button");	
		backButton.setAttribute("onclick","closePanel()");
    	divInt.setAttribute("class","show");
    	title.setAttribute("class", "show-title");
    	title.appendChild(backButton);
    	divInt.appendChild(title);
    	document.body.appendChild(divInt);
    	if(dataO[0].title.localeCompare("not found")!=0){
    		$.each(dataO,function(index,element){
    		var text=document.createTextNode(dataO[index].title);
    		var btn;
			var icon;
    		var img=document.createElement("img");
    		var div=document.createElement("div");
    		var form=document.createElement("form");
    		form.setAttribute("action","profile.jsp");
    		form.setAttribute("method","POST");
			icon=document.createElement("span");
			icon.setAttribute("class","material-icons");
			icon.innerHTML="chevron_right";
    		btn=document.createElement("button");
    		btn.setAttribute("type","submit");
    		btn.setAttribute("name",type+dataO[index].id);
    		div.setAttribute("id",type+dataO[index].id);
    		div.setAttribute("class","followPanel");
    		if(dataO[index].image.localeCompare("")==0){
    			img.setAttribute("src","resource/travelers.png");
    		}
    		else{
    			img.setAttribute("src","data:image/gif;base64,"+dataO[index].image);
    		}
    		img.setAttribute("style","width: 5em; height: 5em;");
    		img.setAttribute("class","image");
			btn.appendChild(icon);
    		form.appendChild(btn);
    		
    		div.appendChild(img);
			div.appendChild(text);
			div.appendChild(form);
    		divInt.appendChild(div);
    	});
    	}
	}
	function readUsers(data,textStatus,jqXHR,type){
    	var btn;
    	var div;
    	var text;
    	var form;
    	var img;
		var icon;
    	var dataO=data.users;
    	var divInt=document.createElement("div");
    	var title=document.createElement("div");
    	var backButton=document.createElement("button");
		var iconBack=document.createElement("span");
		iconBack.setAttribute("class","material-icons md-48");
		iconBack.innerHTML="arrow_back";
		backButton.appendChild(iconBack);
    	backButton.setAttribute("class","s-back-button");
		backButton.setAttribute("onclick","closePanel()");
    	title.setAttribute("class", "show-title");
    	title.appendChild(backButton);
    	divInt.appendChild(title);
    	divInt.setAttribute("class","show");
    	document.body.appendChild(divInt);
    	$.each(dataO,function(index,element){
    		form=document.createElement("form");
    		form.setAttribute("action","profile.jsp");
    		form.setAttribute("method","POST");
    		btn=document.createElement("button");
    		btn.setAttribute("type","submit");
    		btn.setAttribute("name",type+dataO[index].id);
			icon=document.createElement("span");
			icon.setAttribute("class","material-icons");
			icon.innerHTML="chevron_right";
    		div=document.createElement("div");
    		div.setAttribute("id",type+dataO[index].id);
    		div.setAttribute("class","followPanel");
    		text=document.createTextNode(dataO[index].name+" "+dataO[index].surname);
    		img=document.createElement("img");
    		if(dataO[index].image.localeCompare("")==0){
    			img.setAttribute("src","resource/travelers.png");
    		}
    		else{
    			img.setAttribute("src","data:image/gif;base64,"+dataO[index].image);
    		}
    		img.setAttribute("style","width: 5em; height: 5em;");
    		img.setAttribute("class","image");
			btn.appendChild(icon);
    		form.appendChild(btn);
    		div.appendChild(text);
    		div.appendChild(img);
    		div.appendChild(form);
    		divInt.appendChild(div);
    	});
    	}
function showFollowing(){
				jQuery.ajax({
					url:"jsonResponser.jsp",
					type: "GET",
					data:{"following": 'true',"userID":userID},
					dataType:"json",
					error:function(xhr,ajaxOptions,thrownError){
						alert(xhr.status);
				         alert(thrownError);
					},
					success: function(data,textStatus,jqXHR){
				    	readUsers(data,textStatus,jqXHR,"following");
				    }
				});
			}
			function showFollower(){
				jQuery.ajax({
					url: "jsonResponser.jsp",
					type: "GET",
					data:{"follower": 'true',"userID": userID},
					dataType:"json",
					error: function(xhr,ajaxOptions,thrownError){
						alert(xhr.status);
				         alert(thrownError);
				       },
				    success: function(data,textStatus,jqXHR){
				    	readUsers(data,textStatus,jqXHR,"follower");
				    }
				});
			}
			function showFav(){
				jQuery.ajax({
					url:"jsonResponser.jsp",
					type:"GET",
					data:{"travel":'true',"userID":userID},
					dataType:"json",
					error: function(xhr,ajaxOptions,thrownError){
						alert(xhr.status);
				         alert(thrownError);
				       },
				    success: function(data){
				    	readTravel(data,"fav");
						}
				    
				});
			}
			function closePanel(){
				var array=document.getElementsByClassName("show");
				var div=array[0];
				document.body.removeChild(div);
			}