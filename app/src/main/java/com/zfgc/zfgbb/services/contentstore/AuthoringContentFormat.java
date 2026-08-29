package com.zfgc.zfgbb.services.contentstore;

import com.zfgc.zfgbb.services.system.SystemConfigService;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.ContentFormat;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthoringContentFormat {

	public static final int MAX_AUTHORED_CONTENT_LENGTH = 100_000;

	private final SystemConfigService systemConfigService;

	public Optional<ContentFormat> requested(String requestedContentFormat) {
		if (requestedContentFormat == null || requestedContentFormat.isBlank())
			return Optional.empty();
		return Optional.of(ContentFormat.parse(requestedContentFormat)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"contentFormat must be one of " + ContentFormat.authorableCodes()
								+ " but was '" + requestedContentFormat + "'")));
	}

	public ContentFormat required(String fieldName, String requestedContentFormat) {
		return requested(requestedContentFormat)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						fieldName + " must be one of " + ContentFormat.authorableCodes()
								+ " but was '" + requestedContentFormat + "'"));
	}

	public ContentFormat siteDefault() {
		return systemConfigService.authoringDefaultContentFormat();
	}

	public ContentFormat forNewContent(String requestedContentFormat) {
		return requested(requestedContentFormat).orElseGet(this::siteDefault);
	}

	public ContentFormat forSupersedingContent(String requestedContentFormat,
			Supplier<Optional<ContentFormat>> formatOfContentBeingSuperseded) {
		return requested(requestedContentFormat)
				.or(formatOfContentBeingSuperseded)
				.orElseGet(this::siteDefault);
	}
}
