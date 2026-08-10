package com.zfgc.zfgbb.dataprovider.system;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.model.system.InstallStrategy;
import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.dao.meta.InstallRunDao;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InstallRunDataProvider {

	private static final List<String> RESUMABLE_PHASE_STATES = List.of("CORE_READY", "RECYCLE_READY");

	public record Run(String state, String lastCompletedState, String fingerprint, Integer adminUserId,
			String lastError, Optional<InstallStrategy> strategy) {
		public String resumableState() {
			return state.equals("FAILED") ? lastCompletedState : state;
		}
	}

	private final InstallRunDao installRunDao;

	public Run get() {
		InstallRunDbo row = installRunDao.find((short) 1).orElse(null);
		if (row == null) {
			log.warn("install_run singleton row missing; treating as not installed");
			installRunDao.restoreMissingSingleton();
			row = installRunDao.find((short) 1).orElse(null);
		}
		if (row == null)
			throw new IllegalStateException("install_run singleton row (install_id=1) is missing");
		return new Run(row.getState(), row.getLastCompletedState(), row.getRequestFingerprint(),
				row.getAdminUserId(), row.getLastError(),
				InstallStrategy.of(row.getInstallStrategy()));
	}

	public InstallStrategy claim(String fingerprint, boolean recycle, String siteName,
			InstallStrategy requested) {
		Run run = get();
		if (run.resumableState().equals("INSTALLED")
				&& (run.fingerprint() == null || !run.fingerprint().equals(fingerprint)))
			throw new ZfgcConflictException("System is already installed.");
		boolean supersedesEarlierRequest = run.fingerprint() != null && !run.fingerprint().equals(fingerprint);
		if (supersedesEarlierRequest && !run.resumableState().equals("READY"))
			throw new ZfgcConflictException("A different installation request is already in progress.");
		requireResumableRun(run);
		InstallStrategy effective = resumedStrategy(run).orElse(requested);
		if (effective != requested)
			throw new ZfgcConflictException("install started from " + effective.description()
					+ " and stopped at " + run.resumableState() + ", cannot be resumed from "
					+ requested.description());
		installRunDao.claim(fingerprint, recycle, siteName, effective.name(),
				supersedesEarlierRequest);
		return effective;
	}

	private static Optional<InstallStrategy> resumedStrategy(Run run) {
		if (!run.resumableState().equals("READY"))
			return run.strategy();
		return run.strategy().filter(strategy -> strategy == InstallStrategy.ARCHIVE);
	}

	private static void requireResumableRun(Run run) {
		String resumableState = run.resumableState();
		if (resumableState.equals("READY") || resumableState.equals("INSTALLED"))
			return;
		if (!RESUMABLE_PHASE_STATES.contains(resumableState))
			throw unresumable(resumableState, "this build never records that phase");
		if (run.strategy().isEmpty())
			throw unresumable(resumableState,
					"it never recorded which installation sources it started from");
	}

	private static ZfgcConflictException unresumable(String resumableState, String reason) {
		return new ZfgcConflictException("install stopped at " + resumableState
				+ " and cannot be resumed: " + reason);
	}

	public void reestablishAfterArchiveRestore(String fingerprint, boolean recycle,
			String siteName) {
		if (installRunDao.reestablishAfterArchiveRestore(fingerprint, recycle,
				siteName, InstallStrategy.ARCHIVE.name()) != 1)
			throw new IllegalStateException("install run not re-established after archive restore");
	}

	public void advance(List<String> expected, String next) {
		if (installRunDao.advance(expected, next) != 1)
			throw new ZfgcConflictException("Installation phase changed concurrently.");
	}

	public void setAdmin(Integer id) {
		InstallRunDbo row = new InstallRunDbo();
		row.setInstallId((short) 1);
		row.setAdminUserId(id);
		installRunDao.updateSelective(row);
	}

	public void fail(Throwable error) {
		get();
		installRunDao.markFailed(String.valueOf(error.getMessage()));
	}
}
