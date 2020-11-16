package com.warn.main.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	private String key;
	private String encrypted;
	private String decrypted;
	
	public Crypto(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public String getDecrypted() {
		return decrypted;
	}

	public void setDecrypted(String decrypted) {
		this.decrypted = decrypted;
	}
	
	public void encrypt(String text) {
		try {
			Key aes = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aes);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			this.encrypted = encrypted.toString();
			//////////////////////////////////////
			cipher.init(Cipher.DECRYPT_MODE, aes);
			byte[] decrypted = cipher.doFinal(encrypted);
			this.decrypted = decrypted.toString();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void decrypt(String text) {
		try {
			Key aes = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aes);
			byte[] decrypted = cipher.doFinal(text.getBytes());
			this.decrypted = decrypted.toString();
			//////////////////////////////////////
			cipher.init(Cipher.ENCRYPT_MODE, aes);
			byte[] encrypted = cipher.doFinal(decrypted);
			this.encrypted = encrypted.toString();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
