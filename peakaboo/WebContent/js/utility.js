function createDynamiTable(groupData, id) {
	
	$(".spinner").show();
	if (groupData.length == 0) {
		return;
	}
	// EXTRACT VALUE FOR HTML HEADER. 
	// ('Book ID', 'Book Name', 'Category' and 'Price')
	var col = [];
	for (var i = 0; i < groupData.length; i++) {
		for ( var key in groupData[i]) {
			if(key=='user_count')key='user';
			if (col.indexOf(key) === -1) {
					col.push(key);
			}
		}
	}

	// CREATE DYNAMIC TABLE.
	var table = document.createElement("table")
	table.setAttribute("class", "grp-table");
	table.setAttribute("id", "grp-table");

	// CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

	var tr = table.insertRow(-1); // TABLE ROW.

	for (var i = 0; i < col.length; i++) {
		var th = document.createElement("th"); // TABLE HEADER.
		th.innerHTML = col[i].toUpperCase();
		tr.appendChild(th);
	}
	tr.appendChild(th);
	// ADD JSON DATA TO THE TABLE AS ROWS.
	for (var i = 0; i < groupData.length; i++) {

		tr = table.insertRow(-1);

		for (var j = 0; j < col.length; j++) {
			var tabCell = tr.insertCell(-1);
			tabCell.innerHTML = groupData[i][col[j]];
			if (j == col.length - 1) {
				tabCell.innerHTML = "<div><i onclick='editGroup("+ groupData[i]+")' class='fa fa-pencil' style='padding-left: 5px;'></i><i onclick='deleteGroup("+ groupData[i]+ ")' class='fa fa-remove' style='color:red;padding-right: 5px;float:right;'></i></div>";
			}
		}
	}

	// FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
	var divContainer = document.getElementById(id);
	divContainer.innerHTML = "";
	divContainer.appendChild(table);
	$(".spinner").hide();
}

function paginateTable(id, rows) {
	$('#pages').html("");
	if (rows.length == 0) {
		return;
	}
	//$('#'+id ).after('<div id="pages"></div>');
	var rowsShown = rows;
	var rowsTotal = $('#' + id + ' tbody tr').length;
	var numPages = rowsTotal / rowsShown;
	for (i = 0; i < numPages; i++) {
		var pageNum = i + 1;
		$('#pages').append('<a href="#" rel="' + i + '">' + pageNum + '</a> ');
	}
	
	$('#' + id + ' tbody tr').hide();
	$('#' + id + ' tbody tr').slice(0, rowsShown).show();
	$('#pages a:first').addClass('page-active');
	$('#pages a').bind(
			'click',
			function() {

				$('#pages a').removeClass('page-active');
				$(this).addClass('page-active');
				var currPage = $(this).attr('rel');
				var startItem = currPage * rowsShown;
				var endItem = startItem + rowsShown;
				$('#' + id + ' tbody tr').css('opacity', '0.0').hide().slice(
						startItem, endItem).css('display', 'table-row')
						.animate({
							opacity : 1
						}, 300);
			});
}

