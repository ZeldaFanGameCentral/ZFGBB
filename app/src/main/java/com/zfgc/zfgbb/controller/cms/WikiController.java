package com.zfgc.zfgbb.controller.cms;

import com.zfgc.zfgbb.config.security.AllowAnonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.WikiConfig;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiPageRef;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.services.cms.WikiConfigService;
import com.zfgc.zfgbb.services.cms.WikiModerationService;
import com.zfgc.zfgbb.services.cms.WikiService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wiki")
public class WikiController extends BaseController {

	private static final String MODERATOR_ACCESS = "hasAnyRole('ROLE_ZFGC_WIKI_MODERATOR','ROLE_ZFGC_SITE_ADMIN')";

	@Autowired
	private WikiService wikiService;

	@Autowired
	private WikiModerationService wikiModerationService;

	@Autowired
	private WikiConfigService wikiConfigService;

	@GetMapping("/meta/config")
	@AllowAnonymous
	public ResponseEntity<WikiConfig> getConfig() {
		return ResponseEntity.ok(wikiConfigService.getConfig());
	}

	@GetMapping("/meta/pages")
	@AllowAnonymous
	public ResponseEntity<PagedResult<WikiPageRef>> getPageIndex(@RequestParam(name = "namespace", required = false) String namespace,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return ResponseEntity.ok(wikiService.getWikiPageIndex(namespace, search, page, pageSize));
	}

	@GetMapping("/meta/statistics")
	@AllowAnonymous
	public ResponseEntity<Map<String, Object>> getStatistics() {
		return ResponseEntity.ok(wikiService.getWikiStatistics());
	}

	@GetMapping("/meta/random")
	@AllowAnonymous
	public ResponseEntity<WikiPageRef> getRandomPage() {
		return ResponseEntity.ok(wikiService.getRandomWikiPage());
	}

	@GetMapping("/meta/categories")
	@AllowAnonymous
	public ResponseEntity<List<? extends Map<String, ?>>> getCategories() {
		return ResponseEntity.ok(wikiService.getWikiCategories().stream()
				.map(entry -> Map.of("name", entry.getKey(), "count", entry.getValue()))
				.toList());
	}

	@GetMapping("/meta/category")
	@AllowAnonymous
	public ResponseEntity<Map<String, Object>> getCategory(@RequestParam(name = "name", required = false) String name) {
		return ResponseEntity.ok(wikiService.getCategoryPages(name));
	}

	@GetMapping("/meta/recentchanges")
	@AllowAnonymous
	public ResponseEntity<List<WikiRevisionRef>> getRecentChanges() {
		return ResponseEntity.ok(wikiService.getWikiRecentChanges());
	}

	@GetMapping("/meta/history")
	@AllowAnonymous
	public ResponseEntity<List<WikiRevisionRef>> getHistory(@RequestParam(name = "slug") String slug) {
		return ResponseEntity.ok(wikiService.getWikiHistory(slug, isWikiModerator()));
	}

	private boolean isWikiModerator() {
		var user = super.zfgcUser();
		return user != null
				&& (user.hasPermission("ZFGC_WIKI_MODERATOR") || user.hasPermission("ZFGC_SITE_ADMIN"));
	}

	@PostMapping("/meta/revisions")
	public ResponseEntity<WikiRevisionRef> submitRevision(@RequestBody WikiRevisionRequest request) {
		return ResponseEntity.ok(wikiModerationService.submit(
				request.slug(), request.content(), request.summary(), super.zfgcUser()));
	}

	@GetMapping("/meta/moderation/pending")
	@PreAuthorize(MODERATOR_ACCESS)
	public ResponseEntity<List<WikiRevisionRef>> getPendingRevisions() {
		return ResponseEntity.ok(wikiModerationService.getPendingRevisions());
	}

	@GetMapping("/meta/moderation/{revisionId}/preview")
	@PreAuthorize(MODERATOR_ACCESS)
	public ResponseEntity<Map<String, Object>> previewRevision(@PathVariable("revisionId") Integer revisionId) {
		return ResponseEntity.ok(wikiModerationService.preview(revisionId));
	}

	@PostMapping("/meta/moderation/{revisionId}/approve")
	@PreAuthorize(MODERATOR_ACCESS)
	public ResponseEntity<Void> approveRevision(@PathVariable("revisionId") Integer revisionId) {
		wikiModerationService.approve(revisionId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/meta/moderation/{revisionId}/reject")
	@PreAuthorize(MODERATOR_ACCESS)
	public ResponseEntity<Void> rejectRevision(@PathVariable("revisionId") Integer revisionId) {
		wikiModerationService.reject(revisionId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{*slug}")
	@AllowAnonymous
	public ResponseEntity<WikiPage> getPage(@PathVariable("slug") String slug,
			@RequestParam(name = "rev", required = false) Integer revision,
			@RequestParam(name = "source", required = false, defaultValue = "false") boolean source) {
		String path = slug.startsWith("/") ? slug.substring(1) : slug;
		return ResponseEntity.ok(wikiService.getWikiPage(path, revision, source));
	}

	public record WikiRevisionRequest(String slug, String content, String summary) {
	}
}
