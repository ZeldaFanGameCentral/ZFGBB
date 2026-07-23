package com.zfgc.zfgbb.controller;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.services.search.SearchService;
import com.zfgc.zfgbb.model.users.Permission;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

	@Autowired
	private SearchService searchService;

	@GetMapping
	@AllowAnonymous
	public ResponseEntity search(@RequestParam(name = "q") String query,
			@RequestParam(name = "types", required = false) List<String> types) {
		List<Integer> permissionIds = zfgcUser().getPermissions().stream()
				.map(Permission::getId)
				.filter(Objects::nonNull)
				.toList();
		return ResponseEntity.ok(searchService.search(query, types, permissionIds));
	}

	@GetMapping("/realms")
	@AllowAnonymous
	public ResponseEntity realms() {
     log.info("Executing realms");
		return ResponseEntity.ok(searchService.getRealms());
	}
}
