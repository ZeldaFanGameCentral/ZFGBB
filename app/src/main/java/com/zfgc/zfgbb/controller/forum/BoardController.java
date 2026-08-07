package com.zfgc.zfgbb.controller.forum;

import com.zfgc.zfgbb.authorization.AllowAnonymous;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.controller.BaseController;
import com.zfgc.zfgbb.services.forum.ForumService;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController extends BaseController {
	private final ForumService forumService;
	
	@GetMapping("/{boardId}")
	@AllowAnonymous
	public ResponseEntity getBoard(@PathVariable("boardId") Integer boardId, @RequestParam(name="page",required=false) Integer page) {
		return ResponseEntity.ok(forumService.getBoard(boardId, page, super.zfgcUser()));
	}
	
	@GetMapping("/forum")
	@AllowAnonymous
	public ResponseEntity getForum() {
		return ResponseEntity.ok(forumService.getForum(super.zfgcUser()));
	}

	@GetMapping("/recent-activity")
	@AllowAnonymous
	public ResponseEntity getRecentActivity(@RequestParam(name = "boardId", required = false) String boardId,
			@RequestParam(name = "limit", required = false) Integer limit) {
		return ResponseEntity.ok(forumService.getRecentActivity(boardId, limit, super.zfgcUser()));
	}
}
