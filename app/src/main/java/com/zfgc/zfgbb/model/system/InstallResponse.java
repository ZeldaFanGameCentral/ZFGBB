package com.zfgc.zfgbb.model.system;

public record InstallResponse(
		boolean installed,
		Integer adminUserId,
		String siteName,
		String contentPack,
		String accessToken,
		String refreshToken) {

	public InstallResponse withTokens(String accessToken, String refreshToken) {
		return new InstallResponse(installed, adminUserId, siteName, contentPack,
				accessToken, refreshToken);
	}
}
