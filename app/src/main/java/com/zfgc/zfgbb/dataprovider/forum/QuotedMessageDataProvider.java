package com.zfgc.zfgbb.dataprovider.forum;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.forum.MessageHistoryDao;
import com.zfgc.zfgbb.dao.users.UserDao;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class QuotedMessageDataProvider {

	public record QuotedRevision(String body, String contentFormat) {}

	public record QuotedSource(Integer messageId, String authorDisplayName, Integer authorUserId,
			OffsetDateTime createdTs, Integer threadId, Integer postInThread,
			NavigableMap<OffsetDateTime, QuotedRevision> revisionsByCreatedTs) {}

	private final MessageDao messageDao;

	private final MessageHistoryDao messageHistoryDao;

	private final UserDao userDao;

	public Map<Integer, QuotedSource> getQuotableSources(Set<Integer> messageIds, Set<Integer> visibleBoardIds) {
		if (messageIds == null || messageIds.isEmpty() || visibleBoardIds == null || visibleBoardIds.isEmpty()) {
			return Map.of();
		}
		MessageDboExample readableMessages = new MessageDboExample();
		readableMessages.createCriteria().andMessageIdIn(List.copyOf(messageIds))
				.andBoardIdIn(List.copyOf(visibleBoardIds));
		List<MessageDbo> readable = messageDao.get(readableMessages);
		if (readable.isEmpty()) {
			return Map.of();
		}
		Map<Integer, String> displayNames = displayNamesOf(readable);
		Map<Integer, NavigableMap<OffsetDateTime, QuotedRevision>> revisions = revisionsOf(
				readable.stream().map(MessageDbo::getMessageId).collect(Collectors.toSet()));
		Map<Integer, QuotedSource> quotable = new HashMap<>();
		for (MessageDbo message : readable) {
			String displayName = message.getOwnerId() == null
					? message.getGuestAuthorName()
					: displayNames.get(message.getOwnerId());
			quotable.put(message.getMessageId(), new QuotedSource(message.getMessageId(), displayName,
					displayName == null ? null : message.getOwnerId(), message.getCreatedTs(),
					message.getThreadId(), message.getPostInThread(),
					revisions.getOrDefault(message.getMessageId(), Collections.emptyNavigableMap())));
		}
		return quotable;
	}

	private Map<Integer, String> displayNamesOf(List<MessageDbo> readable) {
		List<Integer> ownerIds = readable.stream().map(MessageDbo::getOwnerId)
				.filter(Objects::nonNull).distinct().toList();
		if (ownerIds.isEmpty()) {
			return Map.of();
		}
		UserDboExample owners = new UserDboExample();
		owners.createCriteria().andUserIdIn(ownerIds);
		Map<Integer, String> displayNames = new HashMap<>();
		for (UserDbo owner : userDao.get(owners)) {
			displayNames.put(owner.getUserId(), owner.getDisplayName());
		}
		return displayNames;
	}

	private Map<Integer, NavigableMap<OffsetDateTime, QuotedRevision>> revisionsOf(Set<Integer> readableMessageIds) {
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMessageIdIn(List.copyOf(readableMessageIds));
		ex.setOrderByClause("message_id, created_ts, message_history_id");
		Map<Integer, NavigableMap<OffsetDateTime, QuotedRevision>> byMessageId = new HashMap<>();
		for (MessageHistoryDbo revision : messageHistoryDao.get(ex)) {
			if (revision.getMessageId() == null || revision.getCreatedTs() == null) {
				continue;
			}
			byMessageId.computeIfAbsent(revision.getMessageId(), key -> new TreeMap<>())
					.put(revision.getCreatedTs(),
							new QuotedRevision(revision.getMessageText(), revision.getContentFormat()));
		}
		return byMessageId;
	}
}
