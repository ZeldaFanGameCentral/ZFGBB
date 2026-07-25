package com.zfgc.zfgbb.controller.system;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.model.system.SiteInfo;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Slf4j
@RestController
@RequestMapping("/system/site")
@AllowAnonymous
public class SiteController {

	private final SystemConfigService systemConfigService;
	private final boolean registrationEnabled;
	private final Optional<String> buildVersion;

	public SiteController(SystemConfigService systemConfigService,
			@Value("${zfgbb.registration.enabled:false}") boolean registrationEnabled,
			@Value("${zfgbb.build.version:}") String buildVersion) {
		this.systemConfigService = systemConfigService;
		this.registrationEnabled = registrationEnabled;
		this.buildVersion = buildVersion.isBlank() ? Optional.empty() : Optional.of(buildVersion);
	}

	@GetMapping
	public ResponseEntity<SiteInfo> site() {
		String siteName = systemConfigService.get(SystemConfigService.Keys.SITE_NAME);
		return ResponseEntity.ok(new SiteInfo(siteName, registrationEnabled,
				systemConfigService.authoringDefaultContentFormat().name(), ContentFormat.authorableCodes(),
				buildVersion));
	}
}
