package com.zfgc.zfgbb.model.users;

public record RegistrationRequest(
		String userName,
		String displayName,
		String email,
		String password) {
}
