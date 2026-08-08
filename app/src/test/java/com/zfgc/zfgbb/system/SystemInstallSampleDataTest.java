package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.operations.archive.BackupArchiveValidator;
import com.zfgc.zfgbb.operations.archive.BackupManifest;
import com.zfgc.zfgbb.services.backup.OperationStorageService;
import com.zfgc.zfgbb.testsupport.AbstractSystemInstallTest;
import com.zfgc.zfgbb.testsupport.FixtureSemanticInventory;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemInstallSampleDataTest extends AbstractSystemInstallTest {

	@Container
	static ComposeContainer pg = devPostgres();

	private static final Path CONTENT_ROOT = contentRoot("sample-data-install");
	private static final int APPROVED_INVENTORY_ANCHOR_ADMINISTRATOR_ID = 1;
	private static final short INSTALL_ID = 1;

	@Autowired
	private OperationStorageService operationStorage;

	@Autowired private AccountDeletionAuditDboMapper accountDeletionAuditDboMapper;
	@Autowired private AccountDeletionRequestDboMapper accountDeletionRequestDboMapper;
	@Autowired private BackupJobDboMapper backupJobDboMapper;
	@Autowired private BoardDboMapper boardDboMapper;
	@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private InstallRunDboMapper installRunDboMapper;
	@Autowired private MessageDboMapper messageDboMapper;
	@Autowired private MigrationConflictDboMapper migrationConflictDboMapper;
	@Autowired private MigratorAttachmentRefRewriteDboMapper migratorAttachmentRefRewriteDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private ProjectDboMapper projectDboMapper;
	@Autowired private QuoteStripAuditDboMapper quoteStripAuditDboMapper;
	@Autowired private QuoteStripRunDboMapper quoteStripRunDboMapper;
	@Autowired private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired private TestSystemInfoMapper testSystemInfoMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserRefreshTokenDboMapper userRefreshTokenDboMapper;
	@Autowired private WikiPageDboMapper wikiPageDboMapper;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		installDatasource(registry, pg, CONTENT_ROOT);
	}

	@Test
	@Order(1)
	void installsTheShippedArchiveOntoTheRequestedAdministrator() throws Exception {
		BackupManifest shipped = shippedArchiveManifest();
		assertTrue(shipped.installerCompatible(),
				"the shipped content pack archive must be installable");
		assertEquals(APPROVED_INVENTORY_ANCHOR_ADMINISTRATOR_ID,
				shipped.installerAnchorAdministratorId(),
				"the approved fixture inventory is written against this anchor administrator");

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(true)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(shipped.installerAnchorAdministratorId()))
				.andExpect(jsonPath("$.siteName").value("Installer Site Name"))
				.andExpect(jsonPath("$.installSampleData").value(true))
				.andExpect(jsonPath("$.restartRequired").doesNotExist())
				.andExpect(jsonPath("$.operationId").doesNotExist())
				.andExpect(jsonPath("$.restartCommand").doesNotExist());

		mockMvc.perform(get("/system/site"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.siteName").value("Installer Site Name"));
		assertInstalledWithStrategy("ARCHIVE");

		int anchorAdministratorId = shipped.installerAnchorAdministratorId();
		assertEquals(1, users(user -> user.andUserIdEqualTo(anchorAdministratorId)
				.andUserNameEqualTo("pack_admin").andDisplayNameEqualTo("Pack Administrator")
				.andPasswordHashIsNotNull().andPasswordAlgoIsNotNull()));
		assertEquals(1, siteAdminGrants(anchorAdministratorId),
				"the requested administrator must hold exactly one site administrator grant");
		List<Integer> usersAllowedCredentials = List.of(0, anchorAdministratorId);
		UserDboExample credentialedExample = new UserDboExample();
		credentialedExample.createCriteria().andUserIdNotIn(usersAllowedCredentials).andPasswordHashIsNotNull();
		credentialedExample.or().andUserIdNotIn(usersAllowedCredentials).andPasswordAlgoIsNotNull();
		credentialedExample.or().andUserIdNotIn(usersAllowedCredentials).andPasswordSaltIsNotNull();
		assertEquals(0, userDboMapper.countByExample(credentialedExample),
				"the archive must not ship credentials for anybody but the anchor administrator");

		String touchUpdatedTs = testSystemInfoMapper.getTouchUpdatedTsDefinition();
		assertTrue(touchUpdatedTs.contains("new.updated_ts is distinct from old.updated_ts"),
				"pg_restore reinstalls the shipped archive's own function bodies and its "
						+ "flyway_schema_history, so installing must bring repeatable schema objects "
						+ "back up to the application's versions; the database is instead left "
						+ "running the archive's pre-fix trigger: " + touchUpdatedTs);

		assertTrue(users(user -> user.andUserIdNotEqualTo(0)) > 1,
				"the shipped archive must populate the site with preview members");
		assertTrue(threadDboMapper.countByExample(new ThreadDboExample()) > 0);
		assertTrue(messageDboMapper.countByExample(new MessageDboExample()) > 0);
		assertTrue(wikiPageDboMapper.countByExample(new WikiPageDboExample()) > 0);
		assertTrue(projectDboMapper.countByExample(new ProjectDboExample()) > 0);
		assertEquals(0, migratorIdMapDboMapper.countByExample(new MigratorIdMapDboExample()),
				"the shipped archive must not carry the legacy identifier mappings of the corpus "
						+ "it was cut from, or a later migration would resolve the installer's own "
						+ "legacy rows onto preview rows");
		assertEquals(0, migrationConflictDboMapper.countByExample(new MigrationConflictDboExample()));
		assertEquals(0, migratorAttachmentRefRewriteDboMapper
				.countByExample(new MigratorAttachmentRefRewriteDboExample()));
		assertEquals(0, quoteStripAuditDboMapper.countByExample(new QuoteStripAuditDboExample()));
		assertEquals(0, quoteStripRunDboMapper.countByExample(new QuoteStripRunDboExample()));

		assertEquals(0, accountDeletionRequestDboMapper.countByExample(new AccountDeletionRequestDboExample()));
		assertEquals(0, accountDeletionAuditDboMapper.countByExample(new AccountDeletionAuditDboExample()));
		assertEquals(0, userRefreshTokenDboMapper.countByExample(new UserRefreshTokenDboExample()),
				"installing the archive must not import generation-time sessions");
		assertEquals(0, backupJobDboMapper.countByExample(new BackupJobDboExample()),
				"the archive must not carry the generation deployment's backup jobs");
		assertEquals(0, users(user -> user.andTokensValidAfterTsIsNull()),
				"installation must cut off pre-install tokens for every restored user");
		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andSiteNameEqualTo("Installer Site Name").andProvisionRecycleBinEqualTo(true)));
		assertEquals(1, systemConfigs(config -> config.andConfigKeyEqualTo("site_name")
				.andConfigValueEqualTo("Installer Site Name")));
		assertRecycleBinProvisioned();

		assertTrue(Files.isRegularFile(
				CONTENT_ROOT.resolve("forum/attachments/4/favicon.png")));
		assertTrue(Files.isRegularFile(
				CONTENT_ROOT.resolve("wiki/27/OoT3D_Boxart.png")));
		assertTrue(Files.notExists(CONTENT_ROOT.resolve(".zfgbb")));

		var expected = FixtureSemanticInventory.expected();
		var actual = FixtureSemanticInventory.capture(dataSource, CONTENT_ROOT);
		assertTrue(expected.equals(actual),
				() -> "the shipped archive differs from the approved preview inventory: "
						+ FixtureSemanticInventory.describeDifference(expected, actual));

		login("pack_admin", "pack-admin-password");
	}

	@Test
	@Order(2)
	void decliningTheRecycleBinRemovesTheRestoredRecycleBoard() throws Exception {
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(false)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertInstalledWithStrategy("ARCHIVE");
		assertEquals(0, systemConfigs(config -> config.andConfigKeyEqualTo("recycle_board_id")));
		BoardDboExample recycleBoardExample = new BoardDboExample();
		recycleBoardExample.createCriteria().andBoardNameEqualTo("Recycle Bin");
		assertEquals(0, boardDboMapper.countByExample(recycleBoardExample));
		assertTrue(threadDboMapper.countByExample(new ThreadDboExample()) > 0,
				"declining the recycle bin must leave the rest of the corpus installed");
		login("pack_admin", "pack-admin-password");
	}

	@Test
	@Order(3)
	void sampleDataCannotBeDeclinedAfterTheInstallBecauseTheEndpointIsGone() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody(true, false)))
				.andExpect(status().isNotFound());

		assertInstalledWithStrategy("ARCHIVE");
		assertTrue(threadDboMapper.countByExample(new ThreadDboExample()) > 0,
				"a refused request must not strip the restored corpus back out");
	}

	@Test
	@Order(4)
	void installingAsARestoredCorpusMemberAdoptsThatAccount() throws Exception {
		int anchorAdministratorId = APPROVED_INVENTORY_ANCHOR_ADMINISTRATOR_ID;
		String adoptedUserName = testQueryHelperMapper.findMostProlificMember(
				anchorAdministratorId, anchorAdministratorId);
		assertNotNull(adoptedUserName, "the shipped corpus must contain an adoptable member");
		int adoptedUserId = findUserIdByName(adoptedUserName);
		long messagesOwnedBeforeAdoption = messages(message -> message.andOwnerIdEqualTo(adoptedUserId));
		long threadsOwnedBeforeAdoption = threads(thread -> thread.andCreatedUserIdEqualTo(adoptedUserId));
		assertTrue(messagesOwnedBeforeAdoption > 0,
				"the adopted corpus member must already own posts");
		assertTrue(threadsOwnedBeforeAdoption > 0,
				"the adopted corpus member must already own threads");
		resetInstallRunToFreshInstall();

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(adoptionInstallBody(adoptedUserName)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(adoptedUserId));

		assertInstalledWithStrategy("ARCHIVE");
		assertEquals(1, users(user -> user.andUserIdEqualTo(adoptedUserId)
				.andUserNameEqualTo(adoptedUserName).andSsoKeyEqualTo(adoptedUserName)
				.andDisplayNameEqualTo("Adopted Administrator").andActiveFlagEqualTo(true)
				.andPasswordHashIsNotNull().andPasswordAlgoIsNotNull()
				.andTokensValidAfterTsIsNotNull()),
				"the requested credentials must land on the adopted corpus account");
		assertEquals(1, siteAdminGrants(adoptedUserId),
				"the adopted member must hold exactly one site administrator grant");
		assertEquals(messagesOwnedBeforeAdoption, messages(message -> message.andOwnerIdEqualTo(adoptedUserId)),
				"adoption must leave the adopted member's posts attributed to them");
		assertEquals(threadsOwnedBeforeAdoption, threads(thread -> thread.andCreatedUserIdEqualTo(adoptedUserId)),
				"adoption must leave the adopted member's threads attributed to them");
		assertEquals(1, installRuns(run -> run.andStateEqualTo("INSTALLED")
				.andAdminUserIdEqualTo(adoptedUserId).andLastErrorIsNull()));
		assertEquals(String.valueOf(adoptedUserId),
				systemConfigDboMapper.selectByPrimaryKey("installed_by_user_id").getConfigValue());

		assertEquals(1, users(user -> user.andUserIdEqualTo(anchorAdministratorId)
				.andSsoKeyEqualTo("__deleted__" + anchorAdministratorId).andUserNameEqualTo("[deleted]")
				.andDisplayNameEqualTo("[deleted]").andActiveFlagEqualTo(false)
				.andPasswordHashIsNull().andPasswordAlgoIsNull().andPasswordSaltIsNull()),
				"the superseded generation anchor must be anonymized in place");
		assertEquals(0, siteAdminGrants(anchorAdministratorId),
				"the superseded generation anchor must not stay a site administrator");
		UserContactInfoDboExample anchorContactExample = new UserContactInfoDboExample();
		anchorContactExample.createCriteria().andUserIdEqualTo(anchorAdministratorId);
		assertEquals(0, userContactInfoDboMapper.countByExample(anchorContactExample),
				"the superseded generation anchor must release its email address");
		assertEquals(1, users(user -> user.andUserIdEqualTo(anchorAdministratorId)),
				"the superseded generation anchor must survive as a row so its content keeps its owner");
		ContentResourceDboExample anchorUploadExample = new ContentResourceDboExample();
		anchorUploadExample.createCriteria().andUploadedUserIdEqualTo(anchorAdministratorId);
		assertTrue(contentResourceDboMapper.countByExample(anchorUploadExample) > 0,
				"the anchor still owns content, which is why it is anonymized instead of deleted");

		String accessToken = login(adoptedUserName, "adopted-admin-password")
				.get("accessToken").asString();
		mockMvc.perform(get("/users/loggedInUser")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.id").value(adoptedUserId));
	}

	private long users(Consumer<UserDboExample.Criteria> predicate) {
		UserDboExample example = new UserDboExample();
		predicate.accept(example.createCriteria());
		return userDboMapper.countByExample(example);
	}

	private long messages(Consumer<MessageDboExample.Criteria> predicate) {
		MessageDboExample example = new MessageDboExample();
		predicate.accept(example.createCriteria());
		return messageDboMapper.countByExample(example);
	}

	private long threads(Consumer<ThreadDboExample.Criteria> predicate) {
		ThreadDboExample example = new ThreadDboExample();
		predicate.accept(example.createCriteria());
		return threadDboMapper.countByExample(example);
	}

	private long installRuns(Consumer<InstallRunDboExample.Criteria> predicate) {
		InstallRunDboExample example = new InstallRunDboExample();
		predicate.accept(example.createCriteria().andInstallIdEqualTo(INSTALL_ID));
		return installRunDboMapper.countByExample(example);
	}

	private long systemConfigs(Consumer<SystemConfigDboExample.Criteria> predicate) {
		SystemConfigDboExample example = new SystemConfigDboExample();
		predicate.accept(example.createCriteria());
		return systemConfigDboMapper.countByExample(example);
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

	private void resetInstallRunToFreshInstall() {
		InstallRunDbo run = installRunDboMapper.selectByPrimaryKey(INSTALL_ID);
		run.setState("READY");
		run.setLastCompletedState("READY");
		run.setRequestFingerprint(null);
		run.setProvisionRecycleBin(null);
		run.setSiteName(null);
		run.setAdminUserId(null);
		run.setLastError(null);
		installRunDboMapper.updateByPrimaryKey(run);
	}

	private static String adoptionInstallBody(String adminUserName) {
		return """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "Adopted Administrator",
				  "adminEmail": "adopted-admin@example.invalid",
				  "adminPassword": "adopted-admin-password",
				  "siteName": "Installer Site Name",
				  "installSampleData": true,
				  "provisionRecycleBin": true
				}
				""".formatted(adminUserName);
	}

	private BackupManifest shippedArchiveManifest() throws Exception {
		Path shipped = resolveFromProjectRoot(
				"app/src/main/resources/sample-data/backup.tar.gz");
		return new BackupArchiveValidator(operationStorage.limits()).validate(shipped).manifest();
	}
}
