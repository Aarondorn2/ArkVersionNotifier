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

import com.hackeraj.arkversionnotifier.mocking.MockingUtils;

public class EmailManager {
	private static final Properties properties = 
			!MockingUtils.isMocking()
			? new Properties() //use empty properties with app engine
			: getLocalHostProperties();

	public void sendMail(String subject, String body, List<String> recipients) {
		Session session = Session.getDefaultInstance(properties, null);
		String unsubscribeLink = null;

		try {
		    Message msg = new MimeMessage(session);

		    msg.setFrom(new InternetAddress("notifications@arkversionnotifier.appspot.com"));
		    msg.setSubject(subject);
		    
		    for (String recipient : recipients) {
		    	unsubscribeLink = "<a href=\"arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=" + recipient + "\">unsubscribe</>.";
		    	
			    msg.setContent(body + unsubscribeLink, "text/html; charset=utf-8");
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			    Transport.send(msg);	
		    }
		} catch (AddressException e) {
		    e.printStackTrace();
		} catch (MessagingException e) {
		    e.printStackTrace();
		} 	
	}
	
	private static Properties getLocalHostProperties() {
		Properties props = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", "localhost");
		
		return props;
	}
}
