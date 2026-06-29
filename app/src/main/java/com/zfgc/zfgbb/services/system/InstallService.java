package com.zfgc.zfgbb.services.system;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dao.BoardDao;
import com.zfgc.zfgbb.dao.CategoryDao;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import com.zfgc.zfgbb.mappers.BrBoardPermissionDboMapper;
import com.zfgc.zfgbb.mappers.PermissionDboMapper;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.services.core.UserService;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.config.loadoption.user.LoggedInUserLoadOptions;

@Service
@UnfilteredBoardRead("Principal-less install/seed creates and looks up the recycle-bin board")
public class InstallService {

	private static final long INSTALL_ADVISORY_LOCK_KEY = 0x5A46474242494E53L;
	private static final String SITE_ADMIN_PERMISSION_CODE = "ZFGC_SITE_ADMIN";

	private final SystemConfigService systemConfigService;
	private final UserService userService;
	private final BrUserPermissionDao brUserPermissionDao;
	private final BoardDao boardDao;
	private final CategoryDao categoryDao;
	private final BrBoardPermissionDboMapper brBoardPermissionMapper;
	private final PermissionDboMapper permissionMapper;
	private final DataSource dataSource;
	private final InstallRunRepository installRun;
	private final InstallPhaseTransactions phases;
	private final UserDataProvider userDataProvider;
	private final AuthService authService;

	@Autowired
	public InstallService(SystemConfigService systemConfigService,
			UserService userService,
			BrUserPermissionDao brUserPermissionDao,
			BoardDao boardDao,
			CategoryDao categoryDao,
			BrBoardPermissionDboMapper brBoardPermissionMapper,
			PermissionDboMapper permissionMapper,
				DataSource dataSource, InstallRunRepository installRun, InstallPhaseTransactions phases,
				UserDataProvider userDataProvider, AuthService authService) {
		this.systemConfigService = systemConfigService;
		this.userService = userService;
		this.brUserPermissionDao = brUserPermissionDao;
		this.boardDao = boardDao;
		this.categoryDao = categoryDao;
		this.brBoardPermissionMapper = brBoardPermissionMapper;
		this.permissionMapper = permissionMapper;
		this.dataSource = dataSource;
		this.installRun = installRun;
		this.phases = phases;
		this.userDataProvider = userDataProvider;
		this.authService = authService;
	}

