package com.hackeraj.arkversionnotifier.mocking;

import com.hackeraj.arkversionnotifier.utils.EmailManager;

public class MockEmailManager extends EmailManager {
	
	@Override
	public void sendMail(String subject, String body, String recipient) {
		System.out.println("sent email to: " + recipient);
		System.out.println("subject: " + subject);
		System.out.println("body: " + body);
		
	}
}
