package com.zfgc.zfgbb.system;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import com.zfgc.zfgbb.model.system.InstallStrategy;
import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.services.backup.BackupRestoreService;
import com.zfgc.zfgbb.services.backup.OperationStorageService;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;
import com.zfgc.zfgbb.services.backup.RestoreService;
import com.zfgc.zfgbb.testsupport.AbstractSystemInstallTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemInstallArchiveRestoreTest extends AbstractSystemInstallTest {

	@Container
	static ComposeContainer pg = devPostgres();

	private static final Path CONTENT_ROOT = contentRoot("archive-restore-content");
	private static final Path SAMPLE_ARCHIVE = contentRoot("archive-restore-sample-data")
			.resolve("backup.tar.gz");
	private static final String GENERATION_ADMIN = "generation_admin";
	private static final String GENERATION_PASSWORD = "generation-password";
	private static final String REQUESTED_ADMINISTRATOR = "site_owner";
	private static final String REQUESTED_ADMINISTRATOR_PASSWORD = "owner-password-456";
	private static final String RESTORED_SITE_NAME = "Restored Site";
	private static final String CORPUS_CATEGORY = "Restored Corpus Category";
	private static final String RECYCLED_THREAD = "Restored Recycled Thread";
	private static final short INSTALL_ID = 1;

	@Autowired
	private PostgresBackupTool postgres;

	@Autowired
	private RestoreService restoreService;

	@Autowired
	private OperationStorageService operationStorage;

	@Autowired
	private BackupRestoreService installabilityClassifier;

	@Autowired private AccountDeletionAuditDboMapper accountDeletionAuditDboMapper;
	@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private CategoryDboMapper categoryDboMapper;
	@Autowired private InstallRunDboMapper installRunDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserRefreshTokenDboMapper userRefreshTokenDboMapper;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		installDatasource(registry, pg, CONTENT_ROOT);
		registry.add("zfgbb.install.sample-archive", () -> "file:" + SAMPLE_ARCHIVE);
	}

	@Test
	@Order(1)
	void generationInstallProducesTheInstallerCompatibleArchiveTheContentPackShips() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(GENERATION_ADMIN, GENERATION_PASSWORD, "Generation Site", false)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.adminUserId").value(1));

		insertCategory(CORPUS_CATEGORY);
		Files.createDirectories(CONTENT_ROOT);

		BackupRestoreService.ArchiveInstallability classification =
				installabilityClassifier.classifyInstallability(CONTENT_ROOT);
		assertTrue(classification.compatible(),
				() -> "a generation install must be usable as installation content: "
						+ classification.reason());
		assertEquals(Integer.valueOf(1), classification.anchorAdministratorId());

		writePackArchive(postgres.metadata().schemaVersion(), classification.compatible(),
				classification.anchorAdministratorId());
		assertTrue(Files.isRegularFile(packArchive()));
	}

	@Test
	@Order(2)
	void freshInstallRestoresTheArchiveAndAdoptsTheRequestedAdministrator() throws Exception {
		String probe = "probe-" + UUID.randomUUID().toString().substring(0, 8);
		insertCategory(probe);
		assertEquals(1, categories(category -> category.andCategoryNameEqualTo(probe)));
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(1))
				.andExpect(jsonPath("$.siteName").value("Restored Site"))
				.andExpect(jsonPath("$.installSampleData").value(true));

		assertEquals(0, categories(category -> category.andCategoryNameEqualTo(probe)),
				"installing from the archive must restore over whatever the database held");
		assertEquals(1, categories(category -> category.andCategoryNameEqualTo(CORPUS_CATEGORY)),
				"the archived corpus must be present after installation");

		assertEquals(1, users(user -> user.andUserIdEqualTo(1).andUserNameEqualTo("site_owner")
				.andSsoKeyEqualTo("site_owner").andDisplayNameEqualTo("site_owner Administrator")
				.andPasswordHashIsNotNull().andPasswordAlgoIsNotNull()));
		assertEquals(0, users(user -> user.andUserNameEqualTo(GENERATION_ADMIN)),
				"the generation-time administrator identity must be gone");
		assertEquals(1, siteAdminGrants(1),
				"the requested administrator must hold ZFGC_SITE_ADMIN");

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andLastCompletedStateEqualTo("INSTALLED")
				.andSiteNameEqualTo("Restored Site").andProvisionRecycleBinEqualTo(true)
				.andAdminUserIdEqualTo(1).andRequestFingerprintIsNotNull().andLastErrorIsNull()),
				"the restored install_run row must describe this installation");
		assertEquals(1, systemConfigs(config -> config.andConfigKeyEqualTo("site_name")
				.andConfigValueEqualTo("Restored Site")));
		assertEquals(1, systemConfigs(config -> config.andConfigKeyEqualTo("installed_by_user_id")
				.andConfigValueEqualTo("1")));
		assertEquals(1, systemConfigs(config -> config.andConfigKeyEqualTo("installed")
				.andConfigValueEqualTo("true")));
		assertEquals(0, refreshTokens(),
				"restoring the archive must not import sessions from the generation machine");
		assertEquals(0, users(user -> user.andTokensValidAfterTsIsNull()),
				"installation must cut off pre-install tokens for every restored user");
		assertRecycleBinProvisioned();

		mockMvc.perform(get("/system/site"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.siteName").value("Restored Site"));
		mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isOk());

		assertRequestedAdministratorAuthenticates();
		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"username": "%s", "password": "%s", "useTokens": true}
						""".formatted(GENERATION_ADMIN, GENERATION_PASSWORD)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@Order(3)
	void aDriftedArchiveIsRefusedAndTheInstallationStaysResumable() throws Exception {
		writePackArchive("19700101.1", true, 1);
		String probe = "probe-" + UUID.randomUUID().toString().substring(0, 8);
		insertCategory(probe);
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value(containsString("19700101.1")));

		assertEquals(1, categories(category -> category.andCategoryNameEqualTo(probe)),
				"a drifted archive must be refused before anything destructive happens");
		assertEquals(1, users(user -> user.andUserIdEqualTo(1).andUserNameEqualTo("site_owner")));
		assertEquals(1, installRuns(run -> run.andStateEqualTo("FAILED")
				.andLastCompletedStateEqualTo("READY").andLastErrorLike("%19700101.1%")));
		mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isServiceUnavailable());

		deleteCategory(probe);
		writePackArchive(postgres.metadata().schemaVersion(), true, 1);

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")));
		assertRequestedAdministratorAuthenticates();
	}

	@Test
	@Order(4)
	void anInstallInterruptedAfterTheRestoreServesNoTrafficAndStaysRetryable() throws Exception {
		AccountDeletionAuditDbo deletionAudit = new AccountDeletionAuditDbo();
		deletionAudit.setSubjectUserIdSnapshot(1);
		deletionAudit.setSubjectPseudonym("generation-pseudonym");
		deletionAudit.setMode("ANONYMIZE");
		deletionAudit.setInitiatedBy("SELF");
		accountDeletionAuditDboMapper.insertSelective(deletionAudit);
		writePackArchive(postgres.metadata().schemaVersion(), true, 1);
		assertTrue(refreshTokens() > 0,
				"the archive must be taken while generation-time sessions exist");

		restoreService.restoreArchiveWithoutMaintenanceLease(packArchive());

		assertEquals(0, installRunDboMapper.countByExample(new InstallRunDboExample()),
				"an archive must never carry another deployment's install state");
		assertTrue(refreshTokens() > 0,
				"the restored archive still carries generation-time sessions at this point");
		mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isServiceUnavailable());
		assertEquals(1, installRuns(run -> run.andStateEqualTo("READY")
				.andLastCompletedStateEqualTo("READY").andRequestFingerprintIsNull()),
				"reading the install state must restore the singleton as not installed");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")));
		assertEquals(0, refreshTokens());
		assertEquals(0, accountDeletionAuditDboMapper.countByExample(new AccountDeletionAuditDboExample()));
		assertRequestedAdministratorAuthenticates();
	}

	@Test
	@Order(5)
	void decliningANonEmptyRestoredRecycleBinIsRefusedBeforeTheInstallIsLockedIn() throws Exception {
		Integer recycleBoardId = Integer.valueOf(
				systemConfigDboMapper.selectByPrimaryKey("recycle_board_id").getConfigValue());
		ThreadDbo recycledThread = new ThreadDbo();
		recycledThread.setThreadName(RECYCLED_THREAD);
		recycledThread.setBoardId(recycleBoardId);
		recycledThread.setCreatedUserId(1);
		threadDboMapper.insertSelective(recycledThread);
		writePackArchive(postgres.metadata().schemaVersion(), true, 1);
		ThreadDboExample recycledThreadExample = new ThreadDboExample();
		recycledThreadExample.createCriteria().andThreadNameEqualTo(RECYCLED_THREAD);
		threadDboMapper.deleteByExample(recycledThreadExample);
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(REQUESTED_ADMINISTRATOR, REQUESTED_ADMINISTRATOR_PASSWORD,
						RESTORED_SITE_NAME, true, false)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value(containsString("recycle bin")));

		assertEquals(1, recycledThreads(),
				"the corpus recycle bin must survive the refusal intact");
		assertEquals(1, installRuns(run -> run.andStateEqualTo("FAILED")
				.andLastCompletedStateEqualTo("READY").andRequestFingerprintIsNull()),
				"a refused installation must not lock its request in");
		mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isServiceUnavailable());

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")));
		assertEquals(1, recycledThreads());
		assertRecycleBinProvisioned();
	}

	@Test
	@Order(6)
	void aResumedInstallRefusesAMissingArchiveAndAStrategySwitch() throws Exception {
		interruptInstallAt("CORE_READY", "ARCHIVE");
		Path archive = packArchive();
		Path parked = archive.resolveSibling("backup.tar.gz.parked");
		Files.move(archive, parked);

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail")
						.value(containsString("ships no sample data archive")));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("FAILED")
				.andLastCompletedStateEqualTo("CORE_READY").andInstallStrategyEqualTo("ARCHIVE")),
				"a content pack whose archive disappeared must leave the interrupted run untouched");

		Files.move(parked, archive);
		interruptInstallAt("CORE_READY", "NONE");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.detail").value(containsString("cannot be resumed from")));

		interruptInstallAt("CORE_READY", "ARCHIVE");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andInstallStrategyEqualTo("ARCHIVE")));
		assertEquals(1, recycledThreads(),
				"resuming must not re-run the restore over the installed corpus");
	}

	@Test
	@Order(7)
	void aResumeThatCannotProveWhatItRestoredIsRefusedInsteadOfCompleting() throws Exception {
		String probe = "probe-" + UUID.randomUUID().toString().substring(0, 8);
		insertCategory(probe);
		interruptInstallAt("CORE_READY", null);

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.detail").value(containsString("cannot be resumed")));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("FAILED")
				.andLastCompletedStateEqualTo("CORE_READY").andInstallStrategyIsNull()),
				"refusing a run that never recorded its sources must leave it untouched");
		assertEquals(0, systemConfigs(config -> config.andConfigKeyEqualTo("installed")),
				"a run that never restored its content pack must never be reported as installed");

		interruptInstallAt("PACK_READY", "ARCHIVE");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.detail").value(containsString("cannot be resumed")));

		assertEquals(0, systemConfigs(config -> config.andConfigKeyEqualTo("installed")),
				"a phase this build never records must never complete an installation");

		interruptInstallAt("CORE_READY", "ARCHIVE");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andInstallStrategyEqualTo("ARCHIVE")));
		assertEquals(1, categories(category -> category.andCategoryNameEqualTo(probe)),
				"resuming a run that already restored its archive must not restore it again");
		assertRequestedAdministratorAuthenticates();
	}

	@Test
	@Order(8)
	void anInstallThatFailedBeforeEstablishingAnAdministratorAcceptsDifferentCredentials()
			throws Exception {
		interruptInstallAt("READY", "ARCHIVE");
		updateInstallRun(run -> {
			run.setRequestFingerprint("stale-fingerprint");
			run.setAdminUserId(0);
		});

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andInstallStrategyEqualTo("ARCHIVE").andAdminUserIdEqualTo(1)),
				"a run that never established an administrator must accept a different request");
		assertRequestedAdministratorAuthenticates();
	}

	@Test
	@Order(9)
	void theAnonymizationSentinelIsNeverAdoptedAsTheAdministrator() throws Exception {
		UserDbo sentinel = new UserDbo();
		sentinel.setUserId(0);
		sentinel.setUserName("sentinel_zero");
		userDboMapper.updateByPrimaryKeySelective(sentinel);
		writePackArchive(postgres.metadata().schemaVersion(), true, 1);
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody("sentinel_zero", "sentinel-password-789", RESTORED_SITE_NAME,
						true)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value(containsString("Username already taken")));

		assertEquals(1, users(user -> user.andUserIdEqualTo(0).andSsoKeyEqualTo("__deleted__")
				.andUserNameEqualTo("sentinel_zero").andDisplayNameEqualTo("[deleted]")
				.andActiveFlagEqualTo(false).andPasswordHashIsNull().andPasswordAlgoIsNull()
				.andPasswordSaltIsNull()),
				"user_id 0 must never be adoptable as the administrator");
		assertEquals(0, siteAdminGrants(0),
				"the sentinel must never receive a site administrator grant");
		assertEquals(0, contactInfoFor(0),
				"the sentinel must never acquire the installer's email address");
		assertEquals(1, users(user -> user.andUserIdEqualTo(1).andUserNameEqualTo("site_owner")
				.andPasswordHashIsNotNull()),
				"a refused adoption must leave the restored anchor administrator alone");
		assertEquals(1, installRuns(run -> run.andStateEqualTo("FAILED")
				.andLastCompletedStateEqualTo("READY")));

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(1));

		assertRequestedAdministratorAuthenticates();
	}

	@Test
	@Order(10)
	void aRestoredArchiveCannotBeCompletedAsAnInstallWithoutAContentPack() throws Exception {
		interruptInstallAt("READY", "ARCHIVE");
		updateInstallRun(run -> run.setRequestFingerprint("stale-fingerprint"));

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(REQUESTED_ADMINISTRATOR, REQUESTED_ADMINISTRATOR_PASSWORD,
						RESTORED_SITE_NAME, false)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.detail").value(containsString("cannot be resumed from")));

		assertEquals(0, systemConfigs(config -> config.andConfigKeyEqualTo("installed")),
				"refusing the downgrade must not report the site as installed");
		assertEquals(0, users(user -> user.andUserIdEqualTo(1).andPasswordHashIsNotNull()
				.andUserNameNotEqualTo("site_owner")),
				"the restored archive administrator must never survive as a credentialed account");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestedAdministratorInstallBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertRequestedAdministratorAuthenticates();
	}

	private long installRuns(Consumer<InstallRunDboExample.Criteria> predicate) {
		InstallRunDboExample example = new InstallRunDboExample();
		predicate.accept(example.createCriteria().andInstallIdEqualTo(INSTALL_ID));
		return installRunDboMapper.countByExample(example);
	}

	private long users(Consumer<UserDboExample.Criteria> predicate) {
		UserDboExample example = new UserDboExample();
		predicate.accept(example.createCriteria());
		return userDboMapper.countByExample(example);
	}

	private long categories(Consumer<CategoryDboExample.Criteria> predicate) {
		CategoryDboExample example = new CategoryDboExample();
		predicate.accept(example.createCriteria());
		return categoryDboMapper.countByExample(example);
	}

	private long systemConfigs(Consumer<SystemConfigDboExample.Criteria> predicate) {
		SystemConfigDboExample example = new SystemConfigDboExample();
		predicate.accept(example.createCriteria());
		return systemConfigDboMapper.countByExample(example);
	}

	private long recycledThreads() {
		ThreadDboExample example = new ThreadDboExample();
		example.createCriteria().andThreadNameEqualTo(RECYCLED_THREAD);
		return threadDboMapper.countByExample(example);
	}

	private long refreshTokens() {
		return userRefreshTokenDboMapper.countByExample(new UserRefreshTokenDboExample());
	}

	private long contactInfoFor(int userId) {
		UserContactInfoDboExample example = new UserContactInfoDboExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return userContactInfoDboMapper.countByExample(example);
	}

	private long siteAdminGrants(int userId) {
		PermissionDboExample siteAdminExample = new PermissionDboExample();
		siteAdminExample.createCriteria().andPermissionCodeEqualTo("ZFGC_SITE_ADMIN");
		List<Integer> siteAdminPermissionIds = permissionDboMapper.selectByExample(siteAdminExample)
				.stream().map(PermissionDbo::getPermissionId).toList();
		BrUserPermissionDboExample grantExample = new BrUserPermissionDboExample();
		grantExample.createCriteria().andUserIdEqualTo(userId)
				.andUserPermissionIdIn(siteAdminPermissionIds);
		return brUserPermissionDboMapper.countByExample(grantExample);
	}

	private void insertCategory(String categoryName) {
		CategoryDbo category = new CategoryDbo();
		category.setCategoryName(categoryName);
		categoryDboMapper.insertSelective(category);
	}

	private void deleteCategory(String categoryName) {
		CategoryDboExample example = new CategoryDboExample();
		example.createCriteria().andCategoryNameEqualTo(categoryName);
		categoryDboMapper.deleteByExample(example);
	}

	private void interruptInstallAt(String lastCompletedState, String strategy) {
		updateInstallRun(run -> {
			run.setState("FAILED");
			run.setLastCompletedState(lastCompletedState);
			run.setInstallStrategy(strategy);
			run.setLastError("simulated interruption");
		});
		SystemConfigDboExample installedExample = new SystemConfigDboExample();
		installedExample.createCriteria().andConfigKeyEqualTo("installed");
		systemConfigDboMapper.deleteByExample(installedExample);
	}

	private void assertRequestedAdministratorAuthenticates() throws Exception {
		String accessToken = login(REQUESTED_ADMINISTRATOR, REQUESTED_ADMINISTRATOR_PASSWORD)
				.get("accessToken").asString();
		mockMvc.perform(get("/users/loggedInUser")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.id").value(1));
	}

	private void resetInstallRunToFreshInstall() {
		updateInstallRun(run -> {
			run.setState("READY");
			run.setLastCompletedState("READY");
			run.setRequestFingerprint(null);
			run.setProvisionRecycleBin(null);
			run.setSiteName(null);
			run.setAdminUserId(null);
			run.setLastError(null);
		});
	}

	private void updateInstallRun(Consumer<InstallRunDbo> mutation) {
		InstallRunDbo run = installRunDboMapper.selectByPrimaryKey(INSTALL_ID);
		mutation.accept(run);
		installRunDboMapper.updateByPrimaryKey(run);
	}

	private void writePackArchive(String schemaVersion, boolean installerCompatible,
			Integer anchorAdministratorId) throws Exception {
		Path workspace = Files.createTempDirectory("zfgbb-install-archive");
		Path dump = workspace.resolve("database.dump");
		PostgresBackupTool.DatabaseMetadata metadata = postgres.dump(dump);
		Files.createDirectories(CONTENT_ROOT);
		Path destination = packArchive();
		Files.createDirectories(destination.getParent());
		new BackupArchiveWriter(operationStorage.limits()).write(
				new BackupArchiveWriter.Request(dump, CONTENT_ROOT, "test", schemaVersion,
						metadata.serverMajor(), metadata.dumpToolVersion(),
						installerCompatible, anchorAdministratorId, Instant.now()),
				destination);
	}

	private static Path packArchive() {
		return SAMPLE_ARCHIVE;
	}

	private static String requestedAdministratorInstallBody() {
		return installBody(REQUESTED_ADMINISTRATOR, REQUESTED_ADMINISTRATOR_PASSWORD,
				RESTORED_SITE_NAME, true);
	}

	private static String installBody(String userName, String password, String siteName,
			boolean installSampleData) {
		return installBody(userName, password, siteName, installSampleData, true);
	}

	private static String installBody(String userName, String password, String siteName,
			boolean installSampleData, boolean provisionRecycleBin) {
		return """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "%s Administrator",
				  "adminEmail": "%s@example.invalid",
				  "adminPassword": "%s",
				  "siteName": "%s",
				  "installSampleData": %s,
				  "provisionRecycleBin": %s
				}
				""".formatted(userName, userName, userName, password, siteName, installSampleData,
				provisionRecycleBin);
	}
}
