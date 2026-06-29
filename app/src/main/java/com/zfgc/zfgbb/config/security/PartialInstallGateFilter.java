package com.zfgc.zfgbb.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zfgc.zfgbb.services.system.InstallRunRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PartialInstallGateFilter extends OncePerRequestFilter {

	private final InstallRunRepository installs;

	public PartialInstallGateFilter(InstallRunRepository installs) {
		this.installs = installs;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String state = installs.get().state();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		boolean allowed = "OPTIONS".equals(request.getMethod()) || path.equals("/system/install")
				|| path.equals("/system/install/status") || path.equals("/actuator/health")
				|| path.startsWith("/actuator/health/") || path.equals("/error");
		if (!"INSTALLED".equals(state) && !allowed) {
			response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Installation recovery is in progress.");
			return;
		}
		chain.doFilter(request, response);
	}
}
