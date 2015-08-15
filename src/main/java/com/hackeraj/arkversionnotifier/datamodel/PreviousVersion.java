package com.hackeraj.arkversionnotifier.datamodel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class PreviousVersion {
		@Id public Long id;
		
		private String versionNumber;
		
		
		public String getVersionNumber() {
			return versionNumber;
		}
		public void setVersionNumber(String versionNumber) {
			this.versionNumber = versionNumber;
		}
}
