package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageHistoryDbMapper;

@Component
public class MessageHistoryConverter extends AbstractConverter<Map<Integer, MessageHistoryDbo>> {

	@Override
	public JobType getType() {
		return JobType.MESSAGE_HISTORY;
	}

	@Autowired
	private SMFMessageHistoryDbMapper smfMsgHistoryMapper;

	@Autowired
	private SMFMessageDbMapper smfMsgMapper;

	@Autowired
	private MessageHistoryDboMapper msgHistoryMapper;

	@Autowired
	private IpAddressDboMapper ipMapper;

	@Autowired
	private MigratorIdMapService idMap;

	@Autowired
	private MigratorTimestampMapper migratorTimestampMapper;

	Logger logger = LoggerFactory.getLogger(MessageHistoryConverter.class);

	@Override
	@Transactional
	public Map<Integer, MessageHistoryDbo> convertToZfgbb() {
		Map<Integer, List<SMFMessageHistoryDb>> SMFMessageHistory = smfMsgHistoryMapper
				.selectByExampleWithBLOBs(new SMFMessageHistoryDbExample()).stream()
				.collect(Collectors.groupingBy(SMFMessageHistoryDb::getIdMsg));
		Map<String, IpAddressDbo> ipMap = ipMapper.selectByExample(new IpAddressDboExample()).stream()
				.collect(Collectors.toMap(IpAddressDbo::getIp, Function.identity()));
		Map<Integer, SMFMessageDbWithBLOBs> msgMap = smfMsgMapper.selectByExampleWithBLOBs(new SMFMessageDbExample())
				.stream().collect(Collectors.toMap(SMFMessageDbWithBLOBs::getIdMsg, Function.identity()));

		Map<Integer, MessageHistoryDbo> result = new HashMap<>();
		AtomicInteger totalCount = new AtomicInteger(0);

		logger.info("Beginning conversion of SMF message history to ZFGBB message history");
		logger.info(msgMap.size() + " records found");

		msgMap.values().forEach(msg -> {
			Cancellable.check();
			if (totalCount.get() % 10000 == 0) {
				logger.info("Processing message history for SMF msg id " + msg.getIdMsg() + " record "
						+ totalCount.get() + " of " + msgMap.size());
			}

			Integer zfgbbMessageId = idMap.lookupOrNull(LegacyEntityType.MESSAGE, msg.getIdMsg());
			if (zfgbbMessageId == null) {
				return;
			}
			Integer ipAddressId = ipMap.get(msg.getPosterIp()).getIpAddressId();

			if (SMFMessageHistory.containsKey(msg.getIdMsg())) {
				SMFMessageHistory.get(msg.getIdMsg()).forEach(smfHist -> {
					MessageHistoryDbo currentMsg = new MessageHistoryDbo();
					currentMsg.setCreatedTs(SmfTimes.fromEpochSeconds(smfHist.getModifiedTime()));
					currentMsg.setCurrentFlag(false);
					currentMsg.setIpAddressId(ipAddressId);
					currentMsg.setLegacyId(null);
					currentMsg.setMessageId(zfgbbMessageId);
					currentMsg.setMessageText(smfHist.getBody());
					currentMsg.setUpdatedTs(currentMsg.getCreatedTs());

					currentMsg.setMigrationHash(MigrationHasher.hash(msg.getIdMsg().toString()
							+ "history-" + SmfTimes.fromEpochSeconds(smfHist.getModifiedTime()).toString()
							+ currentMsg.getMessageText()
							+ currentMsg.getCurrentFlag().toString()
							+ ipAddressId
							+ currentMsg.getLegacyId()));

					upsertHistory(currentMsg);
					result.put(currentMsg.getMessageHistoryId(), currentMsg);
				});
			}

			MessageHistoryDbo currentMsg = new MessageHistoryDbo();
			currentMsg.setCreatedTs(SmfTimes.fromEpochSeconds(msg.getPosterTime()));
			currentMsg.setCurrentFlag(true);
			currentMsg.setIpAddressId(ipAddressId);
			currentMsg.setLegacyId(null);
			currentMsg.setMessageId(zfgbbMessageId);
			currentMsg.setMessageText(msg.getBody());
			currentMsg.setUpdatedTs(currentMsg.getCreatedTs());

			currentMsg.setMigrationHash(MigrationHasher.hash(msg.getIdMsg().toString()
					+ "current"
					+ currentMsg.getMessageText()
					+ currentMsg.getCurrentFlag().toString()
					+ currentMsg.getCreatedTs().toString()
					+ ipAddressId
					+ currentMsg.getLegacyId()));

			upsertHistory(currentMsg);
			result.put(currentMsg.getMessageHistoryId(), currentMsg);
			totalCount.incrementAndGet();
		});

		logger.info("Finished converting message history");

		return result;
	}

	private void upsertHistory(MessageHistoryDbo currentMsg) {
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMigrationHashEqualTo(currentMsg.getMigrationHash());
		MessageHistoryDbo existing = msgHistoryMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			msgHistoryMapper.insert(currentMsg);
		} else if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), currentMsg.getMigrationHash())) {
			currentMsg.setMessageHistoryId(existing.getMessageHistoryId());
			msgHistoryMapper.updateByPrimaryKey(currentMsg);
		} else {
			currentMsg.setMessageHistoryId(existing.getMessageHistoryId());
		}

		if (currentMsg.getCreatedTs() != null) {
			migratorTimestampMapper.setMessageHistoryTimestamps(
					currentMsg.getMessageHistoryId(),
					currentMsg.getCreatedTs(),
					currentMsg.getUpdatedTs());
		}
	}
}
