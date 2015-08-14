package com.hackeraj.arkversionnotifier.notificationjob;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LocalRunner {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		try {
			NotificationJobRunner.start();
			
			Thread.sleep(13000);
			
			System.out.println(NotificationJobRunner.checkStatus());
			
			Thread.sleep(13000);
			
			NotificationJobRunner.stop();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
