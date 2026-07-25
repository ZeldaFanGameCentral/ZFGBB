package com.zfgc.zfgbb.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.cms.MergeApplyRequest;
import com.zfgc.zfgbb.model.cms.MergeCandidate;
import com.zfgc.zfgbb.services.cms.merge.MigrationConflictService;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/cms")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class AdminCmsController extends BaseController {

	private final MigrationConflictService migrationConflictService;

	private final SystemConfigService systemConfigService;

	@GetMapping("/merge-candidates")
	public ResponseEntity<List<MergeCandidate>> getMergeCandidates() {
     log.info("Executing getMergeCandidates");
		return ResponseEntity.ok(migrationConflictService.getMergeCandidates());
	}

	@PostMapping("/merge")
	public ResponseEntity<Void> applyMerge(@RequestBody MergeApplyRequest request) {
		log.info("Executing applyMerge");
		log.debug("Executing applyMerge with request={}", request);
		migrationConflictService.apply(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/config")
	public ResponseEntity<SystemConfigService.CmsConfig> getConfig() {
		log.info("Executing getConfig");
		return ResponseEntity.ok(systemConfigService.getCmsConfig());
	}

	@PutMapping("/config")
	public ResponseEntity<SystemConfigService.CmsConfig> setConfig(@RequestBody SystemConfigService.CmsConfig config) {
		log.info("Executing setConfig");
		log.debug("Executing setConfig with config={}", config);
		return ResponseEntity.ok(systemConfigService.setCmsConfig(config));
	}
}
