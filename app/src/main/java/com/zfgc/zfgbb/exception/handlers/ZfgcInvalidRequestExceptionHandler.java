package com.zfgc.zfgbb.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ZfgcInvalidRequestExceptionHandler {

	@ExceptionHandler(value = ZfgcInvalidRequestException.class)
	public ResponseEntity<String> handle(HttpServletRequest req, ZfgcInvalidRequestException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

}
