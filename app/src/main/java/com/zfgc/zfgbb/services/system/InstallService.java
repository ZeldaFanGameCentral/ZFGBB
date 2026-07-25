package com.zfgc.zfgbb.services.system;

import static com.zfgc.zfgbb.util.ZfgcSecurityUtils.sha256Hex;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dao.BoardDao;
import com.zfgc.zfgbb.dao.CategoryDao;
import com.zfgc.zfgbb.dbo.AccountDeletionAuditDboExample;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDboExample;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.mappers.AccountDeletionAuditDboMapper;
import com.zfgc.zfgbb.mappers.AccountDeletionRequestDboMapper;
import com.zfgc.zfgbb.mappers.BrBoardPermissionDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.PermissionDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserPermissionGrantMapper;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.operations.archive.BackupManifest;
import com.zfgc.zfgbb.operations.postgres.PostgresAdvisoryLock;
import com.zfgc.zfgbb.services.users.UserRegistrationService;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.users.deletion.CoreUserDataHandler;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.config.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import lombok.RequiredArgsConstructor;

@Service
@UnfilteredBoardRead("install runs before there is a user")
@RequiredArgsConstructor
public class InstallService {

	private static final long INSTALL_ADVISORY_LOCK_KEY = 0x5A46474242494E53L;
	private static final String SITE_ADMIN_PERMISSION_CODE = "ZFGC_SITE_ADMIN";
	private static final int LOWEST_ADOPTABLE_USER_ID = 1;
	private static final List<String> STATES_AWAITING_RECYCLE_BIN_PHASE = List.of("CORE_READY");

	private final SystemConfigService systemConfigService;
	private final BBCodeGrammarLoader grammarLoader;
	private final UserRegistrationService userRegistrationService;
	private final UserPermissionGrantMapper userPermissionGrantMapper;
	private final InstallerCompatibilityService installerCompatibilityService;
	private final BoardDao boardDao;
	private final CategoryDao categoryDao;
	private final BrBoardPermissionDboMapper brBoardPermissionMapper;
	private final PermissionDboMapper permissionMapper;
	private final ThreadDboMapper threadMapper;
	private final MessageDboMapper messageMapper;
	private final AccountDeletionRequestDboMapper accountDeletionRequestMapper;
	private final AccountDeletionAuditDboMapper accountDeletionAuditMapper;
	private final DataSource dataSource;
	private final InstallRunRepository installRun;
	private final InstallPhaseTransactions phases;
	private final UserDataProvider userDataProvider;
	private final AuthService authService;
	private final ContentArchiveService contentArchiveService;
	private final RestoreService restoreService;
	private final CoreUserDataHandler coreUserDataHandler;

