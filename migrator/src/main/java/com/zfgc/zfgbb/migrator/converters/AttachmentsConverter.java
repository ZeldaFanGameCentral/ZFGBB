package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.FileAttachmentDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFAttachmentsDbMapper;

@Component
public class AttachmentsConverter extends AbstractConverter<Map<Integer, ContentResourceDbo>> {

	@Override
	public JobType getType() {
		return JobType.ATTACHMENTS;
	}

	@Autowired
	private SMFAttachmentsDbMapper smfAttachmentsDbMapper;

	@Autowired
	private ContentResourceDboMapper contentMapper;

	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private FileAttachmentDboMapper attachmentMapper;

	@Override
	@Transactional
	public Map<Integer, ContentResourceDbo> convertToZfgbb() {
		SMFAttachmentsDbExample attachEx = new SMFAttachmentsDbExample();
		attachEx.createCriteria().andIdMsgGreaterThan(0);

		return smfAttachmentsDbMapper.selectByExample(attachEx).stream()
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

					return resource;
				})
				.filter(r -> r != null)
				.collect(Collectors.toMap(ContentResourceDbo::getContentResourceId, Function.identity(),
						(a, b) -> a));
	}
}
