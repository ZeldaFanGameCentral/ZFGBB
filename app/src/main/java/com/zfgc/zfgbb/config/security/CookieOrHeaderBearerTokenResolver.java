package com.zfgc.zfgbb.config.security;

import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;

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
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}
		if (path.startsWith("/users/auth/")) {
			return null;
		}

		String fromHeader = headerResolver.resolve(request);
		if (fromHeader != null) {
			return fromHeader;
		}
		return cookieService.readAccessCookie(request);
	}
}
