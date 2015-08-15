package com.hackeraj.arkversionnotifier.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	
	public static String getHash(String stringToHash) {
        StringBuffer sb = new StringBuffer();
    	
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
	        
	        md.update(stringToHash.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	     
        return sb.toString();
	}
}
