package com.zfgc.zfgbb.model.forum;

public record MessageDeletionResponse(String outcome, boolean originThreadRecycled, boolean originThreadDeleted,
		Integer threadId, Integer boardId, Integer recycleThreadId, Integer pageCount) {}
