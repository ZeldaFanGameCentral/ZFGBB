package com.zfgc.zfgbb.migrator.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.test.util.ReflectionTestUtils;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.converters.MessageHistoryConverter.HistoryInterval;
import com.zfgc.zfgbb.migrator.converters.MessageHistoryConverter.ExistingCandidate;
import com.zfgc.zfgbb.migrator.converters.MessageHistoryConverter.Reconciliation;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;

class MessageHistoryConverterTest {

	@Test
	void previousHashTimelineRowsAreReusedByStableOrdinal() {
		assertEquals(List.of(91, 92, 93),
				MessageHistoryConverter.assignExistingIds(3, List.of(91, 92, 93)));
	}

	@Test
	void rerunTwiceDoesNotAllocateAdditionalTimelineRows() {
		List<Integer> first = MessageHistoryConverter.assignExistingIds(2, List.of(41, 42));
		assertEquals(first, MessageHistoryConverter.assignExistingIds(2, first));
	}

	@Test
	void newlyInsertedLegacyHistoryOnlyAllocatesTheNewOrdinal() {
		assertEquals(java.util.Arrays.asList(41, 42, null),
				MessageHistoryConverter.assignExistingIds(3, List.of(41, 42)));
	}

	@Test
	void duplicateExactHashesConsumeDistinctStableIds() {
		List<ExistingCandidate> existing = List.of(
				new ExistingCandidate(41, "same"), new ExistingCandidate(42, "same"),
				new ExistingCandidate(43, "old-current"));
		assertEquals(List.of(41, 42, 43),
				MessageHistoryConverter.reconcileIds(List.of("same", "same", "new-current"), existing));
		assertEquals(List.of(41, 42, 43),
				MessageHistoryConverter.reconcileIds(List.of("same", "same", "new-current"), existing));
	}

	@Test
	void shrinkingRerunReusesOneRowAndIdentifiesReferencedOldCurrentAsSurplus() {
		List<ExistingCandidate> existing = List.of(
				new ExistingCandidate(41, "old-history"),
				new ExistingCandidate(42, "old-current"));

		Reconciliation first = MessageHistoryConverter.reconcile(List.of("new-current"), existing);
		assertEquals(List.of(41), first.assignedIds());
		assertEquals(List.of(42), first.surplusIds());

		Reconciliation rerun = MessageHistoryConverter.reconcile(
				List.of("new-current"), List.of(new ExistingCandidate(41, "new-current"),
						new ExistingCandidate(42, "old-current")));
		assertEquals(List.of(41), rerun.assignedIds());
		assertEquals(List.of(42), rerun.surplusIds());
	}

	private static SMFMessageHistoryDb historyRow(int modifiedTime, String body) {
		SMFMessageHistoryDb row = new SMFMessageHistoryDb();
		row.setModifiedTime(modifiedTime);
		row.setBody(body);
		return row;
	}

	private static void assertMonotonicNonDecreasing(List<HistoryInterval> intervals) {
		for (int i = 1; i < intervals.size(); i++) {
			assertFalse(intervals.get(i).createdTs().isBefore(intervals.get(i - 1).createdTs()),
					"created_ts must never decrease across reconstructed rows");
		}
	}

	private static void assertExactlyOneCurrentAndLast(List<HistoryInterval> intervals) {
		long currentCount = intervals.stream().filter(HistoryInterval::currentFlag).count();
		assertEquals(1, currentCount, "exactly one row may carry current_flag=true");
		assertTrue(intervals.get(intervals.size() - 1).currentFlag(), "the current row must be last (newest body)");
	}

	private static void assertCurrentCarriesGreatestCreatedTs(List<HistoryInterval> intervals) {
		HistoryInterval current = intervals.stream().filter(HistoryInterval::currentFlag).findFirst().orElseThrow();
		HistoryInterval greatest = intervals.stream()
				.max((a, b) -> a.createdTs().compareTo(b.createdTs())).orElseThrow();
		assertFalse(current.createdTs().isBefore(greatest.createdTs()),
				"current_flag=true must sit on the greatest created_ts");
	}

	@Test
	void editedMessageReconstructsAscendingIntervals() {
		List<HistoryInterval> intervals = MessageHistoryConverter.reconstructIntervals(
				SmfTimes.fromEpochSeconds(1000), "C",
				List.of(historyRow(3000, "P2"), historyRow(2000, "P1")));

		assertEquals(3, intervals.size());
		assertEquals(new HistoryInterval(SmfTimes.fromEpochSeconds(1000), "P1", false), intervals.get(0));
		assertEquals(new HistoryInterval(SmfTimes.fromEpochSeconds(2000), "P2", false), intervals.get(1));
		assertEquals(new HistoryInterval(SmfTimes.fromEpochSeconds(3000), "C", true), intervals.get(2));
		assertMonotonicNonDecreasing(intervals);
		assertExactlyOneCurrentAndLast(intervals);
		assertCurrentCarriesGreatestCreatedTs(intervals);
	}

