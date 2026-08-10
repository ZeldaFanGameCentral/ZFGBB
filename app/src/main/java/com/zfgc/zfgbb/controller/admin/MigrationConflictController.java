package com.zfgc.zfgbb.controller.admin;

import java.util.ArrayList;
import java.util.List;
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
import lombok.extern.slf4j.Slf4j;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.cms.ConflictView;
import com.zfgc.zfgbb.services.cms.merge.MigrationConflictService;

@Slf4j
@RestController
@RequestMapping("/admin/migrate/conflicts")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class MigrationConflictController extends BaseController {
	private final MigrationConflictService conflictService;

	@GetMapping
	public ResponseEntity<List<ConflictView>> list(
			@RequestParam(name = "status", required = false) String status) {
		return ResponseEntity.ok(conflictService.list(status));
	}

	@PostMapping("/scan")
	public ResponseEntity<Map<String, Object>> scan() {
		int detected = conflictService.scan();
		return ResponseEntity.ok(Map.of("detected", detected));
	}

	@PostMapping("/{id}/resolve")
	public ResponseEntity<ConflictView> resolve(@PathVariable Integer id,
			@RequestBody MigrationConflictService.ResolveRequest request) {
		return ResponseEntity.ok(conflictService.resolve(id, request.sourceType(), request.customValue(),
				super.zfgcUser().getUserId()));
	}

	@PostMapping("/resolve")
	public ResponseEntity<List<MigrationConflictService.BulkOutcome>> resolveEach(
			@RequestBody List<MigrationConflictService.ResolveOne> requests) {
		Integer userId = super.zfgcUser().getUserId();
		List<MigrationConflictService.BulkOutcome> outcomes = new ArrayList<>();
		for (MigrationConflictService.ResolveOne request : requests) {
			try {
				conflictService.resolve(request.id(), request.sourceType(), request.customValue(), userId);
				outcomes.add(new MigrationConflictService.BulkOutcome(request.id(), true, null));
			} catch (RuntimeException failure) {
				log.warn("bulk resolve failed for conflict {}", request.id(), failure);
				outcomes.add(new MigrationConflictService.BulkOutcome(request.id(), false,
						failure.getClass().getSimpleName()));
			}
		}
		return ResponseEntity.ok(outcomes);
	}

	@PostMapping("/{id}/reopen")
	public ResponseEntity<ConflictView> reopen(@PathVariable Integer id) {
		return ResponseEntity.ok(conflictService.reopen(id));
	}

	@PostMapping("/{id}/dismiss")
	public ResponseEntity<ConflictView> dismiss(@PathVariable Integer id) {
		return ResponseEntity.ok(conflictService.dismiss(id, super.zfgcUser().getUserId()));
	}
}
