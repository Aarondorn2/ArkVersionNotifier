package com.hackeraj.arkversionnotifier.beans;

import com.hackeraj.arkversionnotifier.utils.Globals;


public class ARKVersion {	
	
	public ARKVersion() {}
	public ARKVersion(String versionNumber, String ETA, ARKVersion upcomingVersion) {
		this.versionNumber = versionNumber;
		this.ETA = ETA;
		this.upcomingVersion = upcomingVersion;
	}
	
	private String versionNumber = "null"; //using string literal because that's what JSONObject resolves to for nulls
	private String ETA = "null"; //using string literal because that's what JSONObject resolves to for nulls
	private ARKVersion upcomingVersion = null;
	
	
	
	public String getVersionNumber() {
		return "null".equals(versionNumber)
				? "not available"
				: "v" + versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getETA() {
		return "null".equals(ETA)
				? Globals.NOT_AVAILABLE
				: ETA;
	}
	public void setETA(String eTA) {
		ETA = eTA;
	}
	public ARKVersion getUpcomingVersion() {
		return upcomingVersion;
	}
	public void setUpcomingVersion(ARKVersion upcomingVersion) {
		this.upcomingVersion = upcomingVersion;
	}
	
}
