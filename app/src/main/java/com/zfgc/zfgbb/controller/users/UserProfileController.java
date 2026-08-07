package com.zfgc.zfgbb.controller.users;

import com.zfgc.zfgbb.config.security.AllowAnonymous;

import org.springframework.http.ResponseEntity;
import org.springframework.http.CacheControl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UpdateUserProfileRequest;
import com.zfgc.zfgbb.services.users.UserService;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController extends BaseController {
	private final UserService userService;
	
	@GetMapping("/{userId}")
	@AllowAnonymous
	public ResponseEntity getUserProfile(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(userService.loadUser(userId, zfgcUser()));
	}

	@GetMapping("/{userId}/allowed-actions")
	@AllowAnonymous
	public ResponseEntity<Set<String>> getAllowedActions(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate())
				.body(userService.profileAllowedActions(userId, zfgcUser()));
	}
	
	@PutMapping("/{userId}")
	@PreAuthorize("(#userId == principal.userId or hasRole('ROLE_ZFGC_PROFILE_ADMIN')) and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity saveUserProfile(@PathVariable("userId") Integer userId,
			@RequestBody UpdateUserProfileRequest request) {
		return ResponseEntity.ok(userService.saveUserProfile(userId, request, zfgcUser()));
	}

	@PostMapping("/{userId}/settings")
	@PreAuthorize("(#userId == principal.userId or hasRole('ROLE_ZFGC_PROFILE_ADMIN')) and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity saveUserSettings(@PathVariable("userId") Integer userId,
			@RequestBody UserSettings settings) {
		return ResponseEntity.ok(userService.saveUserSettings(userId, settings, zfgcUser()));
	}

	@GetMapping("/awards/catalog")
	@AllowAnonymous
	public ResponseEntity getAwardCatalog() {
		return ResponseEntity.ok(userService.getAwardCatalog());
	}

	@PostMapping("/{userId}/awards")
	@PreAuthorize("hasRole('ROLE_ZFGC_PROFILE_ADMIN') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity grantAward(@PathVariable("userId") Integer userId,
			@RequestBody UserService.GrantAwardRequest request) {
		return ResponseEntity.ok(userService.grantAward(userId, request, zfgcUser()));
	}

}
