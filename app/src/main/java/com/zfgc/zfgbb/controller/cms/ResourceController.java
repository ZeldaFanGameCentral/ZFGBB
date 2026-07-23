package com.zfgc.zfgbb.controller.cms;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Resource;
import com.zfgc.zfgbb.model.cms.ResourceShowcase;
import com.zfgc.zfgbb.services.cms.ResourceService;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/resources")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceService resourceService;

	@GetMapping("/facets")
	@AllowAnonymous
	public ResponseEntity<Map<String, List<Map<String, Object>>>> getFacets() {
     log.info("Executing getFacets");
		return ResponseEntity.ok(Map.of("types", toFacet(resourceService.getResourceTypes())));
	}

	private static List<Map<String, Object>> toFacet(List<Map.Entry<String, Long>> values) {
		return values.stream()
				.map(entry -> Map.<String, Object>of("value", entry.getKey(), "count", entry.getValue()))
				.toList();
	}

	@GetMapping
	@AllowAnonymous
	public ResponseEntity<PagedResult<Resource>> getResources(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "hasDownload", required = false) Boolean hasDownload,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return ResponseEntity.ok(resourceService.getResources(search, type, author, hasDownload, sort, page, pageSize));
	}

	@GetMapping("/showcase")
	@AllowAnonymous
	public ResponseEntity<ResourceShowcase> getShowcase() {
     log.info("Executing getShowcase");
		return ResponseEntity.ok(resourceService.getResourceShowcase());
	}

	@GetMapping("/{slug}")
	@AllowAnonymous
	public ResponseEntity<Resource> getResource(@PathVariable("slug") String slug) {
     log.info("Executing getResource");
		return ResponseEntity.ok(resourceService.getResource(slug));
	}

	@PostMapping("/{slug}/discussion")
	public ResponseEntity<Resource> startDiscussion(@PathVariable("slug") String slug) {
     log.info("Executing startDiscussion");
		return ResponseEntity.ok(resourceService.startResourceDiscussion(slug, super.zfgcUser()));
	}
}
