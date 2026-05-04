package com.zfgc.zfgbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.model.users.AuthCredentials;
import com.zfgc.zfgbb.model.users.LoginResponse;
import com.zfgc.zfgbb.model.users.RefreshRequest;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.model.users.TokenPair;
import com.zfgc.zfgbb.services.core.AuthCookieService;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.core.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthCookieService cookieService;

	@Value("${zfgbb.registration.enabled:false}")
	private boolean registrationEnabled;

	@GetMapping("/loggedInUser")
	public ResponseEntity<?> getLoggedInUser() {
		return ResponseEntity.ok(zfgcUser());
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthCredentials credentials) {
		LoginResponse result = authService.login(credentials);

		if (credentials.isUseTokens()) {
			return ResponseEntity.ok(result);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.buildAccessCookie(result.accessToken()).toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.buildRefreshCookie(result.refreshToken()).toString())
				.body(result.user());
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<?> refresh(@RequestBody(required = false) RefreshRequest request, HttpServletRequest httpRequest) {
		String bodyToken = request != null ? request.refreshToken() : null;
		boolean fromBody = bodyToken != null && !bodyToken.isBlank();
		String refreshToken = fromBody ? bodyToken : cookieService.readRefreshCookie(httpRequest);

		if (refreshToken == null || refreshToken.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		TokenPair pair = authService.refresh(refreshToken);

		if (fromBody) {
			return ResponseEntity.ok(pair);
		}

		return ResponseEntity.noContent()
				.header(HttpHeaders.SET_COOKIE, cookieService.buildAccessCookie(pair.accessToken()).toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.buildRefreshCookie(pair.refreshToken()).toString())
				.build();
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<?> logout(@RequestBody(required = false) RefreshRequest request, HttpServletRequest httpRequest) {
		String bodyToken = request != null ? request.refreshToken() : null;
		String refreshToken = (bodyToken != null && !bodyToken.isBlank())
				? bodyToken
				: cookieService.readRefreshCookie(httpRequest);

		if (refreshToken != null && !refreshToken.isBlank()) {
			authService.logout(refreshToken);
		}

		return ResponseEntity.noContent()
				.header(HttpHeaders.SET_COOKIE, cookieService.clearAccessCookie().toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.clearRefreshCookie().toString())
				.build();
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@RequestBody RegistrationRequest request) {
		if (!registrationEnabled) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Registration is currently disabled");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.createNewUser(request));
	}
}