	public InstallResult install(InstallRequest req) {
		if (req == null) {
			throw new ZfgcInvalidRequestException("Install request is required.");
		}
		Optional<String> contentPack = Optional.ofNullable(StringUtils.trimToNull(req.contentPack()));
		contentPack.ifPresent(this::requireInstallableContentPack);
		InstallStrategy requestedStrategy = contentPack.isPresent()
				? InstallStrategy.ARCHIVE : InstallStrategy.NONE;
		String siteName = StringUtils.defaultIfBlank(req.siteName(), "ZFGBB");
		ContentFormat authoringDefaultContentFormat = requestedAuthoringDefaultContentFormat(req);
		String fingerprint = fingerprint(req, contentPack.orElse(null), siteName,
				authoringDefaultContentFormat);
		try (PostgresAdvisoryLock installLock = PostgresAdvisoryLock
				.tryAcquire(dataSource, INSTALL_ADVISORY_LOCK_KEY)
				.orElseThrow(() -> new ZfgcConflictException("Installation is already in progress."))) {
			boolean workflowClaimed = false;
			try {
				if (declinesRecycleBin(req, contentPack))
					requireRecycleBinRemovable();
				boolean archiveInstall = installRun.claim(fingerprint, contentPack.orElse(null),
						Boolean.TRUE.equals(req.provisionRecycleBin()), siteName,
						requestedStrategy) == InstallStrategy.ARCHIVE;
				workflowClaimed = true;
				InstallRunRepository.Run run = installRun.get();
				User admin;
				if ("READY".equals(run.state())) {
					admin = archiveInstall
							? installFromContentArchive(req, contentPack.get(), siteName, fingerprint)
							: phases.call(() -> {
								User created = installCore(req);
								installRun.setAdmin(created.getUserId());
								installRun.advance(List.of("READY"), "CORE_READY");
								return created;
							});
				} else {
					admin = userDataProvider.findUser(run.adminUserId(), UserLoadOptions.loggedIn())
							.orElseThrow(() -> new IllegalStateException("Installation administrator is missing."));
					User authenticated = authService.reauthenticate(admin.getUsername(), req.adminPassword());
					if (!Objects.equals(authenticated.getUserId(), admin.getUserId()))
						throw new IllegalStateException("Installation administrator identity changed.");
				}
				if ("INSTALLED".equals(installRun.get().state()))
					return installResult(admin, siteName, contentPack.orElse(null));
				if (STATES_AWAITING_RECYCLE_BIN_PHASE.contains(installRun.get().state())) {
					phases.run(() -> {
						if (Boolean.TRUE.equals(req.provisionRecycleBin())) provisionRecycleBinBoard();
						else if (contentPack.isPresent()) omitRecycleBinBoard();
						else systemConfigService.unset(SystemConfigService.Keys.RECYCLE_BOARD_ID);
						installRun.advance(STATES_AWAITING_RECYCLE_BIN_PHASE, "RECYCLE_READY");
					});
				}
				phases.run(() -> {
					OffsetDateTime tokenCutoff = OffsetDateTime.now(ZoneOffset.UTC)
							.truncatedTo(ChronoUnit.SECONDS);
					systemConfigService.set(SystemConfigService.Keys.SITE_NAME, siteName);
					systemConfigService.setAuthoringDefaultContentFormat(authoringDefaultContentFormat);
					systemConfigService.set(SystemConfigService.Keys.INSTALLED_AT, LocalDateTime.now().toString());
					systemConfigService.set(SystemConfigService.Keys.INSTALLED_BY_USER_ID, String.valueOf(admin.getUserId()));
					int usersCutOff = userDataProvider.cutOffExistingTokensForAllUsers(tokenCutoff);
					if (usersCutOff < 1)
						throw new IllegalStateException("Installation completed without any users to secure.");
					admin.setTokensValidAfterTs(tokenCutoff);
					systemConfigService.set(SystemConfigService.Keys.INSTALLED, "true");
					installRun.advance(List.of("RECYCLE_READY"), "INSTALLED");
				});
				return installResult(admin, siteName, contentPack.orElse(null));
			} catch (RuntimeException failure) {
				if (workflowClaimed)
					installRun.fail(failure);
				throw failure;
			}
		} catch (SQLException unserializable) {
			throw new IllegalStateException("Unable to serialize installation", unserializable);
		}
	}

	private InstallResult installResult(User admin, String siteName, String contentPack) {
		return new InstallResult(new InstallResponse(true, admin.getUserId(), siteName,
				contentPack, null, null), admin);
	}

	private User installCore(InstallRequest req) {
		Integer siteAdminPermissionId = requirePermissionId(SITE_ADMIN_PERMISSION_CODE);
		User admin = userRegistrationService.createNewUser(administratorRegistration(req));

		grantPermission(admin.getUserId(), siteAdminPermissionId);

		return admin;
	}

	private void requireInstallableContentPack(String contentPackName) {
		if (contentArchiveService.hasArchive(contentPackName))
			return;
		if (!contentArchiveService.hasContentPack(contentPackName))
			throw new ZfgcInvalidRequestException("Unknown content pack: " + contentPackName);
		throw new ZfgcInvalidRequestException("Content pack " + contentPackName
				+ " does not ship an installation archive.");
	}

