package com.zfgc.zfgbb.services.system;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.MigrationConflictDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;

import lombok.Data;
import java.util.Collection;

@Service
@Transactional
@UnfilteredBoardRead("Principal-less migration-conflict reconciliation reads threads by id to compare against imported data")
public class MigrationConflictService {

	public static final String STATUS_OPEN = "OPEN";
	public static final String STATUS_RESOLVED = "RESOLVED";
	public static final String STATUS_DISMISSED = "DISMISSED";

	private static final Set<String> IGNORED_AUTHORS = Set.of("unknown", "guest", "anonymous", "");

	@Autowired
	private ContentEntityDboMapper contentEntityMapper;

	@Autowired
	private ThreadDboMapper threadMapper;

	@Autowired
	private UserDboMapper userMapper;

	@Autowired
	private MigrationConflictDboMapper conflictMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Data
	public static class Candidate {
		private String sourceType;
		private String sourceRef;
		private String value;
		private String label;

		public Candidate() {
		}

		public Candidate(String sourceType, String sourceRef, String value, String label) {
			this.sourceType = sourceType;
			this.sourceRef = sourceRef;
			this.value = value;
			this.label = label;
		}
	}

	@Data
	public static class ConflictView {
		private Integer id;
		private String entityType;
		private Integer entityId;
		private String entityLabel;
		private String fieldName;
		private List<Candidate> candidates;
		private String status;
		private String resolvedSourceType;
		private String resolvedValue;
		private OffsetDateTime detectedTs;
		private OffsetDateTime resolvedTs;
	}

	@Data
	public static class ResolveRequest {
		private String sourceType;
		private String customValue;
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
					.map(candidate -> candidate.getValue().toLowerCase())
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
					.filter(candidate -> candidate.getSourceType().equalsIgnoreCase(sourceType))
					.map(Candidate::getValue)
					.findFirst()
					.orElseThrow(() -> new ZfgcNotFoundException());
		}

		applyToEntity(conflict, value);

		conflict.setStatus(STATUS_RESOLVED);
		conflict.setResolvedSourceType(sourceType.toUpperCase());
		conflict.setResolvedValue(value);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(java.time.ZoneOffset.UTC));
		conflictMapper.updateByPrimaryKey(conflict);
		return toView(conflict, projectTitles(List.of(conflict)));
	}

	public ConflictView dismiss(Integer id, Integer userId) {
		MigrationConflictDbo conflict = require(id);
		conflict.setStatus(STATUS_DISMISSED);
		conflict.setResolvedByUserId(userId);
		conflict.setResolvedTs(OffsetDateTime.now(java.time.ZoneOffset.UTC));
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
			existing.setUpdatedTs(OffsetDateTime.now(java.time.ZoneOffset.UTC));
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
		ConflictView view = new ConflictView();
		view.setId(row.getMigrationConflictId());
		view.setEntityType(row.getEntityType());
		view.setEntityId(row.getEntityId());
		view.setEntityLabel(projectTitles.get(row.getEntityId()));
		view.setFieldName(row.getFieldName());
		view.setCandidates(parse(row.getCandidates()));
		view.setStatus(row.getStatus());
		view.setResolvedSourceType(row.getResolvedSourceType());
		view.setResolvedValue(row.getResolvedValue());
		view.setDetectedTs(row.getCreatedTs());
		view.setResolvedTs(row.getResolvedTs());
		return view;
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
}
