package com.zfgc.zfgbb.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class ForumViewIntegrationTest extends PostgresIntegrationTest {

	@Autowired private RecentActivityViewDboMapper recentActivityMapper;
	@Autowired private AttachmentBoardViewDboMapper attachmentBoardMapper;
	@Autowired private BoardDboMapper boardDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private MessageDboMapper messageDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private FileAttachmentDboMapper fileAttachmentDboMapper;

	private String adminToken;

	@BeforeEach
	void loginAsAdmin() throws Exception {
		adminToken = getAdminToken();
	}

	@Test
	void recentActivityViewPreservesLatestPerThreadBoardFilteringAndTopNOrder() throws Exception {
		int firstThread = postThread(adminToken, "View first " + suffix, "first original");
		postReply(adminToken, firstThread, "first latest");
		int secondThread = postThread(adminToken, "View second " + suffix, "second original");
		postReply(adminToken, secondThread, "second latest");

		Integer firstBoard = boardId(firstThread);
		BoardDboExample boardEx = new BoardDboExample();
		boardEx.createCriteria().andBoardIdNotEqualTo(firstBoard);
		boardEx.setOrderByClause("board_id limit 1");
		Integer secondBoard = boardDboMapper.selectByExample(boardEx).get(0).getBoardId();

		ThreadDbo tUpdate = new ThreadDbo();
		tUpdate.setThreadId(secondThread);
		tUpdate.setBoardId(secondBoard);
		threadDboMapper.updateByPrimaryKeySelective(tUpdate);

		OffsetDateTime now = OffsetDateTime.now();
		MessageDboExample mEx1 = new MessageDboExample();
		mEx1.createCriteria().andThreadIdEqualTo(firstThread);
		for (MessageDbo msg : messageDboMapper.selectByExample(mEx1)) {
			msg.setCreatedTs(now.minusHours(4));
			messageDboMapper.updateByPrimaryKeySelective(msg);
		}

		MessageDboExample mEx1Max = new MessageDboExample();
		mEx1Max.createCriteria().andThreadIdEqualTo(firstThread);
		mEx1Max.setOrderByClause("message_id desc limit 1");
		MessageDbo maxMsg1 = messageDboMapper.selectByExample(mEx1Max).get(0);
		maxMsg1.setCreatedTs(now.minusHours(2));
		messageDboMapper.updateByPrimaryKeySelective(maxMsg1);

		MessageDboExample mEx2 = new MessageDboExample();
		mEx2.createCriteria().andThreadIdEqualTo(secondThread);
		for (MessageDbo msg : messageDboMapper.selectByExample(mEx2)) {
			msg.setCreatedTs(now.minusHours(3));
			messageDboMapper.updateByPrimaryKeySelective(msg);
		}

		MessageDboExample mEx2Max = new MessageDboExample();
		mEx2Max.createCriteria().andThreadIdEqualTo(secondThread);
		mEx2Max.setOrderByClause("message_id desc limit 1");
		MessageDbo maxMsg2 = messageDboMapper.selectByExample(mEx2Max).get(0);
		maxMsg2.setCreatedTs(now.minusHours(1));
		messageDboMapper.updateByPrimaryKeySelective(maxMsg2);

		RecentActivityViewDboExample bothBoards = new RecentActivityViewDboExample();
		bothBoards.createCriteria()
				.andThreadIdIn(List.of(firstThread, secondThread))
				.andBoardIdIn(List.of(firstBoard, secondBoard));
		bothBoards.setOrderByClause("last_post_ts desc");
		List<RecentActivityViewDbo> rows = recentActivityMapper.selectByExample(bothBoards);

		assertEquals(List.of(secondThread, firstThread),
				rows.stream().map(RecentActivityViewDbo::getThreadId).toList());
		assertEquals(2, rows.stream().map(RecentActivityViewDbo::getThreadId).distinct().count());
		assertEquals(ADMIN_DISPLAY_NAME, rows.get(0).getLastPoster());
		assertTrue(rows.get(0).getLastPostTs().isAfter(rows.get(1).getLastPostTs()));

		RecentActivityViewDboExample oneBoard = new RecentActivityViewDboExample();
		oneBoard.createCriteria()
				.andThreadIdIn(List.of(firstThread, secondThread))
				.andBoardIdEqualTo(firstBoard);
		assertEquals(List.of(firstThread), recentActivityMapper.selectByExample(oneBoard).stream()
				.map(RecentActivityViewDbo::getThreadId).toList());

		bothBoards.setLimit(1);
		bothBoards.setOffset(0);
		assertEquals(List.of(secondThread), recentActivityMapper.selectByExampleWithLimits(bothBoards).stream()
				.map(RecentActivityViewDbo::getThreadId).toList());
	}

	@Test
	void attachmentBoardViewResolvesTheAttachmentsThreadBoard() throws Exception {
		int threadId = postThread(adminToken, "Attachment view " + suffix);
		MessageDboExample mEx = new MessageDboExample();
		mEx.createCriteria().andThreadIdEqualTo(threadId);
		mEx.setOrderByClause("message_id desc limit 1");
		Integer messageId = messageDboMapper.selectByExample(mEx).get(0).getMessageId();

		ContentResourceDbo resource = new ContentResourceDbo();
		resource.setContentTypeId(2);
		resource.setUploadedUserId(userIdOf(ADMIN_USER));
		resource.setFilename("view-" + suffix + ".txt");
		resource.setChecksum("view-test");
		resource.setFileExt("txt");
		resource.setMimeType("text/plain");
		contentResourceDboMapper.insertSelective(resource);
		Integer resourceId = resource.getContentResourceId();

		FileAttachmentDbo attachment = new FileAttachmentDbo();
		attachment.setContentResourceId(resourceId);
		attachment.setMessageId(messageId);
		attachment.setDownloads(0);
		fileAttachmentDboMapper.insertSelective(attachment);

		FileAttachmentDboExample faEx = new FileAttachmentDboExample();
		faEx.createCriteria().andContentResourceIdEqualTo(resourceId);
		assertEquals(0, fileAttachmentDboMapper.selectByExample(faEx).get(0).getDownloads());

		AttachmentBoardViewDboExample example = new AttachmentBoardViewDboExample();
		example.createCriteria().andContentResourceIdEqualTo(resourceId);
		List<AttachmentBoardViewDbo> rows = attachmentBoardMapper.selectByExample(example);

		assertEquals(1, rows.size());
		assertEquals(boardId(threadId), rows.get(0).getBoardId());
	}

	private Integer boardId(int threadId) {
		return threadDboMapper.selectByPrimaryKey(threadId).getBoardId();
	}

}