	private User installFromContentArchive(InstallRequest req, String contentPackName,
			String siteName, String fingerprint) {
		int anchorAdministratorId;
		try (ContentArchiveService.ContentArchive archive = contentArchiveService.open(contentPackName)) {
			anchorAdministratorId = restoreService.restoreArchiveWithoutMaintenanceLease(
					archive.path(), InstallService::requireInstallerCompatibleArchive)
					.installerAnchorAdministratorId();
		}
		grammarLoader.reloadFromTheDatabase();
		phases.run(() -> {
			authService.deleteEveryToken();
			accountDeletionRequestMapper.deleteByExample(new AccountDeletionRequestDboExample());
			accountDeletionAuditMapper.deleteByExample(new AccountDeletionAuditDboExample());
			systemConfigService.unset(SystemConfigService.Keys.INSTALLED);
			systemConfigService.unset(SystemConfigService.Keys.INSTALLED_AT);
			systemConfigService.unset(SystemConfigService.Keys.INSTALLED_BY_USER_ID);
		});
		if (!Boolean.TRUE.equals(req.provisionRecycleBin()))
			requireRecycleBinRemovable();
		phases.run(() -> {
			installRun.reestablishAfterArchiveRestore(fingerprint, contentPackName,
					Boolean.TRUE.equals(req.provisionRecycleBin()), siteName);
			systemConfigService.set(SystemConfigService.Keys.SITE_NAME, siteName);
		});
		User administrator = reconcileAdministratorIdentity(req, anchorAdministratorId);
		phases.run(() -> {
			installRun.setAdmin(administrator.getUserId());
			installRun.advance(List.of("READY"), "CORE_READY");
		});
		return administrator;
	}

	private static void requireInstallerCompatibleArchive(BackupManifest manifest) {
		if (!manifest.installerCompatible() || manifest.installerAnchorAdministratorId() <= 0)
			throw new ZfgcInvalidRequestException("The content pack archive cannot be installed "
					+ "because it does not carry a single reconcilable administrator identity.");
	}

	public User reconcileAdministratorIdentity(InstallRequest req, int administratorUserId) {
		if (req == null)
			throw new ZfgcInvalidRequestException("Install request is required.");
		requireRestoredAdministratorAnchor(administratorUserId);
		Integer siteAdminPermissionId = requirePermissionId(SITE_ADMIN_PERMISSION_CODE);
		return phases.call(() -> {
			int reconciledUserId = restoredUserAdoptableAsAdministrator(req, administratorUserId)
					.orElse(administratorUserId);
			if (reconciledUserId != administratorUserId)
				coreUserDataHandler.neutralizeUserIdentity(administratorUserId);
			User administrator = userRegistrationService.reassignUserIdentity(
					administratorRegistration(req), reconciledUserId);
			grantPermission(administrator.getUserId(), siteAdminPermissionId);
			return administrator;
		});
	}

	private Optional<Integer> restoredUserAdoptableAsAdministrator(InstallRequest req,
			int anchorAdministratorId) {
		List<Integer> holders = userDataProvider.findUserIdsHoldingIdentity(req.adminUserName());
		if (holders.size() != 1)
			return Optional.empty();
		return holders.stream()
				.filter(holder -> holder.intValue() >= LOWEST_ADOPTABLE_USER_ID)
				.filter(holder -> holder.intValue() != anchorAdministratorId)
				.findFirst();
	}

	private void requireRestoredAdministratorAnchor(int administratorUserId) {
		if (administratorUserId <= 0)
			throw new ZfgcInvalidRequestException(
					"The administrator identity can only be reconciled onto a real user account.");
		List<Integer> anchors = installerCompatibilityService.siteAdministratorIds();
		if (anchors.size() != 1 || !anchors.get(0).equals(administratorUserId))
			throw new ZfgcInvalidRequestException("User " + administratorUserId
					+ " is not the restored site administrator anchor.");
	}

	private static RegistrationRequest administratorRegistration(InstallRequest req) {
		return new RegistrationRequest(
				req.adminUserName(),
				req.adminDisplayName(),
				req.adminEmail(),
				req.adminPassword());
	}

	private void grantPermission(Integer userId, Integer userPermissionId) {
		userPermissionGrantMapper.grantPermissionIfAbsent(userId, userPermissionId);
	}

	private Integer requirePermissionId(String permissionCode) {
		PermissionDboExample ex = new PermissionDboExample();
		ex.createCriteria().andPermissionCodeEqualTo(permissionCode);
		List<PermissionDbo> matches = permissionMapper.selectByExample(ex);
		if (matches.size() != 1)
			throw new IllegalStateException("Expected exactly one permission for code " + permissionCode);
		return matches.get(0).getPermissionId();
	}

