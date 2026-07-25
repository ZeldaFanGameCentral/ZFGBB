package com.zfgc.zfgbb.services.cms.merge;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.dbo.MigrationConflictDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.model.cms.ContentMergeSide;
import com.zfgc.zfgbb.model.cms.MergeApplyRequest;
import com.zfgc.zfgbb.model.cms.MergeCandidate;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.MigrationConflictDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;

import lombok.RequiredArgsConstructor;
import java.util.Collection;

@Service
@Transactional
@UnfilteredBoardRead("migration job, no user; the absorbed merge-candidate scan runs for a site administrator "
		+ "over every migrated entity, so it reads boards unfiltered too")
@RequiredArgsConstructor
public class MigrationConflictService {

	public static final String STATUS_OPEN = "OPEN";
	public static final String STATUS_RESOLVED = "RESOLVED";
	public static final String STATUS_DISMISSED = "DISMISSED";

	private static final Set<String> IGNORED_AUTHORS = Set.of("unknown", "guest", "anonymous", "");

	private static final int MIN_CONFIDENCE = 75;
	private static final int DEDUP_MIN_CONFIDENCE = 60;
	private static final int MAX_PER_SOURCE = 3;
	private static final Set<String> ARTICLE_NAMESPACES = Set.of("MAIN", "KOT");

	private final ContentEntityDboMapper contentEntityMapper;

	private final ThreadDboMapper threadMapper;

	private final UserDboMapper userMapper;

	private final MigrationConflictDboMapper conflictMapper;

	private final ObjectMapper objectMapper;

	private final WikiPageDboMapper wikiPageMapper;

	private final CmsSimilarityEngine similarityEngine;

	private final CmsEntityMerger entityMerger;

	public record Candidate(String sourceType, String sourceRef, String value, String label) {
	}

	public record ConflictView(Integer id, String entityType, Integer entityId, String entityLabel, String fieldName,
			List<Candidate> candidates, String status, String resolvedSourceType, String resolvedValue,
			OffsetDateTime detectedTs, OffsetDateTime resolvedTs) {
	}

	public record ResolveRequest(String sourceType, String customValue) {
	}

	public int scan() {
		ContentEntityDboExample projectEx = new ContentEntityDboExample();
		projectEx.createCriteria().andEntityTypeEqualTo("PROJECT");
		List<ContentEntityDbo> projects = contentEntityMapper.selectByExample(projectEx);
		Map<Integer, ThreadDbo> threads = threadsById(projects);
		Map<Integer, UserDbo> users = usersById(threads.values());

		int detected = 0;
		for (ContentEntityDbo project : projects) {
			List<Candidate> candidates = authorCandidates(project, threads, users);
			long distinct = candidates.stream()
					.map(candidate -> candidate.value().toLowerCase())
					.filter(value -> !IGNORED_AUTHORS.contains(value))
					.distinct()
					.count();
			if (distinct >= 2) {
				upsert("PROJECT", project.getContentEntityId(), "author_name", candidates);
				detected++;
			}
		}
		return detected;
	}

	private List<Candidate> authorCandidates(ContentEntityDbo project, Map<Integer, ThreadDbo> threads,
			Map<Integer, UserDbo> users) {
		List<Candidate> candidates = new ArrayList<>();
		if (notBlank(project.getAuthorName())) {
			candidates.add(new Candidate("CMS", "project.author_name", project.getAuthorName().trim(),
					"CMS record"));
		}
		ThreadDbo thread = project.getThreadId() == null ? null : threads.get(project.getThreadId());
		if (thread != null && thread.getCreatedUserId() != null) {
			UserDbo author = users.get(thread.getCreatedUserId());
			if (author != null && notBlank(author.getDisplayName())) {
				candidates.add(new Candidate("THREAD", "thread:" + thread.getThreadId(),
						author.getDisplayName().trim(), "Forum thread: " + thread.getThreadName()));
			}
		}
		return candidates;
	}

