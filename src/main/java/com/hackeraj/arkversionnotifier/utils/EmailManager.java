package com.hackeraj.arkversionnotifier.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {

	public void sendMail(String subject, String body, String recipient) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		    Message msg = new MimeMessage(session);

		    msg.setFrom(new InternetAddress("notifications@arkversionnotifier.appspot.com"));
		    msg.setSubject(subject);
		    msg.setContent(body, "text/html; charset=utf-8");
		    
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		    
		    Transport.send(msg);

		} catch (AddressException e) {
		    e.printStackTrace();
		} catch (MessagingException e) {
		    e.printStackTrace();
		} 
		
	}
}
