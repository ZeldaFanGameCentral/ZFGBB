package com.zfgc.zfgbb.dataprovider.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.zfgc.zfgbb.config.loadoption.user.PublicUserLoadOptions;
import com.zfgc.zfgbb.dao.ThreadDao;
import com.zfgc.zfgbb.dao.forum.CurrentMessageDao;
import com.zfgc.zfgbb.dao.forum.FileAttachmentsDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.forum.MessageHistoryDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.custom.SearchQueryMapper;
import com.zfgc.zfgbb.mapstruct.forum.MessageHistoryMap;
import com.zfgc.zfgbb.mapstruct.forum.MessageMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.forum.FileAttachment;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.MessageHistory;

@Repository
public class MessageDataProvider {
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private MessageHistoryDao messageHistoryDao;

	@Autowired
	private ThreadDao threadDao;

	@Autowired
	private CurrentMessageDao currentMessageDao;
	
	@Autowired
	private UserDataProvider userDataProvider;

	@Autowired
	private FileAttachmentsDao fileAttachmentsDao;

	@Autowired
	private ContentResourceDboMapper contentResourceMapper;

	@Autowired
	private SearchQueryMapper searchQueryMapper;

	@Autowired
	private MessageMap messageMap;

	@Autowired
	private MessageHistoryMap messageHistoryMap;

	public Message getMessage(Integer messageId) {
		Message message = messageMap.toModel(messageDao.get(messageId).orElseThrow(ZfgcNotFoundException::new));
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true);
		List<MessageHistory> history = messageHistoryDao.get(ex).stream().map(messageHistoryMap::toModel).collect(Collectors.toList());
		message.setCurrentMessage(history.get(0));
		
