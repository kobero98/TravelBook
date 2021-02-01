/**
 * 
 */
function readTravel(data,type){
    	var dataO=data.travels;
    	var divInt=document.createElement("div");
    	document.body.appendChild(divInt);
    	if(dataO[0].title.localeCompare("not found")!=0){
    		console.log(dataO);
    		$.each(dataO,function(index,element){
    		var text=document.createTextNode(dataO[index].title);
    		var btn=document.createElement("button");
    		var img=document.createElement("img");
    		var div=document.createElement("div");
    		var form=document.createElement("form");
    		form.setAttribute("action","profile.jsp");
    		form.setAttribute("method","POST");
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
    		form.appendChild(btn);
    		div.appendChild(form);
    		div.appendChild(text);
    		div.appendChild(img);
    		divInt.appendChild(div);
    	});
    	}
	}
		function readUsers(data,textStatus,jqXHR,type){
    	var users;
    	var btn;
    	var div;
    	var text;
    	var form;
    	var img;
    	var dataO=data.users;
    	var divInt=document.createElement("div");
    	document.body.appendChild(divInt);
    	$.each(dataO,function(index,element){
    		form=document.createElement("form");
    		form.setAttribute("action","profile.jsp");
    		form.setAttribute("method","POST");
    		btn=document.createElement("button");
    		btn.setAttribute("type","submit");
    		btn.setAttribute("name",type+dataO[index].id);
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
						console.log(xhr.responseText);
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
						console.log(xhr.responseText);
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
						console.log(xhr.responseText);
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