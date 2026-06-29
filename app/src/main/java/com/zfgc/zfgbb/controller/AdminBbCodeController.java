package com.zfgc.zfgbb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.renderer.BBCodeService;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;

@RestController
@RequestMapping("/system/bbcodes")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class AdminBbCodeController extends BaseController {

	@Autowired
	private BBCodeService bbCodeService;

	@GetMapping
	public ResponseEntity<List<BBCodeDataProvider.BbCodeToggle>> listBbCodes() {
		return ResponseEntity.ok(bbCodeService.listBbCodes());
	}

	public record BbCodeEnabledRequest(Boolean enabled) {
	}

	@PutMapping("/{code}")
	public ResponseEntity<BBCodeDataProvider.BbCodeToggle> setEnabled(@PathVariable("code") String code,
			@RequestBody BbCodeEnabledRequest request) {
		if (request == null || request.enabled() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "enabled is required");
		return ResponseEntity.ok(bbCodeService.setBbCodeEnabled(code, request.enabled()));
	}
}
