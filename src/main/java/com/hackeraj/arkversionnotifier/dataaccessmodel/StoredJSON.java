package com.hackeraj.arkversionnotifier.dataaccessmodel;

import org.json.JSONObject;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class StoredJSON {
	@Id public Long id; //set by datastore
	
	public StoredJSON() {}
	public StoredJSON(String jsonString) {
		this.jsonString = jsonString;
	}

	private String jsonString = "";

	public String getJSONString() {
		return jsonString;
	}
	public void setJSONString(String jsonString) {
		this.jsonString = jsonString;
	}
	public JSONObject getJSON() {
		return new JSONObject(jsonString);
	}
	public void setJSON(JSONObject json) {
		this.jsonString = json.toString();
	}

	
}
