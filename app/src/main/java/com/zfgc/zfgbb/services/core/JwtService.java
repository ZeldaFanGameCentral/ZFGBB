package com.zfgc.zfgbb.services.core;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Permission;

@Service
public class JwtService {

	private final JwtEncoder encoder;
	private final long accessTtlMinutes;

	public JwtService(JwtEncoder encoder,
			@Value("${zfgbb.auth.jwt.access-ttl-minutes}") long accessTtlMinutes) {
		this.encoder = encoder;
		this.accessTtlMinutes = accessTtlMinutes;
	}

	public String issueAccessToken(User user) {
		Instant now = Instant.now();
		List<String> permissions = user.getPermissions() == null
				? List.of()
				: user.getPermissions().stream().map(Permission::getPermissionCode).toList();

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(String.valueOf(user.getUserId()))
				.issuedAt(now)
				.expiresAt(now.plus(Duration.ofMinutes(accessTtlMinutes)))
				.claim("userName", user.getUserName())
				.claim("displayName", user.getDisplayName())
				.claim("permissions", permissions)
				.build();

		Jwt jwt = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(() -> "HS256").build(), claims));
		return jwt.getTokenValue();
	}
}
