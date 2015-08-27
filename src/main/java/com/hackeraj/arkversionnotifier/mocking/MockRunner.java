package com.hackeraj.arkversionnotifier.mocking;

import java.io.IOException;

import com.hackeraj.arkversionnotifier.job.AVNJob;
import com.hackeraj.arkversionnotifier.servlets.SubscriptionServlet;

public class MockRunner {

	public static void main(String[] args) {
		String emailToSubscribe = "aarondorn2@gmail.com";
		MockingUtils.setMocking(true);
		MockingUtils.setSendEmailsWhileMocking(true);
		
		subscribe(emailToSubscribe);
		
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		
		unsubscribe(emailToSubscribe);

		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		AVNJob.startAVNJob();
		
	}
	
	private static void subscribe(String email) {
		SubscriptionServlet servlet = new SubscriptionServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		String[] values = {"upcoming","eta","available"};
		
		request.setParameter("email", email);
		request.setParameterValues("subscriptionChoices", values);
		
		try {
			servlet.doPost(request, new MockHttpServletResponse());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void unsubscribe(String email) {
		SubscriptionServlet servlet = new SubscriptionServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setParameter("email", email);
		request.setParameter("type", "unsubscribe");
		try {
			servlet.doGet(request, new MockHttpServletResponse());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
