package com.zfgc.zfgbb.controller.system;

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
import jakarta.validation.Valid;

import com.zfgc.zfgbb.authorization.AllowAnonymous;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.users.LoginResponse;
import com.zfgc.zfgbb.services.auth.AuthCookieService;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.install.InstallService;
import com.zfgc.zfgbb.dataprovider.system.InstallRunDataProvider;
import com.zfgc.zfgbb.services.install.InstallTokenGate;
import com.zfgc.zfgbb.services.system.SystemConfigService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController
@RequestMapping("/system/install")
@AllowAnonymous
@RequiredArgsConstructor
public class SystemController {

	private final InstallService installService;
	private final SystemConfigService systemConfigService;
	private final AuthService authService;
	private final AuthCookieService cookieService;
	@Value("${zfgbb.install.token:}")
	private final String installToken;
	private final InstallRunDataProvider installRun;
	private final InstallTokenGate tokenGate;

	@PostMapping
	public ResponseEntity<InstallResponse> install(
			@Valid @RequestBody InstallRequest request,
			@RequestHeader(value = "X-Install-Token", required = false) String presentedToken) {
		if (StringUtils.isBlank(installToken) || tokenGate.isLocked()) {
			throw notFound();
		}

		if (!constantTimeEquals(presentedToken, installToken)) {
			tokenGate.recordFailure();
			throw notFound();
		}
		tokenGate.recordSuccess();
		InstallResult result;
		try {
			result = installService.install(request);
		} catch (ZfgcConflictException conflict) {
			if (systemConfigService.isInstalled())
				throw notFound();
			throw conflict;
		}
		InstallResponse baseResponse = result.response();

		if (Boolean.TRUE.equals(request.useTokens())) {
			LoginResponse tokens = authService.issueLoginResponse(result.admin(), false);
			InstallResponse withTokens = baseResponse.withTokens(tokens.accessToken(),
					tokens.refreshToken());
			return clearedCookieResponse().body(withTokens);
		}

		return clearedCookieResponse().body(baseResponse);
	}

	private ResponseEntity.BodyBuilder clearedCookieResponse() {
		return cookieService.clearTokenCookies(ResponseEntity.ok());
	}

	private static boolean constantTimeEquals(String presented, String expected) {
		if (presented == null) {
			return false;
		}
		return MessageDigest.isEqual(
				presented.getBytes(StandardCharsets.UTF_8),
				expected.getBytes(StandardCharsets.UTF_8));
	}

	private static ResponseStatusException notFound() {
		return new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
