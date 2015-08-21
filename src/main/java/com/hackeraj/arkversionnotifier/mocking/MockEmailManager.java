package com.hackeraj.arkversionnotifier.mocking;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hackeraj.arkversionnotifier.utils.EmailManager;
import com.hackeraj.arkversionnotifier.utils.Pass;

public class MockEmailManager extends EmailManager {
	
	@Override
	public void sendMail(String subject, String body, List<String> recipients) {
		System.out.println("???????????????");
		
		if (!MockingUtils.isSendEmailsWhileMocking()) {
			//log to console
			System.out.println("subject: " + subject);
			System.out.println("body: " + body);
			
			for (String recipient : recipients) {
				System.out.println("sent email to: " + recipient);
			}
			System.out.println(":( :( :( :( :( :( :'(");
			
		} else {
			System.out.println("!!!!!!!!!!!!!!!");

			String unsubscribeLink = null;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			//sending emails while mocking requires you to set up a GMAIL userId / password in the Pass class
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Pass.getGmailUser(), Pass.getGmailPass());
				}
			  });

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
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
