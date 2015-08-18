package com.hackeraj.arkversionnotifier.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.hackeraj.arkversionnotifier.datamodel.StoredJSON;
import com.hackeraj.arkversionnotifier.datamodel.Subscription;

public class DataManager {

	
	public void saveSubscription(Subscription subscription) {
		ofy().save().entity(subscription);
	}
	
	public QueryResultIterable<Subscription> getSubscriptions() {
		return ofy().load().type(Subscription.class).iterable();
	}

	public void deleteSubscription(String email) {
		if (email != null) {
			ofy().delete().keys(ofy().load().type(Subscription.class).filter("emailHash", Hash.getHash(email)).keys());
		}
	}
	
	public void saveStoredJSON(StoredJSON storedJSON) {
		ofy().save().entity(storedJSON);
	}

	public QueryResultIterable<StoredJSON> getStoredJSON() {
		return ofy().load().type(StoredJSON.class).iterable();
	}
	
	public void deleteStoredJSON() {
		ofy().delete().keys(ofy().load().type(StoredJSON.class).keys());
	}
	
	
}
