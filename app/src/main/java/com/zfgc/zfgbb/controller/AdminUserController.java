package com.zfgc.zfgbb.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.services.core.UserDeletionService;

@Slf4j
@RestController
@RequestMapping("/system/users")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class AdminUserController extends BaseController {

	@Autowired
	private UserDeletionService userDeletionService;
	
	@Autowired
	private com.zfgc.zfgbb.services.core.deletion.CoreUserDataHandler coreUserDataHandler;

	@GetMapping
	public ResponseEntity<List<UserSummary>> list() {
     log.info("Executing list");
		return ResponseEntity.ok(coreUserDataHandler.listUsers());
	}

	@PostMapping("/delete")
	public ResponseEntity<Void> delete(@RequestBody UserDeletionService.UserDeletionRequest request) {
     log.info("Executing delete");
     log.debug("Executing delete with request={}", request);
		userDeletionService.deleteUser(request.userId(), request.mode(), zfgcUser());
		return ResponseEntity.noContent().build();
	}
}
