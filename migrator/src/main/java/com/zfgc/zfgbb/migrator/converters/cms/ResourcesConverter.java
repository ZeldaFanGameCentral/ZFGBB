package com.zfgc.zfgbb.migrator.converters.cms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ResourceDbo;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.ResourceDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.migrator.ci.dbo.CiResourceDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiResourceDbExample;
import com.zfgc.zfgbb.migrator.ci.mappers.CiResourceDbMapper;
import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.Cancellable;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceMainDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceMainDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFResourceMainDbMapper;
import com.zfgc.zfgbb.migrator.smf.queries.SmfDownloadQueryMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResourcesConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(ResourcesConverter.class);

	private final CiResourceDbMapper ciResourceMapper;
	private final SMFResourceMainDbMapper smfResourceMapper;
	private final SmfDownloadQueryMapper downloadQuery;
	private final ResourceDboMapper resourceMapper;
	private final ContentEntityDboMapper contentEntityMapper;
	private final ContentResourceDboMapper contentMapper;
	private final UserDboMapper userMapper;
	private final WikiPageStore wikiPages;
	private final MigratorIdMapService idMap;

	private CmsAssetStore assets;
	private Path filesRoot;

	@Override
	public JobType getType() {
		return JobType.RESOURCES;
	}

	@Override
	@Transactional
	public Void convertToZfgbb() {
		initAssets();
		Set<String> usedSlugs = new HashSet<>();
		contentEntityMapper.selectByExample(new ContentEntityDboExample()).forEach(e -> usedSlugs.add(e.getSlug()));
		Map<String, Integer> resourceByTitle = new HashMap<>();

		for (CiResourceDb ciResource : ciResourceMapper.selectByExampleWithBLOBs(new CiResourceDbExample())) {
			Cancellable.check();
			String title = CmsSupport.unescape(ciResource.getTitle());
			Integer ownerId = idMap.lookupOrNull(LegacyEntityType.USER, ciResource.getMemberId());
			ResourceDbo ext = new ResourceDbo();
			ext.setResourceType(CmsSupport.resourceType(ciResource.getType()));
			ext.setFileSize(ciResource.getSize() == null ? null : ciResource.getSize().longValue());
			ext.setDownloadContentResourceId(storeAsset(ciResource.getFile(), ownerId, CmsAssetStore.TYPE_DOWNLOAD));
			ContentEntityDbo entity = new ContentEntityDbo();
			entity.setEntityType("RESOURCE");
			entity.setTitle(title);
			entity.setSummary(CmsSupport.unescape(ciResource.getDescription()));
			entity.setThreadId(idMap.lookupOrNull(LegacyEntityType.THREAD, ciResource.getTopicId()));
			entity.setCreatedUserId(ownerId);
			entity.setViewCount(ciResource.getViews());
			entity.setDownloadCount(ciResource.getDownloads());
			entity.setPublishedTs(CmsSupport.epoch(
					ciResource.getTimeCreated() != null && ciResource.getTimeCreated() > 0 ? ciResource.getTimeCreated() : ciResource.getLastUpdated()));
			entity.setLastUpdatedTs(CmsSupport.epoch(ciResource.getLastUpdated()));
			entity.setRating(ciResource.getRating());
			entity.setVoteCount(ciResource.getVotes());
			entity.setAuthorName(CmsSupport.unescape(ciResource.getMemberName()));
			entity.setPreviewContentResourceId(storeAsset(ciResource.getPreview(), ownerId, CmsAssetStore.TYPE_IMAGE));
			if (ext.getDownloadContentResourceId() == null
					&& entity.getPreviewContentResourceId() != null
					&& previewIsOriginalUpload(ciResource.getPreview(), ciResource.getSize())) {
				ext.setDownloadContentResourceId(entity.getPreviewContentResourceId());
			}
			entity.setMigrationHash(MigrationHasher.hash("ci" + ciResource.getId() + title + ext.getResourceType()
					+ ext.getDownloadContentResourceId() + entity.getPreviewContentResourceId()
					+ entity.getPublishedTs() + entity.getRating() + entity.getVoteCount() + entity.getAuthorName()));
			Integer resourceId = upsertResource(LegacyEntityType.RESOURCE, ciResource.getId(), entity, ext, usedSlugs);
			ensureEntityPage(entity);
			resourceByTitle.put(CmsSupport.normalizeTitle(title), resourceId);
		}

		for (SMFResourceMainDbWithBLOBs smfResource : smfResourceMapper.selectByExampleWithBLOBs(new SMFResourceMainDbExample())) {
			Cancellable.check();
			String title = CmsSupport.unescape(smfResource.getTitle());
			String norm = CmsSupport.normalizeTitle(title);
			Integer existing = idMap.lookupOrNull(LegacyEntityType.RESOURCE, smfResource.getIdResource());
			if (existing == null) {
				existing = resourceByTitle.get(norm);
			}
			if (existing != null) {
				idMap.record(LegacyEntityType.SMF_RESOURCE, smfResource.getIdResource(), existing);
				fillMissingAssets(existing, smfResource);
				improveResourceMetadata(existing, smfResource, title);
				continue;
			}
			ResourceDbo ext = new ResourceDbo();
			ext.setResourceType(CmsSupport.resourceType(smfResource.getType()));
			ext.setFileSize(smfResource.getFilesize() == null ? null : smfResource.getFilesize().longValue());
			ext.setDownloadContentResourceId(smfAsset(smfResource, true));
			ContentEntityDbo entity = new ContentEntityDbo();
			entity.setEntityType("RESOURCE");
			entity.setTitle(title);
			entity.setSummary(CmsSupport.unescape(smfResource.getBody()));
			entity.setCreatedUserId(idMap.lookupOrNull(LegacyEntityType.USER, smfResource.getIdMember()));
			entity.setViewCount(smfResource.getViews());
			entity.setDownloadCount(smfResource.getDownloads());
			entity.setPublishedTs(CmsSupport.epoch(smfResource.getPosttime()));
			entity.setRating(smfResource.getRating() == null ? null : smfResource.getRating().floatValue());
			entity.setVoteCount(smfResource.getVotecount());
			entity.setAuthorName(displayName(entity.getCreatedUserId()));
			entity.setPreviewContentResourceId(smfAsset(smfResource, false));
			entity.setMigrationHash(MigrationHasher.hash("smf" + smfResource.getIdResource() + title + ext.getResourceType()
					+ ext.getDownloadContentResourceId() + entity.getPreviewContentResourceId()
					+ entity.getPublishedTs() + entity.getRating() + entity.getVoteCount()
					+ entity.getAuthorName()));
			Integer resourceId = upsertResource(LegacyEntityType.SMF_RESOURCE, smfResource.getIdResource(), entity, ext, usedSlugs);
			ensureEntityPage(entity);
			resourceByTitle.put(norm, resourceId);
		}

		logger.info("Finished converting resources ({} unique)", resourceByTitle.size());
		return null;
	}

	private void initAssets() {
		CmsSupport.AssetSource source = CmsSupport.assetSource(contentMapper, "resources");
		if (source == null) {
			assets = null;
			filesRoot = null;
			logger.info("No cmsFilesSourcePath provided; skipping resource asset migration");
			return;
		}
		assets = source.store();
		filesRoot = source.root();
	}

	private Integer storeAsset(String filename, Integer ownerId, int contentTypeId) {
		if (assets == null || filename == null || filename.isBlank()) {
			return null;
		}
		Path source = CmsSupport.confinedResolve(filesRoot, "resources", filename.trim());
		return source == null ? null : assets.store(source, ownerId, contentTypeId);
	}

	private String displayName(Integer zfgbbUserId) {
		return CmsSupport.displayName(userMapper, zfgbbUserId);
	}

	private void improveResourceMetadata(Integer resourceId, SMFResourceMainDbWithBLOBs smfResource, String smfTitle) {
		ContentEntityDbo entity = contentEntityMapper.selectByPrimaryKey(resourceId);
		if (entity == null) {
			return;
		}
		boolean changed = false;
		if (smfTitle != null && entity.getTitle() != null && smfTitle.length() > entity.getTitle().length()) {
			entity.setTitle(smfTitle);
			changed = true;
		}
		if (entity.getAuthorName() == null || entity.getAuthorName().isBlank()) {
			String author = displayName(idMap.lookupOrNull(LegacyEntityType.USER, smfResource.getIdMember()));
			if (author != null && !author.isBlank()) {
				entity.setAuthorName(author);
				changed = true;
			}
		}
		if (changed) {
			contentEntityMapper.updateByPrimaryKey(entity);
		}
	}

	private void fillMissingAssets(Integer resourceId, SMFResourceMainDbWithBLOBs smfResource) {
		if (assets == null) {
			return;
		}
		ContentEntityDbo entity = contentEntityMapper.selectByPrimaryKey(resourceId);
		ResourceDbo ext = resourceMapper.selectByPrimaryKey(resourceId);
		if (entity == null || ext == null
				|| (ext.getDownloadContentResourceId() != null && entity.getPreviewContentResourceId() != null)) {
			return;
		}
		boolean extChanged = false;
		boolean entityChanged = false;
		if (ext.getDownloadContentResourceId() == null) {
			Integer contentId = smfAsset(smfResource, true);
			if (contentId != null) {
				ext.setDownloadContentResourceId(contentId);
				extChanged = true;
			}
		}
		if (entity.getPreviewContentResourceId() == null) {
			Integer contentId = smfAsset(smfResource, false);
			if (contentId != null) {
				entity.setPreviewContentResourceId(contentId);
				entityChanged = true;
			}
		}
		if (extChanged) {
			resourceMapper.updateByPrimaryKey(ext);
		}
		if (entityChanged) {
			contentEntityMapper.updateByPrimaryKey(entity);
		}
	}

	private Integer smfAsset(SMFResourceMainDbWithBLOBs smfResource, boolean download) {
		if (assets == null) {
			return null;
		}
		Integer wantedSize = download ? smfResource.getFilesize() : null;
		return downloadQuery.selectByResource(smfResource.getIdResource()).stream()
				.filter(row -> row.getFileUrl() != null && !row.getFileUrl().isBlank())
				.filter(row -> download == (row.getType() != null && row.getType() == 3))
				.sorted(Comparator
						.comparingInt((SmfDownloadQueryMapper.DownloadRow row) -> download
								? (wantedSize != null && wantedSize > 0 && wantedSize.equals(row.getFileSize()) ? 0 : 1)
								: (Objects.equals(row.getId(), smfResource.getIdPreview()) ? 0 : 1))
						.thenComparing(row -> -(row.getFileSize() == null ? 0 : row.getFileSize())))
				.map(row -> {
					Path source = CmsSupport.confinedResolve(filesRoot, "resources", row.getFileUrl().trim());
					return source == null ? null : assets.store(source,
							idMap.lookupOrNull(LegacyEntityType.USER, smfResource.getIdMember()),
							download ? CmsAssetStore.TYPE_DOWNLOAD : CmsAssetStore.TYPE_IMAGE);
				})
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
	}

	private boolean previewIsOriginalUpload(String preview, Integer expectedSize) {
		if (filesRoot == null || preview == null || preview.isBlank() || expectedSize == null || expectedSize <= 0) {
			return false;
		}
		Path source = CmsSupport.confinedResolve(filesRoot, "resources", preview.trim());
		if (source == null) {
			return false;
		}
		try {
			return Files.size(source) == expectedSize.longValue();
		} catch (IOException e) {
			return false;
		}
	}

	private void ensureEntityPage(ContentEntityDbo entity) {
		Integer pageId = entity.getWikiPageId();
		if (pageId == null) {
			pageId = wikiPages.ensurePage("Resource", entity.getTitle(),
					CmsSupport.wikiSlug("Resource", entity.getSlug()));
			entity.setWikiPageId(pageId);
			contentEntityMapper.updateByPrimaryKey(entity);
		}
		wikiPages.upsertCurrentRevision(pageId, entity.getSummary() == null ? "" : entity.getSummary(),
				entity.getPublishedTs());
	}

	private Integer upsertResource(LegacyEntityType type, Integer legacyId, ContentEntityDbo entity, ResourceDbo ext,
			Set<String> usedSlugs) {
		Integer existingId = idMap.lookupOrNull(type, legacyId);
		if (existingId == null) {
			entity.setSlug(CmsSupport.uniqueSlug(entity.getTitle(), usedSlugs));
			contentEntityMapper.insert(entity);
			ext.setContentEntityId(entity.getContentEntityId());
			resourceMapper.insert(ext);
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
			resourceMapper.insert(ext);
			idMap.record(type, legacyId, entity.getContentEntityId());
			return entity.getContentEntityId();
		} else {
			usedSlugs.add(existing.getSlug());
			entity.setSlug(existing.getSlug());
			entity.setWikiPageId(existing.getWikiPageId());
			if (entity.getThreadId() == null) {
				entity.setThreadId(existing.getThreadId());
			}
			if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), entity.getMigrationHash())) {
				contentEntityMapper.updateByPrimaryKey(entity);
				resourceMapper.updateByPrimaryKey(ext);
			}
		}
		return existingId;
	}
}
