package com.zfgc.zfgbb.services.cms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.services.system.SystemConfigService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@UnfilteredBoardRead("Admin-only token matching scans all thread names to reconcile CMS content links")
@RequiredArgsConstructor
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

	private final ContentEntityDboMapper contentEntityMapper;
	private final WikiPageDboMapper wikiPageMapper;
	private final ThreadDboMapper threadMapper;
	private final SystemConfigService systemConfigService;
	private final CmsSimilarityEngine similarityEngine;
	private final CmsEntityMerger entityMerger;

	@Transactional(readOnly = true)
	public List<MergeCandidate> getMergeCandidates() {
		ContentEntityDboExample projectExample = new ContentEntityDboExample();
		projectExample.createCriteria().andEntityTypeEqualTo("PROJECT");
		List<ContentEntityDbo> projects = contentEntityMapper.selectByExample(projectExample);
		ContentEntityDboExample resourceExample = new ContentEntityDboExample();
		resourceExample.createCriteria().andEntityTypeEqualTo("RESOURCE");
		List<ContentEntityDbo> resources = contentEntityMapper.selectByExample(resourceExample);

		WikiPageDboExample wikiExample = new WikiPageDboExample();
		wikiExample.createCriteria().andNamespaceIn(new ArrayList<>(ARTICLE_NAMESPACES)).andRedirectToIsNull();
		List<WikiPageDbo> articles = wikiPageMapper.selectByExample(wikiExample);

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : projects) {
			candidates.addAll(wikiCandidates("PROJECT", project.getContentEntityId(), project.getTitle(),
					project.getSlug(), project.getWikiPageId(), articles));
		}
		for (ContentEntityDbo resource : resources) {
			candidates.addAll(wikiCandidates("RESOURCE", resource.getContentEntityId(), resource.getTitle(),
					resource.getSlug(), resource.getWikiPageId(), articles));
		}

		for (int indexA = 0; indexA < projects.size(); indexA++) {
			for (int indexB = indexA + 1; indexB < projects.size(); indexB++) {
				ContentEntityDbo projectA = projects.get(indexA);
				ContentEntityDbo projectB = projects.get(indexB);
				int confidence = (int) Math.round(similarityEngine.calculateJaccardSimilarity(projectA.getTitle(), projectB.getTitle()) * 100);
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
				.map(article -> Map.entry(article, similarityEngine.calculateTitleScore(title, article.getTitle())))
				.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
				.sorted(Map.Entry.<WikiPageDbo, Double>comparingByValue().reversed())
				.limit(MAX_PER_SOURCE)
				.map(entry -> new MergeCandidate(sourceType, sourceId, title, slug,
						"WIKI_PAGE", entry.getKey().getWikiPageId(), entry.getKey().getTitle(), entry.getKey().getSlug(),
						(int) Math.round(entry.getValue() * 100), "title match"))
				.toList();
	}

	private List<MergeCandidate> threadCandidates(List<ContentEntityDbo> projects) {
		List<ContentEntityDbo> unlinkedProjects = projects.stream().filter(project -> project.getThreadId() == null).toList();
		if (unlinkedProjects.isEmpty()) {
			return List.of();
		}
		List<ThreadDbo> threads = threadMapper.selectByExample(new ThreadDboExample());
		Map<ThreadDbo, Set<String>> threadTokensMap = new HashMap<>();
		threads.forEach(thread -> threadTokensMap.put(thread, similarityEngine.tokenize(thread.getThreadName())));

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : unlinkedProjects) {
			Set<String> projectTokens = similarityEngine.tokenize(project.getTitle());
			threads.stream()
					.map(thread -> Map.entry(thread, similarityEngine.calculateContainmentScore(projectTokens, threadTokensMap.get(thread),
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
		entityMerger.applyMerge(request);
	}

	public CmsConfig getConfig() {
		return new CmsConfig(systemConfigService.get(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID));
	}

	public CmsConfig setConfig(CmsConfig config) {
		systemConfigService.set(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID, config.discussionBoardId());
		return getConfig();
	}
}