	private static ContentFormat requestedAuthoringDefaultContentFormat(InstallRequest req) {
		String requested = StringUtils.trimToNull(req.defaultContentFormat());
		if (requested == null)
			return ContentFormat.BBCODE;
		return ContentFormat.parse(requested)
				.orElseThrow(() -> new ZfgcInvalidRequestException("defaultContentFormat must be one of "
						+ ContentFormat.authorableCodes() + " but was: " + requested));
	}

	private String fingerprint(InstallRequest req, String pack, String siteName,
			ContentFormat authoringDefaultContentFormat) {
		String canonical = "v1\n" + req.adminUserName() + "\n" + req.adminDisplayName() + "\n"
				+ req.adminEmail() + "\n" + siteName + "\n"
				+ String.valueOf(pack) + "\n" + Boolean.TRUE.equals(req.provisionRecycleBin()) + "\n"
				+ authoringDefaultContentFormat.name();
		return sha256Hex(canonical);
	}

	private void provisionRecycleBinBoard() {
		BoardDbo recycleBinBoard = configuredRecycleBin()
				.or(this::recycleBinBoardByName)
				.orElseGet(this::createRecycleBinBoard);
		repairRecyclePermissions(recycleBinBoard.getBoardId());
		systemConfigService.set(SystemConfigService.Keys.RECYCLE_BOARD_ID,
				String.valueOf(recycleBinBoard.getBoardId()));
	}

	private void omitRecycleBinBoard() {
		Optional<BoardDbo> recycleBinBoard = configuredRecycleBin();
		systemConfigService.unset(SystemConfigService.Keys.RECYCLE_BOARD_ID);
		recycleBinBoard.ifPresent(board -> {
			requireEmptyRecycleBinBoard(board);
			BrBoardPermissionDboExample permissions = new BrBoardPermissionDboExample();
			permissions.createCriteria().andBoardIdEqualTo(board.getBoardId());
			brBoardPermissionMapper.deleteByExample(permissions);
			boardDao.delete(board.getBoardId());
		});
	}

	private void requireRecycleBinRemovable() {
		configuredRecycleBin().ifPresent(this::requireEmptyRecycleBinBoard);
	}

	private void requireEmptyRecycleBinBoard(BoardDbo recycleBinBoard) {
		long retained = retainedRecycleBinContent(recycleBinBoard.getBoardId());
		if (retained > 0)
			throw new ZfgcInvalidRequestException("Board " + recycleBinBoard.getBoardId() + " (\""
					+ recycleBinBoard.getBoardName() + "\") is this installation's recycle bin and "
					+ "still holds " + retained + " item(s), so the installation cannot remove it. "
					+ "Install with provisionRecycleBin enabled, or empty the board before "
					+ "installing.");
	}

	private long retainedRecycleBinContent(Integer boardId) {
		ThreadDboExample threads = new ThreadDboExample();
		threads.createCriteria().andBoardIdEqualTo(boardId);
		MessageDboExample messages = new MessageDboExample();
		messages.createCriteria().andBoardIdEqualTo(boardId);
		BoardDboExample childBoards = new BoardDboExample();
		childBoards.createCriteria().andParentBoardIdEqualTo(boardId);
		return threadMapper.countByExample(threads) + messageMapper.countByExample(messages)
				+ boardDao.count(childBoards);
	}

	private Optional<BoardDbo> recycleBinBoardByName() {
		BoardDboExample named = new BoardDboExample();
		named.createCriteria().andBoardNameEqualTo("Recycle Bin");
		return boardDao.get(named).stream().findFirst();
	}

	private static boolean declinesRecycleBin(InstallRequest req, Optional<String> contentPack) {
		return contentPack.isPresent() && !Boolean.TRUE.equals(req.provisionRecycleBin());
	}

	private Optional<BoardDbo> configuredRecycleBin() {
		String configured = StringUtils.trimToNull(systemConfigService.get(SystemConfigService.Keys.RECYCLE_BOARD_ID));
		if (configured == null)
			return Optional.empty();
		try {
			return boardDao.find(Integer.valueOf(configured));
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

}
