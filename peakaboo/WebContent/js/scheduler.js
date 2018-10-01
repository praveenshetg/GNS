$(document).ready(function() {
	 //$("#sc-calender").load("view/scheduleCalender.html");
	generateGroupNamesOptions('group_id');
	   $('#group_id').multipleSelect();
	$("#includeAttachment").change(function(e){
		$("#hasAttachment").val($("#includeAttachment").is(":checked")? "true":"false");
		if($("#includeAttachment").is(":checked")){
			$("#attachment-path").show();
		}else{
			$("#attachment-path").hide();
		}
	});
	$("#sch-form").submit(function(e) {
		e.preventDefault();
	});
	$('#sc-date-wrapper').calendar({
		today : true, // show a 'today/now' button at the bottom of the calendar 
		closable : true,
		ampm : true,
		multiMonth : 1,
		formatter : {
			date : function(date, settings) {
				if (!date)
					return '';
				var day = date.getDate() + '';
				if (day.length < 2) {
					day = '0' + day;
				}
				var month = (date.getMonth() + 1) + '';
				if (month.length < 2) {
					month = '0' + month;
				}
				var year = date.getFullYear();
				return day + '/' + month + '/' + year;
			}
		},
		monthFirst : false
	// month before day when parsing/converting date from/to text 
	});
	$("#sc-create-event-btn").click(function(e) {
		$(".spinner").show();
		if($("#sc-desc").val() =='' ){
			showSnackBar("Please fill Description");
			$(".spinner").hide();
			return;
		}else if($("#group_id").val()==null){
			showSnackBar("Please select Groups");
			$(".spinner").hide();
			return;
		}else if($("#sc-date").val()==''){
			showSnackBar("Please select Schedule Date");
			$(".spinner").hide();
			return;
		}
				
		var data = '{"subject":"'
			+ $("#sc-subject").val()
			+ '","description":"'
			+ $("#sc-desc").val()
			+ '","period":"'
			+ $("#sc-date").val()
			+ '","scheduleDate":"'
			+ $("#sc-date").val()
			+ '","user_id":"'
			+ userDetails.user.id
			+ '","group_id":"'
			+ $("#group_id").val()
			+ '","username":"'
			+ userDetails.user.username
			+ '"}';
		
		$.ajax({
			method : "POST",
			url : projectDetails.url+""+projectDetails.restappname+"/schedule/addEvent",
			contentType : "application/json",
			data : data
		}).done(function(response) {
			if($("#includeAttachment").is(":checked")){
				var documentData = new FormData();
				documentData.append('file', $('input#attachment-path.findDocumentOnboarding')[0].files[0]);
				documentData.append('scheduleDate', response.scheduleDate);
				documentData.append('type', "EVENT");
				

				
				$.ajax({
					xhr: function() {
					    var xhr = new window.XMLHttpRequest();

					    xhr.upload.addEventListener("progress", function(evt) {
					      if (evt.lengthComputable) {
					        var percentComplete = evt.loaded / evt.total;
					        percentComplete = parseInt(percentComplete * 100);
//					       / $(".percent").html('Uploading..('+percentComplete+'%)');
					        showProgressSnackBar('Uploading..('+percentComplete+'%)');
					        if (percentComplete === 100) {
								hideSnackBar();
					        }

					      }
					    }, false);

					    return xhr;
					  },
				
					url : projectDetails.url + ""+projectDetails.restappname+"/schedule/upload/"+userDetails.user.id+"/"+userDetails.group.id,
				    type: 'POST',
				    data: documentData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    success: function (response) {
				    	$(".spinner").hide();
				    	showSnackBar("Event Scheduled Successfully !!!");
				    }
				});
			}else{
				$(".spinner").hide();
				showSnackBar("Event Scheduled Successfully !!!");
			}
			//$("#sch-form input").val("");
		});
	});
	
	
});