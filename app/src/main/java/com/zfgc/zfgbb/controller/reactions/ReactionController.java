package com.zfgc.zfgbb.controller.reactions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.config.security.AllowAnonymous;
import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.model.reactions.ContentReactionSummary;
import com.zfgc.zfgbb.model.reactions.ReactionRequest;
import com.zfgc.zfgbb.model.reactions.ReactionType;
import com.zfgc.zfgbb.services.reactions.ReactionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
public class ReactionController extends BaseController {
	private final ReactionService reactionService;

	@GetMapping("/types")
	@AllowAnonymous
	public ResponseEntity<List<ReactionType>> getReactionTypes() {
		return ResponseEntity.ok(reactionService.getReactionTypes());
	}

	@GetMapping
	@AllowAnonymous
	public ResponseEntity<ContentReactionSummary> getSummary(
			@RequestParam("reactableType") String reactableType,
			@RequestParam("reactableId") Integer reactableId) {
		return ResponseEntity.ok(reactionService.getSummary(reactableType, reactableId, zfgcUser()));
	}

	@GetMapping("/summaries")
	@AllowAnonymous
	public ResponseEntity<List<ContentReactionSummary>> getSummaries(
			@RequestParam("reactableType") String reactableType,
			@RequestParam("reactableIds") List<Integer> reactableIds) {
		return ResponseEntity.ok(reactionService.getSummaries(reactableType, reactableIds, zfgcUser()));
	}

	@PostMapping
	public ResponseEntity<ContentReactionSummary> toggle(@RequestBody ReactionRequest request) {
		return ResponseEntity.ok(reactionService.toggle(request, zfgcUser()));
	}

	@DeleteMapping
	public ResponseEntity<ContentReactionSummary> remove(
			@RequestParam("reactableType") String reactableType,
			@RequestParam("reactableId") Integer reactableId) {
		return ResponseEntity.ok(reactionService.remove(reactableType, reactableId, zfgcUser()));
	}
}
