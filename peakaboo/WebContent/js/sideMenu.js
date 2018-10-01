function resetButtonStatus() {
	$("#chat-btn").removeClass("active-btn");
	$("#grp-btn").removeClass("active-btn");
	$("#usr-main-btn").removeClass("active-btn");
	$("#sch-btn").removeClass("active-btn");
	$("#rctn-main-btn").removeClass("active-btn");
	$("#home-btn").removeClass("active-btn");
	$("#usr-setting-btn").removeClass("active-btn");
	$("#profile-btn").removeClass("active-btn");
}

$(document).ready(function() {
	
		
		$(document).on("click", "#home-btn", function(e) {
			  resetButtonStatus();
				$("#home-btn").addClass("active-btn")
				setView("view/home");
		 });
			  
	  $(document).on("click", "#chat-btn", function(e) {
		  resetButtonStatus();
			$("#chat-btn").addClass("active-btn")
			setView("view/chatScreen");
	   });
	  $(document).on("click", "#grp-btn", function(e) {
		  resetButtonStatus();
			$("#grp-btn").addClass("active-btn")
			setView("view/group_registration");
	   });
	  $(document).on("click", "#grp-case-study", function(e) {
		  resetButtonStatus();
			$("#grp-btn").addClass("active-btn")
			setView("view/groupCaseStudy");
	   });
	  $(document).on("click", "#usr-main-btn", function(e) {
		  	resetButtonStatus();
			$("#usr-main-btn").addClass("active-btn")
			//setView("view/user_registration");
	   });
	  $(document).on("click", "#usr-btn", function(e) {
		  resetButtonStatus();
			$("#usr-main-btn").addClass("active-btn")
			setView("view/user_registration");
	   });
	  $(document).on("click", "#usr-list-btn", function(e) {
		  resetButtonStatus();
			$("#usr-main-btn").addClass("active-btn")
			setView("view/userView");
	   });
	  $(document).on("click", "#usr-auto-btn", function(e) {
			resetButtonStatus();
			$("#usr-main-btn").addClass("active-btn")
			setView("view/userAutoCreateView");

	  });
	  $(document).on("click", "#sch-btn", function(e) {
		  resetButtonStatus();
			$("#sch-btn").addClass("active-btn")
			setView("view/scheduler");
	   });
	  $(document).on("click", "#rctn-btn", function(e) {
		  resetButtonStatus();
			$("#rctn-main-btn").addClass("active-btn")
			setView("view/reaction");
	   });
	 
	  $(document).on("click", "#rctn-main-btn", function(e) {
		  resetButtonStatus();
			$("#rctn-main-btn").addClass("active-btn")
			setView("view/feedBackListView");
	  });
	  $(document).on("click", "#fb-page-btn", function(e) {
		  resetButtonStatus();
			$("#rctn-main-btn").addClass("active-btn")
			setView("view/feedBackListView");
	   });
	  $(document).on("click", "#mail-config-btn", function(e) {
		  resetButtonStatus();
			$("#usr-setting-btn").addClass("active-btn")
			setView("view/settings/mailConfigView");
	   });
	  $(document).on("click", "#mail-template-btn", function(e) {
		  resetButtonStatus();
			$("#usr-setting-btn").addClass("active-btn")
			setView("view/settings/mailTemplatesView");
	   });
	  function importButtons(role){
			if(role==="admin"){
				$("#home-btn-holder").load("view/common/menuButtons/homeButton.html")
			}else{
				
				alert("no permission");
			}
			
		}
	  $(document).on("click", "#profile-btn", function(e) {
		  resetButtonStatus();
			$("#profile-btn").addClass("active-btn")
			setView("view/user_profile_view");
	   });
	 // importButtons(userDetails.user.role_name);

});

