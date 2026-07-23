package com.zfgc.zfgbb.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zfgc.zfgbb.exception.ZfgcNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ZfgcNotFoundExceptionHandler {

	@ExceptionHandler(value = ZfgcNotFoundException.class)
	public ProblemDetail handle(HttpServletRequest req, ZfgcNotFoundException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Not found.");
	}

}
