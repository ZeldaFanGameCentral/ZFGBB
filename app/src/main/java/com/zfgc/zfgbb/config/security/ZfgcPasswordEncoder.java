package com.zfgc.zfgbb.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.model.users.EncodedPassword;
import com.zfgc.zfgbb.model.users.PasswordAlgo;
import com.zfgc.zfgbb.services.core.PasswordService;

@Component
public class ZfgcPasswordEncoder implements PasswordEncoder {

	private final PasswordService passwordService;

	public ZfgcPasswordEncoder(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return EncodedPassword.of(passwordService.hash(rawPassword.toString())).toEncoded();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (rawPassword == null || encodedPassword == null)
			return false;
		EncodedPassword parsed;
		try {
			parsed = EncodedPassword.parse(encodedPassword);
		} catch (IllegalArgumentException invalidEncoding) {
			return false;
		}
		return passwordService.verify(rawPassword.toString(), parsed.hash(), parsed.algo(), parsed.salt());
	}

	@Override
	public boolean upgradeEncoding(String encodedPassword) {
		if (encodedPassword == null)
			return false;
		try {
			return EncodedPassword.parse(encodedPassword).algo() == PasswordAlgo.SMF2_SHA1;
		} catch (IllegalArgumentException invalidEncoding) {
			return false;
		}
	}
}
