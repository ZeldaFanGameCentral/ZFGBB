package com.zfgc.zfgbb.migrator.converters.cms;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ContentCollectionDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionDboExample;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.dbo.ProjectDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.dbo.ProjectTagDbo;
import com.zfgc.zfgbb.dbo.ProjectTagDboExample;
import com.zfgc.zfgbb.dbo.TagDbo;
import com.zfgc.zfgbb.dbo.TagDboExample;
import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamDboExample;
import com.zfgc.zfgbb.dbo.TeamMemberDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDboExample;
import com.zfgc.zfgbb.mappers.ContentCollectionDboMapper;
import com.zfgc.zfgbb.mappers.ContentCollectionItemDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.ProjectDboMapper;
import com.zfgc.zfgbb.mappers.ProjectDownloadDboMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;
import com.zfgc.zfgbb.mappers.ProjectTagDboMapper;
import com.zfgc.zfgbb.mappers.TagDboMapper;
import com.zfgc.zfgbb.mappers.TeamDboMapper;
import com.zfgc.zfgbb.mappers.TeamMemberDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.migrator.ci.dbo.CiPotmDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiPotmDbExample;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDbExample;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDbWithBLOBs;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDownloadDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDownloadDbExample;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDbExample;
import com.zfgc.zfgbb.migrator.ci.mappers.CiPotmDbMapper;
import com.zfgc.zfgbb.migrator.ci.mappers.CiProjectDbMapper;
import com.zfgc.zfgbb.migrator.ci.mappers.CiProjectDownloadDbMapper;
import com.zfgc.zfgbb.migrator.ci.mappers.CiProjectScreenshotDbMapper;
import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.Cancellable;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFGameDbMapper;
import com.zfgc.zfgbb.migrator.smf.queries.SmfDownloadQueryMapper;
import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.dbo.MigrationConflictDboExample;
import com.zfgc.zfgbb.mappers.MigrationConflictDboMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectsConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectsConverter.class);

	private final CiProjectDbMapper ciProjectMapper;
	private final CiProjectScreenshotDbMapper ciScreenshotMapper;
	private final CiProjectDownloadDbMapper ciDownloadMapper;
	private final SMFGameDbMapper smfGameMapper;
	private final SmfDownloadQueryMapper gameDownloadMapper;
	private final ProjectDboMapper projectMapper;
	private final ContentEntityDboMapper contentEntityMapper;
	private final MigrationConflictDboMapper conflictMapper;
	private final ProjectScreenshotDboMapper screenshotMapper;
	private final ProjectDownloadDboMapper downloadMapper;
	private final ContentResourceDboMapper contentMapper;
	private final ContentCollectionDboMapper collectionMapper;
	private final CiPotmDbMapper potmMapper;
	private final ContentCollectionItemDboMapper collectionItemMapper;
	private final TeamDboMapper teamMapper;
	private final TeamMemberDboMapper teamMemberMapper;
	private final TagDboMapper tagMapper;
	private final ProjectTagDboMapper projectTagMapper;
	private final ProjectNewsDboMapper newsMapper;
	private final UserDboMapper userMapper;
	private final WikiPageStore wikiPages;
	private final MigratorIdMapService idMap;

	private CmsAssetStore assets;
	private Path filesRoot;

	@Override
	public JobType getType() {
		return JobType.PROJECTS;
	}

	@Override
	@Transactional
	public Void convertToZfgbb() {
		initAssets();
		Set<String> usedSlugs = new HashSet<>();
		contentEntityMapper.selectByExample(new ContentEntityDboExample()).forEach(e -> usedSlugs.add(e.getSlug()));
		Map<String, Integer> projectByTitle = new HashMap<>();
		Set<String> wikiAdopted = loadWikiAdoptedEntities();

		Map<Integer, List<CiProjectScreenshotDb>> screenshotsByProject = ciScreenshotMapper
				.selectByExample(new CiProjectScreenshotDbExample()).stream()
				.collect(Collectors.groupingBy(CiProjectScreenshotDb::getProjectId));
		Map<Integer, List<CiProjectDownloadDb>> downloadsByProject = ciDownloadMapper
				.selectByExample(new CiProjectDownloadDbExample()).stream()
				.collect(Collectors.groupingBy(CiProjectDownloadDb::getProjectId));

		for (CiProjectDbWithBLOBs ciProject : ciProjectMapper.selectByExampleWithBLOBs(new CiProjectDbExample())) {
			Cancellable.check();
			String title = CmsSupport.unescape(ciProject.getTitle());
			Integer ownerId = idMap.lookupOrNull(LegacyEntityType.USER, ciProject.getMemberId());
			ProjectDbo ext = new ProjectDbo();
			ext.setStatus(CmsSupport.ciProjectStatus(ciProject.getStatus()));
			ext.setProgress(CmsSupport.toShort(ciProject.getProgress()));
			ext.setLanguage(ciProject.getLanguage());
			ext.setRequirements(ciProject.getRequirements());
			ContentEntityDbo entity = new ContentEntityDbo();
			entity.setEntityType("PROJECT");
			entity.setTitle(title);
			entity.setSummary(CmsSupport.unescape(ciProject.getDescription()));
			entity.setThreadId(idMap.lookupOrNull(LegacyEntityType.THREAD, ciProject.getTopicId()));
			entity.setCreatedUserId(ownerId);
			entity.setViewCount(ciProject.getViews());
			entity.setDownloadCount(ciProject.getDownloads());
			entity.setPublishedTs(CmsSupport.epoch(
					ciProject.getTimeCreated() != null && ciProject.getTimeCreated() > 0 ? ciProject.getTimeCreated() : ciProject.getLastUpdated()));
			entity.setLastUpdatedTs(CmsSupport.epoch(ciProject.getLastUpdated()));
			entity.setRating(ciProject.getRating());
			entity.setVoteCount(ciProject.getVotes());
			entity.setAuthorName(resolvedAuthor(idMap.lookupOrNull(LegacyEntityType.PROJECT, ciProject.getId()),
					CmsSupport.unescape(ciProject.getMemberName())));
			entity.setPreviewContentResourceId(storeAsset("projects", ciProject.getPreview(), ownerId, CmsAssetStore.TYPE_IMAGE));
			entity.setMigrationHash(MigrationHasher.hash("ci" + ciProject.getId() + title + ext.getStatus()
					+ ext.getProgress() + entity.getThreadId() + entity.getPreviewContentResourceId()
					+ entity.getPublishedTs() + entity.getRating() + entity.getVoteCount() + entity.getAuthorName()));
			Integer projectId = upsertProject(LegacyEntityType.PROJECT, ciProject.getId(), entity, ext, usedSlugs);
			ensureEntityPage(entity, wikiAdopted.contains("PROJECT:" + ciProject.getId()));
			convertScreenshots(projectId, ownerId, screenshotsByProject.get(ciProject.getId()));
			convertDownloads(projectId, ownerId, downloadsByProject.get(ciProject.getId()));
			projectByTitle.put(CmsSupport.normalizeTitle(title), projectId);
		}

		Integer z3Id = ensureCollection("z3", "z3 (Community Showcase)", "EVENT");
		Map<Integer, SmfDownloadQueryMapper.CuratedMergeRow> gameMerges = loadGameMerges();
		for (SMFGameDbWithBLOBs game : smfGameMapper.selectByExampleWithBLOBs(new SMFGameDbExample())) {
			Cancellable.check();
			String title = CmsSupport.unescape(game.getTitle());
			String norm = CmsSupport.normalizeTitle(title);
			Integer ownerId = idMap.lookupOrNull(LegacyEntityType.USER, game.getIdMember());
			SmfDownloadQueryMapper.CuratedMergeRow merge = gameMerges.get(game.getIdGame());
			if (merge != null) {
				Integer targetId = idMap.lookupOrNull(LegacyEntityType.valueOf(merge.getTargetEntityType()),
						merge.getTargetLegacyId());
				if (targetId != null) {
					idMap.record(LegacyEntityType.GAME, game.getIdGame(), targetId);
					convertGameAssets(targetId, ownerId, game);
					linkCollection(z3Id, targetId);
					continue;
				}
				logger.warn("curated merge for game {} references unmigrated {} {}", game.getIdGame(),
						merge.getTargetEntityType(), merge.getTargetLegacyId());
			}
			Integer projectId = projectByTitle.get(norm);
			if (projectId == null) {
				ProjectDbo ext = new ProjectDbo();
				ext.setStatus(CmsSupport.gameStatus(game.getStatus()));
				ext.setProgress(CmsSupport.toShort(game.getProgress()));
				ext.setLanguage(game.getLanguage());
				ext.setRequirements(game.getRequirements());
				ContentEntityDbo entity = new ContentEntityDbo();
				entity.setEntityType("PROJECT");
				entity.setTitle(title);
				entity.setSummary(CmsSupport.unescape(game.getBody()));
				entity.setCreatedUserId(ownerId);
				entity.setViewCount(game.getViews());
				entity.setDownloadCount(game.getDownloads());
				entity.setPublishedTs(CmsSupport.epoch(game.getPosttime()));
				entity.setRating(game.getRating());
				entity.setVoteCount(game.getVotecount());
				entity.setAuthorName(resolvedAuthor(idMap.lookupOrNull(LegacyEntityType.GAME, game.getIdGame()),
						displayName(ownerId)));
				entity.setPreviewContentResourceId(gamePreview(game.getIdPreview()));
				entity.setMigrationHash(MigrationHasher.hash("game" + game.getIdGame() + title + ext.getStatus()
						+ ext.getProgress() + entity.getPreviewContentResourceId()
						+ entity.getPublishedTs() + entity.getRating() + entity.getVoteCount()
						+ entity.getAuthorName()));
				projectId = upsertProject(LegacyEntityType.GAME, game.getIdGame(), entity, ext, usedSlugs);
				ensureEntityPage(entity, wikiAdopted.contains("GAME:" + game.getIdGame()));
				projectByTitle.put(norm, projectId);
			} else {
				idMap.record(LegacyEntityType.GAME, game.getIdGame(), projectId);
			}
			convertGameAssets(projectId, ownerId, game);
			linkCollection(z3Id, projectId);
		}

		convertTeams();
		convertTags();
		convertNews();
		convertCuratedCollections();
		convertPotms();
		backfillMissingPreviews();
		logger.info("Finished converting projects ({} unique)", projectByTitle.size());
		return null;
	}

	private String displayName(Integer zfgbbUserId) {
		return CmsSupport.displayName(userMapper, zfgbbUserId);
	}

	private String resolvedAuthor(Integer projectId, String defaultValue) {
		if (projectId == null) {
			return defaultValue;
		}
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		ex.createCriteria().andEntityTypeEqualTo("PROJECT").andEntityIdEqualTo(projectId)
				.andFieldNameEqualTo("author_name").andStatusEqualTo("RESOLVED");
		return conflictMapper.selectByExample(ex).stream().findFirst()
				.map(MigrationConflictDbo::getResolvedValue)
				.orElse(defaultValue);
	}

	private void backfillMissingPreviews() {
		ContentEntityDboExample entityEx = new ContentEntityDboExample();
		entityEx.createCriteria().andEntityTypeEqualTo("PROJECT").andPreviewContentResourceIdIsNull();
		for (ContentEntityDbo entity : contentEntityMapper.selectByExample(entityEx)) {
			ProjectScreenshotDboExample ex = new ProjectScreenshotDboExample();
			ex.createCriteria().andContentEntityIdEqualTo(entity.getContentEntityId());
			ex.setOrderByClause("ordinal asc, project_screenshot_id asc");
			screenshotMapper.selectByExample(ex).stream().findFirst().ifPresent(shot -> {
				entity.setPreviewContentResourceId(shot.getContentResourceId());
				contentEntityMapper.updateByPrimaryKey(entity);
			});
		}
	}

	private void convertTeams() {
		Map<Integer, Integer> teamIds = new HashMap<>();
		for (SmfDownloadQueryMapper.LegacyTeamRow row : gameDownloadMapper.selectCiTeams()) {
			String hash = MigrationHasher.hash("citeam" + row.getId() + row.getTitle() + row.getDescription());
			TeamDboExample ex = new TeamDboExample();
			ex.createCriteria().andMigrationHashEqualTo(hash);
			TeamDbo existing = teamMapper.selectByExample(ex).stream().findFirst().orElse(null);
			if (existing != null) {
				teamIds.put(row.getId(), existing.getTeamId());
				continue;
			}
			TeamDbo team = new TeamDbo();
			team.setName(CmsSupport.unescape(row.getTitle()));
			team.setDescription(CmsSupport.unescape(row.getDescription()));
			team.setCreatedUserId(idMap.lookupOrNull(LegacyEntityType.USER, row.getMemberId()));
			team.setMigrationHash(hash);
			teamMapper.insert(team);
			teamIds.put(row.getId(), team.getTeamId());
		}
		for (SmfDownloadQueryMapper.LegacyPairRow row : gameDownloadMapper.selectCiTeamMembers()) {
			Integer teamId = teamIds.get(row.getLeftId());
			Integer userId = idMap.lookupOrNull(LegacyEntityType.USER, row.getRightId());
			if (teamId == null || userId == null) {
				continue;
			}
			TeamMemberDboExample ex = new TeamMemberDboExample();
			ex.createCriteria().andTeamIdEqualTo(teamId).andUserIdEqualTo(userId);
			if (teamMemberMapper.selectByExample(ex).isEmpty()) {
				TeamMemberDbo member = new TeamMemberDbo();
				member.setTeamId(teamId);
				member.setUserId(userId);
				teamMemberMapper.insert(member);
			}
		}
	}

	private void convertTags() {
		Map<Integer, Integer> tagIds = new HashMap<>();
		for (SmfDownloadQueryMapper.LegacyTagRow row : gameDownloadMapper.selectCiTags()) {
			String name = CmsSupport.unescape(row.getName());
			if (name == null || name.isBlank()) {
				continue;
			}
			TagDboExample ex = new TagDboExample();
			ex.createCriteria().andNameEqualTo(name);
			TagDbo existing = tagMapper.selectByExample(ex).stream().findFirst().orElse(null);
			if (existing == null) {
				TagDbo tag = new TagDbo();
				tag.setName(name);
				tag.setMigrationHash(MigrationHasher.hash("citag" + row.getId() + name));
				tagMapper.insert(tag);
				existing = tag;
			}
			tagIds.put(row.getId(), existing.getTagId());
		}
		for (SmfDownloadQueryMapper.LegacyPairRow row : gameDownloadMapper.selectCiProjectTags()) {
			Integer projectId = idMap.lookupOrNull(LegacyEntityType.PROJECT, row.getLeftId());
			Integer tagId = tagIds.get(row.getRightId());
			if (projectId == null || tagId == null) {
				continue;
			}
			ProjectTagDboExample ex = new ProjectTagDboExample();
			ex.createCriteria().andContentEntityIdEqualTo(projectId).andTagIdEqualTo(tagId);
			if (projectTagMapper.selectByExample(ex).isEmpty()) {
				ProjectTagDbo link = new ProjectTagDbo();
				link.setContentEntityId(projectId);
				link.setTagId(tagId);
				projectTagMapper.insert(link);
			}
		}
	}

	private void convertNews() {
		for (SmfDownloadQueryMapper.LegacyPairRow row : gameDownloadMapper.selectCiProjectNews()) {
			Integer projectId = idMap.lookupOrNull(LegacyEntityType.PROJECT, row.getLeftId());
			Integer threadId = idMap.lookupOrNull(LegacyEntityType.THREAD, row.getRightId());
			if (projectId == null || threadId == null) {
				continue;
			}
			upsertNews(MigrationHasher.hash("cinews" + row.getLeftId() + "t" + row.getRightId()), news -> {
				news.setContentEntityId(projectId);
				news.setThreadId(threadId);
			});
		}
		for (SmfDownloadQueryMapper.LegacyNewsRow row : gameDownloadMapper.selectGameNews()) {
			Integer projectId = idMap.lookupOrNull(LegacyEntityType.GAME, row.getGameId());
			if (projectId == null) {
				continue;
			}
			Integer authorId = idMap.lookupOrNull(LegacyEntityType.USER, row.getMemberId());
			upsertNews(MigrationHasher.hash("gnews" + row.getId()), news -> {
				news.setContentEntityId(projectId);
				String subject = CmsSupport.unescape(row.getSubject());
				news.setSubject(subject != null && subject.length() > 255 ? subject.substring(0, 255) : subject);
				news.setBody(CmsSupport.unescape(row.getBody()));
				news.setAuthorUserId(authorId);
				news.setAuthorName(displayName(authorId));
				news.setPublishedTs(CmsSupport.epoch(row.getPostTime()));
			});
		}
	}

	private void upsertNews(String hash, Consumer<ProjectNewsDbo> filler) {
		ProjectNewsDboExample ex = new ProjectNewsDboExample();
		ex.createCriteria().andMigrationHashEqualTo(hash);
		if (!newsMapper.selectByExample(ex).isEmpty()) {
			return;
		}
		ProjectNewsDbo news = new ProjectNewsDbo();
		filler.accept(news);
		news.setMigrationHash(hash);
		newsMapper.insert(news);
	}

	private Map<Integer, SmfDownloadQueryMapper.CuratedMergeRow> loadGameMerges() {
		Map<Integer, SmfDownloadQueryMapper.CuratedMergeRow> merges = new HashMap<>();
		if (gameDownloadMapper.curatedMergeTableExists() == 0) {
			return merges;
		}
		for (SmfDownloadQueryMapper.CuratedMergeRow row : gameDownloadMapper.selectCuratedMerges()) {
			if ("GAME".equals(row.getSourceEntityType())) {
				merges.put(row.getSourceLegacyId(), row);
			}
		}
		return merges;
	}

	private void convertPotms() {
		List<CiPotmDb> potms = potmMapper.selectByExample(new CiPotmDbExample());
		if (potms.isEmpty()) {
			return;
		}
		potms.sort(Comparator.comparing(CiPotmDb::getTime, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
		Integer collectionId = ensureCollection("potm", "Project of the Month", "FEATURE");
		int ordinal = 1;
		for (CiPotmDb potm : potms) {
			Integer projectId = idMap.lookupOrNull(LegacyEntityType.PROJECT, potm.getProjectId());
			if (projectId == null) {
				logger.warn("potm {} references unmigrated project {}", potm.getId(), potm.getProjectId());
				continue;
			}
			upsertPotmItem(collectionId, projectId, ordinal++, CmsSupport.epoch(potm.getTime()));
		}
	}

	private void upsertPotmItem(Integer collectionId, Integer projectId, int ordinal, OffsetDateTime awardedTs) {
		ContentCollectionItemDboExample ex = new ContentCollectionItemDboExample();
		ex.createCriteria().andContentCollectionIdEqualTo(collectionId).andContentEntityIdEqualTo(projectId);
		ContentCollectionItemDbo existing = collectionItemMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing != null) {
			if (!Objects.equals(existing.getOrdinal(), ordinal) || !Objects.equals(existing.getAwardedTs(), awardedTs)) {
				existing.setOrdinal(ordinal);
				existing.setAwardedTs(awardedTs);
				collectionItemMapper.updateByPrimaryKey(existing);
			}
			return;
		}
		ContentCollectionItemDbo item = new ContentCollectionItemDbo();
		item.setContentCollectionId(collectionId);
		item.setContentEntityId(projectId);
		item.setOrdinal(ordinal);
		item.setAwardedTs(awardedTs);
		item.setMigrationHash(MigrationHasher.hash("potmitem" + collectionId + "p" + projectId));
		collectionItemMapper.insert(item);
	}

	private void convertCuratedCollections() {
		if (gameDownloadMapper.curatedCollectionTableExists() == 0) {
			logger.info("No curated_collection table in source; skipping curated collections");
			return;
		}
		for (SmfDownloadQueryMapper.CuratedCollectionRow row : gameDownloadMapper.selectCuratedCollections()) {
			Integer collectionId = ensureCollection(row.getCode(), row.getTitle(), row.getKind());
			for (SmfDownloadQueryMapper.CuratedItemRow item : gameDownloadMapper.selectCuratedItems(row.getCode())) {
				Integer projectId = idMap.lookupOrNull(LegacyEntityType.valueOf(item.getEntityType()), item.getLegacyId());
				if (projectId == null) {
					logger.warn("curated collection {} references unmigrated {} {}", row.getCode(),
							item.getEntityType(), item.getLegacyId());
					continue;
				}
				linkCollection(collectionId, projectId);
			}
		}
	}

	private void initAssets() {
		CmsSupport.AssetSource source = CmsSupport.assetSource(contentMapper, "projects");
		if (source == null) {
			assets = null;
			filesRoot = null;
			logger.info("No cmsFilesSourcePath provided; skipping project asset migration");
			return;
		}
		assets = source.store();
		filesRoot = source.root();
	}

	private Integer storeAsset(String subdir, String filename, Integer ownerId, int contentTypeId) {
		if (assets == null || filename == null || filename.isBlank()) {
			return null;
		}
		Path source = CmsSupport.confinedResolve(filesRoot, subdir, filename.trim());
		return source == null ? null : assets.store(source, ownerId, contentTypeId);
	}

	private Integer gamePreview(Integer legacyDownloadId) {
		if (legacyDownloadId == null || legacyDownloadId <= 0 || assets == null) {
			return null;
		}
		String fileUrl = gameDownloadMapper.selectFileUrl(legacyDownloadId);
		if (fileUrl == null || fileUrl.isBlank()) {
			return null;
		}
		Path source = CmsSupport.confinedResolve(filesRoot, "games", fileUrl.trim());
		return source == null ? null : assets.store(source, null, CmsAssetStore.TYPE_IMAGE);
	}

	private void ensureEntityPage(ContentEntityDbo entity, boolean wikiAdopted) {
		Integer pageId = entity.getWikiPageId();
		if (pageId == null) {
			pageId = wikiPages.ensurePage("Project", entity.getTitle(),
					CmsSupport.wikiSlug("Project", entity.getSlug()));
			entity.setWikiPageId(pageId);
			contentEntityMapper.updateByPrimaryKey(entity);
		}
		wikiPages.ensureCategory(pageId, "ZFGC Projects");
		if (wikiAdopted) {
			return;
		}
		wikiPages.upsertCurrentRevision(pageId, entity.getSummary() == null ? "" : entity.getSummary(),
				entity.getPublishedTs());
	}

	private Set<String> loadWikiAdoptedEntities() {
		Set<String> adopted = new HashSet<>();
		if (gameDownloadMapper.wikiProjectLinkTableExists() > 0) {
			for (SmfDownloadQueryMapper.WikiProjectLinkRow row : gameDownloadMapper.selectWikiProjectLinks()) {
				adopted.add(row.getEntityType() + ":" + row.getLegacyId());
			}
		}
		return adopted;
	}

	private void convertGameAssets(Integer projectId, Integer ownerId, SMFGameDbWithBLOBs game) {
		if (assets == null) {
			return;
		}
		int screenshotOrdinal = 0;
		int downloadOrdinal = 0;
		for (SmfDownloadQueryMapper.DownloadRow row : gameDownloadMapper.selectByGame(game.getIdGame())) {
			if (row.getFileUrl() == null || row.getFileUrl().isBlank()) {
				continue;
			}
			boolean isDownload = row.getType() != null && row.getType() == 3;
			if (!isDownload && row.getId().equals(game.getIdPreview())) {
				continue;
			}
			Path gameAsset = CmsSupport.confinedResolve(filesRoot, "games", row.getFileUrl().trim());
			if (gameAsset == null) {
				continue;
			}
			Integer contentId = assets.store(gameAsset, ownerId,
					isDownload ? CmsAssetStore.TYPE_DOWNLOAD : CmsAssetStore.TYPE_IMAGE);
			if (contentId == null) {
				continue;
			}
			String caption = CmsSupport.unescape(row.getDescription());
			if (isDownload) {
				ProjectDownloadDbo download = new ProjectDownloadDbo();
				download.setContentEntityId(projectId);
				download.setContentResourceId(contentId);
				download.setLabel(caption);
				download.setFileSize(row.getFileSize() == null ? null : row.getFileSize().longValue());
				download.setPublishedTs(CmsSupport.epoch(row.getPostTime()));
				download.setOrdinal(downloadOrdinal++);
				download.setMigrationHash(MigrationHasher.hash("gdl" + row.getId() + contentId
						+ download.getLabel() + download.getOrdinal()));
				upsertDownload(download);
			} else {
				ProjectScreenshotDbo screenshot = new ProjectScreenshotDbo();
				screenshot.setContentEntityId(projectId);
				screenshot.setContentResourceId(contentId);
				screenshot.setCaption(caption);
				screenshot.setOrdinal(screenshotOrdinal++);
				screenshot.setMigrationHash(MigrationHasher.hash("gss" + row.getId() + contentId
						+ screenshot.getCaption() + screenshot.getOrdinal()));
				ProjectScreenshotDboExample ex = new ProjectScreenshotDboExample();
				ex.createCriteria().andMigrationHashEqualTo(screenshot.getMigrationHash());
				if (screenshotMapper.selectByExample(ex).isEmpty()) {
					screenshotMapper.insert(screenshot);
				}
			}
		}
	}

	private void convertScreenshots(Integer projectId, Integer ownerId, List<CiProjectScreenshotDb> rows) {
		if (assets == null || rows == null) {
			return;
		}
		int ordinal = 0;
		for (CiProjectScreenshotDb row : rows.stream()
				.sorted(Comparator.comparingInt(CiProjectScreenshotDb::getId)).toList()) {
			Integer contentId = storeAsset("projects/gallery", row.getFile(), ownerId, CmsAssetStore.TYPE_IMAGE);
			if (contentId == null) {
				continue;
			}
			ProjectScreenshotDbo screenshot = new ProjectScreenshotDbo();
			screenshot.setContentEntityId(projectId);
			screenshot.setContentResourceId(contentId);
			screenshot.setCaption(CmsSupport.unescape(row.getDescription()));
			screenshot.setOrdinal(ordinal++);
			screenshot.setMigrationHash(MigrationHasher.hash("pss" + row.getId() + contentId
					+ screenshot.getCaption() + screenshot.getOrdinal()));
			ProjectScreenshotDboExample ex = new ProjectScreenshotDboExample();
			ex.createCriteria().andMigrationHashEqualTo(screenshot.getMigrationHash());
			if (screenshotMapper.selectByExample(ex).isEmpty()) {
				screenshotMapper.insert(screenshot);
			}
		}
	}

	private void convertDownloads(Integer projectId, Integer ownerId, List<CiProjectDownloadDb> rows) {
		if (assets == null || rows == null) {
			return;
		}
		int ordinal = 0;
		for (CiProjectDownloadDb row : rows.stream()
				.sorted(Comparator.comparingInt(CiProjectDownloadDb::getId)).toList()) {
			Integer contentId = storeAsset("projects/downloads", row.getFile(), ownerId, CmsAssetStore.TYPE_DOWNLOAD);
			if (contentId == null) {
				continue;
			}
			ProjectDownloadDbo download = new ProjectDownloadDbo();
			download.setContentEntityId(projectId);
			download.setContentResourceId(contentId);
			download.setLabel(CmsSupport.unescape(row.getDescription()));
			download.setFileSize(row.getSize() == null ? null : row.getSize().longValue());
			download.setPublishedTs(CmsSupport.epoch(row.getTime()));
			download.setOrdinal(ordinal++);
			download.setMigrationHash(MigrationHasher.hash("pdl" + row.getId() + contentId
					+ download.getLabel() + download.getOrdinal()));
			upsertDownload(download);
		}
	}

	private void upsertDownload(ProjectDownloadDbo download) {
		ProjectDownloadDboExample ex = new ProjectDownloadDboExample();
		ex.createCriteria().andMigrationHashEqualTo(download.getMigrationHash());
		ProjectDownloadDbo existing = downloadMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			downloadMapper.insert(download);
			return;
		}
		if (existing.getPublishedTs() == null && download.getPublishedTs() != null) {
			existing.setPublishedTs(download.getPublishedTs());
			downloadMapper.updateByPrimaryKey(existing);
		}
	}

	private Integer upsertProject(LegacyEntityType type, Integer legacyId, ContentEntityDbo entity, ProjectDbo ext,
			Set<String> usedSlugs) {
		Integer existingId = idMap.lookupOrNull(type, legacyId);
		if (existingId == null) {
			entity.setSlug(CmsSupport.uniqueSlug(entity.getTitle(), usedSlugs));
			contentEntityMapper.insert(entity);
			ext.setContentEntityId(entity.getContentEntityId());
			projectMapper.insert(ext);
			idMap.record(type, legacyId, entity.getContentEntityId());
			return entity.getContentEntityId();
		}
		ContentEntityDbo existing = contentEntityMapper.selectByPrimaryKey(existingId);
		entity.setContentEntityId(existingId);
		ext.setContentEntityId(existingId);
		if (existing == null) {
			entity.setSlug(CmsSupport.uniqueSlug(entity.getTitle(), usedSlugs));
			contentEntityMapper.insert(entity);
			ext.setContentEntityId(entity.getContentEntityId());
			projectMapper.insert(ext);
			idMap.record(type, legacyId, entity.getContentEntityId());
			return entity.getContentEntityId();
		} else {
			usedSlugs.add(existing.getSlug());
			entity.setSlug(existing.getSlug());
			entity.setWikiPageId(existing.getWikiPageId());
			if (entity.getThreadId() == null) {
				entity.setThreadId(existing.getThreadId());
			}
			if (entity.getPreviewContentResourceId() == null) {
				entity.setPreviewContentResourceId(existing.getPreviewContentResourceId());
			}
			if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), entity.getMigrationHash())) {
				contentEntityMapper.updateByPrimaryKey(entity);
				projectMapper.updateByPrimaryKey(ext);
			}
		}
		return existingId;
	}

	private Integer ensureCollection(String code, String title, String kind) {
		ContentCollectionDboExample ex = new ContentCollectionDboExample();
		ex.createCriteria().andCodeEqualTo(code);
		ContentCollectionDbo existing = collectionMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing != null) {
			return existing.getContentCollectionId();
		}
		ContentCollectionDbo collection = new ContentCollectionDbo();
		collection.setCode(code);
		collection.setTitle(title);
		collection.setKind(kind);
		collection.setMigrationHash(MigrationHasher.hash("collection" + code));
		collectionMapper.insert(collection);
		return collection.getContentCollectionId();
	}

	private void linkCollection(Integer collectionId, Integer projectId) {
		ContentCollectionItemDboExample ex = new ContentCollectionItemDboExample();
		ex.createCriteria().andContentCollectionIdEqualTo(collectionId).andContentEntityIdEqualTo(projectId);
		if (!collectionItemMapper.selectByExample(ex).isEmpty()) {
			return;
		}
		ContentCollectionItemDbo item = new ContentCollectionItemDbo();
		item.setContentCollectionId(collectionId);
		item.setContentEntityId(projectId);
		item.setOrdinal(0);
		item.setMigrationHash(MigrationHasher.hash("collitem" + collectionId + "p" + projectId));
		collectionItemMapper.insert(item);
	}
}
