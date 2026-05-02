package com.zfgc.zfgbb.migrator.converters;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;

@Component
public class MessageConverter extends AbstractConverter<Map<Integer, MessageDbo>> {

	@Autowired
	private MessageDboMapper messageMapper;

	@Autowired
	private SMFMessageDbMapper smfMessageMapper;

	@Autowired
	private MigratorIdMapService idMap;

	@Autowired
	private MigratorTimestampMapper migratorTimestampMapper;

	Logger logger = LoggerFactory.getLogger(MessageConverter.class);

	@Override
	public JobType getType() {
		return JobType.MESSAGES;
	}

	@Override
	@Transactional
	public Map<Integer, MessageDbo> convertToZfgbb() {
		SMFMessageDbExample smfEx = new SMFMessageDbExample();
		smfEx.setOrderByClause("poster_time asc");
		List<SMFMessageDbWithBLOBs> SMFMessages = smfMessageMapper.selectByExampleWithBLOBs(smfEx);
		Map<Integer, MessageDbo> result = new HashMap<>();
		Map<Integer, AtomicInteger> counts = new HashMap<>();
		AtomicInteger totalCount = new AtomicInteger(1);

		logger.info("Beginning conversion of SMF messages to ZFGBB messages");
		logger.info(SMFMessages.size() + " records found");

		SMFMessages.forEach((smfMsg) -> {
			Cancellable.check();
			logger.info("Processing msg record " + totalCount.getAndIncrement() + " of " + SMFMessages.size());
			MessageDbo msg = new MessageDbo();

			counts.putIfAbsent(smfMsg.getIdTopic(), new AtomicInteger(1));

			Integer smfMember = smfMsg.getIdMember();
			msg.setOwnerId(smfMember == null || smfMember == 0
					? null
					: idMap.lookup(LegacyEntityType.USER, smfMember));
			msg.setThreadId(idMap.lookup(LegacyEntityType.THREAD, smfMsg.getIdTopic()));
			msg.setPostInThread(counts.get(smfMsg.getIdTopic()).getAndIncrement());
			msg.setCreatedTs(SmfTimes.fromEpochSeconds(smfMsg.getPosterTime()));

			LocalDateTime updatedTime = SmfTimes.fromEpochSeconds(smfMsg.getModifiedTime());
			if (updatedTime != null) {
				msg.setUpdatedTs(updatedTime);
			} else {
				msg.setUpdatedTs(msg.getCreatedTs());
			}

			msg.setMigrationHash(MigrationHasher.hash(smfMsg.getIdMsg().toString()
					+ "" + smfMsg.getIdMember()
					+ smfMsg.getIdTopic()
					+ (msg.getPostInThread() == null ? -1 : msg.getPostInThread())
					+ msg.getCreatedTs().toString()
					+ (msg.getUpdatedTs() == null ? 0 : msg.getUpdatedTs().toString())));

			Integer existingZfgbbId = idMap.lookupOrNull(LegacyEntityType.MESSAGE, smfMsg.getIdMsg());
			if (existingZfgbbId == null) {
				messageMapper.insert(msg);
				idMap.record(LegacyEntityType.MESSAGE, smfMsg.getIdMsg(), msg.getMessageId());
			} else {
				MessageDbo existing = messageMapper.selectByPrimaryKey(existingZfgbbId);
				if (existing == null) {
					msg.setMessageId(existingZfgbbId);
					messageMapper.insert(msg);
				} else if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), msg.getMigrationHash())) {
					msg.setMessageId(existingZfgbbId);
					messageMapper.updateByPrimaryKey(msg);
				} else {
					msg.setMessageId(existingZfgbbId);
				}
			}

			if (msg.getCreatedTs() != null) {
				migratorTimestampMapper.setMessageTimestamps(
						msg.getMessageId(), msg.getCreatedTs(), msg.getUpdatedTs());
			}

			result.put(smfMsg.getIdMsg(), msg);
		});

		logger.info("Finished converting messages");

		return result;
	}
}
