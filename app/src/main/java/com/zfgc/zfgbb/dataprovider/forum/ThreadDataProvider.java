package com.zfgc.zfgbb.dataprovider.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.BoardDao;
import com.zfgc.zfgbb.dao.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.ThreadDao;
import com.zfgc.zfgbb.dao.forum.PollChoiceDao;
import com.zfgc.zfgbb.dao.forum.PollDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDbo;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDboExample;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.PollQuestionDbo;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.AllMessagesInThreadViewDboMapper;
import com.zfgc.zfgbb.mappers.LatestMessageInThreadViewDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.mapstruct.forum.PollMap;
import com.zfgc.zfgbb.mapstruct.forum.ThreadMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.Poll;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.ThreadSplit;
import com.zfgc.zfgbb.model.users.Permission;

@Repository
public class ThreadDataProvider {

	private static final int MAX_MESSAGES_PER_PAGE = 100;

	@Autowired
	private ThreadDao threadDao;
	
	@Autowired
	private MessageDataProvider messageDataProvider;
	
	@Autowired
	private BoardPermissionViewDao boardPermissionDao;

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private PollDao pollDao;
	
	@Autowired
	private PollChoiceDao pollChoiceDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LatestMessageInThreadViewDboMapper latestMessageMapper;
	
	@Autowired
	private AllMessagesInThreadViewDboMapper allMessagesMapper;
	
	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private MessagePostCountMapper messagePostCountMapper;
	
	@Autowired
	private PollMap pollMap;

	@Autowired
	private ThreadMap threadMap;

	@Autowired
	private UserMap userMap;

	@Autowired
	private PermissionMap permissionMap;

	public Thread getThread(Integer threadId, Integer page, Integer count) {
		int pageSize = (count == null || count < 1) ? 10 : Math.min(count, MAX_MESSAGES_PER_PAGE);
		Optional<ThreadDbo> threadDb = threadDao.get(threadId);
		return threadDb.map(threadDbo -> {
			Thread result = threadMap.toModel(threadDbo);

			MessageDboExample ex = new MessageDboExample();
			ex.createCriteria().andThreadIdEqualTo(threadId);
			long msgCount = messageMapper.countByExample(ex);

			List<Message> messages = (page == null || page <= 0)
					? messageDataProvider.getMessagesFrom(threadId, (int) Math.max(1, msgCount - pageSize + 1), pageSize)
					: messageDataProvider.getMessagesForThread(threadId, page, pageSize);
			result.setMessages(messages);

			result.setBoardPermissions(getBoardPermissions(result.getBoardId()));

			boardDao.get(result.getBoardId())
					.ifPresent(board -> result.setBoardName(board.getBoardName()));

			result.setPollInfo(getPollInfo(threadId));

			result.setPageCount((int)Math.ceil((double)msgCount / (double)pageSize));

			return result;
		}).orElseThrow(() -> new ZfgcNotFoundException());
	}
	
	public Poll getPollInfo(Integer threadId) {
		PollDboExample pollEx = new PollDboExample();
		pollEx.createCriteria().andThreadIdEqualTo(threadId);
		return pollDao.get(pollEx).stream()
						  .findFirst()
						  .map(poll -> {
							  	PollChoiceDboExample choiceEx = new PollChoiceDboExample();
							  	choiceEx.createCriteria().andActiveFlagEqualTo(true)
							  							 .andPollIdEqualTo(poll.getPollId());
							  	List<PollChoiceDbo> choices = pollChoiceDao.get(choiceEx);
							  	
							  	Poll result = pollMap.toModel(poll, choices);

								return result;
						  }).orElse(null);
	}
	
	public List<Thread> getThreadsByBoardId(Integer boardId, Integer pageNumber, Integer pageSize, Boolean sticky){
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
		latestMessageEx.setOrderByClause("last_post_ts desc");
		latestMessageEx.createCriteria().andBoardIdEqualTo(boardId).andPinnedFlagEqualTo(sticky);

		List<LatestMessageInThreadViewDbo> latestMessages = latestMessageMapper.selectByExampleWithLimits(latestMessageEx);
		List<Thread> result = latestMessages.stream().map(threadMap::toThread).collect(Collectors.toList());
		Map<Integer, LatestMessageInThreadViewDbo> messagesByThreadId = latestMessages.stream()
																					  .collect(Collectors.toMap(LatestMessageInThreadViewDbo::getThreadId, Function.identity()));
		latestMessages.clear();

		if (result.isEmpty()) {
			return result;
		}

		List<Integer> threadIds = result.stream().map(Thread::getThreadId).filter(Objects::nonNull).toList();

		Map<Integer, Integer> postCountsByThreadId = threadIds.isEmpty() ? Map.of() :
				messagePostCountMapper.postCountsByThreadIds(threadIds).stream()
						.collect(Collectors.toMap(MessagePostCountMapper.ThreadPostCount::getThreadId,
								MessagePostCountMapper.ThreadPostCount::getPostCount));

		Map<Integer, MessagePostCountMapper.LatestMessageUser> latestMessageUserByThreadId = threadIds.isEmpty() ? Map.of() :
				messagePostCountMapper.latestMessageUsersByThreadIds(threadIds).stream()
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
		Optional<ThreadDbo> threadDb = threadDao.get(threadId);
		return threadDb.map(threadDbo -> {
			Thread result = threadMap.toModel(threadDbo);
			
			result.setBoardPermissions(getBoardPermissions(result.getBoardId()));
			
			MessageDboExample ex = new MessageDboExample();
			ex.createCriteria().andThreadIdEqualTo(threadId);
			long count = messageMapper.countByExample(ex);
			result.setPageCount((int)Math.ceil(count / 10.0));
			
			return result;
		}).orElseThrow(() -> new ZfgcNotFoundException());
	}
	
	public Thread saveThread(Thread thread) {
		ThreadDbo threadDbo = threadMap.toDbo(thread);
		
		threadDbo = threadDao.save(threadDbo);
		thread.setThreadId(threadDbo.getThreadId());
		thread.setCreatedTs(threadDbo.getCreatedTs());
		thread.setUpdatedTs(threadDbo.getUpdatedTs());
		
		if(thread.getPollInfo() != null) {
			PollDbo poll = pollMap.toDbo(thread.getPollInfo());
			pollDao.save(poll);
		}
		
		return getThread(thread.getId());
	}
	
	public List<Permission> getBoardPermissions(Integer boardId){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdEqualTo(boardId);
		return boardPermissionDao.get(bEx).stream().map(permissionMap::toModel).collect(Collectors.toList());
	}
	
	public Thread splitThread(ThreadSplit splitter, Thread newThread) {
		newThread.getMessages().clear();
		
		newThread.setThreadName(splitter.getNewThreadTitle());
		newThread = saveThread(newThread);
		messageDataProvider.moveMessagesToNewThread(splitter.getMessageIdsToMove(), splitter.getThreadId(),
				newThread.getId(), newThread.getBoardId());

		return newThread;
	}
	
}
