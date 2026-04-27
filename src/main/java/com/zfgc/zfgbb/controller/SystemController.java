package com.zfgc.zfgbb.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallStatusResponse;
import com.zfgc.zfgbb.services.system.InstallService;
import com.zfgc.zfgbb.services.system.SystemConfigService;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/system/install")
public class SystemController {

	private final InstallService installService;
	private final SystemConfigService systemConfigService;
	private final String installToken;

	public SystemController(InstallService installService,
			SystemConfigService systemConfigService,
			@Value("${zfgbb.install.token:}") String installToken) {
		this.installService = installService;
		this.systemConfigService = systemConfigService;
		this.installToken = installToken;
	}

	@GetMapping("/status")
	public ResponseEntity<InstallStatusResponse> status() {
		boolean installed = systemConfigService.isInstalled();
		String siteName = installed ? systemConfigService.get(SystemConfigService.Keys.SITE_NAME) : null;
		return ResponseEntity.ok(new InstallStatusResponse(installed, siteName));
	}

	@PostMapping
	public ResponseEntity<InstallResponse> install(
			@RequestBody InstallRequest request,
			@RequestHeader(value = "X-Install-Token", required = false) String presentedToken) {
		if (StringUtils.isBlank(installToken)) {
			// Endpoint disabled: pretend it doesn't exist.
			throw notFound();
		}
		if (!constantTimeEquals(presentedToken, installToken)) {
			throw notFound();
		}
		if (systemConfigService.isInstalled()) {
			throw notFound();
		}

		InstallResponse response = installService.install(request);
		return ResponseEntity.ok(response);
	}

	private static boolean constantTimeEquals(String presented, String expected) {
		if (presented == null) {
			return false;
		}
		// Constant-time comparison to avoid timing-side-channel leaks of token length /
		// prefix.
		return java.security.MessageDigest.isEqual(
				presented.getBytes(java.nio.charset.StandardCharsets.UTF_8),
				expected.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}

	private static ResponseStatusException notFound() {
		return new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
