projectDetails={}
const env = 'DEV'
if(env == 'DEV'){
	projectDetails.url="http://localhost:9090/";
	projectDetails.appname='peakaboo';
	projectDetails.restappname='peakaboo-service';
	//projectDetails.url="http://localhost:9090/"
}else{
	projectDetails.appname='peakaboo';
	projectDetails.restappname='peakaboo-service';
	projectDetails.url="http://peakaboo.proawitz.com/"
}


userDetails={};
userDetails.user={};

groupDetails={};
//groupDetails.groups=[];
groupDetails.group={};
//groupDetails.group.name='PRO-Group-one'
//groupDetails.group.id=1;
$(document).ready(function(){
    $("#usr-btn").click(function(){
        $(".main-div").load("user_registration.html");
    });
});


function scrollToBottom(wrapper_div_id,element_id) {
    var message = jQuery('#'+wrapper_div_id);
    var newMessage = message.children('div:last-child');
    
    var clientHeight = message.innerHeight();
    var scrollTop = message.prop('scrollTop');
    var scrollHeight = message.prop('scrollHeight');
    
    var newMessageHeight = newMessage.innerHeight();
    var lastMessageHeight = newMessage.prev().innerHeight();
    if(lastMessageHeight + newMessageHeight + clientHeight + scrollTop >= scrollHeight) {
        message.scrollTop(scrollHeight);
    }
 }

function scrollSmoothToBottom (id) {
	   var div = document.getElementById(id);
	   $('#' + id).animate({
	      scrollTop: div.scrollHeight - div.clientHeight
	   }, 200);
	}

function login(username, password){
		$(".spinner").show();
		let token = shuffelWord(generateRandomToken()+username);
		let promise = new Promise((resolve, reject) => {
			data = '{"oEmail":"'
				+ username
				+ '","password":"'
				+ password
				+ '","token":"'
				+ token
				+ '"}';
			$.ajax({
				  method: "POST",
				  url: projectDetails.url+""+projectDetails.restappname+"/auth/login",
				  contentType: "application/json",
				  data: data
				}).done(function( response ) {
					resolve(response);
					//reject(Error(request.statusText));
				});
		});
			promise.then((data) => {
			  if(data.error.error == "true"){
				  $("#login-error-msg").html("Invalid username or password");
				  showSnackBar("Invalid username or password");
				  $("#login-error-msg").css("color","#db2828");
				  $(".spinner").hide();
				  
			  }else{
				  userDetails = data;
				 // userDetails.group = data;
				  groupDetails.group = data.group;
				  localStorage.setItem("auth-token",data.user.token);
				  localStorage.setItem("auth-email", data.user.oEmail);
				  $("#c-name").html(projectDetails.appname);
				  $("#welcome-text").html("Welcome "+userDetails.user.username);
				  processLogin(username);
				  $(".spinner").hide();
			  }
			  
			}, (error) => {
			  $(".spinner").hide();
			});
		
		
	/*	$.ajax({
			method : "POST",
			url : projectDetails.url + ""+projectDetails.restappname+"/user/validateUser",
			contentType : "application/json"
		}).done(function(response) {
			// alert( "success" );
			$(".spinner").hide();
			
			//showSnackBar("Loading Group List...")
			//createDynamiTable(groupList, "showData");
			//paginateTable("grp-table", 5);
			//$(".spinner").hide();
			// return groupList;
		}).fail(function(){
			$(".spinner").hide();
		})*/
	

}
 function rand() {
    return Math.random().toString(36).substr(2); // remove `0.`
};
function generateRandomToken() {
    return rand() + rand()+ rand(); // to make it longer
};

function processLogin(username){
	//$("#c-name").html(projectDetails.appname);
	//$("#welcome-text").html("Welcome "+userDetails.user.username);
	$(".nav").load("view/common/header.html");
	
	$(".nav").show();
	
	//$("#main-content").load("view/InstantChat.html");
	
	//var lastPage = localStorage.getItem("pw-last-visited-page")===""?"view/home.html" :localStorage.getItem("pw-last-visited-page") ;
	//setView(lastPage);
	$("#main-content").load("view/home.html");	
	
	//$("#main-content").fadeOut(function() {
		//$("#main-content").load("view/feedBackListView.html");	
		// $("#main-content").fadeIn()
	///});
	
//	console.log(token)
	
}


function setView(url){
	if(url==="login"){
		localStorage.setItem("pw-last-visited-page","");
	}else{
	localStorage.setItem("pw-last-visited-page",url);
	}
	$("#main-content").load(url+".html");
	return;
}