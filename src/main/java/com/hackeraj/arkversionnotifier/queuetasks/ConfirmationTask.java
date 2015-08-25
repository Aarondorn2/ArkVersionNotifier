package com.hackeraj.arkversionnotifier.queuetasks;

import java.util.Arrays;

import com.hackeraj.arkversionnotifier.mocking.MockEmailManager;
import com.hackeraj.arkversionnotifier.mocking.MockingUtils;
import com.hackeraj.arkversionnotifier.utils.EmailBodies;
import com.hackeraj.arkversionnotifier.utils.EmailManager;

public class ConfirmationTask extends QueueTask {
	
	private static final EmailManager emailManager = 
			!MockingUtils.isMocking()
			? new EmailManager()
			: new MockEmailManager();

	@Override
	public void run() {
		String email = params[0]; //get email from params
		String subject = "Ark Notification Subscription Confirmation";
		
		emailManager.sendMail(subject, EmailBodies.EMAIL_BODY_CONFIRMATION, Arrays.asList(email));
	}

}
