package com.zfgc.zfgbb.model.cms;

public record MergeApplyRequest(ContentMergeSide sourceType, Integer sourceId, ContentMergeSide targetType,
		Integer targetId) {
}
