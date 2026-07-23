package com.zfgc.zfgbb.services.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.BBCodeService;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;
import com.zfgc.zfgbb.dataprovider.forum.ForumDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.MessageDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ThreadDataProvider;
import com.zfgc.zfgbb.dao.BoardDao;
import com.zfgc.zfgbb.dao.ThreadDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.forum.MessageHistoryDao;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.mappers.custom.ForumLockMapper;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.RecentActivityViewDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.Category;
import com.zfgc.zfgbb.model.forum.CreateThreadRequest;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.Forum;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.MessageHistory;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.services.core.IpService;
import com.zfgc.zfgbb.services.core.UserDeletionService;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.content.renderer.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.TemplateSource;
import com.zfgc.zfgbb.model.meta.IpAddress;
import com.zfgc.zfgbb.model.users.Permission;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForumService extends AbstractService implements TemplateDataService {

	public static final String DELETION_OUTCOME_RECYCLED = "RECYCLED";
	public static final String DELETION_OUTCOME_PURGED = "PURGED";
	public static final String RESTORE_MODE_MERGED_INTO_ORIGIN = "MERGED_INTO_ORIGIN";
	public static final String RESTORE_MODE_THREAD_RESTORED = "THREAD_RESTORED";
	public static final String RESTORE_REASON_NOT_RECYCLED = "NOT_RECYCLED";
	public static final String RESTORE_REASON_RESTORE_THREAD_INSTEAD = "RESTORE_THREAD_INSTEAD";
	public static final String RESTORE_REASON_RESTORE_TARGET_MISSING = "RESTORE_TARGET_MISSING";
	public static final String RESTORE_REASON_STATE_CHANGED = "RESTORE_STATE_CHANGED";
	public static final String SAVE_REASON_THREAD_RECYCLED = "THREAD_RECYCLED";

	private static final String ACTION_MESSAGE_RECYCLED = "MESSAGE_RECYCLED";
	private static final String ACTION_THREAD_RECYCLED = "THREAD_RECYCLED";
	private static final String ACTION_MESSAGE_RESTORED = "MESSAGE_RESTORED";
	private static final String ACTION_THREAD_RESTORED = "THREAD_RESTORED";
	private static final String ACTION_MESSAGE_PURGED = "MESSAGE_PURGED";
	private static final String ACTION_THREAD_PURGED = "THREAD_PURGED";
	private static final String ACTION_THREAD_SPLIT = "THREAD_SPLIT";

	private static final String ROLE_ZFGC_FORUM_WRITE = "ROLE_ZFGC_FORUM_WRITE";

	private static final int THREAD_PURGE_CHUNK_SIZE = 500;
	private static final int THREAD_PAGE_SIZE = 10;

	public record MessageDeletionResponse(String outcome, boolean originThreadRecycled, boolean originThreadDeleted,
			Integer threadId, Integer boardId, Integer recycleThreadId, Integer pageCount) {}

	public record ThreadDeletionResponse(String outcome, Integer boardId, Integer recycleThreadId) {}

	public record RestoreResponse(String mode, Integer threadId, Integer boardId, Integer postInThread) {}

	public record MessagePosition(Integer messageId, Integer ownerId, Integer threadId, Integer postInThread) {}

	private final ForumDataProvider forumDataProvider;
	private final BoardPermissionViewDboMapper boardPermissionViewDboMapper;
	private final RecentActivityViewDboMapper recentActivityViewDboMapper;
	private final ContentRenderer contentRenderer;
	private final BBCodeService bbCodeService;
	private final ThreadDataProvider threadDataProvider;
	private final MessageDataProvider messageDataProvider;
	private final IpService ipService;
	private final SystemConfigService systemConfigService;
	private final ThreadDao threadDao;
	private final BoardDao boardDao;
	private final MessageDao messageDao;
	private final MessageHistoryDao messageHistoryDao;
	private final AuthorityTiers authorityTiers;
	private final UserDeletionService userDeletionService;
	private final com.zfgc.zfgbb.services.core.deletion.ForumUserDataHandler forumUserDataHandler;
	private final UserDeletionMapper userDeletionMapper;
	private final ModerationLogDboMapper moderationLogMapper;
	private final ForumAccessRules forumAccessRules;
	private final ForumLockMapper forumLockMapper;

	private static final long UNFILTERED_FORUM_CACHE_TTL_NANOS = TimeUnit.SECONDS.toNanos(30);

	private record CachedUnfilteredForum(Forum forum, long cachedAtNanos) {
	}

	private final AtomicReference<CachedUnfilteredForum> unfilteredForumCache = new AtomicReference<>();

	private final Object unfilteredForumRebuildLock = new Object();

	private Forum loadUnfilteredForum() {
		CachedUnfilteredForum cached = unfilteredForumCache.get();
		if (cached != null && System.nanoTime() - cached.cachedAtNanos() < UNFILTERED_FORUM_CACHE_TTL_NANOS)
			return cached.forum();
		synchronized (unfilteredForumRebuildLock) {
			CachedUnfilteredForum recheckedCache = unfilteredForumCache.get();
			if (recheckedCache != null
					&& System.nanoTime() - recheckedCache.cachedAtNanos() < UNFILTERED_FORUM_CACHE_TTL_NANOS)
				return recheckedCache.forum();
			Forum freshForum = forumDataProvider.getForum();
			unfilteredForumCache.set(new CachedUnfilteredForum(freshForum, System.nanoTime()));
			return freshForum;
		}
	}

	public void evictUnfilteredForumCache() {
		unfilteredForumCache.set(null);
	}

	@Transactional(readOnly = true)
	public Forum getForum(User zfgcUser) {
		Forum cachedForum = loadUnfilteredForum();
		List<Integer> userPerms = zfgcUser.getPermissions().stream().map(Permission::getPermissionId).toList();

		Forum visibleForum = new Forum();
		visibleForum.setBoardName(StringUtils.defaultIfBlank(
				systemConfigService.get(SystemConfigService.Keys.SITE_NAME), "ZFGBB"));

		List<Category> visibleCategories = new ArrayList<>();
		for (Category cachedCategory : cachedForum.getCategories()) {
			List<BoardSummary> cachedBoards = cachedCategory.getBoards();
			if (cachedBoards == null)
				continue;
			List<BoardSummary> visibleBoards = new ArrayList<>();
			for (BoardSummary cachedBoard : cachedBoards)
				if (cachedBoard.getBoardPerms() != null && cachedBoard.getBoardPerms().stream()
						.anyMatch(bp -> userPerms.contains(bp.getPermissionId())))
					visibleBoards.add(cachedBoard);
			if (visibleBoards.isEmpty())
				continue;
			Category visibleCategory = new Category();
			visibleCategory.setCategoryId(cachedCategory.getCategoryId());
			visibleCategory.setCategoryName(cachedCategory.getCategoryName());
			visibleCategory.setDescription(cachedCategory.getDescription());
			visibleCategory.setParentCategoryId(cachedCategory.getParentCategoryId());
			visibleCategory.setBoards(visibleBoards);
			visibleCategories.add(visibleCategory);
		}
		visibleForum.setCategories(visibleCategories);

		return visibleForum;
	}

	@TemplateSource("/board/recent-activity")
	public List<RecentActivityViewDbo> getRecentActivity(String boardId,
			Integer limit, User zfgcUser) {
		if (boardId != null && !boardId.matches("\\d{1,9}"))
			return List.of();
		return getRecentActivity(boardId == null ? null : Integer.valueOf(boardId), limit, zfgcUser);
	}

	public List<RecentActivityViewDbo> getRecentActivity(Integer boardId,
			Integer limit, User zfgcUser) {
		int capped = limit == null ? 5 : Math.max(1, Math.min(limit, 25));
		Set<Integer> visibleBoards = visibleBoardIds(zfgcUser);
		if (visibleBoards.isEmpty()) {
			return List.of();
		}
		if (boardId != null && !visibleBoards.contains(boardId)) {
			return List.of();
		}
		RecentActivityViewDboExample ex = new RecentActivityViewDboExample();
		if (boardId != null) {
			ex.createCriteria().andBoardIdEqualTo(boardId);
		} else {
			ex.createCriteria().andBoardIdIn(new ArrayList<>(visibleBoards));
		}
		ex.setOrderByClause("last_post_ts desc");
		ex.setLimit(capped);
		ex.setOffset(0);
		return recentActivityViewDboMapper.selectByExampleWithLimits(ex);
	}

	private Set<Integer> visibleBoardIds(User zfgcUser) {
		return visibleBoardIds(zfgcUser.getPermissions().stream().map(Permission::getPermissionId).toList());
	}

	public Set<Integer> visibleBoardIds(List<Integer> permissionIds) {
		if (permissionIds == null || permissionIds.isEmpty()) {
			return Set.of();
		}
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andPermissionIdIn(permissionIds);
		return boardPermissionViewDboMapper.selectByExample(ex).stream()
				.map(BoardPermissionViewDbo::getBoardId)
				.collect(Collectors.toSet());
	}

	private static final int MESSAGE_ID_QUERY_CHUNK_SIZE = 10000;

	private Map<Integer, OffsetDateTime> currentRevisionCreatedTsByMessageId(List<Integer> messageIds) {
		List<Integer> ids = messageIds.stream().filter(Objects::nonNull).distinct().toList();
		Map<Integer, OffsetDateTime> createdTsByMessageId = new HashMap<>();
		for (List<Integer> chunk : partition(ids, MESSAGE_ID_QUERY_CHUNK_SIZE)) {
			MessageHistoryDboExample example = new MessageHistoryDboExample();
			example.createCriteria().andCurrentFlagEqualTo(true).andMessageIdIn(chunk);
			for (MessageHistoryDbo revision : messageHistoryDao.get(example))
				createdTsByMessageId.put(revision.getMessageId(), revision.getCreatedTs());
		}
		return createdTsByMessageId;
	}

	public static List<List<Integer>> partition(List<Integer> ids, int chunkSize) {
		List<List<Integer>> chunks = new ArrayList<>();
		for (int start = 0; start < ids.size(); start += chunkSize)
			chunks.add(ids.subList(start, Math.min(start + chunkSize, ids.size())));
		return chunks;
	}

	public Board getBoard(Integer boardId, Integer page, User zfgcUser) {
		Board board = forumDataProvider.getBoard(boardId, page, 10);
		List<Integer> userPerms = zfgcUser.getPermissions().stream().map(Permission::getPermissionId).toList();
		if (board.getBoardPerms().stream().noneMatch(bp -> userPerms.contains(bp.getPermissionId())))
			throw new ZfgcNotFoundException();
		Set<Integer> readableBoardIds = visibleBoardIds(userPerms);
		if (board.getChildBoards() != null) {
			board.getChildBoards().removeIf(childSummary -> !readableBoardIds.contains(childSummary.getBoardId()));
			for (BoardSummary childSummary : board.getChildBoards())
				if (childSummary.getChildBoards() != null)
					childSummary.getChildBoards()
							.removeIf(grandchild -> !readableBoardIds.contains(grandchild.getBoardId()));
		}
		return board;
	}

	public Thread getThreadTemplate(Integer boardId, User zfgcUser) {
		Thread thread = new Thread();
		thread.setBoardId(boardId);
		thread.setCreatedUserId(zfgcUser.getUserId());
		thread.setBoardPermissions(forumDataProvider.getBoardPermissions(thread.getBoardId()));
		super.secureObject(thread, zfgcUser);

		Message message = getMessageTemplate(boardId, null, null, zfgcUser);
		thread.getMessages().add(message);

		return thread;
	}

	public Message getMessageTemplate(Integer boardId, Integer threadId, Integer messageId, User zfgcUser) {

		Message message = null;
		message = new Message();
		message.setOwnerId(zfgcUser.getUserId());
		message.setThreadId(threadId);
		message.getCurrentMessage().setCurrentFlag(true);

		return message;

	}

	@Transactional
	public Thread createThread(Integer boardId, CreateThreadRequest request, User zfgcUser) {
		if (request == null || StringUtils.isBlank(request.title()) || StringUtils.isBlank(request.body()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread title and body are required.");
		if (authorityTiers.isReadOnly(zfgcUser) || !authorityTiers.hasRole(zfgcUser, ROLE_ZFGC_FORUM_WRITE))
			throw new ZfgcUnauthorizedException("Thread creation requires forum write access.", zfgcUser);
		Thread thread = new Thread();
		thread.setBoardId(boardId);
		thread.setBoardPermissions(forumDataProvider.getBoardPermissions(boardId));
		super.secureObject(thread, zfgcUser);
		thread.setThreadName(StringUtils.abbreviate(request.title().trim(), 64));
		thread.setCreatedUserId(zfgcUser.getUserId());
		thread.setPinnedFlag(false);
		thread.setLockedFlag(false);
		thread.setRecycledFromBoardId(null);
		thread.setRecycledFromThreadId(null);
		thread = threadDataProvider.saveThread(thread);
		saveMessage(thread.getThreadId(), request.body(), zfgcUser);
		evictUnfilteredForumCache();
		return thread;
	}

	@Transactional
	public Integer createDiscussionThread(String title, String body, User zfgcUser) {
		String boardId = systemConfigService.get(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID);
		if (boardId == null || boardId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"No discussion board configured (cms_discussion_board_id)");
		}
		int discussionBoardId;
		try {
			discussionBoardId = Integer.parseInt(boardId.trim());
		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid discussion board configured (cms_discussion_board_id)");
		}
		return createThread(discussionBoardId, new CreateThreadRequest(title, body), zfgcUser).getThreadId();
	}

	@TemplateSource("/thread/{threadId}")
	public Thread getThread(Integer threadId, Integer page, Integer pageSize, User zfgcUser) {
		Thread thread = threadDataProvider.getThread(threadId, page, pageSize);

		requireReadableThreadElseNotFound(thread, zfgcUser);
		thread.setRecycleBinEnabled(resolveRecycleBoardId(false).isPresent());

		Map<Integer, OffsetDateTime> quotingTsByMessageId = currentRevisionCreatedTsByMessageId(
				thread.getMessages().stream().map(Message::getMessageId).toList());
		bbCodeService.openQuoteScope(thread.getMessages().stream()
				.map(message -> new BBCodeService.QuotingPost(message.getCurrentMessage().getMessageText(),
						quotingTsByMessageId.get(message.getMessageId())))
				.toList(), visibleBoardIds(zfgcUser));
		try {
			thread.getMessages().forEach(message -> {
				String parsed = contentRenderer.render(message.getCurrentMessage().getMessageText(),
						ContentFormat.BBCODE, quotingTsByMessageId.get(message.getMessageId()));
				message.getCurrentMessage().setMessageText(parsed);
			});
		} finally {
			bbCodeService.closeQuoteScope();
		}

		return thread;
	}

	public Set<String> threadAllowedActions(Integer threadId, User user) {
		Thread thread = threadDataProvider.getThread(threadId);
		requireReadableThreadElseNotFound(thread, user);
		return forumAccessRules.permittedThreadActions(user, threadId);
	}

	public Set<String> messageAllowedActions(Integer messageId, User user) {
		MessagePosition message = findMessagePosition(messageId).orElseThrow(ZfgcNotFoundException::new);
		Thread thread = threadDataProvider.getThread(message.threadId());
		requireReadableThreadElseNotFound(thread, user);
		return forumAccessRules.permittedMessageActions(user, messageId);
	}

	private void requireReadableThreadElseNotFound(Thread thread, User zfgcUser) {
		List<Integer> callerPermissionIds = zfgcUser.getPermissions().stream()
				.map(Permission::getPermissionId).toList();
		if (thread.getBoardPermissions().stream()
				.noneMatch(boardPermission -> callerPermissionIds.contains(boardPermission.getPermissionId())))
			throw new ZfgcNotFoundException();
	}

	@Transactional
	public Message saveMessage(Integer threadId, String body, User user) {
		if (StringUtils.isBlank(body))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body is required.");
		Message message = new Message();
		message.setThreadId(threadId);
		message.getCurrentMessage().setUnparsedText(body);
		message.getCurrentMessage().setCurrentFlag(true);
		return saveMessage(message, user);
	}

	@Transactional
	public Message saveMessage(Message message, User user) {
		lockThreadRows(List.of(message.getThreadId()));
		Thread thread = threadDataProvider.getThread(message.getThreadId());
		super.secureObject(thread, user);
		message.setPostInThread(nextPostInThread(thread.getThreadId()));
		message.setBoardId(thread.getBoardId());

		message.setOwnerId(user.getUserId());

		IpAddress ip = ipService.getClientIp();
		if (message.getCurrentMessage() != null && ip != null) {
			message.getCurrentMessage().setIpAddressId(ip.getId());
		}

		Message savedMessage = messageDataProvider.saveMessage(message);
		evictUnfilteredForumCache();
		return savedMessage;
	}







	public List<Message> getMessagesByUserId(Integer userId, Integer page, Integer count, List<Integer> permissionIds) {
		List<Message> messages = messageDataProvider.getMessagesByUser(userId, page, count, permissionIds);
		Map<Integer, OffsetDateTime> quotingTsByMessageId = currentRevisionCreatedTsByMessageId(
				messages.stream().map(Message::getMessageId).toList());
		bbCodeService.openQuoteScope(messages.stream()
				.map(message -> new BBCodeService.QuotingPost(message.getCurrentMessage().getMessageText(),
						quotingTsByMessageId.get(message.getMessageId())))
				.toList(), visibleBoardIds(permissionIds));
		try {
			return messages.stream()
					.map(message -> {
						String parsed = contentRenderer.render(message.getCurrentMessage().getMessageText(),
								ContentFormat.BBCODE, quotingTsByMessageId.get(message.getMessageId()));
						message.getCurrentMessage().setMessageText(parsed);
						return message;

					}).toList();
		} finally {
			bbCodeService.closeQuoteScope();
		}
	}

	public Integer nextPostInThread(Integer threadId) {
		forumLockMapper.lockThread(threadId);
		return forumLockMapper.maxPostInThread(threadId) + 1;
	}

	public boolean isThreadModerator(User user) {
		return forumAccessRules.isForumModerator(user);
	}







	public Optional<Integer> resolveRecycleBoardId(boolean failWhenMisconfigured) {
		String configuredValue = systemConfigService.get(SystemConfigService.Keys.RECYCLE_BOARD_ID);
		if (configuredValue == null || configuredValue.isBlank())
			return Optional.empty();
		Integer recycleBoardId = null;
		try {
			recycleBoardId = Integer.valueOf(configuredValue.trim());
		} catch (NumberFormatException notNumeric) {
		}
		if (recycleBoardId == null || !boardExists(recycleBoardId)) {
			log.warn("recycle_board_id is misconfigured (value '{}' does not name an existing board)",
					configuredValue);
			if (failWhenMisconfigured)
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"recycle_board_id misconfigured");
			return Optional.empty();
		}
		return Optional.of(recycleBoardId);
	}

	public Optional<MessagePosition> findMessagePosition(Integer messageId) {
		return messageDao.get(messageId).map(message -> new MessagePosition(message.getMessageId(),
				message.getOwnerId(), message.getThreadId(), message.getPostInThread()));
	}


	public void lockThreadRows(List<Integer> threadIds) {
		List<Integer> orderedThreadIds = orderedDistinctIds(threadIds);
		for (Integer threadId : orderedThreadIds) {
			if (forumLockMapper.lockThread(threadId) == null)
				throw new ZfgcNotFoundException();
		}
	}

	public static List<Integer> orderedDistinctIds(List<Integer> ids) {
		return ids.stream().filter(Objects::nonNull).distinct().sorted().toList();
	}

	public static boolean restoreProvenanceMatches(Thread wrapper, Thread origin, Integer recycleBoardId) {
		return wrapper != null && origin != null && Objects.equals(wrapper.getBoardId(), recycleBoardId)
				&& Objects.equals(wrapper.getRecycledFromThreadId(), origin.getThreadId());
	}

	public void lockBoardRows(List<Integer> boardIds) {
		List<Integer> orderedBoardIds = boardIds.stream().filter(Objects::nonNull).distinct().sorted().toList();
		for (Integer boardId : orderedBoardIds) {
			if (forumLockMapper.lockBoard(boardId) == null)
				throw new ZfgcNotFoundException();
		}
	}

	public void requireBoardAction(Integer boardId, User user) {
		if (!visibleBoardIds(user).contains(boardId))
			throw new ZfgcNotFoundException();
	}

	public boolean threadExists(Integer threadId) {
		ThreadDboExample example = new ThreadDboExample();
		example.createCriteria().andThreadIdEqualTo(threadId);
		return threadDao.getMapper().countByExample(example) > 0;
	}

	public boolean boardExists(Integer boardId) {
		BoardDboExample example = new BoardDboExample();
		example.createCriteria().andBoardIdEqualTo(boardId);
		return boardDao.getMapper().countByExample(example) > 0;
	}




}
