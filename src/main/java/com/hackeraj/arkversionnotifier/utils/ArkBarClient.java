package com.hackeraj.arkversionnotifier.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class ArkBarClient {
	private static final Logger logger = Logger.getLogger(ArkBarClient.class.getName());
	private static final String arkBarURL = "https://api.ark.bar/v1/version";

	
	public JSONObject getJSON() {	
		JSONObject json = new JSONObject();
		HttpURLConnection conn = null;
		BufferedReader br = null;
		String output = "";
	    StringBuilder sb = new StringBuilder();
	    
		try {
			System.out.println("attempting to get json from URL: " + arkBarURL);

			URL url = new URL(arkBarURL);
			conn = (HttpURLConnection) url.openConnection();

			InputStream is = conn.getInputStream();
			br = new BufferedReader(
                    new InputStreamReader(is));
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
		    json = new JSONObject(sb.toString());
			System.out.println("got json: " + json.toString());

		} catch (Exception e) {
			logger.log(Level.SEVERE, "getJSON -> unable to get JSON from ArkBar client", e);
			try {
				if (br != null) {
					br.close();
				}
				if (conn != null) { 
					conn.disconnect();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}	
		}
		
		return json;
	}
}
