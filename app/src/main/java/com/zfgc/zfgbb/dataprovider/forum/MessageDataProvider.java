package com.zfgc.zfgbb.dataprovider.forum;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dao.meta.IpAddressDao;
import com.zfgc.zfgbb.dao.forum.PersonalMessageDao;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import com.zfgc.zfgbb.dao.forum.PersonalMessageRecipientDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dataprovider.cms.CatalogDataProvider;
import com.zfgc.zfgbb.dataprovider.reactions.ReactionDataProvider;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.forum.MessageHistoryDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.forum.CurrentMessageDao;
import com.zfgc.zfgbb.dao.forum.FileAttachmentDao;
import com.zfgc.zfgbb.mapstruct.forum.MessageHistoryMap;
import com.zfgc.zfgbb.mapstruct.forum.MessageMap;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.model.forum.FileAttachment;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.MessageHistory;
import com.zfgc.zfgbb.model.forum.MessagePosition;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class MessageDataProvider {

	private final MessageDao messageDao;

	private final IpAddressDao ipAddressDao;

	private final PersonalMessageRecipientDao personalMessageRecipientDao;

	private final PersonalMessageDao personalMessageDao;

	private final MigratorIdMapDao migratorIdMapDao;

	private final CatalogDataProvider catalogDataProvider;

	private final ReactionDataProvider reactionDataProvider;

	private final MessageHistoryDao messageHistoryDao;

	private final ThreadDao threadDao;

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final CurrentMessageDao currentMessageDao;

	private final UserDataProvider userDataProvider;

	private final FileAttachmentDao fileAttachmentDao;

	private final ContentResourceDao contentResourceDao;

	private final MessageMap messageMap;

	private final MessageHistoryMap messageHistoryMap;

	private static final int MESSAGE_ID_QUERY_CHUNK_SIZE = 1000;

	public Optional<MessagePosition> findMessagePosition(Integer messageId) {
		return messageDao.find(messageId).map(message -> new MessagePosition(message.getMessageId(),
				message.getOwnerId(), message.getThreadId(), message.getPostInThread()));
	}

	public Optional<ContentFormat> currentRevisionContentFormat(Integer messageId) {
		MessageHistoryDboExample example = new MessageHistoryDboExample();
		example.createCriteria().andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true);
		return messageHistoryDao.getOne(example)
				.flatMap(revision -> ContentFormat.parse(revision.getContentFormat()));
	}

	public Map<Integer, OffsetDateTime> currentRevisionCreatedTsByMessageId(List<Integer> messageIds) {
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

	public Message saveMessage(Message message) {
		MessageDbo messageDbo = messageMap.toDbo(message);
		ensureBoardId(messageDbo);

		messageDao.save(messageDbo);

		MessageHistory history = message.getCurrentMessage();
		history.setMessageId(messageDbo.getMessageId());
		MessageHistoryDbo historyDbo = messageHistoryMap.toDbo(history);
		if (historyDbo.getCurrentFlag() == null)
			historyDbo.setCurrentFlag(true);
		messageHistoryDao.save(historyDbo);

		Message result = messageMap.toModel(reload(messageDbo));
		result.setCurrentMessage(messageHistoryMap.toModel(reload(historyDbo)));

		return result;
	}

	private MessageDbo reload(MessageDbo message) {
		return messageDao.find(message.getMessageId()).orElseThrow(ZfgcNotFoundException::new);
	}

	private MessageHistoryDbo reload(MessageHistoryDbo revision) {
		return messageHistoryDao.find(revision.getMessageHistoryId())
				.orElseThrow(ZfgcNotFoundException::new);
	}

	public Message editMessage(Integer messageId, MessageHistory revision) {
		MessageDbo existing = messageDao.find(messageId).orElseThrow(ZfgcNotFoundException::new);
		messageHistoryDao.clearCurrentFlag(messageId);
		revision.setMessageId(messageId);
		revision.setCurrentFlag(true);
		MessageHistoryDbo saved = messageHistoryDao.save(messageHistoryMap.toDbo(revision));
		Message result = messageMap.toModel(existing);
		result.setCurrentMessage(messageHistoryMap.toModel(reload(saved)));
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
		List<FileAttachmentDbo> attachmentDbos = fileAttachmentDao.get(faEx);
		if (attachmentDbos.isEmpty()) return;

		List<Integer> resourceIds = attachmentDbos.stream()
				.map(FileAttachmentDbo::getContentResourceId).distinct().toList();
		ContentResourceDboExample crEx = new ContentResourceDboExample();
		crEx.createCriteria().andContentResourceIdIn(resourceIds);
		Map<Integer, ContentResourceDbo> resourceById = contentResourceDao.get(crEx).stream()
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
		User author = userDataProvider.findUser(ownerId, UserLoadOptions.publicProfile()).orElseGet(User::orphaned);
		author.retainPublicRankPermissions();
		return author;
	}
	
	public void reparentMessage(Integer messageId, Integer threadId, Integer boardId, Integer postInThread) {
		MessageDbo reparented = new MessageDbo();
		reparented.setThreadId(threadId);
		reparented.setBoardId(boardId);
		reparented.setPostInThread(postInThread);

		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andMessageIdEqualTo(messageId);
		messageDao.updateWhere(reparented, ex);
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
		return messageDao.updateWhere(threadAssignment, ex);
	}

	public void updateBoardIdForThread(Integer threadId, Integer boardId) {
		MessageDbo boardAssignment = new MessageDbo();
		boardAssignment.setBoardId(boardId);

		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(threadId);
		messageDao.updateWhere(boardAssignment, ex);
	}

	private void ensureBoardId(MessageDbo messageDbo) {
		if (messageDbo.getBoardId() != null || messageDbo.getThreadId() == null)
			return;
		threadDao.find(messageDbo.getThreadId())
				.ifPresent(threadDbo -> messageDbo.setBoardId(threadDbo.getBoardId()));
	}
	
	public List<Message> getMessagesByUser(Integer userId, Integer pageNumber, Integer count, List<Integer> permissionIds){
		int resolvedPageNumber = pageNumber == null || pageNumber < 1 ? 1 : pageNumber;
		int pageSize = count == null || count < 1 ? 25 : Math.min(count, 50);

		long offset = (long) (resolvedPageNumber - 1) * pageSize;
		if (offset > Integer.MAX_VALUE) {
			return List.of();
		}

		List<Integer> visibleBoardIds = visibleBoardIds(permissionIds);
		if (visibleBoardIds.isEmpty())
			return List.of();

		MessageDboExample pageExample = new MessageDboExample();
		pageExample.createCriteria().andOwnerIdEqualTo(userId).andBoardIdIn(visibleBoardIds);
		pageExample.setOrderByClause("created_ts desc");
		pageExample.setLimit(pageSize);
		pageExample.setOffset((int) offset);
		List<Integer> pageMessageIds = messageDao.get(pageExample).stream()
				.map(MessageDbo::getMessageId).toList();
		if (pageMessageIds.isEmpty())
			return List.of();

		CurrentMessageDboExample bodyExample = new CurrentMessageDboExample();
		bodyExample.createCriteria().andMessageIdIn(pageMessageIds);
		Map<Integer, CurrentMessageDbo> bodiesByMessageId = currentMessageDao.get(bodyExample).stream()
				.collect(Collectors.toMap(CurrentMessageDbo::getMessageId, Function.identity()));
		Map<Integer, User> ownerCache = new HashMap<>();
		return pageMessageIds.stream()
				.map(bodiesByMessageId::get)
				.filter(Objects::nonNull)
				.map(dbo -> mapMessage(dbo, ownerCache))
				.toList();
	}

	private List<Integer> visibleBoardIds(List<Integer> permissionIds) {
		if (permissionIds == null || permissionIds.isEmpty())
			return List.of();
		BoardPermissionViewDboExample visibleExample = new BoardPermissionViewDboExample();
		visibleExample.createCriteria().andPermissionIdIn(permissionIds);
		return boardPermissionViewDao.get(visibleExample).stream()
				.map(BoardPermissionViewDbo::getBoardId).distinct().toList();
	}

	public List<Integer> findOwnedMessageIds(Integer userId, int chunkSize) {
		return messageDao.findOwnedMessageIds(userId, chunkSize);
	}

	public int countOwnedMessages(Integer userId) {
		return messageDao.countOwnedMessages(userId);
	}

	public List<Integer> findThreadIdsForMessages(List<Integer> messageIds) {
		return messageDao.findThreadIdsForMessages(messageIds);
	}

	public void resequencePostInThread(List<Integer> threadIds) {
		if (!threadIds.isEmpty())
			messageDao.resequencePostInThread(threadIds);
	}

	public List<ReleasedResource> purgeMessagesByIds(List<Integer> messageIds) {
		if (messageIds.isEmpty())
			return List.of();
		messageHistoryDao.deleteAttachmentRefRewritesForMessages(messageIds);
		List<Integer> attachmentIds = fileAttachmentDao.findAttachmentIdsForMessages(messageIds);
		List<Integer> releasedResourceIds = fileAttachmentDao.findAttachmentContentResourceIds(messageIds);
		FileAttachmentDboExample attachmentsExample = new FileAttachmentDboExample();
		attachmentsExample.createCriteria().andMessageIdIn(messageIds);
		fileAttachmentDao.deleteWhere(attachmentsExample);
		if (!attachmentIds.isEmpty())
			deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
		List<Integer> candidateIpIds = messageHistoryDao.findHistoryIpAddressIds(messageIds);
		messageHistoryDao.deleteHistoryForMessages(messageIds);
		if (!candidateIpIds.isEmpty())
			ipAddressDao.deleteUnreferencedIpAddresses(candidateIpIds);
		reactionDataProvider.deleteReactions("MESSAGE", messageIds);
		deleteMigratorIdMapEntries("MESSAGE", messageIds);
		messageDao.deleteMessagesByIds(messageIds);
		return catalogDataProvider.deleteContentResourcesIfUnreferenced(releasedResourceIds);
	}

	public int orphanOwnedMessagesBatch(Integer userId, Integer sentinelId, int chunkSize) {
		List<Integer> messageIds = messageDao.findOwnedMessageIds(userId, chunkSize);
		if (messageIds.isEmpty())
			return 0;
		List<Integer> candidateIpIds = messageHistoryDao.findHistoryIpAddressIds(messageIds);
		messageHistoryDao.scrubHistoryForMessages(messageIds);
		if (!candidateIpIds.isEmpty())
			ipAddressDao.deleteUnreferencedIpAddresses(candidateIpIds);
		List<Integer> attachmentIds = fileAttachmentDao.findAttachmentIdsForMessages(messageIds);
		if (!attachmentIds.isEmpty())
			deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
		fileAttachmentDao.scrubAttachmentMigrationHashesForMessages(messageIds);
		deleteMigratorIdMapEntries("MESSAGE", messageIds);
		messageDao.reassignAndScrubMessages(messageIds, sentinelId);
		return messageIds.size();
	}

	public int countSentPersonalMessages(Integer userId) {
		PersonalMessageDboExample sentPersonalMessagesExample = new PersonalMessageDboExample();
		sentPersonalMessagesExample.createCriteria().andSenderUserIdEqualTo(userId);
		return (int) personalMessageDao.count(sentPersonalMessagesExample);
	}

	public void purgePersonalMessages(Integer userId) {
		List<Integer> conversationIds = personalMessageDao.findParticipantConversationIds(userId);
		personalMessageDao.scrubSentPersonalMessages(userId);
		PersonalMessageRecipientDboExample recipientExample = new PersonalMessageRecipientDboExample();
		recipientExample.createCriteria().andRecipientUserIdEqualTo(userId);
		personalMessageRecipientDao.deleteWhere(recipientExample);
		if (conversationIds.isEmpty())
			return;
		personalMessageDao.gcEmptyConversationsAmong(conversationIds);
	}

	private void deleteMigratorIdMapEntries(String entityType, List<Integer> zfgbbIds) {
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(entityType).andZfgbbIdIn(zfgbbIds);
		migratorIdMapDao.deleteWhere(example);
	}
}
