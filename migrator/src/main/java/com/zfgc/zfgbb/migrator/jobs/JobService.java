package com.zfgc.zfgbb.migrator.jobs;

import java.sql.Connection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.dao.DataAccessException;

import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.migrator.web.SmfBoardSummary;
import com.zfgc.zfgbb.migrator.web.SmfMemberGroupSummary;
import com.zfgc.zfgbb.operations.contentstore.ContentRootProvider;
import com.zfgc.zfgbb.operations.maintenance.MutationLeaseProvider;
import com.zfgc.zfgbb.wiki.WikiTitle;

import jakarta.annotation.PreDestroy;

@Service
public class JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);

	private final ConcurrentHashMap<UUID, Job> jobs = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<UUID, Future<?>> futures = new ConcurrentHashMap<>();
	private final ExecutorService executor;

	private final Map<JobType, AbstractConverter<?>> convertersByType;
	private final JdbcTemplate targetJdbc;
	private final TransactionTemplate targetTransactions;
	private final Set<UUID> admittedJobs = ConcurrentHashMap.newKeySet();
	private final Optional<MutationLeaseProvider> mutationLeases;
	private final Optional<ContentRootProvider> contentRoots;
	private MutationLeaseProvider.Lease pipelineLease;

	public JobService(List<AbstractConverter<?>> converters, JdbcTemplate targetJdbc,
			PlatformTransactionManager transactionManager,
			@Qualifier("migrationJobExecutor") ExecutorService executor,
			Optional<MutationLeaseProvider> mutationLeases,
			Optional<ContentRootProvider> contentRoots) {
		this.targetJdbc = targetJdbc;
		this.targetTransactions = transactionManager == null ? null : new TransactionTemplate(transactionManager);
		this.executor = executor;
		this.mutationLeases = mutationLeases;
		this.contentRoots = contentRoots;
		this.convertersByType = converters.stream()
				.collect(Collectors.toMap(AbstractConverter::getType, Function.identity()));
		List<JobType> missing = new ArrayList<>();
		for (JobType type : JobType.values()) {
			if (type.isPipeline()) {
				continue;
			}
			if (!convertersByType.containsKey(type)) {
				missing.add(type);
			}
		}
		if (!missing.isEmpty()) {
			throw new IllegalStateException(
					"No AbstractConverter registered for JobType(s): " + missing);
		}
	}

	public synchronized List<Job> submit(JobType type, SmfConnectionParams params) {
		if (!admittedJobs.isEmpty())
			throw new IllegalArgumentException("Another migration is queued or running; wait for it to finish");
		validateTablePrefix(params.smfTablePrefix());
		validateAssetRoots(type, params);
		validateConnection(params);
		try {
			SmfConnectionParams requested = params;
			params = targetTransactions.execute(status -> bootstrapNamespaces(requested));
			List<JobType> steps = type.expand();
			SmfConnectionParams resolvedParams = params;
			List<Job> submitted = steps.stream().map(step -> prepareOne(step, resolvedParams)).toList();
			enqueuePrepared(submitted);
			return submitted;
		} catch (DataAccessException e) {
			throw new IllegalArgumentException("Invalid wiki namespace configuration: " + e.getMostSpecificCause().getMessage(), e);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	void validateAssetRoots(JobType type, SmfConnectionParams params) {
		List<JobType> steps = type.expand();
		if (steps.contains(JobType.ATTACHMENT_FILES)) {
			requireConfigured("attachmentsSourcePath", params.attachmentsSourcePath());
			requireReadableDirectory("attachmentsSourcePath", params.attachmentsSourcePath());
		}
		if (steps.contains(JobType.USER_BIO_INFO)) {
			requireReadableDirectory("avatarsSourcePath", params.avatarsSourcePath());
		}
		if (steps.contains(JobType.PROJECTS) || steps.contains(JobType.RESOURCES)) {
			requireReadableDirectory("cmsFilesSourcePath", params.cmsFilesSourcePath());
		}
		if (steps.contains(JobType.WIKI_PAGES)) {
			requireReadableDirectory("wikiImagesSourcePath", params.wikiImagesSourcePath());
		}
	}

	private static boolean isConfigured(String value) {
		return value != null && !value.isBlank();
	}

	private static void requireConfigured(String field, String value) {
		if (!isConfigured(value))
			throw new IllegalArgumentException(field + " is required for this migration");
	}

	private static void requireReadableDirectory(String field, String configured) {
		if (configured == null || configured.isBlank()) {
			return;
		}
		Path path = Path.of(configured);
		if (!path.isAbsolute())
			throw new IllegalArgumentException(field + " must be an absolute path: " + configured);
		if (!Files.exists(path))
			throw new IllegalArgumentException(field + " does not exist: " + configured);
		if (!Files.isDirectory(path))
			throw new IllegalArgumentException(field + " is not a directory: " + configured);
		if (!Files.isReadable(path))
			throw new IllegalArgumentException(field + " is not readable by the server: " + configured);
	}

	synchronized void enqueuePrepared(List<Job> submitted) {
		if (!admittedJobs.isEmpty())
			throw new IllegalArgumentException("Another migration is queued or running; wait for it to finish");
		try {
			pipelineLease = mutationLeases.isPresent()
					? mutationLeases.get().acquireMutationLease() : () -> {};
		} catch (Exception failure) {
			throw new IllegalArgumentException(
					"Unable to coordinate the migration with application maintenance",
					failure);
		}
		List<Job> accepted = new ArrayList<>();
		try {
			submitted.forEach(job -> {
				jobs.put(job.getId(), job);
				admittedJobs.add(job.getId());
			});
			for (Job job : submitted) {
				FutureTask<Void> task = new FutureTask<>(() -> { run(job); return null; });
				futures.put(job.getId(), task);
				executor.execute(task);
				accepted.add(job);
			}
			releasePipelineLeaseIfIdle();
		} catch (RejectedExecutionException e) {
			submitted.stream().filter(job -> !accepted.contains(job))
					.forEach(job -> cancelQueued(job, "Migration executor rejected the job"));
			throw new IllegalArgumentException("Migration executor rejected the submitted jobs", e);
		} catch (RuntimeException | Error failure) {
			submitted.stream().filter(job -> !accepted.contains(job))
					.forEach(job -> cancelQueued(job, "Migration submission failed"));
			throw failure;
		}
	}

	private Map<Integer, String> configuredImportNamespaces() {
		Map<Integer, String> configured = new LinkedHashMap<>();
		targetJdbc.query("select source_namespace_id, namespace_name from zfgbb.wiki_import_namespace "
				+ "order by source_namespace_id", rs -> {
					configured.put(rs.getInt("source_namespace_id"), rs.getString("namespace_name"));
				});
		return configured;
	}

	private static final List<String> EDIT_POLICY_STRICTNESS = List.of("ZFGC_WIKI_MODERATOR", "ZFGC_SITE_ADMIN");

	private static String strictestEditPolicy(String left, String right) {
		return EDIT_POLICY_STRICTNESS.indexOf(left) >= EDIT_POLICY_STRICTNESS.indexOf(right) ? left : right;
	}

	private void reconcileImportNamespacesWithRoleHolders(Map<Integer, String> importNamespaceIds) {
		for (var entry : importNamespaceIds.entrySet()) {
			String role = CmsSupport.engineRoleName(entry.getKey());
			String configured = entry.getValue() == null ? "" : entry.getValue().trim();
			if (role == null || configured.isEmpty()) continue;
			List<String> holder = targetJdbc.queryForList(
					"select name from zfgbb.wiki_namespace where engine_role = ?", String.class, role);
			if (holder.isEmpty() || holder.get(0).equalsIgnoreCase(configured)) continue;
			log.warn("MediaWiki namespace {} maps to '{}' but the {} namespace is already '{}'; importing into the existing namespace", entry.getKey(), configured, role, holder.get(0));
			entry.setValue(holder.get(0));
			targetJdbc.update("update zfgbb.wiki_import_namespace set namespace_name = ?, updated_ts = now() "
					+ "where source_namespace_id = ?", holder.get(0), entry.getKey());
		}
	}

	private void persistWikiLegacyHost(String wikiLegacyHost) {
		String host = wikiLegacyHost == null ? "" : wikiLegacyHost.trim();
		if (host.isEmpty()) return;
		targetJdbc.update("insert into zfgbb.system_config(config_key, config_value) values (?, ?) "
				+ "on conflict (config_key) do update set config_value = excluded.config_value, "
				+ "updated_ts = current_timestamp", "wiki_legacy_host", host);
	}

	private SmfConnectionParams bootstrapNamespaces(SmfConnectionParams params) {
		persistWikiLegacyHost(params.wikiLegacyHost());
		Map<Integer, String> importNamespaceIds = configuredImportNamespaces();
		if (params.wikiNamespaceIds() != null) importNamespaceIds.putAll(params.wikiNamespaceIds());
		reconcileImportNamespacesWithRoleHolders(importNamespaceIds);
		Map<String, String> editPolicyByName = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for (var entry : importNamespaceIds.entrySet()) {
			String name = entry.getValue() == null ? "" : entry.getValue().trim();
			if (name.isEmpty()) continue;
			editPolicyByName.merge(name, Objects.requireNonNullElse(
					CmsSupport.defaultEditPermissionCode(entry.getKey()), ""),
					JobService::strictestEditPolicy);
		}
		if (params.wikiNamespaceIds() != null) params.wikiNamespaceIds().keySet().stream()
				.map(importNamespaceIds::get).filter(Objects::nonNull)
				.map(String::trim).distinct().sorted(String.CASE_INSENSITIVE_ORDER).forEach(name ->
				targetJdbc.update("insert into zfgbb.wiki_namespace(name) values (?) on conflict do nothing", name));
		for (var entry : editPolicyByName.entrySet()) {
			if (entry.getValue().isEmpty()) continue;
			targetJdbc.update("update zfgbb.wiki_namespace set edit_permission_code = ? "
					+ "where lower(name) = lower(?) and edit_permission_code is null",
					entry.getValue(), entry.getKey());
		}
		for (var entry : importNamespaceIds.entrySet()) {
			String role = CmsSupport.engineRoleName(entry.getKey());
			String name = entry.getValue() == null ? "" : entry.getValue().trim();
			if (role == null || name.isEmpty()) continue;
			targetJdbc.update("update zfgbb.wiki_namespace set engine_role = ? "
					+ "where lower(name) = lower(?) and engine_role is null "
					+ "and not exists (select 1 from zfgbb.wiki_namespace held where held.engine_role = ?)",
					role, name, role);
		}
		for (var entry : importNamespaceIds.entrySet()) {
			String name = entry.getValue() == null ? "" : entry.getValue().trim();
			if (name.isEmpty()) continue;
			targetJdbc.update("update zfgbb.wiki_import_namespace set namespace_name = ?, updated_ts = now() "
					+ "where source_namespace_id = ? and namespace_name is distinct from ? "
					+ "and not exists (select 1 from zfgbb.wiki_import_namespace other "
					+ "where lower(other.namespace_name) = lower(?) and other.source_namespace_id <> ?)",
					name, entry.getKey(), name, name, entry.getKey());
		}
		Map<String, String> requestedModes = normalizeCiMap(params.wikiNamespaceCaseModes(), "namespace");
		for (var entry : requestedModes.entrySet()) {
			String mode = WikiTitle.CaseMode.valueOf(entry.getValue()).name();
			List<String> existing = targetJdbc.queryForList(
					"select name from zfgbb.wiki_namespace where lower(name)=lower(?)", String.class, entry.getKey());
			String canonicalName = existing.isEmpty() ? entry.getKey().trim() : existing.get(0);
			targetJdbc.update("insert into zfgbb.wiki_namespace(name, case_mode) values (?, ?) "
					+ "on conflict (name) do update set case_mode = excluded.case_mode", canonicalName, mode);
		}
		Map<String, String> requestedAliases = normalizeCiMap(params.wikiNamespaceAliases(), "alias");
		requestedAliases.entrySet().stream()
				.sorted(Map.Entry.comparingByKey()).forEach(entry -> {
			List<String> targets = targetJdbc.queryForList(
					"select name from zfgbb.wiki_namespace where lower(name)=lower(?)", String.class, entry.getValue().trim());
			if (targets.size() != 1) throw new IllegalArgumentException("Wiki namespace alias target does not exist: " + entry.getValue());
			targetJdbc.update("delete from zfgbb.wiki_namespace_alias where lower(alias)=lower(?)", entry.getKey().trim());
			targetJdbc.update("insert into zfgbb.wiki_namespace_alias(alias, namespace_name) values (?, ?) "
					+ "on conflict (alias) do update set namespace_name=excluded.namespace_name",
					entry.getKey().trim(), targets.get(0));
		});
		Map<String, String> resolvedModes = targetJdbc.query("select name, case_mode from zfgbb.wiki_namespace",
				rs -> { Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				while (rs.next()) result.put(rs.getString(1), rs.getString(2)); return result; });
		Map<String, String> resolvedAliases = targetJdbc.query(
				"select alias, namespace_name from zfgbb.wiki_namespace_alias",
				rs -> { Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
					while (rs.next()) result.put(rs.getString(1), rs.getString(2)); return result; });
		Map<Integer, String> effectiveNamespaceIds = importNamespaceIds;
		return new SmfConnectionParams(params.jdbcUrl(), params.username(), params.password(), params.smfTablePrefix(),
				params.smfLegacyHost(), params.appBaseUrl(), params.attachmentsSourcePath(),
				params.avatarsSourcePath(), params.cmsFilesSourcePath(), params.wikiImagesSourcePath(), params.force(),
				params.createMemberWikiPages(), params.discussionBoardId(), params.resourcesBoardId(), params.talkBoardIds(),
				params.groupPermissionMap(), Map.copyOf(resolvedModes), Map.copyOf(resolvedAliases),
				Map.copyOf(effectiveNamespaceIds), params.wikiLegacyHost());
	}

	private Map<String, String> normalizeCiMap(Map<String, String> input, String label) {
		Map<String, String> normalized = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		if (input != null) input.forEach((key, value) -> {
			if (key == null || key.isBlank() || normalized.putIfAbsent(key.trim(), value) != null)
				throw new IllegalArgumentException("Duplicate or blank wiki namespace " + label + ": " + key);
		});
		return normalized;
	}

	private void validateTablePrefix(String tablePrefix) {
		if (tablePrefix != null && !tablePrefix.isBlank() && !tablePrefix.trim().matches("^[A-Za-z0-9_]+$")) {
			throw new IllegalArgumentException("Invalid migrator table prefix '" + tablePrefix + "'.");
		}
	}

	private void validateConnection(SmfConnectionParams params) {
		DataSource dataSource = buildDataSource(params.jdbcUrl(), params.username(), params.password());
		try (Connection conn = dataSource.getConnection()) {
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot connect to SMF database: " + e.getMessage(), e);
		} finally {
			closeDataSource(dataSource);
		}
	}

	public List<SmfBoardSummary> listBoards(String host, Integer port, String database,
			String user, String password, String tablePrefix) {
		String prefix = (tablePrefix == null || tablePrefix.isBlank()) ? "smf_" : tablePrefix;
		if (!prefix.matches("^[A-Za-z0-9_]*$")) {
			throw new IllegalArgumentException("Invalid table prefix.");
		}
		String jdbcUrl = SmfConnectionParams.smfJdbcUrl(host, port, database)
				+ "?useSSL=false&allowPublicKeyRetrieval=true";
		DataSource dataSource = buildDataSource(jdbcUrl, user, password);
		try {
			JdbcTemplate jdbc = new JdbcTemplate(dataSource);
			return jdbc.query(
					"SELECT id_board, name, num_topics, num_posts FROM " + prefix + "boards ORDER BY name",
					(rs, rowNum) -> new SmfBoardSummary(rs.getInt("id_board"), rs.getString("name"),
							rs.getInt("num_topics"), rs.getInt("num_posts")));
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot connect to SMF database: " + e.getMessage(), e);
		} finally {
			closeDataSource(dataSource);
		}
	}

	public List<SmfMemberGroupSummary> listMemberGroups(String host, Integer port, String database,
			String user, String password, String tablePrefix) {
		String prefix = (tablePrefix == null || tablePrefix.isBlank()) ? "smf_" : tablePrefix;
		if (!prefix.matches("^[A-Za-z0-9_]*$")) {
			throw new IllegalArgumentException("Invalid table prefix.");
		}
		String jdbcUrl = SmfConnectionParams.smfJdbcUrl(host, port, database)
				+ "?useSSL=false&allowPublicKeyRetrieval=true";
		DataSource dataSource = buildDataSource(jdbcUrl, user, password);
		try {
			JdbcTemplate jdbc = new JdbcTemplate(dataSource);
			return jdbc.query(
					"SELECT id_group, group_name FROM " + prefix + "membergroups ORDER BY id_group",
					(rs, rowNum) -> new SmfMemberGroupSummary(rs.getInt("id_group"),
							rs.getString("group_name"), List.of()));
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot connect to SMF database: " + e.getMessage(), e);
		} finally {
			closeDataSource(dataSource);
		}
	}

	private Job prepareOne(JobType type, SmfConnectionParams params) {
		Job job = new Job();
		job.setId(UUID.randomUUID());
		job.setType(type);
		job.setSubmittedAt(Instant.now());
		job.setSmfJdbcUrl(params.jdbcUrl());
		job.setSmfUser(params.username());
		job.setSmfPassword(params.password());
		job.setSmfTablePrefix(params.smfTablePrefix());
		job.setSmfLegacyHost(params.smfLegacyHost());
		job.setAppBaseUrl(params.appBaseUrl());
		job.setAttachmentsSourcePath(params.attachmentsSourcePath());
		job.setAvatarsSourcePath(params.avatarsSourcePath());
		job.setCmsFilesSourcePath(params.cmsFilesSourcePath());
		job.setWikiImagesSourcePath(params.wikiImagesSourcePath());
		job.setForce(params.force());
		job.setCreateMemberWikiPages(params.createMemberWikiPages());
		job.setDiscussionBoardId(params.discussionBoardId());
		job.setResourcesBoardId(params.resourcesBoardId());
		job.setTalkBoardIds(params.talkBoardIds());
		job.setGroupPermissionMap(params.groupPermissionMap());
		job.setWikiNamespaceCaseModes(params.wikiNamespaceCaseModes());
		job.setWikiNamespaceAliases(params.wikiNamespaceAliases());
		job.setWikiNamespaceIds(params.wikiNamespaceIds());
		job.setState(JobState.QUEUED);
		return job;
	}

	private String contentRootPath() {
		return contentRoots.map(roots -> roots.activeContentRoot().toString()).orElse(null);
	}

	private void run(Job job) {
		synchronized (job) {
			if (job.getState() != JobState.QUEUED) {
				futures.remove(job.getId());
				return;
			}
			job.setStartedAt(Instant.now());
			job.setState(JobState.RUNNING);
		}
		DataSource dataSource = null;
		JobState outcome = JobState.COMPLETED;
		String error = null;
		try {
			dataSource = buildDataSource(job.getSmfJdbcUrl(), job.getSmfUser(), job.getSmfPassword());
			JobContextHolder.set(dataSource, job.getAttachmentsSourcePath(), contentRootPath(),
					job.getAvatarsSourcePath(), job.getCmsFilesSourcePath(), job.getWikiImagesSourcePath(),
					job.getSmfTablePrefix(), job.getSmfLegacyHost(), job.getAppBaseUrl(), job.isForce(),
					job.isCreateMemberWikiPages(), job.getDiscussionBoardId(), job.getResourcesBoardId(),
					job.getTalkBoardIds(), job.getGroupPermissionMap(), job.getWikiNamespaceCaseModes(),
					job.getWikiNamespaceAliases(), job.getWikiNamespaceIds());

			dispatch(job.getType());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			outcome = JobState.CANCELLED;
		} catch (Exception e) {
			if (Thread.currentThread().isInterrupted() || e.getCause() instanceof InterruptedException) {
				outcome = JobState.CANCELLED;
			} else {
				log.error("Migrator job {} ({}) failed", job.getId(), job.getType(), e);
				outcome = JobState.FAILED;
				error = e.getMessage();
			}
		} catch (Error e) {
			log.error("Migrator job {} ({}) failed hard", job.getId(), job.getType(), e);
			outcome = JobState.FAILED;
			error = e.toString();
			throw e;
		} finally {
			futures.remove(job.getId());
			JobContextHolder.clear();
			closeDataSource(dataSource);
			finish(job, outcome, error);
		}
	}

	private void finish(Job job, JobState state, String error) {
		synchronized (job) {
			job.setError(error);
			job.setFinishedAt(Instant.now());
			accountTerminal(job);
			job.setState(state);
		}
		if (state == JobState.FAILED) {
			cancelRemainingAfterFailure(job);
		}
	}

	private void cancelRemainingAfterFailure(Job failed) {
		List<Job> queued = jobs.values().stream()
				.filter(candidate -> admittedJobs.contains(candidate.getId()))
				.filter(candidate -> candidate.getState() == JobState.QUEUED)
				.toList();
		if (queued.isEmpty()) {
			return;
		}
		log.error("{} failed; cancelling the {} step(s) still queued in this run", failed.getType(), queued.size());
		queued.forEach(job -> cancelQueued(job,
				"Skipped because " + failed.getType() + " failed earlier in this run"));
	}

	public int cancelAll() {
		List<UUID> admitted;
		synchronized (this) {
			admitted = List.copyOf(admittedJobs);
		}
		int cancelled = 0;
		for (UUID id : admitted) {
			Job job = jobs.get(id);
			JobState before = job == null ? null : job.getState();
			cancel(id);
			JobState after = job == null ? null : job.getState();
			if (before != after) {
				cancelled++;
			}
		}
		return cancelled;
	}

	private void dispatch(JobType type) throws Exception {
		AbstractConverter<?> converter = convertersByType.get(type);
		if (converter == null) {
			throw new IllegalStateException(type + " has no associated converter (likely a pipeline marker)");
		}
		converter.convertToZfgbb();
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
	}

	private DataSource buildDataSource(String jdbcUrl, String username, String password) {
		return DataSourceBuilder.create()
				.url(jdbcUrl)
				.username(username)
				.password(password)
				.build();
	}

	private void closeDataSource(DataSource dataSource) {
		if (dataSource instanceof AutoCloseable closeable) {
			try {
				closeable.close();
			} catch (Exception ignored) {
			}
		}
	}

	public Optional<Job> get(UUID id) {
		return Optional.ofNullable(jobs.get(id));
	}

	public Collection<Job> list() {
		return jobs.values();
	}

	public boolean cancel(UUID id) {
		Future<?> future = futures.get(id);
		Job job = jobs.get(id);
		if (future == null || job == null) {
			return false;
		}
		boolean queuedClaim;
		synchronized (job) {
			queuedClaim = job.getState() == JobState.QUEUED;
			if (queuedClaim) {
				job.setFinishedAt(Instant.now());
				accountTerminal(job);
				job.setState(JobState.CANCELLED);
			}
		}
		boolean cancelled = future.cancel(true);
		if (queuedClaim) {
			futures.remove(id, future);
		}
		return cancelled;
	}

	@PreDestroy
	public void stopJobs() {
		jobs.values().stream().filter(job -> admittedJobs.contains(job.getId()))
				.forEach(job -> cancelQueued(job, "Migration service shut down"));
	}

	private void cancelQueued(Job job, String error) {
		boolean queuedClaim = false;
		synchronized (job) {
			if (job.getState() == JobState.RUNNING) {
				Future<?> future = futures.get(job.getId());
				if (future != null) future.cancel(true);
				return;
			}
			if (job.getState() == JobState.QUEUED) {
				job.setError(error);
				job.setFinishedAt(Instant.now());
				accountTerminal(job);
				job.setState(JobState.CANCELLED);
				queuedClaim = true;
			}
		}
		if (queuedClaim) futures.remove(job.getId());
		if (!queuedClaim) accountTerminal(job);
	}

	private synchronized void accountTerminal(Job job) {
		admittedJobs.remove(job.getId());
		releasePipelineLeaseIfIdle();
	}

	private synchronized void releasePipelineLeaseIfIdle() {
		if (!admittedJobs.isEmpty() || pipelineLease == null)
			return;
		try {
			pipelineLease.close();
		} catch (Exception failure) {
			log.error("Failed to release the migration maintenance lease", failure);
		} finally {
			pipelineLease = null;
		}
	}
}
