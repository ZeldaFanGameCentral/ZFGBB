package com.zfgc.zfgbb.controller.forum;

import com.zfgc.zfgbb.config.security.AllowAnonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.CacheControl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.CreateThreadRequest;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.services.forum.ForumModerationOrchestrator;

@RestController
@RequestMapping("/thread")
public class ThreadController extends BaseController {
	
	@Autowired
	private ForumService forumService;

	@Autowired
	private ForumModerationOrchestrator forumModerationOrchestrator;
	
	@GetMapping("/template")
	@AllowAnonymous
	public ResponseEntity getThreadTemplate(@RequestParam("boardId") Integer boardId) {
		Thread template = forumService.getThreadTemplate(boardId, super.zfgcUser());
		return ResponseEntity.ok(template);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_WRITE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity saveThread(@RequestParam("boardId") Integer boardId,
			@RequestBody CreateThreadRequest request) {
		Thread saved = forumService.createThread(boardId, request, super.zfgcUser());
		return ResponseEntity.ok(saved);
	}
	
	@GetMapping("/{threadId}")
	@AllowAnonymous
	public ResponseEntity getThread(@PathVariable("threadId") Integer threadId, @RequestParam("pageSize") Integer pageSize, @RequestParam("page") Integer page) {
		return ResponseEntity.ok(forumService.getThread(threadId, page, pageSize, super.zfgcUser()));
	}

	@GetMapping("/{threadId}/allowed-actions")
	@AllowAnonymous
	public ResponseEntity<java.util.Set<String>> getAllowedActions(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate())
				.body(forumService.threadAllowedActions(threadId, super.zfgcUser()));
	}
	
	@DeleteMapping("/{threadId}")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<ForumService.ThreadDeletionResponse> deleteThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.deleteThread(threadId, super.zfgcUser()));
	}

	@PutMapping("/{threadId}/restore")
	@PreAuthorize("hasPermission(#threadId, 'THREAD', 'thread.restore')")
	public ResponseEntity<ForumService.RestoreResponse> restoreThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.restoreThread(threadId, super.zfgcUser()));
	}

	@PutMapping("/{threadId}/move/{boardId}")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity moveThread(@PathVariable("threadId") Integer threadId, @PathVariable("boardId") Integer boardId) {
		return ResponseEntity.ok(forumModerationOrchestrator.moveThread(threadId, boardId, super.zfgcUser()));
	}
	
	@PutMapping("/{threadId}/lockToggle")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity lockToggleThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.toggleLocked(threadId, super.zfgcUser()));
	}
	
	@PutMapping("/{threadId}/stickyToggle")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity stickyToggleThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.toggleSticky(threadId, super.zfgcUser()));
	}
	
	@GetMapping("/{threadId}/split")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity getThreadSplitTemplate(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.getSplitTemplate(threadId, super.zfgcUser()));
	}
	
	@PostMapping("/{threadId}/split")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity splitThread(@PathVariable("threadId") Integer threadId, @RequestBody ThreadSplit threadSplit) {
		return ResponseEntity.ok(forumModerationOrchestrator.splitThread(threadSplit, super.zfgcUser()));
	}
}
