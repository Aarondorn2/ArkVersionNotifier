package com.hackeraj.arkversionnotifier.job;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackeraj.arkversionnotifier.beans.ARKVersion;
import com.hackeraj.arkversionnotifier.datamodel.StoredJSON;
import com.hackeraj.arkversionnotifier.datamodel.Subscription;
import com.hackeraj.arkversionnotifier.mocking.MockArkBarClient;
import com.hackeraj.arkversionnotifier.mocking.MockDataManager;
import com.hackeraj.arkversionnotifier.mocking.MockEmailManager;
import com.hackeraj.arkversionnotifier.mocking.MockingUtils;
import com.hackeraj.arkversionnotifier.utils.ArkBarClient;
import com.hackeraj.arkversionnotifier.utils.DataManager;
import com.hackeraj.arkversionnotifier.utils.EmailBodies;
import com.hackeraj.arkversionnotifier.utils.EmailManager;
import com.hackeraj.arkversionnotifier.utils.Encryption;
import com.hackeraj.arkversionnotifier.utils.Globals;
import com.hackeraj.arkversionnotifier.utils.Utils;

public class AVNJob {
	
	private static final DataManager dataManager = 
			!MockingUtils.isMocking() //if not mocking
			? new DataManager()
			: new MockDataManager();
	private static final EmailManager emailManager = 
			!MockingUtils.isMocking() //if not mocking
			? new EmailManager()
			: new MockEmailManager();
	private static final ArkBarClient arkBarClient = 
			!MockingUtils.isMocking() && !Utils.isDev() //if not mocking and not devserver
			? new ArkBarClient()
			: new MockArkBarClient();
	private static final Logger logger = Logger.getLogger(AVNJob.class.getName());
	
	
	public static void execute() {
		boolean isStoredVersionUpdateNeeded = true;
		
		JSONObject json = arkBarClient.getJSON();
		ARKVersion newVersion = buildVersionFromJSON(json);
		ARKVersion storedVersion = getStoredVersion();
		
		//if there isn't a stored version, simply update the stored version.
		if (storedVersion != null) {
						
			//if first time an upcoming version is announced
			if (storedVersion.getUpcomingVersion().getVersionNumber().equalsIgnoreCase(Globals.NOT_AVAILABLE)
				&&	!newVersion.getUpcomingVersion().getVersionNumber().equalsIgnoreCase(Globals.NOT_AVAILABLE)) {
				
				notifyUpcoming(newVersion);
				
			} else
			//if the update has been applied
			if (!newVersion.getVersionNumber().equals(storedVersion.getVersionNumber())) {
				
				notifyAvailable(newVersion, storedVersion);
				
			} else 
			//if the ETA for an upcoming version was announced
			if (!newVersion.getUpcomingVersion().getETA().equals(storedVersion.getUpcomingVersion().getETA())) {
				
				notifyETAUpdated(newVersion, storedVersion);
				
			} else {
				//no changes, no need to update DB
				isStoredVersionUpdateNeeded = false;
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
			logger.log(Level.SEVERE, "buildVersionFromJSON -> unable to build JSON", e);
		}
		
		return newVersion;
	}
	
	
	private static ARKVersion getStoredVersion() {
		ARKVersion storedVersion = null;

		//go to DB for storedJSON
		for (StoredJSON storedJSON : dataManager.getStoredJSON()) {
			storedVersion = buildVersionFromJSON(storedJSON.getJSON());
		}
		
		return storedVersion;
	}

	
	private static void updateStoredVersion(JSONObject json) {
		StoredJSON storedJSON = new StoredJSON();
		storedJSON.setJSON(json);
		
		//delete old record from DB
		dataManager.deleteStoredJSON();
		
		//add new one
		dataManager.saveStoredJSON(storedJSON);
	}

	
	private static void notifyAvailable(ARKVersion newVersion, ARKVersion storedVersion) {
		String emailSubject = "ArkVersionNotification: New Version Available";
		String emailBody = EmailBodies.EMAIL_BODY_NOTIFY_AVAILABLE;
				emailBody = emailBody.replace("::currentVersion", newVersion.getVersionNumber());
				emailBody = emailBody.replace("::previousVersion", storedVersion.getVersionNumber());
		
		emailManager.sendMail(emailSubject, 
				emailBody, 
				getSubscribedEmailAddresses("available"));
	}

	private static void notifyUpcoming(ARKVersion newVersion) {
		String emailSubject = "ArkVersionNotification: Upcoming Version Announced";
		String emailBody = EmailBodies.EMAIL_BODY_NOTIFY_UPCOMING;
				emailBody = emailBody.replace("::currentVersion", newVersion.getVersionNumber());
				emailBody = emailBody.replace("::upcomingVersion", newVersion.getUpcomingVersion().getVersionNumber());
				emailBody = emailBody.replace("::ETA", newVersion.getUpcomingVersion().getETA());
		
		emailManager.sendMail(emailSubject, 
				emailBody, 
				getSubscribedEmailAddresses("upcoming"));
	}

	private static void notifyETAUpdated(ARKVersion newVersion, ARKVersion storedVersion) {
		String emailSubject = "ArkVersionNotification: New ETA for Upcoming Version";
		String emailBody = EmailBodies.EMAIL_BODY_NOTIFY_ETA_UPDATED;
				emailBody = emailBody.replace("::upcomingVersion", newVersion.getUpcomingVersion().getVersionNumber());
				emailBody = emailBody.replace("::previousETA", storedVersion.getUpcomingVersion().getETA());
				emailBody = emailBody.replace("::ETA", newVersion.getUpcomingVersion().getETA());
		
		emailManager.sendMail(emailSubject, 
				emailBody, 
				getSubscribedEmailAddresses("eta"));
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