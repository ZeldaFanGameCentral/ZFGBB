package com.zfgc.zfgbb.migrator.converters;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.queries.SmfResilientReadMapper;
import com.zfgc.zfgbb.migrator.smf.queries.SmfMessageStreamMapper;

@Component
public class MessageHistoryConverter extends AbstractConverter<Void> {

	@Autowired
	private SmfResilientReadMapper resilientReads;

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
	private SqlSessionFactory sqlSessionFactory;

	@Value("${zfgbb.migrator.batch-size:5000}")
	private int batchSize;

	private static final Logger logger = LoggerFactory.getLogger(MessageHistoryConverter.class);

	private boolean freshRun;

	private Map<Integer, Integer> messageIdMap;

	@Override
	public JobType getType() {
		return JobType.MESSAGE_HISTORY;
	}

	@Override
	public Void convertToZfgbb() {
		long total = smfMsgMapper.countByExample(new SMFMessageDbExample());
		boolean force = JobContextHolder.isForce();
		long existingHistory = msgHistoryMapper.countByExample(new MessageHistoryDboExample());
		freshRun = !force && existingHistory == 0;
		messageIdMap = idMap.getAllForType(LegacyEntityType.MESSAGE);
		logger.info("Beginning conversion of {} SMF messages -> message history (batch size {}, freshRun={})",
				total, batchSize, freshRun);

		Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId = resilientReads.messagesHistoryTableExists() == 0
				? Map.of()
				: resilientReads.selectAllMessageHistory().stream()
						.collect(Collectors.groupingBy(SMFMessageHistoryDb::getIdMsg));
		Map<String, IpAddressDbo> ipByIp = ipMapper.selectByExample(new IpAddressDboExample()).stream()
				.collect(Collectors.toMap(IpAddressDbo::getIp, Function.identity(), (a, b) -> a));

		Integer lastId = 0;
		long processed = 0;

		while (true) {
			List<SMFMessageDbWithBLOBs> batch =
					smfMessageStreamMapper.selectAfterIdLimit(lastId, batchSize);
			if (batch.isEmpty()) {
				break;
			}

			processBatch(batch, historyByMsgId, ipByIp);

			processed += batch.size();
			lastId = batch.get(batch.size() - 1).getIdMsg();
			logger.info("Processed {}/{} message-history records", processed, total);
		}

		logger.info("Finished converting message history");
		return null;
	}

	private void processBatch(List<SMFMessageDbWithBLOBs> batch,
			Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId,
			Map<String, IpAddressDbo> ipByIp) {
		try (SqlSession batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
			MessageHistoryDboMapper batchHistoryMapper = batchSession.getMapper(MessageHistoryDboMapper.class);
			MigratorTimestampMapper batchTimestampMapper = batchSession.getMapper(MigratorTimestampMapper.class);
			List<PersistedRow> persistedRows = new ArrayList<>();
			for (SMFMessageDbWithBLOBs msg : batch) {
				Cancellable.check();
				convertOne(msg, historyByMsgId, ipByIp, batchHistoryMapper, batchTimestampMapper,
						persistedRows);
			}
			batchSession.flushStatements();
			for (PersistedRow persistedRow : persistedRows) {
				if (persistedRow.deleteId() != null) {
					batchTimestampMapper.deleteUnreferencedMigratedHistory(persistedRow.deleteId());
				} else {
					Integer historyId = persistedRow.row().getMessageHistoryId();
					if (historyId == null)
						throw new IllegalStateException("Message-history insert did not return its generated primary key");
					batchTimestampMapper.setMessageHistoryTimestamps(
							historyId, persistedRow.row().getCreatedTs(), persistedRow.row().getUpdatedTs());
				}
			}
			batchSession.flushStatements();
			batchSession.commit();
		}
	}

	private void convertOne(SMFMessageDbWithBLOBs msg,
			Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId,
			Map<String, IpAddressDbo> ipByIp,
			MessageHistoryDboMapper batchHistoryMapper,
			MigratorTimestampMapper batchTimestampMapper,
			List<PersistedRow> persistedRows) {
		Integer zfgbbMessageId = messageIdMap.get(msg.getIdMsg());
		if (zfgbbMessageId == null) {
			return;
		}
		String posterIp = msg.getPosterIp();
		if (posterIp == null || posterIp.isBlank()) {
			posterIp = "127.0.0.1";
		}
		IpAddressDbo ipAddress = ipByIp.get(posterIp);
		Integer ipAddressId = ipAddress != null ? ipAddress.getIpAddressId() : null;

		List<SMFMessageHistoryDb> historyRows = historyByMsgId.get(msg.getIdMsg());
		List<HistoryInterval> intervals = reconstructIntervals(
				SmfTimes.fromEpochSeconds(msg.getPosterTime()), msg.getBody(), historyRows);
		List<MessageHistoryDbo> existingMigrated = existingMigratedRows(zfgbbMessageId);
		List<MessageHistoryDbo> desiredRows = new ArrayList<>(intervals.size());
		for (HistoryInterval interval : intervals) {
			MessageHistoryDbo row = new MessageHistoryDbo();
			row.setCreatedTs(interval.createdTs());
			row.setUpdatedTs(interval.createdTs());
			row.setCurrentFlag(interval.currentFlag());
			row.setIpAddressId(ipAddressId);
			row.setMessageId(zfgbbMessageId);
			row.setMessageText(interval.body());

			row.setMigrationHash(MigrationHasher.hash(msg.getIdMsg().toString()
					+ "history-" + String.valueOf(interval.createdTs())
					+ interval.body()
					+ Boolean.toString(interval.currentFlag())
					+ ipAddressId));
			desiredRows.add(row);
		}

		Reconciliation reconciliation = reconcile(
				desiredRows.stream().map(MessageHistoryDbo::getMigrationHash).toList(),
				existingMigrated.stream()
						.map(row -> new ExistingCandidate(row.getMessageHistoryId(), row.getMigrationHash())).toList());

		demoteMigratedCurrentCandidates(existingMigrated, batchTimestampMapper);

		for (int index = 0; index < desiredRows.size(); index++) {
			MessageHistoryDbo row = desiredRows.get(index);
			Integer reusableId = reconciliation.assignedIds().get(index);
			if (reusableId != null) {
				row.setMessageHistoryId(reusableId);
				batchHistoryMapper.updateByPrimaryKey(row);
			} else {
				batchHistoryMapper.insert(row);
			}
			persistedRows.add(PersistedRow.timestamp(row));
		}
		for (Integer surplusId : reconciliation.surplusIds())
			persistedRows.add(PersistedRow.delete(surplusId));
	}

