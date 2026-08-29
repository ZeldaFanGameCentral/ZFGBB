package com.zfgc.zfgbb.services.forum;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.authorization.ResourceAccessRules;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.MessageState;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.ThreadState;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.mapstruct.forum.ThreadMap;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Component
@BoardVisibilityChokepoint
@RequiredArgsConstructor
public class ForumAccessStateLoader implements ResourceAccessRules {

	private final ForumAccessRules forumAccessRules;
	private final ThreadDao threadDao;
	private final MessageDao messageDao;
	private final ThreadMap threadMap;
	private final SystemConfigService systemConfigService;

	@Override
	public boolean supports(String resourceType) {
		return Objects.equals(resourceType, "THREAD") || Objects.equals(resourceType, "MESSAGE");
	}

	@Override
	public boolean allows(User actor, String targetType, int targetId, String action) {
		return switch (targetType) {
			case "THREAD" -> forumAccessRules.allowsThreadAction(actor, action, () -> loadThreadState(targetId));
			case "MESSAGE" -> forumAccessRules.allowsMessageAction(actor, action, () -> loadMessageState(targetId));
			default -> false;
		};
	}

	public Optional<MessageState> editableMessage(User actor, int messageId) {
		return forumAccessRules.editableMessage(actor, () -> loadMessageState(messageId));
	}

	public ThreadState toThreadState(Thread thread) {
		boolean recycled = thread.getRecycledFromBoardId() != null || thread.getRecycledFromThreadId() != null
				|| isRecycleBoard(thread.getBoardId());
		return new ThreadState(thread.getThreadId(), thread.getBoardId(), thread.getCreatedUserId(),
				Boolean.TRUE.equals(thread.getLockedFlag()), recycled);
	}

	private Optional<ThreadState> loadThreadState(Integer threadId) {
		return threadDao.find(threadId).map(threadMap::toModel).map(this::toThreadState);
	}

	private Optional<MessageState> loadMessageState(Integer messageId) {
		return messageDao.find(messageId)
				.map(message -> new MessageState(messageId, message.getOwnerId(),
						message.getThreadId() == null ? null : loadThreadState(message.getThreadId()).orElse(null)));
	}

	private boolean isRecycleBoard(Integer boardId) {
		if (boardId == null)
			return false;
		String configuredValue = systemConfigService.get(SystemConfigService.Keys.RECYCLE_BOARD_ID);
		if (configuredValue == null || configuredValue.isBlank())
			return false;
		try {
			return boardId.equals(Integer.valueOf(configuredValue.trim()));
		} catch (NumberFormatException notNumeric) {
			return false;
		}
	}
}
