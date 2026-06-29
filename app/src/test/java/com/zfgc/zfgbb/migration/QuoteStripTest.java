package com.zfgc.zfgbb.migration;

import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.codeConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.quoteConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.resolved;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.lang.reflect.Modifier;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.zfgc.zfgbb.content.renderer.BBCodeService;
import com.zfgc.zfgbb.content.renderer.QuotedMessageLookup;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations;
import com.zfgc.zfgbb.services.conversion.QuoteStripPlanner;
import com.zfgc.zfgbb.services.conversion.QuoteStripService;

class QuoteStripTest {

	private static final OffsetDateTime SOURCE_TS = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	private static final OffsetDateTime QUOTING_TS = OffsetDateTime.of(2020, 6, 1, 0, 0, 0, 0, ZoneOffset.UTC);

	private BBCodeService bbCodeService;
	private QuoteStripPlanner planner;

	@BeforeEach
	void setup() {
		bbCodeService = new BBCodeService();
		bbCodeService.validBbCodes = Map.of("QUOTE", quoteConfig(), "CODE", codeConfig());
		planner = new QuoteStripPlanner();
		ReflectionTestUtils.setField(planner, "bbCodeService", bbCodeService);
	}

	private static final class StubContext implements QuoteStripPlanner.StripContext {
		private final Map<Integer, String> sourceBodies;
		final List<String> keeps = new ArrayList<>();
		int strips = 0;

		StubContext(Map<Integer, String> sourceBodies) {
			this.sourceBodies = sourceBodies;
		}

		@Override
		public String resolveFloorBody(Integer msgId) {
			return sourceBodies.get(msgId);
		}

		@Override
		public String normalize(String text) {
			return QuoteStripService.normalize(text);
		}

		@Override
		public void recordStrip(Integer msgId) {
			strips++;
		}

		@Override
		public void recordKeep(Integer msgId, String reason) {
			keeps.add(reason);
		}
	}

	@Nested
	class Lifecycle {

		@Test
		void leaseMigrationMapsLegacyActiveDirectionsToResumableStates() throws Exception {
			String migration;
			try (java.io.InputStream stream = getClass().getResourceAsStream(
					"/db/migration/tables/conversion/V20260719.2__add-quote-strip-run-lease.sql")) {
				assertTrue(stream != null, "quote lease migration must be packaged");
				migration = new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
			}
			assertTrue(migration.contains("when 'APPLYING' then 'APPLY_PARTIAL'"));
			assertTrue(migration.contains("when 'REVERTING' then 'REVERT_PARTIAL'"));
			assertTrue(migration.contains("else 'FAILED'"));
		}

		@ParameterizedTest
		@CsvSource({
				"applyWithResidualMismatchStaysRecoverable, 1, APPLIED, APPLY_PARTIAL, APPLY_PARTIAL",
				"revertWithResidualExceptionStaysRecoverable, 2, REVERTED, REVERT_PARTIAL, REVERT_PARTIAL",
				"operationOnlyBecomesTerminalWithoutResidualRows, 0, APPLIED, APPLY_PARTIAL, APPLIED" })
		void completionStatusStaysRecoverableWhileResidualRowsRemain(String caseName, int residualRows,
				String completeStatus, String partialStatus, String expected) {
			assertEquals(expected, QuoteStripService.completionStatus(residualRows, completeStatus, partialStatus));
		}

		@Test
		void reportContractCarriesOperationStatus() {
			QuoteStripOperations.QuoteStripReport dryRun = new QuoteStripOperations.QuoteStripReport(
					null, null, 3, 2, 2, Map.of(), Map.of(), List.of());
			QuoteStripOperations.QuoteStripReport persisted = new QuoteStripOperations.QuoteStripReport(
					java.util.UUID.randomUUID(), "PLANNED", 3, 2, 2, Map.of(), Map.of(), List.of());
			assertNull(dryRun.status());
			assertEquals("PLANNED", persisted.status());
		}
	}

