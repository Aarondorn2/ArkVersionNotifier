package com.hackeraj.arkversionnotifier.mocking;

import java.util.ArrayList;
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

					private int incr = 0;
					private List<Subscription> subscriptionList = buildSubscriptionList();
					
					private List<Subscription> buildSubscriptionList() {
						List<Subscription> list = new ArrayList<Subscription>();
						
						for (Subscription sub : subscriptions.values()) {
							list.add(sub);
						}
						
						return list;
					}
					
					@Override
					public boolean hasNext() {
						return incr++ < subscriptions.size();
					}

					@Override
					public Subscription next() {
						return subscriptionList.get(incr -1);
					}

					@Override
					public Cursor getCursor() {
						// not used
						return null;
					}

					@Override
					public List<Index> getIndexList() {
						// not used
						return null;
					}

					@Override
					public void remove() {
						// not used
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
					
					private int incr = 0;

					@Override
					public boolean hasNext() {
						return JSON != null && incr++ < 1;
					}

					@Override
					public StoredJSON next() {
						return JSON;
					}

					@Override
					public Cursor getCursor() {
						// not used
						return null;
					}

					@Override
					public List<Index> getIndexList() {
						// not used
						return null;
					}

					@Override
					public void remove() {
						// not used
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
