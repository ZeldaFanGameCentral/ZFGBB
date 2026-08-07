package com.zfgc.zfgbb.web.filter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zfgc.zfgbb.services.system.MaintenanceCoordinator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class MaintenanceGateFilter extends OncePerRequestFilter {
	private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS", "TRACE");
	private final MaintenanceCoordinator maintenance;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		if (SAFE_METHODS.contains(request.getMethod())) {
			chain.doFilter(request, response);
			return;
		}
		try {
			Optional<MaintenanceCoordinator.Lease> lease = maintenance.tryMutationLease();
			if (lease.isEmpty()) {
				response.setHeader(HttpHeaders.RETRY_AFTER, "5");
				response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),
						"Application maintenance is in progress.");
				return;
			}
			try (MaintenanceCoordinator.Lease heldLease = lease.get()) {
				chain.doFilter(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException("Unable to acquire mutation maintenance lease", e);
		}
	}
}
