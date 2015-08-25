package com.hackeraj.arkversionnotifier.utils;

import com.google.appengine.api.utils.SystemProperty;

public class Utils {

	public static boolean isDev() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
	}
	
	public static String arrayToString(String[] array) {
		StringBuilder sb = new StringBuilder();
		
		for (String s : array) {
			sb.append(s + ",");
		}
		sb.deleteCharAt(sb.length() -1);
		
		return sb.toString();
	}
	
	public static String[] stringToArray(String string) {
		return string.split(",");
	}
}