	@Nested
	class SchemaGuard {

		@Test
		void quoteStripRunColumnSetMatchesTheFromScratchMigrations() {
			assertEquals(
					Set.of("run_id", "status", "candidate_rows", "planned_rows", "planned_quotes",
							"created_ts", "updated_ts", "lease_owner", "lease_expires_ts", "heartbeat_ts", "attempt_no"),
					effectiveColumns("quote_strip_run"),
					"quote_strip_run columns drifted from the raw SQL QuoteStripConversionMapper depends on");
		}

		@Test
		void quoteStripAuditColumnSetMatchesTheFromScratchMigration() {
			assertEquals(
					Set.of("quote_strip_audit_id", "run_id", "message_history_id", "message_id",
							"before_text", "after_text", "status", "planned_ts", "applied_ts"),
					effectiveColumns("quote_strip_audit"),
					"quote_strip_audit columns drifted from the raw SQL QuoteStripConversionMapper depends on");
		}
	}

	@Nested
	class PlannerStrip {

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.migration.QuoteStripTest#faithfulStripCases")
		void faithfulQuotesAreStrippedToEmptyBodies(String caseName, String sourceBody, String input,
				String expectedResult) {
			StubContext context = new StubContext(Map.of(5, sourceBody));

			assertEquals(expectedResult, planner.stripFaithfulMsgQuotes(input, context));
			assertEquals(1, context.strips);
		}

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.migration.QuoteStripTest#keptQuoteCases")
		void unfaithfulOrUnsafeQuotesAreKeptWithReason(String caseName, String sourceBody, String input,
				String expectedKeepReason) {
			StubContext context = new StubContext(sourceBody == null ? Map.of() : Map.of(5, sourceBody));

			assertEquals(input, planner.stripFaithfulMsgQuotes(input, context));
			assertEquals(0, context.strips);
			assertEquals(List.of(expectedKeepReason), context.keeps);
		}

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.migration.QuoteStripTest#untouchedInputCases")
		void nonStrippableInputPassesThroughWithoutStripOrKeep(String caseName, String input) {
			StubContext context = new StubContext(Map.of(5, "body text"));

			assertEquals(input, planner.stripFaithfulMsgQuotes(input, context));
			assertEquals(0, context.strips);
			assertTrue(context.keeps.isEmpty());
		}

		@Test
		void mixedSiblingsStripOnlyFaithfulOne() {
			Map<Integer, String> sources = new HashMap<>();
			sources.put(5, "aaa");
			sources.put(6, "totally different");
			StubContext context = new StubContext(sources);
			String result = planner.stripFaithfulMsgQuotes("[quote msg=5]aaa[/quote] middle [quote msg=6]bbb[/quote]",
					context);

			assertEquals("[quote msg=5][/quote] middle [quote msg=6]bbb[/quote]", result);
			assertEquals(1, context.strips);
			assertEquals(List.of(QuoteStripPlanner.KEEP_MODIFIED), context.keeps);
		}

		@Test
		void nullInputReturnsNull() {
			assertEquals(null, planner.stripFaithfulMsgQuotes(null, new StubContext(Map.of())));
		}
	}

	static Stream<Arguments> faithfulStripCases() {
		return Stream.of(
				arguments("faithfulQuoteIsStrippedToEmptyBody", "hello world",
						"[quote thread=3 msg=5]hello world[/quote]", "[quote thread=3 msg=5][/quote]"),
				arguments("boundaryWhitespaceAndBreakVariantsStillStrip", "  hello world\r\n",
						"[quote msg=5]<br/>hello world<br/>[/quote]", "[quote msg=5][/quote]"),
				arguments("internalBreakVersusNewlineStillStrips", "line1\nline2",
						"[quote msg=5]line1<br/>line2[/quote]", "[quote msg=5][/quote]"),
				arguments("quoteInsideCodeIsNotTreatedAsNestedAndStrips", "see [code][quote msg=7]q[/quote][/code]",
						"[quote msg=5]see [code][quote msg=7]q[/quote][/code][/quote]", "[quote msg=5][/quote]"),
				arguments("openerAttributeBytesArePreservedOnStrip", "hello",
						"[quote author=Bob thread=3 msg=5]hello[/quote]", "[quote author=Bob thread=3 msg=5][/quote]"));
	}

