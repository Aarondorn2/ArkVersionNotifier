package com.hackeraj.arkversionnotifier.job;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackeraj.arkversionnotifier.datamodel.ARKVersion;
import com.hackeraj.arkversionnotifier.datamodel.StoredJSON;
import com.hackeraj.arkversionnotifier.datamodel.Subscription;
import com.hackeraj.arkversionnotifier.mocking.MockArkBarClient;
import com.hackeraj.arkversionnotifier.mocking.MockDataManager;
import com.hackeraj.arkversionnotifier.mocking.MockingUtils;
import com.hackeraj.arkversionnotifier.utils.ArkBarClient;
import com.hackeraj.arkversionnotifier.utils.DataManager;
import com.hackeraj.arkversionnotifier.utils.Email;
import com.hackeraj.arkversionnotifier.utils.EmailBodies;
import com.hackeraj.arkversionnotifier.utils.Encryption;

//TODO: do the testings.

public class AVNJob {
	
	private static final DataManager dataManager = 
			!MockingUtils.isMocking()
			? new DataManager()
			: new MockDataManager();
	private static final ArkBarClient arkBarClient = 
			!MockingUtils.isMocking()
			? new ArkBarClient()
			: new MockArkBarClient();
	
	private static boolean checkEvery20 = false;
	private static int invokeCount = 0;
	private static StoredJSON cachedJSON = new StoredJSON();

	
	public static void execute() {
		boolean isStoredVersionUpdateNeeded = false;
		
		JSONObject json = arkBarClient.getJSON();
		ARKVersion newVersion = buildVersionFromJSON(json);
		ARKVersion storedVersion = getStoredVersion();
		
		//if there isn't a stored version, simply update the stored version.
		if (storedVersion == null) {
			isStoredVersionUpdateNeeded = true;
		} else {
			
			System.out.println("execute -> invokeCount = " + invokeCount);
			
			if (checkEvery20 || invokeCount++ % 3 == 0) {
				//if first time an upcoming version is announced
				if (!checkEvery20 && !newVersion.getUpcomingVersion().getVersionNumber().equals("null")) {
					notifyUpcoming(newVersion);
					isStoredVersionUpdateNeeded = true;
					
					//check every 20 minutes until update is available!
					checkEvery20 = true;
				} else
				//if the update has been applied
				if (!newVersion.getVersionNumber().equals(storedVersion.getVersionNumber())) {
					notifyAvailable(newVersion, storedVersion);
					isStoredVersionUpdateNeeded = true;
					
					if (checkEvery20) {
						//change back to checking very 60 minutes
						checkEvery20 = false;
					}
				} else 
				//if the ETA for an upcoming version was announced
				if (!newVersion.getUpcomingVersion().getETA().equals(storedVersion.getUpcomingVersion().getETA())) {
					notifyETAUpdated(newVersion, storedVersion);
					isStoredVersionUpdateNeeded = true;
				}
				
			}
		}
		
		
		if (isStoredVersionUpdateNeeded) {
			updateStoredVersion(json);
		}
	}	
	
	
	private static ARKVersion buildVersionFromJSON(JSONObject json) {
		ARKVersion newVersion = new ARKVersion();
		
		try {
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
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return newVersion;
	}
	
	
	private static ARKVersion getStoredVersion() {
		ARKVersion storedVersion = null;

		//get json from cache if available - save datastore call
		if (!cachedJSON.getJSONString().isEmpty()) {
			storedVersion = buildVersionFromJSON(cachedJSON.getJSON());
		} else {
			//if cache not available, go to DB for storedJSON
			for (StoredJSON storedJSON : dataManager.getStoredJSON()) {
				storedVersion = buildVersionFromJSON(storedJSON.getJSON());
			}
		}
		
		return storedVersion;
	}

	
	private static void updateStoredVersion(JSONObject json) {
		StoredJSON storedJSON = new StoredJSON();
		storedJSON.setJSON(json);
		
		//update cache
		cachedJSON.setJSON(json);
		
		//delete old record from DB
		dataManager.deleteStoredJSON();
		
		//add new one
		dataManager.saveStoredJSON(storedJSON);
	}

	
	private static void notifyAvailable(ARKVersion newVersion, ARKVersion storedVersion) {
		String emailSubject = "ArkVersionNotification: New Version Available";
		String unsubscribeLink = null;
		String emailBody = EmailBodies.notifyAvailableEmailBody;
		emailBody = emailBody.replace("::currentVersion", newVersion.getVersionNumber());
		emailBody = emailBody.replace("::previousVersion", storedVersion.getVersionNumber());
		
		for (String emailAddress : getSubscribedEmailAddresses("available")) {
			unsubscribeLink = "<a href=\"arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=" + emailAddress + "\">unsubscribe</>.";
			Email.sendMail(emailSubject, 
					emailBody + unsubscribeLink, 
					emailAddress);
			
		}
	}

	private static void notifyUpcoming(ARKVersion newVersion) {
		String emailSubject = "ArkVersionNotification: Upcoming Version Announced";
		String unsubscribeLink = null;
		String emailBody = EmailBodies.notifyUpcomingEmailBody;
		emailBody = emailBody.replace("::currentVersion", newVersion.getVersionNumber());
		emailBody = emailBody.replace("::upcomingVersion", newVersion.getUpcomingVersion().getVersionNumber());
		emailBody = emailBody.replace("::ETA", newVersion.getUpcomingVersion().getETA());
		
		
		for (String emailAddress : getSubscribedEmailAddresses("upcoming")) {
			unsubscribeLink = "<a href=\"arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=" + emailAddress + "\">unsubscribe</>.";
			Email.sendMail(emailSubject, 
					emailBody + unsubscribeLink, 
					emailAddress);
			
		}
	}

	private static void notifyETAUpdated(ARKVersion newVersion, ARKVersion storedVersion) {
		String emailSubject = "ArkVersionNotification: New ETA for Upcoming Version";
		String unsubscribeLink = null;
		String emailBody = EmailBodies.notifyETAUpdatedEmailBody;
		emailBody = emailBody.replace("::upcomingVersion", newVersion.getUpcomingVersion().getVersionNumber());
		emailBody = emailBody.replace("::previousETA", storedVersion.getUpcomingVersion().getETA());
		emailBody = emailBody.replace("::ETA", newVersion.getUpcomingVersion().getETA());
		
		for (String emailAddress : getSubscribedEmailAddresses("eta")) {
			unsubscribeLink = "<a href=\"arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=" + emailAddress + "\">unsubscribe</>.";
			Email.sendMail(emailSubject, 
					emailBody + unsubscribeLink, 
					emailAddress);
			
		}
		
		
	}
	
	
	private static List<String> getSubscribedEmailAddresses(String type) {
		List<String> emails = new ArrayList<String>();
			
		for (Subscription subscription : dataManager.getSubscriptions()) {
			if (("available".equals(type) && subscription.getNotifyAvailable())
				|| ("upcoming".equals(type) && subscription.getNotifyUpcoming())
				|| ("eta".equals(type) && subscription.getNotifyETAChange())) {
				emails.add(Encryption.decrypt(subscription.getEncryptedEmail()));
			}
		}
		
		return emails;
	}
	

	
	
}