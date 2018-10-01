var divContainer = document.getElementById("user-login-profile-pic");
if(userDetails.user.image!==null){
divContainer.setAttribute("src", 'data:image/png;base64,' + userDetails.user.image);
}
$("#c-name").html(projectDetails.appname);
if(userDetails.user.role_name==="admin"){
	$("#menu-list").load("view/common/side_menu_admin.html");
}
else{
	$("#menu-list").load("view/common/side_menu_user.html");
}
$("#side-menu-btn").click(function(e) {
	e.stopPropagation();
	console.log("button clicked")
	$(".btn-group").toggleClass("show-side-menu");
	

});
$(".c-name").click(function(){
	//$("#home-btn").click();
});
$("#p-logout-btn").click(function() {
	$(".nav").hide();
	localStorage.removeItem('auth-token');
	
	//$("#outer-content").css("top","0");
	 setView("login");
	//$(".nav").load("view/common/login_header.html");
});
$("#edit-profile-btn").click(function() {
	setView("view/user_profile")
	//$("#main-content").load("view/user_profile.html");

});
