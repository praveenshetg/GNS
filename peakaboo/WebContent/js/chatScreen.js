	$(document).ready(function() {
		getChatList(groupDetails.group.id);
		$("#chatList").css("height", "" + ($(window).height() - 140) + "px");
		
		$("#user-msg").keypress(function(e) {
		    if ( e.keyCode == 13 && $('#user-msg').val()!=="" ) {  // detect the enter
		        $('#send-message-btn').click(); // fire a sample click
		    }
		});
		if(userDetails.user.role_name!=="admin"){
			$("#grp-header").html(userDetails.group.name);
		}else{
			generateGroupNamesOptions('group_id');
			SelectElement('group_id', groupDetails.group.id)
		}
		$("#chat_form").submit(function(e) {
			e.preventDefault();
		});

		$("#send-message-btn").click(function(e) {
			if($("#user-chat-attachment").val()=="" ){
				if ($("#user-msg").val() == '') {
								return;
				}
			}
			d = Date.now();
			d = new Date(d);
			d = d.getDate()
					+ '/'
					+ (d.getMonth() + 1)
					+ '/'
					+ d.getFullYear()
					+ ' '
					+ (d.getHours() > 12 ? d.getHours() - 12 : d
							.getHours()) + ':' + d.getMinutes()
					+ ' ' + (d.getHours() >= 12 ? "PM" : "AM");

			var data = '{"description":"' + $("#user-msg").val()
					+ '","user_id":"' + userDetails.user.id
					+ '","group_id":"' + groupDetails.group.id
					+ '","scheduleDate":"' + d + '","username":"'
					+ userDetails.user.username + '" }'
			pushChat(data,d);
			$("#user-msg").val("");
		});
		$("#group_id").change(function(e) {
			// $("#grp-header").text(''+$("#group_id option:selected").text()+'');
			if(userDetails.user.role_name==="admin"){
			getChatList($("#group_id").val());
			}else{
				getChatList(userDetails.group.id);
			}
		})
		
		 $(".chat-input-outer input[type=file]").change(function(){
			 
		 });
		 
		 $("#refresh-msgs").click(function(e) {
				if(userDetails.user.role_name==="admin"){
					getChatList($("#group_id").val());
					}else{
						getChatList(userDetails.group.id);
					}
				
			});
	});