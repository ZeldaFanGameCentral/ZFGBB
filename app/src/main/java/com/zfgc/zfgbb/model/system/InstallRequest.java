package com.zfgc.zfgbb.model.system;

public record InstallRequest(
		String adminUserName,
		String adminDisplayName,
		String adminEmail,
		String adminPassword,
		String siteName,
		Boolean applySampleData,
		Boolean useTokens) {
}
