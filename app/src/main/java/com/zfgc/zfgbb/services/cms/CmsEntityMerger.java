package com.zfgc.zfgbb.services.cms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ProjectDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentCollectionItemDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapMapper;
import com.zfgc.zfgbb.mappers.ProjectDboMapper;
import com.zfgc.zfgbb.mappers.ProjectDownloadDboMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.mappers.custom.ProjectMergeMapper;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CmsEntityMerger {

	private final ProjectDboMapper projectMapper;
	private final ContentEntityDboMapper contentEntityMapper;
	private final WikiPageDboMapper wikiPageMapper;
	private final WikiPageRevisionDboMapper wikiRevisionMapper;
	private final WikiPageCategoryDboMapper wikiCategoryMapper;
	private final ProjectScreenshotDboMapper screenshotMapper;
	private final ProjectDownloadDboMapper downloadMapper;
	private final ContentCollectionItemDboMapper collectionItemMapper;
	private final ProjectMergeMapper projectMergeMapper;
	private final MigratorIdMapMapper migratorIdMapMapper;
	private final ReactionDboMapper reactionMapper;
	private final ProjectNewsDboMapper projectNewsMapper;

	public void applyMerge(CmsAdminService.MergeApplyRequest request) {
		String action = request.sourceType() + ">" + request.targetType();
		switch (action) {
			case "PROJECT>WIKI_PAGE", "RESOURCE>WIKI_PAGE" -> linkEntityWiki(request.sourceId(), request.targetId());
			case "PROJECT>PROJECT" -> mergeProjects(request.sourceId(), request.targetId());
			case "PROJECT>THREAD", "RESOURCE>THREAD" -> linkEntityThread(request.sourceId(), request.targetId());
			default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported merge: " + action);
		}
	}

	private void linkEntityWiki(Integer entityId, Integer wikiPageId) {
		ContentEntityDbo entity = require(contentEntityMapper.selectByPrimaryKey(entityId));
		if (entity.getWikiPageId() == null) {
			entity.setWikiPageId(wikiPageId);
			contentEntityMapper.updateByPrimaryKey(entity);
			return;
		}
		String leadSummaryText = adoptArticle(entity.getWikiPageId(), wikiPageId);
		if ((entity.getSummary() == null || entity.getSummary().isBlank()) && leadSummaryText != null && !leadSummaryText.isBlank()) {
			entity.setSummary(leadSummaryText);
			contentEntityMapper.updateByPrimaryKey(entity);
		}
	}

	private String adoptArticle(Integer entityPageId, Integer articlePageId) {
		if (entityPageId.equals(articlePageId)) {
			return null;
		}
		WikiPageDbo entityPage = require(wikiPageMapper.selectByPrimaryKey(entityPageId));
		WikiPageDbo articlePage = require(wikiPageMapper.selectByPrimaryKey(articlePageId));

		WikiPageRevisionDboExample entityRevisionExample = new WikiPageRevisionDboExample();
		entityRevisionExample.createCriteria().andWikiPageIdEqualTo(entityPageId);
		List<WikiPageRevisionDbo> entityRevisions = wikiRevisionMapper.selectByExample(entityRevisionExample);
		if (entityRevisions.size() == 1 && entityRevisions.get(0).getMigrationHash() != null) {
			wikiRevisionMapper.deleteByPrimaryKey(entityRevisions.get(0).getWikiPageRevisionId());
		} else {
			for (WikiPageRevisionDbo revision : entityRevisions) {
				if (Boolean.TRUE.equals(revision.getCurrentFlag())) {
					revision.setCurrentFlag(false);
					wikiRevisionMapper.updateByPrimaryKey(revision);
				}
			}
		}

		WikiPageRevisionDboExample moveRevisionExample = new WikiPageRevisionDboExample();
		moveRevisionExample.createCriteria().andWikiPageIdEqualTo(articlePageId);
		String leadSummaryText = null;
		for (WikiPageRevisionDbo revision : wikiRevisionMapper.selectByExample(moveRevisionExample)) {
			revision.setWikiPageId(entityPageId);
			if (Boolean.TRUE.equals(revision.getCurrentFlag())) {
				leadSummaryText = CmsSupport.leadSummary(revision.getContent());
			}
			wikiRevisionMapper.updateByPrimaryKey(revision);
		}

		WikiPageCategoryDboExample entityCategoryExample = new WikiPageCategoryDboExample();
		entityCategoryExample.createCriteria().andWikiPageIdEqualTo(entityPageId);
		Set<String> entityCategories = new HashSet<>();
		wikiCategoryMapper.selectByExample(entityCategoryExample)
				.forEach(category -> entityCategories.add(category.getCategoryName()));

		WikiPageCategoryDboExample articleCategoryExample = new WikiPageCategoryDboExample();
		articleCategoryExample.createCriteria().andWikiPageIdEqualTo(articlePageId);
		for (WikiPageCategoryDbo category : wikiCategoryMapper.selectByExample(articleCategoryExample)) {
			if (entityCategories.add(category.getCategoryName())) {
				WikiPageCategoryDbo movedCategory = new WikiPageCategoryDbo();
				movedCategory.setWikiPageId(entityPageId);
				movedCategory.setCategoryName(category.getCategoryName());
				wikiCategoryMapper.insert(movedCategory);
			}
		}
		wikiCategoryMapper.deleteByExample(articleCategoryExample);

		articlePage.setRedirectTo(entityPage.getSlug());
		wikiPageMapper.updateByPrimaryKey(articlePage);
		return leadSummaryText;
	}

	private void linkEntityThread(Integer entityId, Integer threadId) {
		ContentEntityDbo entity = require(contentEntityMapper.selectByPrimaryKey(entityId));
		entity.setThreadId(threadId);
		contentEntityMapper.updateByPrimaryKey(entity);
	}

	private void mergeProjects(Integer sourceId, Integer targetId) {
		ContentEntityDbo sourceEntity = require(contentEntityMapper.selectByPrimaryKey(sourceId));
		ContentEntityDbo targetEntity = require(contentEntityMapper.selectByPrimaryKey(targetId));
		ProjectDbo sourceProjectExtension = projectMapper.selectByPrimaryKey(sourceId);
		ProjectDbo targetProjectExtension = projectMapper.selectByPrimaryKey(targetId);

		ProjectScreenshotDboExample screenshotExample = new ProjectScreenshotDboExample();
		screenshotExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		for (ProjectScreenshotDbo screenshot : screenshotMapper.selectByExample(screenshotExample)) {
			screenshot.setContentEntityId(targetId);
			screenshotMapper.updateByPrimaryKey(screenshot);
		}

		ProjectDownloadDboExample downloadExample = new ProjectDownloadDboExample();
		downloadExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		for (ProjectDownloadDbo download : downloadMapper.selectByExample(downloadExample)) {
			download.setContentEntityId(targetId);
			downloadMapper.updateByPrimaryKey(download);
		}

		ContentCollectionItemDboExample collectionItemExample = new ContentCollectionItemDboExample();
		collectionItemExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		for (ContentCollectionItemDbo collectionItem : collectionItemMapper.selectByExample(collectionItemExample)) {
			ContentCollectionItemDboExample duplicateItemExample = new ContentCollectionItemDboExample();
			duplicateItemExample.createCriteria().andContentCollectionIdEqualTo(collectionItem.getContentCollectionId())
					.andContentEntityIdEqualTo(targetId);
			if (collectionItemMapper.selectByExample(duplicateItemExample).isEmpty()) {
				collectionItem.setContentEntityId(targetId);
				collectionItemMapper.updateByPrimaryKey(collectionItem);
			} else {
				collectionItemMapper.deleteByPrimaryKey(collectionItem.getContentCollectionItemId());
			}
		}

		projectMergeMapper.deleteDuplicateProjectReactions(sourceId, targetId);
		ReactionDbo reactionRepoint = new ReactionDbo();
		reactionRepoint.setReactableId(targetId);
		ReactionDboExample reactionRepointExample = new ReactionDboExample();
		reactionRepointExample.createCriteria().andReactableTypeEqualTo("PROJECT").andReactableIdEqualTo(sourceId);
		reactionMapper.updateByExampleSelective(reactionRepoint, reactionRepointExample);

		if (targetEntity.getWikiPageId() == null) {
			targetEntity.setWikiPageId(sourceEntity.getWikiPageId());
		}
		if (targetEntity.getThreadId() == null) {
			targetEntity.setThreadId(sourceEntity.getThreadId());
		}
		if (targetEntity.getPreviewContentResourceId() == null) {
			targetEntity.setPreviewContentResourceId(sourceEntity.getPreviewContentResourceId());
		}
		if (targetEntity.getSummary() == null || targetEntity.getSummary().isBlank()) {
			targetEntity.setSummary(sourceEntity.getSummary());
		}
		contentEntityMapper.updateByPrimaryKey(targetEntity);

		if (targetProjectExtension != null && sourceProjectExtension != null) {
			boolean extensionChanged = false;
			if (targetProjectExtension.getLanguage() == null) {
				targetProjectExtension.setLanguage(sourceProjectExtension.getLanguage());
				extensionChanged = true;
			}
			if (targetProjectExtension.getRequirements() == null) {
				targetProjectExtension.setRequirements(sourceProjectExtension.getRequirements());
				extensionChanged = true;
			}
			if (extensionChanged) {
				projectMapper.updateByPrimaryKey(targetProjectExtension);
			}
		}

		migratorIdMapMapper.repointMigratorIdMap(targetId, sourceId);

		projectMergeMapper.repointProjectTags(targetId, sourceId);
		ProjectNewsDbo projectNewsRepoint = new ProjectNewsDbo();
		projectNewsRepoint.setContentEntityId(targetId);
		ProjectNewsDboExample projectNewsRepointExample = new ProjectNewsDboExample();
		projectNewsRepointExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		projectNewsMapper.updateByExampleSelective(projectNewsRepoint, projectNewsRepointExample);

		contentEntityMapper.deleteByPrimaryKey(sourceId);
		if (sourceEntity.getWikiPageId() != null && !sourceEntity.getWikiPageId().equals(targetEntity.getWikiPageId())) {
			adoptArticle(targetEntity.getWikiPageId(), sourceEntity.getWikiPageId());
		}
	}

	private static <T> T require(T value) {
		if (value == null) {
			throw new ZfgcNotFoundException();
		}
		return value;
	}
}
