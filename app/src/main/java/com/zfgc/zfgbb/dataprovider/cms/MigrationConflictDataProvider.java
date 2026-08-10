package com.zfgc.zfgbb.dataprovider.cms;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.dao.cms.MigrationConflictDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.dbo.MigrationConflictDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mapstruct.cms.MigrationConflictMap;
import com.zfgc.zfgbb.model.cms.ArticleRef;
import com.zfgc.zfgbb.model.cms.ConflictCandidate;
import com.zfgc.zfgbb.model.cms.ConflictSubject;
import com.zfgc.zfgbb.model.cms.ConflictThread;
import com.zfgc.zfgbb.model.cms.ConflictView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Repository
@UnfilteredBoardRead("migration job")
@RequiredArgsConstructor
public class MigrationConflictDataProvider {

	public static final String STATUS_OPEN = "OPEN";
	public static final String STATUS_RESOLVED = "RESOLVED";
	public static final String STATUS_DISMISSED = "DISMISSED";

	private static final String ENTITY_TYPE_PROJECT = "PROJECT";

	private final ContentEntityDao contentEntityDao;

	private final UserErasureDao userErasureDao;

	private final ThreadDao threadDao;

	private final UserDao userDao;

	private final WikiPageDao wikiPageDao;

	private final MigrationConflictDao migrationConflictDao;

	private final MigrationConflictMap conflictMap;

	private final ObjectMapper objectMapper;

	public List<ConflictSubject> entitiesOfType(String entityType) {
		ContentEntityDboExample ex = new ContentEntityDboExample();
		ex.createCriteria().andEntityTypeEqualTo(entityType);
		return contentEntityDao.get(ex).stream().map(MigrationConflictDataProvider::toSubject).toList();
	}

	public List<ConflictThread> threadsById(Collection<Integer> threadIds) {
		List<Integer> ids = threadIds.stream().filter(id -> id != null).distinct().toList();
		if (ids.isEmpty())
			return List.of();
		ThreadDboExample ex = new ThreadDboExample();
		ex.createCriteria().andThreadIdIn(new ArrayList<>(ids));
		return withAuthorNames(threadDao.get(ex));
	}

	public List<ConflictThread> allThreads() {
		return threadDao.get(new ThreadDboExample()).stream()
				.map(thread -> new ConflictThread(thread.getThreadId(), thread.getThreadName(), null)).toList();
	}

	public List<ArticleRef> articlesInNamespaces(Collection<String> namespaces) {
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceIn(new ArrayList<>(namespaces)).andRedirectToIsNull();
		return wikiPageDao.get(ex).stream()
				.map(article -> new ArticleRef(article.getWikiPageId(), article.getTitle(), article.getSlug())).toList();
	}

