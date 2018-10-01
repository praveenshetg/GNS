package com.papi.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.papi.dao.SchedulerDao;
import com.papi.dao.SettingsDao;
import com.papi.dao.UserDao;
import com.papi.entity.Conversation;
import com.papi.entity.MailConfiguration;
import com.papi.utility.PWScheduler;
import com.papi.utility.PWUtility;
import com.papi.wrapper.PWSchedulerWrapper;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {
	@Autowired
	SchedulerDao schedulerDao;
	@Autowired
	UserDao userDao;
	@Autowired
	SettingsDao settingsDao;
	
	final static Logger logger = Logger.getLogger(SchedulerController.class);
	
	@RequestMapping()
	public String welcomePage() {
		return "Welcome to Scheduler.";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public PWSchedulerWrapper addEvent(@RequestBody PWSchedulerWrapper schedule) {
		logger.info("inside method addEvent()");
		Timer t1 = new Timer();
		PWScheduler ss = null;
		HashMap<String, String> mailConfig = new HashMap<String, String>();
		HashMap<String, Conversation> eventDetails = new HashMap<String, Conversation>();
		MailConfiguration mailConfiguration = getmailConfigDegtails();
		mailConfig.put("host", mailConfiguration.getHost());
		mailConfig.put("port", mailConfiguration.getPort());
		mailConfig.put("username", mailConfiguration.getUsername());
		mailConfig.put("password", mailConfiguration.getPassword());
		mailConfig.put("subject",schedule.getSubject());
		mailConfig.put("msg",schedule.getDescription());
		
		String groups = schedule.getGroups();
		List<Long> groupIds = new ArrayList<Long>();
		for (String s : groups.split(","))
			groupIds.add(Long.parseLong(s));
		for(Long groupId: groupIds){
			eventDetails.put("conversation", addConversation(schedule, groupId));
			ss = new PWScheduler(eventDetails, mailConfig, schedulerDao);
			ss.setRecipientList(getUserListForGroup(groupId));
		}

		
		String scDate = schedule.getScheduleDate();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

		Date date = null;
		try {
			date = format.parse(scDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		t1.schedule(ss, date);
		logger.info("exit method addEvent()");
		return schedule;
	}
	public MailConfiguration getmailConfigDegtails(){
		logger.info("inside method getmailConfigDegtails()");
		return settingsDao.getMailConfiguration(new Long(1));
	}
	public Conversation addConversation(PWSchedulerWrapper schedule, Long groupId){
		logger.info("inside method addConversation()");
		Conversation conv = new Conversation();
		conv.setDescription(schedule.getDescription());
		conv.setScheduleDate(schedule.getScheduleDate());
		conv.setUsername(schedule.getUsername());
		conv.setGroup_id(groupId);
		conv.setUser_id(schedule.getUser_id());
		conv.setStatus("REQUESTED");
		conv.setType("EVENT");
		conv.setAttachmentType("TEXT");
		conv.setAttachmentContentType("MESSAGE");
		schedulerDao.addConversation(conv);
		logger.info("exit method addConversation()");
		return conv;
	}
	public ArrayList<String> getUserListForGroup(Long groupId){
		logger.info("getUserListForGroup("+groupId+")");
		ArrayList<String> recipientList= null;
		recipientList = (ArrayList<String>) userDao.getAllUsersMailId(groupId);
		logger.debug(recipientList);
		return recipientList;
	}
	@RequestMapping(value = "/sharemessage", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void sharemessage(@RequestBody Conversation conversation) {
		conversation.setStatus("COMPLETED");
		conversation.setType("MESSAGE");
		conversation.setAttachmentType("TEXT");
		conversation.setAttachmentContentType("Text/Text");
		conversation.setIsAttachment(false);
		schedulerDao.addConversation(conversation);
	}

	@RequestMapping(value = "/getmessagelist/{groupId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Conversation> getConversationListForGroup(@PathVariable("groupId") Long groupId) {
		System.out.println("getConversationListForGroup");

		return schedulerDao.getAllConversationForGroup(groupId);
	}

	@RequestMapping(value = "/getmessagesoftype/{groupId}", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Conversation> getConversationListForGroupOfType(@PathVariable("groupId") Long groupId) {
		System.out.println("getConversationListForGroup");

		return schedulerDao.getAllConversationForGroup(groupId, "MESSAGE", "MEDIA", true);
	}
	
	@Bean(name = "multipartResolverForChat")
	@RequestMapping(value = "/upload/{userId}/{groupId}", method = RequestMethod.POST)
	public Boolean fileUpload(@PathVariable("userId") Long userId,@PathVariable("groupId") Long groupId,@RequestParam("file") MultipartFile file,@RequestParam("scheduleDate") String scheduleDate, @RequestParam("type") String type) throws IOException {
		String originalFilename = file.getOriginalFilename();
		System.out.println(originalFilename);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		System.out.println(timeStamp);
		String destinationPath ="/opt/apache-tomcat-8.5.30/webapps/media/";
		//String destinationPath ="/Users/pshet/tempMedia/";
		// Save file on system
		if (!file.getOriginalFilename().isEmpty()) {
			Conversation conv = new Conversation();
			conv.setDescription(timeStamp+"-"+originalFilename);
			conv.setScheduleDate(scheduleDate);
			conv.setGroup_id(groupId);
			conv.setUser_id(userId);
			if(type.equals("MESSAGE")){
				conv.setStatus("COMPLETED");	
			}else{
			conv.setStatus("REQUESTED");
			}
			conv.setType(type.toUpperCase());
			conv.setAttachmentType(PWUtility.getContentType(file.getContentType()));
			conv.setAttachmentContentType(file.getContentType());
			conv.setIsAttachment(true);
			try{
				schedulerDao.addConversation(conv);
			}catch(Exception e){
				logger.error(e);
			}
			
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(new File(destinationPath, timeStamp+"-"+originalFilename)));

			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
			
			//converting to byte array to save in DB
//		   InputStream inputStream = file.getInputStream();
//		   ByteArrayOutputStream out = new ByteArrayOutputStream();
//			int read = 0;
//			byte[] bytes = new byte[1024];
//			while ((read = inputStream.read(bytes)) != -1) {
//				out.write(bytes, 0, read);
//			}
//			System.out.println(out.toByteArray());
			//user.setImage()
		} else {
			return false;
		}
		return true;
	}
	public Conversation updateConversation(Conversation conversation) {
		System.out.println("getConversationListForGroup");

		return schedulerDao.updateConversation(conversation);
	}
}
