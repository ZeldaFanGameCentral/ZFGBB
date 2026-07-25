package com.zfgc.zfgbb.controller.admin;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.cms.merge.MigrationConflictService;

@Slf4j
@RestController
@RequestMapping("/admin/migrate/conflicts")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class MigrationConflictController extends BaseController {

	private final MigrationConflictService conflictService;

	@GetMapping
	public ResponseEntity<List<MigrationConflictService.ConflictView>> list(
			@RequestParam(name = "status", required = false) String status) {
		return ResponseEntity.ok(conflictService.list(status));
	}

	@PostMapping("/scan")
	public ResponseEntity<Map<String, Object>> scan() {
     log.info("Executing scan");
		int detected = conflictService.scan();
		return ResponseEntity.ok(Map.of("detected", detected));
	}

	@PostMapping("/{id}/resolve")
	public ResponseEntity<MigrationConflictService.ConflictView> resolve(@PathVariable Integer id,
			@RequestBody MigrationConflictService.ResolveRequest request) {
		return ResponseEntity.ok(conflictService.resolve(id, request.sourceType(), request.customValue(),
				super.zfgcUser().getUserId()));
	}

	@PostMapping("/{id}/dismiss")
	public ResponseEntity<MigrationConflictService.ConflictView> dismiss(@PathVariable Integer id) {
     log.info("Executing dismiss with id={}", id);
		return ResponseEntity.ok(conflictService.dismiss(id, super.zfgcUser().getUserId()));
	}
}
