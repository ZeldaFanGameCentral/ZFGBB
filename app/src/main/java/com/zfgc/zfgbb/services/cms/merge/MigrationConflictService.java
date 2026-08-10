package com.zfgc.zfgbb.services.cms.merge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zfgc.zfgbb.dataprovider.cms.MigrationConflictDataProvider;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.model.cms.ArticleRef;
import com.zfgc.zfgbb.model.cms.ConflictCandidate;
import com.zfgc.zfgbb.model.cms.ConflictSubject;
import com.zfgc.zfgbb.model.cms.ConflictThread;
import com.zfgc.zfgbb.model.cms.ConflictView;
import com.zfgc.zfgbb.model.cms.ContentMergeSide;
import com.zfgc.zfgbb.model.cms.MergeApplyRequest;
import com.zfgc.zfgbb.model.cms.MergeCandidate;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MigrationConflictService {

	public static final String STATUS_OPEN = MigrationConflictDataProvider.STATUS_OPEN;
	public static final String STATUS_RESOLVED = MigrationConflictDataProvider.STATUS_RESOLVED;
	public static final String STATUS_DISMISSED = MigrationConflictDataProvider.STATUS_DISMISSED;

	private static final Set<String> IGNORED_AUTHORS = Set.of("unknown", "guest", "anonymous", "");

	private static final int MIN_CONFIDENCE = 75;
	private static final int DEDUP_MIN_CONFIDENCE = 60;
	private static final int MAX_PER_SOURCE = 3;
	private static final Set<String> ARTICLE_NAMESPACES = Set.of("MAIN", "KOT");

	private static final String ENTITY_TYPE_PROJECT = "PROJECT";
	private static final String ENTITY_TYPE_RESOURCE = "RESOURCE";
	private static final String FIELD_AUTHOR_NAME = "author_name";
	private static final String SOURCE_TYPE_CUSTOM = "CUSTOM";

	private final MigrationConflictDataProvider conflictData;

	private final CmsSimilarityEngine similarityEngine;

	private final CmsEntityMerger entityMerger;

	public record ResolveRequest(String sourceType, String customValue) {
	}

	public record BulkOutcome(Integer id, boolean ok, String error) {}

	public record ResolveOne(Integer id, String sourceType, String customValue) {}

	public int scan() {
		List<ConflictSubject> projects = conflictData.entitiesOfType(ENTITY_TYPE_PROJECT);
		Map<Integer, ConflictThread> threads = conflictData
				.threadsById(projects.stream().map(ConflictSubject::threadId).toList()).stream()
				.collect(Collectors.toMap(ConflictThread::threadId, Function.identity(), (a, b) -> a));

		int detected = 0;
		for (ConflictSubject project : projects) {
			List<ConflictCandidate> candidates = authorCandidates(project, threads);
			long distinct = candidates.stream()
					.map(candidate -> candidate.value().toLowerCase())
					.filter(value -> !IGNORED_AUTHORS.contains(value))
					.distinct()
					.count();
			if (distinct >= 2) {
				conflictData.upsertConflict(ENTITY_TYPE_PROJECT, project.entityId(), FIELD_AUTHOR_NAME, candidates);
				detected++;
			}
		}
		return detected;
	}

	private List<ConflictCandidate> authorCandidates(ConflictSubject project, Map<Integer, ConflictThread> threads) {
		List<ConflictCandidate> candidates = new ArrayList<>();
		if (StringUtils.hasText(project.authorName()))
			candidates.add(new ConflictCandidate("CMS", "project.author_name", project.authorName().trim(),
					"CMS record"));
		ConflictThread thread = project.threadId() == null ? null : threads.get(project.threadId());
		if (thread != null && StringUtils.hasText(thread.authorDisplayName()))
			candidates.add(new ConflictCandidate("THREAD", "thread:" + thread.threadId(),
					thread.authorDisplayName().trim(), "Forum thread: " + thread.threadName()));
		return candidates;
	}

	public List<ConflictView> list(String status) {
		return conflictData.list(status);
	}

	public ConflictView resolve(Integer id, String sourceType, String customValue, Integer userId) {
		ConflictView conflict = conflictData.find(id);
		String value;
		if (SOURCE_TYPE_CUSTOM.equalsIgnoreCase(sourceType)) {
			value = customValue == null ? "" : customValue.trim();
		} else {
			value = conflict.candidates().stream()
					.filter(candidate -> candidate.sourceType().equalsIgnoreCase(sourceType))
					.map(ConflictCandidate::value)
					.findFirst()
					.orElseThrow(ZfgcNotFoundException::new);
		}

		applyToEntity(conflict, value);
		return conflictData.markResolved(id, sourceType, value, userId);
	}

	public ConflictView dismiss(Integer id, Integer userId) {
		return conflictData.markDismissed(id, userId);
	}

	public ConflictView reopen(Integer id) {
		return conflictData.markOpen(id);
	}

	private void applyToEntity(ConflictView conflict, String value) {
		if (ENTITY_TYPE_PROJECT.equals(conflict.entityType()) && FIELD_AUTHOR_NAME.equals(conflict.fieldName())) {
			conflictData.applyAuthorName(conflict.entityId(), value);
			return;
		}
		throw new IllegalArgumentException("no applier for " + conflict.entityType() + "." + conflict.fieldName());
	}

	@Transactional(readOnly = true)
	public List<MergeCandidate> getMergeCandidates() {
		List<ConflictSubject> projects = conflictData.entitiesOfType(ENTITY_TYPE_PROJECT);
		List<ConflictSubject> resources = conflictData.entitiesOfType(ENTITY_TYPE_RESOURCE);
		List<ArticleRef> articles = conflictData.articlesInNamespaces(ARTICLE_NAMESPACES);

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ConflictSubject project : projects)
			candidates.addAll(wikiCandidates(ContentMergeSide.PROJECT, project, articles));
		for (ConflictSubject resource : resources)
			candidates.addAll(wikiCandidates(ContentMergeSide.RESOURCE, resource, articles));

		for (int indexA = 0; indexA < projects.size(); indexA++) {
			for (int indexB = indexA + 1; indexB < projects.size(); indexB++) {
				ConflictSubject projectA = projects.get(indexA);
				ConflictSubject projectB = projects.get(indexB);
				int confidence = (int) Math.round(
						similarityEngine.calculateJaccardSimilarity(projectA.title(), projectB.title()) * 100);
				if (confidence >= DEDUP_MIN_CONFIDENCE)
					candidates.add(new MergeCandidate(ContentMergeSide.PROJECT, projectB.entityId(), projectB.title(),
							projectB.slug(), ContentMergeSide.PROJECT, projectA.entityId(), projectA.title(),
							projectA.slug(), confidence, "duplicate title"));
			}
		}

		candidates.addAll(threadCandidates(projects));
		candidates.sort(Comparator.comparingInt(MergeCandidate::confidence).reversed());
		return candidates;
	}

	private List<MergeCandidate> wikiCandidates(ContentMergeSide sourceType, ConflictSubject source,
			List<ArticleRef> articles) {
		return articles.stream()
				.filter(article -> !article.wikiPageId().equals(source.wikiPageId()))
				.map(article -> Map.entry(article, similarityEngine.calculateTitleScore(source.title(),
						article.title())))
				.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
				.sorted(Map.Entry.<ArticleRef, Double>comparingByValue().reversed())
				.limit(MAX_PER_SOURCE)
				.map(entry -> new MergeCandidate(sourceType, source.entityId(), source.title(), source.slug(),
						ContentMergeSide.WIKI_PAGE, entry.getKey().wikiPageId(), entry.getKey().title(),
						entry.getKey().slug(), (int) Math.round(entry.getValue() * 100), "title match"))
				.toList();
	}

	private List<MergeCandidate> threadCandidates(List<ConflictSubject> projects) {
		List<ConflictSubject> unlinkedProjects = projects.stream().filter(project -> project.threadId() == null)
				.toList();
		if (unlinkedProjects.isEmpty())
			return List.of();
		List<ConflictThread> threads = conflictData.allThreads();
		Map<ConflictThread, Set<String>> threadTokensMap = new HashMap<>();
		for (ConflictThread thread : threads)
			threadTokensMap.put(thread, similarityEngine.tokenize(thread.threadName()));

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ConflictSubject project : unlinkedProjects) {
			Set<String> projectTokens = similarityEngine.tokenize(project.title());
			threads.stream()
					.map(thread -> Map.entry(thread,
							similarityEngine.calculateContainmentScore(projectTokens, threadTokensMap.get(thread),
									CmsSupport.normalizeTitle(project.title()),
									CmsSupport.normalizeTitle(thread.threadName()))))
					.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
					.sorted(Map.Entry.<ConflictThread, Double>comparingByValue().reversed())
					.limit(MAX_PER_SOURCE)
					.forEach(entry -> candidates.add(new MergeCandidate(
							ContentMergeSide.PROJECT, project.entityId(), project.title(), project.slug(),
							ContentMergeSide.THREAD, entry.getKey().threadId(), entry.getKey().threadName(), null,
							(int) Math.round(entry.getValue() * 100), "thread title match")));
		}
		return candidates;
	}

	public void apply(MergeApplyRequest request) {
		entityMerger.applyMerge(request);
	}
}
