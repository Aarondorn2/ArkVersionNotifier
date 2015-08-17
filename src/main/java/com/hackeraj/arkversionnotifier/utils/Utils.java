package com.hackeraj.arkversionnotifier.utils;

import com.google.appengine.api.utils.SystemProperty;

public class Utils {

	public static boolean isDevEnv() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
	}
}
