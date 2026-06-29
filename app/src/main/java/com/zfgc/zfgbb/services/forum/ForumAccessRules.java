package com.zfgc.zfgbb.services.forum;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.authorization.ResourceAccessRules;
import com.zfgc.zfgbb.dao.ThreadDao;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Component
public class ForumAccessRules implements ResourceAccessRules {

	private static final String ROLE_FORUM_WRITE = "ROLE_ZFGC_FORUM_WRITE";
	private static final String ROLE_FORUM_MODERATE = "ROLE_ZFGC_FORUM_MODERATE";

	private final AuthorityTiers tiers;
	private final ThreadDao threadDao;
	private final MessageDboMapper messageMapper;
	private final SystemConfigService systemConfigService;

	public ForumAccessRules(AuthorityTiers tiers, ThreadDao threadDao, MessageDboMapper messageMapper,
			SystemConfigService systemConfigService) {
		this.tiers = tiers;
		this.threadDao = threadDao;
		this.messageMapper = messageMapper;
		this.systemConfigService = systemConfigService;
	}

	public record ThreadState(Integer threadId, Integer boardId, Integer ownerUserId, boolean locked,
			boolean recycled) {}

	public record MessageState(Integer messageId, Integer ownerUserId, ThreadState thread) {}

	@Override
	public boolean supports(String resourceType) {
		return "THREAD".equals(resourceType) || "MESSAGE".equals(resourceType);
	}

	@Override
	public boolean allows(User actor, String targetType, int targetId, String action) {
		if (!tiers.authenticated(actor))
			return false;
		return switch (targetType) {
			case "THREAD" -> allowsThreadAction(actor, targetId, action);
			case "MESSAGE" -> allowsMessageAction(actor, targetId, action);
			default -> false;
		};
	}

	private boolean allowsThreadAction(User actor, int threadId, String action) {
		return switch (action) {
			case "thread.reply" -> {
				ThreadState thread = loadThreadState(threadId);
				yield thread != null && canReplyToThread(actor, thread);
			}
			case "thread.restore" -> moderatorAllowed(actor);
			default -> false;
		};
	}

	private boolean allowsMessageAction(User actor, int messageId, String action) {
		return switch (action) {
			case "message.restore" -> moderatorAllowed(actor);
			case "message.delete" -> allowsMessageDelete(actor, messageId);
			default -> false;
		};
	}

	private boolean allowsMessageDelete(User actor, int messageId) {
		if (tiers.isReadOnly(actor))
			return false;
		if (tiers.hasRole(actor, ROLE_FORUM_MODERATE))
			return true;
		MessageState message = loadMessageState(messageId);
		if (message == null || message.thread() == null)
			return false;
		boolean owner = actor.getUserId().equals(message.ownerUserId());
		return owner && !message.thread().locked() && !message.thread().recycled();
	}

	public boolean canReplyToThread(User actor, ThreadState thread) {
		if (!tiers.authenticated(actor) || tiers.isReadOnly(actor) || thread.recycled())
			return false;
		return (!thread.locked() && tiers.hasRole(actor, ROLE_FORUM_WRITE)) || tiers.hasRole(actor, ROLE_FORUM_MODERATE);
	}

	public boolean canDeleteMessage(User actor, MessageState message) {
		if (!tiers.authenticated(actor) || tiers.isReadOnly(actor))
			return false;
		ThreadState thread = message.thread();
		boolean owner = actor.getUserId().equals(message.ownerUserId());
		return tiers.hasRole(actor, ROLE_FORUM_MODERATE) || (owner && !thread.locked() && !thread.recycled());
	}

	public boolean canRestoreThread(User actor, ThreadState thread) {
		if (!tiers.authenticated(actor) || tiers.isReadOnly(actor))
			return false;
		return thread.recycled() && tiers.hasRole(actor, ROLE_FORUM_MODERATE);
	}

	public boolean canRestoreMessage(User actor, MessageState message) {
		if (!tiers.authenticated(actor) || tiers.isReadOnly(actor))
			return false;
		return message.thread().recycled() && tiers.hasRole(actor, ROLE_FORUM_MODERATE);
	}

	public boolean isForumModerator(User actor) {
		return tiers.authenticated(actor) && tiers.hasRole(actor, ROLE_FORUM_MODERATE);
	}

	public Set<String> permittedThreadActions(User actor, Integer threadId) {
		ThreadState thread = loadThreadState(threadId);
		if (thread == null || !tiers.authenticated(actor))
			return Set.of();
		Set<String> permitted = new LinkedHashSet<>();
		if (canReplyToThread(actor, thread))
			permitted.add("thread.reply");
		if (moderatorAllowed(actor)) {
			permitted.add("thread.delete");
			permitted.add("thread.move");
			permitted.add("thread.lock");
			permitted.add("thread.pin");
			permitted.add("thread.split");
		}
		if (canRestoreThread(actor, thread))
			permitted.add("thread.restore");
		return permitted;
	}

	public Set<String> permittedMessageActions(User actor, Integer messageId) {
		MessageState message = loadMessageState(messageId);
		if (message == null || message.thread() == null || !tiers.authenticated(actor))
			return Set.of();
		Set<String> permitted = new LinkedHashSet<>();
		if (canDeleteMessage(actor, message))
			permitted.add("message.delete");
		if (canRestoreMessage(actor, message))
			permitted.add("message.restore");
		return permitted;
	}

	private boolean moderatorAllowed(User actor) {
		return !tiers.isReadOnly(actor) && tiers.hasRole(actor, ROLE_FORUM_MODERATE);
	}

	private ThreadState loadThreadState(Integer threadId) {
		return threadDao.get(threadId).map(this::toThreadState).orElse(null);
	}

	private ThreadState toThreadState(ThreadDbo thread) {
		boolean recycled = thread.getRecycledFromBoardId() != null || thread.getRecycledFromThreadId() != null
				|| isRecycleBoard(thread.getBoardId());
		return new ThreadState(thread.getThreadId(), thread.getBoardId(), thread.getCreatedUserId(),
				Boolean.TRUE.equals(thread.getLockedFlag()), recycled);
	}

	private MessageState loadMessageState(Integer messageId) {
		MessageDbo message = messageMapper.selectByPrimaryKey(messageId);
		if (message == null)
			return null;
		ThreadState thread = message.getThreadId() == null ? null : loadThreadState(message.getThreadId());
		return new MessageState(messageId, message.getOwnerId(), thread);
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
