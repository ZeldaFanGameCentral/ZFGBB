package com.zfgc.zfgbb.controller;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
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

import jakarta.validation.Valid;

import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.AccountDeletionConfirmation;
import com.zfgc.zfgbb.model.users.AccountDeletionPreview;
import com.zfgc.zfgbb.model.users.AccountDeletionRequest;
import com.zfgc.zfgbb.model.users.AccountDeletionState;
import com.zfgc.zfgbb.model.users.AuthCredentials;
import com.zfgc.zfgbb.model.users.LoginResponse;
import com.zfgc.zfgbb.model.users.RefreshRequest;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.model.users.TokenPair;
import com.zfgc.zfgbb.services.core.AccountDeletionService;
import com.zfgc.zfgbb.services.core.AuthCookieService;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.core.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

	private final UserService userService;
	private final AuthService authService;
	private final AuthCookieService cookieService;
	private final AccountDeletionService accountDeletionService;

	@Value("${zfgbb.registration.enabled:false}")
	private boolean registrationEnabled;

	@GetMapping("/loggedInUser")
	@AllowAnonymous
	public ResponseEntity<User> getLoggedInUser() {
     log.info("Executing getLoggedInUser");
		return ResponseEntity.ok(zfgcUser());
	}

	@PostMapping("/auth/login")
	@AllowAnonymous
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody AuthCredentials credentials) {
		log.info("Executing login");
		log.debug("Executing login for user={}", credentials.getUsername());
		LoginResponse result = authService.login(credentials);

		if (credentials.isUseTokens()) {
			return ResponseEntity.ok(result);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.buildAccessCookie(result.accessToken(), result.stayLoggedIn()).toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.buildRefreshCookie(result.refreshToken(), result.stayLoggedIn()).toString())
				.body(result.withoutTokens());
	}

	@PostMapping("/auth/refresh")
	@AllowAnonymous
	public ResponseEntity<TokenPair> refresh(@Valid @RequestBody(required = false) RefreshRequest request, HttpServletRequest httpRequest) {
		log.debug("Executing refresh");
		String bodyToken = request != null ? request.refreshToken() : null;
		boolean fromBody = bodyToken != null && !bodyToken.isBlank();
		String refreshToken = fromBody ? bodyToken : cookieService.readRefreshCookie(httpRequest).orElse(null);

		if (refreshToken == null || refreshToken.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		TokenPair pair = authService.refresh(refreshToken);

		if (fromBody) {
			return ResponseEntity.ok(pair);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.buildAccessCookie(pair.accessToken(), pair.stayLoggedIn()).toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.buildRefreshCookie(pair.refreshToken(), pair.stayLoggedIn()).toString())
				.body(pair.withoutTokens());
	}

	@PostMapping("/auth/logout")
	@AllowAnonymous
	public ResponseEntity<Void> logout(@Valid @RequestBody(required = false) RefreshRequest request, HttpServletRequest httpRequest) {
		log.debug("Executing logout");
		String bodyToken = request != null ? request.refreshToken() : null;
		String refreshToken = (bodyToken != null && !bodyToken.isBlank())
				? bodyToken
				: cookieService.readRefreshCookie(httpRequest).orElse(null);

		if (refreshToken != null && !refreshToken.isBlank()) {
			authService.logout(refreshToken);
		}

		return ResponseEntity.noContent()
				.header(HttpHeaders.SET_COOKIE, cookieService.clearAccessCookie().toString())
				.header(HttpHeaders.SET_COOKIE, cookieService.clearRefreshCookie().toString())
				.build();
	}

	@PostMapping("/register")
	@AllowAnonymous
	public ResponseEntity<User> registerNewUser(@Valid @RequestBody RegistrationRequest request) {
		log.info("Executing registerNewUser");
		log.debug("Executing registerNewUser with request={}", request);
		if (!registrationEnabled) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Registration is currently disabled");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(request));
	}

	@PostMapping("/account/delete")
	public ResponseEntity<AccountDeletionState> requestAccountDeletion(@Valid @RequestBody AccountDeletionRequest body) {
		log.info("Executing requestAccountDeletion");
		log.debug("Executing requestAccountDeletion with body={}", body);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(accountDeletionService.requestDeletion(zfgcUser(), body));
	}

	@GetMapping("/account/delete")
	public ResponseEntity<AccountDeletionState> accountDeletionState() {
     log.info("Executing accountDeletionState");
		return ResponseEntity.ok(accountDeletionService.currentDeletionState(zfgcUser()));
	}

	@PostMapping("/account/delete/preview")
	public ResponseEntity<AccountDeletionPreview> previewAccountDeletion() {
     log.info("Executing previewAccountDeletion");
		return ResponseEntity.ok(accountDeletionService.previewDeletion(zfgcUser()));
	}

	@PostMapping("/account/delete/resend")
	public ResponseEntity<AccountDeletionState> resendAccountDeletionConfirmation() {
     log.info("Executing resendAccountDeletionConfirmation");
		return ResponseEntity.ok(accountDeletionService.resendConfirmation(zfgcUser()));
	}

	@PostMapping("/account/delete/cancel")
	public ResponseEntity<AccountDeletionState> cancelAccountDeletion() {
     log.info("Executing cancelAccountDeletion");
		return ResponseEntity.ok(accountDeletionService.cancelPendingDeletion(zfgcUser()));
	}

	@PostMapping("/account/delete/confirm")
	@AllowAnonymous
	public ResponseEntity<AccountDeletionState> confirmAccountDeletion(
			@Valid @RequestBody(required = false) AccountDeletionConfirmation body, HttpServletRequest httpRequest) {
		AccountDeletionService.AccountDeletionConfirmOutcome outcome = accountDeletionService
				.confirmDeletion(body == null ? null : body.token(), httpRequest.getRemoteAddr());
		User caller = zfgcUser();
		if (outcome.subjectUserId() != null && outcome.subjectUserId().equals(caller.getUserId())) {
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, cookieService.clearAccessCookie().toString())
					.header(HttpHeaders.SET_COOKIE, cookieService.clearRefreshCookie().toString())
					.body(outcome.state());
		}
		return ResponseEntity.ok(outcome.state());
	}
}
