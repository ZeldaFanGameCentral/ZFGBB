package com.zfgc.zfgbb.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zfgc.zfgbb.exception.ZfgcConflictException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ZfgcConflictExceptionHandler {

	@ExceptionHandler(value = ZfgcConflictException.class)
	public ResponseEntity<String> handle(HttpServletRequest req, ZfgcConflictException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

}
