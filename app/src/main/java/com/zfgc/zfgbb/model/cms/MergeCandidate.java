package com.zfgc.zfgbb.model.cms;

public record MergeCandidate(ContentMergeSide sourceType, Integer sourceId, String sourceTitle, String sourceSlug,
		ContentMergeSide targetType, Integer targetId, String targetTitle, String targetSlug,
		int confidence, String reason) {
}
