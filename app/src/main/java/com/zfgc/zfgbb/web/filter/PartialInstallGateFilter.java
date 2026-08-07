package com.zfgc.zfgbb.web.filter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zfgc.zfgbb.services.install.InstallRunRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class PartialInstallGateFilter extends OncePerRequestFilter {

	private final InstallRunRepository installs;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String state = installs.get().state();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		boolean allowed = request.getMethod().equals("OPTIONS") || path.equals("/system/install")
				|| path.equals("/system/install/status") || path.equals("/actuator/health")
				|| path.startsWith("/actuator/health/") || path.equals("/error");
		if (!state.equals("INSTALLED") && !allowed) {
			response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Installation recovery is in progress.");
			return;
		}
		chain.doFilter(request, response);
	}
}
