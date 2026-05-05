package com.zfgc.zfgbb.config.security;

import java.time.Instant;
import java.util.Date;

import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;
import com.zfgc.zfgbb.services.core.AuthCookieService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieOrHeaderBearerTokenResolver implements BearerTokenResolver {

	private final DefaultBearerTokenResolver headerResolver = new DefaultBearerTokenResolver();
	private final AuthCookieService cookieService;

	public CookieOrHeaderBearerTokenResolver(AuthCookieService cookieService) {
		this.cookieService = cookieService;
	}

	@Override
	public String resolve(HttpServletRequest request) {
		String token = headerResolver.resolve(request);
		if (token == null) {
			token = cookieService.readAccessCookie(request);
		}
		if (token == null) {
			return null;
		}

		try {
			SignedJWT jwt = SignedJWT.parse(token);
			Date exp = jwt.getJWTClaimsSet().getExpirationTime();
			if (exp != null && exp.toInstant().isBefore(Instant.now())) {
				return null;
			}
		} catch (Exception ignored) {
		}

		return token;
	}
}
