package com.zfgc.zfgbb.services.cms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ProjectDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentCollectionItemDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapMapper;
import com.zfgc.zfgbb.mappers.ProjectDboMapper;
import com.zfgc.zfgbb.mappers.ProjectDownloadDboMapper;
import com.zfgc.zfgbb.mappers.custom.ProjectMergeMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Service
@Transactional
@UnfilteredBoardRead("Admin-only token matching scans all thread names to reconcile CMS content links")
public class CmsAdminService extends AbstractService {

	private static final int MIN_CONFIDENCE = 75;
	private static final int DEDUP_MIN_CONFIDENCE = 60;
	private static final int MAX_PER_SOURCE = 3;
	private static final Set<String> ARTICLE_NAMESPACES = Set.of("MAIN", "KOT");

	public record MergeCandidate(String sourceType, Integer sourceId, String sourceTitle, String sourceSlug,
			String targetType, Integer targetId, String targetTitle, String targetSlug,
			int confidence, String reason) {}

	public record MergeApplyRequest(String sourceType, Integer sourceId, String targetType, Integer targetId) {}

	public record CmsConfig(String discussionBoardId) {}

	@Autowired
	private ProjectDboMapper projectMapper;

	@Autowired
	private ContentEntityDboMapper contentEntityMapper;

	@Autowired
	private WikiPageDboMapper wikiPageMapper;

	@Autowired
	private WikiPageRevisionDboMapper wikiRevisionMapper;

	@Autowired
	private WikiPageCategoryDboMapper wikiCategoryMapper;

	@Autowired
	private ThreadDboMapper threadMapper;

	@Autowired
	private ProjectScreenshotDboMapper screenshotMapper;

	@Autowired
	private ProjectDownloadDboMapper downloadMapper;

	@Autowired
	private ContentCollectionItemDboMapper collectionItemMapper;

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private ProjectMergeMapper projectMergeMapper;

	@Autowired
	private MigratorIdMapMapper migratorIdMapMapper;

	@Autowired
	private ReactionDboMapper reactionMapper;

	@Autowired
	private ProjectNewsDboMapper projectNewsMapper;

	@Transactional(readOnly = true)
	public List<MergeCandidate> getMergeCandidates() {
		ContentEntityDboExample projectEx = new ContentEntityDboExample();
		projectEx.createCriteria().andEntityTypeEqualTo("PROJECT");
		List<ContentEntityDbo> projects = contentEntityMapper.selectByExample(projectEx);
		ContentEntityDboExample resourceEx = new ContentEntityDboExample();
		resourceEx.createCriteria().andEntityTypeEqualTo("RESOURCE");
		List<ContentEntityDbo> resources = contentEntityMapper.selectByExample(resourceEx);

		WikiPageDboExample wikiEx = new WikiPageDboExample();
		wikiEx.createCriteria().andNamespaceIn(new ArrayList<>(ARTICLE_NAMESPACES)).andRedirectToIsNull();
		List<WikiPageDbo> articles = wikiPageMapper.selectByExample(wikiEx);

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : projects) {
			candidates.addAll(wikiCandidates("PROJECT", project.getContentEntityId(), project.getTitle(),
					project.getSlug(), project.getWikiPageId(), articles));
		}
		for (ContentEntityDbo resource : resources) {
			candidates.addAll(wikiCandidates("RESOURCE", resource.getContentEntityId(), resource.getTitle(),
					resource.getSlug(), resource.getWikiPageId(), articles));
		}

		for (int i = 0; i < projects.size(); i++) {
			for (int j = i + 1; j < projects.size(); j++) {
				ContentEntityDbo projectA = projects.get(i);
				ContentEntityDbo projectB = projects.get(j);
				int confidence = (int) Math.round(titleJaccard(projectA.getTitle(), projectB.getTitle()) * 100);
				if (confidence >= DEDUP_MIN_CONFIDENCE) {
					candidates.add(new MergeCandidate("PROJECT", projectB.getContentEntityId(), projectB.getTitle(), projectB.getSlug(),
							"PROJECT", projectA.getContentEntityId(), projectA.getTitle(), projectA.getSlug(),
							confidence, "duplicate title"));
				}
			}
		}