	public InstallResult install(InstallRequest req) {
		if (req == null) {
			throw new ZfgcInvalidRequestException("Install request is required.");
		}
		Optional<String> contentPack = Optional.ofNullable(StringUtils.trimToNull(req.contentPack()));
		contentPack.ifPresent(this::requireContentPack);
		String siteName = StringUtils.defaultIfBlank(req.siteName(), "ZFGBB");
		String fingerprint = fingerprint(req, contentPack.orElse(null), siteName);
		try (Connection lock = dataSource.getConnection()) {
			lock.setAutoCommit(true);
			boolean acquired;
			try (java.sql.PreparedStatement acquire = lock.prepareStatement("select pg_try_advisory_lock(?)")) {
			acquire.setLong(1, INSTALL_ADVISORY_LOCK_KEY);
			try (java.sql.ResultSet result = acquire.executeQuery()) {
				acquired = result.next() && result.getBoolean(1);
			}
			}
			if (!acquired)
				throw new com.zfgc.zfgbb.exception.ZfgcConflictException("Installation is already in progress.");
			boolean workflowClaimed = false;
			try {
				installRun.claim(fingerprint, contentPack.orElse(null), Boolean.TRUE.equals(req.provisionRecycleBin()), siteName);
				workflowClaimed = true;
				InstallRunRepository.Run run = installRun.get();
				User admin;
				if ("READY".equals(run.state())) {
					admin = phases.call(() -> {
						User created = installCore(req);
						installRun.setAdmin(created.getUserId());
						installRun.advance(List.of("READY"), "CORE_READY");
						return created;
					});
				} else {
					admin = userDataProvider.findUser(run.adminUserId(), new LoggedInUserLoadOptions())
							.orElseThrow(() -> new IllegalStateException("Installation administrator is missing."));
					User authenticated = authService.reauthenticate(admin.getUsername(), req.adminPassword());
					if (!Objects.equals(authenticated.getUserId(), admin.getUserId()))
						throw new IllegalStateException("Installation administrator identity changed.");
				}
				if ("INSTALLED".equals(installRun.get().state()))
					return installResult(admin, siteName, contentPack.orElse(null));
				if ("CORE_READY".equals(installRun.get().state())) {
					contentPack.ifPresent(this::applyContentPack);
					phases.call(() -> { installRun.advance(List.of("CORE_READY"), "PACK_READY"); return null; });
				}
				if ("PACK_READY".equals(installRun.get().state())) {
					phases.call(() -> {
						if (Boolean.TRUE.equals(req.provisionRecycleBin())) provisionRecycleBinBoard();
						else systemConfigService.unset(SystemConfigService.Keys.RECYCLE_BOARD_ID);
						installRun.advance(List.of("PACK_READY"), "RECYCLE_READY"); return null;
					});
				}
				phases.call(() -> {
					systemConfigService.set(SystemConfigService.Keys.SITE_NAME, siteName);
					systemConfigService.set(SystemConfigService.Keys.INSTALLED_AT, LocalDateTime.now().toString());
					systemConfigService.set(SystemConfigService.Keys.INSTALLED_BY_USER_ID, String.valueOf(admin.getUserId()));
					systemConfigService.set(SystemConfigService.Keys.INSTALLED, "true");
					installRun.advance(List.of("RECYCLE_READY"), "INSTALLED"); return null;
				});
				return installResult(admin, siteName, contentPack.orElse(null));
			} catch (RuntimeException failure) {
				if (workflowClaimed)
					installRun.fail(failure);
				throw failure;
			} finally {
				try (java.sql.PreparedStatement release = lock.prepareStatement("select pg_advisory_unlock(?)")) {
					release.setLong(1, INSTALL_ADVISORY_LOCK_KEY); release.execute();
				}
			}
		} catch (java.sql.SQLException e) {
			throw new IllegalStateException("Unable to serialize installation", e);
		}
	}

	private InstallResult installResult(User admin, String siteName, String contentPack) {
		return new InstallResult(new InstallResponse(true, admin.getUserId(), siteName,
				contentPack, null, null), admin);
	}

	protected User installCore(InstallRequest req) {
		Integer siteAdminPermissionId = requirePermissionId(SITE_ADMIN_PERMISSION_CODE);
		RegistrationRequest reg = new RegistrationRequest(
				req.adminUserName(),
				req.adminDisplayName(),
				req.adminEmail(),
				req.adminPassword());
		User admin = userService.createNewUser(reg);

		BrUserPermissionDbo siteAdmin = new BrUserPermissionDbo();
		siteAdmin.setUserId(admin.getUserId());
		siteAdmin.setUserPermissionId(siteAdminPermissionId);
		brUserPermissionDao.save(siteAdmin);

		return admin;
	}

	private Integer requirePermissionId(String permissionCode) {
		PermissionDboExample ex = new PermissionDboExample();
		ex.createCriteria().andPermissionCodeEqualTo(permissionCode);
		List<PermissionDbo> matches = permissionMapper.selectByExample(ex);
		if (matches.size() != 1)
			throw new IllegalStateException("Expected exactly one permission for code " + permissionCode);
		return matches.get(0).getPermissionId();
	}

