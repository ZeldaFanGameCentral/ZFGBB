package com.zfgc.zfgbb.services.forum;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.ContentTagResolver;
import com.zfgc.zfgbb.dataprovider.forum.QuotedMessageDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.QuotedMessageDataProvider.QuotedRevision;
import com.zfgc.zfgbb.dataprovider.forum.QuotedMessageDataProvider.QuotedSource;
import com.zfgc.zfgbb.dataprovider.forum.ThreadDataProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuotedMessageSource implements ContentTagResolver {

	public static final String RESOLVER_CODE = "MESSAGE";

	private final QuotedMessageDataProvider quotedMessageDataProvider;

	@Override
	public String resolverCode() {
		return RESOLVER_CODE;
	}

	@Override
	public Map<Integer, Resolved> resolve(Set<Integer> sourceIds, Set<Integer> visibleBoardIds) {
		Map<Integer, Resolved> resolved = new HashMap<>();
		for (QuotedSource source : quotedMessageDataProvider.getQuotableSources(sourceIds, visibleBoardIds).values())
			resolved.put(source.messageId(), new Resolved(source.authorDisplayName(), source.authorUserId(),
					source.createdTs(), source.threadId(), pageOf(source.postInThread()), null, true,
					revisionsOf(source)));
		return resolved;
	}

	private static Integer pageOf(Integer postInThread) {
		return postInThread == null
				? 1
				: (postInThread - 1) / ThreadDataProvider.DEFAULT_MESSAGES_PER_PAGE + 1;
	}

	private static NavigableMap<OffsetDateTime, SourceRevision> revisionsOf(QuotedSource source) {
		NavigableMap<OffsetDateTime, SourceRevision> revisions = new TreeMap<>();
		for (Map.Entry<OffsetDateTime, QuotedRevision> revision : source.revisionsByCreatedTs().entrySet())
			revisions.put(revision.getKey(), new SourceRevision(revision.getValue().body(),
					ContentFormat.parse(revision.getValue().contentFormat()).orElse(ContentFormat.BBCODE)));
		return revisions;
	}
}
