package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.sql.Connection;
import java.time.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.controller.system.SiteController;
import com.zfgc.zfgbb.model.system.SiteInfo;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.operations.postgres.PostgresAdvisoryLock;
import com.zfgc.zfgbb.services.system.MaintenanceCoordinator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.config.MailDispatcherConfig;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.config.security.PartialInstallGateFilter;
import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.mappers.SystemConfigDboMapper;
import com.zfgc.zfgbb.services.users.MailDispatcher;
import com.zfgc.zfgbb.services.system.AuthoringContentFormat;
import com.zfgc.zfgbb.services.system.InstallPhaseTransactions;
import com.zfgc.zfgbb.services.system.InstallRunRepository;
import com.zfgc.zfgbb.services.system.SystemConfigService;

class PlatformTest {

	record ContextPoisoningMarker() {}

	@TestConfiguration
	static class MarkerContribution {

		@Bean
		ContextPoisoningMarker contextPoisoningMarker() {
			return new ContextPoisoningMarker();
		}
	}

	@Nested
	class InstallGate {

		private record GateRun(MockFilterChain chain, MockHttpServletResponse response) {}

		private InstallRunRepository installs;
		private PartialInstallGateFilter filter;

		@BeforeEach
		void setup() {
			installs = mock(InstallRunRepository.class);
			filter = new PartialInstallGateFilter(installs);
		}

		private GateRun runFilter(String state, String method, String contextPath, String requestUri) throws Exception {
			when(installs.get()).thenReturn(new InstallRunRepository.Run(state, null, null, null, null,
					Optional.empty()));
			MockHttpServletRequest request = new MockHttpServletRequest(method, requestUri);
			request.setContextPath(contextPath);
			MockHttpServletResponse response = new MockHttpServletResponse();
			MockFilterChain chain = new MockFilterChain();
			filter.doFilter(request, response, chain);
			return new GateRun(chain, response);
		}

