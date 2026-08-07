package com.zfgc.zfgbb.dao.meta;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.dbo.InstallRunDboExample;
import com.zfgc.zfgbb.mappers.InstallRunDboMapper;
import com.zfgc.zfgbb.mappers.custom.InstallRunCustomMapper;

@Repository
public class InstallRunDao extends KeyedDao<InstallRunDbo, InstallRunDboExample, Short> {

	private final InstallRunCustomMapper installRunCustomMapper;

	public InstallRunDao(InstallRunDboMapper mapper, InstallRunCustomMapper installRunCustomMapper) {
		super(mapper);
		this.installRunCustomMapper = installRunCustomMapper;
	}

	public int restoreMissingSingleton() {
		return installRunCustomMapper.restoreMissingInstallSingleton();
	}

	public int claim(String fingerprint, boolean recycle, String siteName, String strategy,
			boolean supersedesEarlierRequest) {
		return installRunCustomMapper.claimInstall(fingerprint, recycle, siteName, strategy,
				supersedesEarlierRequest);
	}

	public int reestablishAfterArchiveRestore(String fingerprint, boolean recycle, String siteName,
			String strategy) {
		return installRunCustomMapper.reestablishInstallAfterArchiveRestore(fingerprint, recycle, siteName,
				strategy);
	}

	public int advance(List<String> expected, String next) {
		return installRunCustomMapper.advanceInstall(expected, next);
	}

	public int markFailed(String error) {
		return installRunCustomMapper.markInstallFailed(error);
	}
}
