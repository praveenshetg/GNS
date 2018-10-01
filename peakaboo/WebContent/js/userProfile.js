$('#user-reg-form').submit(function(e) { 
	e.preventDefault();
});

fillUserProfile(userDetails);
$("#update-usr-btn").click(function(){
	if($("#fName").val() ===""){
		showSnackBar("Please Enter First Name");
		return;
	}else if($("#organization").val() ===""){
		showSnackBar("Please Enter Organization Name");
		return;
	}else if($("#oEmail").val() === ""){
		showSnackBar("Please Enter Official Email Id");
		return;
		if(!validateEmail($("#oEmail").val())){
	
		showSnackBar("Please Enter valid Official Email Id");
		return;
		}
	}else if($("#oEmail").val() !== "" && !validateEmail($("#pEmail").val())){
		showSnackBar("Please Enter valid personal Email Id");
		return;
	}else if($("#phone").val()!=="" && !validatePhoneNumber($("#phone").val())){
		showSnackBar("Please Enter valid Phone Number");
		return;
	}
	var data = '{"fName":"'
		+ $("#fName").val()
		+ '","lName":"'
		+ $("#lName").val()
		+ '","pEmail":"'
		+ $("#pEmail").val()
		+ '","designation":"'
		+ $("#designation").val()
		+ '","gender":"'
		+ $("#gender").val()
		+ '","phone":"'
		+ $("#phone").val()
		+ '","organization":"'
		+ $("#organization").val()
		+ '","dob":"'
		+ $("#dob").val()
		+ '","aboutYou":"'
		+ $("#aboutYou").val()
		+ '"}';
	$.ajax({
		  method: "POST",
		  url: projectDetails.url+""+projectDetails.restappname+"/user/updateUser/"+userDetails.user.id,
		  dataType: "json",
		    contentType: "application/json",
		  data: data
		})
		.done(function( msg ) {
			showSnackBar("Profile Updated Successfully.")
			//setView("view/user_profile_view");
			//location.reload();
			//$("#user-reg-form input").val("");
		}).fail(function(error){
			showSnackBar("Something went wrong, Please try again.")
		});

});
$('#change-password-form').submit(function(e) { 
	e.preventDefault();
});

$("#change-pass-btn").click(function(){
	
	if($("#newPassword").val()!=$("#confirmPassword").val()){
		showSnackBar("Passwords are not matching. Please try again.");
		return;
	}
	var data = '{"oldPassword":"'
		+ $("#oldPassword").val()
		+ '","newPassword":"'
		+ $("#newPassword").val()
		+ '"}';
	$.ajax({
		  method: "POST",
		  url: projectDetails.url+""+projectDetails.restappname+"/user/changePassword/"+userDetails.user.id,
		  dataType: "json",
		    contentType: "application/json",
		  data: data
		})
		.done(function( response ) {
			showSnackBar(response.msg)
			$("#change-password-form input").val("");
			//span.click();
		});

});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        
        reader.onload = function (e) {
            $('#profile-img-tag').attr('src', e.target.result);
            $('#profile-img-tag').attr('style', "display:table-cell");
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#profile-img-path").change(function(){
    readURL(this);
});
//Get the modal
var modal = document.getElementById('changePasswordModal');

// Get the button that opens the modal
var btn = document.getElementById("changePasswordModalBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$(".uploadDocumentGeneral").on("click", function (evt) {

	var documentData = new FormData();
	
	documentData.append('file', $('input#profile-img-path.findDocumentOnboarding')[0].files[0]);
	$.ajax({
		xhr: function() {
	    var xhr = new window.XMLHttpRequest();

	    xhr.upload.addEventListener("progress", function(evt) {
	      if (evt.lengthComputable) {
	        var percentComplete = evt.loaded / evt.total;
	        percentComplete = parseInt(percentComplete * 100);
//	       / $(".percent").html('Uploading..('+percentComplete+'%)');
	        showProgressSnackBar('Uploading..('+percentComplete+'%)');
	        if (percentComplete === 100) {
	        	hideSnackBar();
	        }

	      }
	    }, false);

	    return xhr;
	  },
		url : projectDetails.url + ""+projectDetails.restappname+"/user/upload/"+userDetails.user.id,
	    type: 'POST',
	    data: documentData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (response) {
	    	showSnackBar("Profile uploaded successfully");
	    	//setView("view/user_profile_view");
	    	//location.reload();
	    }
	});

	return false;
	});