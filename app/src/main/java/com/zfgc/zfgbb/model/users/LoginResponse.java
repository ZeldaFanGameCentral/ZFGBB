package com.zfgc.zfgbb.model.users;

import com.zfgc.zfgbb.model.User;

public record LoginResponse(String accessToken, String refreshToken, boolean stayLoggedIn, long accessTokenTtlSeconds,
		User user) {

	public LoginResponse withoutTokens() {
		return new LoginResponse(null, null, stayLoggedIn, accessTokenTtlSeconds, user);
	}
}
