package com.zfgc.zfgbb.dao.forum;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.mappers.FileAttachmentDboMapper;

@Repository
public class FileAttachmentDao extends IdentityDao<FileAttachmentDbo, FileAttachmentDboExample> {

	public FileAttachmentDao(FileAttachmentDboMapper mapper) {
		super(mapper);
	}

	public List<Integer> findAttachmentContentResourceIds(List<Integer> messageIds) {
		FileAttachmentDboExample byMessages = new FileAttachmentDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		return get(byMessages).stream().map(FileAttachmentDbo::getContentResourceId).distinct().toList();
	}

	public List<Integer> findAttachmentIdsForMessages(List<Integer> messageIds) {
		FileAttachmentDboExample byMessages = new FileAttachmentDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		return get(byMessages).stream().map(FileAttachmentDbo::getFileAttachmentId).toList();
	}

	public int scrubAttachmentMigrationHashesForMessages(List<Integer> messageIds) {
		FileAttachmentDbo scrubbed = new FileAttachmentDbo();
		FileAttachmentDboExample byMessages = new FileAttachmentDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		return updateWhereSettingColumns(scrubbed, Set.of("migration_hash"), byMessages);
	}

	public Set<Integer> contentResourceIdsAmong(List<Integer> contentResourceIds) {
		FileAttachmentDboExample referencing = new FileAttachmentDboExample();
		referencing.createCriteria().andContentResourceIdIn(contentResourceIds);
		return get(referencing).stream().map(FileAttachmentDbo::getContentResourceId).collect(Collectors.toSet());
	}
}
