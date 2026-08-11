package com.zfgc.zfgbb.services.system;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dao.forum.BoardDao;
import com.zfgc.zfgbb.dao.forum.BrBoardPermissionDao;
import com.zfgc.zfgbb.dao.forum.CategoryDao;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.users.PermissionDao;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResponse;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.services.users.UserRegistrationService;

@Service
public class InstallService {

	public static final Integer ZFGC_SITE_ADMIN_PERMISSION_ID = 10;

	private final SystemConfigService systemConfigService;
	private final UserRegistrationService userRegistrationService;
	private final BrUserPermissionDao brUserPermissionDao;
	private final BoardDao boardDao;
	private final CategoryDao categoryDao;
	private final BrBoardPermissionDao brBoardPermissionDao;
	private final PermissionDao permissionDao;

	public InstallService(SystemConfigService systemConfigService,
			UserRegistrationService userRegistrationService,
			BrUserPermissionDao brUserPermissionDao,
			BoardDao boardDao,
			CategoryDao categoryDao,
			BrBoardPermissionDao brBoardPermissionDao,
			PermissionDao permissionDao) {
		this.systemConfigService = systemConfigService;
		this.userRegistrationService = userRegistrationService;
		this.brUserPermissionDao = brUserPermissionDao;
		this.boardDao = boardDao;
		this.categoryDao = categoryDao;
		this.brBoardPermissionDao = brBoardPermissionDao;
		this.permissionDao = permissionDao;
	}

	public InstallResult install(InstallRequest req) {
		if (req == null) {
			throw new ZfgcInvalidRequestException("Install request is required.");
		}
		if (systemConfigService.isInstalled()) {
			throw new ZfgcInvalidRequestException("System is already installed.");
		}

		User admin = installCore(req);

		String siteName = StringUtils.defaultIfBlank(req.siteName(), "ZFGBB");
		InstallResponse response = new InstallResponse(true, admin.getUserId(), siteName, null, null);
		return new InstallResult(response, admin);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected User installCore(InstallRequest req) {
		RegistrationRequest reg = new RegistrationRequest(
				req.adminUserName(),
				req.adminDisplayName(),
				req.adminEmail(),
				req.adminPassword());
		User admin = userRegistrationService.createNewUser(reg);

		// createNewUser grants ZFGC_USER; the install endpoint also grants
		// ZFGC_SITE_ADMIN.
		BrUserPermissionDbo siteAdmin = new BrUserPermissionDbo();
		siteAdmin.setUserId(admin.getUserId());
		siteAdmin.setUserPermissionId(ZFGC_SITE_ADMIN_PERMISSION_ID);
		brUserPermissionDao.insert(siteAdmin);

		if (Boolean.TRUE.equals(req.provisionRecycleBin()))
			provisionRecycleBinBoard();
		else
			systemConfigService.unset(SystemConfigService.Keys.RECYCLE_BOARD_ID);

		String siteName = StringUtils.defaultIfBlank(req.siteName(), "ZFGBB");
		systemConfigService.set(SystemConfigService.Keys.SITE_NAME, siteName);
		systemConfigService.setAuthoringDefaultContentFormat(ContentFormat.BBCODE);
		systemConfigService.set(SystemConfigService.Keys.INSTALLED_AT, OffsetDateTime.now().toString());
		systemConfigService.set(SystemConfigService.Keys.INSTALLED_BY_USER_ID, String.valueOf(admin.getUserId()));
		// Flip the marker LAST so a partial-install failure means we didnt install.

		systemConfigService.set(SystemConfigService.Keys.INSTALLED, "true");

		return admin;
	}

	private void provisionRecycleBinBoard() {
		BoardDbo recycleBinBoard = configuredRecycleBin()
				.or(this::recycleBinBoardByName)
				.orElseGet(this::createRecycleBinBoard);
		repairRecyclePermissions(recycleBinBoard.getBoardId());
		systemConfigService.set(SystemConfigService.Keys.RECYCLE_BOARD_ID,
				String.valueOf(recycleBinBoard.getBoardId()));
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

	private Optional<BoardDbo> recycleBinBoardByName() {
		BoardDboExample named = new BoardDboExample();
		named.createCriteria().andBoardNameEqualTo("Recycle Bin");
		return boardDao.getOne(named);
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
			if (!brBoardPermissionDao.get(existing).isEmpty())
				continue;
			BrBoardPermissionDbo boardPermission = new BrBoardPermissionDbo();
			boardPermission.setBoardId(boardId);
			boardPermission.setPermissionId(permissionId);
			brBoardPermissionDao.insertSelective(boardPermission);
		}
	}

	private Integer firstCategoryId() {
		CategoryDboExample ex = new CategoryDboExample();
		ex.setOrderByClause("category_order, category_id");
		return categoryDao.getOne(ex)
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
		return permissionDao.get(ex).stream()
				.map(PermissionDbo::getPermissionId)
				.toList();
	}
}
