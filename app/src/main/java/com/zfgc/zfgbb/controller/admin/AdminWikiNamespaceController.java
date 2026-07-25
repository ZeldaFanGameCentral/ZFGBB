package com.zfgc.zfgbb.controller.admin;

import com.zfgc.zfgbb.controller.BaseController;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.ImportNamespaceRecord;
import com.zfgc.zfgbb.services.cms.wiki.WikiNamespaceRegistry;

@Slf4j
@RestController
@RequestMapping("/admin/wiki/import-namespaces")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class AdminWikiNamespaceController extends BaseController {

	private final WikiNamespaceRegistry wikiNamespaceRegistry;

	@GetMapping
	public ResponseEntity<List<ImportNamespaceRecord>> listImportNamespaces() {
		return ResponseEntity.ok(wikiNamespaceRegistry.listImportNamespaces());
	}

	public record ImportNamespaceRequest(String namespaceName) {
	}

	@PutMapping("/{sourceNamespaceId}")
	public ResponseEntity<List<ImportNamespaceRecord>> saveImportNamespace(
			@PathVariable("sourceNamespaceId") Integer sourceNamespaceId,
			@RequestBody ImportNamespaceRequest request) {
		if (request == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "namespaceName is required");
		try {
			return ResponseEntity.ok(
					wikiNamespaceRegistry.saveImportNamespace(sourceNamespaceId, request.namespaceName()));
		} catch (IllegalArgumentException invalid) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalid.getMessage(), invalid);
		}
	}

	@DeleteMapping("/{sourceNamespaceId}")
	public ResponseEntity<List<ImportNamespaceRecord>> removeImportNamespace(
			@PathVariable("sourceNamespaceId") Integer sourceNamespaceId) {
		return ResponseEntity.ok(wikiNamespaceRegistry.removeImportNamespace(sourceNamespaceId));
	}
}
