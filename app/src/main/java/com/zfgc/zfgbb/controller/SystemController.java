package com.zfgc.zfgbb.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.system.InstallStatusResponse;
import com.zfgc.zfgbb.model.users.LoginResponse;
import com.zfgc.zfgbb.services.core.AuthCookieService;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.system.InstallService;
import com.zfgc.zfgbb.services.system.InstallRunRepository;
import com.zfgc.zfgbb.services.system.SystemConfigService;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/system/install")
@AllowAnonymous
public class SystemController {

	private final InstallService installService;
	private final SystemConfigService systemConfigService;
	private final AuthService authService;
	private final AuthCookieService cookieService;
	private final String installToken;
	private final InstallRunRepository installRun;

	public SystemController(InstallService installService,
			SystemConfigService systemConfigService,
			AuthService authService,
			AuthCookieService cookieService,
			@Value("${zfgbb.install.token:}") String installToken, InstallRunRepository installRun) {
		this.installService = installService;
		this.systemConfigService = systemConfigService;
		this.authService = authService;
		this.cookieService = cookieService;
		this.installToken = installToken;
		this.installRun = installRun;
	}

	@GetMapping("/status")
	public ResponseEntity<InstallStatusResponse> status() {
		boolean installed = systemConfigService.isInstalled();
		String siteName = installed ? systemConfigService.get(SystemConfigService.Keys.SITE_NAME) : null;
		InstallRunRepository.Run run = installRun.get();
		return ResponseEntity.ok(new InstallStatusResponse(installed, siteName, run.state(), run.lastError()));
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
		InstallResult result;
		try {
			result = installService.install(request);
		} catch (com.zfgc.zfgbb.exception.ZfgcConflictException conflict) {
			if (systemConfigService.isInstalled())
				throw notFound();
			throw conflict;
		}
		LoginResponse tokens = authService.issueLoginResponse(result.admin(), false);
		InstallResponse baseResponse = result.response();

		if (Boolean.TRUE.equals(request.useTokens())) {
			InstallResponse withTokens = new InstallResponse(
					baseResponse.installed(),
					baseResponse.adminUserId(),
					baseResponse.siteName(),
					baseResponse.contentPack(),
					tokens.accessToken(),
					tokens.refreshToken());
			return ResponseEntity.ok(withTokens);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.buildAccessCookie(tokens.accessToken(), false).toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.buildRefreshCookie(tokens.refreshToken(), false).toString())
				.body(baseResponse);
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