	static Stream<Arguments> keptQuoteCases() {
		return Stream.of(
				arguments("trimmedSourceIsKept", "hello world with more",
						"[quote msg=5]hello world[/quote]", QuoteStripPlanner.KEEP_MODIFIED),
				arguments("ellipsisTruncationIsKept", "hello world, this is the full sentence",
						"[quote msg=5]hello world...[/quote]", QuoteStripPlanner.KEEP_MODIFIED),
				arguments("insertedTextIsKept", "the quick brown fox",
						"[quote msg=5]the quick RED brown fox[/quote]", QuoteStripPlanner.KEEP_MODIFIED),
				arguments("nestedQuoteInsideEmbeddedIsKept", "does not matter",
						"[quote msg=5]outer [quote msg=7]inner[/quote][/quote]",
						QuoteStripPlanner.KEEP_NESTED_EMBEDDED),
				arguments("nestedQuoteInsideSourceIsKept", "reply [quote msg=9]grandparent[/quote]",
						"[quote msg=5]reply body[/quote]", QuoteStripPlanner.KEEP_SOURCE_NESTED),
				arguments("unresolvedSourceIsKept", null,
						"[quote msg=5]some body[/quote]", QuoteStripPlanner.KEEP_SOURCE_UNAVAILABLE),
				arguments("blankSourceIsKept", "   \n  ",
						"[quote msg=5]some body[/quote]", QuoteStripPlanner.KEEP_SOURCE_UNAVAILABLE),
				arguments("blankEmbeddedIsKept", "real source body",
						"[quote msg=5]   [/quote]", QuoteStripPlanner.KEEP_BLANK_EMBEDDED),
				arguments("alreadyEmptyBodyIsNoOp", "source",
						"[quote msg=5][/quote]", QuoteStripPlanner.KEEP_BLANK_EMBEDDED));
	}

	static Stream<Arguments> untouchedInputCases() {
		return Stream.of(
				arguments("malformedUnclosedQuoteIsKeptWithoutCorruption", "[quote msg=5]body text with no closer"),
				arguments("nonMsgQuoteIsLeftByteIdentical", "[quote author=Bob]legacy body[/quote]"));
	}

	@Nested
	class Normalize {

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.migration.QuoteStripTest#normalizeCases")
		void normalizeCanonicalizesBodies(String caseName, String input, String expected) {
			assertEquals(expected, QuoteStripService.normalize(input));
		}
	}

	static Stream<Arguments> normalizeCases() {
		return Stream.of(
				arguments("normalizeCanonicalizesBreakVariants bareBr", "a<br>b", "a\nb"),
				arguments("normalizeCanonicalizesBreakVariants selfClosingBr", "a<br/>b", "a\nb"),
				arguments("normalizeCanonicalizesBreakVariants spacedSelfClosingBr", "a<br />b", "a\nb"),
				arguments("normalizeCanonicalizesCarriageReturns", "a\r\nb\rc", "a\nb\nc"),
				arguments("normalizeTrimsBoundaryButKeepsInternalRuns spaces", "  a  b  ", "a  b"),
				arguments("normalizeTrimsBoundaryButKeepsInternalRuns newlines", "\n\na\nb\n\n", "a\nb"),
				arguments("normalizeNullIsEmpty", null, ""));
	}

