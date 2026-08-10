package com.zfgc.zfgbb.dataprovider.forum;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dao.forum.BoardDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dao.forum.PollDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDboExample;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.forum.LatestMessageInThreadViewDao;
import com.zfgc.zfgbb.dao.forum.PollChoiceDao;
import com.zfgc.zfgbb.dao.forum.UserPollChoiceDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.mapstruct.forum.PollMap;
import com.zfgc.zfgbb.mapstruct.forum.ThreadMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.Poll;
import com.zfgc.zfgbb.model.forum.ForumPagination;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.model.users.Permission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ThreadDataProvider {

	private static final int MAX_MESSAGES_PER_PAGE = 100;

	public static final int DEFAULT_MESSAGES_PER_PAGE = ForumPagination.MESSAGES_PER_THREAD_PAGE;

	private final ThreadDao threadDao;

	private final UserErasureDao userErasureDao;

	private final UserPollChoiceDao userPollChoiceDao;

	private final MigratorIdMapDao migratorIdMapDao;

	private final MessageDataProvider messageDataProvider;

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final BoardDao boardDao;

	private final PollDao pollDao;

	private final PollChoiceDao pollChoiceDao;

	private final UserDao userDao;

	private final LatestMessageInThreadViewDao latestMessageInThreadViewDao;

	private final MessageDao messageDao;

	private final PollMap pollMap;

	private final ThreadMap threadMap;

	private final UserMap userMap;

	private final PermissionMap permissionMap;

	public boolean threadExists(Integer threadId) {
		ThreadDboExample example = new ThreadDboExample();
		example.createCriteria().andThreadIdEqualTo(threadId);
		return threadDao.exists(example);
	}

	public Integer nextPostInThread(Integer threadId) {
		threadDao.lockForUpdate(threadId);
		return messageDao.maxPostInThread(threadId) + 1;
	}

	public List<Integer> lockThreads(List<Integer> orderedThreadIds) {
		return threadDao.lockForUpdate(orderedThreadIds);
	}

	public Thread getThread(Integer threadId, Integer page, Integer count) {
		int pageSize = (count == null || count < 1) ? DEFAULT_MESSAGES_PER_PAGE : Math.min(count, MAX_MESSAGES_PER_PAGE);
		Optional<ThreadDbo> threadDb = threadDao.find(threadId);
		return threadDb.map(threadDbo -> {
			Thread result = threadMap.toModel(threadDbo);

			MessageDboExample ex = new MessageDboExample();
			ex.createCriteria().andThreadIdEqualTo(threadId);
			long msgCount = messageDao.count(ex);

			List<Message> messages = (page == null || page <= 0)
					? messageDataProvider.getMessagesFrom(threadId, (int) Math.max(1, msgCount - pageSize + 1), pageSize)
					: messageDataProvider.getMessagesForThread(threadId, page, pageSize);
			result.setMessages(messages);

			result.setBoardPermissions(getBoardPermissions(result.getBoardId()));

			boardDao.find(result.getBoardId())
					.ifPresent(board -> result.setBoardName(board.getBoardName()));

			result.setPollInfo(getPollInfo(threadId));

			result.setPageCount((int)Math.ceil((double)msgCount / (double)pageSize));

			return result;
		}).orElseThrow(() -> new ZfgcNotFoundException());
	}
	
	public Poll getPollInfo(Integer threadId) {
		PollDboExample pollEx = new PollDboExample();
		pollEx.createCriteria().andThreadIdEqualTo(threadId);
		return pollDao.getOne(pollEx)
						  .map(poll -> {
							  	PollChoiceDboExample choiceEx = new PollChoiceDboExample();
							  	choiceEx.createCriteria().andActiveFlagEqualTo(true)
							  							 .andPollIdEqualTo(poll.getPollId());
							  	List<PollChoiceDbo> choices = pollChoiceDao.get(choiceEx);
							  	
							  	Poll result = pollMap.toModel(poll, choices);

								return result;
						  }).orElse(null);
	}
	
	public List<Thread> getThreadsByBoardId(Integer boardId, Integer pageNumber, Integer pageSize){
		LatestMessageInThreadViewDboExample latestMessageEx = new LatestMessageInThreadViewDboExample();
		if(pageNumber != null && pageSize != null) {
			int safePageSize = Math.min(Math.max(pageSize, 1), MAX_MESSAGES_PER_PAGE);
			int safePageNumber = Math.max(pageNumber, 1);
			long zeroBasedOffset = (long) (safePageNumber - 1) * safePageSize;
			if (zeroBasedOffset > Integer.MAX_VALUE)
				return List.of();
			latestMessageEx.setLimit(safePageSize);
			latestMessageEx.setOffset((int) zeroBasedOffset);
		}
		latestMessageEx.setOrderByClause("pinned_flag desc, last_post_ts desc");
		latestMessageEx.createCriteria().andBoardIdEqualTo(boardId);

		List<LatestMessageInThreadViewDbo> latestMessages = latestMessageInThreadViewDao.get(latestMessageEx);
		List<Thread> result = latestMessages.stream().map(threadMap::toThread).collect(Collectors.toList());
		Map<Integer, LatestMessageInThreadViewDbo> messagesByThreadId = latestMessages.stream()
																					  .collect(Collectors.toMap(LatestMessageInThreadViewDbo::getThreadId, Function.identity()));
		latestMessages.clear();

		if (result.isEmpty()) {
			return result;
		}

		List<Integer> threadIds = result.stream().map(Thread::getThreadId).filter(Objects::nonNull).toList();

		Map<Integer, Integer> postCountsByThreadId = threadIds.isEmpty() ? Map.of() :
				messageDao.postCountsByThreadIds(threadIds).stream()
						.collect(Collectors.toMap(MessagePostCountMapper.ThreadPostCount::getThreadId,
								MessagePostCountMapper.ThreadPostCount::getPostCount));

		Map<Integer, MessagePostCountMapper.LatestMessageUser> latestMessageUserByThreadId = threadIds.isEmpty() ? Map.of() :
				messageDao.latestMessageUsersByThreadIds(threadIds).stream()
						.collect(Collectors.toMap(MessagePostCountMapper.LatestMessageUser::getThreadId, Function.identity()));

		Map<Integer, User> startersByUserId = loadStarterUsers(result);

		result.forEach(thread -> {
			User starter = startersByUserId.get(thread.getCreatedUserId());
			thread.setCreatedUser(starter != null ? starter : User.orphaned());

			int postCount = postCountsByThreadId.getOrDefault(thread.getThreadId(), 0);
			thread.setPostCount(postCount);

			LatestMessageInThreadViewDbo latestDbo = messagesByThreadId.get(thread.getThreadId());
			if (latestDbo != null) {
				thread.setLatestMessage(threadMap.toLatestMessage(latestDbo));
				MessagePostCountMapper.LatestMessageUser latestUser = latestMessageUserByThreadId.get(thread.getThreadId());
				if (latestUser != null) {
					thread.getLatestMessage().setOwnerId(latestUser.getLastPostedUserId());
					thread.getLatestMessage().setOwnerName(latestUser.getLastPostedUser());
				}
			}
		});

		return result;
	}

	private Map<Integer, User> loadStarterUsers(List<Thread> threads) {
		List<Integer> starterUserIds = threads.stream().map(Thread::getCreatedUserId)
				.filter(userId -> userId != null).distinct().toList();
		if (starterUserIds.isEmpty())
			return Map.of();
		UserDboExample userEx = new UserDboExample();
		userEx.createCriteria().andUserIdIn(starterUserIds);
		return userDao.get(userEx).stream().map(userMap::toModel)
				.collect(Collectors.toMap(User::getUserId, Function.identity()));
	}
	
	public Thread getThread(Integer threadId) {
		Optional<ThreadDbo> threadDb = threadDao.find(threadId);
		return threadDb.map(threadDbo -> {
			Thread result = threadMap.toModel(threadDbo);
			
			result.setBoardPermissions(getBoardPermissions(result.getBoardId()));
			
			MessageDboExample ex = new MessageDboExample();
			ex.createCriteria().andThreadIdEqualTo(threadId);
			long count = messageDao.count(ex);
			result.setPageCount((int)Math.ceil(count / (double) DEFAULT_MESSAGES_PER_PAGE));
			
			return result;
		}).orElseThrow(() -> new ZfgcNotFoundException());
	}
	
	public Thread saveThread(Thread thread) {
		ThreadDbo threadDbo = threadMap.toDbo(thread);
		
		threadDao.save(threadDbo);
		thread.setThreadId(threadDbo.getThreadId());

		if(thread.getPollInfo() != null) {
			PollDbo poll = pollMap.toDbo(thread.getPollInfo());
			pollDao.save(poll);
		}
		
		return getThread(thread.getId());
	}
	
	public List<Permission> getBoardPermissions(Integer boardId){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdEqualTo(boardId);
		return boardPermissionViewDao.get(bEx).stream().map(permissionMap::toModel).collect(Collectors.toList());
	}
	
	public Thread splitThread(ThreadSplit splitter, Thread newThread) {
		newThread.getMessages().clear();
		
		newThread.setThreadName(splitter.newThreadTitle());
		newThread = saveThread(newThread);
		messageDataProvider.moveMessagesToNewThread(splitter.messageIdsToMove(), splitter.threadId(),
				newThread.getId(), newThread.getBoardId());

		return newThread;
	}

	public void purgeOwnedPolls(Integer userId) {
		List<Integer> pollIds = userErasureDao.findOwnedPollIds(userId);
		if (!pollIds.isEmpty()) {
			userErasureDao.deleteUserPollVotes(userId);
			userErasureDao.deleteUserPollChoices(userId);
			deleteMigratorIdMapEntries("POLL", pollIds);
			PollDboExample ownedPollsExample = new PollDboExample();
			ownedPollsExample.createCriteria().andCreatedUserIdEqualTo(userId);
			pollDao.deleteWhere(ownedPollsExample);
		}
		deleteOwnVotesAndRecount(userId);
	}

	public void orphanOwnedPolls(Integer userId, Integer sentinelId) {
		List<Integer> pollIds = userErasureDao.findOwnedPollIds(userId);
		if (!pollIds.isEmpty()) {
			deleteMigratorIdMapEntries("POLL", pollIds);
			userErasureDao.reassignPolls(userId, sentinelId);
		}
		deleteOwnVotesAndRecount(userId);
	}

	private void deleteOwnVotesAndRecount(Integer userId) {
		List<Integer> votedChoiceIds = userErasureDao.findVotedPollChoiceIds(userId);
		UserPollChoiceDboExample ownVotesExample = new UserPollChoiceDboExample();
		ownVotesExample.createCriteria().andUserIdEqualTo(userId);
		userPollChoiceDao.deleteWhere(ownVotesExample);
		if (!votedChoiceIds.isEmpty())
			userErasureDao.recountPollChoiceVotes(votedChoiceIds);
	}

	public void purgeThreadsWithGc(Integer userId, Integer sentinelId) {
		List<Integer> ownedThreadIds = userErasureDao.findOwnedThreadIds(userId);
		if (!ownedThreadIds.isEmpty())
			deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
		gcThreadsEmptiedByDeletion(ownedThreadIds);
		userErasureDao.reassignThreads(userId, sentinelId);
	}

	public void gcThreadsEmptiedByDeletion(List<Integer> candidateThreadIds) {
		if (candidateThreadIds.isEmpty())
			return;
		List<Integer> emptyThreadIds = userErasureDao.findEmptyThreadIdsAmong(candidateThreadIds);
		if (emptyThreadIds.isEmpty())
			return;
		List<Integer> pollIds = userErasureDao.findPollIdsOnThreads(emptyThreadIds);
		if (!pollIds.isEmpty())
			deleteMigratorIdMapEntries("POLL", pollIds);
		userErasureDao.deleteVotesForPollsOnThreads(emptyThreadIds);
		userErasureDao.deleteChoicesForPollsOnThreads(emptyThreadIds);
		PollDboExample pollsOnThreadsExample = new PollDboExample();
		pollsOnThreadsExample.createCriteria().andThreadIdIn(emptyThreadIds);
		pollDao.deleteWhere(pollsOnThreadsExample);
		deleteMigratorIdMapEntries("THREAD", emptyThreadIds);
		userErasureDao.deleteThreadsByIds(emptyThreadIds);
	}

	public void orphanThreads(Integer userId, Integer sentinelId) {
		List<Integer> ownedThreadIds = userErasureDao.findOwnedThreadIds(userId);
		if (!ownedThreadIds.isEmpty())
			deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
		userErasureDao.reassignThreads(userId, sentinelId);
	}

	public int countOwnedThreads(Integer userId) {
		return userErasureDao.countOwnedThreads(userId);
	}

	public int countOwnedPolls(Integer userId) {
		PollDboExample ownedPollsExample = new PollDboExample();
		ownedPollsExample.createCriteria().andCreatedUserIdEqualTo(userId);
		return (int) pollDao.count(ownedPollsExample);
	}

	private void deleteMigratorIdMapEntries(String entityType, List<Integer> zfgbbIds) {
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(entityType).andZfgbbIdIn(zfgbbIds);
		migratorIdMapDao.deleteWhere(example);
	}
}
