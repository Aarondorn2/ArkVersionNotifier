package com.hackeraj.arkversionnotifier.datamodel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Subscription {
	@Id public Long id;
	
	public Subscription() {}
	public Subscription(String email, Boolean notifyUpcoming, Boolean notifyETAChange, Boolean notifyAvailable) {
		this.email = email;
		this.notifyUpcoming = notifyUpcoming;
		this.notifyETAChange = notifyETAChange;
		this.notifyAvailable = notifyAvailable;
	}
	
	@Index private String email;
	private Boolean notifyUpcoming;
	private Boolean notifyETAChange;
	private Boolean notifyAvailable;
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getNotifyUpcoming() {
		return notifyUpcoming;
	}
	public void setNotifyUpcoming(Boolean notifyUpcoming) {
		this.notifyUpcoming = notifyUpcoming;
	}
	public Boolean getNotifyETAChange() {
		return notifyETAChange;
	}
	public void setNotifyETAChange(Boolean notifyETAChange) {
		this.notifyETAChange = notifyETAChange;
	}
	public Boolean getNotifyAvailable() {
		return notifyAvailable;
	}
	public void setNotifyAvailable(Boolean notifyAvailable) {
		this.notifyAvailable = notifyAvailable;
	} 
}
