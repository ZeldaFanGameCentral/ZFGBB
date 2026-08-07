package com.zfgc.zfgbb.controller.content;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.authorization.AllowAnonymous;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.renderer.ContentFormatConverter;
import com.zfgc.zfgbb.content.renderer.ConvertedContent;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.services.cms.wiki.WikiService;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.services.contentstore.AuthoringContentFormat;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController extends BaseController {
	private static final int MAX_PREVIEW_LENGTH = 100_000;

	private final ContentService contentService;
	private final BBCodeGrammarLoader grammarLoader;
	private final ContentRenderingService contentRenderingService;
	private final ContentFormatConverter contentFormatConverter;
	private final ForumService forumService;
	private final WikiService wikiService;
	private final AuthoringContentFormat authoringContentFormat;

	public record PreviewRequest(String content, String scope, String slug, String contentFormat) {}

	public record ConvertRequest(String content, String scope, String fromContentFormat, String toContentFormat) {}

	private static ContentScope authoringScope(String requestedScope) {
		if (requestedScope == null || requestedScope.isBlank())
			return ContentScope.FORUM;
		ContentScope requested;
		try {
			requested = ContentScope.valueOf(requestedScope.toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException unknown) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"unknown scope " + requestedScope);
		}
		if (!requested.itsASurfaceContentIsReadOn())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"scope is not a surface: " + requestedScope);
		return requested;
	}

	@GetMapping("bbcodes")
	@AllowAnonymous
	public ResponseEntity<List<? extends Map<String, ?>>> getBBCodes(
			@RequestParam(name = "scope", required = false) String requestedScope) {
		return ResponseEntity.ok(grammarLoader.theConfigsHonouredOn(authoringScope(requestedScope)).stream()
				.map(config -> Map.of(
						"code", config.getCode(),
						"selfClosing", Boolean.TRUE.equals(config.getSelfClosingFlag())))
				.sorted(Comparator.comparing(entry -> (String) entry.get("code")))
				.toList());
	}

	@PostMapping("preview")
	public ResponseEntity<Map<String, String>> preview(@RequestBody PreviewRequest request) {
		if (request == null || request.content() == null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "content is required");
		}
		if (request.content().length() > MAX_PREVIEW_LENGTH) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "content is too long");
		}
		ContentScope scope = authoringScope(request.scope());
		ContentFormat contentFormat = authoringContentFormat.forNewContent(request.contentFormat());
		if (scope == ContentScope.FORUM) {
			return ResponseEntity.ok(Map.of("contentParsed",
					previewForumContent(request.content(), contentFormat)));
		}
		return ResponseEntity.ok(Map.of("contentParsed",
				wikiService.previewContent(request.slug(), request.content(), contentFormat, scope)));
	}

	@PostMapping("convert")
	public ResponseEntity<ConvertedContent> convert(@RequestBody ConvertRequest request) {
		if (request == null || request.content() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
		}
		if (request.content().length() > MAX_PREVIEW_LENGTH) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is too long");
		}
		ContentFormat fromContentFormat = authoringContentFormat.required("fromContentFormat",
				request.fromContentFormat());
		ContentFormat toContentFormat = authoringContentFormat.required("toContentFormat", request.toContentFormat());
		return ResponseEntity.ok(contentFormatConverter.convert(request.content(), fromContentFormat,
				toContentFormat, authoringScope(request.scope())));
	}

	private String previewForumContent(String content, ContentFormat contentFormat) {
		Set<Integer> visibleBoardIds = forumService.visibleBoardIds(zfgcUser().permissionIds());
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		try (ContentRenderingService.QuoteScope quoteScope = contentRenderingService.openQuoteScope(
				List.of(new ContentRenderingService.QuotingPost(content, now)), visibleBoardIds)) {
			return contentRenderingService.renderWithTemplates(content, contentFormat, ContentScope.FORUM,
					Map.of(), now);
		}
	}

	@GetMapping("archive/{resourceId}")
	@AllowAnonymous
	public ResponseEntity<List<ContentService.ArchiveEntry>> getArchiveEntries(@PathVariable("resourceId") Integer resourceId) {
		contentService.authorizeAccess(resourceId, zfgcUser());
		return ResponseEntity.ok(contentService.getArchiveEntries(resourceId));
	}

	@GetMapping("{resourceId}")
	@AllowAnonymous
	public ResponseEntity<Resource> getContent(@PathVariable("resourceId") Integer resourceId)
			throws MalformedURLException {
		Optional<ContentResourceDbo> contentResource;
		Resource resource;
		try {
			contentService.authorizeAccess(resourceId, zfgcUser());
			contentResource = contentService.getContentResourceDbo(resourceId);
			resource = contentService.getImageResource(resourceId);
		} catch (ZfgcNotFoundException e) {
			return missingContent();
		}
		if (!resource.exists()) {
			return missingContent();
		}
		String displayName = contentResource.isPresent()
				? contentResource.get().getFilename()
				: resource.getFilename();
		MediaType mediaType = contentService.getMimeType(displayName).orElse(MediaType.APPLICATION_OCTET_STREAM);
		boolean inline = mediaType.getType().equals("image") && !mediaType.getSubtype().contains("svg");
		ContentDisposition disposition = ContentDisposition.builder(inline ? "inline" : "attachment")
				.filename(displayName == null ? "download" : displayName, StandardCharsets.UTF_8)
				.build();
		return ResponseEntity.ok()
				.contentType(mediaType)
				.header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
				.body(resource);
	}

	private ResponseEntity<Resource> missingContent() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.parseMediaType("image/webp"))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.inline().filename("i-am-error.webp").build().toString())
				.body(new ClassPathResource("content/i-am-error.webp"));
	}

}