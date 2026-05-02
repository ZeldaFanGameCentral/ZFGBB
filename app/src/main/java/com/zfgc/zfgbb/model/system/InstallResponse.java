package com.zfgc.zfgbb.model.system;

public record InstallResponse(
		boolean installed,
		Integer adminUserId,
		String siteName,
		boolean sampleDataApplied,
		String accessToken,
		String refreshToken) {
}
