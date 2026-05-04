package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

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
import com.zfgc.zfgbb.migrator.smf.queries.SmfMessageStreamMapper;

@Component
public class MessageHistoryConverter extends AbstractConverter<Void> {

	@Autowired
	private SMFMessageHistoryDbMapper smfMsgHistoryMapper;

	@Autowired
	private SMFMessageDbMapper smfMsgMapper;

	@Autowired
	private SmfMessageStreamMapper smfMessageStreamMapper;

	@Autowired
	private MessageHistoryDboMapper msgHistoryMapper;

	@Autowired
	private IpAddressDboMapper ipMapper;

	@Autowired
	private MigratorIdMapService idMap;

	@Autowired
	private MigratorTimestampMapper migratorTimestampMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Value("${zfgbb.migrator.batch-size:5000}")
	private int batchSize;

	private static final Logger logger = LoggerFactory.getLogger(MessageHistoryConverter.class);

	@Override
	public JobType getType() {
		return JobType.MESSAGE_HISTORY;
	}

	@Override
	public Void convertToZfgbb() {
		Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId = smfMsgHistoryMapper
				.selectByExampleWithBLOBs(new SMFMessageHistoryDbExample()).stream()
				.collect(Collectors.groupingBy(SMFMessageHistoryDb::getIdMsg));
		Map<String, IpAddressDbo> ipByIp = ipMapper.selectByExample(new IpAddressDboExample()).stream()
				.collect(Collectors.toMap(IpAddressDbo::getIp, Function.identity(), (a, b) -> a));

		long total = smfMsgMapper.countByExample(new SMFMessageDbExample());
		logger.info("Beginning conversion of {} SMF messages -> message history (batch size {})", total, batchSize);

		Integer lastId = 0;
		long processed = 0;

		while (true) {
			final Integer cursor = lastId;
			List<SMFMessageDbWithBLOBs> batch =
					smfMessageStreamMapper.selectAfterIdLimit(cursor, batchSize);
			if (batch.isEmpty()) {
				break;
			}

			transactionTemplate.executeWithoutResult(status -> {
				for (SMFMessageDbWithBLOBs msg : batch) {
					Cancellable.check();
					convertOne(msg, historyByMsgId, ipByIp);
				}
			});

			processed += batch.size();
			lastId = batch.get(batch.size() - 1).getIdMsg();
			logger.info("Processed {}/{} message-history records", processed, total);
		}

		logger.info("Finished converting message history");
		return null;
	}

	private void convertOne(SMFMessageDbWithBLOBs msg,
			Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId,
			Map<String, IpAddressDbo> ipByIp) {
		Integer zfgbbMessageId = idMap.lookupOrNull(LegacyEntityType.MESSAGE, msg.getIdMsg());
		if (zfgbbMessageId == null) {
			return;
		}
		IpAddressDbo ip = ipByIp.get(msg.getPosterIp());
		if (ip == null) {
			return;
		}
		Integer ipAddressId = ip.getIpAddressId();

		List<SMFMessageHistoryDb> historyRows = historyByMsgId.get(msg.getIdMsg());
		if (historyRows != null) {
			for (SMFMessageHistoryDb smfHist : historyRows) {
				MessageHistoryDbo histRow = new MessageHistoryDbo();
				histRow.setCreatedTs(SmfTimes.fromEpochSeconds(smfHist.getModifiedTime()));
				histRow.setCurrentFlag(false);
				histRow.setIpAddressId(ipAddressId);
				histRow.setLegacyId(null);
				histRow.setMessageId(zfgbbMessageId);
				histRow.setMessageText(smfHist.getBody());
				histRow.setUpdatedTs(histRow.getCreatedTs());

				histRow.setMigrationHash(MigrationHasher.hash(msg.getIdMsg().toString()
						+ "history-" + SmfTimes.fromEpochSeconds(smfHist.getModifiedTime()).toString()
						+ histRow.getMessageText()
						+ histRow.getCurrentFlag().toString()
						+ ipAddressId
						+ histRow.getLegacyId()));

				upsertHistory(histRow);
			}
		}

		MessageHistoryDbo currentRow = new MessageHistoryDbo();
		currentRow.setCreatedTs(SmfTimes.fromEpochSeconds(msg.getPosterTime()));
		currentRow.setCurrentFlag(true);
		currentRow.setIpAddressId(ipAddressId);
		currentRow.setLegacyId(null);
		currentRow.setMessageId(zfgbbMessageId);
		currentRow.setMessageText(msg.getBody());
		currentRow.setUpdatedTs(currentRow.getCreatedTs());

		currentRow.setMigrationHash(MigrationHasher.hash(msg.getIdMsg().toString()
				+ "current"
				+ currentRow.getMessageText()
				+ currentRow.getCurrentFlag().toString()
				+ currentRow.getCreatedTs().toString()
				+ ipAddressId
				+ currentRow.getLegacyId()));

		upsertHistory(currentRow);
	}

	private void upsertHistory(MessageHistoryDbo row) {
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMigrationHashEqualTo(row.getMigrationHash());
		MessageHistoryDbo existing = msgHistoryMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			msgHistoryMapper.insert(row);
		} else if (JobContextHolder.isForce()
				|| !Objects.equals(existing.getMigrationHash(), row.getMigrationHash())) {
			row.setMessageHistoryId(existing.getMessageHistoryId());
			msgHistoryMapper.updateByPrimaryKey(row);
		} else {
			row.setMessageHistoryId(existing.getMessageHistoryId());
		}

		if (row.getCreatedTs() != null) {
			migratorTimestampMapper.setMessageHistoryTimestamps(
					row.getMessageHistoryId(), row.getCreatedTs(), row.getUpdatedTs());
		}
	}
}
