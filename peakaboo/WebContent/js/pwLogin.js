$( document ).ready(function() {
	$('#login_form').submit(function(e) {
		e.preventDefault();
	});
	$("#login-btn").click(function(){
		if($("#pw-username").val()=='' ||$("#pw-password").val()=='' ){
			showSnackBar("Please Enter both username and password");
			//$(".spinner").hide();
			return;
		}else{
			login($("#pw-username").val(), $("#pw-password").val())
		}
	})
});