package com.zfgc.zfgbb.content.renderer;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

import com.zfgc.zfgbb.content.ContentFormat;

public interface ContentTagResolver {

	record SourceRevision(String body, ContentFormat contentFormat) {}

	record Resolved(String authorDisplayName, Integer authorUserId, OffsetDateTime createdTs,
			Integer threadId, Integer page, Integer sourceBoardId, boolean permitted,
			NavigableMap<OffsetDateTime, SourceRevision> revisionsByCreatedTs) {}

	String resolverCode();

	Map<Integer, Resolved> resolve(Set<Integer> sourceIds, Set<Integer> visibleBoardIds);
}
