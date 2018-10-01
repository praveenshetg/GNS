package com.papi.utility;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.papi.controller.SchedulerController;
import com.papi.entity.MailConfiguration;
import com.papi.entity.User;

public class PWUtility {
	final static Logger logger = Logger.getLogger(PWUtility.class);
	// This Method returns set of email id of groups
	public List<String> getUserEmailIdOfGroups(List<Long> groups) {

		return new ArrayList<String>();
	}

	public Date formatSchedulerDate(String date) {

		return new Date(0);
	}

	public static List<User> createUserFromUserEmail(List<String> users, Long groupId) {
		List<User> usList = new ArrayList<User>();
		for (String emailId : users) {
			String username = generateUserName(emailId);
			String password = generateRandomPasswordForUser(username);
			
			System.out.println("user Created for "+emailId+" with username : "+username+ " and password : "+password);
			//User user = new User(username, password, emailId);
			usList.add(new User(username, password, emailId,  groupId ));
		}

		//usList.add(new User(username, password, emailId, groupId));
		return usList;
	}

	public static String generateRandomPasswordForUser(String username) {
		StringBuilder password = new StringBuilder();
		 password.append(shuffle(username.substring(0,username.length()-2)));
		 password.append(generateRandomNumber().toString());
		
				return password.toString();

	}

	public static String generateUserName(String emailId) {
		String regex = "(\\w+)@";
		String username = "";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(emailId);
        if(m.find())
        	username =  m.group(1);
        else{
        	username=generateRandomNumber().toString();
        }
        //if username exists in DB re generate new one
		return username;
	}

	public User createUserbyEmailId(String emailId) {

		return new User();
	}

	public static Integer generateRandomNumber() {
		// create instance of Random class
		Random rand = new Random();

		// Generate random integers in range 0 to 9999
		return rand.nextInt(10000);
	}
	
	public static String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
       return output.toString();
    }
	
	public static void sendMail(String subject, String content,String toMailId, MailConfiguration mailConfig ) {
		final String username = mailConfig.getUsername();
		final String password = mailConfig.getPassword();
		Properties props = new Properties();
		props.put("mail.smtp.host", mailConfig.getHost());
		props.put("mail.smtp.socketFactory.port", mailConfig.getPort());
		props.put("mail.smtp.port", mailConfig.getPort());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		System.out.println(props);
		try{
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
		
		// compose message

		try {
			MimeMessage message = new MimeMessage(session);
			String personName = "Peakaboo";
			InternetAddress fromAddress = null;
			try {
				fromAddress = new InternetAddress(username, personName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setFrom(fromAddress);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toMailId));
			message.setSubject(subject);
			message.setContent(content, "text/html");

			// send message
			Transport.send(message);
			System.out.println("message sent successfully to :"+toMailId);

		} catch (MessagingException e) {
			System.out.println("Failed to eeesend email to the user :"+toMailId);
			logger.error(e);
		}
		}catch(Exception e){
			logger.error(e);
		}

	}
	
	public static String getContentType(String contentType){
		String returnContentType = "";
		switch(contentType){
		case "image/png" : returnContentType = "IMAGE"; break;
		case "image/jpeg" : returnContentType = "IMAGE"; break;
		case "image/jpg" : returnContentType = "IMAGE"; break;
		case "image/gif" : returnContentType = "IMAGE"; break;
		case "video/mp4" : returnContentType = "VIDEO"; break;
		case "application/pdf" : returnContentType = "IMAGE"; break;
		default: returnContentType = "TEXT"; break;
		}
		return returnContentType;
		
		
	}

}