	private static void demoteMigratedCurrentCandidates(List<MessageHistoryDbo> existingMigrated,
			MigratorTimestampMapper timestampMapper) {
		for (MessageHistoryDbo existing : existingMigrated)
			timestampMapper.demoteMigratedHistoryCurrent(existing.getMessageHistoryId());
	}

	private static Integer pollUnused(ArrayDeque<Integer> candidates, Set<Integer> usedIds) {
		while (candidates != null && !candidates.isEmpty()) {
			Integer candidate = candidates.removeFirst();
			if (!usedIds.contains(candidate))
				return candidate;
		}
		return null;
	}

	static List<Integer> reconcileIds(List<String> desiredHashes, List<ExistingCandidate> existing) {
		return reconcile(desiredHashes, existing).assignedIds();
	}

	static Reconciliation reconcile(List<String> desiredHashes, List<ExistingCandidate> existing) {
		Map<String, ArrayDeque<Integer>> byHash = new HashMap<>();
		for (ExistingCandidate candidate : existing)
			byHash.computeIfAbsent(candidate.hash(), ignored -> new ArrayDeque<>()).add(candidate.id());
		Set<Integer> used = new java.util.HashSet<>();
		List<Integer> assigned = new ArrayList<>();
		for (String hash : desiredHashes) {
			Integer id = pollUnused(byHash.get(hash), used);
			if (id == null)
				id = existing.stream().map(ExistingCandidate::id).filter(candidate -> !used.contains(candidate))
						.findFirst().orElse(null);
			if (id != null)
				used.add(id);
			assigned.add(id);
		}
		List<Integer> surplus = existing.stream().map(ExistingCandidate::id)
				.filter(candidate -> !used.contains(candidate)).toList();
		return new Reconciliation(assigned, surplus);
	}

	static List<Integer> assignExistingIds(int intervalCount, List<Integer> existingIds) {
		List<Integer> assigned = new ArrayList<>(intervalCount);
		for (int index = 0; index < intervalCount; index++)
			assigned.add(index < existingIds.size() ? existingIds.get(index) : null);
		return assigned;
	}

	private List<MessageHistoryDbo> existingMigratedRows(Integer messageId) {
		if (freshRun)
			return List.of();
		MessageHistoryDboExample ex = new MessageHistoryDboExample();
		ex.createCriteria().andMessageIdEqualTo(messageId).andMigrationHashIsNotNull();
		ex.setOrderByClause("created_ts, message_history_id");
		return msgHistoryMapper.selectByExample(ex);
	}

	record ExistingCandidate(Integer id, String hash) {}
	record Reconciliation(List<Integer> assignedIds, List<Integer> surplusIds) {}

	private record PersistedRow(MessageHistoryDbo row, Integer deleteId) {
		static PersistedRow timestamp(MessageHistoryDbo row) {
			return new PersistedRow(row, null);
		}

		static PersistedRow delete(Integer id) {
			return new PersistedRow(null, id);
		}
	}

	record HistoryInterval(OffsetDateTime createdTs, String body, boolean currentFlag) {}

	static List<HistoryInterval> reconstructIntervals(OffsetDateTime posterTs, String currentBody,
			List<SMFMessageHistoryDb> historyRows) {
		List<HistoryInterval> intervals = new ArrayList<>();
		OffsetDateTime previousTs = posterTs;
		if (historyRows != null && !historyRows.isEmpty()) {
			List<SMFMessageHistoryDb> ascending = new ArrayList<>(historyRows);
			ascending.sort(Comparator.comparingLong(
					row -> row.getModifiedTime() == null ? Long.MIN_VALUE : row.getModifiedTime()));
			for (SMFMessageHistoryDb smfHist : ascending) {
				intervals.add(new HistoryInterval(previousTs, HtmlUtils.htmlUnescape(smfHist.getBody()), false));
				previousTs = SmfTimes.fromEpochSeconds(smfHist.getModifiedTime());
			}
		}
		intervals.add(new HistoryInterval(previousTs, HtmlUtils.htmlUnescape(currentBody), true));
		return clampMonotonic(intervals);
	}

	private static List<HistoryInterval> clampMonotonic(List<HistoryInterval> intervals) {
		List<HistoryInterval> clamped = new ArrayList<>(intervals.size());
		OffsetDateTime last = null;
		for (HistoryInterval interval : intervals) {
			OffsetDateTime createdTs = interval.createdTs();
			if (createdTs == null) {
				createdTs = last != null ? last : OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
			} else if (last != null && createdTs.isBefore(last)) {
				createdTs = last;
			}
			clamped.add(new HistoryInterval(createdTs, interval.body(), interval.currentFlag()));
			last = createdTs;
		}
		return clamped;
	}

}
