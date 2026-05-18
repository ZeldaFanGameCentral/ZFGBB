package com.zfgc.zfgbb.config.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;
import com.zfgc.zfgbb.services.core.AuthCookieService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessCookieBearerHeaderFilter extends OncePerRequestFilter {

	private final AuthCookieService cookieService;

	public AccessCookieBearerHeaderFilter(AuthCookieService cookieService) {
		this.cookieService = cookieService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			chain.doFilter(request, response);
			return;
		}
		String token = cookieService.readAccessCookie(request);
		if (token == null || isExpired(token)) {
			chain.doFilter(request, response);
			return;
		}
		chain.doFilter(new AuthHeaderRequest(request, "Bearer " + token), response);
	}

	private static boolean isExpired(String token) {
		try {
			SignedJWT jwt = SignedJWT.parse(token);
			Date exp = jwt.getJWTClaimsSet().getExpirationTime();
			return exp != null && exp.toInstant().isBefore(Instant.now());
		} catch (Exception e) {
			return false;
		}
	}

	private static final class AuthHeaderRequest extends HttpServletRequestWrapper {
		private final String authHeader;

		AuthHeaderRequest(HttpServletRequest request, String authHeader) {
			super(request);
			this.authHeader = authHeader;
		}

		@Override
		public String getHeader(String name) {
			if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
				return authHeader;
			}
			return super.getHeader(name);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
				return Collections.enumeration(Collections.singletonList(authHeader));
			}
			return super.getHeaders(name);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			Enumeration<String> original = super.getHeaderNames();
			java.util.Set<String> names = new java.util.LinkedHashSet<>();
			while (original.hasMoreElements()) {
				names.add(original.nextElement());
			}
			names.add(HttpHeaders.AUTHORIZATION);
			return Collections.enumeration(names);
		}
	}
}
