package com.zfgc.zfgbb.exception.handlers;

import java.util.ConcurrentModificationException;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RuntimeExceptionHandler {

	@ExceptionHandler(value = AuthenticationException.class)
	public ProblemDetail handleAuthenticationFailure(HttpServletRequest request, AuthenticationException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ProblemDetail handleDataIntegrityViolation(HttpServletRequest request, DataIntegrityViolationException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The request could not be completed due to invalid or conflicting data.");
	}

	@ExceptionHandler(value = ConcurrentModificationException.class)
	public ProblemDetail handleConcurrentModification(HttpServletRequest request, ConcurrentModificationException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "The record was modified concurrently. Please retry.");
	}

	@ExceptionHandler(value = ConcurrencyFailureException.class)
	public ProblemDetail handleConcurrencyFailure(HttpServletRequest request, ConcurrencyFailureException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "The record was modified concurrently. Please retry.");
	}

	@ExceptionHandler(value = ZfgcConflictException.class)
	public ProblemDetail handleConflict(HttpServletRequest request, ZfgcConflictException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
	}

	@ExceptionHandler(value = ZfgcInvalidRequestException.class)
	public ProblemDetail handleInvalidRequest(HttpServletRequest request, ZfgcInvalidRequestException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(value = InvalidBBCodeGrammarException.class)
	public ProblemDetail handleInvalidBBCodeGrammar(HttpServletRequest request,
			InvalidBBCodeGrammarException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(value = ZfgcNotFoundException.class)
	public ProblemDetail handleNotFound(HttpServletRequest request, ZfgcNotFoundException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Not found.");
	}

	@ExceptionHandler(value = ZfgcUnauthorizedException.class)
	public ProblemDetail handleUnauthorized(HttpServletRequest request, ZfgcUnauthorizedException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Forbidden.");
	}

}
