package com.hackeraj.arkversionnotifier.notificationjob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NotificationJob implements Job {
	private static boolean normalSchedule = true;
	private static final String arkBarURL = "https://api.ark.bar/v1/version";

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JSONObject json = getJSON(arkBarURL);
		System.out.println(json.toString());

		if (normalSchedule && isUpcomingVersionAvailable(json)) {
			//check every 20 minutes until update is available!
			NotificationJobRunner.scheduleJob(20, true);
			normalSchedule = false;
			notifyUpcoming(json);
		} else
		if (isUpdateAvailable(json)) {
			notifyAvailable(json);
			
			if (!normalSchedule) {
				//check back to normal schedule
				NotificationJobRunner.scheduleJob(60, true);
				normalSchedule = true;
			}
		} 
	}

	private static boolean isUpcomingVersionAvailable(JSONObject json) {
		boolean isAvailable = false;
		try {
			JSONObject upcoming = json.getJSONObject("upcoming");
			if (!JSONObject.NULL.equals(upcoming.get("version"))) { //if version is not null, then a new one is available! :D
				isAvailable = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return isAvailable;
	}

	private static boolean isUpdateAvailable(JSONObject json) {
		boolean isAvailable = false;
		try {
			String currentVersion = (String) json.get("current");
			System.out.println(currentVersion.toString());
			String previousVersion = getPreviousVersion();
			if (previousVersion.equals(currentVersion)) {
				isAvailable = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return isAvailable;
	}

	private static void notifyAvailable(JSONObject json) {
		sendEmails("");		
	}

	private static void notifyUpcoming(JSONObject json) {
		sendEmails("");
	}

	private static void sendEmails(String emailBody) {
		//update version in DB also
		//through Mandrill?
	}
	
	private static String getPreviousVersion() {
		String prevVersion = "";
		
		//go to DB for prevVersion
		
		return prevVersion;
	}
	
	
	private static boolean local = false;
	private static JSONObject getJSON(String stringUrl) {
		if(local) {
			try {
				return new JSONObject("{\"current\":195.2,\"upcoming\":{\"version\":\"196.0\",\"status\":\"ETA: Tuesday Morning EDT\"}}");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		stringUrl = "http://www.google.com";
		
		JSONObject json = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		String output = "";
	    StringBuilder sb = new StringBuilder();
	    
		try {
			System.out.println("attempting to get json from URL: " + stringUrl);

			URL url = new URL(stringUrl);
			conn = (HttpURLConnection) url.openConnection();
			System.out.println("got conn");

			InputStream is = conn.getInputStream();
			System.out.println("??");
			
			br = new BufferedReader(
                    new InputStreamReader(is));
			System.out.println("?");
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}

			System.out.println(sb.toString());
			
//		    json = new JSONObject(sb.toString());
//			System.out.println("got json: " + json.toString());

			br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (br != null) {
					br.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
		}
		
		return json;
	}
	
}
