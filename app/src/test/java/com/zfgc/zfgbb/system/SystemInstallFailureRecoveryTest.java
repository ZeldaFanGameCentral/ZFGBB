package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import com.zfgc.zfgbb.services.system.InstallRunRepository;
import com.zfgc.zfgbb.testsupport.ZfgbbIntegrationTest;

class SystemInstallFailureRecoveryTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	@Autowired
	private InstallRunRepository installRun;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
	}

	@Test
	void failAndResumeCasGuardsTheInstallStateMachineOnARealDatabase() {
		assertEquals("READY", installRun.get().state(),
				"a fresh uninstalled container seeds install_run at READY");

		installRun.advance(List.of("READY"), "CORE_READY");
		Map<String, Object> afterAdvance = installStateRow();
		assertEquals("CORE_READY", afterAdvance.get("state"));
		assertEquals("CORE_READY", afterAdvance.get("last_completed_state"));

		installRun.fail(new RuntimeException("boom"));
		Map<String, Object> afterFail = installStateRow();
		assertEquals("FAILED", afterFail.get("state"));
		assertEquals("CORE_READY", afterFail.get("last_completed_state"),
				"fail copies the in-progress state into last_completed_state");
		assertEquals("boom", afterFail.get("last_error"));
		assertEquals("CORE_READY", installRun.get().resumableState(),
				"a FAILED run resumes from its last completed state");

		installRun.fail(new RuntimeException("again"));
		Map<String, Object> afterSecondFail = installStateRow();
		assertEquals("FAILED", afterSecondFail.get("state"));
		assertEquals("boom", afterSecondFail.get("last_error"),
				"fail is a no-op once already FAILED so the original error survives");

		installRun.claim("resume-fingerprint", "zfgc", true, "ZFGC Test");
		Map<String, Object> afterClaim = installStateRow();
		assertEquals("CORE_READY", afterClaim.get("state"),
				"claim resumes a FAILED run back to its last completed state");
		assertNull(afterClaim.get("last_error"), "claim clears the recorded error on resume");
		assertEquals("resume-fingerprint", afterClaim.get("request_fingerprint"));

		jdbcTemplate.update("update zfgbb.install_run set state = 'INSTALLED' where install_id = 1");
		installRun.fail(new RuntimeException("post-install"));
		assertEquals("INSTALLED", installStateRow().get("state"),
				"fail must never move an already INSTALLED run to FAILED");
	}

	private Map<String, Object> installStateRow() {
		return jdbcTemplate.queryForMap("select state, last_completed_state, last_error, request_fingerprint "
				+ "from zfgbb.install_run where install_id = 1");
	}
}