	public List<ConflictView> list(String status) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		if (notBlank(status)) {
			ex.createCriteria().andStatusEqualTo(status);
		}
		ex.setOrderByClause("status asc, migration_conflict_id asc");
		List<MigrationConflictDbo> rows = conflictMapper.selectByExample(ex);
		Map<Integer, String> projectTitles = projectTitles(rows);
		return rows.stream().map(row -> toView(row, projectTitles)).toList();
	}

	public ConflictView resolve(Integer id, String sourceType, String customValue, Integer userId) {
		MigrationConflictDbo conflict = require(id);
		List<Candidate> candidates = parse(conflict.getCandidates());
		String value;
		if ("CUSTOM".equalsIgnoreCase(sourceType)) {
			value = customValue == null ? "" : customValue.trim();
		} else {
			value = candidates.stream()
					.filter(candidate -> candidate.sourceType().equalsIgnoreCase(sourceType))
					.map(Candidate::value)
					.findFirst()
					.orElseThrow(() -> new ZfgcNotFoundException());
		}

		applyToEntity(conflict, value);

		conflict.setStatus(STATUS_RESOLVED);
		conflict.setResolvedSourceType(sourceType.toUpperCase());
		conflict.setResolvedValue(value);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		conflictMapper.updateByPrimaryKey(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	public ConflictView dismiss(Integer id, Integer userId) {
		MigrationConflictDbo conflict = require(id);
		conflict.setStatus(STATUS_DISMISSED);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		conflictMapper.updateByPrimaryKey(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	private void applyToEntity(MigrationConflictDbo conflict, String value) {
		if ("PROJECT".equals(conflict.getEntityType()) && "author_name".equals(conflict.getFieldName())) {
			ContentEntityDbo project = contentEntityMapper.selectByPrimaryKey(conflict.getEntityId());
			if (project == null) {
				throw new ZfgcNotFoundException();
			}
			project.setAuthorName(value);
			contentEntityMapper.updateByPrimaryKey(project);
			return;
		}
		throw new IllegalArgumentException(
				"no applier for " + conflict.getEntityType() + "." + conflict.getFieldName());
	}

	private void upsert(String entityType, Integer entityId, String fieldName, List<Candidate> candidates) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		ex.createCriteria().andEntityTypeEqualTo(entityType).andEntityIdEqualTo(entityId)
				.andFieldNameEqualTo(fieldName);
		MigrationConflictDbo existing = conflictMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing != null) {
			if (STATUS_RESOLVED.equals(existing.getStatus()) || STATUS_DISMISSED.equals(existing.getStatus())) {
				return;
			}
			existing.setCandidates(write(candidates));
			existing.setUpdatedTs(OffsetDateTime.now(ZoneOffset.UTC));
			conflictMapper.updateByPrimaryKey(existing);
			return;
		}
		MigrationConflictDbo row = new MigrationConflictDbo();
		row.setEntityType(entityType);
		row.setEntityId(entityId);
		row.setFieldName(fieldName);
		row.setCandidates(write(candidates));
		row.setStatus(STATUS_OPEN);
		conflictMapper.insert(row);
	}

	private ConflictView toView(MigrationConflictDbo row, Map<Integer, String> projectTitles) {
		return new ConflictView(row.getMigrationConflictId(), row.getEntityType(), row.getEntityId(),
				projectTitles.get(row.getEntityId()), row.getFieldName(), parse(row.getCandidates()),
				row.getStatus(), row.getResolvedSourceType(), row.getResolvedValue(), row.getCreatedTs(),
				row.getResolvedTs());
	}

	private Map<Integer, ThreadDbo> threadsById(List<ContentEntityDbo> projects) {
		List<Integer> ids = projects.stream().map(ContentEntityDbo::getThreadId).filter(x -> x != null).distinct().toList();
		if (ids.isEmpty()) {
			return Map.of();
		}
		ThreadDboExample ex = new ThreadDboExample();
		ex.createCriteria().andThreadIdIn(ids);
		return threadMapper.selectByExample(ex).stream()
				.collect(Collectors.toMap(ThreadDbo::getThreadId, t -> t, (a, b) -> a));
	}

	private Map<Integer, UserDbo> usersById(Collection<ThreadDbo> threads) {
		List<Integer> ids = threads.stream().map(ThreadDbo::getCreatedUserId).filter(x -> x != null).distinct().toList();
		if (ids.isEmpty()) {
			return Map.of();
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdIn(ids);
		return userMapper.selectByExample(ex).stream()
				.collect(Collectors.toMap(UserDbo::getUserId, u -> u, (a, b) -> a));
	}

	private Map<Integer, String> projectTitles(List<MigrationConflictDbo> rows) {
		List<Integer> ids = rows.stream().filter(row -> "PROJECT".equals(row.getEntityType()))
				.map(MigrationConflictDbo::getEntityId).filter(x -> x != null).distinct().toList();
		if (ids.isEmpty()) {
			return Map.of();
		}
		ContentEntityDboExample ex = new ContentEntityDboExample();
		ex.createCriteria().andContentEntityIdIn(ids);
		Map<Integer, String> titles = new HashMap<>();
		contentEntityMapper.selectByExample(ex).forEach(p -> titles.put(p.getContentEntityId(), p.getTitle()));
		return titles;
	}

	private MigrationConflictDbo require(Integer id) {
		MigrationConflictDbo row = conflictMapper.selectByPrimaryKey(id);
		if (row == null) {
			throw new ZfgcNotFoundException();
		}
		return row;
	}

	private List<Candidate> parse(String candidates) {
		try {
			return objectMapper.readValue(candidates, new TypeReference<List<Candidate>>() {
			});
		} catch (Exception e) {
			return List.of();
		}
	}

	private String write(List<Candidate> candidates) {
		try {
			return objectMapper.writeValueAsString(candidates);
		} catch (Exception e) {
			throw new RuntimeException("failed to serialize conflict candidates", e);
		}
	}

	private static boolean notBlank(String s) {
		return s != null && !s.trim().isEmpty();
	}

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
			candidates.addAll(wikiCandidates(ContentMergeSide.PROJECT, project.getContentEntityId(), project.getTitle(),
					project.getSlug(), project.getWikiPageId(), articles));
		}
		for (ContentEntityDbo resource : resources) {
			candidates.addAll(wikiCandidates(ContentMergeSide.RESOURCE, resource.getContentEntityId(), resource.getTitle(),
					resource.getSlug(), resource.getWikiPageId(), articles));
		}

		for (int indexA = 0; indexA < projects.size(); indexA++) {
			for (int indexB = indexA + 1; indexB < projects.size(); indexB++) {
				ContentEntityDbo projectA = projects.get(indexA);
				ContentEntityDbo projectB = projects.get(indexB);
				int confidence = (int) Math.round(similarityEngine.calculateJaccardSimilarity(projectA.getTitle(), projectB.getTitle()) * 100);
				if (confidence >= DEDUP_MIN_CONFIDENCE) {
					candidates.add(new MergeCandidate(ContentMergeSide.PROJECT, projectB.getContentEntityId(),
							projectB.getTitle(), projectB.getSlug(),
							ContentMergeSide.PROJECT, projectA.getContentEntityId(), projectA.getTitle(),
							projectA.getSlug(), confidence, "duplicate title"));
				}
			}
		}

		candidates.addAll(threadCandidates(projects));
		candidates.sort(Comparator.comparingInt(MergeCandidate::confidence).reversed());
		return candidates;
	}

	private List<MergeCandidate> wikiCandidates(ContentMergeSide sourceType, Integer sourceId, String title,
			String slug, Integer linkedWikiPageId, List<WikiPageDbo> articles) {
		return articles.stream()
				.filter(article -> !article.getWikiPageId().equals(linkedWikiPageId))
				.map(article -> Map.entry(article, similarityEngine.calculateTitleScore(title, article.getTitle())))
				.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
				.sorted(Map.Entry.<WikiPageDbo, Double>comparingByValue().reversed())
				.limit(MAX_PER_SOURCE)
				.map(entry -> new MergeCandidate(sourceType, sourceId, title, slug,
						ContentMergeSide.WIKI_PAGE, entry.getKey().getWikiPageId(), entry.getKey().getTitle(),
						entry.getKey().getSlug(),
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
							ContentMergeSide.PROJECT, project.getContentEntityId(), project.getTitle(), project.getSlug(),
							ContentMergeSide.THREAD, entry.getKey().getThreadId(), entry.getKey().getThreadName(), null,
							(int) Math.round(entry.getValue() * 100), "thread title match")));
		}
		return candidates;
	}

	public void apply(MergeApplyRequest request) {
		entityMerger.applyMerge(request);
	}
}
