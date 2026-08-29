package com.zfgc.zfgbb.model.forum;

public record RestoreResponse(String mode, Integer threadId, Integer boardId, Integer postInThread,
		Integer postInThreadPage) {

	public static RestoreResponse mergedIntoOrigin(String mode, Integer threadId, Integer boardId,
			Integer postInThread) {
		int page = postInThread == null ? 1
				: Math.max(1, (postInThread - 1) / ForumPagination.MESSAGES_PER_THREAD_PAGE + 1);
		return new RestoreResponse(mode, threadId, boardId, postInThread, page);
	}

	public static RestoreResponse threadRestored(String mode, Integer threadId, Integer boardId) {
		return new RestoreResponse(mode, threadId, boardId, null, null);
	}
}