function createDynamiForm(group, id, callBack) {
	if (group.length == 0) {
		return;
	}
	$(".spinner").show();
	// EXTRACT VALUE FOR HTML HEADER. 
	// ('Book ID', 'Book Name', 'Category' and 'Price')
	var col = [];
	for (var i = 0; i < group.length; i++) {
		for ( var key in group[i]) {
			if (col.indexOf(key) === -1) {
				col.push(key);
			}
		}
	}

	// CREATE DYNAMIC FORM.
	var form = document.createElement("form")
	form.setAttribute("class", "grp-table");
	form.setAttribute("id", "feedback-collection-form");
	//
	var fbq_outer_div = document.createElement("div");
	fbq_outer_div.setAttribute("class", "fbq_outer_div");
	var fbq_list_div = document.createElement("div");
	fbq_list_div.setAttribute("class", "fbq_list_div");
	let h3 = document.createElement("h3");
	h3.textContent = (group[0][col[4]]).toUpperCase();
	;
	form.appendChild(h3);
	for (var k = 0; k < group.length; k++) {
		let hr = document.createElement("hr");
		//fbq_list_div.appendChild(hr);
		//for (var j = 0; j<col.length; j++) {
		let fb_question = document.createElement("div");
		fb_question.setAttribute("class", "fbq_q");

		//create paragraph element
		let p = document.createElement("p");
		p.textContent = (k + 1) + ") " + group[k][col[1]];

		fb_question.appendChild(p)
		switch (group[k][col[2]]) {
		case 'text':
			let iText = document.createElement("input");
			iText.type = "text";
			iText.name = "user_name";
			iText.id = "user_name1";
			iText.setAttribute("class", "fbq-text-box");
			iText.setAttribute("style", "margin-left: 15px; width: 90%;");
			fb_question.appendChild(iText);
			break;
		case 'rating':
			let rating_div = document.createElement("div");
			rating_div.setAttribute("class", "ans_rating");
			//rating_div.setAttribute("id", "rating");
			let rating_outer_div = document.createElement("div");
			rating_outer_div.setAttribute("class", "pw_rate_widget");
			//rating_div.setAttribute("id", "");
			for (var r = 1; r <= 5; r++) {
				let star_div = document.createElement("div");
				star_div.setAttribute("class", "star_"+r+" pw_ratings_stars");
				rating_outer_div.appendChild(star_div)
			}
			rating_div.appendChild(rating_outer_div)
			fb_question.appendChild(rating_div)
			break;
		default:
			break;
		}
		//create input element
		//		let iText = document.createElement("input");
		//		iText.type = "text";
		//		iText.name = "user_name";
		//		iText.id = "user_name1";
		//		iText.setAttribute("class", "fbq-text-box" );
		//		
		//		fb_question.appendChild(p)
		//		fb_question.appendChild(iText);

		//		let  rating_div = document.createElement("div");
		//		rating_div.setAttribute("class", "rating" );
		//		rating_div.setAttribute("id", "rating-"+j );
		//
		//		for(var r=5;r>=1;r--){
		//			console.log("adding star")
		//			let  rating_span = document.createElement("span");
		//			rating_span.setAttribute("class", "fa fa-star" );
		//			iRadio.type = "radio";
		//			iRadio.name = "rating";
		//			iRadio.id = "start"+r;
		//			iRadio.setAttribute("for", "start"+r );
		//			
		//			let label = document.createElement("label");
		//			label.for = "text";
		//			label.name = "user_name";
		//			//label.id = "user_name1";
		//			label.setAttribute("class", "fbq-text-box" );
		//			
		//			rating_span.appendChild(iRadio);
		//			rating_span.appendChild(label);
		//			rating_div.appendChild(rating_span);
		//			
		//		}

		//		let  rating_edit_btn = document.createElement("button");
		//		rating_edit_btn.setAttribute("for", "q-edit" );
		//		rating_edit_btn.innerHTML='<div onclick=editFBQuestion('+true+')>Edit</div>'
		//			fb_question.setAttribute("class", "fbq_q" );
		//			
		//			//create paragraph element
		//			var p1 = document.createElement("p");
		//			p1.textContent="This is first Question";
		//			
		//			//create input element
		//			var i1 = document.createElement("input");
		//			i1.type = "text";
		//			i1.name = "user_name";
		//			i1.id = "user_name1";
		//			i1.setAttribute("class", "fbq-text-box" );
		//			
		//			fb_question.appendChild(p1)
		//			fb_question.appendChild(i1);
		//		fb_question.appendChild(rating_div)
		//		fb_question.appendChild(rating_edit_btn)
		fbq_list_div.appendChild(fb_question);
		//addStarEven("rating-"+j)
		//}
		let hr1 = document.createElement("hr");
		//fbq_list_div.appendChild(hr);
	}

	fbq_outer_div.appendChild(fbq_list_div);
	//var i = document.createElement("input");
	//i.type = "text";
	//i.name = "user_name";
	//i.id = "user_name1";

	//create a checkbox
	//var c = document.createElement("input");
	//c.type = "radio";
	//c.id = "checkbox1";
	//c.name = "check1";

	//create a button
	var s = document.createElement("input");
	s.type = "submit";
	s.value = "Submit";

	// add all elements to the form
	form.appendChild(fbq_outer_div);
	form.appendChild(s);

	// add the form inside the body
	//$(id).append(form);   //using jQuery or
	//document.getElementsByTagName('body')[0].appendChild(form); //pure javascript

	// CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

	// FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
	var divContainer = document.getElementById(id);
	divContainer.innerHTML = "";
	divContainer.appendChild(form);
	generateEvents();
	$(".spinner").hide();
	//callBack();
}

