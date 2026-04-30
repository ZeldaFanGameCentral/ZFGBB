package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.FileAttachmentDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFAttachmentsDbMapper;

@Component
public class AttachmentsConverter extends AbstractConverter<Map<Integer, ContentResourceDbo>> {

	@Override
	public JobType getType() {
		return JobType.ATTACHMENTS;
	}

	private static final Logger logger = LoggerFactory.getLogger(AttachmentsConverter.class);

	private static final Pattern ATTACH_REF = Pattern.compile("\\[attach=(\\d+)\\]");

	@Autowired
	private SMFAttachmentsDbMapper smfAttachmentsDbMapper;

	@Autowired
	private ContentResourceDboMapper contentMapper;

	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private FileAttachmentDboMapper attachmentMapper;

	@Autowired
	private MessageHistoryDboMapper messageHistoryMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public Map<Integer, ContentResourceDbo> convertToZfgbb() {
		SMFAttachmentsDbExample attachEx = new SMFAttachmentsDbExample();
		attachEx.createCriteria().andIdMsgGreaterThan(0);

		Map<Integer, Integer> smfToZfgbbAttachId = new HashMap<>();

		Map<Integer, ContentResourceDbo> result = smfAttachmentsDbMapper.selectByExample(attachEx).stream()
				.map(attachment -> {
					Cancellable.check();
					MessageDbo msg = messageMapper.selectByPrimaryKey(attachment.getIdMsg());
					if (msg == null) {
						return null;
					}

					ContentResourceDbo resource = new ContentResourceDbo();
					resource.setContentTypeId(2);
					resource.setChecksum(attachment.getFileHash());
					resource.setFileExt(attachment.getFileext());
					resource.setFilename(attachment.getFilename());
					resource.setMimeType(attachment.getMimeType());
					resource.setUploadedUserId(msg.getOwnerId());
					resource.setFileSize(attachment.getSize().longValue());
					resource.setMigrationHash(MigrationHasher.hash(resource.getContentTypeId().toString()
							+ resource.getUploadedUserId().toString()
							+ resource.getFilename()
							+ resource.getChecksum()
							+ resource.getFileExt()
							+ resource.getMimeType()
							+ resource.getFileSize().toString()));

					ContentResourceDboExample existingResourceEx = new ContentResourceDboExample();
					existingResourceEx.createCriteria().andMigrationHashEqualTo(resource.getMigrationHash());
					contentMapper.selectByExample(existingResourceEx).stream().findFirst()
							.ifPresentOrElse(
									existing -> resource.setContentResourceId(existing.getContentResourceId()),
									() -> contentMapper.insert(resource));

					// create the file attachment record
					FileAttachmentDbo fileAttachment = new FileAttachmentDbo();
					fileAttachment.setActiveFlag(attachment.getApproved().intValue() == 1);
					fileAttachment.setContentResourceId(resource.getContentResourceId());
					fileAttachment.setMessageId(msg.getMessageId());
					fileAttachment.setDownloads(attachment.getDownloads());
					fileAttachment.setMigrationHash(MigrationHasher.hash(fileAttachment.getMessageId().toString()
							+ fileAttachment.getActiveFlag().toString()
							+ fileAttachment.getContentResourceId().toString()
							+ fileAttachment.getDownloads().toString()));

					FileAttachmentDboExample existingAttachmentEx = new FileAttachmentDboExample();
					existingAttachmentEx.createCriteria().andMigrationHashEqualTo(fileAttachment.getMigrationHash());
					attachmentMapper.selectByExample(existingAttachmentEx).stream().findFirst()
							.ifPresentOrElse(
									existing -> fileAttachment.setFileAttachmentId(existing.getFileAttachmentId()),
									() -> attachmentMapper.insert(fileAttachment));

					smfToZfgbbAttachId.put(attachment.getIdAttach(), fileAttachment.getFileAttachmentId());

					return resource;
				})
				.filter(r -> r != null)
				.collect(Collectors.toMap(ContentResourceDbo::getContentResourceId, Function.identity(),
						(a, b) -> a));

		rewriteAttachmentRefs(smfToZfgbbAttachId);

		return result;
	}

	private void rewriteAttachmentRefs(Map<Integer, Integer> smfToZfgbbAttachId) {
		if (smfToZfgbbAttachId.isEmpty()) {
			return;
		}
		Set<Integer> alreadyRewritten = new HashSet<>(jdbcTemplate.queryForList(
				"select message_history_id from zfgbb.migrator_attachment_ref_rewrites", Integer.class));
		List<MessageHistoryDbo> histories = messageHistoryMapper.selectByExample(new MessageHistoryDboExample());
		int rewritten = 0;
		for (MessageHistoryDbo history : histories) {
			Cancellable.check();
			if (alreadyRewritten.contains(history.getMessageHistoryId())) {
				continue;
			}
			String body = history.getMessageText();
			if (body == null) {
				continue;
			}
			String updated = substituteAttachRefs(body, smfToZfgbbAttachId);
			if (!updated.equals(body)) {
				history.setMessageText(updated);
				messageHistoryMapper.updateByPrimaryKey(history);
				rewritten++;
			}
			jdbcTemplate.update(
					"insert into zfgbb.migrator_attachment_ref_rewrites (message_history_id) values (?) "
							+ "on conflict (message_history_id) do nothing",
					history.getMessageHistoryId());
		}
		if (rewritten > 0) {
			logger.info("Rewrote [attach=N] references in {} message_history rows", rewritten);
		}
	}

	private static String substituteAttachRefs(String body, Map<Integer, Integer> smfToZfgbbAttachId) {
		Matcher matcher = ATTACH_REF.matcher(body);
		StringBuilder out = new StringBuilder();
		while (matcher.find()) {
			int smfId = Integer.parseInt(matcher.group(1));
			Integer zfgbbId = smfToZfgbbAttachId.get(smfId);
			String replacement = zfgbbId != null ? "[attach=" + zfgbbId + "]" : matcher.group();
			matcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		matcher.appendTail(out);
		return out.toString();
	}
}
