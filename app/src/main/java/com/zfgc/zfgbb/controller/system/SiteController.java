package com.zfgc.zfgbb.controller.system;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.authorization.AllowAnonymous;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.model.system.SiteInfo;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Slf4j
@RestController
@RequestMapping("/system/site")
@AllowAnonymous
@RequiredArgsConstructor
public class SiteController {

	private final SystemConfigService systemConfigService;

	@Value("${zfgbb.registration.enabled:false}")
	private final boolean registrationEnabled;

	@Value("${zfgbb.build.version:}")
	private final String buildVersion;

	@GetMapping
	public ResponseEntity<SiteInfo> site() {
		boolean installed = systemConfigService.isInstalled();
		String siteName = installed ? systemConfigService.get(SystemConfigService.Keys.SITE_NAME) : null;
		return ResponseEntity.ok(new SiteInfo(siteName, registrationEnabled,
				systemConfigService.authoringDefaultContentFormat().name(), ContentFormat.authorableCodes(),
				buildVersion.isBlank() ? Optional.empty() : Optional.of(buildVersion), installed));
	}
}
