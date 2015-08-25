package com.hackeraj.arkversionnotifier.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {
	private static final Logger logger = Logger.getLogger(EmailManager.class.getName());

	public void sendMail(String subject, String body, List<String> recipients) {
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		Message msg = null;
		try {
			InternetAddress fromAddr = new InternetAddress(Globals.EMAIL_SENDER_ADDRESS, Globals.EMAIL_SENDER_NAME);

		    for (String recipient : recipients) {
			    msg = new MimeMessage(session); //create new message for each recipient

			    msg.setFrom(fromAddr);
			    msg.setSubject(subject);
		    	
			    msg.setContent(body.replace("::email", recipient), "text/html; charset=utf-8");
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			    Transport.send(msg);	
		    }
		} catch (AddressException e) {
			logger.log(Level.SEVERE, "sendMail -> problem with the recipient email address", e);
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "sendMail -> problem with sending email", e);
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "sendMail -> problem with the sender email address", e);
		} 	
	}
	
}
