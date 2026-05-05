package com.zfgc.zfgbb.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zfgc.zfgbb.exception.ZfgcNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ZfgcNotFoundExceptionHandler {

	@ExceptionHandler(value = ZfgcNotFoundException.class)
	public ResponseEntity<String> handle(HttpServletRequest req, ZfgcNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found.");
	}

}
