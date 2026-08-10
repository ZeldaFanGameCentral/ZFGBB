package com.zfgc.zfgbb.services.forum;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.dataprovider.meta.IpDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ForumDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.MessageDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ThreadDataProvider;
import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.MessageState;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.ThreadState;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.Category;
import com.zfgc.zfgbb.model.forum.CreateThreadRequest;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.Forum;
import com.zfgc.zfgbb.model.forum.ForumPagination;
import com.zfgc.zfgbb.model.forum.MessagePosition;
import com.zfgc.zfgbb.mapstruct.forum.BoardMap;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.RecentActivity;
import com.zfgc.zfgbb.model.forum.MessageHistory;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.services.contentstore.AuthoringContentFormat;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.model.meta.IpAddress;
import com.zfgc.zfgbb.model.users.Permission;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ForumService extends AbstractService implements TemplateDataService {

	private static final String ROLE_ZFGC_FORUM_WRITE = "ROLE_ZFGC_FORUM_WRITE";

	private final ForumDataProvider forumDataProvider;
	private final ContentRenderingService contentRenderingService;
	private final AuthoringContentFormat authoringContentFormat;

	private final ThreadDataProvider threadDataProvider;
	private final MessageDataProvider messageDataProvider;
	private final IpDataProvider ipDataProvider;
	private final SystemConfigService systemConfigService;
	private final AuthorityTiers authorityTiers;
	private final ForumAccessRules forumAccessRules;
	private final ForumAccessStateLoader forumAccessStateLoader;
	private final BoardMap boardMap;

	private static final long UNFILTERED_FORUM_CACHE_TTL_NANOS = TimeUnit.SECONDS.toNanos(30);

	private record CachedUnfilteredForum(Forum forum, long cachedAtNanos) {
	}

	private final AtomicReference<CachedUnfilteredForum> unfilteredForumCache = new AtomicReference<>();

	private final Object unfilteredForumRebuildLock = new Object();

	private final AtomicLong unfilteredForumEvictions = new AtomicLong();

	private Forum loadUnfilteredForum() {
		CachedUnfilteredForum cached = unfilteredForumCache.get();
		if (cached != null && System.nanoTime() - cached.cachedAtNanos() < UNFILTERED_FORUM_CACHE_TTL_NANOS)
			return cached.forum();
		synchronized (unfilteredForumRebuildLock) {
			CachedUnfilteredForum recheckedCache = unfilteredForumCache.get();
			if (recheckedCache != null
					&& System.nanoTime() - recheckedCache.cachedAtNanos() < UNFILTERED_FORUM_CACHE_TTL_NANOS)
				return recheckedCache.forum();
			long evictionsBeforeTheRead = unfilteredForumEvictions.get();
			Forum freshForum = forumDataProvider.getForum();
			if (unfilteredForumEvictions.get() == evictionsBeforeTheRead)
				unfilteredForumCache.set(new CachedUnfilteredForum(freshForum, System.nanoTime()));
			return freshForum;
		}
	}

	public void evictUnfilteredForumCache() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
				@Override
				public void afterCommit() {
					evictTheUnfilteredForumNow();
				}
			});
		} else {
			evictTheUnfilteredForumNow();
		}
	}

	private void evictTheUnfilteredForumNow() {
		unfilteredForumEvictions.incrementAndGet();
		unfilteredForumCache.set(null);
	}

	@Transactional(readOnly = true)
	public Forum getForum(User zfgcUser) {
		Forum cachedForum = loadUnfilteredForum();

		Forum visibleForum = new Forum();
		visibleForum.setBoardName(StringUtils.defaultIfBlank(
				systemConfigService.get(SystemConfigService.Keys.SITE_NAME), "ZFGBB"));

		Set<Integer> readableBoardIds = anyBoardHasChildren(cachedForum)
				? visibleBoardIds(zfgcUser) : Set.of();
		List<Category> visibleCategories = new ArrayList<>();
		for (Category cachedCategory : cachedForum.getCategories()) {
			List<BoardSummary> cachedBoards = cachedCategory.getBoards();
			if (cachedBoards == null)
				continue;
			List<BoardSummary> visibleBoards = new ArrayList<>();
			for (BoardSummary cachedBoard : cachedBoards)
				if (zfgcUser.canAccess(cachedBoard))
					visibleBoards.add(withReadableChildBoards(cachedBoard, readableBoardIds));
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
	public List<RecentActivity> getRecentActivity(String boardId,
			Integer limit, User zfgcUser) {
		if (boardId != null && !boardId.matches("\\d{1,9}"))
			return List.of();
		return getRecentActivity(boardId == null ? null : Integer.valueOf(boardId), limit, zfgcUser);
	}

	public List<RecentActivity> getRecentActivity(Integer boardId,
			Integer limit, User zfgcUser) {
		int capped = limit == null ? 5 : Math.max(1, Math.min(limit, 25));
		Set<Integer> visibleBoards = visibleBoardIds(zfgcUser);
		if (visibleBoards.isEmpty()) {
			return List.of();
		}
		if (boardId != null && !visibleBoards.contains(boardId)) {
			return List.of();
		}
		return forumDataProvider.getRecentActivity(boardId, visibleBoards, capped);
	}

	private static boolean anyBoardHasChildren(Forum cachedForum) {
		for (Category category : cachedForum.getCategories()) {
			if (category.getBoards() == null)
				continue;
			for (BoardSummary board : category.getBoards())
				if (board.getChildBoards() != null && !board.getChildBoards().isEmpty())
					return true;
		}
		return false;
	}

	private BoardSummary withReadableChildBoards(BoardSummary cachedBoard, Set<Integer> readableBoardIds) {
		if (cachedBoard.getChildBoards() == null || cachedBoard.getChildBoards().isEmpty())
			return cachedBoard;
		BoardSummary visibleBoard = boardMap.deepCopy(cachedBoard);
		visibleBoard.getChildBoards().removeIf(child -> !readableBoardIds.contains(child.getBoardId()));
		return visibleBoard;
	}

	private Set<Integer> visibleBoardIds(User zfgcUser) {
		return visibleBoardIds(zfgcUser.permissionIds());
	}

	public Set<Integer> visibleBoardIds(List<Integer> permissionIds) {
		return forumDataProvider.visibleBoardIds(permissionIds);
	}

	private Map<Integer, OffsetDateTime> currentRevisionCreatedTsByMessageId(List<Integer> messageIds) {
		return messageDataProvider.currentRevisionCreatedTsByMessageId(messageIds);
	}

	public Board getBoard(Integer boardId, Integer page, User zfgcUser) {
		Board board = forumDataProvider.getBoard(boardId, page, ForumPagination.THREADS_PER_BOARD_PAGE);
		if (!zfgcUser.canAccess(board))
			throw new ZfgcNotFoundException();
		Set<Integer> readableBoardIds = visibleBoardIds(zfgcUser);
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

		Message message = getMessageTemplate(null, zfgcUser);
		thread.getMessages().add(message);

		return thread;
	}

	public Message getMessageTemplate(Integer threadId, User zfgcUser) {
		Message message = new Message();
		message.setOwnerId(zfgcUser.getUserId());
		message.setThreadId(threadId);
		message.getCurrentMessage().setCurrentFlag(true);
		message.getCurrentMessage().setContentFormat(authoringContentFormat.siteDefault().name());
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
		thread.setThreadName(request.title().trim());
		thread.setCreatedUserId(zfgcUser.getUserId());
		thread.setPinnedFlag(false);
		thread.setLockedFlag(false);
		thread.setRecycledFromBoardId(null);
		thread.setRecycledFromThreadId(null);
		thread = threadDataProvider.saveThread(thread);
		saveMessage(thread.getThreadId(), request.body(), request.contentFormat(), zfgcUser);
		evictUnfilteredForumCache();
		return thread;
	}

	@Transactional
	public Integer createDiscussionThread(String title, String body, String requestedContentFormat, User zfgcUser) {
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
		return createThread(discussionBoardId, new CreateThreadRequest(title, body, requestedContentFormat),
				zfgcUser).getThreadId();
	}

	@TemplateSource("/thread/{threadId}")
	public Thread getThread(Integer threadId, Integer page, Integer pageSize, User zfgcUser) {
		Thread thread = threadDataProvider.getThread(threadId, page, pageSize);

		requireReadableThreadElseNotFound(thread, zfgcUser);
		thread.setRecycleBinEnabled(resolveRecycleBoardId(false).isPresent());

		renderMessageBodiesWithinQuoteScope(thread.getMessages(), visibleBoardIds(zfgcUser));
		inlineAllowedActions(thread, zfgcUser);

		return thread;
	}

	private void inlineAllowedActions(Thread thread, User zfgcUser) {
		ThreadState threadState = forumAccessStateLoader.toThreadState(thread);
		thread.setAllowedActions(forumAccessRules.permittedThreadActions(zfgcUser, threadState));
		for (Message message : thread.getMessages())
			message.setAllowedActions(forumAccessRules.permittedMessageActions(zfgcUser,
					new MessageState(message.getMessageId(), message.getOwnerId(), threadState)));
	}

	private void requireReadableThreadElseNotFound(Thread thread, User zfgcUser) {
		if (!zfgcUser.canAccess(thread))
			throw new ZfgcNotFoundException();
	}

	@Transactional
	public Message saveMessage(Integer threadId, String body, String requestedContentFormat, User user) {
		if (StringUtils.isBlank(body))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body is required.");
		Message message = new Message();
		message.setThreadId(threadId);
		message.getCurrentMessage().setUnparsedText(body);
		message.getCurrentMessage().setCurrentFlag(true);
		message.getCurrentMessage().setContentFormat(
				authoringContentFormat.forNewContent(requestedContentFormat).name());
		return saveMessage(message, user);
	}

	private IpAddress clientIpAddress() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String remoteAddress = (requestAttributes != null && requestAttributes.getRequest() != null)
				? requestAttributes.getRequest().getRemoteAddr()
				: "127.0.0.1";
		return ipDataProvider.createOrRetrieveIp(remoteAddress);
	}

	@Transactional
	public Message saveMessage(Message message, User user) {
		lockThreadRows(List.of(message.getThreadId()));
		Thread thread = threadDataProvider.getThread(message.getThreadId());
		super.secureObject(thread, user);
		message.setPostInThread(nextPostInThread(thread.getThreadId()));
		message.setBoardId(thread.getBoardId());

		message.setOwnerId(user.getUserId());

		IpAddress ip = clientIpAddress();
		if (message.getCurrentMessage() != null && ip != null) {
			message.getCurrentMessage().setIpAddressId(ip.getId());
		}

		Message savedMessage = messageDataProvider.saveMessage(message);
		evictUnfilteredForumCache();
		return savedMessage;
	}

	@Transactional
	public Message editMessage(Integer messageId, String body, String requestedContentFormat, User user) {
		if (StringUtils.isBlank(body))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body is required.");
		Integer editedBoardId = forumAccessStateLoader.editableMessage(user, messageId)
				.map(editable -> editable.thread().boardId())
				.orElseThrow(() -> new ZfgcUnauthorizedException("Message editing is not permitted.", user));
		List<Permission> editedBoardPermissions = forumDataProvider.getBoardPermissions(editedBoardId);
		super.secureObject(() -> editedBoardPermissions, user);
		MessageHistory revision = new MessageHistory();
		revision.setUnparsedText(body);
		revision.setCurrentFlag(true);
		revision.setContentFormat(authoringContentFormat.forSupersedingContent(requestedContentFormat,
				() -> currentRevisionContentFormat(messageId)).name());
		IpAddress ip = clientIpAddress();
		if (ip != null)
			revision.setIpAddressId(ip.getId());
		Message saved = messageDataProvider.editMessage(messageId, revision);
		evictUnfilteredForumCache();
		return saved;
	}

	private Optional<ContentFormat> currentRevisionContentFormat(Integer messageId) {
		return messageDataProvider.currentRevisionContentFormat(messageId);
	}


	public List<Message> getMessagesByUserId(Integer userId, Integer page, Integer count, List<Integer> permissionIds) {
		return renderMessageBodiesWithinQuoteScope(
				messageDataProvider.getMessagesByUser(userId, page, count, permissionIds),
				visibleBoardIds(permissionIds));
	}

	private List<Message> renderMessageBodiesWithinQuoteScope(List<Message> messages,
			Set<Integer> visibleBoardIds) {
		Map<Integer, OffsetDateTime> quotingTsByMessageId = currentRevisionCreatedTsByMessageId(
				messages.stream().map(Message::getMessageId).toList());
		try (ContentRenderingService.QuoteScope quoteScope = contentRenderingService.openQuoteScope(messages.stream()
				.map(message -> new ContentRenderingService.QuotingPost(message.getCurrentMessage().getMessageText(),
						quotingTsByMessageId.get(message.getMessageId())))
				.toList(), visibleBoardIds)) {
			for (Message message : messages)
				message.getCurrentMessage().setMessageText(contentRenderingService.render(
						message.getCurrentMessage().getMessageText(),
						ContentFormat.parse(message.getCurrentMessage().getContentFormat())
								.orElse(ContentFormat.BBCODE),
						ContentScope.FORUM,
						quotingTsByMessageId.get(message.getMessageId())));
		}
		return messages;
	}

	public Integer nextPostInThread(Integer threadId) {
		return threadDataProvider.nextPostInThread(threadId);
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
		return messageDataProvider.findMessagePosition(messageId);
	}

	public void lockThreadRows(List<Integer> threadIds) {
		List<Integer> orderedThreadIds = orderedDistinctIds(threadIds);
		if (!orderedThreadIds.isEmpty()) {
			List<Integer> locked = threadDataProvider.lockThreads(orderedThreadIds);
			if (locked.size() != orderedThreadIds.size())
				throw new ZfgcNotFoundException();
		}
	}

	public static List<Integer> orderedDistinctIds(List<Integer> ids) {
		return ids.stream().filter(Objects::nonNull).distinct().sorted().toList();
	}

	public void lockBoardRows(List<Integer> boardIds) {
		List<Integer> orderedBoardIds = orderedDistinctIds(boardIds);
		if (!orderedBoardIds.isEmpty()) {
			List<Integer> locked = forumDataProvider.lockBoards(orderedBoardIds);
			if (locked.size() != orderedBoardIds.size())
				throw new ZfgcNotFoundException();
		}
	}

	public void requireBoardAction(Integer boardId, User user) {
		if (!visibleBoardIds(user).contains(boardId))
			throw new ZfgcNotFoundException();
	}

	public boolean threadExists(Integer threadId) {
		return threadDataProvider.threadExists(threadId);
	}

	public boolean boardExists(Integer boardId) {
		return forumDataProvider.boardExists(boardId);
	}
}
