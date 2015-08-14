package com.hackeraj.arkversionnotifier.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class Encryption {
	//put String password in another file to exclude from GitHub source
	private static final String pass = Pass.getPassword();
	
	
	
	public static String encrypt(String stringToEncrypt) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(pass);
		
		return textEncryptor.encrypt(stringToEncrypt);
	}
	
	public static String decrypt(String encryptedString) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(pass);
		
		return textEncryptor.decrypt(encryptedString);
	}
	

	
}
