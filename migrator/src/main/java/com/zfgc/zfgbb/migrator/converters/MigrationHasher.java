package com.zfgc.zfgbb.migrator.converters;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class MigrationHasher {

	private MigrationHasher() {
	}

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Base64.getUrlEncoder().encodeToString(md.digest(input.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 unavailable", e);
		}
	}
}
