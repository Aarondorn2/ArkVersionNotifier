package com.hackeraj.arkversionnotifier.notificationjob;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LocalRunner {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		try {
			AVNJobRunner.start();
			
			Thread.sleep(13000);
			
			System.out.println(AVNJobRunner.checkStatus());
			
			Thread.sleep(13000);
			
			AVNJobRunner.stop();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
