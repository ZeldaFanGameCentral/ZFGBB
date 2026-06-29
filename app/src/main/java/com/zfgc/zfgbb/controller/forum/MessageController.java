package com.zfgc.zfgbb.controller.forum;

import com.zfgc.zfgbb.config.security.AllowAnonymous;

import java.util.List;

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

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.services.forum.ForumModerationOrchestrator;
import java.util.Objects;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {
	
	@Autowired
	private ForumService forumService;

	@Autowired
	private ForumModerationOrchestrator forumModerationOrchestrator;
	
	@GetMapping("/template")
	@AllowAnonymous
	public ResponseEntity getMessageTemplate(@RequestParam("threadId") Integer threadId) {
		Message template = forumService.getMessageTemplate(null, threadId, null, super.zfgcUser());
		return ResponseEntity.ok(template);
	}
	
	@PostMapping("/{threadId}")
	@PreAuthorize("hasPermission(#threadId, 'THREAD', 'thread.reply')")
	public ResponseEntity addMessageToThread(@PathVariable Integer threadId, @RequestBody MessagePostRequest request) {
		return ResponseEntity.ok(forumService.saveMessage(threadId, request.body(), super.zfgcUser()));
	}

	public record MessagePostRequest(String body) {}

	@GetMapping("/{messageId}/allowed-actions")
	@AllowAnonymous
	public ResponseEntity<java.util.Set<String>> getAllowedActions(@PathVariable Integer messageId) {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate())
				.body(forumService.messageAllowedActions(messageId, super.zfgcUser()));
	}

	@DeleteMapping("/{messageId}")
	@PreAuthorize("hasPermission(#messageId, 'MESSAGE', 'message.delete')")
	public ResponseEntity<ForumService.MessageDeletionResponse> deleteMessage(@PathVariable Integer messageId) {
		return ResponseEntity.ok(forumModerationOrchestrator.deleteMessage(messageId, super.zfgcUser()));
	}

	@PutMapping("/{messageId}/restore")
	@PreAuthorize("hasPermission(#messageId, 'MESSAGE', 'message.restore')")
	public ResponseEntity<ForumService.RestoreResponse> restoreMessage(@PathVariable Integer messageId) {
		return ResponseEntity.ok(forumModerationOrchestrator.restoreMessage(messageId, super.zfgcUser()));
	}

	@GetMapping("/user/{userId}")
	@AllowAnonymous
	public List<Message> getMessagesByUser(@PathVariable Integer userId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		List<Integer> permissionIds = super.zfgcUser().getPermissions().stream()
				.map(Permission::getId)
				.filter(Objects::nonNull)
				.toList();
		return forumService.getMessagesByUserId(userId, page, pageSize, permissionIds);
	}
	
}
