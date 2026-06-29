package com.zfgc.zfgbb.migrator.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.MigratorPermissionService;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;

@RestController
@RequestMapping("/system/migrate")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class MigrateController {

	private static final Logger logger = LoggerFactory.getLogger(MigrateController.class);

	private final JobService jobService;
	private final MigratorPermissionService permissionService;
	private final QuoteStripOperations quoteStripOperations;

	@Value("${zfgbb.migrator.app-base-url:}")
	private String appBaseUrl;

	public MigrateController(JobService jobService, MigratorPermissionService permissionService,
			QuoteStripOperations quoteStripOperations) {
		this.jobService = jobService;
		this.permissionService = permissionService;
		this.quoteStripOperations = quoteStripOperations;
	}

	@PostMapping("/membergroups")
	public List<SmfMemberGroupSummary> memberGroups(@RequestBody MigrateMemberGroupsRequest request) {
		if (request == null || isBlank(request.smfHost()) || isBlank(request.smfDatabase())
				|| isBlank(request.smfUser()) || request.smfPassword() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"smfHost, smfDatabase, smfUser, and smfPassword are required");
		}
		try {
			return jobService.listMemberGroups(request.smfHost(), request.smfPort(), request.smfDatabase(),
					request.smfUser(), request.smfPassword(), request.smfTablePrefix()).stream()
					.map(group -> new SmfMemberGroupSummary(group.id(), group.name(),
							new ArrayList<>(permissionService.mapSmfGroupToCodes(group.id()))))
					.toList();
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/permission-codes")
	public List<PermissionCodeSummary> permissionCodes() {
		return permissionService.listPermissionCodes();
	}

	@PostMapping("/upload")
	public ResponseEntity<MigrateUploadResponse> upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file is required");
		}

		String uploadId = UUID.randomUUID().toString();
		Path extractDir = Path.of(System.getProperty("java.io.tmpdir"), "zfgbb-migrate-" + uploadId);

		try {
			extractZip(file.getInputStream(), extractDir);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed to extract zip: " + e.getMessage());
		}

		String attachmentsPath = resolveSubdir(extractDir, "attachments");
		String avatarsPath = resolveSubdir(extractDir, "avatars");

		logger.info("Extracted migration upload {} -> attachments={}, avatars={}",
				uploadId, attachmentsPath, avatarsPath);

		return ResponseEntity.ok(new MigrateUploadResponse(uploadId, attachmentsPath, avatarsPath));
	}

	@PostMapping("/jobs")
	public ResponseEntity<List<Job>> submit(@RequestBody MigrateJobRequest request) {
		if (request == null || request.getType() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type is required");
		}
		if (isBlank(request.getSmfHost()) || isBlank(request.getSmfDatabase())
				|| isBlank(request.getSmfUser()) || isBlank(request.getSmfPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"smfHost, smfDatabase, smfUser, and smfPassword are required");
		}

		int port = request.getSmfPort() != null ? request.getSmfPort() : 3306;
		String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s",
				request.getSmfHost(), port, request.getSmfDatabase());

		String effectiveAppBaseUrl = request.getAppBaseUrl() != null && !request.getAppBaseUrl().isBlank()
				? request.getAppBaseUrl()
				: appBaseUrl;
		validateWikiNamespaces(request);
		SmfConnectionParams params = new SmfConnectionParams(
				jdbcUrl,
				request.getSmfUser(),
				request.getSmfPassword(),
				request.getSmfTablePrefix(),
				request.getSmfLegacyHost(),
				effectiveAppBaseUrl,
				request.getAttachmentsSourcePath(),
				request.getAttachmentsTargetPath(),
				request.getAvatarsSourcePath(),
				request.getCmsFilesSourcePath(),
				request.getWikiImagesSourcePath(),
				Boolean.TRUE.equals(request.getForce()),
				Boolean.TRUE.equals(request.getCreateMemberWikiPages()),
				request.getDiscussionBoardId(),
				request.getResourcesBoardId(),
				request.getTalkBoardIds(),
				request.getGroupPermissionMap(), request.getWikiNamespaceCaseModes(), request.getWikiNamespaceAliases(),
				request.getWikiNamespaceIds());

		try {
			List<Job> submitted = jobService.submit(request.getType(), params);
			return ResponseEntity.accepted().body(submitted);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/jobs")
	public Collection<Job> list() {
		return jobService.list();
	}

	@GetMapping("/jobs/{id}")
	public Job get(@PathVariable UUID id) {
		return jobService.get(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/jobs/{id}")
	public ResponseEntity<Void> cancel(@PathVariable UUID id) {
		if (jobService.get(id).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		jobService.cancel(id);
		return ResponseEntity.noContent().build();
	}

	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private void validateWikiNamespaces(MigrateJobRequest request) {
		var modes = request.getWikiNamespaceCaseModes();
		var aliases = request.getWikiNamespaceAliases();
		var ids = request.getWikiNamespaceIds();
		if (modes != null && modes.size() > 100 || aliases != null && aliases.size() > 200
				|| ids != null && ids.size() > 200)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many wiki namespace definitions");
		java.util.Set<String> identities = new java.util.HashSet<>();
		if (modes != null) modes.forEach((name, mode) -> {
			validateNamespaceToken(name, "namespace");
			try { com.zfgc.zfgbb.wiki.WikiTitle.CaseMode.valueOf(mode); }
			catch (RuntimeException e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid wiki namespace case mode for '" + name + "': " + mode); }
			if (!identities.add(name.toLowerCase(java.util.Locale.ROOT)))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate wiki namespace: " + name);
		});
		if (aliases != null) aliases.forEach((alias, target) -> {
			validateNamespaceToken(alias, "alias"); validateNamespaceToken(target, "alias target");
			if (!identities.add(alias.toLowerCase(java.util.Locale.ROOT)))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Wiki namespace alias collides with a namespace or alias: " + alias);
		});
		if (ids != null) ids.forEach((id, name) -> {
			if (id == null || id < 0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid MediaWiki namespace id: " + id);
			validateNamespaceToken(name, "ID mapping");
		});
	}

	private void validateNamespaceToken(String value, String label) {
		if (value == null || value.isBlank() || value.length() > 100 || value.indexOf(':') >= 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid wiki namespace " + label + ": " + value);
	}

	private void extractZip(InputStream inputStream, Path targetDir) throws IOException {
		Files.createDirectories(targetDir);
		try (ZipInputStream zis = new ZipInputStream(inputStream)) {
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				Path dest = targetDir.resolve(entry.getName()).normalize();
				if (!dest.startsWith(targetDir)) {
					throw new IOException("zip entry outside target directory: " + entry.getName());
				}
				if (entry.isDirectory()) {
					Files.createDirectories(dest);
				} else {
					Files.createDirectories(dest.getParent());
					Files.copy(zis, dest);
				}
				zis.closeEntry();
			}
		}
	}

	private String resolveSubdir(Path extractDir, String name) {
		Path subdir = extractDir.resolve(name);
		if (Files.isDirectory(subdir)) {
			return subdir.toString();
		}
		return null;
	}
	@PostMapping("/quote-strip/report")
	public QuoteStripOperations.QuoteStripReport quoteStripReport(
			@RequestParam(value = "runId", required = false) UUID runId) {
		return quoteStripOperations.report(runId);
	}

	@PostMapping("/quote-strip/apply/{runId}")
	public QuoteStripOperations.QuoteStripApplyResult quoteStripApply(@PathVariable("runId") UUID runId) {
		return quoteStripOperations.apply(runId);
	}

	@PostMapping("/quote-strip/revert/{runId}")
	public QuoteStripOperations.QuoteStripRevertResult quoteStripRevert(@PathVariable("runId") UUID runId) {
		return quoteStripOperations.revert(runId);
	}

	@PostMapping("/quote-strip/purge/{runId}")
	public QuoteStripOperations.QuoteStripPurgeResult quoteStripPurge(@PathVariable("runId") UUID runId,
			@RequestParam(value = "force", defaultValue = "false") boolean force) {
		return quoteStripOperations.purge(runId, force);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> onUnreadableRequest(HttpMessageNotReadableException e) {
		Throwable cause = e;
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		return ResponseEntity.badRequest().body(Map.of("error", cause.getMessage()));
	}
}
