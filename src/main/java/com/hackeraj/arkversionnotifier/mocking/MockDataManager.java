package com.hackeraj.arkversionnotifier.mocking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Index;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.hackeraj.arkversionnotifier.datamodel.StoredJSON;
import com.hackeraj.arkversionnotifier.datamodel.Subscription;
import com.hackeraj.arkversionnotifier.utils.DataManager;
import com.hackeraj.arkversionnotifier.utils.Hash;

public class MockDataManager extends DataManager{
	private static Map<String, Subscription> subscriptions = new HashMap<String, Subscription>();
	private static StoredJSON JSON = null;

	@Override
	public void saveSubscription(Subscription subscription) {
		subscriptions.put(subscription.getEmailHash(), subscription);
	}

	@Override
	public QueryResultIterable<Subscription> getSubscriptions() {
		return new QueryResultIterable<Subscription>() {
			
			@Override
			public QueryResultIterator<Subscription> iterator() {
				return new QueryResultIterator<Subscription>() {

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public Subscription next() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Cursor getCursor() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public List<Index> getIndexList() {
						// TODO Auto-generated method stub
						return null;
					}
				};
			}
		};
	}

	@Override
	public void deleteSubscription(String email) {
		String hash = Hash.getHash(email);
		if (subscriptions.containsKey(hash)) {
			subscriptions.remove(hash);
		}
	}

	@Override
	public void saveStoredJSON(StoredJSON storedJSON) {
		JSON = storedJSON;
	}
	
	@Override
	public QueryResultIterable<StoredJSON> getStoredJSON() {
		return new QueryResultIterable<StoredJSON>() {
			
			@Override
			public QueryResultIterator<StoredJSON> iterator() {
				return new QueryResultIterator<StoredJSON>() {

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public StoredJSON next() {
						return JSON;
					}

					@Override
					public Cursor getCursor() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public List<Index> getIndexList() {
						// TODO Auto-generated method stub
						return null;
					}
				};
			}
		};
	}

	@Override
	public void deleteStoredJSON() {
		JSON = null;
	}

}
