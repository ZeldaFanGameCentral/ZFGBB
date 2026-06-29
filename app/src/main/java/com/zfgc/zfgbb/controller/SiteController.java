package com.zfgc.zfgbb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import com.zfgc.zfgbb.model.system.SiteInfo;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@RestController
@RequestMapping("/system")
@AllowAnonymous
public class SiteController {

	private final SystemConfigService systemConfigService;
	private final boolean registrationEnabled;

	public SiteController(SystemConfigService systemConfigService,
			@Value("${zfgbb.registration.enabled:false}") boolean registrationEnabled) {
		this.systemConfigService = systemConfigService;
		this.registrationEnabled = registrationEnabled;
	}

	@GetMapping("/site")
	public ResponseEntity<SiteInfo> site() {
		String siteName = systemConfigService.get(SystemConfigService.Keys.SITE_NAME);
		return ResponseEntity.ok(new SiteInfo(siteName, registrationEnabled));
	}
}
