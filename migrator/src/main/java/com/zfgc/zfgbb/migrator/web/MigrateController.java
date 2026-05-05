package com.zfgc.zfgbb.migrator.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;

@RestController
@RequestMapping("/system/migrate")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class MigrateController {

	private static final Logger logger = LoggerFactory.getLogger(MigrateController.class);

	private final JobService jobService;

	public MigrateController(JobService jobService) {
		this.jobService = jobService;
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

		SmfConnectionParams params = new SmfConnectionParams(
				jdbcUrl,
				request.getSmfUser(),
				request.getSmfPassword(),
				request.getSmfTablePrefix(),
				request.getSmfLegacyHost(),
				request.getAppBaseUrl(),
				request.getAttachmentsSourcePath(),
				request.getAttachmentsTargetPath(),
				request.getAvatarsSourcePath(),
				Boolean.TRUE.equals(request.getForce()));

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
}
