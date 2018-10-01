document.onreadystatechange = function () {
  var state = document.readyState
  if (state == 'complete') {
      setTimeout(function(){
          //document.getElementById('interactive');
        // $(".spinner").hide();//todo to display spinner during website loading
      },1000);
  }
}


	$(function() {
		getGroups();
		//$("#menu-list").load("view/common/side_menu.html");
		
		//$(".nav").load("view/common/login_header.html");
		$(".nav").hide();
		let promise = new Promise((resolve, reject) => {
			data = '{"oEmail":"'
				+ localStorage.getItem("auth-email")
				+ '","token":"'
				+ localStorage.getItem("auth-token")
				+ '"}';
			$.ajax({
				  method: "POST",
				  url: projectDetails.url+""+projectDetails.restappname+"/auth/loginCheck",
				  contentType: "application/json",
				  data: data
				}).done(function( response ) {
					resolve(response);
					//reject(Error(request.statusText));
				});
		});
			promise.then((data) => {
			 if(data.error.loginValid=="false"){
				 $("#login-error-msg").html("Login Expired!!! Please login again.");
				 setView("login")
				
			  
			 }else{
				 userDetails = data;
				 // userDetails.group = data;
				  groupDetails.group = data.group;
				  localStorage.setItem("auth-token",data.user.token);
				  localStorage.setItem("auth-email", data.user.oEmail);
				  $("#c-name").html(projectDetails.appname);
				  $("#welcome-text").html("Welcome "+userDetails.user.username);
				  getGroups();
				  processLogin("");
				 
			 }
			  $(".spinner").hide();
			}, (error) => {
			  //console.log('Promise rejected.');
			 // console.log(error.message);
			  $(".spinner").hide();
			});
			
		//$("#main-content").load("login.html");

	});
	
	$("body").click(function(e) {
		//console.log("body clicked")
		$(".btn-group").removeClass("show-side-menu");

	});
	
	 var user="end_user"
		 if (user=="end_user"){
			 $(".admin").hide();
			 $(".end_user").show();
			}
	 $(".spinner").hide();
	 $('video').off('play').on('play', function() {
		    var dd = this.id
		    $('video').each(function( index ) {
		        if(dd != this.id){
		            this.pause();
		            this.currentTime = 0;
		        }
		    });
		});
	 //-----PW-IMAGE-Nodal-----------------
	// Get the modal
	 var pw_img_modal = document.getElementById('pw-img-Modal');

	 // Get the image and insert it inside the modal - use its "alt" text as a caption
	 var pwimg = document.getElementById('myImg');
	 var pwmodalImg = document.getElementById("img01");
	 var pwcaptionText = document.getElementById("pw-img-caption");
	 function handleClick(e){
		 pw_img_modal.style.display = "block";
		 pwmodalImg.src = e.src;
	     pwcaptionText.innerHTML = e.alt;
	 }

	 // Get the <span> element that closes the modal
	 var pwspan = document.getElementsByClassName("pw-img-modal-close")[0];

	 // When the user clicks on <span> (x), close the modal
	 pwspan.onclick = function() { 
		 pw_img_modal.style.display = "none";
	 }