function createChatList(chats, user_id, group_id, div_id, isAttachment) {
	
	$(".spinner").show();
	// CREATE DYNAMIC Chat Page.
	var outerdiv = document.createElement("div")
	outerdiv.setAttribute("id", "messages-container");
	for (var k = 0; k < chats.length; k++) {
		let container_box = document.createElement("div");
		container_box.setAttribute("class", "container-box");
		

		let container = document.createElement("div");
		if (user_id == chats[k].user_id) {
			container.setAttribute("class", "container darker");
			//container.setAttribute("width", "30%");
		} else {
			container.setAttribute("class", "container");
		}
			  
		let img_anchor  = document.createElement("a");  
		let img_wrapper  = document.createElement("div");  
		let video_tag  = document.createElement("video");
		let br_tag  = document.createElement("br");
		img_anchor.setAttribute("download", chats[k].description);
		img_anchor.setAttribute("href", "media/"+chats[k].description);
		video_tag.setAttribute("width", "200px");
		video_tag.setAttribute("height", "130px");
		video_tag.setAttribute("controls","");
		video_tag.setAttribute("style", "border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
		
		let attachmentType= "MESSAGE";
		if(chats[k].type.includes("image") || chats[k].attachmentType.includes("IMAGE")){
			attachmentType="IMAGE"
				container_box.setAttribute("style", "background-color: white !important;");
			container.setAttribute("style", "background-color: white !important;");
		}else if(chats[k].attachmentType.includes("video") || chats[k].attachmentType.includes("VIDEO")){
			attachmentType="VIDEO"
				container_box.setAttribute("style", "background-color: white !important;");
			container.setAttribute("style", "background-color: white !important;");
		}
		
		if(attachmentType=="IMAGE"){
			let img_div = document.createElement("img");
			if (chats[k].attachmentContentType ==="application/pdf") {
				img_div.setAttribute("src", "images/pdf.png");
				img_div.setAttribute("style", "width: 70PX;height:70PX;border:0px solid #6b6b6a;background: transparent; border-radius:5px;");
			} else {
				img_div.setAttribute("src", "/media/"+chats[k].description);
				img_div.setAttribute("style", "width: 200PX;height:200PX;border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
			}
			img_div.setAttribute("alt", chats[k].description);
			if (user_id == chats[k].user_id) {
				img_div.setAttribute("class", "right pw-chat-Img");
			} else {
				img_div.setAttribute("class", "pw-chat-Img");
			}
//			<img id="myImg" src="img_snow.jpg" class="pw-chat-Img " alt="Snow" style="width:100%;max-width:300px" onclick="handleClick(this);">
//			<img id="myImg1" src="img_snow.jpg" class="pw-chat-Img " alt="Snow" style="width:100%;max-width:300px" onclick="handleClick(this);">
			
			if (chats[k].attachmentContentType ==="application/pdf") {
			img_anchor.appendChild(img_div)
			}else{
				img_div.setAttribute("onclick","handleClick(this);");
			img_wrapper.appendChild(img_div);
			}
			
		}else if(attachmentType == "VIDEO"){
			
			let src_tag = document.createElement("source");
			if (user_id == chats[k].user_id) {
				src_tag.setAttribute("src", "/media/"+chats[k].description);
				video_tag.setAttribute("style","float:right;border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
			} else {
				src_tag.setAttribute("src", "/media/"+chats[k].description);
				video_tag.setAttribute("style","float:left;border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
			}
			if (user_id == chats[k].user_id) {
				src_tag.setAttribute("type", chats[k].attachmentContentType);
			} else {
				src_tag.setAttribute("type", chats[k].attachmentContentType);
			}
			src_tag.setAttribute("style", "width: 100px;height:100px;");
			video_tag.appendChild(src_tag)
		}
		let p_tag = document.createElement("p");
		let iframe_tag = document.createElement("iframe");
		let object_tag = document.createElement("object");
		
		/*(isAttachment){
			//iframe_tag.textContent = chats[k].description;
			//iframe_tag.setAttribute("src", "http://18.191.20.120:8080/peakaboo/images/img_avatar.png");
			iframe_tag.setAttribute("src","images/img_avatar.png");
			iframe_tag.setAttribute("width", "100px");
			iframe_tag.setAttribute("height", "80px");
			iframe_tag.setAttribute("class","iframe-id");
			
			
			object_tag.setAttribute("src","images/img_avatar.png");
			object_tag.setAttribute("width", "100px");
			object_tag.setAttribute("height", "80px");
			object_tag.setAttribute("class","iframe-j");
		}*/
		//else{
			p_tag.textContent = chats[k].description;
		//}
		let span_tag = document.createElement("span");
		if (user_id == chats[k].user_id) {
			span_tag.setAttribute("class", "time-left");
			span_tag.textContent = "["+chats[k].scheduleDate+"]";
		} else {
			span_tag.setAttribute("class", "time-right");
			let username = chats[k].username!== 'null'&& chats[k].username!==null? chats[k].username.toLowerCase() : ""
			span_tag.textContent = "~ "+username+" ["+chats[k].scheduleDate+"]";
		}
		let usr_img_div = document.createElement("img");
		if (user_id == chats[k].user_id) {
			usr_img_div.setAttribute("src", 'data:image/png;base64,' + userDetails.user.image);
		} else {
			usr_img_div.setAttribute("src", "images/img_avatar.png");
		}
		if (user_id == chats[k].user_id) {
			usr_img_div.setAttribute("class", "usr_img_chat right");
		} else {
			usr_img_div.setAttribute("class", "usr_img_chat");
		}
		if(attachmentType!="IMAGE"|| attachmentType!="VIDEO"){
		//container.appendChild(usr_img_div);
		}
	//	if(isAttachment){
			//container.appendChild(iframe_tag);
			//container.appendChild(object_tag);
		//}else{
		if(attachmentType=="MESSAGE"){
			container.appendChild(p_tag);
		}else if(attachmentType=="IMAGE"){
			if (chats[k].attachmentContentType ==="application/pdf") {
				container.appendChild(img_anchor);
			}else{
				container.appendChild(img_wrapper);
			}
			
			container.appendChild(br_tag);
		}else if(attachmentType=="VIDEO"){
			container.appendChild(video_tag);
			container.appendChild(br_tag);
		}
		//}
		
		container.appendChild(span_tag);
		container_box.appendChild(container);
		outerdiv.appendChild(container_box);
	}
	// FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
	var divContainer = document.getElementById(div_id);
	divContainer.innerHTML = "";
	divContainer.appendChild(outerdiv);
	$(".spinner").hide();
}

function createMediaChatList(chats, user_id, group_id, div_id, isAttachment){

	
	$(".spinner").show();
	// CREATE DYNAMIC Chat Page.
	var outerdiv = document.createElement("div")
	outerdiv.setAttribute("id", "media-container");
	outerdiv.setAttribute("class", "sec-00");
	if(chats.length === 0){
		var h3_tag = document.createElement("h2")
		h3_tag.setAttribute("id", "");
		h3_tag.setAttribute("class", "");
		h3_tag.textContent ="No Media Files";
		outerdiv.appendChild(h3_tag);
	}else{
	for (var k = 0; k < chats.length; k++) {
		let container_box = document.createElement("div");
		container_box.setAttribute("class", "media-box");
		

		let container = document.createElement("div");
		if (user_id == chats[k].user_id) {
			container.setAttribute("class", "darddker");
			//container.setAttribute("width", "30%");
		} else {
			container.setAttribute("class", "container222");
		}
		 let img_wrapper  = document.createElement("div"); 
		let img_anchor  = document.createElement("a");  
		let video_tag  = document.createElement("video");
		let br_tag  = document.createElement("br");
		img_anchor.setAttribute("download", chats[k].description);
		img_anchor.setAttribute("href", "/media/"+chats[k].description);
		video_tag.setAttribute("width", "150px");
		video_tag.setAttribute("height", "150px");
		video_tag.setAttribute("controls","");
		video_tag.setAttribute("style", "border:4px solid white;background: #151515; border-radius:5px;");
		
		let attachmentType= "MESSAGE";
		if(chats[k].attachmentType.includes("image") || chats[k].attachmentType.includes("IMAGE")){
			attachmentType="IMAGE"
			//	container_box.setAttribute("style", "background-color: white !important;");
			//container.setAttribute("style", "background-color: white !important;");
		}else if(chats[k].attachmentType.includes("video") || chats[k].attachmentType.includes("VIDEO")){
			attachmentType="VIDEO"
			//	container_box.setAttribute("style", "background-color: white !important;");
			//container.setAttribute("style", "background-color: white !important;");
		}
		
		if(attachmentType=="IMAGE"){
			let img_div = document.createElement("img");
			if (chats[k].attachmentContentType ==="application/pdf") {
				img_div.setAttribute("src", "images/pdf.png");
				img_div.setAttribute("class", "pdf-img  pw-chat-Img");
			} else {
				img_div.setAttribute("src", "/media/"+chats[k].description);
				img_div.setAttribute("class", "pw-chat-Img");
			}
			img_div.setAttribute("alt",chats[k].description);
			
			//img_div.setAttribute("style", "");
				if (chats[k].attachmentContentType ==="application/pdf") {
					img_anchor.appendChild(img_div)
					}else{
						img_div.setAttribute("onclick","handleClick(this);");
					img_wrapper.appendChild(img_div);
					}
		}else if(attachmentType == "VIDEO"){
			
			let src_tag = document.createElement("source");
			if (user_id == chats[k].user_id) {
				src_tag.setAttribute("src", "/media/"+chats[k].description);
				video_tag.setAttribute("style","float:right;border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
			} else {
				src_tag.setAttribute("src", "/media/"+chats[k].description);
				video_tag.setAttribute("style","float:left;border:4px solid #6b6b6a;background: #151515; border-radius:5px;");
			}
			if (user_id == chats[k].user_id) {
				src_tag.setAttribute("type", chats[k].attachmentContentType);
			} else {
				src_tag.setAttribute("type", chats[k].attachmentContentType);
			}
			src_tag.setAttribute("style", "width: 100px;height:100px;");
			video_tag.appendChild(src_tag)
		}
		let p_tag = document.createElement("p");
		let iframe_tag = document.createElement("iframe");
		let object_tag = document.createElement("object");
		
		/*(isAttachment){
			//iframe_tag.textContent = chats[k].description;
			//iframe_tag.setAttribute("src", "http://18.191.20.120:8080/peakaboo/images/img_avatar.png");
			iframe_tag.setAttribute("src","images/img_avatar.png");
			iframe_tag.setAttribute("width", "100px");
			iframe_tag.setAttribute("height", "80px");
			iframe_tag.setAttribute("class","iframe-id");
			
			
			object_tag.setAttribute("src","images/img_avatar.png");
			object_tag.setAttribute("width", "100px");
			object_tag.setAttribute("height", "80px");
			object_tag.setAttribute("class","iframe-j");
		}*/
		//else{
			p_tag.textContent = chats[k].description;
		//}
		let span_tag = document.createElement("span");
		if (user_id == chats[k].user_id) {
			span_tag.setAttribute("class", "time-left");
			span_tag.textContent = "["+chats[k].scheduleDate+"]";
		} else {
			span_tag.setAttribute("class", "time-right");
			let username = chats[k].username!==null? chats[k].username.toLowerCase() : ""
			span_tag.textContent = "~ "+username+" ["+chats[k].scheduleDate+"]";
		}
		let usr_img_div = document.createElement("img");
		if (user_id == chats[k].user_id) {
			usr_img_div.setAttribute("src", 'data:image/png;base64,' + userDetails.user.image);
		} else {
			usr_img_div.setAttribute("src", "images/img_avatar.png");
		}
		if (user_id == chats[k].user_id) {
			usr_img_div.setAttribute("class", "usr_img_chat right");
		} else {
			usr_img_div.setAttribute("class", "usr_img_chat");
		}
		if(attachmentType!="IMAGE"|| attachmentType!="VIDEO"){
		//container.appendChild(usr_img_div);
		}
	//	if(isAttachment){
			//container.appendChild(iframe_tag);
			//container.appendChild(object_tag);
		//}else{
		if(attachmentType=="MESSAGE"){
			container.appendChild(p_tag);
		}else if(attachmentType=="IMAGE"){
			if (chats[k].attachmentContentType ==="application/pdf") {
				container.appendChild(img_anchor);
			}else{
			container.appendChild(img_wrapper);
			}
			//container.appendChild(br_tag);
		}else if(attachmentType=="VIDEO"){
			container.appendChild(video_tag);
			//container.appendChild(br_tag);
		}
		//}
		
		//container.appendChild(span_tag);
		container_box.appendChild(container);
		outerdiv.appendChild(container_box);
	}
	}
	// FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
	var divContainer = document.getElementById(div_id);
	divContainer.innerHTML = "";
	divContainer.appendChild(outerdiv);
	$(".spinner").hide();

}
function pushChat(data,scheduleDate) {
	$(".spinner").show();
	if($("#user-chat-attachment").val()!=="" ){
		var documentData = new FormData();
		documentData.append('file', $('input#user-chat-attachment.findDocumentOnboarding')[0].files[0]);
		documentData.append('scheduleDate', scheduleDate);
		documentData.append('type', "MESSAGE");
		$.ajax({
			xhr: function() {
			    var xhr = new window.XMLHttpRequest();

			    xhr.upload.addEventListener("progress", function(evt) {
			      if (evt.lengthComputable) {
			        var percentComplete = evt.loaded / evt.total;
			        percentComplete = parseInt(percentComplete * 100);
//			       / $(".percent").html('Uploading..('+percentComplete+'%)');
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
		    	showSnackBar("Document posted.");
		    	getChatList(groupDetails.group.id);
		    },
		});
	}else{
	$.ajax({
		method : "POST",
		url : projectDetails.url + ""+projectDetails.restappname+"/schedule/sharemessage",
		dataType : "json",
		contentType : "application/json",
		data : data
	}).done(function() {
		
		if($("#user-chat-attachment").val()!=="" ){
			var documentData = new FormData();
			documentData.append('file', $('input#user-chat-attachment.findDocumentOnboarding')[0].files[0]);
			documentData.append('scheduleDate', scheduleDate);
			documentData.append('type', "MESSAGE");
			
			$.ajax({
				url : projectDetails.url + ""+projectDetails.restappname+"/schedule/upload/"+userDetails.user.id+"/"+userDetails.group.id,
			    type: 'POST',
			    data: documentData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    success: function (response) {
			    	$(".spinner").hide();
			    	showSnackBar("Document posted.");
			    }
			});
		}
	}).always(function() {
		//showSnackBar("Loading Messages...");
		getChatList(groupDetails.group.id);
		//scrollToBottom('messages-container','div')
	});
	}
}

function getChatList(groupId) {
	groupDetails.group.id= groupId
	$(".spinner").show();
	$.ajax({
		method : "GET",
		url : projectDetails.url + ""+projectDetails.restappname+"/schedule/getmessagelist/"+groupId,
		dataType : "json",
		contentType : "application/json"//,
			//data : '{"type":"MESSAGE"}'
	}).done(function(chatList) {
		//alert("Data Saved: " + chatList);
		//showSnackBar("Loading Messages...")
		createChatList(chatList, userDetails.user.id, groupId , "chatList",true)
		//scrollToBottom('messages-container','div')
		scrollSmoothToBottom('chatList')
		//$(".iframe-id").contents().find("img").attr("style","width:100%;height:100%")
		$(".spinner").hide();
		//scrollToBottom();
	});

}
function getMediaChatList(groupId) {
	groupDetails.group.id= groupId
	$(".spinner").show();
	$.ajax({
		method : "POST",
		url : projectDetails.url + ""+projectDetails.restappname+"/schedule/getmessagesoftype/"+groupId,
		dataType : "json",
		contentType : "application/json",
		data : '{"type":"MESSAGE","isAttachment":"true"}'
	}).done(function(mediaList) {
		//alert("Data Saved: " + chatList);
		//showSnackBar("Loading Messages...")
		createMediaChatList(mediaList, userDetails.user.id, userDetails.group.id, "media-outer-div",true)
		//scrollToBottom('messages-container','div')
		//scrollSmoothToBottom('chatList')
		//$(".iframe-id").contents().find("img").attr("style","width:100%;height:100%")
		$(".spinner").hide();
		//scrollToBottom();
	});

}

function showSnackBar(msg) {
	//var x = document.getElementById("snackbar");
	$('#snackbar').html(msg);
	$('#snackbar').addClass('show');
	setTimeout(function() {
		$('#snackbar').removeClass('show')
	}, 3000);
}
function showProgressSnackBar(msg) {
	$('#snackbar').html("<i class='fa fa-circle-o-notch fa-spin'></i> "+msg);
	$('#snackbar').addClass('show');
}
function hideSnackBar() {
		$('#snackbar').removeClass('show')
}
function sssscrollToBottom() {
	window.scrollTo(0, document.body.scrollHeight);
}

function getGroupList() {
	$(".spinner").show();
	$.ajax({
		method : "GET",
		url : projectDetails.url + ""+projectDetails.restappname+"/group/getGroupList",
		contentType : "application/json"
	}).done(function(groupList) {
		// alert( "success" );
		//showSnackBar("Loading Group List...")
		groupDetails.groups = groupList;
		createDynamiTable(groupList, "showData");
		paginateTable("grp-table", 5);
		$(".spinner").hide();
		// return groupList;
	});
}

function getGroups(){
	$(".spinner").show();
	$.ajax({
		method : "GET",
		url : projectDetails.url + ""+projectDetails.restappname+"/group/getGroupList",
		contentType : "application/json"
	}).done(function(groupList) {
		groupDetails.groups = groupList;
		$(".spinner").hide();
		// return groupList;
	});
}


function getUserList(groupId) {
	groupDetails.group.id = groupId
	$(".spinner").show();
	$.ajax({
		method : "GET",
		url : projectDetails.url + ""+projectDetails.restappname+"/user/getUserList/"+groupId,
		contentType : "application/json"
	}).done(function(userList) {
		// alert( "success" );
		//showSnackBar("Loading User List...")
		if(userList.length!=0){
			createDynamiTable(userList, "showUserData");
			paginateTable("grp-table", 5);
		}else{
			$("#showUserData").html("")
			$("#pages").hide();
		}
		
		$(".spinner").hide();
		// return groupList;
	});
}
function generateGroupNamesOptions(div_id) {
	$(".spinner").show();
//	$.ajax({
//		method : "GET",
//		url : projectDetails.url + ""+projectDetails.restappname+"/group/getGroupList",
//		contentType : "application/json"
//	}).done(function(groups) {
	  let groups = groupDetails.groups;
		var group_select = document.getElementById(div_id);

		for (group in groups) {
			let option = document.createElement("option");
			option.setAttribute("value", groups[group].id);
			option.textContent = groups[group].name;
			group_select.appendChild(option);
		}
		$(".spinner").hide();
//	});

}

function addStarEven(rating_div) {
	var rating = document.getElementById(rating_div);
	var stars = rating.getElementsByClassName("fa");

	for (var i = 0; i < stars.length; i++) {
		stars[i].addEventListener("mouseover", (function(k) {
			return function() {
				var a = checkedStars(stars, k)
			}
		})(i))
	}

}
function checkedStars(stars, l) {
	removeChecked(stars);
	for (var x = 0; x <= l; x++) {

		if (stars[x]) {
			stars[x].className += " fa fa-star checked";
		}

	}
	return x;
}

function removeChecked(stars) {
	for (var i = 0; i < stars.length; i++) {
		stars[i].className = "fa fa-star";
	}
}

function getQuestions() {
	$(".spinner").show();
	$.ajax({
		method : "GET",
		url : projectDetails.url + ""+projectDetails.restappname+"/fbquestion/getQuestionList",
		dataType : "json",
		contentType : "application/json"
	//data : data //'{"name":"' + $("#pw_grp_name").val() + '"}'
	}).done(function(questions) {

		//alert("Data Saved: " + chatList);
		//showSnackBar("Loading Questions...")
		createDynamiForm(questions, "showFeedBackData", function(){
			//generateEvents();
		});
		$(".spinner").hide();
		//scrollToBottom('messages-container','div')
		//scrollSmoothToBottom('chatList')
		//scrollToBottom();
	});

}
function generateEvents(){
	$('.pw_ratings_stars').hover(
	        // Handles the mouseover
	        function() {
	            $(this).prevAll().andSelf().addClass('ratings_over');
	            $(this).nextAll().removeClass('ratings_vote'); 
	        },
	        // Handles the mouseout
	        function() {
	            $(this).prevAll().andSelf().removeClass('ratings_over');
	        }
	    );
	       $('.pw_rate_widget').each(function(i) {
	        var widget = this;
	        var out_data = {
	            widget_id : $(widget).attr('id'),
	            fetch: 1
	        };
	       
	    });
	        
	    var x= {
	        "widget_id"     : "r1",
	        "number_votes"  : 129,
	        "total_points"  : 344,
	        "dec_avg"       : 2.7,
	        "whole_avg"     : 3
	    }
	        function set_votes(widget) {
	            
	           // var avg = $(widget).data('fsr').whole_avg;
	            ///var votes = $(widget).data('fsr').number_votes;
	            //var exact = $(widget).data('fsr').dec_avg;
	            
	            //$(widget).find('.star_' + ).prevAll().andSelf().addClass('ratings_vote');
	            $(widget).find('.star_' + avg).nextAll().removeClass('ratings_vote'); 
	            $(widget).find('.total_votes').text( votes + ' votes recorded (' + exact + ' rating)' );
	        }
		$('.pw_ratings_stars').bind('click', function() {
	        var star = this;
	        var widget = $(this).parent();
	        var index = Array.prototype.indexOf.call(this.parentNode.children, this);
	        var pos= index+1;
	        var clicked_data = {
	            clicked_on : $(star).attr('class'),
	            widget_id : widget.attr('id')
	        };
	        var out_data = {
	                widget_id : $(widget).attr('id'),
	                fetch: 1
	            };
	            $(widget).find('.star_' + pos).prevAll().andSelf().addClass('ratings_vote');
	            $(widget).find('.star_' + pos).nextAll().removeClass('ratings_vote');
	       });
		$("#feedback-collection-form").submit(function(e){
			e.preventDefault();
			showSnackBar("Thanks for your feedback!");
		})
}
function SelectElement(id, valueToSelect)
{    
	$("#"+id).val(valueToSelect);
}

function shuffelWord (word){
    var shuffledWord = '';
    word = word.split('');
    while (word.length > 0) {
      shuffledWord +=  word.splice(word.length * Math.random() << 0, 1);
    }
    return shuffledWord;
}

function fillUserProfile(userDetails){
	$("#fName").val(userDetails.user.fName)
	$("#lName").val(userDetails.user.lName)
	$("#oEmail").val(userDetails.user.oEmail)
	$("#pEmail").val(userDetails.user.pEmail)
	$("#designation").val(userDetails.user.designation)
	//$("#sc-date").val(userDetails.user)
	$("#phone").val(userDetails.user.phone)
	$("#organization").val(userDetails.user.organization)
	$("#dob").val(userDetails.user.dob)
	$("#aboutYou").val(userDetails.user.aboutYou)
			var divContainer = document.getElementById("profile-img-tag");
			if(userDetails.user.image!==null){
				divContainer.setAttribute("src", 'data:image/png;base64,' + userDetails.user.image);
			}
			let divContainer1 = document.getElementById("uploadpic-div-outer");
			//let iframe = document.createElement("iframe");
			//iframe.setAttribute("src", 'data:image/png;base64,' + userDetails.user.image);
			//divContainer1.appendChild(iframe)
	return;
}
function fillMailCongfigDetails(mailConfig){
	if(mailConfig){
		$("#host").val(mailConfig.host)
		$("#port").val(mailConfig.port)
		$("#server-email").val(mailConfig.username)
		$("#server-password").val(mailConfig.password)
	}
	return;
}
function validateEmail($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  return emailReg.test( $email );
	}
function validatePhoneNumber(phoneNumber) {
  var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  return (phoneNumber.match(phoneno))
}