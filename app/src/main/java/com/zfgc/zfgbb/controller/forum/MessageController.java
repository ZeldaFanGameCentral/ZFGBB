package com.zfgc.zfgbb.controller.forum;

import com.zfgc.zfgbb.authorization.AllowAnonymous;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.CacheControl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
import com.zfgc.zfgbb.model.forum.MessageDeletionResponse;
import com.zfgc.zfgbb.model.forum.MessageResponse;
import com.zfgc.zfgbb.model.forum.RestoreResponse;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.services.forum.ForumModerationOrchestrator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController extends BaseController {
	private final ForumService forumService;

	private final ForumModerationOrchestrator forumModerationOrchestrator;
	
	@GetMapping("/template")
	@AllowAnonymous
	public ResponseEntity<MessageResponse> getMessageTemplate(@RequestParam("threadId") Integer threadId) {
		Message template = forumService.getMessageTemplate(threadId, super.zfgcUser());
		return ResponseEntity.ok(new MessageResponse(template));
	}
	
	@PostMapping("/{threadId}")
	@PreAuthorize("hasPermission(#threadId, 'THREAD', 'thread.reply')")
	public ResponseEntity<MessageResponse> addMessageToThread(@PathVariable Integer threadId, @Valid @RequestBody MessagePostRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(forumService.saveMessage(threadId,
				request.body(), request.contentFormat(), super.zfgcUser())));
	}

	public record MessagePostRequest(@NotBlank @Size(max=10000) String body, String contentFormat) {}

	@PutMapping("/{messageId}")
	@PreAuthorize("hasPermission(#messageId, 'MESSAGE', 'message.edit')")
	public ResponseEntity<MessageResponse> editMessage(@PathVariable Integer messageId,
			@Valid @RequestBody MessagePostRequest request) {
		return ResponseEntity.ok(new MessageResponse(forumService.editMessage(messageId, request.body(),
				request.contentFormat(), super.zfgcUser())));
	}

	@GetMapping("/{messageId}/allowed-actions")
	@AllowAnonymous
	public ResponseEntity<Set<String>> getAllowedActions(@PathVariable Integer messageId) {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate())
				.body(forumService.messageAllowedActions(messageId, super.zfgcUser()));
	}

	@DeleteMapping("/{messageId}")
	@PreAuthorize("hasPermission(#messageId, 'MESSAGE', 'message.delete')")
	public ResponseEntity<MessageDeletionResponse> deleteMessage(@PathVariable Integer messageId) {
		return ResponseEntity.ok(forumModerationOrchestrator.deleteMessage(messageId, super.zfgcUser()));
	}

	@PutMapping("/{messageId}/restore")
	@PreAuthorize("hasPermission(#messageId, 'MESSAGE', 'message.restore')")
	public ResponseEntity<RestoreResponse> restoreMessage(@PathVariable Integer messageId) {
		return ResponseEntity.ok(forumModerationOrchestrator.restoreMessage(messageId, super.zfgcUser()));
	}

	@GetMapping("/user/{userId}")
	@AllowAnonymous
	public List<MessageResponse> getMessagesByUser(@PathVariable Integer userId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return forumService.getMessagesByUserId(userId, page, pageSize, super.zfgcUser().permissionIds())
				.stream().map(MessageResponse::new).toList();
	}
	
}
