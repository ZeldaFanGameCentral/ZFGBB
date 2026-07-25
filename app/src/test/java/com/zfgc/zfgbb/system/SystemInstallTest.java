package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
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

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.services.auth.AuthCookieService;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.system.InstallRunRepository;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.operations.postgres.PostgresAdvisoryLock;
import com.zfgc.zfgbb.testsupport.ZfgbbIntegrationTest;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemInstallTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	private static final Path CONTENT_ROOT = Path.of(System.getProperty("java.io.tmpdir"),
			"zfgbb-system-install-" + UUID.randomUUID());
	private static String staleAccessToken;

	@Autowired
	private InstallRunRepository installRun;

	@Autowired
	private AuthService authService;

	@Autowired private BoardDboMapper boardDboMapper;
		@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private InstallRunDboMapper installRunDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private ProjectDboMapper projectDboMapper;
	@Autowired private ResourceDboMapper resourceDboMapper;
	@Autowired private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserRefreshTokenDboMapper userRefreshTokenDboMapper;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
		r.add("zfgbb.content.path", CONTENT_ROOT::toString);
	}

	@Test
	@Order(1)
	void installRequiresTheToken() throws Exception {
		mockMvc.perform(get("/system/install/status")
				.cookie(new Cookie(AuthCookieService.ACCESS_COOKIE_NAME, "malformed-jwt")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(false));

		mockMvc.perform(post("/system/install")
				.cookie(new Cookie(AuthCookieService.ACCESS_COOKIE_NAME, "malformed-jwt"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", "not-the-real-token")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(2)
	void installCreatesAdminOnACleanDatabase() throws Exception {
		User previousInstallationUser = User.builder()
				.userId(1)
				.userName("previous_admin")
				.displayName("Previous Admin")
				.activeFlag(true)
				.permissions(List.of())
				.build();
		staleAccessToken = authService.issueAccessToken(previousInstallationUser);

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.cookie(new Cookie(AuthCookieService.ACCESS_COOKIE_NAME, staleAccessToken))
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(1))
				.andExpect(jsonPath("$.siteName").value("ZFGC Test"))
				.andExpect(jsonPath("$.contentPack").doesNotExist())
				.andExpect(jsonPath("$.accessToken").doesNotExist())
				.andExpect(jsonPath("$.refreshToken").doesNotExist())
				.andExpect(cookie().value(AuthCookieService.ACCESS_COOKIE_NAME, ""))
				.andExpect(cookie().maxAge(AuthCookieService.ACCESS_COOKIE_NAME, 0))
				.andExpect(cookie().value(AuthCookieService.REFRESH_COOKIE_NAME, ""))
				.andExpect(cookie().maxAge(AuthCookieService.REFRESH_COOKIE_NAME, 0));

		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.siteName").value("ZFGC Test"));

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));
	}

	private List<Integer> permissionIdsOf(String... permissionCodes) {
		PermissionDboExample byCode = new PermissionDboExample();
		byCode.createCriteria().andPermissionCodeIn(List.of(permissionCodes));
		List<Integer> permissionIds = permissionDboMapper.selectByExample(byCode).stream()
				.map(PermissionDbo::getPermissionId).toList();
		assertEquals(permissionCodes.length, permissionIds.size(),
				"every named permission code must resolve: " + List.of(permissionCodes));
		return permissionIds;
	}

	@Test
	@Order(3)
	void cleanInstallCreatesOnlyGenericInstallationData() {
		assertEquals(2, brUserPermissionDboMapper.countByExample(where(new BrUserPermissionDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(1)
						.andUserPermissionIdIn(permissionIdsOf("ZFGC_USER", "ZFGC_SITE_ADMIN")))),
				"admin carries ZFGC_USER + ZFGC_SITE_ADMIN");
		assertEquals(0, threadDboMapper.countByExample(null));
		assertEquals(0, projectDboMapper.countByExample(null));
		assertEquals(0, resourceDboMapper.countByExample(null));
		assertEquals(0, userRefreshTokenDboMapper.countByExample(null),
				"browser installation must not create an authenticated session");
		assertEquals(0, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andTokensValidAfterTsIsNull())),
				"installation must cut off pre-install tokens for every installed user");
		assertEquals(1, installRunDboMapper.countByExample(where(new InstallRunDboExample(),
				example -> example.createCriteria().andInstallIdEqualTo((short) 1).andInstallStrategyEqualTo("NONE"))),
				"an installation without a content pack records the non-archive strategy");
	}

	@Test
	@Order(4)
	void staleBearerForAReusedUserIdIsRejected() throws Exception {
		mockMvc.perform(get("/users/loggedInUser")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + staleAccessToken))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@Order(5)
	void adminCanLogInAndUseTheImmediateBearer() throws Exception {
		JsonNode loginResponse = login(ADMIN_USER, ADMIN_PASSWORD);
		mockMvc.perform(get("/users/loggedInUser")
				.header(HttpHeaders.AUTHORIZATION,
						"Bearer " + loginResponse.get("accessToken").asString()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.id").value(1));
	}

	@Test
	@Order(6)
	void explicitTokenInstallResponseKeepsBodyTokensAndClearsBrowserCookies() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.cookie(new Cookie(AuthCookieService.ACCESS_COOKIE_NAME, staleAccessToken))
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBodyWithTokens()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.refreshToken").isString())
				.andExpect(cookie().maxAge(AuthCookieService.ACCESS_COOKIE_NAME, 0))
				.andExpect(cookie().maxAge(AuthCookieService.REFRESH_COOKIE_NAME, 0));
	}

	@Test
	@Order(7)
	void recycleBinIsProvisionedWithoutDuplication() {
		assertEquals(1, boardDboMapper.countByExample(where(new BoardDboExample(),
				example -> example.createCriteria().andBoardNameEqualTo("Recycle Bin"))),
				"install provisions exactly one recycle board");
		assertRecycleBinProvisioned();
	}

	@Test
	@Order(8)
	void installRunRowIsReadableThroughTheGeneratedSmallintPrimaryKeyMapper() {
		InstallRunDbo installRunRow = installRunDboMapper.selectByPrimaryKey((short) 1);
		assertNotNull(installRunRow, "seeded install_run row must be selectable by its smallint primary key");
		assertEquals("INSTALLED", installRunRow.getState());
		assertEquals("INSTALLED", installRunRow.getLastCompletedState());
		assertEquals(Integer.valueOf(1), installRunRow.getAdminUserId());
		assertNotNull(installRunRow.getRequestFingerprint(), "claim() persisted a request fingerprint");
		assertNull(installRunRow.getLastError());
	}

	@Test
	@Order(9)
	void failAndResumeCasGuardsTheInstallStateMachineOnARealDatabase() {
		installRun.fail(new RuntimeException("post-install"));
		assertEquals("INSTALLED", installRunDboMapper.selectByPrimaryKey((short) 1).getState(),
				"fail must never move an already INSTALLED run to FAILED");
	}

	@Test
	@Order(10)
	void anInterruptedInstallWithoutAContentPackResumesToInstalled() throws Exception {
		InstallRunDbo interruptedRun = new InstallRunDbo();
		interruptedRun.setInstallId((short) 1);
		interruptedRun.setState("FAILED");
		interruptedRun.setLastCompletedState("CORE_READY");
		interruptedRun.setInstallStrategy("NONE");
		interruptedRun.setLastError("simulated interruption");
		installRunDboMapper.updateByPrimaryKeySelective(interruptedRun);
		systemConfigDboMapper.deleteByExample(where(new SystemConfigDboExample(),
				example -> example.createCriteria().andConfigKeyEqualTo("installed")));
		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(false));

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(1))
				.andExpect(jsonPath("$.siteName").value("ZFGC Test"))
				.andExpect(jsonPath("$.contentPack").doesNotExist());

		assertEquals(1, installRunDboMapper.countByExample(where(new InstallRunDboExample(),
				example -> example.createCriteria().andInstallIdEqualTo((short) 1).andStateEqualTo("INSTALLED")
						.andLastCompletedStateEqualTo("INSTALLED").andInstallStrategyEqualTo("NONE")
						.andAdminUserIdEqualTo(1).andLastErrorIsNull())),
				"resuming an installation without a content pack must complete the run");
		assertEquals(1, systemConfigDboMapper.countByExample(where(new SystemConfigDboExample(),
				example -> example.createCriteria().andConfigKeyEqualTo("installed").andConfigValueEqualTo("true"))),
				"the resumed installation must mark the site installed again");
		assertEquals(1, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(1).andUserNameEqualTo(ADMIN_USER))),
				"resuming must keep the administrator the interrupted run already created");
		assertEquals(1, boardDboMapper.countByExample(where(new BoardDboExample(),
				example -> example.createCriteria().andBoardNameEqualTo("Recycle Bin"))),
				"resuming must not duplicate the recycle board");
		assertRecycleBinProvisioned();
		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.state").value("INSTALLED"));
	}

	@Test
	@Order(11)
	void anInstallWithoutAContentPackNeverAdoptsAnExistingAccount() throws Exception {
		register("existing_member", "member-password-123");
		int existingUserId = findUserIdByName("existing_member");
		InstallRunDbo reopenedRun = installRunDboMapper.selectByPrimaryKey((short) 1);
		reopenedRun.setState("READY");
		reopenedRun.setLastCompletedState("READY");
		reopenedRun.setRequestFingerprint(null);
		reopenedRun.setAdminUserId(null);
		reopenedRun.setLastError(null);
		installRunDboMapper.updateByPrimaryKey(reopenedRun);

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody().replace("\"adminUserName\": \"" + ADMIN_USER + "\"",
						"\"adminUserName\": \"existing_member\"")))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("Username already taken."));

		List<Integer> siteAdminPermissionIds = permissionDboMapper.selectByExample(where(new PermissionDboExample(),
				example -> example.createCriteria().andPermissionCodeEqualTo("ZFGC_SITE_ADMIN")))
				.stream().map(PermissionDbo::getPermissionId).toList();
		assertEquals(0, brUserPermissionDboMapper.countByExample(where(new BrUserPermissionDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(existingUserId)
						.andUserPermissionIdIn(siteAdminPermissionIds))),
				"an installation without a content pack must never adopt an existing account");
		assertEquals(1, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(1).andUserNameEqualTo(ADMIN_USER))),
				"the refused installation must leave the established administrator alone");
		assertEquals(1, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(existingUserId)
						.andUserNameEqualTo("existing_member").andDisplayNameEqualTo("existing_member")
						.andActiveFlagEqualTo(true))),
				"the refused installation must not rewrite the existing member's identity");

		InstallRunDbo restoredRun = installRunDboMapper.selectByPrimaryKey((short) 1);
		restoredRun.setState("INSTALLED");
		restoredRun.setLastCompletedState("INSTALLED");
		restoredRun.setAdminUserId(1);
		restoredRun.setLastError(null);
		installRunDboMapper.updateByPrimaryKey(restoredRun);
		login("existing_member", "member-password-123");
		login(ADMIN_USER, ADMIN_PASSWORD);
	}

	private static <E> E where(E example, Consumer<E> criteria) {
		criteria.accept(example);
		return example;
	}

	private static String installBody() {
		return """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "Test Admin",
				  "adminEmail": "%s@fake-email.fake.tld.thing",
				  "adminPassword": "%s",
				  "siteName": "ZFGC Test",
				  "defaultContentFormat": "MARKDOWN",
				  "provisionRecycleBin": true
				}
				""".formatted(ADMIN_USER, ADMIN_USER, ADMIN_PASSWORD);
	}

	private static String installBodyWithTokens() {
		return installBody().replace(
				"\"provisionRecycleBin\": true",
				"\"provisionRecycleBin\": true,\n  \"useTokens\": true");
	}

	@Test
	void theInstallRequestChoosesTheAuthoringDefaultEveryEditorWillSee() throws Exception {
		mockMvc.perform(get("/system/site"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.defaultContentFormat").value("MARKDOWN"));

		assertEquals("MARKDOWN", systemConfigDboMapper
				.selectByPrimaryKey(SystemConfigService.Keys.AUTHORING_DEFAULT_CONTENT_FORMAT)
				.getConfigValue(),
				"the installer must persist the authoring default the install request asked for, or the "
						+ "choice is silently discarded and every new site starts on bbcode");
	}

	@Test
	void anInstallNamingAFormatNothingCanRenderIsRefused() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody().replace("\"MARKDOWN\"", "\"HTML\"")))
				.andExpect(status().isBadRequest());
	}

	@Test
	void everyAuditedTableMaintainsUpdatedTsInTheDatabase() {
		assertEquals(List.of(), testQueryHelperMapper.findTablesMissingUpdatedTsTrigger(),
				"AbstractDao's optimistic-concurrency guard compares stored updated_ts, so a table "
						+ "without the touch_updated_ts trigger silently disables it for that table");
	}

	@Test
	void theInstallAdvisoryLockIsReleasedBackToPostgresRatherThanPooledWithTheSession() throws Exception {
		long classId = INSTALL_ADVISORY_LOCK_KEY >>> 32;
		long objId = INSTALL_ADVISORY_LOCK_KEY & 0xffffffffL;
		assertEquals(0, advisoryLocksHeld(classId, objId),
				"an earlier install must not have left its advisory lock behind");

		try (PostgresAdvisoryLock held = PostgresAdvisoryLock
				.tryAcquire(dataSource, INSTALL_ADVISORY_LOCK_KEY).orElseThrow()) {
			assertEquals(1, advisoryLocksHeld(classId, objId));
			assertTrue(PostgresAdvisoryLock
					.tryAcquire(dataSource, INSTALL_ADVISORY_LOCK_KEY).isEmpty(),
					"a second holder must be turned away while the lock is held");
		}

		assertEquals(0, advisoryLocksHeld(classId, objId),
				"closing the lock must return the key to PostgreSQL; a session pooled while still holding it "
						+ "makes every later install report that one is already in progress");
		try (PostgresAdvisoryLock next = PostgresAdvisoryLock
				.tryAcquire(dataSource, INSTALL_ADVISORY_LOCK_KEY).orElseThrow(
						() -> new AssertionError("the next install must be able to acquire the lock"))) {
			assertEquals(1, advisoryLocksHeld(classId, objId));
		}
	}

	private long advisoryLocksHeld(long classId, long objId) throws Exception {
		try (var session = dataSource.getConnection();
				var statement = session.prepareStatement("""
						select count(*) from pg_locks where locktype = 'advisory' and mode = 'ExclusiveLock'
							and classid = ? and objid = ? and objsubid = 1
							and database = (select oid from pg_database where datname = current_database())
						""")) {
			statement.setLong(1, classId);
			statement.setLong(2, objId);
			try (var result = statement.executeQuery()) {
				return result.next() ? result.getLong(1) : -1;
			}
		}
	}

	private static final long INSTALL_ADVISORY_LOCK_KEY = 0x5A46474242494E53L;
}