	@Nested
	class Pregate {

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.migration.QuoteStripTest#pregateCases")
		void pregateReasonDecidesRowEligibility(String caseName, OffsetDateTime createdTs,
				Set<Integer> duplicateCurrentMessageIds, Map<Integer, OffsetDateTime> laterQuoterFloors,
				String expectedReason) {
			assertEquals(expectedReason,
					QuoteStripService.pregateReason(createdTs, 5, duplicateCurrentMessageIds, laterQuoterFloors));
		}
	}

	static Stream<Arguments> pregateCases() {
		return Stream.of(
				arguments("pregateNullCreatedTsIsSkipped", null, Set.of(), Map.of(),
						QuoteStripService.PREGATE_NULL_TS),
				arguments("pregateDuplicateCurrentIsSkipped", QUOTING_TS, Set.of(5), Map.of(),
						QuoteStripService.PREGATE_DUPLICATE_CURRENT),
				arguments("pregateLaterQuoterFlooringExactlyOnCurrentExcludes", SOURCE_TS, Set.of(),
						Map.of(5, SOURCE_TS), QuoteStripService.PREGATE_LIVE_SPLICE_SOURCE),
				arguments("pregateLaterQuoterAfterCurrentExcludes", SOURCE_TS, Set.of(),
						Map.of(5, SOURCE_TS.plusSeconds(1)), QuoteStripService.PREGATE_LIVE_SPLICE_SOURCE),
				arguments("pregateEarlierQuoterIsEligible", SOURCE_TS, Set.of(),
						Map.of(5, SOURCE_TS.minusSeconds(1)), null),
				arguments("pregateNoQuoterIsEligible", SOURCE_TS, Set.of(), Map.of(), null));
	}

	@Nested
	class RowContext {

		private QuoteStripService.RowStripContext rowContext(Map<Integer, QuotedMessageLookup.Resolved> resolvedSources,
				Map<String, Long> keeps, long[] strips) {
			return new QuoteStripService.RowStripContext(QUOTING_TS, resolvedSources, keeps, strips);
		}

		@Test
		void notPermittedSourceIsKeptEvenWhenBodyIsFaithful() {
			Map<String, Long> keeps = new HashMap<>();
			long[] strips = new long[1];
			QuoteStripService.RowStripContext context =
					rowContext(Map.of(5, resolved(false, SOURCE_TS, "hello world")), keeps, strips);
			String input = "[quote thread=3 msg=5]hello world[/quote]";

			assertEquals(input, planner.stripFaithfulMsgQuotes(input, context));
			assertEquals(0L, strips[0]);
			assertEquals(Map.of(QuoteStripPlanner.KEEP_SOURCE_UNAVAILABLE, 1L), keeps);
		}

		@Test
		void permittedSourceWithSameFaithfulBodyStrips() {
			Map<String, Long> keeps = new HashMap<>();
			long[] strips = new long[1];
			QuoteStripService.RowStripContext context =
					rowContext(Map.of(5, resolved(true, SOURCE_TS, "hello world")), keeps, strips);
			String input = "[quote thread=3 msg=5]hello world[/quote]";

			assertEquals("[quote thread=3 msg=5][/quote]", planner.stripFaithfulMsgQuotes(input, context));
			assertEquals(1L, strips[0]);
			assertTrue(keeps.isEmpty());
		}
	}

	@Nested
	class OrderGuard {

