package com.zfgc.zfgbb.controller;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.BBCodeService;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.core.ContentService;
import com.zfgc.zfgbb.services.forum.ForumService;

@Slf4j
@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController extends BaseController {

	private static final int MAX_PREVIEW_LENGTH = 100_000;

	private final ContentService contentService;
	private final BBCodeService bbCodeService;
	private final ContentRenderer contentRenderer;
	private final ForumService forumService;

	public record PreviewRequest(String content, String scope) {}

	@GetMapping("bbcodes")
	@AllowAnonymous
	public ResponseEntity<List<? extends Map<String, ?>>> getBbcodes() {
     log.info("Executing getBbcodes");
		return ResponseEntity.ok(bbCodeService.validBbCodes.values().stream()
				.map(config -> Map.of(
						"code", config.getCode(),
						"selfClosing", Boolean.TRUE.equals(config.getSelfClosingFlag())))
				.sorted(Comparator.comparing(entry -> (String) entry.get("code")))
				.toList());
	}

	@PostMapping("preview")
	public ResponseEntity<Map<String, String>> preview(@RequestBody PreviewRequest request) {
		log.debug("Executing preview with request={}", request);

		if (request == null || request.content() == null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "content is required");
		}
		if (request.content().length() > MAX_PREVIEW_LENGTH) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "content is too long");
		}
		ContentScope scope = "WIKI".equalsIgnoreCase(request.scope())
				? ContentScope.WIKI
				: ContentScope.FORUM;
		if (scope == ContentScope.FORUM) {
			return ResponseEntity.ok(Map.of("contentParsed", previewForumContent(request.content())));
		}
		return ResponseEntity.ok(Map.of("contentParsed",
				contentRenderer.renderWithTemplates(request.content(),
						ContentFormat.BBCODE, scope, Map.of())));
	}

	private String previewForumContent(String content) {
		User user = zfgcUser();
		List<Integer> permissionIds = user.getPermissions().stream().map(Permission::getPermissionId).toList();
		Set<Integer> visibleBoardIds = forumService.visibleBoardIds(permissionIds);
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		bbCodeService.openQuoteScope(List.of(new BBCodeService.QuotingPost(content, now)), visibleBoardIds);
		try {
			return contentRenderer.renderWithTemplates(content, ContentFormat.BBCODE, ContentScope.FORUM,
					Map.of(), now);
		} finally {
			bbCodeService.closeQuoteScope();
		}
	}

	@GetMapping("archive/{resourceId}")
	@AllowAnonymous
	public ResponseEntity<List<ContentService.ArchiveEntry>> getArchiveEntries(@PathVariable("resourceId") Integer resourceId) {
     log.info("Executing getArchiveEntries");
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
		boolean inline = "image".equals(mediaType.getType()) && !mediaType.getSubtype().contains("svg");
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