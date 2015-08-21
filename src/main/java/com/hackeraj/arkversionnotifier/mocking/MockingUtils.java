package com.hackeraj.arkversionnotifier.mocking;

public class MockingUtils {
	private static boolean isMocking = false;
	private static boolean sendEmailsWhileMocking = false;

	public static boolean isMocking() {
		return isMocking;
	}

	public static void setMocking(boolean isMocking) {
		MockingUtils.isMocking = isMocking;
	}

	public static boolean isSendEmailsWhileMocking() {
		return sendEmailsWhileMocking;
	}

	public static void setSendEmailsWhileMocking(boolean sendEmailsWhileMocking) {
		MockingUtils.sendEmailsWhileMocking = sendEmailsWhileMocking;
	}
	
}
