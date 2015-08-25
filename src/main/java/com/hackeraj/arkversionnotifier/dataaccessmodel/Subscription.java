package com.hackeraj.arkversionnotifier.dataaccessmodel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.hackeraj.arkversionnotifier.utils.Encryption;
import com.hackeraj.arkversionnotifier.utils.Hash;

@Entity
public class Subscription {
	@Id public Long id; //set by datastore
	
	public Subscription() {}
	public Subscription(String email, Boolean notifyUpcoming, Boolean notifyETAChange, Boolean notifyAvailable) {
		this.emailHash = Hash.getHash(email);
		this.encryptedEmail = Encryption.encrypt(email);
		this.notifyUpcoming = notifyUpcoming;
		this.notifyETAChange = notifyETAChange;
		this.notifyAvailable = notifyAvailable;
	}
	
	@Index private String emailHash;
	private String encryptedEmail;
	private Boolean notifyUpcoming;
	private Boolean notifyETAChange;
	private Boolean notifyAvailable;
	

	
	public String getEmailHash() {
		return emailHash;
	}
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	public String getEncryptedEmail() {
		return encryptedEmail;
	}
	public void setEncryptedEmail(String encryptedEmail) {
		this.encryptedEmail = encryptedEmail;
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
