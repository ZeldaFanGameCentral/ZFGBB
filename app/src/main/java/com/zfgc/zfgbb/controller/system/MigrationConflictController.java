package com.zfgc.zfgbb.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.system.MigrationConflictService;

@RestController
@RequestMapping("/system/migrate/conflicts")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class MigrationConflictController extends BaseController {

	@Autowired
	private MigrationConflictService conflictService;

	@GetMapping
	public ResponseEntity<List<MigrationConflictService.ConflictView>> list(
			@RequestParam(name = "status", required = false) String status) {
		return ResponseEntity.ok(conflictService.list(status));
	}

	@PostMapping("/scan")
	public ResponseEntity<Map<String, Object>> scan() {
		int detected = conflictService.scan();
		return ResponseEntity.ok(Map.of("detected", detected));
	}

	@PostMapping("/{id}/resolve")
	public ResponseEntity<MigrationConflictService.ConflictView> resolve(@PathVariable Integer id,
			@RequestBody MigrationConflictService.ResolveRequest request) {
		return ResponseEntity.ok(conflictService.resolve(id, request.getSourceType(), request.getCustomValue(),
				super.zfgcUser().getUserId()));
	}

	@PostMapping("/{id}/dismiss")
	public ResponseEntity<MigrationConflictService.ConflictView> dismiss(@PathVariable Integer id) {
		return ResponseEntity.ok(conflictService.dismiss(id, super.zfgcUser().getUserId()));
	}
}
