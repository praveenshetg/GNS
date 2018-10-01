package com.papi.utility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.papi.controller.SchedulerController;
import com.papi.dao.SchedulerDao;
import com.papi.entity.Conversation;
@Service
public class PWScheduler extends TimerTask {
	final static Logger logger = Logger.getLogger(PWScheduler.class);
	@Autowired
	private SchedulerDao schedulerDao;

	private HashMap<String, String> mailConfig;
	private HashMap<String, Conversation> eventDetails;
	private String subject;
	private String msg;
	private ArrayList<String> recipientList;
	

	public HashMap<String, String> getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(HashMap<String, String> mailConfig) {
		this.mailConfig = mailConfig;
	}

	public HashMap<String, Conversation> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(HashMap<String, Conversation> eventDetails) {
		this.eventDetails = eventDetails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<String> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(ArrayList<String> recipientList) {
		this.recipientList = recipientList;
	}
	
	//TODO Autowiring 
	public PWScheduler( HashMap<String, Conversation> eventDetails,HashMap<String, String> mailConfig,SchedulerDao schedulerDao){
	//String subject S.tring msg,ArrayList<String> recipientList) {
		this.eventDetails = eventDetails;
		this.mailConfig = mailConfig;
		this.subject = mailConfig.get("subject");
		this.msg = mailConfig.get("msg");
		this.schedulerDao = schedulerDao;
	}
	
	@PostConstruct
    public void init() {
      // getPendingTasks();
      // updatePendingTasks();
       
    }

	@Override
	public void run() {
		System.out.println("firing event.....");
		updateScheduledEvent(this.eventDetails);
		try {
			sendMail();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateScheduledEvent(HashMap<String, Conversation> eventDetails){
		System.out.println("updateScheduledEvent--->");
		Conversation conv =  eventDetails.get("conversation");
		System.out.println(conv);
		conv.setStatus("COMPLETED");
		//ApplicationContext context = new ClassPathXmlApplicationContext("spring-servlet.xml");
		 //SchedulerController controller = (SchedulerController) context.getBean("schedulerController");
		//SchedulerController controller = new SchedulerController();
		// controller.updateConversation(conv);
		schedulerDao.updateConversation(conv);
	}
	public void sendMail() throws UnsupportedEncodingException {

		/*
		 * String msg=""; String email="";
		 */
		final String username = this.mailConfig.get("username");
		final String password = this.mailConfig.get("password");
		Properties props = new Properties();
//		 props.put("mail.smtp.host", "smtp.gmail.com");
//		 props.put("mail.smtp.socketFactory.port", "465");
//		 props.put("mail.smtp.socketFactory.class",
//		 "javax.net.ssl.SSLSocketFactory");
//		 props.put("mail.smtp.auth", "true");
//		 props.put("mail.smtp.port", "465");
//{mail.smtp.port=465, mail.smtp.socketFactory.port=465, mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory, mail.smtp.auth=true, mail.smtp.host=smtp.gmail.com}
//{mail.smtp.port=465, mail.smtp.socketFactory.port=465, mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory, mail.smtp.auth=true, mail.smtp.host=smtp.gmail.com}
		props.put("mail.smtp.host", this.mailConfig.get("host"));
		props.put("mail.smtp.socketFactory.port", this.mailConfig.get("port"));
		props.put("mail.smtp.port", this.mailConfig.get("port"));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		logger.info("mail props"+props);
		try{
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
				// "freeinternship1@gmail.com", "freeintern");
			}
		});

		// compose message

		try {
			MimeMessage message = new MimeMessage(session);
			String personName = "Peakaboo";
			InternetAddress fromAddress = new InternetAddress(username, personName);
			message.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[recipientList.size()];

			// To get the array of toaddresses
			for (int i = 0; i < recipientList.size(); i++) {
				toAddress[i] = new InternetAddress(recipientList.get(i));
			}

			// Set To: header field of the header.
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(
			// "praveenshetg@gmail.com"));
			message.setSubject(mailConfig.get("subject"));
			// message.setText("Hi Sir/Madam,\n\n You have requested a password
			// reset, please follow the link below to reset your password." +
			// "\n" + "Please ignore this email if you did not request a
			// password change.\n\n https://fordammy.com/papi/passwordreset/");
			message.setContent(mailConfig.get("msg"),"text/html");
			// send message
			Transport.send(message);
			logger.info("message sent successfully to :"+recipientList);

		} catch (MessagingException e) {
			logger.error("Failed to send email to the user :"+recipientList);
			//throw new RuntimeException(e);
			logger.error(e);
		}
		}catch(Exception e){
			logger.error(e);
			logger.error(e.getStackTrace());
		}

	}

}
