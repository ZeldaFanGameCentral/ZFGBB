package com.zfgc.zfgbb.services.core;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthCookieService {

	public static final String ACCESS_COOKIE_NAME = "zfgbb_access_token";
	public static final String REFRESH_COOKIE_NAME = "zfgbb_refresh_token";

	private final boolean secure;
	private final long accessTtlMinutes;
	private final long refreshTtlDays;
	private final String refreshPath;

	public AuthCookieService(
			@Value("${zfgbb.auth.cookie.secure:false}") boolean secure,
			@Value("${zfgbb.auth.jwt.access-ttl-minutes}") long accessTtlMinutes,
			@Value("${zfgbb.auth.refresh.ttl-days}") long refreshTtlDays,
			@Value("${server.servlet.context-path:}") String contextPath) {
		this.secure = secure;
		this.accessTtlMinutes = accessTtlMinutes;
		this.refreshTtlDays = refreshTtlDays;
		this.refreshPath = contextPath + "/users/auth/refresh";
	}

	public ResponseCookie buildAccessCookie(String token, boolean persistent) {
		ResponseCookieBuilder b = ResponseCookie.from(ACCESS_COOKIE_NAME, token)
				.httpOnly(true)
				.secure(secure)
				.sameSite("Lax")
				.path("/");
		// Without persistence, omit maxAge so the browser drops the cookie when the
		// session ends. With persistence, bound it by the access-token TTL.
		if (persistent) {
			b.maxAge(Duration.ofMinutes(accessTtlMinutes));
		}
		return b.build();
	}

	public ResponseCookie buildRefreshCookie(String token, boolean persistent) {
		ResponseCookieBuilder b = ResponseCookie.from(REFRESH_COOKIE_NAME, token)
				.httpOnly(true)
				.secure(secure)
				.sameSite("Strict")
				.path(refreshPath);
		if (persistent) {
			b.maxAge(Duration.ofDays(refreshTtlDays));
		}
		return b.build();
	}

	public ResponseCookie clearAccessCookie() {
		return ResponseCookie.from(ACCESS_COOKIE_NAME, "")
				.httpOnly(true)
				.secure(secure)
				.sameSite("Lax")
				.path("/")
				.maxAge(0)
				.build();
	}

	public ResponseCookie clearRefreshCookie() {
		return ResponseCookie.from(REFRESH_COOKIE_NAME, "")
				.httpOnly(true)
				.secure(secure)
				.sameSite("Strict")
				.path(refreshPath)
				.maxAge(0)
				.build();
	}

	public String readAccessCookie(HttpServletRequest request) {
		return readCookie(request, ACCESS_COOKIE_NAME);
	}

	public String readRefreshCookie(HttpServletRequest request) {
		return readCookie(request, REFRESH_COOKIE_NAME);
	}

	private String readCookie(HttpServletRequest request, String name) {
		if (request == null || request.getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if (name.equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isBlank()) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
