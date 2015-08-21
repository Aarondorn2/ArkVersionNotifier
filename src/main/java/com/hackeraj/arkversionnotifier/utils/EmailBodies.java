package com.hackeraj.arkversionnotifier.utils;

public class EmailBodies {
	private static final String disclaimer = "This email notificaiton has been auto-generated. Please do not reply to this email.  If you would no longer like to receive these notifications, please";
	private static final String patchNotesURL = "steamcommunity.com/app/346110/.../0/594820656447032287/";
	
	public static final String notifyAvailableEmailBody = 
			"A new version has been released for ARK: Survival Evolved! <br /> <br />" +
			"<b>The current version is now: ::currentVersion </b><br />" +
			"The previous version was: ::previousVersion <br /><br />" +
			"<a href=\"" + patchNotesURL + "\">Click here to view <b>Patch Notes</b> on Steam</a> <br /><br /><br />" +
			disclaimer;
	public static final String notifyUpcomingEmailBody = 
			"There has been a new version announced for ARK: Survival Evolved! <br /> <br />" +
			"The current version is: ::currentVersion <br />" +
			"<b>The upcoming version is: ::upcomingVersion </b><br />" +
			"The ETA for the upcoming version is: ::ETA <br /><br /><br />" + 
			"<a href=\"" + patchNotesURL + "\">Click here to view <b>Patch Notes</b> on Steam</a> <br /><br /><br />" +
			disclaimer;
	public static final String notifyETAUpdatedEmailBody = 
			"There has been an update to the ETA of the next version of ARK: Survival Evolved! <br /> <br />" +
			"The upcoming version is: ::upcomingVersion <br />" +
			"The previous ETA was: ::previousETA <br />" +
			"<b>The new ETA is: ::ETA </b><br /><br /><br />" + 
			"<a href=\"" + patchNotesURL + "\">Click here to view <b>Patch Notes</b> on Steam</a> <br /><br /><br />" +
			disclaimer;
	
}
