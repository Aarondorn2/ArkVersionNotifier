package com.hackeraj.arkversionnotifier.datamodel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CurrentVersion {
		@Id public Long id;
		
		@Index private String versionNumber;
		
		
		public String getVersionNumber() {
			return versionNumber;
		}
		public void setVersionNumber(String versionNumber) {
			this.versionNumber = versionNumber;
		}
}
