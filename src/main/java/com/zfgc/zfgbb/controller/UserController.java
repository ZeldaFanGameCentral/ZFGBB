package com.zfgc.zfgbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.model.users.AuthCredentials;
import com.zfgc.zfgbb.model.users.RefreshRequest;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.core.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@GetMapping("/loggedInUser")
	public ResponseEntity<?> getLoggedInUser() {
		return ResponseEntity.ok(zfgcUser());
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthCredentials credentials) {
		return ResponseEntity.ok(authService.login(credentials));
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
		return ResponseEntity.ok(authService.refresh(request.refreshToken()));
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<?> logout(@RequestBody RefreshRequest request) {
		authService.logout(request.refreshToken());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@RequestBody RegistrationRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.createNewUser(request));
	}
}
