package com.zfgc.zfgbb.migrator.converters.cms;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ThreadDbo;
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

@Component
public class CommentsConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(CommentsConverter.class);

	@Override
	public JobType getType() {
		return JobType.CMS_COMMENTS;
	}

	@Autowired
	private SMFGameCommentDbMapper smfGameCommentMapper;

	@Autowired
	private SMFResourceCommentDbMapper smfResourceCommentMapper;

	@Autowired
	private ContentEntityDboMapper contentEntityMapper;

	@Autowired
	private ThreadDboMapper threadMapper;

	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private MessageHistoryDboMapper messageHistoryMapper;

	@Autowired
	private MigratorTimestampMapper migratorTimestampMapper;

	@Autowired
	private MigratorIdMapService idMap;

	@Autowired
	private com.zfgc.zfgbb.mappers.IpAddressDboMapper ipAddressMapper;

	private Map<String, Integer> ipIdByAddress;

	@Override
	@Transactional
	public Void convertToZfgbb() {
		Integer boardId = resolveDiscussionBoard();
		if (boardId == null) {
			logger.info("No discussionBoardId provided; skipping legacy comment migration");
			return null;
		}
		ipIdByAddress = ipAddressMapper.selectByExample(new com.zfgc.zfgbb.dbo.IpAddressDboExample()).stream()
				.collect(Collectors.toMap(com.zfgc.zfgbb.dbo.IpAddressDbo::getIp,
						com.zfgc.zfgbb.dbo.IpAddressDbo::getIpAddressId, (firstId, duplicateId) -> firstId));
		convertGameComments(boardId);
		Integer resourcesBoardId = idMap.lookupOrNull(LegacyEntityType.BOARD,
				JobContextHolder.getResourcesBoardId());
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
			logger.warn("discussionBoardId {} does not resolve to a migrated board", legacyBoardId);
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
						java.util.LinkedHashMap::new, Collectors.toList()));

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
						gameComments.get(0).getIdMember());
				project.setThreadId(threadId);
				contentEntityMapper.updateByPrimaryKey(project);
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
						java.util.LinkedHashMap::new, Collectors.toList()));

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
						resourceComments.get(0).getIdMember());
				resource.setThreadId(threadId);
				contentEntityMapper.updateByPrimaryKey(resource);
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
			Integer legacyStarterId) {
		ThreadDbo thread = new ThreadDbo();
		thread.setBoardId(boardId);
		thread.setThreadName(title);
		thread.setCreatedUserId(legacyStarterId == null || legacyStarterId == 0
				? null
				: idMap.lookupOrNull(LegacyEntityType.USER, legacyStarterId));
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
		if (idMap.lookupOrNull(entityType, legacyCommentId) != null) {
			return;
		}
		OffsetDateTime postedTs = SmfTimes.fromEpochSeconds(postTime);

		MessageDbo message = new MessageDbo();
		message.setOwnerId(legacyMemberId == null || legacyMemberId == 0
				? null
				: idMap.lookupOrNull(LegacyEntityType.USER, legacyMemberId));
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
}
