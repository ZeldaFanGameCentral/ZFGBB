package com.zfgc.zfgbb.controller.admin;

import com.zfgc.zfgbb.controller.BaseController;

import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.zfgc.zfgbb.model.system.AdminBackupResponse;
import com.zfgc.zfgbb.services.system.BackupRestoreService;

@RestController
@RequestMapping("/admin/backups")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class AdminBackupController extends BaseController {
	private final BackupRestoreService operations;

	public AdminBackupController(BackupRestoreService operations) {
		this.operations = operations;
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
	public ResponseEntity<AdminBackupResponse> createBackup() {
		return ResponseEntity.accepted()
				.cacheControl(CacheControl.noStore())
				.body(operations.createBackup(zfgcUser()));
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
	public ResponseEntity<List<AdminBackupResponse>> backups() {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore())
				.body(operations.backups());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
	public ResponseEntity<AdminBackupResponse> backup(@PathVariable String id) {
		return ResponseEntity.ok().cacheControl(CacheControl.noStore())
				.body(operations.backup(id));
	}

	@GetMapping("/{id}/download")
	@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable String id) {
		BackupRestoreService.DownloadClaim claim = operations.claimDownload(id);
		StreamingResponseBody body = output -> operations.streamDownload(claim, output);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.noStore())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(claim.job().archiveBytes())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment()
								.filename("zfgbb-backup-" + id + ".tar.gz")
								.build().toString())
				.body(body);
	}
}
