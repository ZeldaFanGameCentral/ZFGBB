package com.zfgc.zfgbb.services.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.model.users.HashedPassword;
import com.zfgc.zfgbb.model.users.PasswordAlgo;

@Service
public class PasswordService {

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	public HashedPassword hash(String raw) {
		return new HashedPassword(bcrypt.encode(raw), PasswordAlgo.BCRYPT, null);
	}

	public boolean verify(String raw, String storedHash, PasswordAlgo algo, String salt) {
		if (raw == null || storedHash == null || algo == null) {
			return false;
		}
		return switch (algo) {
			case BCRYPT -> bcrypt.matches(raw, storedHash);
			case SMF2_SHA1 -> verifySmf2Sha1(raw, storedHash, salt);
		};
	}

	private boolean verifySmf2Sha1(String raw, String storedHash, String salt) {
		if (salt == null) {
			return false;
		}
		String computed = sha1Hex(raw + salt);
		return MessageDigest.isEqual(
				computed.getBytes(StandardCharsets.UTF_8),
				storedHash.getBytes(StandardCharsets.UTF_8));
	}

	private static String sha1Hex(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(digest.length * 2);
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-1 not available", e);
		}
	}
}
