package com.zfgc.zfgbb.model.cms;

import java.time.OffsetDateTime;
import java.util.List;

public record ConflictView(Integer id, String entityType, Integer entityId, String entityLabel, String fieldName,
		List<ConflictCandidate> candidates, String status, String resolvedSourceType, String resolvedValue,
		OffsetDateTime detectedTs, OffsetDateTime resolvedTs) {
}
