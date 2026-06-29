package com.zfgc.zfgbb.exception.handlers;

import java.util.ConcurrentModificationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RuntimeExceptionHandler {

	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationFailure(HttpServletRequest request, AuthenticationException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolation(HttpServletRequest request, DataIntegrityViolationException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request could not be completed due to invalid or conflicting data.");
	}

	@ExceptionHandler(value = ConcurrentModificationException.class)
	public ResponseEntity<String> handleConcurrentModification(HttpServletRequest request, ConcurrentModificationException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body("The record was modified concurrently. Please retry.");
	}

}
