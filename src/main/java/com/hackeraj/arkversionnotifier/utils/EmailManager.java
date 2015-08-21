package com.hackeraj.arkversionnotifier.utils;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {

	public void sendMail(String subject, String body, List<String> recipients) {
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);

		try {
		    Message msg = new MimeMessage(session);

		    msg.setFrom(new InternetAddress("notifications@arkversionnotifier.appspot.com"));
		    msg.setSubject(subject);
		    
		    for (String recipient : recipients) {
		    	
			    msg.setContent(body.replace("::email", recipient), "text/html; charset=utf-8");
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			    Transport.send(msg);	
		    }
		} catch (AddressException e) {
		    e.printStackTrace();
		} catch (MessagingException e) {
		    e.printStackTrace();
		} 	
	}
	
}
