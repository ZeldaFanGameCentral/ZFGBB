package com.zfgc.zfgbb.controller.admin;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@RestController
@RequestMapping("/admin/site")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class AdminSiteController extends BaseController {
	public record AuthoringConfig(String defaultContentFormat, List<String> contentFormats) {}

	public record AuthoringConfigRequest(String defaultContentFormat) {}

	private final SystemConfigService systemConfigService;

	@GetMapping("/authoring")
	public ResponseEntity<AuthoringConfig> getAuthoringConfig() {
		return ResponseEntity.ok(authoringConfig());
	}

	@PutMapping("/authoring")
	public ResponseEntity<AuthoringConfig> setAuthoringConfig(@RequestBody AuthoringConfigRequest request) {
		String requested = request == null ? null : request.defaultContentFormat();
		systemConfigService.setAuthoringDefaultContentFormat(ContentFormat.parse(requested)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"defaultContentFormat must be one of " + ContentFormat.authorableCodes()
								+ " but was '" + requested + "'")));
		return ResponseEntity.ok(authoringConfig());
	}

	private AuthoringConfig authoringConfig() {
		return new AuthoringConfig(systemConfigService.authoringDefaultContentFormat().name(),
				ContentFormat.authorableCodes());
	}
}
