package com.zfgc.zfgbb.migrator.converters.cms;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.Cancellable;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameCommentDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameCommentDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceCommentDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceCommentDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFGameCommentDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFResourceCommentDbMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentsConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(CommentsConverter.class);
	private static final int DELETED_USER_ID = 0;

	private final SMFGameCommentDbMapper smfGameCommentMapper;
	private final SMFResourceCommentDbMapper smfResourceCommentMapper;
	private final ContentEntityDboMapper contentEntityMapper;
	private final ThreadDboMapper threadMapper;
	private final MessageDboMapper messageMapper;
	private final MessageHistoryDboMapper messageHistoryMapper;
	private final MigratorTimestampMapper migratorTimestampMapper;
	private final MigratorIdMapService idMap;
	private final IpAddressDboMapper ipAddressMapper;

	@Override
	public JobType getType() {
		return JobType.CMS_COMMENTS;
	}

	private Map<String, Integer> ipIdByAddress;

	@Override
	@Transactional
	public Void convertToZfgbb() {
		Integer boardId = resolveDiscussionBoard();
		if (boardId == null) {
			logger.info("No discussionBoardId provided; skipping legacy comment migration");
			return null;
		}
		ipIdByAddress = ipAddressMapper.selectByExample(new IpAddressDboExample()).stream()
				.collect(Collectors.toMap(IpAddressDbo::getIp,
						IpAddressDbo::getIpAddressId, (firstId, duplicateId) -> firstId));
		convertGameComments(boardId);
		Integer legacyResourcesBoardId = JobContextHolder.getResourcesBoardId();
		Integer resourcesBoardId = idMap.lookupOrNull(LegacyEntityType.BOARD, legacyResourcesBoardId);
		if (legacyResourcesBoardId != null && resourcesBoardId == null) {
			throw new IllegalStateException("resourcesBoardId " + legacyResourcesBoardId
					+ " is not a migrated legacy board id");
		}
		convertResourceComments(resourcesBoardId != null ? resourcesBoardId : boardId);
		return null;
	}

	private Integer resolveDiscussionBoard() {
		Integer legacyBoardId = JobContextHolder.getDiscussionBoardId();
		if (legacyBoardId == null) {
			return null;
		}
		Integer boardId = idMap.lookupOrNull(LegacyEntityType.BOARD, legacyBoardId);
		if (boardId == null) {
			throw new IllegalStateException("discussionBoardId " + legacyBoardId
					+ " is not a migrated legacy board id");
		}
		return boardId;
	}

	private void convertGameComments(Integer boardId) {
		List<SMFGameCommentDbWithBLOBs> comments =
				smfGameCommentMapper.selectByExampleWithBLOBs(new SMFGameCommentDbExample());
		comments.sort(Comparator.comparing(SMFGameCommentDbWithBLOBs::getPosttime)
				.thenComparing(SMFGameCommentDbWithBLOBs::getIdComment));
		Map<Integer, List<SMFGameCommentDbWithBLOBs>> byGame = comments.stream()
				.collect(Collectors.groupingBy(SMFGameCommentDbWithBLOBs::getIdGame,
						LinkedHashMap::new, Collectors.toList()));

		byGame.forEach((idGame, gameComments) -> {
			Cancellable.check();
			Integer projectId = idMap.lookupOrNull(LegacyEntityType.GAME, idGame);
			ContentEntityDbo project = projectId == null ? null : contentEntityMapper.selectByPrimaryKey(projectId);
			if (project == null) {
				logger.warn("game {} comments reference an unmigrated project; skipping {} comments",
						idGame, gameComments.size());
				return;
			}
			Integer threadId = project.getThreadId();
			if (threadId == null) {
				threadId = createThread(boardId, project.getTitle(), "gamecomments" + idGame,
						SmfTimes.fromEpochSeconds(gameComments.get(0).getPosttime()),
						LegacyEntityType.GAME_COMMENT, gameComments.get(0).getIdComment(),
						gameComments.get(0).getIdMember());
				project.setThreadId(threadId);
				contentEntityMapper.updateByPrimaryKey(project);
			} else {
				refreshThreadAuthor(threadId, "gamecomments" + idGame, LegacyEntityType.GAME_COMMENT,
						gameComments.get(0).getIdComment(), gameComments.get(0).getIdMember());
			}
			Integer threadBoardId = resolveThreadBoard(threadId);
			AtomicInteger nextPost = new AtomicInteger(countThreadMessages(threadId) + 1);
			for (SMFGameCommentDbWithBLOBs comment : gameComments) {
				appendComment(LegacyEntityType.GAME_COMMENT, comment.getIdComment(), threadId, threadBoardId,
						comment.getIdMember(), comment.getBody(), comment.getPosttime(),
						comment.getPostip(), nextPost);
			}
		});
	}

	private void convertResourceComments(Integer boardId) {
		List<SMFResourceCommentDbWithBLOBs> comments =
				smfResourceCommentMapper.selectByExampleWithBLOBs(new SMFResourceCommentDbExample());
		comments.sort(Comparator.comparing(SMFResourceCommentDbWithBLOBs::getPosttime)
				.thenComparing(SMFResourceCommentDbWithBLOBs::getIdComment));
		Map<Integer, List<SMFResourceCommentDbWithBLOBs>> byResource = comments.stream()
				.collect(Collectors.groupingBy(SMFResourceCommentDbWithBLOBs::getIdResource,
						LinkedHashMap::new, Collectors.toList()));

		byResource.forEach((idResource, resourceComments) -> {
			Cancellable.check();
			ContentEntityDbo resource = resolveResource(idResource);
			if (resource == null) {
				logger.warn("resource {} comments reference an unmigrated resource; skipping {} comments",
						idResource, resourceComments.size());
				return;
			}
			Integer threadId = resource.getThreadId();
			if (threadId == null) {
				threadId = createThread(boardId, resource.getTitle(), "resourcecomments" + idResource,
						SmfTimes.fromEpochSeconds(resourceComments.get(0).getPosttime()),
						LegacyEntityType.RESOURCE_COMMENT, resourceComments.get(0).getIdComment(),
						resourceComments.get(0).getIdMember());
				resource.setThreadId(threadId);
				contentEntityMapper.updateByPrimaryKey(resource);
			} else {
				refreshThreadAuthor(threadId, "resourcecomments" + idResource, LegacyEntityType.RESOURCE_COMMENT,
						resourceComments.get(0).getIdComment(), resourceComments.get(0).getIdMember());
			}
			Integer threadBoardId = resolveThreadBoard(threadId);
			AtomicInteger nextPost = new AtomicInteger(countThreadMessages(threadId) + 1);
			for (SMFResourceCommentDbWithBLOBs comment : resourceComments) {
				appendComment(LegacyEntityType.RESOURCE_COMMENT, comment.getIdComment(), threadId, threadBoardId,
						comment.getIdMember(), comment.getBody(), comment.getPosttime(),
						comment.getPostip(), nextPost);
			}
		});
	}

	private ContentEntityDbo resolveResource(Integer idResource) {
		Integer resourceId = idMap.lookupOrNull(LegacyEntityType.RESOURCE, idResource);
		if (resourceId == null) {
			resourceId = idMap.lookupOrNull(LegacyEntityType.SMF_RESOURCE, idResource);
		}
		return resourceId == null ? null : contentEntityMapper.selectByPrimaryKey(resourceId);
	}

	private Integer createThread(Integer boardId, String title, String hashKey, OffsetDateTime startedTs,
			LegacyEntityType sourceType, Integer sourceId, Integer legacyStarterId) {
		ThreadDbo thread = new ThreadDbo();
		thread.setBoardId(boardId);
		thread.setThreadName(title);
		thread.setCreatedUserId(resolveAuthorId(sourceType, sourceId, legacyStarterId));
		thread.setLockedFlag(false);
		thread.setPinnedFlag(false);
		thread.setViewCount(0);
		thread.setMigrationHash(MigrationHasher.hash(hashKey));
		threadMapper.insert(thread);
		if (startedTs != null) {
			migratorTimestampMapper.setThreadTimestamps(thread.getThreadId(), startedTs, startedTs);
		}
		return thread.getThreadId();
	}

	private int countThreadMessages(Integer threadId) {
		MessageDboExample ex = new MessageDboExample();
		ex.createCriteria().andThreadIdEqualTo(threadId);
		return (int) messageMapper.countByExample(ex);
	}

	private Integer resolveThreadBoard(Integer threadId) {
		ThreadDbo thread = threadMapper.selectByPrimaryKey(threadId);
		return thread == null ? null : thread.getBoardId();
	}

	private void appendComment(LegacyEntityType entityType, Integer legacyCommentId, Integer threadId,
			Integer threadBoardId, Integer legacyMemberId, String body, Integer postTime, String postIp,
			AtomicInteger nextPost) {
		var existingMessageId = idMap.find(entityType, legacyCommentId);
		if (existingMessageId.isPresent()) {
			if (JobContextHolder.isForce()) {
				Integer authorId = resolveAuthorId(entityType, legacyCommentId, legacyMemberId);
				MessageDbo existing = messageMapper.selectByPrimaryKey(existingMessageId.get());
				if (existing != null && !Objects.equals(existing.getOwnerId(), authorId)) {
					MessageDbo update = new MessageDbo();
					update.setMessageId(existingMessageId.get());
					update.setOwnerId(authorId);
					messageMapper.updateByPrimaryKeySelective(update);
				}
			}
			return;
		}
		OffsetDateTime postedTs = SmfTimes.fromEpochSeconds(postTime);
		Integer authorId = resolveAuthorId(entityType, legacyCommentId, legacyMemberId);

		MessageDbo message = new MessageDbo();
		message.setOwnerId(authorId);
		message.setThreadId(threadId);
		message.setBoardId(threadBoardId);
		message.setPostInThread(nextPost.getAndIncrement());
		message.setMigrationHash(MigrationHasher.hash(entityType + "" + legacyCommentId
				+ legacyMemberId + postTime + body));
		messageMapper.insert(message);
		idMap.record(entityType, legacyCommentId, message.getMessageId());

		MessageHistoryDbo history = new MessageHistoryDbo();
		history.setMessageId(message.getMessageId());
		history.setMessageText(HtmlUtils.htmlUnescape(body));
		history.setContentFormat(ContentFormat.BBCODE.name());
		history.setCurrentFlag(true);
		String ip = postIp == null || postIp.isBlank() ? "127.0.0.1" : postIp.trim();
		history.setIpAddressId(ipIdByAddress.computeIfAbsent(ip,
				address -> CmsSupport.ensureIpAddress(ipAddressMapper, address)));
		messageHistoryMapper.insert(history);

		if (postedTs != null) {
			migratorTimestampMapper.setMessageTimestamps(message.getMessageId(), postedTs, postedTs);
			migratorTimestampMapper.setMessageHistoryTimestamps(history.getMessageHistoryId(), postedTs, postedTs);
		}
	}

	private void refreshThreadAuthor(Integer threadId, String migrationHashKey, LegacyEntityType sourceType,
			Integer sourceId, Integer legacyMemberId) {
		if (!JobContextHolder.isForce())
			return;
		ThreadDbo existing = threadMapper.selectByPrimaryKey(threadId);
		if (existing == null
				|| !Objects.equals(existing.getMigrationHash(), MigrationHasher.hash(migrationHashKey)))
			return;
		Integer authorId = resolveAuthorId(sourceType, sourceId, legacyMemberId);
		if (!Objects.equals(existing.getCreatedUserId(), authorId)) {
			ThreadDbo update = new ThreadDbo();
			update.setThreadId(threadId);
			update.setCreatedUserId(authorId);
			threadMapper.updateByPrimaryKeySelective(update);
		}
	}

	private Integer resolveAuthorId(LegacyEntityType sourceType, Integer sourceId, Integer legacyMemberId) {
		if (legacyMemberId == null || legacyMemberId == 0)
			return DELETED_USER_ID;
		return idMap.find(LegacyEntityType.USER, legacyMemberId).orElseGet(() -> {
			logger.warn("{} {} references missing legacy user {}; assigning deleted user",
					sourceType, sourceId, legacyMemberId);
			return DELETED_USER_ID;
		});
	}
}
