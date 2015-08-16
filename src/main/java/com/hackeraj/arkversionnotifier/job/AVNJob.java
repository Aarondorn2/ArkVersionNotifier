package com.hackeraj.arkversionnotifier.job;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hackeraj.arkversionnotifier.datamodel.ARKVersion;
import com.hackeraj.arkversionnotifier.datamodel.StoredJSON;

//TODO: finish the codings.

public class AVNJob implements Job {
	private static boolean normalSchedule = true;
	private static final String arkBarURL = "https://api.ark.bar/v1/version";

	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		boolean isStoredVersionUpdateNeeded = false;
		
		JSONObject json = getJSON(arkBarURL);
		ARKVersion newVersion = buildVersionFromJSON(json);
		ARKVersion storedVersion = getStoredVersion();
		
		//if there isn't a stored version, simply update the stored version.
		if (storedVersion == null) {
			isStoredVersionUpdateNeeded = true;
		} else {
			//if first time an upcoming version is announced
			if (normalSchedule && !newVersion.getUpcomingVersion().getVersionNumber().equals("null")) {
				notifyUpcoming(newVersion, storedVersion);
				isStoredVersionUpdateNeeded = true;
				
				//check every 20 minutes until update is available!
				AVNJobRunner.scheduleJob(20, true);
				normalSchedule = false;
			} else
			//if the update has been applied
			if (!newVersion.getVersionNumber().equals(storedVersion.getVersionNumber())) {
				notifyAvailable(newVersion, storedVersion);
				isStoredVersionUpdateNeeded = true;
				
				if (!normalSchedule) {
					//change back to normal schedule
					AVNJobRunner.scheduleJob(60, true);
					normalSchedule = true;
				}
			} else 
			//if the ETA for an upcoming version was announced
			if (!newVersion.getUpcomingVersion().getETA().equals(storedVersion.getUpcomingVersion().getETA())) {
				notifyETAUpdated(newVersion, storedVersion);
				isStoredVersionUpdateNeeded = true;
			}
		}
		
		
		if (isStoredVersionUpdateNeeded) {
			updateStoredVersion(json);
		}
	}	
	
	
	private static ARKVersion buildVersionFromJSON(JSONObject json) {
		ARKVersion newVersion = null;
		JSONObject upcoming = json.getJSONObject("upcoming");
		
		newVersion = new ARKVersion(
				String.valueOf(json.get("current")),
				"now",
				new ARKVersion(
						String.valueOf(upcoming.get("version")),
						String.valueOf(upcoming.get("status")),
						null
						)
				);
		
		return newVersion;
	}
	
	
	private static ARKVersion getStoredVersion() {
		ARKVersion storedVersion = null;

		//go to DB for storedJSON
		for (StoredJSON storedJSON : ofy().load().type(StoredJSON.class).iterable()) {
			storedVersion = buildVersionFromJSON(storedJSON.getJSON());
		}
		
		return storedVersion;
	}

	
	private static void updateStoredVersion(JSONObject json) {
		StoredJSON storedJSON = new StoredJSON();
		storedJSON.setJSON(json);
		
		//delete old record from DB
		ofy().delete().keys(ofy().load().type(StoredJSON.class).keys());

		//add new one
		ofy().save().entity(storedJSON);
	}

	
	private static void notifyAvailable(ARKVersion newVersion, ARKVersion storedVersion) {
		sendEmails("available");		
	}

	private static void notifyUpcoming(ARKVersion newVersion, ARKVersion storedVersion) {
		sendEmails("upcoming");
	}

	private static void notifyETAUpdated(ARKVersion newVersion, ARKVersion storedVersion) {
		sendEmails("eta");
	}
	

	private static void sendEmails(String emailBody) {
		System.out.println(emailBody);
		//update version in DB also
		//through Mandrill?
	}
	

	private static JSONObject getJSON(String stringUrl) {	
		JSONObject json = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		String output = "";
	    StringBuilder sb = new StringBuilder();
	    
		try {
			System.out.println("attempting to get json from URL: " + stringUrl);

			URL url = new URL(stringUrl);
			conn = (HttpURLConnection) url.openConnection();

			InputStream is = conn.getInputStream();
			br = new BufferedReader(
                    new InputStreamReader(is));
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
		    json = new JSONObject(sb.toString());
			System.out.println("got json: " + json.toString());

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
