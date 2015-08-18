package com.hackeraj.arkversionnotifier.mocking;

public class MockingUtils {
	private static boolean isMocking = false;

	public static boolean isMocking() {
		return isMocking;
	}

	public static void setMocking(boolean isMocking) {
		MockingUtils.isMocking = isMocking;
	}
	
}
