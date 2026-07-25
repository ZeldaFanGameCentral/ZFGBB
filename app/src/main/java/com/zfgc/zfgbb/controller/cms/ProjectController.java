package com.zfgc.zfgbb.controller.cms;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.ProjectShowcase;
import com.zfgc.zfgbb.services.cms.catalog.ProjectService;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController extends BaseController {

	private final ProjectService projectService;

	@GetMapping
	@AllowAnonymous
	public ResponseEntity<PagedResult<Project>> getProjects(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "language", required = false) String language,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "hasDownload", required = false) Boolean hasDownload,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return ResponseEntity.ok(projectService.getProjects(search, status, language, author, hasDownload, sort, page, pageSize));
	}

	@GetMapping("/facets")
	@AllowAnonymous
	public ResponseEntity<Map<String, List<Map<String, Object>>>> getFacets() {
     log.info("Executing getFacets");
		Map<String, List<Map.Entry<String, Long>>> facets = projectService.getFacets();
		return ResponseEntity.ok(Map.of(
				"languages", toFacet(facets.get("languages")),
				"statuses", toFacet(facets.get("statuses"))));
	}

	@GetMapping("/showcase")
	@AllowAnonymous
	public ResponseEntity<ProjectShowcase> getShowcase() {
     log.info("Executing getShowcase");
		return ResponseEntity.ok(projectService.getProjectShowcase());
	}

	@GetMapping("/card")
	@AllowAnonymous
	public ResponseEntity<Map<String, Object>> getProjectCard(@RequestParam(name = "slug", required = false) String slug) {
     log.info("Executing getProjectCard");
		return ResponseEntity.ok(projectService.getProjectCard(slug));
	}

	@GetMapping("/news")
	@AllowAnonymous
	public ResponseEntity<Map<String, Object>> getProjectNews(@RequestParam(name = "slug", required = false) String slug,
			@RequestParam(name = "limit", required = false) Integer limit) {
		return ResponseEntity.ok(projectService.getProjectNewsFeed(slug, limit));
	}

	@GetMapping("/{slug}")
	@AllowAnonymous
	public ResponseEntity<Project> getProject(@PathVariable("slug") String slug) {
     log.info("Executing getProject");
		return ResponseEntity.ok(projectService.getProject(slug));
	}

	@PostMapping("/{slug}/discussion")
	public ResponseEntity<Project> startDiscussion(@PathVariable("slug") String slug) {
     log.info("Executing startDiscussion");
		return ResponseEntity.ok(projectService.startProjectDiscussion(slug, super.zfgcUser()));
	}
}
