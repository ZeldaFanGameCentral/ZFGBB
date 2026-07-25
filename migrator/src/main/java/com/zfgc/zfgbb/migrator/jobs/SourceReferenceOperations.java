package com.zfgc.zfgbb.migrator.jobs;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

public interface SourceReferenceOperations {

	@FunctionalInterface
	interface SourceBodyRewriter {
		String rewrite(Integer sourceId, String body);
	}

	Set<Integer> collectSourceReferenceIds(String body);

	boolean containsSourceReference(String body);

	String rewriteSourceReferenceBodies(String body, SourceBodyRewriter rewriter);

	Map<Integer, NavigableMap<OffsetDateTime, String>> everyRevisionOfTheSourcesNamed(Set<Integer> sourceIds);
}
