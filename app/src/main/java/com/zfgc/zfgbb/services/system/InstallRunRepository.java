package com.zfgc.zfgbb.services.system;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.mappers.InstallRunDboMapper;
import com.zfgc.zfgbb.mappers.custom.InstallRunCustomMapper;

@Repository
public class InstallRunRepository {

	private static final Logger LOG = LoggerFactory.getLogger(InstallRunRepository.class);
	private static final List<String> RESUMABLE_PHASE_STATES = List.of("CORE_READY", "RECYCLE_READY");

	public record Run(String state, String lastCompletedState, String fingerprint, Integer adminUserId,
			String lastError, Optional<InstallStrategy> strategy) {
		public String resumableState() {
			return "FAILED".equals(state) ? lastCompletedState : state;
		}
	}

	private final InstallRunCustomMapper installRunCustomMapper;
	private final InstallRunDboMapper installRunDboMapper;

	public InstallRunRepository(InstallRunCustomMapper installRunCustomMapper,
			InstallRunDboMapper installRunDboMapper) {
		this.installRunCustomMapper = installRunCustomMapper;
		this.installRunDboMapper = installRunDboMapper;
	}

	public Run get() {
		InstallRunDbo row = installRunDboMapper.selectByPrimaryKey((short) 1);
		if (row == null) {
			LOG.warn("The install_run singleton row is missing, so this deployment is treated as "
					+ "not installed until an installation completes.");
			installRunCustomMapper.restoreMissingInstallSingleton();
			row = installRunDboMapper.selectByPrimaryKey((short) 1);
		}
		if (row == null)
			throw new IllegalStateException("install_run singleton row (install_id=1) is missing");
		return new Run(row.getState(), row.getLastCompletedState(), row.getRequestFingerprint(),
				row.getAdminUserId(), row.getLastError(),
				InstallStrategy.of(row.getInstallStrategy()));
	}

	public InstallStrategy claim(String fingerprint, String pack, boolean recycle, String siteName,
			InstallStrategy requested) {
		Run run = get();
		if ("INSTALLED".equals(run.resumableState())
				&& (run.fingerprint() == null || !run.fingerprint().equals(fingerprint)))
			throw new ZfgcConflictException("System is already installed.");
		boolean supersedesEarlierRequest =
				run.fingerprint() != null && !run.fingerprint().equals(fingerprint);
		if (supersedesEarlierRequest && !"READY".equals(run.resumableState()))
			throw new ZfgcConflictException("A different installation request is already in progress.");
		requireResumableRun(run);
		InstallStrategy effective = resumedStrategy(run).orElse(requested);
		if (effective != requested)
			throw new ZfgcConflictException("This installation already started from "
					+ effective.description() + " and stopped at " + run.resumableState()
					+ ", so it cannot be resumed from " + requested.description()
					+ ". Restore the original installation sources and retry.");
		installRunCustomMapper.claimInstall(fingerprint, pack, recycle, siteName, effective.name(),
				supersedesEarlierRequest);
		return effective;
	}

	private static Optional<InstallStrategy> resumedStrategy(Run run) {
		if (!"READY".equals(run.resumableState()))
			return run.strategy();
		return run.strategy().filter(strategy -> strategy == InstallStrategy.ARCHIVE);
	}

	private static void requireResumableRun(Run run) {
		String resumableState = run.resumableState();
		if ("READY".equals(resumableState) || "INSTALLED".equals(resumableState))
			return;
		if (!RESUMABLE_PHASE_STATES.contains(resumableState))
			throw unresumable(resumableState, "this build never records that phase");
		if (run.strategy().isEmpty())
			throw unresumable(resumableState,
					"it never recorded which installation sources it started from");
	}

	private static ZfgcConflictException unresumable(String resumableState, String reason) {
		return new ZfgcConflictException("This installation stopped at " + resumableState
				+ " and cannot be resumed because " + reason + ", so resuming it would report an "
				+ "installation whose content was never applied. Reset the zfgbb.install_run row "
				+ "(install_id=1) to state='READY', last_completed_state='READY' with a null "
				+ "request_fingerprint and a null install_strategy, then retry the installation.");
	}

	public void reestablishAfterArchiveRestore(String fingerprint, String pack, boolean recycle,
			String siteName) {
		if (installRunCustomMapper.reestablishInstallAfterArchiveRestore(fingerprint, pack, recycle,
				siteName, InstallStrategy.ARCHIVE.name()) != 1)
			throw new IllegalStateException(
					"Unable to re-establish the installation run after the archive restore.");
	}

	public void advance(List<String> expected, String next) {
		if (installRunCustomMapper.advanceInstall(expected, next) != 1)
			throw new ZfgcConflictException("Installation phase changed concurrently.");
	}

	public void setAdmin(Integer id) {
		InstallRunDbo row = new InstallRunDbo();
		row.setInstallId((short) 1);
		row.setAdminUserId(id);
		installRunDboMapper.updateByPrimaryKeySelective(row);
	}

	public void fail(Throwable error) {
		get();
		installRunCustomMapper.markInstallFailed(String.valueOf(error.getMessage()));
	}
}
