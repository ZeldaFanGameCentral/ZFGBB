package com.zfgc.zfgbb.services.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.dataprovider.forum.MessageDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ThreadDataProvider;
import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.forum.ModerationLogDao;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.model.forum.MessageDeletionResponse;
import com.zfgc.zfgbb.model.forum.RestoreResponse;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadDeletionResponse;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.model.forum.MessagePosition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ForumModerationOrchestrator extends AbstractService {

	public static final String DELETION_OUTCOME_RECYCLED = "RECYCLED";
	public static final String DELETION_OUTCOME_PURGED = "PURGED";
	public static final String RESTORE_MODE_MERGED_INTO_ORIGIN = "MERGED_INTO_ORIGIN";
	public static final String RESTORE_MODE_THREAD_RESTORED = "THREAD_RESTORED";
	public static final String RESTORE_REASON_NOT_RECYCLED = "NOT_RECYCLED";
	public static final String RESTORE_REASON_RESTORE_THREAD_INSTEAD = "RESTORE_THREAD_INSTEAD";
	public static final String RESTORE_REASON_RESTORE_TARGET_MISSING = "RESTORE_TARGET_MISSING";
	public static final String RESTORE_REASON_STATE_CHANGED = "RESTORE_STATE_CHANGED";

	private static final String ACTION_MESSAGE_RECYCLED = "MESSAGE_RECYCLED";
	private static final String ACTION_THREAD_RECYCLED = "THREAD_RECYCLED";
	private static final String ACTION_MESSAGE_RESTORED = "MESSAGE_RESTORED";
	private static final String ACTION_THREAD_RESTORED = "THREAD_RESTORED";
	private static final String ACTION_MESSAGE_PURGED = "MESSAGE_PURGED";
	private static final String ACTION_THREAD_PURGED = "THREAD_PURGED";
	private static final String ACTION_THREAD_SPLIT = "THREAD_SPLIT";

	private static final int THREAD_PURGE_CHUNK_SIZE = 500;

	private final ForumService forumService;
	private final ThreadDataProvider threadDataProvider;
	private final MessageDataProvider messageDataProvider;
	private final MessageDao messageDao;
	private final ContentService contentService;
	private final UserErasureDao userErasureDao;
	private final ModerationLogDao moderationLogDao;

	public ThreadDeletionResponse deleteThread(Integer threadId, User user) {
		forumService.lockThreadRows(List.of(threadId));
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		Optional<Integer> recycleBoardId = forumService.resolveRecycleBoardId(true);
		boolean inRecycleBin = recycleBoardId.filter(thread.getBoardId()::equals).isPresent();
		if (inRecycleBin || recycleBoardId.isEmpty())
			return purgeThread(thread, user);

		Integer originBoardId = thread.getBoardId();
		thread.setBoardId(recycleBoardId.get());
		thread.setRecycledFromBoardId(originBoardId);
		thread.setRecycledFromThreadId(null);
		threadDataProvider.saveThread(thread);
		messageDataProvider.updateBoardIdForThread(threadId, recycleBoardId.get());
		writeModerationLog(ACTION_THREAD_RECYCLED, user, thread.getCreatedUserId(), originBoardId, threadId, null,
				"thread_id=" + threadId + " recycled from board_id=" + originBoardId);
		forumService.evictUnfilteredForumCache();
		return new ThreadDeletionResponse(DELETION_OUTCOME_RECYCLED, originBoardId, threadId);
	}

	public Thread moveThread(Integer threadId, Integer boardId, User user) {
		forumService.lockThreadRows(List.of(threadId));
		forumService.lockBoardRows(List.of(boardId));
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		forumService.requireBoardAction(boardId, user);

		thread.setBoardId(boardId);
		if (forumService.resolveRecycleBoardId(false).filter(boardId::equals).isEmpty()) {
			thread.setRecycledFromBoardId(null);
			thread.setRecycledFromThreadId(null);
		}
		Thread movedThread = threadDataProvider.saveThread(thread);
		messageDataProvider.updateBoardIdForThread(threadId, boardId);
		forumService.evictUnfilteredForumCache();
		return movedThread;
	}

	public Thread toggleLocked(Integer threadId, User user) {
		forumService.lockThreadRows(List.of(threadId));
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		thread.setLockedFlag(!thread.getLockedFlag());
		return threadDataProvider.saveThread(thread);
	}

	public Thread toggleSticky(Integer threadId, User user) {
		forumService.lockThreadRows(List.of(threadId));
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		thread.setPinnedFlag(!thread.getPinnedFlag());
		return threadDataProvider.saveThread(thread);
	}

	public ThreadSplit getSplitTemplate(Integer threadId, User user) {
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		ThreadSplit template = new ThreadSplit(threadId, thread.getBoardId(), null, null);

		return template;
	}

	public Thread splitThread(ThreadSplit split, User user) {
		forumService.lockThreadRows(List.of(split.threadId()));
		Thread sourceThread = threadDataProvider.getThread(split.threadId());
		super.secureObject(sourceThread, user);

		List<Integer> requestedMessageIds = forumService.orderedDistinctIds(split.messageIdsToMove());
		if (requestedMessageIds.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Select at least one message to split.");
		MessageDboExample movableScan = new MessageDboExample();
		movableScan.createCriteria().andThreadIdEqualTo(split.threadId()).andMessageIdIn(requestedMessageIds);
		List<Integer> movableMessageIds = messageDao.get(movableScan).stream().map(MessageDbo::getMessageId).toList();
		if (movableMessageIds.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"None of the selected messages belong to this thread.");
		split = new ThreadSplit(split.threadId(), split.boardId(), movableMessageIds, split.newThreadTitle());

		Thread newThread = forumService.getThreadTemplate(split.boardId(), user);
		Thread splitResult = threadDataProvider.splitThread(split, newThread);

		userErasureDao.resequencePostInThread(List.of(split.threadId(), splitResult.getThreadId()));
		threadDataProvider.gcThreadsEmptiedByDeletion(List.of(split.threadId()));

		writeModerationLog(ACTION_THREAD_SPLIT, user, sourceThread.getCreatedUserId(), newThread.getBoardId(),
				splitResult.getThreadId(), null, "thread_id=" + split.threadId() + " split: "
						+ movableMessageIds.size() + " messages moved to new thread_id=" + splitResult.getThreadId()
						+ " board_id=" + newThread.getBoardId());
		forumService.evictUnfilteredForumCache();
		return splitResult;
	}

	public MessageDeletionResponse deleteMessage(Integer messageId, User user) {
		MessagePosition message = forumService.findMessagePosition(messageId).orElseThrow(ZfgcNotFoundException::new);
		forumService.lockThreadRows(List.of(message.threadId()));
		message = reloadMessageUnderThreadLock(messageId, message.threadId());
		Thread thread = threadDataProvider.getThread(message.threadId());
		super.secureObject(thread, user);
		boolean moderator = forumService.isThreadModerator(user);

		Optional<Integer> recycleBoardId = forumService.resolveRecycleBoardId(true);
		boolean inRecycleBin = recycleBoardId.filter(thread.getBoardId()::equals).isPresent();
		if (inRecycleBin && !moderator)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Only moderators may permanently delete recycled posts.");

		if (inRecycleBin || recycleBoardId.isEmpty())
			return purgeSingleMessage(message, thread, user);
		return recycleSingleMessage(message, thread, recycleBoardId.get(), user);
	}

	private MessageDeletionResponse recycleSingleMessage(MessagePosition message, Thread thread,
			Integer recycleBoardId, User user) {
		Integer originThreadId = thread.getThreadId();
		Integer originBoardId = thread.getBoardId();
		forumService.lockThreadRows(List.of(originThreadId));
		MessagePosition lockedMessage = reloadMessageUnderThreadLock(message.messageId(), originThreadId);

		long messagesInThread = countMessagesInThread(originThreadId);
		if (messagesInThread <= 1) {
			thread.setBoardId(recycleBoardId);
			thread.setRecycledFromBoardId(originBoardId);
			thread.setRecycledFromThreadId(null);
			threadDataProvider.saveThread(thread);
			messageDataProvider.updateBoardIdForThread(originThreadId, recycleBoardId);
			writeModerationLog(ACTION_THREAD_RECYCLED, user, thread.getCreatedUserId(), originBoardId, originThreadId,
					null, "thread_id=" + originThreadId + " recycled from board_id=" + originBoardId
							+ " (sole post message_id=" + lockedMessage.messageId() + ")");
			forumService.evictUnfilteredForumCache();
			return new MessageDeletionResponse(DELETION_OUTCOME_RECYCLED, true, false, null, originBoardId,
					originThreadId, null);
		}

		Thread wrapper = new Thread();
		wrapper.setThreadName(thread.getThreadName());
		wrapper.setBoardId(recycleBoardId);
		wrapper.setCreatedUserId(lockedMessage.ownerId());
		wrapper.setRecycledFromBoardId(originBoardId);
		wrapper.setRecycledFromThreadId(originThreadId);
		Thread savedWrapper = threadDataProvider.saveThread(wrapper);

		messageDataProvider.reparentMessage(lockedMessage.messageId(), savedWrapper.getThreadId(), recycleBoardId, 1);
		userErasureDao.resequencePostInThread(List.of(originThreadId));
		writeModerationLog(ACTION_MESSAGE_RECYCLED, user, lockedMessage.ownerId(), originBoardId, originThreadId,
				lockedMessage.messageId(), "message_id=" + lockedMessage.messageId() + " post "
						+ lockedMessage.postInThread() + " recycled from thread_id=" + originThreadId + " board_id="
						+ originBoardId + " to wrapper thread_id=" + savedWrapper.getThreadId());
		forumService.evictUnfilteredForumCache();
		return new MessageDeletionResponse(DELETION_OUTCOME_RECYCLED, false, false, originThreadId, originBoardId,
				savedWrapper.getThreadId(), pageCountOf(messagesInThread - 1));
	}

	private MessageDeletionResponse purgeSingleMessage(MessagePosition message, Thread thread, User user) {
		Integer threadId = thread.getThreadId();
		Integer boardId = thread.getBoardId();
		forumService.lockThreadRows(List.of(threadId));
		MessagePosition lockedMessage = reloadMessageUnderThreadLock(message.messageId(), threadId);

		writeModerationLog(ACTION_MESSAGE_PURGED, user, lockedMessage.ownerId(), boardId, threadId,
				lockedMessage.messageId(), "message_id=" + lockedMessage.messageId() + " post "
						+ lockedMessage.postInThread() + " purged from thread_id=" + threadId + " board_id=" + boardId);
		List<Integer> touchedThreadIds = messageDataProvider.findThreadIdsForMessages(List.of(lockedMessage.messageId()));
		List<String> releasedBlobPaths = blobPathsOf(
				messageDataProvider.purgeMessagesByIds(List.of(lockedMessage.messageId())));
		threadDataProvider.gcThreadsEmptiedByDeletion(touchedThreadIds);
		messageDataProvider.resequencePostInThread(touchedThreadIds);

		boolean threadDeleted = !forumService.threadExists(threadId);
		Integer pageCount = threadDeleted ? null : pageCountOf(countMessagesInThread(threadId));
		deleteBlobFilesAfterCommit(releasedBlobPaths);
		forumService.evictUnfilteredForumCache();
		return new MessageDeletionResponse(DELETION_OUTCOME_PURGED, false, threadDeleted,
				threadDeleted ? null : threadId, boardId, null, pageCount);
	}

	private ThreadDeletionResponse purgeThread(Thread thread, User user) {
		Integer threadId = thread.getThreadId();
		Integer boardId = thread.getBoardId();
		MessageDboExample messageScan = new MessageDboExample();
		messageScan.createCriteria().andThreadIdEqualTo(threadId);
		messageScan.setOrderByClause("message_id");
		List<Integer> messageIds = messageDao.get(messageScan).stream().map(MessageDbo::getMessageId).toList();

		writeModerationLog(ACTION_THREAD_PURGED, user, thread.getCreatedUserId(), boardId, threadId, null,
				"thread_id=" + threadId + " purged from board_id=" + boardId + ", " + messageIds.size() + " messages");

		List<String> releasedBlobPaths = new ArrayList<>();
		for (int chunkStart = 0; chunkStart < messageIds.size(); chunkStart += THREAD_PURGE_CHUNK_SIZE)
			releasedBlobPaths.addAll(blobPathsOf(messageDataProvider.purgeMessagesByIds(
					messageIds.subList(chunkStart, Math.min(chunkStart + THREAD_PURGE_CHUNK_SIZE, messageIds.size())))));
		threadDataProvider.gcThreadsEmptiedByDeletion(List.of(threadId));

		deleteBlobFilesAfterCommit(releasedBlobPaths);
		forumService.evictUnfilteredForumCache();
		return new ThreadDeletionResponse(DELETION_OUTCOME_PURGED, boardId, null);
	}

	public RestoreResponse restoreMessage(Integer messageId, User user) {
		MessagePosition message = forumService.findMessagePosition(messageId).orElseThrow(ZfgcNotFoundException::new);
		Thread wrapperThread = threadDataProvider.getThread(message.threadId());
		Optional<Integer> recycleBoardId = forumService.resolveRecycleBoardId(true);
		if (recycleBoardId.filter(wrapperThread.getBoardId()::equals).isEmpty())
			throw new ZfgcConflictException(RESTORE_REASON_NOT_RECYCLED);
		if (wrapperThread.getRecycledFromThreadId() == null)
			throw new ZfgcConflictException(RESTORE_REASON_RESTORE_THREAD_INSTEAD);

		Integer wrapperThreadId = wrapperThread.getThreadId();
		Integer originThreadId = wrapperThread.getRecycledFromThreadId();
		forumService.lockThreadRows(List.of(wrapperThreadId, originThreadId));
		MessagePosition lockedMessage = reloadMessageUnderThreadLock(messageId, wrapperThreadId);
		wrapperThread = threadDataProvider.getThread(wrapperThreadId);
		Thread originThread;
		try {
			originThread = threadDataProvider.getThread(originThreadId);
		} catch (ZfgcNotFoundException missingOrigin) {
			throw new ZfgcConflictException(RESTORE_REASON_STATE_CHANGED);
		}
		if (!restoreProvenanceMatches(wrapperThread, originThread, recycleBoardId.get()))
			throw new ZfgcConflictException(RESTORE_REASON_STATE_CHANGED);
		super.secureObject(wrapperThread, user);
		Integer originBoardId = originThread.getBoardId();
		forumService.lockBoardRows(List.of(originBoardId));
		forumService.requireBoardAction(originBoardId, user);
		Integer restoredPostInThread = forumService.nextPostInThread(originThreadId);
		messageDataProvider.reparentMessage(messageId, originThreadId, originBoardId, restoredPostInThread);
		threadDataProvider.gcThreadsEmptiedByDeletion(List.of(wrapperThreadId));
		if (forumService.threadExists(wrapperThreadId))
			userErasureDao.resequencePostInThread(List.of(wrapperThreadId));

		writeModerationLog(ACTION_MESSAGE_RESTORED, user, lockedMessage.ownerId(), wrapperThread.getBoardId(),
				originThreadId, messageId, "message_id=" + messageId + " restored to thread_id=" + originThreadId
						+ " board_id=" + originBoardId + " post " + restoredPostInThread + " from wrapper thread_id="
						+ wrapperThreadId);
		forumService.evictUnfilteredForumCache();
		return RestoreResponse.mergedIntoOrigin(RESTORE_MODE_MERGED_INTO_ORIGIN, originThreadId, originBoardId,
				restoredPostInThread);
	}

	public RestoreResponse restoreThread(Integer threadId, User user) {
		Thread thread = threadDataProvider.getThread(threadId);
		super.secureObject(thread, user);
		Optional<Integer> recycleBoardId = forumService.resolveRecycleBoardId(true);
		if (recycleBoardId.filter(thread.getBoardId()::equals).isEmpty())
			throw new ZfgcConflictException(RESTORE_REASON_NOT_RECYCLED);

		if (thread.getRecycledFromThreadId() != null) {
			MessageDboExample soleMessageScan = new MessageDboExample();
			soleMessageScan.createCriteria().andThreadIdEqualTo(threadId);
			soleMessageScan.setOrderByClause("post_in_thread, message_id");
			soleMessageScan.setLimit(1);
			soleMessageScan.setOffset(0);
			Integer soleMessageId = messageDao.get(soleMessageScan).stream()
					.map(MessageDbo::getMessageId).findFirst().orElseThrow(ZfgcNotFoundException::new);
			return restoreMessage(soleMessageId, user);
		}

		forumService.lockThreadRows(List.of(threadId));
		thread = threadDataProvider.getThread(threadId);
		if (recycleBoardId.filter(thread.getBoardId()::equals).isEmpty()
				|| thread.getRecycledFromThreadId() != null)
			throw new ZfgcConflictException(RESTORE_REASON_STATE_CHANGED);
		if (thread.getRecycledFromBoardId() == null)
			throw new ZfgcConflictException(RESTORE_REASON_RESTORE_TARGET_MISSING);

		Integer originBoardId = thread.getRecycledFromBoardId();
		forumService.lockBoardRows(List.of(originBoardId));
		forumService.requireBoardAction(originBoardId, user);
		thread.setBoardId(originBoardId);
		thread.setRecycledFromBoardId(null);
		thread.setRecycledFromThreadId(null);
		threadDataProvider.saveThread(thread);
		messageDataProvider.updateBoardIdForThread(threadId, originBoardId);
		writeModerationLog(ACTION_THREAD_RESTORED, user, thread.getCreatedUserId(), recycleBoardId.get(), threadId,
				null, "thread_id=" + threadId + " restored to board_id=" + originBoardId);
		forumService.evictUnfilteredForumCache();
		return RestoreResponse.threadRestored(RESTORE_MODE_THREAD_RESTORED, threadId, originBoardId);
	}

	private void writeModerationLog(String action, User actor, Integer targetUserId, Integer boardId, Integer threadId,
			Integer messageId, String detail) {
		ModerationLogDbo entry = new ModerationLogDbo();
		entry.setAction(action);
		entry.setActorUserId(actor == null ? null : actor.getUserId());
		entry.setTargetUserId(targetUserId);
		entry.setBoardId(boardId);
		entry.setThreadId(threadId);
		entry.setMessageId(messageId);
		entry.setDetail(detail);
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		entry.setLoggedTs(now);
		entry.setCreatedTs(now);
		moderationLogDao.insertSelective(entry);
	}

	private List<String> blobPathsOf(List<ReleasedResource> released) {
		List<String> blobPaths = new ArrayList<>();
		for (ReleasedResource resource : released) {
			try {
				blobPaths.add(contentService.storedFile(resource).toString());
			} catch (RuntimeException pathUnresolvable) {
			}
		}
		return blobPaths;
	}

	private void deleteBlobFilesAfterCommit(List<String> blobPaths) {
		if (blobPaths.isEmpty())
			return;
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				for (String blobPath : blobPaths) {
					try {
						Files.deleteIfExists(Path.of(blobPath));
					} catch (IOException | RuntimeException blobDeleteFailure) {
						log.warn("orphan blob {} could not be deleted; operator sweep required", blobPath,
								blobDeleteFailure);
					}
				}
			}
		});
	}

	private Integer pageCountOf(long messageCount) {
		return (int) Math.ceil(messageCount / (double) ThreadDataProvider.DEFAULT_MESSAGES_PER_PAGE);
	}

	private long countMessagesInThread(Integer threadId) {
		MessageDboExample example = new MessageDboExample();
		example.createCriteria().andThreadIdEqualTo(threadId);
		return messageDao.count(example);
	}

	public static boolean restoreProvenanceMatches(Thread wrapper, Thread origin, Integer recycleBoardId) {
		return wrapper != null && origin != null && Objects.equals(wrapper.getBoardId(), recycleBoardId)
				&& Objects.equals(wrapper.getRecycledFromThreadId(), origin.getThreadId());
	}

	private MessagePosition reloadMessageUnderThreadLock(Integer messageId, Integer expectedThreadId) {
		MessagePosition lockedMessage = forumService.findMessagePosition(messageId).orElseThrow(ZfgcNotFoundException::new);
		if (!lockedMessage.threadId().equals(expectedThreadId))
			throw new ZfgcNotFoundException();
		return lockedMessage;
	}
}
