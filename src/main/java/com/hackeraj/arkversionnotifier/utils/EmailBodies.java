package com.hackeraj.arkversionnotifier.utils;

public class EmailBodies {
	private static final String patchNotesURL = "http://steamcommunity.com/app/346110/discussions/0/594820656447032287/";
	
	private static final String patchNotesHTML = 
			"<div> "
					+ "<a href=\"" + patchNotesURL + "\">"
							+ "Click here to view <b>Patch Notes</b> on Steam"
					+ "</a> "
			+ "</div>";
	private static final String disclaimer = 
		"<div style=\"font-size:12px\">"
			+ "This email notification has been auto-generated. "
			+ "Please do not reply to this email.  "
			+ "If you would no longer like to receive these notifications, please "
			+ "<a href=\"http://arkversionnotifier.appspot.com/subscribe?type=unsubscribe&email=::email\">unsubscribe<a/>."
		+ "</div>";

	
	public static final String EMAIL_BODY_NOTIFY_AVAILABLE = 
			"<div style=\"font-size:18px\">" +
				"<div>There has been a new version announced for ARK: Survival Evolved! </div>" +
				"<br />" +
				"<table style=\"font-size:18px\">" +
					"<tr>" +
						"<td width=\"250\"><b>The current version is now:</b></td>" +
						"<td>::currentVersion</td>" +
					"</tr>" +
					"<tr>" +
						"<td>The previous version was:</td>" +
						"<td>::previousVersion</td>" +
					"</tr>" +
				"</table>" +
				"<br />" +
				patchNotesHTML +
				"<br /><br /><br />" +
				disclaimer +
			"</div>";
	
	public static final String EMAIL_BODY_NOTIFY_UPCOMING = 
			"<div style=\"font-size:18px\">" +
				"<div>There has been a new version announced for ARK: Survival Evolved! </div>" +
				"<br />" +
				"<table style=\"font-size:18px\">" +
					"<tr>" +
						"<td width=\"350\">The current version is:</td>" +
						"<td>::currentVersion</td>" +
					"</tr>" +
					"<tr>" +
						"<td><b>The upcoming version is:</b></td>" +
						"<td>::upcomingVersion</td>" +
					"</tr>" +
					"<tr>" +
						"<td>The ETA for the upcoming version is:</td>" +
						"<td>::ETA</td>" +
					"</tr>" +
				"</table>" +
				"<br />" +
				patchNotesHTML +
				"<br /><br /><br />" +
				disclaimer +
			"</div>";
	
	public static final String EMAIL_BODY_NOTIFY_ETA_UPDATED = 
			"<div style=\"font-size:18px\">" +
				"<div>There has been an update to the ETA of the next version of ARK: Survival Evolved! </div>" +
				"<br />" +
				"<table style=\"font-size:18px\">" +
					"<tr>" +
						"<td width=\"250\">The upcoming version is:</td>" +
						"<td>::upcomingVersion</td>" +
					"</tr>" +
					"<tr>" +
						"<td>The previous ETA was:</td>" +
						"<td>::previousETA</td>" +
					"</tr>" +
					"<tr>" +
						"<td><b>The new ETA is:</b></td>" +
						"<td>::ETA</td>" +
					"</tr>" +
				"</table>" +
				"<br />" +
				patchNotesHTML +
				"<br /><br /><br />" +
				disclaimer +
			"</div>";
	

	public static final String EMAIL_BODY_CONFIRMATION = 
			"<div style=\"font-size:18px\">" +
				"<div>You have been successfully subscribed to recieve notifications for ARK: Survival Evolved! </div>" +
				"<br /><br /><br />" +
				disclaimer +
			"</div>";
	
}
