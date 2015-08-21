package com.hackeraj.arkversionnotifier.mocking;

import java.util.List;

import com.hackeraj.arkversionnotifier.utils.EmailManager;

public class MockEmailManager extends EmailManager {
	
	@Override
	public void sendMail(String subject, String body, List<String> recipients) {
		String unsubscribeLink = null;
		
		//log to console
		System.out.println("subject: " + subject);
		
		for (String recipient : recipients) {
	    	unsubscribeLink = "<a href=\"arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=" + recipient + "\">unsubscribe</>.";
	    	
			System.out.println("sent email to: " + recipient);
			System.out.println("body: " + body + unsubscribeLink);
		}
	}
}
