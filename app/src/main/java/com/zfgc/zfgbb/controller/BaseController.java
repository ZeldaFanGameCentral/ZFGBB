package com.zfgc.zfgbb.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.zfgc.zfgbb.model.User;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {
	@Autowired
	HttpServletRequest request;
	
	protected User zfgcUser(){
		Principal userPrincipal = request.getUserPrincipal();

		if(userPrincipal == null) {
			return User.guest();
		}

		return (User) ((Authentication) userPrincipal).getPrincipal();
	}
}