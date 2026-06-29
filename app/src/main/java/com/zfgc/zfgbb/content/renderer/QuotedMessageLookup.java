package com.zfgc.zfgbb.content.renderer;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;

@Component
@UnfilteredBoardRead("Loads quoted message and message-history bodies for BBCode quote rendering; self-gates on caller-supplied visibleBoardIds")
public class QuotedMessageLookup {

	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private MessageHistoryDboMapper messageHistoryMapper;

	@Autowired
	private UserDboMapper userMapper;

	public static final int THREAD_PAGE_SIZE = 10;

	public record Resolved(String authorDisplayName, Integer authorUserId, OffsetDateTime createdTs,
			Integer threadId, Integer page, Integer sourceBoardId, boolean permitted,
			NavigableMap<OffsetDateTime, String> revisionsByCreatedTs) {}

	public Map<Integer, Resolved> resolve(Set<Integer> messageIds) {
		return resolve(messageIds, null);
	}

	public Map<Integer, Resolved> resolve(Set<Integer> messageIds, Set<Integer> visibleBoardIds) {
		if (messageIds == null || messageIds.isEmpty()) {
			return Map.of();
		}
		MessageDboExample msgEx = new MessageDboExample();
		msgEx.createCriteria().andMessageIdIn(List.copyOf(messageIds));
		List<MessageDbo> messages = messageMapper.selectByExample(msgEx);
		if (messages.isEmpty()) {
			return Map.of();
		}
		Set<Integer> ownerIds = new HashSet<>();
		for (MessageDbo message : messages) {
			if (message.getOwnerId() != null) {
				ownerIds.add(message.getOwnerId());
			}
		}
		Map<Integer, String> displayByUserId = new HashMap<>();
		if (!ownerIds.isEmpty()) {
			UserDboExample userEx = new UserDboExample();
			userEx.createCriteria().andUserIdIn(List.copyOf(ownerIds));
			for (UserDbo user : userMapper.selectByExample(userEx)) {
				displayByUserId.put(user.getUserId(), user.getDisplayName());
			}
		}
		Map<Integer, NavigableMap<OffsetDateTime, String>> revisionsByMessageId =
				visibleBoardIds == null ? Map.of() : loadRevisions(messageIds);
		Map<Integer, Resolved> result = new HashMap<>();
		for (MessageDbo message : messages) {
			String author = message.getOwnerId() == null
					? null
					: displayByUserId.get(message.getOwnerId());
			Integer authorUserId = author == null ? null : message.getOwnerId();
			Integer page = message.getPostInThread() == null
					? 1
					: (message.getPostInThread() - 1) / THREAD_PAGE_SIZE + 1;
			Integer sourceBoardId = message.getBoardId();
			boolean permitted = visibleBoardIds != null
					&& sourceBoardId != null && visibleBoardIds.contains(sourceBoardId);
			result.put(message.getMessageId(),
					new Resolved(author, authorUserId, message.getCreatedTs(), message.getThreadId(), page,
							sourceBoardId, permitted,
							permitted ? revisionsByMessageId.get(message.getMessageId()) : null));
		}
		return result;
	}

	private Map<Integer, NavigableMap<OffsetDateTime, String>> loadRevisions(Set<Integer> messageIds) {
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMessageIdIn(List.copyOf(messageIds));
		ex.setOrderByClause("message_id, created_ts, message_history_id");
		Map<Integer, NavigableMap<OffsetDateTime, String>> byMessageId = new HashMap<>();
		for (MessageHistoryDbo row : messageHistoryMapper.selectByExample(ex)) {
			if (row.getMessageId() == null || row.getCreatedTs() == null) {
				continue;
			}
			byMessageId.computeIfAbsent(row.getMessageId(), key -> new TreeMap<>())
					.put(row.getCreatedTs(), row.getMessageText());
		}
		return byMessageId;
	}
}
