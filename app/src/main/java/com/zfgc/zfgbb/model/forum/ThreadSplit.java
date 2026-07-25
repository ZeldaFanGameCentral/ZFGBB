package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ThreadSplit(
		Integer threadId,
		Integer boardId,
		List<Integer> messageIdsToMove,
		@NotBlank @Size(max = 255) String newThreadTitle
) {
	public ThreadSplit(Integer threadId, Integer boardId, List<Integer> messageIdsToMove, String newThreadTitle) {
		this.threadId = threadId;
		this.boardId = boardId;
		this.messageIdsToMove = messageIdsToMove == null ? new ArrayList<>() : messageIdsToMove;
		this.newThreadTitle = newThreadTitle;
	}
}