package com.zfgc.zfgbb.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

@Component
public class AllowAnonymousRequestMatcher implements RequestMatcher {

	private final RequestMappingHandlerMapping handlerMapping;

	public AllowAnonymousRequestMatcher(
			@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		HandlerMethod handler = resolveHandlerMethod(request);
		return handler != null
				&& (handler.hasMethodAnnotation(AllowAnonymous.class)
						|| handler.getBeanType().isAnnotationPresent(AllowAnonymous.class));
	}

	private HandlerMethod resolveHandlerMethod(HttpServletRequest request) {
		boolean parsedHere = false;
		try {
			if (!ServletRequestPathUtils.hasParsedRequestPath(request)) {
				ServletRequestPathUtils.parseAndCache(request);
				parsedHere = true;
			}
			HandlerExecutionChain chain = handlerMapping.getHandler(request);
			if (chain != null && chain.getHandler() instanceof HandlerMethod handlerMethod) {
				return handlerMethod;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (parsedHere) {
				ServletRequestPathUtils.clearParsedRequestPath(request);
			}
		}
		return null;
	}
}
