package com.zfgc.zfgbb.controller.admin;

import com.zfgc.zfgbb.controller.BaseController;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.services.users.UserService;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class AdminUserController extends BaseController {

	private final UserService userService;

	public record UserDeletionRequest(Integer userId, DeletionMode mode) {}

	@GetMapping
	public ResponseEntity<List<UserSummary>> list() {
		return ResponseEntity.ok(userService.listUsers());
	}

	@PostMapping("/delete")
	public ResponseEntity<Void> delete(@Valid @RequestBody UserDeletionRequest request) {
		userService.deleteUser(request.userId(), request.mode(), zfgcUser());
		return ResponseEntity.noContent().build();
	}
}