	@Test
	void neverEditedMessageEmitsSingleCurrentRow() {
		List<HistoryInterval> intervals = MessageHistoryConverter.reconstructIntervals(
				SmfTimes.fromEpochSeconds(1000), "Only", null);

		assertEquals(1, intervals.size());
		assertEquals(new HistoryInterval(SmfTimes.fromEpochSeconds(1000), "Only", true), intervals.get(0));
		assertExactlyOneCurrentAndLast(intervals);
		assertCurrentCarriesGreatestCreatedTs(intervals);
	}

	@Test
	void emptyHistoryEmitsSingleCurrentRow() {
		List<HistoryInterval> intervals = MessageHistoryConverter.reconstructIntervals(
				SmfTimes.fromEpochSeconds(1000), "Only", List.of());

		assertEquals(1, intervals.size());
		assertEquals(new HistoryInterval(SmfTimes.fromEpochSeconds(1000), "Only", true), intervals.get(0));
		assertExactlyOneCurrentAndLast(intervals);
		assertCurrentCarriesGreatestCreatedTs(intervals);
	}

	@Test
	void anomalousEditBeforePostIsClampedMonotonic() {
		List<HistoryInterval> intervals = MessageHistoryConverter.reconstructIntervals(
				SmfTimes.fromEpochSeconds(5000), "C",
				List.of(historyRow(2000, "P1")));

		assertEquals(2, intervals.size());
		assertMonotonicNonDecreasing(intervals);
		assertEquals(SmfTimes.fromEpochSeconds(5000), intervals.get(0).createdTs());
		assertEquals("P1", intervals.get(0).body());
		assertEquals(SmfTimes.fromEpochSeconds(5000), intervals.get(1).createdTs());
		assertTrue(intervals.get(1).currentFlag());
		assertExactlyOneCurrentAndLast(intervals);
		assertCurrentCarriesGreatestCreatedTs(intervals);
	}

	@Test
	void bodiesAreHtmlUnescaped() {
		List<HistoryInterval> intervals = MessageHistoryConverter.reconstructIntervals(
				SmfTimes.fromEpochSeconds(1000), "a &amp; b", null);

		assertEquals("a & b", intervals.get(0).body());
	}

	@Test
	void processBatchPersistsReconstructedTimestampsByPrimaryKeyAfterInsertFlush() {
		MessageHistoryConverter converter = new MessageHistoryConverter();
		SqlSessionFactory sqlSessionFactory = mock(SqlSessionFactory.class);
		SqlSession batchSession = mock(SqlSession.class);
		MessageHistoryDboMapper historyMapper = mock(MessageHistoryDboMapper.class);
		MigratorTimestampMapper timestampMapper = mock(MigratorTimestampMapper.class);

		when(sqlSessionFactory.openSession(ExecutorType.BATCH, false)).thenReturn(batchSession);
		when(batchSession.getMapper(MessageHistoryDboMapper.class)).thenReturn(historyMapper);
		when(batchSession.getMapper(MigratorTimestampMapper.class)).thenReturn(timestampMapper);
		int[] generatedId = { 1000 };
		doAnswer(invocation -> {
			((MessageHistoryDbo) invocation.getArgument(0)).setMessageHistoryId(generatedId[0]++);
			return 1;
		}).when(historyMapper).insert(any());

		ReflectionTestUtils.setField(converter, "sqlSessionFactory", sqlSessionFactory);
		ReflectionTestUtils.setField(converter, "freshRun", true);
		ReflectionTestUtils.setField(converter, "messageIdMap", Map.of(42, 100));

		SMFMessageDbWithBLOBs message = new SMFMessageDbWithBLOBs();
		message.setIdMsg(42);
		message.setPosterTime(2000);
		message.setBody("C");
		message.setPosterIp("127.0.0.1");

		Map<Integer, List<SMFMessageHistoryDb>> historyByMsgId =
				Map.of(42, List.of(historyRow(2000, "P1"), historyRow(2000, "P1")));

		ReflectionTestUtils.invokeMethod(converter, "processBatch",
				List.of(message), historyByMsgId, Map.of());

		ArgumentCaptor<MessageHistoryDbo> insertedRows = ArgumentCaptor.forClass(MessageHistoryDbo.class);
		verify(historyMapper, times(3)).insert(insertedRows.capture());

		ArgumentCaptor<Integer> historyIds = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<OffsetDateTime> createdTimes = ArgumentCaptor.forClass(OffsetDateTime.class);
		verify(timestampMapper, times(3))
				.setMessageHistoryTimestamps(historyIds.capture(), createdTimes.capture(), any());

		assertEquals(
				insertedRows.getAllValues().stream().map(MessageHistoryDbo::getMessageHistoryId).toList(),
				historyIds.getAllValues(),
				"each persisted interval must be re-stamped by its generated primary key");
		assertEquals(
				List.of(SmfTimes.fromEpochSeconds(2000), SmfTimes.fromEpochSeconds(2000),
						SmfTimes.fromEpochSeconds(2000)),
				createdTimes.getAllValues(),
				"reconstructed SMF times must be persisted, not the DB insert-time default");

		InOrder ordered = inOrder(historyMapper, batchSession, timestampMapper);
		ordered.verify(historyMapper, times(3)).insert(any());
		ordered.verify(batchSession).flushStatements();
		ordered.verify(timestampMapper, times(3)).setMessageHistoryTimestamps(any(), any(), any());
		ordered.verify(batchSession).commit();
	}
}
