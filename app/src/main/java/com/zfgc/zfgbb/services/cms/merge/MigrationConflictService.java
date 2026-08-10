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

import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.core.JacksonException;
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
import com.zfgc.zfgbb.mapstruct.cms.MigrationConflictMap;
import com.zfgc.zfgbb.model.cms.ConflictCandidate;
import com.zfgc.zfgbb.model.cms.ConflictView;
import com.zfgc.zfgbb.model.cms.MergeCandidate;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.cms.MigrationConflictDao;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.dao.users.UserDao;

import lombok.RequiredArgsConstructor;
import java.util.Collection;

@Slf4j
@Service
@Transactional
@UnfilteredBoardRead("migration job")
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

	private final ContentEntityDao contentEntityDao;

	private final ThreadDao threadDao;

	private final UserDao userDao;

	private final MigrationConflictDao migrationConflictDao;

	private final ObjectMapper objectMapper;

	private final WikiPageDao wikiPageDao;

	private final MigrationConflictMap conflictMap;

	private final CmsSimilarityEngine similarityEngine;

	private final CmsEntityMerger entityMerger;

	public record ResolveRequest(String sourceType, String customValue) {
	}

	public int scan() {
		ContentEntityDboExample projectEx = new ContentEntityDboExample();
		projectEx.createCriteria().andEntityTypeEqualTo("PROJECT");
		List<ContentEntityDbo> projects = contentEntityDao.get(projectEx);
		Map<Integer, ThreadDbo> threads = threadsById(projects);
		Map<Integer, UserDbo> users = usersById(threads.values());

		int detected = 0;
		for (ContentEntityDbo project : projects) {
			List<ConflictCandidate> candidates = authorCandidates(project, threads, users);
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

	private List<ConflictCandidate> authorCandidates(ContentEntityDbo project, Map<Integer, ThreadDbo> threads,
			Map<Integer, UserDbo> users) {
		List<ConflictCandidate> candidates = new ArrayList<>();
		if (StringUtils.hasText(project.getAuthorName())) {
			candidates.add(new ConflictCandidate("CMS", "project.author_name", project.getAuthorName().trim(),
					"CMS record"));
		}
		ThreadDbo thread = project.getThreadId() == null ? null : threads.get(project.getThreadId());
		if (thread != null && thread.getCreatedUserId() != null) {
			UserDbo author = users.get(thread.getCreatedUserId());
			if (author != null && StringUtils.hasText(author.getDisplayName())) {
				candidates.add(new ConflictCandidate("THREAD", "thread:" + thread.getThreadId(),
						author.getDisplayName().trim(), "Forum thread: " + thread.getThreadName()));
			}
		}
		return candidates;
	}

	public List<ConflictView> list(String status) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		if (StringUtils.hasText(status)) {
			ex.createCriteria().andStatusEqualTo(status);
		}
		ex.setOrderByClause("status asc, migration_conflict_id asc");
		List<MigrationConflictDbo> rows = migrationConflictDao.get(ex);
		Map<Integer, String> projectTitles = projectTitles(rows);
		return rows.stream().map(row -> toView(row, projectTitles)).toList();
	}

	public ConflictView resolve(Integer id, String sourceType, String customValue, Integer userId) {
		MigrationConflictDbo conflict = migrationConflictDao.find(id)
				.orElseThrow(ZfgcNotFoundException::new);
		List<ConflictCandidate> candidates = deserializeCandidates(conflict.getCandidates());
		String value;
		if ("CUSTOM".equalsIgnoreCase(sourceType)) {
			value = customValue == null ? "" : customValue.trim();
		} else {
			value = candidates.stream()
					.filter(candidate -> candidate.sourceType().equalsIgnoreCase(sourceType))
					.map(ConflictCandidate::value)
					.findFirst()
					.orElseThrow(() -> new ZfgcNotFoundException());
		}

		applyToEntity(conflict, value);

		conflict.setStatus(STATUS_RESOLVED);
		conflict.setResolvedSourceType(sourceType.toUpperCase());
		conflict.setResolvedValue(value);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		migrationConflictDao.update(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	public ConflictView dismiss(Integer id, Integer userId) {
		MigrationConflictDbo conflict = migrationConflictDao.find(id)
				.orElseThrow(ZfgcNotFoundException::new);
		conflict.setStatus(STATUS_DISMISSED);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		migrationConflictDao.update(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	public ConflictView reopen(Integer id) {
		MigrationConflictDbo conflict = migrationConflictDao.find(id)
				.orElseThrow(ZfgcNotFoundException::new);
		conflict.setStatus(STATUS_OPEN);
		conflict.setResolvedSourceType(null);
		conflict.setResolvedValue(null);
		conflict.setResolvedByUserId(null);
		conflict.setResolvedTs(null);
		migrationConflictDao.update(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	public record BulkOutcome(Integer id, boolean ok, String error) {}

	public record ResolveOne(Integer id, String sourceType, String customValue) {}

	private void applyToEntity(MigrationConflictDbo conflict, String value) {
		if ("PROJECT".equals(conflict.getEntityType()) && "author_name".equals(conflict.getFieldName())) {
			ContentEntityDbo project = contentEntityDao.find(conflict.getEntityId()).orElse(null);
			if (project == null) {
				throw new ZfgcNotFoundException();
			}
			project.setAuthorName(value);
			contentEntityDao.save(project);
			return;
		}
		throw new IllegalArgumentException(
				"no applier for " + conflict.getEntityType() + "." + conflict.getFieldName());
	}

	private void upsert(String entityType, Integer entityId, String fieldName, List<ConflictCandidate> candidates) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		ex.createCriteria().andEntityTypeEqualTo(entityType).andEntityIdEqualTo(entityId)
				.andFieldNameEqualTo(fieldName);
		MigrationConflictDbo existing = migrationConflictDao.getOne(ex).orElse(null);
		if (existing != null) {
			if (STATUS_RESOLVED.equals(existing.getStatus()) || STATUS_DISMISSED.equals(existing.getStatus())) {
				return;
			}
			existing.setCandidates(serializeCandidates(candidates));
			migrationConflictDao.update(existing);
			return;
		}
		MigrationConflictDbo row = new MigrationConflictDbo();
		row.setEntityType(entityType);
		row.setEntityId(entityId);
		row.setFieldName(fieldName);
		row.setCandidates(serializeCandidates(candidates));
		row.setStatus(STATUS_OPEN);
		migrationConflictDao.insert(row);
	}

	private ConflictView toView(MigrationConflictDbo row, Map<Integer, String> projectTitles) {
		return conflictMap.toView(row, projectTitles.get(row.getEntityId()), readableCandidates(row));
	}

	private List<ConflictCandidate> readableCandidates(MigrationConflictDbo row) {
		try {
			return deserializeCandidates(row.getCandidates());
		} catch (ZfgcInvalidRequestException unreadable) {
			log.warn("conflict {} has candidates this service cannot read; listing it with none",
					row.getMigrationConflictId(), unreadable);
			return List.of();
		}
	}

	private Map<Integer, ThreadDbo> threadsById(List<ContentEntityDbo> projects) {
		List<Integer> ids = projects.stream().map(ContentEntityDbo::getThreadId).filter(x -> x != null).distinct()
				.toList();
		if (ids.isEmpty()) {
			return Map.of();
		}
		ThreadDboExample ex = new ThreadDboExample();
		ex.createCriteria().andThreadIdIn(ids);
		return threadDao.get(ex).stream()
				.collect(Collectors.toMap(ThreadDbo::getThreadId, t -> t, (a, b) -> a));
	}

	private Map<Integer, UserDbo> usersById(Collection<ThreadDbo> threads) {
		List<Integer> ids = threads.stream().map(ThreadDbo::getCreatedUserId).filter(x -> x != null).distinct()
				.toList();
		if (ids.isEmpty()) {
			return Map.of();
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdIn(ids);
		return userDao.get(ex).stream()
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
		contentEntityDao.get(ex).forEach(p -> titles.put(p.getContentEntityId(), p.getTitle()));
		return titles;
	}

	private List<ConflictCandidate> deserializeCandidates(String candidates) {
		try {
			return objectMapper.readValue(candidates, new TypeReference<List<ConflictCandidate>>() {
			});
		} catch (JacksonException unreadable) {
			throw new ZfgcInvalidRequestException("unreadable conflict candidates");
		}
	}

	private String serializeCandidates(List<ConflictCandidate> candidates) {
		try {
			return objectMapper.writeValueAsString(candidates);
		} catch (JacksonException unwritable) {
			throw new IllegalStateException("unserializable conflict candidates: " + candidates,
					unwritable);
		}
	}

	@Transactional(readOnly = true)
	public List<MergeCandidate> getMergeCandidates() {
		ContentEntityDboExample projectExample = new ContentEntityDboExample();
		projectExample.createCriteria().andEntityTypeEqualTo("PROJECT");
		List<ContentEntityDbo> projects = contentEntityDao.get(projectExample);
		ContentEntityDboExample resourceExample = new ContentEntityDboExample();
		resourceExample.createCriteria().andEntityTypeEqualTo("RESOURCE");
		List<ContentEntityDbo> resources = contentEntityDao.get(resourceExample);

		WikiPageDboExample wikiExample = new WikiPageDboExample();
		wikiExample.createCriteria().andNamespaceIn(new ArrayList<>(ARTICLE_NAMESPACES)).andRedirectToIsNull();
		List<WikiPageDbo> articles = wikiPageDao.get(wikiExample);

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : projects) {
			candidates.addAll(wikiCandidates(ContentMergeSide.PROJECT, project.getContentEntityId(), project.getTitle(),
					project.getSlug(), project.getWikiPageId(), articles));
		}
		for (ContentEntityDbo resource : resources) {
			candidates.addAll(
					wikiCandidates(ContentMergeSide.RESOURCE, resource.getContentEntityId(), resource.getTitle(),
							resource.getSlug(), resource.getWikiPageId(), articles));
		}

		for (int indexA = 0; indexA < projects.size(); indexA++) {
			for (int indexB = indexA + 1; indexB < projects.size(); indexB++) {
				ContentEntityDbo projectA = projects.get(indexA);
				ContentEntityDbo projectB = projects.get(indexB);
				int confidence = (int) Math.round(
						similarityEngine.calculateJaccardSimilarity(projectA.getTitle(), projectB.getTitle()) * 100);
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
		List<ContentEntityDbo> unlinkedProjects = projects.stream().filter(project -> project.getThreadId() == null)
				.toList();
		if (unlinkedProjects.isEmpty()) {
			return List.of();
		}
		List<ThreadDbo> threads = threadDao.get(new ThreadDboExample());
		Map<ThreadDbo, Set<String>> threadTokensMap = new HashMap<>();
		threads.forEach(thread -> threadTokensMap.put(thread, similarityEngine.tokenize(thread.getThreadName())));

		List<MergeCandidate> candidates = new ArrayList<>();
		for (ContentEntityDbo project : unlinkedProjects) {
			Set<String> projectTokens = similarityEngine.tokenize(project.getTitle());
			threads.stream()
					.map(thread -> Map.entry(thread,
							similarityEngine.calculateContainmentScore(projectTokens, threadTokensMap.get(thread),
									CmsSupport.normalizeTitle(project.getTitle()),
									CmsSupport.normalizeTitle(thread.getThreadName()))))
					.filter(entry -> entry.getValue() * 100 >= MIN_CONFIDENCE)
					.sorted(Map.Entry.<ThreadDbo, Double>comparingByValue().reversed())
					.limit(MAX_PER_SOURCE)
					.forEach(entry -> candidates.add(new MergeCandidate(
							ContentMergeSide.PROJECT, project.getContentEntityId(), project.getTitle(),
							project.getSlug(),
							ContentMergeSide.THREAD, entry.getKey().getThreadId(), entry.getKey().getThreadName(), null,
							(int) Math.round(entry.getValue() * 100), "thread title match")));
		}
		return candidates;
	}

	public void apply(MergeApplyRequest request) {
		entityMerger.applyMerge(request);
	}
}