		@Test
		void migrationE2eSubclassesDeclareDistinctClassOrdersAndTheCorpusMutatingClassRunsLast() {
			JavaClasses migrationPackageClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.importPackages("com.zfgc.zfgbb.migration");
			List<Class<?>> concreteSubclasses = new ArrayList<>();
			for (JavaClass javaClass : migrationPackageClasses) {
				if (!javaClass.isAssignableTo(MigrationE2E.class))
					continue;
				Class<?> subclass = javaClass.reflect();
				if (!Modifier.isAbstract(subclass.getModifiers()))
					concreteSubclasses.add(subclass);
			}
			assertFalse(concreteSubclasses.isEmpty(), "the MigrationE2E family must be on the test classpath");

			Map<Integer, Class<?>> subclassesByOrderValue = new HashMap<>();
			for (Class<?> subclass : concreteSubclasses) {
				Order order = subclass.getDeclaredAnnotation(Order.class);
				assertNotNull(order, subclass.getName()
						+ " must declare a class-level @Order so the shared-corpus run sequence stays explicit");
				Class<?> collidingSubclass = subclassesByOrderValue.put(order.value(), subclass);
				assertNull(collidingSubclass, subclass.getName() + " reuses @Order(" + order.value() + ") already held by "
						+ (collidingSubclass == null ? "" : collidingSubclass.getName()));
			}

			int maximumOrderValue = subclassesByOrderValue.keySet().stream().max(Integer::compare).orElseThrow();
			assertEquals(MigrationAdminToolsTest.class, subclassesByOrderValue.get(maximumOrderValue),
					"the corpus-mutating MigrationAdminToolsTest must hold the maximum @Order so it runs last");
		}
	}

	private static final List<String> QUOTE_STRIP_MIGRATIONS = List.of(
			"/db/migration/tables/conversion/V20260718.2__add-quote-strip-audit.sql",
			"/db/migration/tables/conversion/V20260719.2__add-quote-strip-run-lease.sql");

	private static final Set<String> NON_COLUMN_LEADING_TOKENS =
			Set.of("constraint", "primary", "unique", "foreign", "check", "index");

	private static final Pattern COLUMN_NAME = Pattern.compile("^[a-z_][a-z0-9_]*$");

	private static final Pattern ADD_COLUMN =
			Pattern.compile("add\\s+column\\s+(?:if\\s+not\\s+exists\\s+)?(\\w+)", Pattern.CASE_INSENSITIVE);

	static Set<String> effectiveColumns(String tableName) {
		Set<String> columns = new LinkedHashSet<>();
		for (String migrationResource : QUOTE_STRIP_MIGRATIONS)
			for (String statement : readMigration(migrationResource).split(";"))
				collectColumns(statement, tableName, columns);
		return columns;
	}

	private static void collectColumns(String statement, String tableName, Set<String> columns) {
		String normalized = statement.trim().toLowerCase(Locale.ROOT);
		String qualifiedTable = "zfgbb." + tableName;
		if (normalized.startsWith("create table") && normalized.contains(qualifiedTable + " (")) {
			collectCreateTableColumns(statement, columns);
			return;
		}
		if (normalized.startsWith("alter table") && normalized.contains(qualifiedTable))
			collectAlterAddColumns(statement, columns);
	}

	private static void collectCreateTableColumns(String statement, Set<String> columns) {
		int bodyStart = statement.indexOf('(');
		int bodyEnd = statement.lastIndexOf(')');
		if (bodyStart < 0 || bodyEnd < bodyStart)
			return;
		for (String line : statement.substring(bodyStart + 1, bodyEnd).split("\n")) {
			String trimmedLine = line.trim();
			if (trimmedLine.isEmpty())
				continue;
			String leadingToken = trimmedLine.split("\\s+")[0].toLowerCase(Locale.ROOT);
			if (COLUMN_NAME.matcher(leadingToken).matches() && !NON_COLUMN_LEADING_TOKENS.contains(leadingToken))
				columns.add(leadingToken);
		}
	}

	private static void collectAlterAddColumns(String statement, Set<String> columns) {
		Matcher matcher = ADD_COLUMN.matcher(statement);
		while (matcher.find())
			columns.add(matcher.group(1).toLowerCase(Locale.ROOT));
	}

	private static String readMigration(String resource) {
		try (java.io.InputStream stream = QuoteStripTest.class.getResourceAsStream(resource)) {
			assertNotNull(stream, resource + " must be packaged on the classpath");
			return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
		} catch (java.io.IOException failure) {
			throw new java.io.UncheckedIOException(failure);
		}
	}
}
