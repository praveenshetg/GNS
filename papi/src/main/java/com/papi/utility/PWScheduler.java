package com.papi.utility;
import java.util.Properties;
import java.util.TimerTask;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class PWScheduler extends TimerTask {

	@Override
	public void run() {
		System.out.println("firing event.....");
		sendMail();
	}

	public void sendMail() {

		/*
		 * String msg=""; String email="";
		 */

		String title = "Expense Management";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");


        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "freeinternship1@gmail.com", "freeintern");
                    }
                });

		// compose message

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("freeinternship1@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"praveenshetg@gmail.com"));
			message.setSubject(title);
			message.setText("zsasas" + " " + "asasas");

			// send message
			Transport.send(message);
			System.out.println("message sent successfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
