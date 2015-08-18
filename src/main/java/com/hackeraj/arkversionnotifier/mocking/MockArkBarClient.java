package com.hackeraj.arkversionnotifier.mocking;

import org.json.JSONObject;

import com.hackeraj.arkversionnotifier.utils.ArkBarClient;

public class MockArkBarClient extends ArkBarClient {
	
	private static int incr = 0;

	@Override
	public JSONObject getJSON() {
		JSONObject json = null;

		if (incr == 0) {
			json = new JSONObject("{\"current\":200.3,\"upcoming\":{\"version\":null,\"status\":null}}");
		} else
		if (incr == 1) {
			json = new JSONObject("{\"current\":200.3,\"upcoming\":{\"version\":201,\"status\":null}}");
		} else
		if (incr == 2) {
			json = new JSONObject("{\"current\":200.3,\"upcoming\":{\"version\":201,\"status\":\"ETA: 4pm EDT Deployment!\"}}");
		} else
		if (incr == 3) {
			json = new JSONObject("{\"current\":201,\"upcoming\":{\"version\":null,\"status\":null}}");
		} else
		if (incr == 4) {
			json = new JSONObject("{\"current\":201,\"upcoming\":{\"version\":null,\"status\":null}}");
		} else
		if (incr == 5) {
			json = new JSONObject("{\"current\":201,\"upcoming\":{\"version\":202,\"status\":\"SOON!\"}}");
		} else
		if (incr >= 6) {
			json = new JSONObject("{\"current\":201,\"upcoming\":{\"version\":202,\"status\":\"SOONER!\"}}");
		}
		
		System.out.println("MockArkBarClient -> incr=" + incr++);
		
		return json;
	}
}