	private String fingerprint(InstallRequest req, String pack, String siteName) {
		String canonical = "v1\n" + req.adminUserName() + "\n" + req.adminDisplayName() + "\n"
				+ req.adminEmail() + "\n" + siteName + "\n"
				+ String.valueOf(pack) + "\n" + Boolean.TRUE.equals(req.provisionRecycleBin());
		try {
			return java.util.HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
					.digest(canonical.getBytes(StandardCharsets.UTF_8)));
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private void requireContentPack(String contentPack) {
		if (!contentPack.matches("[a-z0-9-]+"))
			throw new ZfgcInvalidRequestException("Invalid content pack name: " + contentPack);
		try {
			Resource[] scripts = new PathMatchingResourcePatternResolver()
					.getResources("classpath:db/packs/" + contentPack + "/*.sql");
			if (scripts.length == 0)
				throw new ZfgcInvalidRequestException("Unknown content pack: " + contentPack);
		} catch (IOException e) {
			throw new ZfgcInvalidRequestException("Unknown content pack: " + contentPack);
		}
	}

	private void provisionRecycleBinBoard() {
		BoardDbo recycleBinBoard = configuredRecycleBin().orElseGet(() -> {
			BoardDboExample named = new BoardDboExample();
			named.createCriteria().andBoardNameEqualTo("Recycle Bin");
			return boardDao.get(named).stream().findFirst().orElseGet(this::createRecycleBinBoard);
		});
		repairRecyclePermissions(recycleBinBoard.getBoardId());
		systemConfigService.set(SystemConfigService.Keys.RECYCLE_BOARD_ID,
				String.valueOf(recycleBinBoard.getBoardId()));
	}

	private Optional<BoardDbo> configuredRecycleBin() {
		String configured = StringUtils.trimToNull(systemConfigService.get(SystemConfigService.Keys.RECYCLE_BOARD_ID));
		if (configured == null)
			return Optional.empty();
		try {
			return boardDao.get(Integer.valueOf(configured))
					.filter(board -> "Recycle Bin".equals(board.getBoardName()));
		} catch (NumberFormatException invalid) {
			return Optional.empty();
		}
	}

	private BoardDbo createRecycleBinBoard() {
		BoardDbo recycleBinBoard = new BoardDbo();
		recycleBinBoard.setBoardName("Recycle Bin");
		recycleBinBoard.setDescription("Removed posts awaiting permanent deletion.");
		recycleBinBoard.setCategoryId(firstCategoryId());
		recycleBinBoard.setSeqno(nextBoardSeqno());
		return boardDao.save(recycleBinBoard);
	}

	private void repairRecyclePermissions(Integer boardId) {
		for (Integer permissionId : moderationPermissionIds()) {
			BrBoardPermissionDboExample existing = new BrBoardPermissionDboExample();
			existing.createCriteria().andBoardIdEqualTo(boardId).andPermissionIdEqualTo(permissionId);
			if (!brBoardPermissionMapper.selectByExample(existing).isEmpty())
				continue;
			BrBoardPermissionDbo boardPermission = new BrBoardPermissionDbo();
			boardPermission.setBoardId(boardId);
			boardPermission.setPermissionId(permissionId);
			brBoardPermissionMapper.insertSelective(boardPermission);
		}
	}

	private Integer firstCategoryId() {
		CategoryDboExample ex = new CategoryDboExample();
		ex.setOrderByClause("category_order, category_id");
		return categoryDao.get(ex).stream()
				.findFirst()
				.map(CategoryDbo::getCategoryId)
				.orElseGet(this::createDefaultCategory);
	}

	private Integer createDefaultCategory() {
		CategoryDbo category = new CategoryDbo();
		category.setCategoryName("General");
		category.setCategoryOrder((short) 1);
		return categoryDao.save(category).getCategoryId();
	}

	private Integer nextBoardSeqno() {
		return boardDao.get(new BoardDboExample()).stream()
				.map(BoardDbo::getSeqno)
				.filter(Objects::nonNull)
				.max(Integer::compareTo)
				.map(maxSeqno -> maxSeqno + 1)
				.orElse(1);
	}

	private List<Integer> moderationPermissionIds() {
		PermissionDboExample ex = new PermissionDboExample();
		ex.createCriteria().andPermissionCodeIn(List.of("ZFGC_SITE_ADMIN", "ZFGC_SITE_MODERATOR"));
		return permissionMapper.selectByExample(ex).stream()
				.map(PermissionDbo::getPermissionId)
				.toList();
	}

	private void applyContentPack(String contentPack) {
		Flyway.configure()
				.dataSource(dataSource)
				.locations("classpath:db/packs/" + contentPack)
				.schemas("zfgbb")
				.defaultSchema("zfgbb")
				.table("flyway_pack_history")
				.baselineOnMigrate(true)
				.baselineVersion(MigrationVersion.fromVersion("0"))
				.load()
				.migrate();
	}
}
