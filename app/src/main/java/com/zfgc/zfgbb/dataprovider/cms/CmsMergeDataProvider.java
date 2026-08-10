package com.zfgc.zfgbb.dataprovider.cms;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.cms.ContentCollectionItemDao;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.dao.cms.ProjectDao;
import com.zfgc.zfgbb.dao.cms.ProjectDownloadDao;
import com.zfgc.zfgbb.dao.cms.ProjectNewsDao;
import com.zfgc.zfgbb.dao.cms.ProjectScreenshotDao;
import com.zfgc.zfgbb.dao.cms.ProjectTagDao;
import com.zfgc.zfgbb.dao.cms.WikiPageCategoryDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.dao.cms.WikiPageRevisionDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dao.reactions.ReactionDao;
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
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CmsMergeDataProvider {

	private final ProjectDao projectDao;
	private final ContentEntityDao contentEntityDao;
	private final WikiPageDao wikiPageDao;
	private final WikiPageRevisionDao wikiPageRevisionDao;
	private final WikiPageCategoryDao wikiPageCategoryDao;
	private final ProjectScreenshotDao projectScreenshotDao;
	private final ProjectDownloadDao projectDownloadDao;
	private final ContentCollectionItemDao contentCollectionItemDao;
	private final ProjectTagDao projectTagDao;
	private final MigratorIdMapDao migratorIdMapDao;
	private final ReactionDao reactionDao;
	private final ProjectNewsDao projectNewsDao;

	public void linkEntityWiki(Integer entityId, Integer wikiPageId) {
		ContentEntityDbo entity = contentEntityDao.find(entityId).orElseThrow(ZfgcNotFoundException::new);
		if (entity.getWikiPageId() == null) {
			entity.setWikiPageId(wikiPageId);
			contentEntityDao.save(entity);
			return;
		}
		String leadSummaryText = adoptArticle(entity.getWikiPageId(), wikiPageId);
		if ((entity.getSummary() == null || entity.getSummary().isBlank()) && leadSummaryText != null && !leadSummaryText.isBlank()) {
			entity.setSummary(leadSummaryText);
			contentEntityDao.save(entity);
		}
	}

	private String adoptArticle(Integer entityPageId, Integer articlePageId) {
		if (entityPageId.equals(articlePageId)) {
			return null;
		}
		WikiPageDbo entityPage = wikiPageDao.find(entityPageId).orElseThrow(ZfgcNotFoundException::new);
		WikiPageDbo articlePage = wikiPageDao.find(articlePageId).orElseThrow(ZfgcNotFoundException::new);

		WikiPageRevisionDboExample entityRevisionExample = new WikiPageRevisionDboExample();
		entityRevisionExample.createCriteria().andWikiPageIdEqualTo(entityPageId);
		List<WikiPageRevisionDbo> entityRevisions = wikiPageRevisionDao.get(entityRevisionExample);
		if (entityRevisions.size() == 1 && entityRevisions.get(0).getMigrationHash() != null) {
			wikiPageRevisionDao.delete(entityRevisions.get(0).getWikiPageRevisionId());
		} else {
			for (WikiPageRevisionDbo revision : entityRevisions) {
				if (Boolean.TRUE.equals(revision.getCurrentFlag())) {
					revision.setCurrentFlag(false);
					wikiPageRevisionDao.save(revision);
				}
			}
		}

		WikiPageRevisionDboExample moveRevisionExample = new WikiPageRevisionDboExample();
		moveRevisionExample.createCriteria().andWikiPageIdEqualTo(articlePageId);
		String leadSummaryText = null;
		for (WikiPageRevisionDbo revision : wikiPageRevisionDao.get(moveRevisionExample)) {
			revision.setWikiPageId(entityPageId);
			if (Boolean.TRUE.equals(revision.getCurrentFlag())) {
				leadSummaryText = CmsSupport.leadSummary(revision.getContent());
			}
			wikiPageRevisionDao.save(revision);
		}

		WikiPageCategoryDboExample entityCategoryExample = new WikiPageCategoryDboExample();
		entityCategoryExample.createCriteria().andWikiPageIdEqualTo(entityPageId);
		Set<String> entityCategories = new HashSet<>();
		wikiPageCategoryDao.get(entityCategoryExample)
				.forEach(category -> entityCategories.add(category.getCategoryName()));

		WikiPageCategoryDboExample articleCategoryExample = new WikiPageCategoryDboExample();
		articleCategoryExample.createCriteria().andWikiPageIdEqualTo(articlePageId);
		for (WikiPageCategoryDbo category : wikiPageCategoryDao.get(articleCategoryExample)) {
			if (entityCategories.add(category.getCategoryName())) {
				WikiPageCategoryDbo movedCategory = new WikiPageCategoryDbo();
				movedCategory.setWikiPageId(entityPageId);
				movedCategory.setCategoryName(category.getCategoryName());
				wikiPageCategoryDao.insert(movedCategory);
			}
		}
		wikiPageCategoryDao.deleteWhere(articleCategoryExample);

		articlePage.setRedirectTo(entityPage.getSlug());
		wikiPageDao.save(articlePage);
		return leadSummaryText;
	}

	public void mergeProjects(Integer sourceId, Integer targetId) {
		ContentEntityDbo sourceEntity = contentEntityDao.find(sourceId).orElseThrow(ZfgcNotFoundException::new);
		ContentEntityDbo targetEntity = contentEntityDao.find(targetId).orElseThrow(ZfgcNotFoundException::new);
		Optional<ProjectDbo> sourceProjectExtension = projectDao.find(sourceId);
		Optional<ProjectDbo> targetProjectExtension = projectDao.find(targetId);

		ProjectScreenshotDbo screenshotRepoint = new ProjectScreenshotDbo();
		screenshotRepoint.setContentEntityId(targetId);
		ProjectScreenshotDboExample screenshotRepointExample = new ProjectScreenshotDboExample();
		screenshotRepointExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		projectScreenshotDao.updateWhere(screenshotRepoint, screenshotRepointExample);

		ProjectDownloadDbo downloadRepoint = new ProjectDownloadDbo();
		downloadRepoint.setContentEntityId(targetId);
		ProjectDownloadDboExample downloadRepointExample = new ProjectDownloadDboExample();
		downloadRepointExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		projectDownloadDao.updateWhere(downloadRepoint, downloadRepointExample);

		ContentCollectionItemDboExample collectionItemExample = new ContentCollectionItemDboExample();
		collectionItemExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		for (ContentCollectionItemDbo collectionItem : contentCollectionItemDao.get(collectionItemExample)) {
			ContentCollectionItemDboExample duplicateItemExample = new ContentCollectionItemDboExample();
			duplicateItemExample.createCriteria().andContentCollectionIdEqualTo(collectionItem.getContentCollectionId())
					.andContentEntityIdEqualTo(targetId);
			if (contentCollectionItemDao.get(duplicateItemExample).isEmpty()) {
				collectionItem.setContentEntityId(targetId);
				contentCollectionItemDao.save(collectionItem);
			} else {
				contentCollectionItemDao.delete(collectionItem.getContentCollectionItemId());
			}
		}

		reactionDao.deleteDuplicateProjectReactions(sourceId, targetId);
		ReactionDbo reactionRepoint = new ReactionDbo();
		reactionRepoint.setReactableId(targetId);
		ReactionDboExample reactionRepointExample = new ReactionDboExample();
		reactionRepointExample.createCriteria().andReactableTypeEqualTo("PROJECT").andReactableIdEqualTo(sourceId);
		reactionDao.updateWhere(reactionRepoint, reactionRepointExample);

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
		contentEntityDao.save(targetEntity);

		if (targetProjectExtension.isPresent() && sourceProjectExtension.isPresent()) {
			ProjectDbo target = targetProjectExtension.get();
			ProjectDbo source = sourceProjectExtension.get();
			boolean extensionChanged = false;
			if (target.getLanguage() == null) {
				target.setLanguage(source.getLanguage());
				extensionChanged = true;
			}
			if (target.getRequirements() == null) {
				target.setRequirements(source.getRequirements());
				extensionChanged = true;
			}
			if (extensionChanged) {
				projectDao.update(target);
			}
		}

		migratorIdMapDao.repoint(targetId, sourceId);

		projectTagDao.repoint(targetId, sourceId);
		ProjectNewsDbo projectNewsRepoint = new ProjectNewsDbo();
		projectNewsRepoint.setContentEntityId(targetId);
		ProjectNewsDboExample projectNewsRepointExample = new ProjectNewsDboExample();
		projectNewsRepointExample.createCriteria().andContentEntityIdEqualTo(sourceId);
		projectNewsDao.updateWhere(projectNewsRepoint, projectNewsRepointExample);

		contentEntityDao.delete(sourceId);
		if (sourceEntity.getWikiPageId() != null && !sourceEntity.getWikiPageId().equals(targetEntity.getWikiPageId())) {
			adoptArticle(targetEntity.getWikiPageId(), sourceEntity.getWikiPageId());
		}
	}
}
