package com.zfgc.zfgbb.migrator.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface QuoteStripOperations {

	QuoteStripReport report(UUID runId);

	QuoteStripApplyResult apply(UUID runId);

	QuoteStripRevertResult revert(UUID runId);

	QuoteStripPurgeResult purge(UUID runId, boolean force);

	record PlannedSample(Integer messageHistoryId, Integer messageId, String beforeText, String afterText) {}

	record QuoteStripReport(UUID runId, String status, int candidateRows, int plannedRows, long plannedQuotes,
			Map<String, Long> pregateHistogram, Map<String, Long> keepReasonHistogram, List<PlannedSample> sample) {}

	record QuoteStripApplyResult(UUID runId, int plannedRows, int applied, int skipped, int refused) {}

	record QuoteStripRevertResult(UUID runId, int appliedRows, int reverted, int skipped, int refused) {}

	record QuoteStripPurgeResult(UUID runId, int purged, boolean refused) {}
}