		@ParameterizedTest
		@MethodSource("gateDecisionCases")
		void gateDecisionMatrix(String caseName, String state, String method, String contextPath, String uri,
				boolean expectPass) throws Exception {
			GateRun run = runFilter(state, method, contextPath, uri);

			if (expectPass) {
				assertNotNull(run.chain().getRequest());
				assertEquals(HttpStatus.OK.value(), run.response().getStatus());
			} else {
				assertNull(run.chain().getRequest());
				assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), run.response().getStatus());
			}
		}

		static Stream<Arguments> gateDecisionCases() {
			return Stream.of(
					arguments("uninstalledStateStillAllowsAllowlistedPaths install",
							"PROVISIONING", "GET", "", "/system/install", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths installStatus",
							"PROVISIONING", "GET", "", "/system/install/status", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths actuatorHealth",
							"PROVISIONING", "GET", "", "/actuator/health", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths actuatorHealthLiveness",
							"PROVISIONING", "GET", "", "/actuator/health/liveness", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths error",
							"PROVISIONING", "GET", "", "/error", true),
					arguments("uninstalledStateAllowsOptionsPreflightRegardlessOfPath",
							"PROVISIONING", "OPTIONS", "", "/thread", true),
					arguments("installedStatePassesEveryRequestThrough",
							"INSTALLED", "GET", "", "/thread", true),
					arguments("allowlistIsEvaluatedAgainstThePathWithTheContextPathStripped allowlisted",
							"PROVISIONING", "GET", "/api", "/api/system/install", true),
					arguments("allowlistIsEvaluatedAgainstThePathWithTheContextPathStripped blocked",
							"PROVISIONING", "GET", "/api", "/api/thread", false),
					arguments("contextPathInstallEndpointRemainsReachableDuringRecovery",
							"FAILED", "POST", "/zfgbb", "/zfgbb/system/install", true),
					arguments("readyStillGatesApplicationButAllowsOptions gated",
							"READY", "GET", "/zfgbb", "/zfgbb/users/register", false),
					arguments("readyStillGatesApplicationButAllowsOptions optionsAllowed",
							"READY", "OPTIONS", "/zfgbb", "/zfgbb/users/register", true),
					arguments("prefixLookalikeDoesNotBypassGate",
							"CORE_READY", "GET", "", "/system/install-secret", false));
		}

		@Test
		void uninstalledStateBlocksNonAllowlistedPathWithServiceUnavailable() throws Exception {
			GateRun run = runFilter("PROVISIONING", "GET", "", "/thread");

			assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), run.response().getStatus());
			assertEquals("Installation recovery is in progress.", run.response().getErrorMessage());
			assertNull(run.chain().getRequest());
		}
	}

	@Nested
	class AdvisoryLockRelease {

		private static final long KEY = 0x5A46474242494E53L;

		private Connection session;
		private DataSource dataSource;

		@BeforeEach
		void setup() throws SQLException {
			session = mock(Connection.class);
			dataSource = mock(DataSource.class);
			when(dataSource.getConnection()).thenReturn(session);
			PreparedStatement acquire = mock(PreparedStatement.class);
			ResultSet acquired = mock(ResultSet.class);
			when(session.prepareStatement("select pg_try_advisory_lock(?)")).thenReturn(acquire);
			when(acquire.executeQuery()).thenReturn(acquired);
			when(acquired.next()).thenReturn(true);
			when(acquired.getBoolean(1)).thenReturn(true);
		}

		private void unlockFailsWith(SQLException failure) throws SQLException {
			PreparedStatement release = mock(PreparedStatement.class);
			when(session.prepareStatement("select pg_advisory_unlock(?)")).thenReturn(release);
			when(release.executeQuery()).thenThrow(failure);
		}

		@Test
		void aSessionWhoseUnlockFailsIsTerminatedRatherThanReturnedToThePoolStillHoldingTheLock()
				throws SQLException {
			unlockFailsWith(new SQLException("connection reset"));
			PostgresAdvisoryLock lock = PostgresAdvisoryLock.tryAcquire(dataSource, KEY).orElseThrow();

			assertThrows(SQLException.class, lock::close);

			verify(session).abort(any());
			verify(session).close();
		}

		@Test
		void aDriverRuntimeFailureDuringAcquisitionStillReturnsTheBorrowedConnection() throws SQLException {
			doThrow(new IllegalStateException("driver blew up")).when(session).setAutoCommit(true);

			assertThrows(IllegalStateException.class, () -> PostgresAdvisoryLock.tryAcquire(dataSource, KEY));

			verify(session).close();
		}

		@Test
		void closingTwiceDoesNotTouchAnAlreadyReturnedSession() throws SQLException {
			PreparedStatement release = mock(PreparedStatement.class);
			ResultSet unlocked = mock(ResultSet.class);
			when(session.prepareStatement("select pg_advisory_unlock(?)")).thenReturn(release);
			when(release.executeQuery()).thenReturn(unlocked);
			when(unlocked.next()).thenReturn(true);
			when(unlocked.getBoolean(1)).thenReturn(true);

			PostgresAdvisoryLock lock = PostgresAdvisoryLock.tryAcquire(dataSource, KEY).orElseThrow();
			lock.close();
			lock.close();

			verify(session, times(1)).close();
		}
	}

	@Nested
	class InstallPhaseIsolation {

		@EnableTransactionManagement
		static class Phases {

			@Bean
			InstallPhaseTransactions phases() {
				return new InstallPhaseTransactions();
			}

			@Bean
			PlatformTransactionManager transactionManager() {
				PlatformTransactionManager transactions = mock(PlatformTransactionManager.class);
				when(transactions.getTransaction(any())).thenReturn(new SimpleTransactionStatus());
				return transactions;
			}
		}

		private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(Phases.class);

		private void assertStartsItsOwnTransaction(Consumer<InstallPhaseTransactions> phase) {
			contextRunner.run(context -> {
				phase.accept(context.getBean(InstallPhaseTransactions.class));

				ArgumentCaptor<TransactionDefinition> started =
						ArgumentCaptor.forClass(TransactionDefinition.class);
				verify(context.getBean(PlatformTransactionManager.class))
						.getTransaction(started.capture());
				assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW,
						started.getValue().getPropagationBehavior(),
						"install phases must suspend the caller's transaction and commit on their "
								+ "own, or a resumed install replays completed phases");
			});
		}

		@Test
		void callSuspendsTheCallersTransaction() {
			assertStartsItsOwnTransaction(phases -> phases.call(() -> "phase result"));
		}

		@Test
		void runSuspendsTheCallersTransaction() {
			assertStartsItsOwnTransaction(phases -> phases.run(() -> {}));
		}
	}

	@Nested
	class SiteNameCache {

		private SystemConfigDboMapper rows;
		private SystemConfigService config;

		@BeforeEach
		void setup() {
			rows = mock(SystemConfigDboMapper.class);
			config = new SystemConfigService(rows);
		}

		@AfterEach
		void discardAnyOpenTransaction() {
			if (TransactionSynchronizationManager.isSynchronizationActive())
				TransactionSynchronizationManager.clearSynchronization();
		}

		private void storedSiteNameIs(String value) {
			doAnswer(lookup -> {
				SystemConfigDbo row = new SystemConfigDbo();
				row.setConfigKey(SystemConfigService.Keys.SITE_NAME);
				row.setConfigValue(value);
				return row;
			}).when(rows).selectByPrimaryKey(SystemConfigService.Keys.SITE_NAME);
		}

		private void siteNameIsUnset() {
			doReturn(null).when(rows).selectByPrimaryKey(SystemConfigService.Keys.SITE_NAME);
		}

		private String siteName() {
			return config.get(SystemConfigService.Keys.SITE_NAME);
		}

		private void completeTheOpenTransaction(int status) {
			for (TransactionSynchronization pending : TransactionSynchronizationManager
					.getSynchronizations())
				pending.afterCompletion(status);
			TransactionSynchronizationManager.clearSynchronization();
		}

		private void commitTheOpenTransaction() {
			completeTheOpenTransaction(TransactionSynchronization.STATUS_COMMITTED);
		}

		@Test
		void aSiteNameCachedInsideATransactionThatRollsBackIsNotServedAfterwards() {
			storedSiteNameIs("Doomed Name");
			TransactionSynchronizationManager.initSynchronization();
			config.set(SystemConfigService.Keys.SITE_NAME, "Doomed Name");

			assertEquals("Doomed Name", siteName(),
					"a read inside the writing transaction observes its own uncommitted write");

			storedSiteNameIs("Committed Name");
			completeTheOpenTransaction(TransactionSynchronization.STATUS_ROLLED_BACK);

			assertEquals("Committed Name", siteName(),
					"a rolled-back write must not leave its value cached; invalidating only on commit "
							+ "would serve a value that was never committed until the next successful write");
		}

		@Test
		void repeatedReadsOfAnUnchangedSiteNameQueryTheDatabaseOnce() {
			storedSiteNameIs("Cached Name");

			assertEquals("Cached Name", siteName());
			assertEquals("Cached Name", siteName());

			verify(rows, times(1)).selectByPrimaryKey(SystemConfigService.Keys.SITE_NAME);
		}

		@Test
		void aReadRacingAnUncommittedWriteDoesNotCacheThePreCommitValueForever() {
			storedSiteNameIs("Old Name");
			TransactionSynchronizationManager.initSynchronization();
			config.set(SystemConfigService.Keys.SITE_NAME, "New Name");

			assertEquals("Old Name", siteName(),
					"a reader in another transaction still observes the committed value, and caches it");

			commitTheOpenTransaction();
			storedSiteNameIs("New Name");

			assertEquals("New Name", siteName(),
					"committing must drop the value a concurrent reader cached before the commit");
		}

		@Test
		void aSiteNameWriteOutsideATransactionInvalidatesImmediately() {
			storedSiteNameIs("Old Name");
			assertEquals("Old Name", siteName());

			config.set(SystemConfigService.Keys.SITE_NAME, "New Name");
			storedSiteNameIs("New Name");

			assertEquals("New Name", siteName());
		}

		@Test
		void unsettingTheSiteNameDropsTheCachedValue() {
			storedSiteNameIs("Old Name");
			assertEquals("Old Name", siteName());

			config.unset(SystemConfigService.Keys.SITE_NAME);
			siteNameIsUnset();

			assertNull(siteName());
		}

		@Test
		void anUnsetSiteNameIsNotCachedAsIfItWereAValue() {
			siteNameIsUnset();
			assertNull(siteName());

			storedSiteNameIs("Installed Name");

			assertEquals("Installed Name", siteName(),
					"an absent row is the cache's own empty state, so a missing site name must never "
							+ "be memoized as though it were a value");
		}
	}

	@Nested
	class AuthoringFormatResolution {

		private SystemConfigDboMapper rows;
		private AuthoringContentFormat authoringContentFormat;

		@BeforeEach
		void setup() {
			rows = mock(SystemConfigDboMapper.class);
			authoringContentFormat = new AuthoringContentFormat(new SystemConfigService(rows));
		}

		private void storedAuthoringDefaultIs(String configValue) {
			SystemConfigDbo row = new SystemConfigDbo();
			row.setConfigKey(SystemConfigService.Keys.AUTHORING_DEFAULT_CONTENT_FORMAT);
			row.setConfigValue(configValue);
			doReturn(row).when(rows)
					.selectByPrimaryKey(SystemConfigService.Keys.AUTHORING_DEFAULT_CONTENT_FORMAT);
		}

		@Test
		void anUnsetSettingBehavesExactlyAsBBCode() {
			doReturn(null).when(rows)
					.selectByPrimaryKey(SystemConfigService.Keys.AUTHORING_DEFAULT_CONTENT_FORMAT);

			assertEquals(ContentFormat.BBCODE, authoringContentFormat.forNewContent(null),
					"a site installed before this setting existed must keep authoring bbcode");
		}

		@Test
		void anUnreadableStoredSettingFallsBackToBBCodeRatherThanFailingEveryWrite() {
			storedAuthoringDefaultIs("PARCHMENT");

			assertEquals(ContentFormat.BBCODE, authoringContentFormat.forNewContent(null),
					"a hand-edited config row must not make every post and every wiki edit fail");
		}

		@Test
		void theSiteDefaultOnlyAppliesWhenTheAuthorNamedNothing() {
			storedAuthoringDefaultIs("MARKDOWN");

			assertEquals(ContentFormat.MARKDOWN, authoringContentFormat.forNewContent(null));
			assertEquals(ContentFormat.MARKDOWN, authoringContentFormat.forNewContent("  "));
			assertEquals(ContentFormat.BBCODE, authoringContentFormat.forNewContent("BBCODE"),
					"an explicit request always wins over the site default");
			assertEquals(ContentFormat.BBCODE, authoringContentFormat.forNewContent("bbcode"),
					"the wire value is a code, not a case-sensitive token");
		}

		@Test
		void supersedingContentPrefersTheFormatItReplacesOverTheSiteDefault() {
			storedAuthoringDefaultIs("MARKDOWN");

			assertEquals(ContentFormat.BBCODE, authoringContentFormat.forSupersedingContent(null,
					() -> Optional.of(ContentFormat.BBCODE)),
					"an edit that names no format must never restamp existing content with the site default");
			assertEquals(ContentFormat.MARKDOWN, authoringContentFormat.forSupersedingContent(null,
					Optional::empty),
					"content with no predecessor falls through to the site default");
			assertEquals(ContentFormat.MARKDOWN, authoringContentFormat.forSupersedingContent("MARKDOWN",
					() -> Optional.of(ContentFormat.BBCODE)),
					"an author may still convert their own content by naming the format explicitly");
		}

		@Test
		void aFormatNobodyCanRenderIsRefusedInsteadOfSilentlyDowngraded() {
			storedAuthoringDefaultIs("BBCODE");

			ResponseStatusException refused = assertThrows(ResponseStatusException.class,
					() -> authoringContentFormat.forNewContent("HTML"));

			assertEquals(HttpStatus.BAD_REQUEST, refused.getStatusCode());
			assertTrue(refused.getReason().contains("[BBCODE, MARKDOWN]"),
					"the refusal must name what is authorable: " + refused.getReason());
		}
	}

	@Nested
	class MaintenanceWindowFailure {

		@Test
		void aDrainThatTimesOutStillReportsTheDrainFailureWhenReleasingItsWindowAlsoFails()
				throws Exception {
			Connection session = mock(Connection.class);
			DataSource dataSource = mock(DataSource.class);
			when(dataSource.getConnection()).thenReturn(session);

			PreparedStatement acquire = mock(PreparedStatement.class);
			ResultSet acquired = mock(ResultSet.class);
			when(session.prepareStatement("select pg_try_advisory_lock(?)")).thenReturn(acquire);
			when(acquire.executeQuery()).thenReturn(acquired);
			when(acquired.next()).thenReturn(true);
			when(acquired.getBoolean(1)).thenReturn(true);

			PreparedStatement replicaCount = mock(PreparedStatement.class);
			ResultSet singleReplica = mock(ResultSet.class);
			when(session.prepareStatement(contains("pg_stat_activity"))).thenReturn(replicaCount);
			when(replicaCount.executeQuery()).thenReturn(singleReplica);
			when(singleReplica.next()).thenReturn(true);
			when(singleReplica.getLong(1)).thenReturn(1L);

			PreparedStatement release = mock(PreparedStatement.class);
			when(session.prepareStatement("select pg_advisory_unlock(?)")).thenReturn(release);
			when(release.executeQuery()).thenThrow(new SQLException("connection reset"));

			BackupRestoreProperties properties = new BackupRestoreProperties();
			properties.setMutationDrainTimeout(Duration.ofMillis(50));
			MaintenanceCoordinator coordinator = new MaintenanceCoordinator(dataSource, properties);
			coordinator.tryMutationLease().orElseThrow();

			SQLException drainTimedOut = assertThrows(SQLException.class,
					() -> coordinator.acquireExclusive(Duration.ofMillis(50)));

			assertEquals("timed out draining active application mutations", drainTimedOut.getMessage(),
					"a window that fails to drain must report the drain failure, not whatever went wrong "
							+ "while releasing the lock it had already taken");
			assertEquals(1, drainTimedOut.getSuppressed().length,
					"the release failure must travel as a suppressed exception rather than vanish");
			verify(session).abort(any());
		}
	}

	@Nested
	class MailDispatch {

		private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(MailDispatcherConfig.class);

		@Test
		void blankHostActivatesTheInMemoryCaptureDispatcher() {
			contextRunner.withPropertyValues("spring.mail.host=").run(context -> {
				assertEquals(1, context.getBeansOfType(MailDispatcher.class).size());
				assertInstanceOf(MailDispatcherConfig.InMemoryMailDispatcher.class, context.getBean(MailDispatcher.class));
			});
		}

		@Test
		void missingHostPropertyActivatesTheInMemoryCaptureDispatcher() {
			contextRunner.run(context -> assertInstanceOf(MailDispatcherConfig.InMemoryMailDispatcher.class,
					context.getBean(MailDispatcher.class)));
		}

		@Test
		void configuredHostActivatesTheSmtpDispatcher() {
			contextRunner.withPropertyValues("spring.mail.host=smtp.fastmail.com")
					.withBean(JavaMailSender.class, JavaMailSenderImpl::new)
					.run(context -> {
						assertEquals(1, context.getBeansOfType(MailDispatcher.class).size());
						assertFalse(context.getBean(MailDispatcher.class)
								instanceof MailDispatcherConfig.InMemoryMailDispatcher);
					});
		}

		@Test
		void prodProfileWithoutHostDisablesOutboundMailEntirely() {
			contextRunner.withPropertyValues("spring.profiles.active=prod", "spring.mail.host=")
					.run(context -> assertTrue(context.getBeansOfType(MailDispatcher.class).isEmpty()));
		}

		@Test
		void inMemoryDispatcherCapturesMessagesForAssertions() {
			MailDispatcherConfig.InMemoryMailDispatcher dispatcher = new MailDispatcherConfig.InMemoryMailDispatcher();
			dispatcher.dispatch(new MailDispatcher.OutboundMail("someone@example.test", "Subject line", "Body text"));
			assertEquals(1, dispatcher.sentMessages().size());
			assertEquals("Subject line", dispatcher.sentMessages().get(0).subject());
			dispatcher.clear();
			assertTrue(dispatcher.sentMessages().isEmpty());
		}
	}

	@Nested
	class BuildVersion {

		private SiteInfo sitePayloadBuiltWith(String configuredVersion) {
			SystemConfigService systemConfig = mock(SystemConfigService.class);
			when(systemConfig.get(SystemConfigService.Keys.SITE_NAME)).thenReturn("ZFGC");
			when(systemConfig.authoringDefaultContentFormat()).thenReturn(ContentFormat.MARKDOWN);
			return new SiteController(systemConfig, false, configuredVersion).site().getBody();
		}

		@Test
		void anUnsetBuildVersionStaysOffTheAnonymousSitePayload() {
			assertEquals(Optional.empty(), sitePayloadBuiltWith("").buildVersion(),
					"release builds off main ship without ZFGBB_BUILD_VERSION and /system/site is anonymous, "
							+ "so a blank value must resolve to absent rather than an empty string any "
							+ "visitor can read");
		}

		@Test
		void aConfiguredBuildVersionReachesTheAnonymousSitePayload() {
			assertEquals(Optional.of("2025.5.0-183-ga4514fd50"),
					sitePayloadBuiltWith("2025.5.0-183-ga4514fd50").buildVersion());
		}
	}
}