		return message;
	}
	
	public Message saveMessage(Message message) {
		MessageDbo messageDbo = messageMap.toDbo(message);
		ensureBoardId(messageDbo);

		messageDbo = messageDao.save(messageDbo);

		MessageHistory history = message.getCurrentMessage();
		history.setMessageId(messageDbo.getMessageId());
		MessageHistoryDbo historyDbo = messageHistoryMap.toDbo(history);
		if (historyDbo.getCurrentFlag() == null)
			historyDbo.setCurrentFlag(true);
		historyDbo = messageHistoryDao.save(historyDbo);
		
		Message result = messageMap.toModel(messageDbo);
		result.setCurrentMessage(messageHistoryMap.toModel(historyDbo));

		return result;
	}
	
	public List<Message> getMessagesForThread(Integer threadId, Integer page, Integer count){
		return getMessagesFrom(threadId, ((page - 1) * count) + 1, count);
	}

	public List<Message> getMessagesFrom(Integer threadId, Integer start, Integer count){
		CurrentMessageDboExample ex = new CurrentMessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(threadId)
						   .andPostInThreadBetween(start, start + count - 1);
		ex.setOrderByClause("post_in_thread asc");

		List<CurrentMessageDbo> currentMessageDbos = currentMessageDao.get(ex);
		List<Integer> ownerIds = currentMessageDbos.stream()
				.map(CurrentMessageDbo::getOwnerId)
				.filter(ownerId -> ownerId != null)
				.distinct()
				.toList();
		Map<Integer, User> ownerCache = new HashMap<>(userDataProvider.findPublicAuthorsByIds(ownerIds));

		List<Message> messages = currentMessageDbos
						 .stream()
						 .map(dbo -> mapMessage(dbo, ownerCache)).toList();

		hydrateAttachments(messages);
		return messages;
	}

	private void hydrateAttachments(List<Message> messages) {
		List<Integer> messageIds = messages.stream()
				.map(Message::getMessageId)
				.filter(id -> id != null)
				.toList();
		if (messageIds.isEmpty()) return;

		FileAttachmentDboExample faEx = new FileAttachmentDboExample();
		faEx.createCriteria().andMessageIdIn(messageIds);
		List<FileAttachmentDbo> attachmentDbos = fileAttachmentsDao.get(faEx);
		if (attachmentDbos.isEmpty()) return;

		List<Integer> resourceIds = attachmentDbos.stream()
				.map(FileAttachmentDbo::getContentResourceId).distinct().toList();
		ContentResourceDboExample crEx = new ContentResourceDboExample();
		crEx.createCriteria().andContentResourceIdIn(resourceIds);
		Map<Integer, ContentResourceDbo> resourceById = contentResourceMapper.selectByExample(crEx).stream()
				.collect(Collectors.toMap(ContentResourceDbo::getContentResourceId, r -> r));

		Map<Integer, List<FileAttachment>> byMessageId = attachmentDbos.stream()
				.filter(attachmentDbo -> {
					ContentResourceDbo resourceDbo = resourceById.get(attachmentDbo.getContentResourceId());
					return resourceDbo == null || resourceDbo.getFilename() == null
							|| !resourceDbo.getFilename().endsWith("_thumb");
				})
				.collect(Collectors.groupingBy(FileAttachmentDbo::getMessageId,
						Collectors.mapping(attachmentDbo -> {
							ContentResourceDbo resourceDbo = resourceById.get(attachmentDbo.getContentResourceId());
							return FileAttachment.builder()
									.fileAttachmentId(attachmentDbo.getFileAttachmentId())
									.contentResourceId(attachmentDbo.getContentResourceId())
									.filename(Optional.ofNullable(resourceDbo).map(ContentResourceDbo::getFilename))
									.mimeType(Optional.ofNullable(resourceDbo).map(ContentResourceDbo::getMimeType))
									.fileSize(Optional.ofNullable(resourceDbo).map(ContentResourceDbo::getFileSize))
									.downloads(Optional.ofNullable(attachmentDbo.getDownloads()).orElse(0))
									.build();
						}, Collectors.toList())));

		messages.forEach(m -> m.setFileAttachments(
				byMessageId.getOrDefault(m.getMessageId(), Collections.emptyList())));
	}
	
	private Message mapMessage(CurrentMessageDbo currentMessageDbo, Map<Integer, User> ownerCache){

		 MessageHistory hist = messageHistoryMap.toModel(currentMessageDbo);
		 hist.setUnparsedText(hist.getMessageText());

		 User createdUser = ownerCache.computeIfAbsent(currentMessageDbo.getOwnerId(),
				 this::loadPublicAuthor);

		 return messageMap.toModel(currentMessageDbo, createdUser)
		 		   .toBuilder()
		 		   .currentMessage(hist)
		 		   .build();
	}

	private User loadPublicAuthor(Integer ownerId) {
		User author = userDataProvider.findUser(ownerId, new PublicUserLoadOptions()).orElseGet(User::orphaned);
		author.retainPublicRankPermissions();
		if (author.getBioInfo() != null) {
			author.getBioInfo().setSignature(null);
		}
		return author;
	}
	
	public Message postMessageToThread(Integer threadId, Message message) {
		Preconditions.checkNotNull(message, "message cannot be null.");
		Preconditions.checkNotNull(message.getCurrentMessage(), "message history cannot be null.");
		Preconditions.checkNotNull(threadId, "threadId cannot be null.");
		//ensure we have the right thread set
		message.setThreadId(threadId);
		MessageDbo messageDbo = messageMap.toDbo(message);
		ensureBoardId(messageDbo);

		//insert a message history record
		MessageHistoryDbo historyDbo = messageHistoryMap.toDbo(message.getCurrentMessage());

		historyDbo = messageHistoryDao.save(historyDbo);

		Message result = messageMap.toModel(messageDao.save(messageDbo));
		result.setCurrentMessage(messageHistoryMap.toModel(historyDbo));
		
		return result;
	}
	
	public Message editMessage(Message message) {
		Preconditions.checkNotNull(message, "message cannot be null.");
		Preconditions.checkNotNull(message.getCurrentMessage(), "message history cannot be null.");
		
		MessageHistoryDbo historyDbo = messageHistoryMap.toDbo(message);
		messageHistoryDao.save(historyDbo);
		
		return getMessage(message.getMessageId());
	}
	
	public void reparentMessage(Integer messageId, Integer threadId, Integer boardId, Integer postInThread) {
		MessageDbo reparented = new MessageDbo();
		reparented.setThreadId(threadId);
		reparented.setBoardId(boardId);
		reparented.setPostInThread(postInThread);

		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andMessageIdEqualTo(messageId);
		messageDao.getMapper().updateByExampleSelective(reparented, ex);
	}

	public int moveMessagesToNewThread(List<Integer> messageIds, Integer sourceThreadId, Integer newThreadId,
			Integer newBoardId) {
		if (messageIds == null || messageIds.isEmpty())
			return 0;

		MessageDbo threadAssignment = new MessageDbo();
		threadAssignment.setThreadId(newThreadId);
		threadAssignment.setBoardId(newBoardId);

		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(sourceThreadId).andMessageIdIn(messageIds);
		return messageDao.getMapper().updateByExampleSelective(threadAssignment, ex);
	}

	public void updateBoardIdForThread(Integer threadId, Integer boardId) {
		MessageDbo boardAssignment = new MessageDbo();
		boardAssignment.setBoardId(boardId);

		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(threadId);
		messageDao.getMapper().updateByExampleSelective(boardAssignment, ex);
	}

	private void ensureBoardId(MessageDbo messageDbo) {
		if (messageDbo.getBoardId() != null || messageDbo.getThreadId() == null)
			return;
		threadDao.get(messageDbo.getThreadId())
				.ifPresent(threadDbo -> messageDbo.setBoardId(threadDbo.getBoardId()));
	}
	
	public Long getTotalPostsInThread(Integer threadId) {
		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(threadId);
		Long count = messageDao.getMapper().countByExample(ex);
		
		return count;
	}
	
	public List<Message> getMessagesByUser(Integer userId, Integer pageNumber, Integer count, List<Integer> permissionIds){
		int resolvedPageNumber = pageNumber == null || pageNumber < 1 ? 1 : pageNumber;
		int pageSize = count == null || count < 1 ? 25 : Math.min(count, 50);
		List<Integer> perms = permissionIds == null || permissionIds.isEmpty() ? List.of(-1) : permissionIds;

		long offset = (long) (resolvedPageNumber - 1) * pageSize;
		if (offset > Integer.MAX_VALUE) {
			return List.of();
		}

		Map<Integer, User> ownerCache = new HashMap<>();
		return searchQueryMapper.messagesByUser(userId, perms, pageSize, (int) offset)
						 .stream()
						 .map(dbo -> mapMessage(dbo, ownerCache)).toList();
	}
}
