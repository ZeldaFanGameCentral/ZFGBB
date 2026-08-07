package com.zfgc.zfgbb.controller.search;

import com.zfgc.zfgbb.controller.BaseController;

import com.zfgc.zfgbb.config.security.AllowAnonymous;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.services.search.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController extends BaseController {
	private final SearchService searchService;

	@GetMapping
	@AllowAnonymous
	public ResponseEntity search(@RequestParam(name = "q") String query,
			@RequestParam(name = "types", required = false) List<String> types) {
		return ResponseEntity.ok(searchService.search(query, types, zfgcUser().permissionIds()));
	}

	@GetMapping("/realms")
	@AllowAnonymous
	public ResponseEntity realms() {
		return ResponseEntity.ok(searchService.getRealms());
	}
}
