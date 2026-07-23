package com.zfgc.zfgbb.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.zfgc.zfgbb.services.cms.CmsAdminService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/system/cms")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class CmsAdminController extends BaseController {

	@Autowired
	private CmsAdminService cmsAdminService;

	@GetMapping("/merge-candidates")
	public ResponseEntity<List<CmsAdminService.MergeCandidate>> getMergeCandidates() {
     log.info("Executing getMergeCandidates");
		return ResponseEntity.ok(cmsAdminService.getMergeCandidates());
	}

	@PostMapping("/merge")
	public ResponseEntity<Void> applyMerge(@RequestBody CmsAdminService.MergeApplyRequest request) {
		log.info("Executing applyMerge");
		log.debug("Executing applyMerge with request={}", request);
		cmsAdminService.apply(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/config")
	public ResponseEntity<CmsAdminService.CmsConfig> getConfig() {
		log.info("Executing getConfig");
		return ResponseEntity.ok(cmsAdminService.getConfig());
	}

	@PutMapping("/config")
	public ResponseEntity<CmsAdminService.CmsConfig> setConfig(@RequestBody CmsAdminService.CmsConfig config) {
		log.info("Executing setConfig");
		log.debug("Executing setConfig with config={}", config);
		return ResponseEntity.ok(cmsAdminService.setConfig(config));
	}
}
