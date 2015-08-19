package com.hackeraj.arkversionnotifier.mocking;

import java.io.IOException;

import com.hackeraj.arkversionnotifier.job.AVNJob;
import com.hackeraj.arkversionnotifier.servlets.SubscriptionServlet;

public class MockRunner {

	public static void main(String[] args) {
		String email = "aarondorn2@gmail.com";
		String email2 = "test2@nothing.net";
		MockingUtils.setMocking(true);
		
		subscribe(email);
		subscribe(email2);
		
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		
		unsubscribe(email);

		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		AVNJob.execute();
		
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
