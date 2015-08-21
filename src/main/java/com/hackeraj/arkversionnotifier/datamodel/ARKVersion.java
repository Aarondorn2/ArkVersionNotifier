package com.hackeraj.arkversionnotifier.datamodel;


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
				? "not available"
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