	public List<ConflictView> list(String status) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		if (StringUtils.hasText(status))
			ex.createCriteria().andStatusEqualTo(status);
		ex.setOrderByClause("status asc, migration_conflict_id asc");
		List<MigrationConflictDbo> rows = migrationConflictDao.get(ex);
		Map<Integer, String> titles = titlesByEntityId(rows);
		return rows.stream().map(row -> toView(row, titles, true)).toList();
	}

	public ConflictView find(Integer id) {
		MigrationConflictDbo row = migrationConflictDao.find(id).orElseThrow(ZfgcNotFoundException::new);
		return toView(row, titlesByEntityId(List.of(row)), false);
	}

	public ConflictView markResolved(Integer id, String sourceType, String value, Integer userId) {
		MigrationConflictDbo row = migrationConflictDao.find(id).orElseThrow(ZfgcNotFoundException::new);
		row.setStatus(STATUS_RESOLVED);
		row.setResolvedSourceType(sourceType.toUpperCase());
		row.setResolvedValue(value);
		row.setResolvedByUserId(userId);
		row.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		migrationConflictDao.update(row);
		return toView(row, titlesByEntityId(List.of(row)), true);
	}

	public ConflictView markDismissed(Integer id, Integer userId) {
		MigrationConflictDbo row = migrationConflictDao.find(id).orElseThrow(ZfgcNotFoundException::new);
		row.setStatus(STATUS_DISMISSED);
		row.setResolvedByUserId(userId);
		row.setResolvedTs(OffsetDateTime.now(ZoneOffset.UTC));
		migrationConflictDao.update(row);
		return toView(row, titlesByEntityId(List.of(row)), true);
	}

	public ConflictView markOpen(Integer id) {
		MigrationConflictDbo row = migrationConflictDao.find(id).orElseThrow(ZfgcNotFoundException::new);
		row.setStatus(STATUS_OPEN);
		row.setResolvedSourceType(null);
		row.setResolvedValue(null);
		row.setResolvedByUserId(null);
		row.setResolvedTs(null);
		migrationConflictDao.update(row);
		return toView(row, titlesByEntityId(List.of(row)), true);
	}

	public void upsertConflict(String entityType, Integer entityId, String fieldName,
			List<ConflictCandidate> candidates) {
		MigrationConflictDboExample ex = new MigrationConflictDboExample();
		ex.createCriteria().andEntityTypeEqualTo(entityType).andEntityIdEqualTo(entityId)
				.andFieldNameEqualTo(fieldName);
		MigrationConflictDbo existing = migrationConflictDao.getOne(ex).orElse(null);
		if (existing != null) {
			if (STATUS_RESOLVED.equals(existing.getStatus()) || STATUS_DISMISSED.equals(existing.getStatus()))
				return;
			existing.setCandidates(serialize(candidates));
			migrationConflictDao.update(existing);
			return;
		}
		MigrationConflictDbo row = new MigrationConflictDbo();
		row.setEntityType(entityType);
		row.setEntityId(entityId);
		row.setFieldName(fieldName);
		row.setCandidates(serialize(candidates));
		row.setStatus(STATUS_OPEN);
		migrationConflictDao.insert(row);
	}

	public void applyAuthorName(Integer entityId, String value) {
		ContentEntityDbo entity = contentEntityDao.find(entityId).orElseThrow(ZfgcNotFoundException::new);
		entity.setAuthorName(value);
		contentEntityDao.save(entity);
	}

	private List<ConflictThread> withAuthorNames(List<ThreadDbo> threads) {
		List<Integer> authorIds = threads.stream().map(ThreadDbo::getCreatedUserId).filter(id -> id != null).distinct()
				.toList();
		Map<Integer, String> displayNames = new HashMap<>();
		if (!authorIds.isEmpty()) {
			UserDboExample ex = new UserDboExample();
			ex.createCriteria().andUserIdIn(new ArrayList<>(authorIds));
			for (UserDbo author : userDao.get(ex))
				displayNames.put(author.getUserId(), author.getDisplayName());
		}
		return threads.stream().map(thread -> new ConflictThread(thread.getThreadId(), thread.getThreadName(),
				displayNames.get(thread.getCreatedUserId()))).toList();
	}

	private Map<Integer, String> titlesByEntityId(List<MigrationConflictDbo> rows) {
		List<Integer> ids = rows.stream().filter(row -> ENTITY_TYPE_PROJECT.equals(row.getEntityType()))
				.map(MigrationConflictDbo::getEntityId).filter(id -> id != null).distinct().toList();
		if (ids.isEmpty())
			return Map.of();
		ContentEntityDboExample ex = new ContentEntityDboExample();
		ex.createCriteria().andContentEntityIdIn(new ArrayList<>(ids));
		Map<Integer, String> titles = new HashMap<>();
		for (ContentEntityDbo entity : contentEntityDao.get(ex))
			titles.put(entity.getContentEntityId(), entity.getTitle());
		return titles;
	}

	private ConflictView toView(MigrationConflictDbo row, Map<Integer, String> titles, boolean tolerateUnreadable) {
		return conflictMap.toView(row, titles.get(row.getEntityId()), candidates(row, tolerateUnreadable));
	}

	private List<ConflictCandidate> candidates(MigrationConflictDbo row, boolean tolerateUnreadable) {
		if (!tolerateUnreadable)
			return deserialize(row.getCandidates());
		try {
			return deserialize(row.getCandidates());
		} catch (ZfgcInvalidRequestException unreadable) {
			log.warn("conflict {} has candidates this service cannot read; listing it with none",
					row.getMigrationConflictId(), unreadable);
			return List.of();
		}
	}

	private List<ConflictCandidate> deserialize(String candidates) {
		try {
			return objectMapper.readValue(candidates, new TypeReference<List<ConflictCandidate>>() {
			});
		} catch (JacksonException unreadable) {
			throw new ZfgcInvalidRequestException("unreadable conflict candidates");
		}
	}

	private String serialize(List<ConflictCandidate> candidates) {
		try {
			return objectMapper.writeValueAsString(candidates);
		} catch (JacksonException unwritable) {
			throw new IllegalStateException("unserializable conflict candidates: " + candidates, unwritable);
		}
	}

	private static ConflictSubject toSubject(ContentEntityDbo entity) {
		return new ConflictSubject(entity.getContentEntityId(), entity.getTitle(), entity.getSlug(),
				entity.getAuthorName(), entity.getThreadId(), entity.getWikiPageId());
	}

	public void nullConflictResolvers(Integer userId) {
		userErasureDao.nullMigrationConflictResolvers(userId);
	}
}
