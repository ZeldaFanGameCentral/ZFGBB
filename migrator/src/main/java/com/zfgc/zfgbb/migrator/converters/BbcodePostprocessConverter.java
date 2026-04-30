package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFAttachmentsDbMapper;

@Component
public class BbcodePostprocessConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(BbcodePostprocessConverter.class);

	@Autowired
	private SMFAttachmentsDbMapper smfAttachmentsMapper;

	@Autowired
	private MessageHistoryDboMapper messageHistoryMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public JobType getType() {
		return JobType.BBCODE_REWRITE;
	}

	@Override
	@Transactional
	public Void convertToZfgbb() {
		Map<Integer, Integer> smfToZfgbbAttachId = buildAttachIdMap();
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
			String updated = LegacyUrlRewriter.rewrite(body, smfToZfgbbAttachId);
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
			logger.info("Rewrote BBCode references in {} message_history rows", rewritten);
		}
		return null;
	}

	private Map<Integer, Integer> buildAttachIdMap() {
		Map<String, Integer> hashToSmfId = new HashMap<>();
		smfAttachmentsMapper.selectByExample(new SMFAttachmentsDbExample()).forEach(a -> {
			if (a.getFileHash() != null && a.getIdAttach() != null) {
				hashToSmfId.put(a.getFileHash(), a.getIdAttach());
			}
		});

		Map<Integer, Integer> result = new HashMap<>();
		jdbcTemplate.query(
				"select fa.file_attachment_id, cr.checksum "
						+ "from zfgbb.file_attachments fa "
						+ "join zfgbb.content_resource cr on cr.content_resource_id = fa.content_resource_id",
				rs -> {
					String checksum = rs.getString("checksum");
					Integer smfId = hashToSmfId.get(checksum);
					if (smfId != null) {
						result.put(smfId, rs.getInt("file_attachment_id"));
					}
				});
		return result;
	}
}
