package com.zfgc.zfgbb.controller.forum;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.CacheControl;

import jakarta.validation.Valid;
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

import com.zfgc.zfgbb.model.forum.CreateThreadRequest;
import com.zfgc.zfgbb.model.forum.RestoreResponse;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadDeletionResponse;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.services.forum.ForumModerationOrchestrator;
import lombok.RequiredArgsConstructor;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/thread")
@RequiredArgsConstructor
public class ThreadController extends BaseController {

	private final ForumService forumService;

	private final ForumModerationOrchestrator forumModerationOrchestrator;
	
	@GetMapping("/template")
	@AllowAnonymous
	public ResponseEntity<Thread> getThreadTemplate(@RequestParam("boardId") Integer boardId) {
		Thread template = forumService.getThreadTemplate(boardId, super.zfgcUser());
		return ResponseEntity.ok(template);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_WRITE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<Thread> saveThread(@RequestParam("boardId") Integer boardId,
			@Valid @RequestBody CreateThreadRequest request) {
		Thread saved = forumService.createThread(boardId, request, super.zfgcUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/{threadId}")
	@AllowAnonymous
	public ResponseEntity<Thread> getThread(@PathVariable("threadId") Integer threadId, @RequestParam("pageSize") Integer pageSize, @RequestParam("page") Integer page) {
		return ResponseEntity.ok(forumService.getThread(threadId, page, pageSize, super.zfgcUser()));
	}

	@GetMapping("/{threadId}/allowed-actions")
	@AllowAnonymous
	public ResponseEntity<Set<String>> getAllowedActions(@PathVariable("threadId") Integer threadId) {
     log.info("Executing getAllowedActions");
		return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate())
				.body(forumService.threadAllowedActions(threadId, super.zfgcUser()));
	}
	
	@DeleteMapping("/{threadId}")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<ThreadDeletionResponse> deleteThread(@PathVariable("threadId") Integer threadId) {
     log.info("Executing deleteThread");
		return ResponseEntity.ok(forumModerationOrchestrator.deleteThread(threadId, super.zfgcUser()));
	}

	@PutMapping("/{threadId}/restore")
	@PreAuthorize("hasPermission(#threadId, 'THREAD', 'thread.restore')")
	public ResponseEntity<RestoreResponse> restoreThread(@PathVariable("threadId") Integer threadId) {
     log.info("Executing restoreThread");
		return ResponseEntity.ok(forumModerationOrchestrator.restoreThread(threadId, super.zfgcUser()));
	}

	@PutMapping("/{threadId}/move/{boardId}")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<Thread> moveThread(@PathVariable("threadId") Integer threadId, @PathVariable("boardId") Integer boardId) {
		return ResponseEntity.ok(forumModerationOrchestrator.moveThread(threadId, boardId, super.zfgcUser()));
	}
	
	@PutMapping("/{threadId}/lockToggle")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<Thread> lockToggleThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.toggleLocked(threadId, super.zfgcUser()));
	}
	
	@PutMapping("/{threadId}/stickyToggle")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<Thread> stickyToggleThread(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.toggleSticky(threadId, super.zfgcUser()));
	}
	
	@GetMapping("/{threadId}/split")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<ThreadSplit> getThreadSplitTemplate(@PathVariable("threadId") Integer threadId) {
		return ResponseEntity.ok(forumModerationOrchestrator.getSplitTemplate(threadId, super.zfgcUser()));
	}
	
	@PostMapping("/{threadId}/split")
	@PreAuthorize("hasRole('ROLE_ZFGC_FORUM_MODERATE') and !hasRole('ROLE_ZFGC_READ_ONLY')")
	public ResponseEntity<Thread> splitThread(@PathVariable("threadId") Integer threadId, @Valid @RequestBody ThreadSplit threadSplit) {
		return ResponseEntity.ok(forumModerationOrchestrator.splitThread(threadSplit, super.zfgcUser()));
	}
}
