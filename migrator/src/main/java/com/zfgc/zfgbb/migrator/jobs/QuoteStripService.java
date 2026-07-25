package com.zfgc.zfgbb.migrator.jobs;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.mappers.QuoteStripConversionMapper;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.PlannedSample;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripApplyResult;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripPurgeResult;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripReport;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripRevertResult;
import lombok.RequiredArgsConstructor;

@Service
@ConditionalOnProperty(prefix = "zfgbb.migrator", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class QuoteStripService implements QuoteStripOperations {

	private static final String STATUS_PLANNED = "PLANNED";
	private static final String STATUS_APPLIED = "APPLIED";
	private static final String RUN_PLANNING = "PLANNING";
	private static final String RUN_PLANNED = "PLANNED";
	private static final String RUN_APPLYING = "APPLYING";
	private static final String RUN_APPLIED = "APPLIED";
	private static final String RUN_REVERTING = "REVERTING";
	private static final String RUN_REVERTED = "REVERTED";
	private static final String RUN_APPLY_PARTIAL = "APPLY_PARTIAL";
	private static final String RUN_REVERT_PARTIAL = "REVERT_PARTIAL";

	public static final String PREGATE_NULL_TS = "null-ts";
	public static final String PREGATE_LIVE_SPLICE_SOURCE = "live-splice-source";
	private static final String KEEP_NO_MSG_QUOTE = "no-msg-quote";

	private static final int SAMPLE_CAP = 20;

	private static final Pattern LITERAL_BR = Pattern.compile("<br\\s*/?>", Pattern.CASE_INSENSITIVE);

	private final QuoteStripConversionMapper quoteStripConversionMapper;

	private final MessageHistoryDboMapper messageHistoryMapper;

	private final SourceReferenceOperations sourceReferences;

	private final QuoteStripPlanner quoteStripPlanner;

	private final DistributedLeaseManager leaseManager;

	private record CandidateRow(Integer messageHistoryId, Integer messageId, String messageText,
			OffsetDateTime createdTs) {}

	private record PlannedRow(Integer messageHistoryId, Integer messageId, String beforeText, String afterText) {}

	private record AuditRow(Integer messageHistoryId, Integer messageId, String beforeText, String afterText) {}

	private static final class Plan {
		final int candidateRows;
		final List<PlannedRow> plannedRows = new ArrayList<>();
		final Map<String, Long> pregateHistogram = new LinkedHashMap<>();
		final Map<String, Long> keepReasonHistogram = new LinkedHashMap<>();
		long plannedQuotes;

		Plan(int candidateRows) {
			this.candidateRows = candidateRows;
		}
	}

	@Override
	@Transactional
	public QuoteStripReport report(UUID runId) {
		if (runId != null && runExists(runId)) {
			return loadExistingReport(runId);
		}
		Plan plan = buildPlan();
		if (runId != null) {
			UUID owner = UUID.randomUUID();
			int created = quoteStripConversionMapper.insertRun(runId, RUN_PLANNING, owner);
			if (created == 0)
				return loadExistingReport(runId);
			persistPlanned(runId, plan.plannedRows);
			quoteStripConversionMapper.markRunPlanned(runId, RUN_PLANNED, plan.candidateRows, plan.plannedRows.size(),
					plan.plannedQuotes, RUN_PLANNING, owner);
		}
		List<PlannedSample> sample = sampleOf(plan.plannedRows);
		return new QuoteStripReport(runId, runId == null ? null : RUN_PLANNED,
				plan.candidateRows, plan.plannedRows.size(), plan.plannedQuotes,
				plan.pregateHistogram, plan.keepReasonHistogram, sample);
	}

	@Override
	public QuoteStripApplyResult apply(UUID runId) {
		return leaseManager.executeWithLease(runId, List.of(RUN_PLANNED, RUN_APPLY_PARTIAL), RUN_APPLYING, quoteStripConversionMapper, context -> {
			List<AuditRow> planned = loadAudit(runId, STATUS_PLANNED);
			int applied = 0;
			int skipped = 0;
			int refused = 0;
			boolean leaseLost = false;
			for (AuditRow row : planned) {
				if (!context.heartbeat()) {
					refused += planned.size() - applied - skipped - refused;
					leaseLost = true;
					break;
				}
				if (row.beforeText() == null || row.afterText() == null) {
					refused++;
					continue;
				}
				boolean changed;
				try {
					changed = context.inNewTransaction(() -> {
						int updated = quoteStripConversionMapper.updateMessageHistoryBody(row.afterText(),
								row.messageHistoryId(), row.beforeText(), runId, RUN_APPLYING, context.getOwner());
						if (updated == 1) {
							int auditUpdated = quoteStripConversionMapper.markAuditApplied(STATUS_APPLIED, runId,
									row.messageHistoryId(), STATUS_PLANNED, RUN_APPLYING, context.getOwner());
							if (auditUpdated != 1)
								throw new IllegalStateException("Quote-strip audit row changed concurrently");
						}
						if (updated == 0) {
							context.requireLease();
						}
						return updated == 1;
					});
				} catch (DistributedLeaseManager.LeaseLostException lost) {
					leaseLost = true;
					refused = planned.size() - applied - skipped;
					break;
				} catch (RuntimeException rowFailure) {
					refused++;
					continue;
				}
				if (changed)
					applied++;
				else
					skipped++;
			}
			int residual = countAudit(runId, STATUS_PLANNED);
			if (!leaseLost) {
				int updated = quoteStripConversionMapper.finishRun(runId, completionStatus(residual, RUN_APPLIED, RUN_APPLY_PARTIAL), RUN_APPLYING, context.getOwner());
				if (updated != 1)
					throw new IllegalStateException("Quote-strip run changed concurrently");
			}
			return new QuoteStripApplyResult(runId, planned.size(), applied, skipped, refused);
		}, () -> new QuoteStripApplyResult(runId, 0, 0, 0, 1));
	}

	@Override
	public QuoteStripRevertResult revert(UUID runId) {
		return leaseManager.executeWithLease(runId, List.of(RUN_APPLIED, RUN_REVERT_PARTIAL), RUN_REVERTING, quoteStripConversionMapper, context -> {
			List<AuditRow> applied = loadAudit(runId, STATUS_APPLIED);
			int reverted = 0;
			int skipped = 0;
			int refused = 0;
			boolean leaseLost = false;
			for (AuditRow row : applied) {
				if (!context.heartbeat()) {
					refused += applied.size() - reverted - skipped - refused;
					leaseLost = true;
					break;
				}
				if (row.beforeText() == null || row.afterText() == null) {
					refused++;
					continue;
				}
				boolean restored;
				try {
					restored = context.inNewTransaction(() -> {
						int updated = quoteStripConversionMapper.updateMessageHistoryBody(row.beforeText(),
								row.messageHistoryId(), row.afterText(), runId, RUN_REVERTING, context.getOwner());
						if (updated == 1) {
							int auditUpdated = quoteStripConversionMapper.markAuditReverted(STATUS_PLANNED, runId,
									row.messageHistoryId(), STATUS_APPLIED, RUN_REVERTING, context.getOwner());
							if (auditUpdated != 1)
								throw new IllegalStateException("Quote-strip audit row changed concurrently");
						}
						if (updated == 0) {
							context.requireLease();
						}
						return updated == 1;
					});
				} catch (DistributedLeaseManager.LeaseLostException lost) {
					leaseLost = true;
					refused = applied.size() - reverted - skipped;
					break;
				} catch (RuntimeException rowFailure) {
					refused++;
					continue;
				}
				if (restored)
					reverted++;
				else
					skipped++;
			}
			int residual = countAudit(runId, STATUS_APPLIED);
			if (!leaseLost) {
				int updated = quoteStripConversionMapper.finishRun(runId, completionStatus(residual, RUN_REVERTED, RUN_REVERT_PARTIAL), RUN_REVERTING, context.getOwner());
				if (updated != 1)
					throw new IllegalStateException("Quote-strip run changed concurrently");
			}
			return new QuoteStripRevertResult(runId, applied.size(), reverted, skipped, refused);
		}, () -> new QuoteStripRevertResult(runId, 0, 0, 0, 1));
	}

	@Override
	@Transactional
	public QuoteStripPurgeResult purge(UUID runId, boolean force) {
		Integer liveLease = quoteStripConversionMapper.countLiveLease(runId, RUN_PLANNING, RUN_APPLYING, RUN_REVERTING);
		if (liveLease != null && liveLease > 0)
			return new QuoteStripPurgeResult(runId, 0, true);
		if (!force) {
			Integer appliedCount = quoteStripConversionMapper.countAudit(runId, STATUS_APPLIED);
			if (appliedCount != null && appliedCount > 0)
				return new QuoteStripPurgeResult(runId, 0, true);
		}
		int purged = quoteStripConversionMapper.deleteAudit(runId);
		quoteStripConversionMapper.deleteRun(runId);
		return new QuoteStripPurgeResult(runId, purged, false);
	}

	private boolean runExists(UUID runId) {
		Integer count = quoteStripConversionMapper.countRun(runId);
		return count != null && count > 0;
	}

	private int countAudit(UUID runId, String status) {
		Integer count = quoteStripConversionMapper.countAudit(runId, status);
		return count == null ? 0 : count;
	}

	public static String completionStatus(int residualRows, String completeStatus, String partialStatus) {
		return residualRows == 0 ? completeStatus : partialStatus;
	}

	private QuoteStripReport loadExistingReport(UUID runId) {
		QuoteStripConversionMapper.QuoteStripRunSummary summary = quoteStripConversionMapper.loadRunSummary(runId);
		return new QuoteStripReport(runId, summary.getStatus(), summary.getCandidateRows(), summary.getPlannedRows(),
				summary.getPlannedQuotes(), Map.of(), Map.of(), sampleOfAudit(runId));
	}

	private List<PlannedSample> sampleOfAudit(UUID runId) {
		List<PlannedSample> sample = new ArrayList<>();
		for (QuoteStripConversionMapper.QuoteStripAuditRow row : quoteStripConversionMapper.sampleAudit(runId))
			sample.add(new PlannedSample(row.getMessageHistoryId(), row.getMessageId(), row.getBeforeText(),
					row.getAfterText()));
		return sample;
	}

	private Plan buildPlan() {
		List<CandidateRow> candidates = loadCandidateRows();
		Map<Integer, OffsetDateTime> maxQuoterTs = buildMaxQuoterTsIndex();

		Plan plan = new Plan(candidates.size());
		List<CandidateRow> eligible = new ArrayList<>();
		Set<Integer> sourceIds = new HashSet<>();
		for (CandidateRow candidate : candidates) {
			Optional<String> pregate =
					pregateReason(candidate.createdTs(), candidate.messageId(), maxQuoterTs);
			if (pregate.isPresent()) {
				bump(plan.pregateHistogram, pregate.get());
				continue;
			}
			eligible.add(candidate);
			sourceIds.addAll(sourceReferences.collectSourceReferenceIds(candidate.messageText()));
		}

		final int resolveBatchSize = 10000;
		Map<Integer, NavigableMap<OffsetDateTime, String>> resolved = new HashMap<>();
		List<Integer> sourceIdList = new ArrayList<>(sourceIds);
		for (int start = 0; start < sourceIdList.size(); start += resolveBatchSize) {
			Set<Integer> chunk = new HashSet<>(
					sourceIdList.subList(start, Math.min(start + resolveBatchSize, sourceIdList.size())));
			resolved.putAll(sourceReferences.everyRevisionOfTheSourcesNamed(chunk));
		}

		long[] stripCounter = new long[1];
		for (CandidateRow candidate : eligible) {
			long stripBefore = stripCounter[0];
			int keepBefore = totalCount(plan.keepReasonHistogram);
			RowStripContext context =
					new RowStripContext(candidate.createdTs(), resolved, plan.keepReasonHistogram, stripCounter);
			String afterText = quoteStripPlanner.stripFaithfulMsgQuotes(candidate.messageText(), context);
			if (!candidate.messageText().equals(afterText)) {
				plan.plannedRows.add(new PlannedRow(candidate.messageHistoryId(), candidate.messageId(),
						candidate.messageText(), afterText));
			} else if (stripCounter[0] == stripBefore && totalCount(plan.keepReasonHistogram) == keepBefore) {
				bump(plan.keepReasonHistogram, KEEP_NO_MSG_QUOTE);
			}
		}
		plan.plannedQuotes = stripCounter[0];
		return plan;
	}

	private void persistPlanned(UUID runId, List<PlannedRow> plannedRows) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		for (PlannedRow row : plannedRows)
			quoteStripConversionMapper.insertAudit(runId, row.messageHistoryId(), row.messageId(), row.beforeText(),
					row.afterText(), STATUS_PLANNED, now);
	}

	private List<AuditRow> loadAudit(UUID runId, String status) {
		List<AuditRow> rows = new ArrayList<>();
		for (QuoteStripConversionMapper.QuoteStripAuditRow row : quoteStripConversionMapper.loadAudit(runId, status))
			rows.add(new AuditRow(row.getMessageHistoryId(), row.getMessageId(), row.getBeforeText(),
					row.getAfterText()));
		return rows;
	}

	private List<CandidateRow> loadCandidateRows() {
		MessageHistoryDboExample example = new MessageHistoryDboExample();
		example.createCriteria().andCurrentFlagEqualTo(true).andMigrationHashIsNotNull()
				.andMessageTextLike("%[quote%");
		List<CandidateRow> candidates = new ArrayList<>();
		for (MessageHistoryDbo row : messageHistoryMapper.selectByExample(example))
			candidates.add(new CandidateRow(row.getMessageHistoryId(), row.getMessageId(), row.getMessageText(),
					row.getCreatedTs()));
		return candidates;
	}

	private Map<Integer, OffsetDateTime> buildMaxQuoterTsIndex() {
		Map<Integer, OffsetDateTime> maxQuoterTs = new HashMap<>();
		for (QuoteStripConversionMapper.QuoterTimestampRow row : quoteStripConversionMapper.loadQuoterTimestampRows()) {
			OffsetDateTime quoterTs = row.getCreatedTs();
			if (quoterTs == null)
				continue;
			for (Integer quotedMsgId : sourceReferences.collectSourceReferenceIds(row.getMessageText()))
				maxQuoterTs.merge(quotedMsgId, quoterTs, QuoteStripService::latest);
		}
		return maxQuoterTs;
	}


	private List<PlannedSample> sampleOf(List<PlannedRow> plannedRows) {
		List<PlannedRow> shuffled = new ArrayList<>(plannedRows);
		Collections.shuffle(shuffled);
		List<PlannedSample> sample = new ArrayList<>();
		for (PlannedRow row : shuffled.subList(0, Math.min(SAMPLE_CAP, shuffled.size())))
			sample.add(new PlannedSample(row.messageHistoryId(), row.messageId(), row.beforeText(), row.afterText()));
		return sample;
	}

	private static Optional<String> pregateReason(OffsetDateTime createdTs, Integer messageId,
			Map<Integer, OffsetDateTime> maxQuoterTs) {
		if (createdTs == null)
			return Optional.of(PREGATE_NULL_TS);
		OffsetDateTime quoterFloor = maxQuoterTs.get(messageId);
		if (quoterFloor != null && !quoterFloor.isBefore(createdTs))
			return Optional.of(PREGATE_LIVE_SPLICE_SOURCE);
		return Optional.empty();
	}

	private static void bump(Map<String, Long> histogram, String reason) {
		histogram.merge(reason, 1L, Long::sum);
	}

	private static int totalCount(Map<String, Long> histogram) {
		long total = 0;
		for (Long value : histogram.values())
			total += value;
		return (int) total;
	}

	private static OffsetDateTime latest(OffsetDateTime left, OffsetDateTime right) {
		return left.isAfter(right) ? left : right;
	}

	public static String normalize(String text) {
		if (text == null)
			return "";
		String canonical = LITERAL_BR.matcher(text).replaceAll("\n").replace("\r\n", "\n").replace("\r", "\n");
		int start = 0;
		int end = canonical.length();
		while (start < end && Character.isWhitespace(canonical.charAt(start)))
			start++;
		while (end > start && Character.isWhitespace(canonical.charAt(end - 1)))
			end--;
		return canonical.substring(start, end);
	}

	public static final class RowStripContext implements QuoteStripPlanner.StripContext {

		private final OffsetDateTime floorTs;
		private final Map<Integer, NavigableMap<OffsetDateTime, String>> revisionsBySourceId;
		private final Map<String, Long> keepReasonHistogram;
		private final long[] stripCounter;

		public RowStripContext(OffsetDateTime floorTs,
				Map<Integer, NavigableMap<OffsetDateTime, String>> revisionsBySourceId,
				Map<String, Long> keepReasonHistogram, long[] stripCounter) {
			this.floorTs = floorTs;
			this.revisionsBySourceId = revisionsBySourceId;
			this.keepReasonHistogram = keepReasonHistogram;
			this.stripCounter = stripCounter;
		}

		@Override
		public String resolveFloorBody(Integer msgId) {
			NavigableMap<OffsetDateTime, String> revisions = revisionsBySourceId.get(msgId);
			if (revisions == null)
				return null;
			Map.Entry<OffsetDateTime, String> floor = revisions.floorEntry(floorTs);
			return floor == null ? null : floor.getValue();
		}

		@Override
		public String normalize(String text) {
			return QuoteStripService.normalize(text);
		}

		@Override
		public void recordStrip(Integer msgId) {
			stripCounter[0]++;
		}

		@Override
		public void recordKeep(Integer msgId, String reason) {
			bump(keepReasonHistogram, reason);
		}
	}
}
