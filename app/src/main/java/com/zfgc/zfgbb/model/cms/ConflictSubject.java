package com.zfgc.zfgbb.model.cms;

public record ConflictSubject(Integer entityId, String title, String slug, String authorName, Integer threadId,
		Integer wikiPageId) {
}
