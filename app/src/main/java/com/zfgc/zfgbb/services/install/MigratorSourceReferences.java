package com.zfgc.zfgbb.services.install;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.content.renderer.ContentTagResolver;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.dao.forum.BoardDao;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.migrator.jobs.SourceReferenceOperations;

import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "zfgbb.migrator", name = "enabled", havingValue = "true")
@UnfilteredBoardRead("background job, no user")
@RequiredArgsConstructor
public class MigratorSourceReferences implements SourceReferenceOperations {

	private final SourceReferenceService sourceReferenceService;

	private final BoardDao boardDao;

	@Override
	public Set<Integer> collectSourceReferenceIds(String body) {
		return sourceReferenceService.collectSourceReferenceIds(body);
	}

	@Override
	public boolean containsSourceReference(String body) {
		return sourceReferenceService.containsSourceReference(body);
	}

	@Override
	public String rewriteSourceReferenceBodies(String body, SourceBodyRewriter rewriter) {
		return sourceReferenceService.rewriteSourceReferenceBodies(body, rewriter::rewrite);
	}

	@Override
	public Map<Integer, NavigableMap<OffsetDateTime, String>> everyRevisionOfTheSourcesNamed(Set<Integer> sourceIds) {
		Map<Integer, NavigableMap<OffsetDateTime, String>> revisionsBySourceId = new HashMap<>();
		for (Map.Entry<Integer, ContentTagResolver.Resolved> resolved
				: sourceReferenceService.resolve(sourceIds, everyBoardId()).entrySet()) {
			ContentTagResolver.Resolved source = resolved.getValue();
			if (source.permitted() && source.revisionsByCreatedTs() != null)
				revisionsBySourceId.put(resolved.getKey(), theBodiesOf(source.revisionsByCreatedTs()));
		}
		return revisionsBySourceId;
	}

	private static NavigableMap<OffsetDateTime, String> theBodiesOf(
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions) {
		NavigableMap<OffsetDateTime, String> bodies = new TreeMap<>();
		revisions.forEach((createdTs, revision) -> bodies.put(createdTs, revision.body()));
		return bodies;
	}

	private Set<Integer> everyBoardId() {
		Set<Integer> boardIds = new HashSet<>();
		for (BoardDbo board : boardDao.get(new BoardDboExample()))
			boardIds.add(board.getBoardId());
		return boardIds;
	}
}
