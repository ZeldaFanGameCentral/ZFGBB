package com.zfgc.zfgbb;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class MigrateControllerHttpTest {

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@DynamicPropertySource
	static void datasources(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url", pg::getJdbcUrl);
		r.add("spring.datasource.username", pg::getUsername);
		r.add("spring.datasource.password", pg::getPassword);
		r.add("zfgbb.migrator.enabled", () -> "true");
		r.add("zfgbb.migrator.smf.datasource.driver-class-name", () -> "org.postgresql.Driver");
		r.add("zfgbb.migrator.smf.datasource.jdbc-url", pg::getJdbcUrl);
		r.add("zfgbb.migrator.smf.datasource.username", pg::getUsername);
		r.add("zfgbb.migrator.smf.datasource.password", pg::getPassword);
	}

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private JobService jobService;

	@Test
	void post_jobs_without_auth_returns_401() throws Exception {
		mockMvc.perform(post("/system/migrate/jobs")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\":\"USERS\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void post_jobs_without_site_admin_role_returns_403() throws Exception {
		mockMvc.perform(post("/system/migrate/jobs")
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_USER")))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\":\"USERS\"}"))
				.andExpect(status().isForbidden());
	}

	@Test
	void post_jobs_as_site_admin_returns_202_with_job_list() throws Exception {
		Job stub = newJob(JobType.USERS, JobState.QUEUED);
		Mockito.when(jobService.submit(JobType.USERS)).thenReturn(List.of(stub));

		mockMvc.perform(post("/system/migrate/jobs")
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_SITE_ADMIN")))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\":\"USERS\"}"))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(stub.getId().toString()))
				.andExpect(jsonPath("$[0].type").value("USERS"))
				.andExpect(jsonPath("$[0].state").value("QUEUED"));
	}

	@Test
	void post_jobs_with_no_type_returns_400() throws Exception {
		mockMvc.perform(post("/system/migrate/jobs")
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_SITE_ADMIN")))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void get_jobs_returns_list_for_site_admin() throws Exception {
		Job usersJob = newJob(JobType.USERS, JobState.COMPLETED);
		Job categoriesJob = newJob(JobType.CATEGORIES, JobState.RUNNING);
		Mockito.when(jobService.list()).thenReturn(List.of(usersJob, categoriesJob));

		mockMvc.perform(get("/system/migrate/jobs")
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_SITE_ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void get_jobs_by_id_returns_404_when_unknown() throws Exception {
		UUID missing = UUID.randomUUID();
		Mockito.when(jobService.get(missing)).thenReturn(Optional.empty());

		mockMvc.perform(get("/system/migrate/jobs/" + missing)
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_SITE_ADMIN"))))
				.andExpect(status().isNotFound());
	}

	@Test
	void delete_jobs_by_id_returns_204_when_known() throws Exception {
		Job stub = newJob(JobType.USERS, JobState.QUEUED);
		Mockito.when(jobService.get(stub.getId())).thenReturn(Optional.of(stub));
		Mockito.when(jobService.cancel(stub.getId())).thenReturn(true);

		mockMvc.perform(delete("/system/migrate/jobs/" + stub.getId())
				.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ZFGC_SITE_ADMIN"))))
				.andExpect(status().isNoContent());
	}

	private Job newJob(JobType type, JobState state) {
		Job job = new Job();
		job.setId(UUID.randomUUID());
		job.setType(type);
		job.setState(state);
		job.setSubmittedAt(Instant.now());
		return job;
	}
}
