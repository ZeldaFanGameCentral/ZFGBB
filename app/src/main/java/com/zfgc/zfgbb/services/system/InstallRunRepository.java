package com.zfgc.zfgbb.services.system;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.authorization.RawSqlAccess;
import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.mappers.InstallRunDboMapper;

@Repository
@RawSqlAccess("install CAS: self-ref CASE + rowcount-gated claim/advance/fail, un-generatable")
public class InstallRunRepository {

	public record Run(String state, String lastCompletedState, String fingerprint, Integer adminUserId,
			String lastError) {
		public String resumableState() {
			return "FAILED".equals(state) ? lastCompletedState : state;
		}
	}

	private final JdbcTemplate jdbc;
	private final InstallRunDboMapper installRunDboMapper;

	public InstallRunRepository(JdbcTemplate jdbc, InstallRunDboMapper installRunDboMapper) {
		this.jdbc = jdbc;
		this.installRunDboMapper = installRunDboMapper;
	}

	public Run get() {
		InstallRunDbo row = installRunDboMapper.selectByPrimaryKey((short) 1);
		if (row == null)
			throw new IllegalStateException("install_run singleton row (install_id=1) is missing");
		return new Run(row.getState(), row.getLastCompletedState(), row.getRequestFingerprint(),
				row.getAdminUserId(), row.getLastError());
	}

	public void claim(String fingerprint, String pack, boolean recycle, String siteName) {
		Run run = get();
		if ("INSTALLED".equals(run.resumableState())
				&& (run.fingerprint() == null || !run.fingerprint().equals(fingerprint)))
			throw new ZfgcConflictException("System is already installed.");
		if (run.fingerprint() != null && !run.fingerprint().equals(fingerprint))
			throw new ZfgcConflictException("A different installation request is already in progress.");
		jdbc.update("update zfgbb.install_run set request_fingerprint=?, content_pack=?, provision_recycle_bin=?, "
				+ "site_name=?, state=case when state='FAILED' then last_completed_state else state end, "
				+ "last_error=null, updated_ts=current_timestamp where install_id=1",
				fingerprint, pack, recycle, siteName);
	}

	public void advance(List<String> expected, String next) {
		String marks = String.join(",", expected.stream().map(x -> "?").toList());
		java.util.ArrayList<Object> args = new java.util.ArrayList<>();
		args.add(next);
		args.add(next);
		args.addAll(expected);
		if (jdbc.update("update zfgbb.install_run set state=?, last_completed_state=?, last_error=null, "
				+ "updated_ts=current_timestamp "
				+ "where install_id=1 and state in (" + marks + ")", args.toArray()) != 1)
			throw new ZfgcConflictException("Installation phase changed concurrently.");
	}

	public void setAdmin(Integer id) {
		InstallRunDbo row = new InstallRunDbo();
		row.setInstallId((short) 1);
		row.setAdminUserId(id);
		installRunDboMapper.updateByPrimaryKeySelective(row);
	}

	public void fail(Throwable error) {
		jdbc.update("update zfgbb.install_run set last_completed_state=state, state='FAILED', last_error=?, "
				+ "updated_ts=current_timestamp where install_id=1 and state not in ('INSTALLED','FAILED')",
				String.valueOf(error.getMessage()));
	}
}