		candidates.addAll(threadCandidates(projects));
		candidates.sort(Comparator.comparingInt(MergeCandidate::confidence).reversed());
		return candidates;
	}

	private List<MergeCandidate> wikiCandidates(String sourceType, Integer sourceId, String title,
			String slug, Integer linkedWikiPageId, List<WikiPageDbo> articles) {
		return articles.stream()
				.filter(article -> !article.getWikiPageId().equals(linkedWikiPageId))
				.map(article -> Map.entry(article, titleScore(title, article.getTitle())))
				.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
				.sorted(Map.Entry.<WikiPageDbo, Double>comparingByValue().reversed())
				.limit(MAX_PER_SOURCE)
				.map(entry -> new MergeCandidate(sourceType, sourceId, title, slug,
						"WIKI_PAGE", entry.getKey().getWikiPageId(), entry.getKey().getTitle(), entry.getKey().getSlug(),
						(int) Math.round(entry.getValue() * 100), "title match"))
				.toList();
	}

	private List<MergeCandidate> threadCandidates(List<ContentEntityDbo> projects) {
		List<ContentEntityDbo> unlinked = projects.stream().filter(project -> project.getThreadId() == null).toList();
		if (unlinked.isEmpty()) {
			return List.of();
		}
		List<ThreadDbo> threads = threadMapper.selectByExample(new ThreadDboExample());
		Map<ThreadDbo, Set<String>> threadTokens = new HashMap<>();
		threads.forEach(thread -> threadTokens.put(thread, tokens(thread.getThreadName())));

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : unlinked) {
			Set<String> projectTokens = tokens(project.getTitle());
			threads.stream()
					.map(thread -> Map.entry(thread, containment(projectTokens, threadTokens.get(thread),
							CmsSupport.normalizeTitle(project.getTitle()), CmsSupport.normalizeTitle(thread.getThreadName()))))
					.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
					.sorted(Map.Entry.<ThreadDbo, Double>comparingByValue().reversed())
					.limit(MAX_PER_SOURCE)
					.forEach(entry -> candidates.add(new MergeCandidate(
							"PROJECT", project.getContentEntityId(), project.getTitle(), project.getSlug(),
							"THREAD", entry.getKey().getThreadId(), entry.getKey().getThreadName(), null,
							(int) Math.round(entry.getValue() * 100), "thread title match")));
		}
		return candidates;
	}

	public void apply(MergeApplyRequest request) {
		String action = request.sourceType() + ">" + request.targetType();
		switch (action) {
			case "PROJECT>WIKI_PAGE", "RESOURCE>WIKI_PAGE" -> linkEntityWiki(request.sourceId(), request.targetId());
			case "PROJECT>PROJECT" -> mergeProjects(request.sourceId(), request.targetId());
			case "PROJECT>THREAD", "RESOURCE>THREAD" -> linkEntityThread(request.sourceId(), request.targetId());
			default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported merge: " + action);
		}
	}

	public CmsConfig getConfig() {
		return new CmsConfig(systemConfigService.get(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID));
	}

	public CmsConfig setConfig(CmsConfig config) {
		systemConfigService.set(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID, config.discussionBoardId());
		return getConfig();
	}

	private void linkEntityWiki(Integer entityId, Integer wikiPageId) {
		ContentEntityDbo entity = require(contentEntityMapper.selectByPrimaryKey(entityId));
		if (entity.getWikiPageId() == null) {
			entity.setWikiPageId(wikiPageId);
			contentEntityMapper.updateByPrimaryKey(entity);
			return;
		}
		String lead = adoptArticle(entity.getWikiPageId(), wikiPageId);
		if ((entity.getSummary() == null || entity.getSummary().isBlank()) && lead != null && !lead.isBlank()) {
			entity.setSummary(lead);
			contentEntityMapper.updateByPrimaryKey(entity);
		}
	}

	private String adoptArticle(Integer entityPageId, Integer articlePageId) {
		if (entityPageId.equals(articlePageId)) {
			return null;
		}
		WikiPageDbo entityPage = require(wikiPageMapper.selectByPrimaryKey(entityPageId));
		WikiPageDbo article = require(wikiPageMapper.selectByPrimaryKey(articlePageId));

		WikiPageRevisionDboExample entityRevEx = new WikiPageRevisionDboExample();
		entityRevEx.createCriteria().andWikiPageIdEqualTo(entityPageId);
		List<WikiPageRevisionDbo> entityRevisions = wikiRevisionMapper.selectByExample(entityRevEx);
		if (entityRevisions.size() == 1 && entityRevisions.get(0).getMigrationHash() != null) {
			wikiRevisionMapper.deleteByPrimaryKey(entityRevisions.get(0).getWikiPageRevisionId());
		} else {
			for (WikiPageRevisionDbo rev : entityRevisions) {
				if (Boolean.TRUE.equals(rev.getCurrentFlag())) {
					rev.setCurrentFlag(false);
					wikiRevisionMapper.updateByPrimaryKey(rev);
				}
			}
		}
		WikiPageRevisionDboExample moveEx = new WikiPageRevisionDboExample();
		moveEx.createCriteria().andWikiPageIdEqualTo(articlePageId);
		String lead = null;
		for (WikiPageRevisionDbo rev : wikiRevisionMapper.selectByExample(moveEx)) {
			rev.setWikiPageId(entityPageId);
			if (Boolean.TRUE.equals(rev.getCurrentFlag())) {
				lead = CmsSupport.leadSummary(rev.getContent());
			}
			wikiRevisionMapper.updateByPrimaryKey(rev);
		}

		WikiPageCategoryDboExample entityCatEx = new WikiPageCategoryDboExample();
		entityCatEx.createCriteria().andWikiPageIdEqualTo(entityPageId);
		Set<String> entityCategories = new HashSet<>();
		wikiCategoryMapper.selectByExample(entityCatEx)
				.forEach(cat -> entityCategories.add(cat.getCategoryName()));
		WikiPageCategoryDboExample articleCatEx = new WikiPageCategoryDboExample();
		articleCatEx.createCriteria().andWikiPageIdEqualTo(articlePageId);
		for (WikiPageCategoryDbo cat : wikiCategoryMapper.selectByExample(articleCatEx)) {
			if (entityCategories.add(cat.getCategoryName())) {
				WikiPageCategoryDbo moved = new WikiPageCategoryDbo();
				moved.setWikiPageId(entityPageId);
				moved.setCategoryName(cat.getCategoryName());
				wikiCategoryMapper.insert(moved);
			}
		}
		wikiCategoryMapper.deleteByExample(articleCatEx);

		article.setRedirectTo(entityPage.getSlug());
		wikiPageMapper.updateByPrimaryKey(article);
		return lead;
	}

	private void linkEntityThread(Integer entityId, Integer threadId) {
		ContentEntityDbo entity = require(contentEntityMapper.selectByPrimaryKey(entityId));
		entity.setThreadId(threadId);
		contentEntityMapper.updateByPrimaryKey(entity);
	}

	private void mergeProjects(Integer sourceId, Integer targetId) {
		ContentEntityDbo source = require(contentEntityMapper.selectByPrimaryKey(sourceId));
		ContentEntityDbo target = require(contentEntityMapper.selectByPrimaryKey(targetId));
		ProjectDbo sourceExt = projectMapper.selectByPrimaryKey(sourceId);
		ProjectDbo targetExt = projectMapper.selectByPrimaryKey(targetId);

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
		ContentCollectionItemDboExample itemEx = new ContentCollectionItemDboExample();
		itemEx.createCriteria().andContentEntityIdEqualTo(sourceId);
		for (ContentCollectionItemDbo item : collectionItemMapper.selectByExample(itemEx)) {
			ContentCollectionItemDboExample dupEx = new ContentCollectionItemDboExample();
			dupEx.createCriteria().andContentCollectionIdEqualTo(item.getContentCollectionId())
					.andContentEntityIdEqualTo(targetId);
			if (collectionItemMapper.selectByExample(dupEx).isEmpty()) {
				item.setContentEntityId(targetId);
				collectionItemMapper.updateByPrimaryKey(item);
			} else {
				collectionItemMapper.deleteByPrimaryKey(item.getContentCollectionItemId());
			}
		}

		projectMergeMapper.deleteDuplicateProjectReactions(sourceId, targetId);
		ReactionDbo reactionRepoint = new ReactionDbo();
		reactionRepoint.setReactableId(targetId);
		ReactionDboExample reactionRepointExample = new ReactionDboExample();
		reactionRepointExample.createCriteria().andReactableTypeEqualTo("PROJECT").andReactableIdEqualTo(sourceId);
		reactionMapper.updateByExampleSelective(reactionRepoint, reactionRepointExample);

		if (target.getWikiPageId() == null) {
			target.setWikiPageId(source.getWikiPageId());
		}
		if (target.getThreadId() == null) {
			target.setThreadId(source.getThreadId());
		}
		if (target.getPreviewContentResourceId() == null) {
			target.setPreviewContentResourceId(source.getPreviewContentResourceId());
		}
		if (target.getSummary() == null || target.getSummary().isBlank()) {
			target.setSummary(source.getSummary());
		}
		contentEntityMapper.updateByPrimaryKey(target);
		if (targetExt != null && sourceExt != null) {
			boolean extChanged = false;
			if (targetExt.getLanguage() == null) {
				targetExt.setLanguage(sourceExt.getLanguage());
				extChanged = true;
			}
			if (targetExt.getRequirements() == null) {
				targetExt.setRequirements(sourceExt.getRequirements());
				extChanged = true;
			}
			if (extChanged) {
				projectMapper.updateByPrimaryKey(targetExt);
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
		if (source.getWikiPageId() != null && !source.getWikiPageId().equals(target.getWikiPageId())) {
			adoptArticle(target.getWikiPageId(), source.getWikiPageId());
		}
	}

	private static <T> T require(T value) {
		if (value == null) {
			throw new ZfgcNotFoundException();
		}
		return value;
	}

	private static Set<String> tokens(String title) {
		Set<String> out = new HashSet<>();
		for (String token : CmsSupport.normalizeTitle(title).split("[^a-z0-9]+")) {
			if (token.length() >= 2) {
				out.add(token);
			}
		}
		return out;
	}

	private static double titleScore(String titleA, String titleB) {
		return containment(tokens(titleA), tokens(titleB), CmsSupport.normalizeTitle(titleA), CmsSupport.normalizeTitle(titleB));
	}

	private static double containment(Set<String> tokensA, Set<String> tokensB, String normalizedA, String normalizedB) {
		if (normalizedA.isEmpty() || normalizedB.isEmpty()) {
			return 0;
		}
		if (normalizedA.equals(normalizedB)) {
			return 1.0;
		}
		Set<String> shorter = tokensA.size() <= tokensB.size() ? tokensA : tokensB;
		Set<String> longer = shorter == tokensA ? tokensB : tokensA;
		if (shorter.size() < 2) {
			return 0;
		}
		long shared = shorter.stream().filter(longer::contains).count();
		return (double) shared / shorter.size();
	}

	private static double titleJaccard(String titleA, String titleB) {
		String normalizedA = CmsSupport.normalizeTitle(titleA);
		String normalizedB = CmsSupport.normalizeTitle(titleB);
		if (normalizedA.isEmpty() || normalizedB.isEmpty()) {
			return 0;
		}
		if (normalizedA.equals(normalizedB)) {
			return 1.0;
		}
		Set<String> tokensA = tokens(titleA);
		Set<String> tokensB = tokens(titleB);
		if (tokensA.isEmpty() || tokensB.isEmpty()) {
			return 0;
		}
		Set<String> union = new HashSet<>(tokensA);
		union.addAll(tokensB);
		long shared = tokensA.stream().filter(tokensB::contains).count();
		return (double) shared / union.size();
	}
}
