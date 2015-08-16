package com.hackeraj.arkversionnotifier.datamodel;


public class ARKVersion {	
	
	public ARKVersion() {}
	public ARKVersion(String versionNumber, String ETA, ARKVersion upcomingVersion) {
		this.versionNumber = versionNumber;
		this.ETA = ETA;
		this.upcomingVersion = upcomingVersion;
	}
	
	private String versionNumber = null;
	private String ETA = null;
	private ARKVersion upcomingVersion = null;
	
	
	
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getETA() {
		return ETA;
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
