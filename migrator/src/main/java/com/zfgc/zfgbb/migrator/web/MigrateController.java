package com.zfgc.zfgbb.migrator.web;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;

@RestController
@RequestMapping("/system/migrate")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
public class MigrateController {

	private final JobService jobService;

	public MigrateController(JobService jobService) {
		this.jobService = jobService;
	}

	@PostMapping("/jobs")
	public ResponseEntity<List<Job>> submit(@RequestBody MigrateJobRequest request) {
		if (request == null || request.getType() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type is required");
		}
		List<Job> submitted = jobService.submit(request.getType());
		return ResponseEntity.accepted().body(submitted);
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
}
